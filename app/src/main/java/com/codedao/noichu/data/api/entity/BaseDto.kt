package com.codedao.noichu.data.api.entity

data class BaseDto(
    val message: String?,
    val status: Int?,
    val errors: List<String>?
)