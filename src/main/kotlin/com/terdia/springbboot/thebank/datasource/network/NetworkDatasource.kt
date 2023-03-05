package com.terdia.springbboot.thebank.datasource.network

import com.terdia.springbboot.thebank.datasource.BankDataSource
import com.terdia.springbboot.thebank.datasource.network.dto.BankList
import com.terdia.springbboot.thebank.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

@Repository("network")
class NetworkDatasource(
    @Autowired private val restTemplate: RestTemplate
) : BankDataSource {
    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun retrieveBanks(): Collection<Bank> {
        // https://developer.thenewboston.com/api/bank-api/banks
        val response = restTemplate.getForEntity<BankList>("http://54.193.31.159/banks")

        return response.body?.results ?: throw IOException("network issue")
    }

    override fun addBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}