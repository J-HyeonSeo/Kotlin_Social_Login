package com.example.social.controller

import com.example.social.model.MemberDto
import com.example.social.service.SocialService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class SocialController(val socialService: SocialService) {

    @GetMapping("/social/info")
    fun getProfileData(principal: Principal): ResponseEntity<MemberDto> {
        val memberId = principal.name.toLong()
        return ResponseEntity.ok(socialService.getMemberInfo(memberId))
    }

}