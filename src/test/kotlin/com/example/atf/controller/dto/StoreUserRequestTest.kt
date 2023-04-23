package com.example.atf.controller.dto

import io.mockk.junit5.MockKExtension
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

//@SpringBootTest
@ExtendWith(SpringExtension::class)
@ExtendWith(MockKExtension::class)
class StoreUserRequestTest {

	private lateinit var validator: Validator

	@BeforeEach
	fun before() {
		validator = Validation.buildDefaultValidatorFactory().validator
	}

	@Nested
	@DisplayName("ユーザーID")
	inner class UserId {

		@Test
		@DisplayName("未入力チェック")
		fun isNull() {
			// setup
			val storeUserRequest = StoreUserRequest(null, "test", "hoge@example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("userId is blank"))
		}

		@Test
		@DisplayName("空文字チェック")
		fun isBlank() {
			// setup
			val storeUserRequest = StoreUserRequest("", "test", "hoge@example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(2, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("userId is blank"))
			assertTrue(messages.contains("userId is invalid size"))
		}

		@Test
		@DisplayName("形式不正チェック")
		fun invalidPattern() {
			// setup
			val storeUserRequest = StoreUserRequest("#$$$", "test", "hoge@example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("userId is invalid pattern"))
		}

		@Test
		@DisplayName("最小桁数チェック")
		fun invalidMinSize() {
			// setup
			val storeUserRequest = StoreUserRequest("a".repeat(3), "test", "hoge@example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("userId is invalid size"))
		}

		@Test
		@DisplayName("最大桁数チェック")
		fun invalidMaxSize() {
			// setup
			val storeUserRequest = StoreUserRequest("a".repeat(33), "test", "hoge@example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("userId is invalid size"))
		}
	}

	@Nested
	@DisplayName("名前")
	inner class Name {

		@Test
		@DisplayName("未入力チェック")
		fun isNull() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", null, "hoge@example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("name is blank"))
		}

		@Test
		@DisplayName("空文字チェック")
		fun isBlank() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", "", "hoge@example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(2, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("name is blank"))
			assertTrue(messages.contains("name is invalid size"))
		}

		@Test
		@DisplayName("最小桁数チェック")
		fun invalidMinSize() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", "a".repeat(3), "hoge@example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("name is invalid size"))
		}

		@Test
		@DisplayName("最大桁数チェック")
		fun invalidMaxSize() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", "a".repeat(65), "hoge@example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("name is invalid size"))
		}
	}

	@Nested
	@DisplayName("メールアドレス")
	inner class EmailAddress {

		@Test
		@DisplayName("未入力チェック")
		fun isNull() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", "name", null, "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("emailAddress is blank"))
		}

		@Test
		@DisplayName("空文字チェック")
		fun isBlank() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", "name", "", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(2, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("emailAddress is blank"))
			assertTrue(messages.contains("emailAddress is invalid pattern"))
		}

		@Test
		@DisplayName("形式不正チェック")
		fun invalidPattern() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", "name", "hoge.example.com", "2000/10/20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("emailAddress is invalid pattern"))
		}
	}

	@Nested
	@DisplayName("誕生日")
	inner class Birthday {

		@Test
		@DisplayName("未入力チェック")
		fun isNull() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", "name", "hoge@example.com", null)

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("birthday is blank"))
		}

		@Test
		@DisplayName("空文字チェック")
		fun isBlank() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", "name", "hoge@example.com", "")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(2, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("birthday is blank"))
			assertTrue(messages.contains("birthday is invalid pattern"))
		}

		@Test
		@DisplayName("形式不正チェック")
		fun invalidPattern() {
			// setup
			val storeUserRequest = StoreUserRequest("userId", "name", "hoge@example.com", "2000-10-20")

			// execute
			val actual = validator.validate(storeUserRequest)

			// verify
			assertEquals(1, actual.size)
			val messages = actual.map { it.message }
			assertTrue(messages.contains("birthday is invalid pattern"))
		}
	}
}
