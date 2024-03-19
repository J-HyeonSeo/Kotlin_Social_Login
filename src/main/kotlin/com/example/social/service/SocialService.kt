package com.example.social.service

import com.example.social.mapper.SocialMapper
import com.example.social.model.MemberDto
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class SocialService(val socialMapper: SocialMapper) {

    fun getMemberInfo(id: Long): MemberDto {
        return socialMapper.selectMemberById(id)
    }

}