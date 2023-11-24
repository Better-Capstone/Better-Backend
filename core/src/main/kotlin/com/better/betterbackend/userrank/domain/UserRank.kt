package com.better.betterbackend.userrank.domain

import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.user.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "user_rank")
class UserRank (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "score")
    var score: Int = 4000,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "userRank",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE]
    )
    var userRankHistoryList: List<UserRankHistory> = ArrayList(),

    ): BaseTimeEntity() {

}