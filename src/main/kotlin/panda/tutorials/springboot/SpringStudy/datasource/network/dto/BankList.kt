package panda.tutorials.springboot.SpringStudy.datasource.network.dto

import com.fasterxml.jackson.annotation.JsonProperty
import panda.tutorials.springboot.SpringStudy.model.Bank

data class BankList (
    val results : Collection<Bank>
)