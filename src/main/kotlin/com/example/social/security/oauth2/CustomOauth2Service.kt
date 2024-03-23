package com.example.social.security.oauth2

import com.example.social.mapper.SocialMapper
import com.example.social.model.MemberDto
import com.example.social.model.MemberResisterDto
import com.example.social.model.MemberUpdateDto
import com.example.social.model.Oauth2Attributes
import com.example.social.security.TokenProvider
import com.example.social.security.oauth2.attributes.Oauth2AttributesFactory
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOauth2Service(
    val tokenProvider: TokenProvider,
    val httpServletResponse: HttpServletResponse,
    val socialMapper: SocialMapper,
    val objectMapper: ObjectMapper,
    val oauth2AttributesFactory: Oauth2AttributesFactory
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {

        val oAuth2User: OAuth2User =
            super.loadUser(userRequest) // Oauth2 정보를 가져옴

        val clientName = userRequest?.clientRegistration?.clientName ?: ""
        val oauth2Attributes = oauth2AttributesFactory.getOauth2AttributesManager(clientName).getAttributes(oAuth2User)

        //DB에 데이터 저장 및 업데이트
        val member: MemberDto = saveOrUpdateMember(oauth2Attributes)

        //JWT(AccessToken, RefreshToken) 발급해야함.
        val token: String = tokenProvider.generateJwtToken(member.id, listOf("ROLE_TEST"))
        val tokenMap = HashMap<String, String>()
        tokenMap["token"] = token

        httpServletResponse.contentType = MediaType.APPLICATION_JSON_VALUE
        try {
            httpServletResponse.writer.use {
                it.print(
                    objectMapper.writeValueAsString(
                        tokenMap
                    )
                )
            }
        } catch (e: Exception) {
            throw RuntimeException("Something went wrong during write Json Web Token to HttpResponse")
        }

        return oAuth2User
    }

    /**
     * 해당 Member가 이미 존재하면, Update, 새로운 Member이면, 새롭게 DB에 데이터를 Insert하는 메서드.
     */
    internal fun saveOrUpdateMember(oauth2Attributes: Oauth2Attributes): MemberDto {

        val socialId = oauth2Attributes.socialId
        val nickname = oauth2Attributes.nickname
        val profileUrl = oauth2Attributes.profileUrl

        if (socialId.isEmpty() || nickname.isEmpty() || profileUrl.isEmpty()) {
            throw RuntimeException("They're not have attributes data")
        }

        //이미 회원가입한 이력이 있는 경우!
        if (socialMapper.existsBySocialId(socialId)) {
            socialMapper.updateMember(MemberUpdateDto(socialId, nickname, profileUrl))
        } else {
            socialMapper.insertMember(MemberResisterDto(socialId, nickname, profileUrl))
        }
        return socialMapper.selectMemberBySocialId(socialId)
    }

}