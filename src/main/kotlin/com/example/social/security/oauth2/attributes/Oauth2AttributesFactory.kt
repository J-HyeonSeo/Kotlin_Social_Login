package com.example.social.security.oauth2.attributes

import com.example.social.security.oauth2.attributes.google.GoogleOauth2Attributes
import com.example.social.security.oauth2.attributes.kakao.KakaoOauth2Attributes
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class Oauth2AttributesFactory(
    val googleOauth2Attributes: GoogleOauth2Attributes,
    val kakaoOauth2Attributes: KakaoOauth2Attributes
) {
    fun getOauth2AttributesManager(clientName: String): Oauth2AttributesManager = when(clientName) {
        "kakao" -> kakaoOauth2Attributes
        "google" -> googleOauth2Attributes
        else -> throw RuntimeException("Does not defined Attributes class of input clientName")
    }

}