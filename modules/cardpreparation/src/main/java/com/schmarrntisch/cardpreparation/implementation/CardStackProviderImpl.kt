package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.model.PicoloCard
import com.schmarrntisch.cardpreparation.CardExpansionService
import com.schmarrntisch.cardpreparation.CardFillService
import com.schmarrntisch.cardpreparation.CardInflationService
import com.schmarrntisch.cardpreparation.CardSelectionService
import com.schmarrntisch.cardpreparation.CardStackProvider

internal class CardStackProviderImpl(
    private val cardExpansionService: CardExpansionService,
    private val cardSelectionService: CardSelectionService,
    private val cardFillService: CardFillService,
    private val cardInflationService: CardInflationService
) : CardStackProvider {
    override fun provideCardStack(playerNames: List<String>): List<PicoloCard> {
        val expandedCardsByCategory = cardExpansionService.provideCards()
        val selectedCards =
            cardSelectionService.selectCardsForGame(expandedCardsByCategory, playerNames.size)
        val filledCards = cardFillService.fillCards(selectedCards, playerNames)
        return cardInflationService.inflateCards(filledCards)
    }
}