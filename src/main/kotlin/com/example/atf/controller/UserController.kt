package com.example.atf.controller

import com.example.atf.config.annotation.TraceId
import com.example.atf.constant.ApiConstant
import com.example.atf.constant.RequestProperty
import com.example.atf.controller.dto.GetUserProfileResponse
import com.example.atf.controller.dto.StoreUserRequest
import com.example.atf.controller.dto.UpdateUserRequest
import com.example.atf.model.User
import com.example.atf.service.UserService
import com.example.atf.util.ConfigUtil
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/users")
class UserController(
	private val configUtil: ConfigUtil,
	private val requestProperty: RequestProperty,
	private val userService: UserService,
): ControllerBase(configUtil, requestProperty) {

	@TraceId
	@GetMapping("/getProfile")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	fun usersGetProfileGet(
		@RequestHeader(required = false, value = API_KEY) apiKey: String?,
		@RequestParam("userId") userId: String
	): GetUserProfileResponse {
		checkApiKey(apiKey)
		val user = userService.fetch(userId)
		return GetUserProfileResponse(
			userId = userId,
			name = user.name ?: "",
			emailAddress = user.emailAddress ?: "",
			age = user.getAge(),
			birthday = formatter.format(user.birthday)
		)
	}

	@TraceId
	@PostMapping("/storeProfile")
	@ResponseStatus(HttpStatus.CREATED)
	fun usersStoreProfilePost(
		@RequestHeader(required = false, value = API_KEY) apiKey: String?,
		@Valid @RequestBody storeUserRequest: StoreUserRequest
	) {
		checkApiKey(apiKey)
		val user = storeUserRequest.let {
			User(
				id = it.userId ?: "",
				name = it.name,
				emailAddress = it.emailAddress,
				birthday = LocalDate.parse(it.birthday, formatter)
			)
		}
		userService.create(user, ApiConstant.BE_API002)
	}

	@TraceId
	@PutMapping("/updateProfile")
	@ResponseStatus(HttpStatus.CREATED)
	fun usersUpdateProfilePut(
		@RequestHeader(required = false, value = API_KEY) apiKey: String?,
		@RequestParam("userId") userId: String,
		@Valid @RequestBody updateUserRequest: UpdateUserRequest
	) {
		checkApiKey(apiKey)
		val user = User(
			id = userId,
			name = updateUserRequest.name,
			emailAddress = updateUserRequest.emailAddress,
			birthday = updateUserRequest.birthday?.let {
				LocalDate.parse(it, formatter)
			}
		)
		userService.update(user, ApiConstant.BE_API003)
	}

	@TraceId
	@DeleteMapping("/deleteProfile")
	@ResponseStatus(HttpStatus.OK)
	fun usersDeleteProfileDelete(
		@RequestHeader(required = false, value = API_KEY) apiKey: String?,
		@RequestParam("userId") userId: String,
	) {
		checkApiKey(apiKey)
		userService.delete(userId)
	}
}
