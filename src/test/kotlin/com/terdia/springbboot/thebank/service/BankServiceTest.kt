package com.terdia.springbboot.thebank.service

import com.terdia.springbboot.thebank.datasource.mock.MockBankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BankServiceTest {

    private val dataSource: MockBankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)

     @Test
     fun `should call its data source to retrieve banks`() {
         // given
         //every { dataSource.retrieveBanks() } returns emptyList()

         // when
         val service = bankService.getBanks()

         // then
         verify (exactly = 1) { dataSource.retrieveBanks() }
     }
}