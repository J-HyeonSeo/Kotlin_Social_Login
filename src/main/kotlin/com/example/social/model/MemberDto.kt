package com.example.social.model

data class MemberResisterDto(
    val email: String,
    val password: String,
    val name: String,
    val profileUrl: String
)

data class MemberUpdateDto(
    val email: String,
    val name: String,
    val profileUrl: String
)

data class MemberDto(
    val id: Int,
    val email: String,
    val name: String,
    val profileUrl: String
)