package org.idd.dia.application.dto

import org.idd.dia.domain.model.Member

data class RegisterMemberRequest(
    val nicknameValue: String,
    val oauthIdValue: String,
    val oauthProvider: Member.OauthProvider,
    val imageUrlValue: String,
) {
    val oauthId: Member.OauthId
        get() = Member.OauthId(oauthIdValue)

    val nickname: Member.Nickname
        get() = Member.Nickname(nicknameValue)

    val imageUrl: Member.ImageUrl
        get() = Member.ImageUrl(imageUrlValue)
}
