package com.example.social.model

data class MemberResisterDto(
    val socialId: String,
    val nickname: String,
    val profileUrl: String
)

data class MemberUpdateDto(
    val socialId: String,
    val nickname: String,
    val profileUrl: String
)

data class MemberDto(
    val id: Long,
    val socialId: String,
    val nickname: String,
    val profileUrl: String
)