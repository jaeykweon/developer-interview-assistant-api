package org.idd.dia.application.dto

import org.idd.dia.domain.entity.MemberEntity
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

// todo: viewmodel로 할까 response로 할까
data class MemberInfoViewModel(
    val pkValue: Long,
    val oauthIdValue: String,
    val oauthProviderValue: String,
    val nicknameValue: String,
    val imageUrlValue: String,
    val createdTime: String,
    val updatedTime: String
) {
    constructor(memberEntity: MemberEntity) : this(
        pkValue = memberEntity.pkValue,
        oauthIdValue = memberEntity.oauthIdValue,
        oauthProviderValue = memberEntity.oauthProviderValue,
        nicknameValue = memberEntity.nicknameValue,
        imageUrlValue = memberEntity.imageUrlValue,
        createdTime = memberEntity.createdTime.toString(),
        updatedTime = memberEntity.updatedTime.toString()
    )
}
