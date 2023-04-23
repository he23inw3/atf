package com.example.atf.mapper

import com.example.atf.mapper.entity.UserEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserMapperTest {

	@Autowired
	lateinit var jdbcTemplate: JdbcTemplate

	@Autowired
	lateinit var testSuite: UserMapper

	@Nested
	@DisplayName("Select系試験")
	@Sql("/mybatis/mapper/UserMapperTest/select.sql")
	inner class Select {

		@Test
		@DisplayName("selectAllで対象のユーザーレコードを取得できることを確認")
		fun selectAll() {
			// setup

			// execute
			val actual = testSuite.selectAll(listOf("U001", "U003"))

			// verify
			val expected = jdbcTemplate.query("select * from tbl_user where id in (?, ?);", TblUserRowMapper(), "U001", "U003")
			assertEquals(expected, actual)
		}
	}

	@Nested
	@DisplayName("Insert系試験")
	inner class Insert {

		private val birthday = LocalDate.of(2000, 1, 1)
		private val now = LocalDateTime.of(2023, 1, 1, 22, 0, 0)

		@Test
		@DisplayName("bulkInsertで複数レコードを挿入できることを確認")
		fun bulkInsert() {
			// setup
			val before = jdbcTemplate.queryForObject("select count(*) from tbl_user;", Int::class.java)
			assertEquals(before, 0)
			val u001 = UserEntity(
				id = "U001",
				name = "INSERTED_USER01",
				birthday = birthday,
				emailAddress = "a@dmm.co.jp",
				createdBy = "junit",
				createdAt = now,
				updatedBy = "junit",
				updatedAt = now
			)
			val u002 = UserEntity(
				id = "U002",
				name = "INSERTED_USER02",
				birthday = birthday,
				emailAddress = "b@dmm.co.jp",
				createdBy = "junit",
				createdAt = now,
				updatedBy = "junit",
				updatedAt = now
			)

			// execute
			testSuite.bulkInsert(listOf(u001, u002))

			// verify
			val actual = jdbcTemplate.query("select * from tbl_user;", TblUserRowMapper())
			val expected = listOf(u001, u002)
			assertEquals(expected, actual)
		}
	}

	@Nested
	@DisplayName("Update系試験")
	@Sql("/mybatis/mapper/UserMapperTest/update.sql")
	inner class Update {

		private val birthday = LocalDate.of(2000, 1, 1)
		private val createdNow = LocalDateTime.of(2022, 1, 3, 22, 0, 0)
		private val updatedNow = LocalDateTime.of(2023, 2, 4, 23, 59 ,59)

		@Test
		@DisplayName("updateでレコードを更新できることを確認")
		fun update_case1() {
			// setup
			val before = jdbcTemplate.queryForObject("select count(*) from tbl_user;", Int::class.java)
			assertEquals(before, 4)
			val u003 = UserEntity(
				id = "U003",
				name = "UPDATED_USER03",
				birthday = birthday,
				emailAddress = "c@dmm.co.jp",
				createdBy = "junit",
				createdAt = createdNow,
				updatedBy = "junit_updated",
				updatedAt = updatedNow
			)

			// execute
			testSuite.update(u003)

			// verify
			val actual = jdbcTemplate.queryForObject("select * from tbl_user WHERE ID = ?;", TblUserRowMapper(), "U003")
			val expected = UserEntity(
				id = "U003",
				name = "UPDATED_USER03",
				birthday = birthday,
				emailAddress = "c@dmm.co.jp",
				createdBy = "junit",
				createdAt = createdNow,
				updatedBy = "junit_updated",
				updatedAt = updatedNow,
			)
			assertEquals(expected, actual)
		}
	}

	@Nested
	@DisplayName("Delete系試験")
	@Sql("/mybatis/mapper/UserMapperTest/delete.sql")
	inner class Delete {

		@Test
		@DisplayName("deleteで対象レコードを削除できることを確認")
		fun delete_case1() {
			// setup
			val before = jdbcTemplate.queryForObject("select count(*) from tbl_user;", Int::class.java)
			assertEquals(before, 4)

			// execute
			testSuite.delete("U004")

			// verify
			val after = jdbcTemplate.queryForObject("select count(*) from tbl_user;", Int::class.java)
			assertEquals(after, 3)
			assertThrows<EmptyResultDataAccessException> {
				jdbcTemplate.queryForObject("select * from tbl_user where id = ?;", TblUserRowMapper(), "U004")
			}
		}
	}


	@Nested
	inner class TblUserRowMapper : RowMapper<UserEntity?> {
		@Throws(SQLException::class)
		override fun mapRow(rs: ResultSet, rowNum: Int): UserEntity {
			return UserEntity(
				id = rs.getString("id"),
				name = rs.getString("name"),
				birthday = rs.getDate("birthday").toLocalDate(),
				emailAddress = rs.getString("email_address"),
				createdBy = rs.getString("created_by"),
				createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
				updatedBy = rs.getString("updated_by"),
				updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
			)
		}
	}
}
