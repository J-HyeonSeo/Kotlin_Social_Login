package com.example.social.model

data class MemberResisterDto(
    val email: String,
    val nickname: String,
    val profileUrl: String
)

data class MemberUpdateDto(
    val email: String,
    val nickname: String,
    val profileUrl: String
)

data class MemberDto(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileUrl: String
)