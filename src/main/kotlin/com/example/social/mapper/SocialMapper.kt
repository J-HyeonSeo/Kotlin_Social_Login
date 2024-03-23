package com.example.social.mapper

import com.example.social.model.MemberDto
import com.example.social.model.MemberResisterDto
import com.example.social.model.MemberUpdateDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface SocialMapper {

    fun existsBySocialId(@Param("socialId") email: String): Boolean
    fun selectMemberBySocialId(@Param("socialId") email: String): MemberDto
    fun selectMemberById(@Param("id") id: Long): MemberDto
    fun insertMember(registerDto: MemberResisterDto)
    fun updateMember(updateDto: MemberUpdateDto)


}