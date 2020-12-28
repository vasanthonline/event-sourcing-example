package com.example.demo.entities

import com.example.demo.entities.AccountQueryEntity
import org.springframework.data.repository.CrudRepository


interface AccountRepository : CrudRepository<AccountQueryEntity?, String?>
