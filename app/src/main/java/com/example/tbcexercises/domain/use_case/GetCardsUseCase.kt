package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.GetCard
import com.example.tbcexercises.domain.repository.CardRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val cardRepository: CardRepository) {
    suspend operator fun invoke(accountNumber: String? = null): Resource<List<GetCard>, NetworkError> {
        val result = cardRepository.getCards()
        if (result is Resource.Success) {
            accountNumber?.let { accNumber ->
                result.data.filter { it.accountNumber.startsWith(accNumber) }
            }
        }

        return cardRepository.getCards()
    }
}