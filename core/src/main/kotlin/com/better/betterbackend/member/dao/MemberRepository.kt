package com.better.betterbackend.member.dao

import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Long> {

    fun findMembersByUser(user: User): List<Member>

    fun findMemberByUserAndStudy(user: User, study: Study): Member?

}