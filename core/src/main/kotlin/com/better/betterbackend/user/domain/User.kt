package com.better.betterbackend.user.domain

import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.model.BaseTimeEntity
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class User (

    @Id
    var id: Long? = null,

    val nickname: String,

    val name: String,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val userRank: UserRank?,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val memberList: List<Member>,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.REMOVE])
    val ownedStudyList: List<Study>,

): BaseTimeEntity(), UserDetails {

    // todo: 생성자에 들어가는 UserRank 수정 필요
    constructor(id: Long, nickname: String, name: String) : this(id, nickname, name, null, emptyList(), emptyList())

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return nickname
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}