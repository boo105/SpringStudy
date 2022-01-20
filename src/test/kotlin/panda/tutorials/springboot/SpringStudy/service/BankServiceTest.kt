package panda.tutorials.springboot.SpringStudy.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import panda.tutorials.springboot.SpringStudy.datasource.BankDataSource

internal class BankServiceTest {

    /*
    every {...}를 통해서 매번 mock 처리를 하는 것은 번거로울 수 있습니다.
    mock 대상이 많거나 특별히 확인할 내용이 없다면 더욱 그럴 것입니다. 이런 경우에 relaxed mock을 이용하는 게 좋습니다.
    * */
    private val dataSource : BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)

    @Test
    fun `should call its data source to retrieve banks`() {
        // given
        // every는 mock 객체가 어떻게 동작할지 정의한다.
        // 여기서는 dataSource.retrieveBanks() 함수가 emptyList를 반환하는 동작을 정의하는 것이다.
        // every { dataSource.retrieveBanks() } returns emptyList()

        // when
        bankService.getBanks();

        // then
        // verify는 mock객체를 받음, 그리고 해당 mock객체의 원하는 상호작용이 있었는가(call됬는가) 검증함.
        // 여기서는 dataSource.retrieveBanks() 가 한번의 호출이 됬는지 검증함.
        verify(exactly = 1) { dataSource.retrieveBanks() }
    }
}
