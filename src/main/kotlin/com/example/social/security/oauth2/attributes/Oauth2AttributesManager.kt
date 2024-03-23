package com.example.social.security.oauth2.attributes

import com.example.social.model.Oauth2Attributes
import org.springframework.security.oauth2.core.user.OAuth2User

interface Oauth2AttributesManager {
    fun getAttributes(oauth2User: OAuth2User): Oauth2Attributes
}