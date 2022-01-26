package panda.tutorials.springboot.SpringStudy.controller

import com.fasterxml.jackson.databind.ObjectMapper
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
import panda.tutorials.springboot.SpringStudy.model.Bank

// 우리는 Controller를 테스트 할거기 때문에 당연히 스트링부트 실행환경을 구축해야한다.
// @SpringBootTest : 어노테이션을 통해 애플리케이션 테스트에 필요한 거의 모든 의존성들을 제공해준다.
// @AutoConfigureMockMvc : MockMvc를 생성한다.
@SpringBootTest   // 이거 하면 스프링부트 자체를 실행해 테스트 환경 만드는듯?
@AutoConfigureMockMvc // MockMvc 쓰려면 필요함.
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    // @Autowired : 필요한 의존 객체의 “타입"에 해당하는 IoC 컨테이너 안에 존재하는 Bean을 찾아 주입한다.
    // 추후 더 알아보자
    // @Autowired
    //lateinit var mockMvc : MockMvc

    val baseUrl = "/api/banks"


    // 메소드 단위로 테스트하면 유닛 테스트하기 딱좋다 ( 테스트 간의 영향이 없기 떄문 )
    // @TestInstance는 메소드끼리 영향을 주는 테스트가 가능함.

    /*
    * Spring test에서 같은 context를 사용하는 테스트(같은 context.xml 파일을 이용해서 생성되거나,
    * 같은 SpringBootApplication 이용)가 여러 개 있을 때 각각의 테스트마다 새로운 context를 생성하는게 아니라
    * 기존의 context를 재활용하기 때문에 발생하는 문제였다
    * -> 따라서 그냥 단순히 inner class test로 테스트를 하게 되면 독립적인 테스트 환경을 구성을 못함.
    * */

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {
        /* @DirtiesContext는 어노테이션을 통해 테스트를 수행하기 전, 수행한 이후,
        그리고 테스트의 각 테스트 케이스마다 수행하기 전, 수행한 이후에 context를 다시 생성하도록 지시할 수 있다.
        메소드 뿐만 아니라 클래스 레밸도 적용가능.
        * */
        @Test
        @DirtiesContext
        fun `should delete the bank with the given account number`() {
            //given
            val accountNumber = 123456
            //when
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNoContent() } }

            //then

            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound()  } }
        }

        @Test
        fun `should return NOT FOUND if no bank with given account number exists`() {
            //given
            val invalidAccountNumber = "does_not_exist"
            //when
            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
            //then
        }
    }

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            /* 여기서 MockBankDataSource를 자동으로 불러오는데....
            datasource가 하나라 그렇다고 하고
            2개이상부터는 명시를 해줘야 한다고 한다.....
            */
            // when/then
            mockMvc.get(baseUrl)
                .andDo{ print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("123456") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank with the given account number`() {
            // given
            val accountNumber = 123456

            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value(3.14) }
                    jsonPath("$.transactionFee") { value(1) }
                }
        }

        @Test
        fun `should return Not Found if the account number does not exist`() {
            // given
            val accountNumber = "does_not_exist"

            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }

    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add the new bank`() {
            //given
            val newBank = Bank("acc123", 31.415, 2)
            
            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
//                    jsonPath("$.accountNumber") { value("acc123") }
//                    jsonPath("$.trust") { value("31.415") }
//                    jsonPath("$.transactionFee") { value("2") }
                }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }
        }
        
        @Test
        fun `should return BAD REQUEST if bank with given account number already exists`() {
            //given
            val invalidBank = Bank("123456", 1.0, 1)
            
            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            
            //then
            performPost.andDo { print() }
                .andExpect {
                    status {  isBadRequest() }
                }
        }
    }
    
    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        @Test
        fun `should update an existing bank`() {
            //given
            val updateBank = Bank("123456", 1.0, 1)
            //when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateBank)
            }

            //then
            performPatchRequest.andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updateBank))
                    }
                }

            mockMvc.get("$baseUrl/${updateBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updateBank)) } }
        }
        
        @Test
        fun `should return NOT FOUND if no bank with given account number exists`() {
            //given
            val invalidBank = Bank("does_not_exist", 1.0, 1)
            //when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            performPatchRequest.andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
            //then 
        }
    }
}
