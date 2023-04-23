package com.example.atf.model

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@ExtendWith(MockKExtension::class)
class UserTest {

	private val now = LocalDate.of(2020, 1, 1)

	@BeforeEach
	fun before() {
		mockkStatic(LocalDate::class)
	}

	@Nested
	@DisplayName("GetAge")
	inner class GetAge {

		@Test
		@DisplayName("正常系")
		fun success() {
			// setup
			val birthday = LocalDateTime.of(2000, 1, 1, 0, 0).toLocalDate()
			val testSuite = spyk(User("", "", birthday, ""))
			every { LocalDate.now() } returns now

			// execute
			val actual = testSuite.getAge()

			// verify
			assertEquals(20, actual)
		}

		@Test
		@DisplayName("異常系")
		fun failed() {
			// setup
			val birthday = null
			val testSuite = spyk(User("1", "", birthday, ""))
			every { LocalDate.now() } returns now

			// execute
			val actual = testSuite.getAge()

			// verify
			assertEquals(0, actual)
		}
	}
}
