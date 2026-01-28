package com.tlpcraft.kmp.demo.domain.repository

import com.tlpcraft.kmp.demo.domain.model.MyData

interface MyRepository {
    fun getMyData(): MyData
}
