package com.example.atf.controller

import com.example.atf.constant.ApiConstant
import com.example.atf.constant.RequestProperty
import com.example.atf.controller.dto.GetUserProfileResponse
import com.example.atf.controller.dto.StoreUserRequest
import com.example.atf.controller.dto.UpdateUserRequest
import com.example.atf.exception.InvalidApiKeyException
import com.example.atf.model.User
import com.example.atf.service.UserService
import com.example.atf.util.ConfigUtil
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@ExtendWith(MockKExtension::class)
class UserControllerTest {

	@InjectMockKs
	private lateinit var testSuite: UserController

	@MockK
	private lateinit var configUtil: ConfigUtil

	@MockK
	private lateinit var requestProperty: RequestProperty

	@MockK
	private lateinit var userService: UserService

	private val apiKey = "atf"

	private val birthday = LocalDate.of(2000, 10, 20)

	private val now = LocalDate.of(2020, 10, 20)

	@BeforeEach
	fun before() {
		MockKAnnotations.init(this, relaxUnitFun = true)
		mockkStatic(LocalDate::class)
	}

	@Nested
	@DisplayName("ユーザープロファイル取得")
	inner class UsersGetProfileGet {

		@Test
		@DisplayName("正常系")
		fun success() {
			// setup
			val user = User("0001", "test_user", birthday, "test@jp")
			every { userService.fetch("0001") } returns user
			every { requestProperty.apiKey } returns apiKey
			every { LocalDate.now() } returns now
			every { configUtil.getLogMessage("BE8880", apiKey) } returns "request : $apiKey"
			every { configUtil.getLogMessage("BE8881", apiKey) } returns "properties : $apiKey"

			// do
			val actual = testSuite.usersGetProfileGet(apiKey, "0001")

			// verify
			val expected = GetUserProfileResponse("0001", "test_user", "test@jp", 20, "2000/10/20")
			assertEquals(expected, actual)
			verify { userService.fetch("0001") }
		}

		@Test
		@DisplayName("異常系_APIKeyエラー")
		fun failed_apiKeyError() {
			// setup
			every { requestProperty.apiKey } returns apiKey
			every { LocalDate.now() } returns now
			every { configUtil.getLogMessage("BE8880", "dummy") } returns "request : dummy"
			every { configUtil.getLogMessage("BE8881", apiKey) } returns "properties : $apiKey"

			// verify
			assertThrows<InvalidApiKeyException> {
				// do
				testSuite.usersGetProfileGet("dummy", "0001")
			}
		}
	}

	@Nested
	@DisplayName("ユーザープロファイル登録")
	inner class UsersStoreProfilePost {

		@Test
		@DisplayName("正常系")
		fun success() {
			// setup
			val user = User("0001", "test_user", birthday, "test@jp")
			every { userService.create(user, ApiConstant.BE_API002) } returns Unit
			every { requestProperty.apiKey } returns apiKey
			every { LocalDate.now() } returns now
			every { configUtil.getLogMessage("BE8880", apiKey) } returns "request : $apiKey"
			every { configUtil.getLogMessage("BE8881", apiKey) } returns "properties : $apiKey"

			// do
			val request = StoreUserRequest("0001", "test_user", "test@jp", "2000/10/20")
			testSuite.usersStoreProfilePost(apiKey, request)

			// verify
			verify { userService.create(user, ApiConstant.BE_API002) }
		}

		@Test
		@DisplayName("異常系_APIKeyエラー")
		fun failed_apiKeyError() {
			// setup
			every { requestProperty.apiKey } returns apiKey
			every { LocalDate.now() } returns now
			every { configUtil.getLogMessage("BE8880", "dummy") } returns "request : dummy"
			every { configUtil.getLogMessage("BE8881", apiKey) } returns "properties : $apiKey"

			// verify
			assertThrows<InvalidApiKeyException> {
				// do
				val request = StoreUserRequest("0001", "test_user", "test@jp", "2000/10/20")
				testSuite.usersStoreProfilePost("dummy", request)
			}
		}
	}

	@Nested
	@DisplayName("ユーザープロファイル更新")
	inner class UsersUpdateProfilePut {

		@Test
		@DisplayName("正常系")
		fun success() {
			// setup
			val user = User("0001", "test_user", birthday, "test@jp")
			every { userService.update(user, ApiConstant.BE_API003) } returns Unit
			every { requestProperty.apiKey } returns apiKey
			every { LocalDate.now() } returns now
			every { configUtil.getLogMessage("BE8880", apiKey) } returns "request : $apiKey"
			every { configUtil.getLogMessage("BE8881", apiKey) } returns "properties : $apiKey"

			// do
			val request = UpdateUserRequest("test_user", "test@jp", "2000/10/20")
			testSuite.usersUpdateProfilePut(apiKey, "0001", request)

			// verify
			verify { userService.update(user, ApiConstant.BE_API003) }
		}

		@Test
		@DisplayName("異常系_APIKeyエラー")
		fun failed_apiKeyError() {
			// setup
			every { requestProperty.apiKey } returns apiKey
			every { LocalDate.now() } returns now
			every { configUtil.getLogMessage("BE8880", "dummy") } returns "request : dummy"
			every { configUtil.getLogMessage("BE8881", apiKey) } returns "properties : $apiKey"

			// verify
			assertThrows<InvalidApiKeyException> {
				// do
				val request = UpdateUserRequest("test_user", "test@jp", "2000/10/20")
				testSuite.usersUpdateProfilePut("dummy", "0001", request)
			}
		}
	}

	@Nested
	@DisplayName("ユーザープロファイル削除")
	inner class UsersDeleteProfileDelete {

		@Test
		@DisplayName("正常系")
		fun success() {
			// setup
			every { userService.delete("0001") } returns Unit
			every { requestProperty.apiKey } returns apiKey
			every { LocalDate.now() } returns now
			every { configUtil.getLogMessage("BE8880", apiKey) } returns "request : $apiKey"
			every { configUtil.getLogMessage("BE8881", apiKey) } returns "properties : $apiKey"

			// do
			testSuite.usersDeleteProfileDelete(apiKey, "0001")

			// verify
			verify { userService.delete("0001") }
		}

		@Test
		@DisplayName("異常系_APIKeyエラー")
		fun failed_apiKeyError() {
			// setup
			every { requestProperty.apiKey } returns apiKey
			every { LocalDate.now() } returns now
			every { configUtil.getLogMessage("BE8880", "dummy") } returns "request : dummy"
			every { configUtil.getLogMessage("BE8881", apiKey) } returns "properties : $apiKey"

			// verify
			assertThrows<InvalidApiKeyException> {
				// do
				testSuite.usersDeleteProfileDelete("dummy", "0001")
			}
		}
	}
}
