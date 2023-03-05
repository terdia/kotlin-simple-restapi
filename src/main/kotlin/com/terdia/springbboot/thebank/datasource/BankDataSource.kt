package com.terdia.springbboot.thebank.datasource

import com.terdia.springbboot.thebank.model.Bank

interface BankDataSource {

    fun retrieveBank(accountNumber: String): Bank
    fun retrieveBanks(): Collection<Bank>
    fun addBank(bank: Bank): Bank
    fun updateBank(bank: Bank): Bank
    fun deleteBank(accountNumber: String): Unit
}