package com.example.atf.service

import com.example.atf.model.User
import com.example.atf.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
	private val userRepository: UserRepository
) {

	@Transactional(rollbackFor = [Exception::class])
	fun create(user: User, operatorId: String) {
		userRepository.create(listOf(user), operatorId)
	}

	@Transactional(readOnly = true)
	fun fetch(userId: String): User {
		return userRepository.fetch(userId)
	}

	@Transactional(readOnly = true)
	fun fetchAll(userIdList: List<String>): List<User> {
		return userRepository.fetchAll(userIdList)
	}

	@Transactional(rollbackFor = [Exception::class])
	fun update(user: User, operatorId: String) {
		userRepository.update(user, operatorId)
	}

	@Transactional(rollbackFor = [Exception::class])
	fun delete(userId: String) {
		userRepository.delete(userId)
	}
}
