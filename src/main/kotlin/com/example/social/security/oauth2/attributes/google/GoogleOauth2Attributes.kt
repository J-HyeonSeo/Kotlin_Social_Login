package com.example.social.security.oauth2.attributes.google

import com.example.social.model.Oauth2Attributes
import com.example.social.security.oauth2.attributes.Oauth2AttributesManager
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class GoogleOauth2Attributes: Oauth2AttributesManager {
    override fun getAttributes(oauth2User: OAuth2User): Oauth2Attributes {
        TODO("Not yet implemented")
    }
}