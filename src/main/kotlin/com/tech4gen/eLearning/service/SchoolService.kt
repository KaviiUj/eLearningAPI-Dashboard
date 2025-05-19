package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.School
import com.tech4gen.eLearning.database.model.school.SchoolListResponse
import com.tech4gen.eLearning.database.model.school.SchoolRequest
import com.tech4gen.eLearning.database.model.school.SchoolResponse
import com.tech4gen.eLearning.database.repository.SchoolRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class SchoolService(
    private val schoolRepository: SchoolRepository
) {

    fun addSchool(schoolRequest: List<SchoolRequest>): SchoolListResponse {

        val dataList: MutableList<SchoolResponse> = arrayListOf()
        schoolRequest.forEach { schoolRequest ->
            val schoolData = School(
                address = schoolRequest.address,
                censusNumber = schoolRequest.censusNumber ?: "",
                district = schoolRequest.district,
                medium = schoolRequest.medium ?: "",
                nsPs = schoolRequest.nsPs ?: "",
                province = schoolRequest.province,
                school = schoolRequest.school,
                stage = schoolRequest.stage ?: "",
                telephoneNo = schoolRequest.telephoneNo,
                zone = schoolRequest.zone
            )

            schoolRepository.save(schoolData)
            val data = SchoolResponse(
                schoolId = schoolData.id.toHexString(),
                address = schoolData.address,
                censusNumber = schoolData.censusNumber,
                district = schoolData.district,
                medium = schoolData.medium,
                nsPs = schoolData.nsPs,
                province = schoolData.province,
                school = schoolData.school,
                stage = schoolData.stage,
                telephoneNo = schoolData.telephoneNo,
                zone = schoolData.zone,
                createdAt = schoolData.createdAt.toString()
            )

            dataList.add(data)
        }

        return SchoolListResponse(
            message = "School added successfully",
            schoolList = dataList
        )
    }

    fun getAllSchool(name: String): SchoolListResponse {
        val schoolList = schoolRepository.findAll()
        val dataList: MutableList<SchoolResponse> = arrayListOf()

        schoolList.forEach { school ->
            if (name.isEmpty() || school.school.contains(name, ignoreCase = true)) {
                val data = SchoolResponse(
                    schoolId = school.id.toHexString(),
                    address = school.address,
                    censusNumber = school.censusNumber,
                    district = school.district,
                    medium = school.medium,
                    nsPs = school.nsPs,
                    province = school.province,
                    school = school.school,
                    stage = school.stage,
                    telephoneNo = school.telephoneNo,
                    zone = school.zone,
                    createdAt = school.createdAt.toString()
                )
                dataList.add(data)
            }
        }

        if (dataList.isEmpty()) {
            throw UsernameNotFoundException("No schools found" + if (name.isNotEmpty()) " for name: $name" else "")
        }

        return SchoolListResponse(
            message = "Schools retrieved successfully",
            schoolList = dataList
        )
    }
}