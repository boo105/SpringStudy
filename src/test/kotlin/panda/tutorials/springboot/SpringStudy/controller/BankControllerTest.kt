package panda.tutorials.springboot.SpringStudy.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

// 우리는 Controller를 테스트 할거기 때문에 당연히 스트링부트 실행환경을 구축해야한다.
// @SpringBootTest : 어노테이션을 통해 애플리케이션 테스트에 필요한 거의 모든 의존성들을 제공해준다.
// @AutoConfigureMockMvc : MockMvc를 생성한다.
@SpringBootTest   // 이거 하면 스프링부트 자체를 실행해 테스트 환경 만드는듯?
@AutoConfigureMockMvc // MockMvc 쓰려면 필요함.
internal class BankControllerTest {

    // @Autowired : 필요한 의존 객체의 “타입"에 해당하는 IoC 컨테이너 안에 존재하는 Bean을 찾아 주입한다.
    // 추후 더 알아보자
    @Autowired
    lateinit var mockMvc : MockMvc

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("getBanks()")
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
    @DisplayName("getBank()")
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

            // when
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }

            // then
        }

    }

}
