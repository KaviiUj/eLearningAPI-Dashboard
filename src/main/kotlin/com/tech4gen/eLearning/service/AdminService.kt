package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Admin
import com.tech4gen.eLearning.database.document.RefreshToken
import com.tech4gen.eLearning.database.model.admin.TokenResponse
import com.tech4gen.eLearning.database.repository.AdminRepository
import com.tech4gen.eLearning.database.repository.RefreshTokenRepository
import com.tech4gen.eLearning.security.HashEncoder
import com.tech4gen.eLearning.security.JwtService
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.client.HttpClientErrorException
import java.security.MessageDigest
import java.time.Instant
import java.util.Base64
import javax.naming.AuthenticationException

@Service
class AdminService(
    private val jwtService: JwtService,
    private val adminRepository: AdminRepository,
    private val hashEncoder: HashEncoder,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun createAdmin(
        userName: String,
        password: String,
        role: Int
    ): TokenResponse {
        val hashedPassword = hashEncoder.encode(password)
        val existingAdmin = adminRepository.findByUserName(userName)

        if (existingAdmin != null) {
            throw HttpClientErrorException(HttpStatus.CONFLICT,"User already exists")
        }

        val admin = adminRepository.save(
            Admin(
                userName = userName,
                password = hashedPassword,
                role = role
            )
        )

        val accessToken = jwtService.generateAccessToken(admin.id.toString())
        val newRefresh = jwtService.generateRefreshToken(admin.id.toString())

        storeRefreshToken(admin.id, newRefresh)

        val response = TokenResponse(
            message = "User Created Successfully",
            userId = admin.id.toHexString(),
            accessToken = accessToken,
            refreshToken = newRefresh,
        )

        return response
    }


    fun adminLogin(
        userName: String,
        password: String,
    ): TokenResponse {
        val admin = adminRepository.findByUserName(userName)
            ?: throw BadCredentialsException("Invalid Credentials")
        if (!hashEncoder.matches(password, admin.password)) {
            throw BadCredentialsException("Invalid Credentials")
        }
        val accessToken = jwtService.generateAccessToken(admin.id.toString())
        val newRefresh = jwtService.generateRefreshToken(admin.id.toString())

        storeRefreshToken(admin.id, newRefresh)

        return TokenResponse(
            accessToken = accessToken,
            refreshToken = newRefresh,
            message = "Login Successful",
            userId = admin.id.toHexString()
        )
    }

    @Transactional
    fun logout(accessToken: String) {

        if (!jwtService.validAccessToken(accessToken)) {
            throw BadCredentialsException("Invalid Token")
        }

        val userId = jwtService.getUserIdFromToken(accessToken)
        val admin = adminRepository.findById(ObjectId(userId)).orElseThrow {
            throw BadCredentialsException("Invalid Token")
        }

        refreshTokenRepository.deleteByUserId(admin.id)
    }


    @Transactional
    fun refreshToken(
        refreshToken: String
    ): TokenResponse {
        if (!jwtService.validRefreshToken(refreshToken)) {
            throw AuthenticationException("Invalid refresh token")
        }

        val userId = jwtService.getUserIdFromToken(refreshToken)
        val admin = adminRepository.findById(ObjectId(userId)).orElseThrow {
            throw AuthenticationException("Invalid refresh token")
        }

        val hashed = hashedToken(refreshToken)
        refreshTokenRepository.findByUserIdAndHashedToken(admin.id, hashed) ?: throw AuthenticationException(
            "Invalid refresh token"
        )
        refreshTokenRepository.deleteByUserIdAndHashedToken(admin.id, hashed)

        val accessToken = jwtService.generateAccessToken(admin.id.toString())
        val newRefresh = jwtService.generateRefreshToken(admin.id.toString())

        storeRefreshToken(admin.id, newRefresh)

        return TokenResponse(
            accessToken = accessToken,
            refreshToken = newRefresh,
            message = "Login Successful",
            userId = admin.id.toHexString()
        )

    }


    private fun storeRefreshToken(
        userId: ObjectId,
        refreshRawToken: String
    ) {
        val hashedToken = hashedToken(refreshRawToken)
        val expiresMs = jwtService.refreshTokenValidity
        val expireAt = Instant.now().plusMillis(expiresMs)

        refreshTokenRepository.save(
            RefreshToken(
                userId = userId,
                hashedToken = hashedToken,
                expiresAt = expireAt
            )
        )
    }

    private fun hashedToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}