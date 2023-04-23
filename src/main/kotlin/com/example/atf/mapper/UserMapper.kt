package com.example.atf.mapper

import com.example.atf.mapper.entity.UserEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UserMapper {

	fun selectById(@Param("userId") userId: String): UserEntity

	fun selectAll(@Param("userIdList") userIdList: List<String>): List<UserEntity>

	fun bulkInsert(@Param("userList") userList: List<UserEntity>)

	fun update(@Param("user") user: UserEntity)

	fun delete(@Param("userId") userId: String)
}
