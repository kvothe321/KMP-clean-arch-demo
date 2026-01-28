package com.tlpcraft.kmp.demo.data.repository

import com.tlpcraft.kmp.demo.domain.model.MyData
import com.tlpcraft.kmp.demo.domain.repository.MyRepository

class MyRepositoryImpl : MyRepository {
    override fun getMyData(): MyData {
        println("Hello from MyRepositoryImpl!")
        return MyData("Hello from MyRepositoryImpl!")
    }
}
