package panda.tutorials.springboot.SpringStudy.datasource

import panda.tutorials.springboot.SpringStudy.model.Bank

interface BankDataSource {

    fun retrieveBanks() : Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
}