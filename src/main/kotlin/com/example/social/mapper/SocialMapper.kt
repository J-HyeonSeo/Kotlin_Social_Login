package com.example.social.mapper

import com.example.social.model.MemberDto
import com.example.social.model.MemberResisterDto
import com.example.social.model.MemberUpdateDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface SocialMapper {

    fun existsByEmail(@Param("email") email: String): Boolean
    fun selectMemberByEmail(@Param("email") email: String): MemberDto
    fun selectMemberById(@Param("id") id: Long): MemberDto
    fun insertMember(registerDto: MemberResisterDto)
    fun updateMember(updateDto: MemberUpdateDto)


}