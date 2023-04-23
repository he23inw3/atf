package com.example.atf.repository

import com.example.atf.config.annotation.DbAccess
import com.example.atf.mapper.entity.UserEntity
import com.example.atf.model.User
import com.example.atf.mapper.UserMapper
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserRepository(
	private val userMapper: UserMapper
) {

	@DbAccess("ユーザ情報登録")
	fun create(userList: List<User>, operatorId: String) {
		val now = LocalDateTime.now()
		userList.map {
			UserEntity(
				id = it.id,
				name = it.name,
				birthday = it.birthday,
				emailAddress = it.emailAddress,
				createdBy = operatorId,
				createdAt = now,
				updatedBy = operatorId,
				updatedAt = now
			)
		}.let {
			userMapper.bulkInsert(it)
		}
	}

	@DbAccess("ユーザ情報取得")
	fun fetch(userId: String): User {
		return userMapper.selectById(userId).let {
			User(
				id = it.id,
				name = it.name,
				birthday = it.birthday,
				emailAddress = it.emailAddress
			)
		}
	}

	@DbAccess("ユーザ情報取得")
	fun fetchAll(userIdList: List<String>): List<User> {
		return userMapper.selectAll(userIdList).map {
			User(
				id = it.id,
				name = it.name,
				birthday = it.birthday,
				emailAddress = it.emailAddress
			)
		}
	}

	@DbAccess("ユーザ情報更新")
	fun update(user: User, operatorId: String) {
		val now = LocalDateTime.now()
		userMapper.update(
			UserEntity(
				id = user.id,
				name = user.name,
				birthday = user.birthday,
				emailAddress = user.emailAddress,
				updatedBy = operatorId,
				updatedAt = now
			)
		)
	}

	@DbAccess("ユーザ情報削除")
	fun delete(userId: String) {
		userMapper.delete(userId)
	}
}
