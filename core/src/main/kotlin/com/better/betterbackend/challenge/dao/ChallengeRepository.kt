package com.better.betterbackend.challenge.dao

import com.better.betterbackend.challenge.domain.Challenge
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChallengeRepository: JpaRepository<Challenge, Long> {
}