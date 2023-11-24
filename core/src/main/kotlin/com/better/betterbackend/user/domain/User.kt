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
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "nickname")
    val nickname: String,

    @Column(name = "name")
    val name: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE, CascadeType.REMOVE])
    @JoinColumn(name = "user_rank_id")
    val userRank: UserRank,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    val memberList: List<Member> = ArrayList(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = [CascadeType.REMOVE])
    val ownedStudyList: List<Study> = ArrayList(),

): BaseTimeEntity(), UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return id.toString()
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