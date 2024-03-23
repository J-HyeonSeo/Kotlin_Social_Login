package com.example.social.security.oauth2.attributes.kakao

import com.example.social.model.Oauth2Attributes
import com.example.social.security.oauth2.attributes.Oauth2AttributesManager
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class KakaoOauth2Attributes: Oauth2AttributesManager {
    override fun getAttributes(oAuth2User: OAuth2User): Oauth2Attributes {
        val socialId = oAuth2User.attributes["id"] as? Long ?: ""

        val properties = oAuth2User.attributes["properties"] as Map<*, *>
        val nickname = properties["nickname"] as? String ?: ""
        val profileUrl = properties["profile_image"] as? String ?: ""

        return Oauth2Attributes(socialId.toString(), nickname, profileUrl)
    }

}