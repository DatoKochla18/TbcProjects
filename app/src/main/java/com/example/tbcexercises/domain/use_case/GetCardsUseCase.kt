package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.GetCard
import com.example.tbcexercises.domain.repository.CardRepository
import com.example.tbcexercises.domain.util.CardSearchType
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val cardRepository: CardRepository) {
    suspend operator fun invoke(cardSearchType: CardSearchType = CardSearchType.None): Resource<List<GetCard>, NetworkError> {
        val result = cardRepository.getCards()
        return if (result is Resource.Success) {
            when (cardSearchType) {
                is CardSearchType.AccountNumber -> Resource.Success(result.data.filter {
                    it.accountNumber.startsWith(
                        cardSearchType.text
                    )
                })

                CardSearchType.None -> result
                is CardSearchType.PersonalNumber -> Resource.Success(result.data.filter {
                    it.personalNumber.startsWith(
                        cardSearchType.text
                    )
                })

                is CardSearchType.PhoneNumber -> Resource.Success(result.data.filter {
                    it.phoneNumber.startsWith(
                        cardSearchType.text
                    )
                })
            }
        } else result


    }
}