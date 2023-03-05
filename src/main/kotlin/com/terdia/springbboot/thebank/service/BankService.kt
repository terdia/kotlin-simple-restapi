package com.terdia.springbboot.thebank.service

import com.terdia.springbboot.thebank.datasource.BankDataSource
import com.terdia.springbboot.thebank.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService (@Qualifier("mock") private val dataSource: BankDataSource) {
    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun addBank(bank: Bank): Bank = dataSource.addBank(bank)
    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)
    fun deleteBank(accountNumber: String): Unit = dataSource.deleteBank(accountNumber)
}