package com.better.betterbackend.member.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.lang.reflect.Member

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
}