package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.admin.LoginRequest
import com.tech4gen.eLearning.database.model.admin.RefreshRequest
import com.tech4gen.eLearning.database.model.admin.RegisterRequest
import com.tech4gen.eLearning.database.model.admin.RegisterResponse
import com.tech4gen.eLearning.database.model.admin.TokenResponse
import com.tech4gen.eLearning.service.AdminService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AdminController(
    private val adminService: AdminService
) {
    @PostMapping("/register")
    fun registerAdmin(
        @Valid @RequestBody request: RegisterRequest
    ): ResponseEntity<RegisterResponse> {

        val admin = adminService.createAdmin(
            userName = request.userName,
            password = request.password,
            role = request.role
        )

        val response = RegisterResponse(
            userId = admin.id.toHexString(),
            userName = request.userName,
            message = "User Created Successfully"
        )

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response)
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): TokenResponse {

        val login = adminService.adminLogin(
            userName = request.userName,
            password = request.password
        )

        return login
    }

    @PostMapping("/refresh")
    fun refresh(
        @Valid @RequestBody refreshRequest: RefreshRequest
    ): TokenResponse {
        return adminService.refreshToken(refreshRequest.refreshToken)
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") authHeader: String): ResponseEntity<Map<String, String>> {
        adminService.logout(authHeader.replace("Bearer ", ""))
        return ResponseEntity.ok(mapOf("message" to "Logout Successful"))
    }
}