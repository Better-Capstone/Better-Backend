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
    //    var id: Long? = null,//id 삭제

    var nickname: String,

//    val kakaoId: String,// 얘 이름 id로 바꿔서 프라이머리키 만들기 type Long으로 바꾸기 Nullable 안해도됨

    val name: String,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val userRank: UserRank?,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val memberList: List<Member>,

): BaseTimeEntity() {
    constructor(id: Long?, nickname: String) : this(id,nickname,"Default Name", null, emptyList()){
        this.id = id
        this.nickname = nickname
    }
}
interface SaveData : JpaRepository<User,Long>{
}
@Service
class UserService {
    @Autowired
    private lateinit var saveData: SaveData

    fun saveUser(id : Long , nickname: String){
        val user = User(id = id, nickname = nickname)
        saveData.save(user)
    }
}

