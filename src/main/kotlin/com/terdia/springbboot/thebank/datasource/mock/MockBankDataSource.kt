package com.terdia.springbboot.thebank.datasource.mock

import com.terdia.springbboot.thebank.datasource.BankDataSource
import com.terdia.springbboot.thebank.model.Bank
import org.springframework.stereotype.Repository

@Repository("mock")
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank("1234", 3.2, 1),
        Bank("1235", 17.0, 0),
        Bank("1236", 0.2, 100),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank = findById(accountNumber)

    override fun addBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Bank with account number: ${bank.accountNumber} already exists")
        }
        banks.add(bank)

        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val existingBank = findById(bank.accountNumber)
        val index = banks.indexOf(existingBank)
        banks[index] = bank

        return banks[index]
    }

    override fun deleteBank(accountNumber: String): Unit {
        val bank = findById(accountNumber)
        banks.remove(bank)
    }

    private fun findById(accountNumber: String): Bank = (banks.firstOrNull { it.accountNumber == accountNumber }
        ?: throw NoSuchElementException("NOT_FOUND"))
}