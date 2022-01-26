package panda.tutorials.springboot.SpringStudy.datasource.network

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import panda.tutorials.springboot.SpringStudy.datasource.BankDataSource
import panda.tutorials.springboot.SpringStudy.datasource.network.dto.BankList
import panda.tutorials.springboot.SpringStudy.model.Bank
import java.io.IOException

// @Qualifier 사용하기 위해 이름 정의함.
@Repository("network")
class NetworkDataSource(@Autowired private val restTemplate: RestTemplate) : BankDataSource {
    // 저거 url 강의 예제에서 쓴건데 접속 안되니까 그냥 코드로만 보셈.
    override fun retrieveBanks(): Collection<Bank> {
        val response : ResponseEntity<BankList> =
            restTemplate.getForEntity<BankList>("http://54.193.31.159/banks")

        return response.body?.results
            ?: throw IOException("Could not fetch banks from the network")
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}