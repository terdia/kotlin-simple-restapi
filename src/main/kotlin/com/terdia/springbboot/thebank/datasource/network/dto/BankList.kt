package com.terdia.springbboot.thebank.datasource.network.dto

import com.terdia.springbboot.thebank.model.Bank

data class BankList(
    val results: Collection<Bank>
)
