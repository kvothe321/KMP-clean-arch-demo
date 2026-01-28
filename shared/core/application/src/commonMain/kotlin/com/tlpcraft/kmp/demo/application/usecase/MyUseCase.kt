package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.MyData
import com.tlpcraft.kmp.demo.domain.repository.MyRepository
import com.tlpcraft.kmp.demo.domain.usecase.UseCase

class MyUseCase(private val myRepository: MyRepository) : UseCase<Unit, MyData> {
    override suspend fun invoke(param: Unit) = myRepository.getMyData()
}
