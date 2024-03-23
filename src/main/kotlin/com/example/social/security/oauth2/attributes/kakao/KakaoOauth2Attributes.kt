package com.example.social.security.oauth2.attributes.kakao

import com.example.social.model.Oauth2Attributes
import com.example.social.security.oauth2.attributes.Oauth2AttributesManager
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class KakaoOauth2Attributes: Oauth2AttributesManager {
    override fun getAttributes(oAuth2User: OAuth2User): Oauth2Attributes {
        val accountInfo = oAuth2User.getAttribute<Map<String, Any>>("kakao_account")
        val email = accountInfo?.get("email") as? String ?: ""
        val profileInfo = accountInfo?.get("profile") as? Map<*, *>
        val nickname = profileInfo?.get("nickname") as? String ?: ""
        val profileUrl = profileInfo?.get("profile_image_url") as? String ?: ""

        return Oauth2Attributes(email, nickname, profileUrl)
    }

}