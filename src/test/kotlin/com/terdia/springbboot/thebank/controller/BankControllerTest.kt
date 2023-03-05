package com.terdia.springbboot.thebank.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.terdia.springbboot.thebank.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

private const val BASE_URL = "/api/banks"

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor (
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should returns the bank with the given account number`() {
            // given
            val accountNumber = 1234

            // when / then
            mockMvc.get("$BASE_URL/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("3.2")}
                }
        }

         @Test
         fun `should return not found exception if account number does not exists`() {
             // given
             val accountNumber = "does_not_exists"

             // when / then
             mockMvc.get("$BASE_URL/$accountNumber")
                 .andDo { print() }
                 .andExpect {
                     status { isNotFound() }
                 }
         }
    }

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all banks`() {
            mockMvc.get(BASE_URL)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].account_number") { value("1234") }
                }
        }
    }
    
    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {

         @Test
         fun `should add the new bank with given payload`() {
             // given
             val newBank = Bank("acc123", 31.45, 2)

             // when
            val performPost = mockMvc.post(BASE_URL) {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(newBank)
             }

             //then
             performPost.andDo { print() }
             .andExpect {
                 status { isCreated() }
                 content {
                     contentType(MediaType.APPLICATION_JSON)
                     json(objectMapper.writeValueAsString(newBank))
                 }
             }
         }

         @Test
         fun `should return bad request if bank with given account number already exists`() {
             // given
             val invalidBank = Bank("1234", 31.45, 2)

             // when
             val performPost = mockMvc.post(BASE_URL) {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(invalidBank)
             }

             //then
             performPost.andDo { print() }
                 .andExpect {
                     status { isBadRequest() }
             }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {

         @Test
         fun `should update an existing bank`() {
             // given
             val updatedBank = Bank("1234", 109.3, 5)

             // when
             val performPatch = mockMvc.patch(BASE_URL) {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(updatedBank)
             }
             // then
             performPatch
                 .andDo { print() }
                 .andExpect {
                     status { isAccepted() }
                     content {
                         json(objectMapper.writeValueAsString(updatedBank))
                     }
                 }

             mockMvc.get("$BASE_URL/${updatedBank.accountNumber}")
                 .andExpect {
                     content {
                         json(objectMapper.writeValueAsString(updatedBank))
                     }
                 }
         }

        @Test
        fun `should return not found exception if account number does not exists`() {
            // given
            val updatedBank = Bank("not_exist", 109.3, 5)

            // when
            val performPatch = mockMvc.patch(BASE_URL) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
            // then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank {

         @Test
         @DirtiesContext
         fun `should delete bank with the account number`() {

             // given
             val accountNumber = "1234"

             // when / then
             mockMvc.delete("$BASE_URL/$accountNumber")
                 .andDo { print() }
                 .andExpect {
                     status { isNoContent() }
                 }

             mockMvc.get("$BASE_URL/$accountNumber")
                 .andExpect {
                     content {
                         status { isNotFound() }
                     }
                 }
         }

        @Test
        fun `should return not found exception if account number does not exists`() {
            // given
            val accountNumber = "not_exist"

            // when
            mockMvc.delete("$BASE_URL/$accountNumber")
            .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }
}