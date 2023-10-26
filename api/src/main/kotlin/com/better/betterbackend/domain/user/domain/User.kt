package com.better.betterbackend.domain.user.domain

import com.better.betterbackend.domain.member.domain.Member
import com.better.betterbackend.domain.userrank.domain.UserRank
import com.better.betterbackend.global.common.BaseTimeEntity
import jakarta.persistence.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Entity
class User (

    @Id
    var id: Long? = null,

    var nickname: String,

    val name: String,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val userRank: UserRank?,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val memberList: List<Member>,
    
): BaseTimeEntity() {
    constructor(id: Long?, nickname: String, name: String) : this(id, nickname,name, null, emptyList())

}
