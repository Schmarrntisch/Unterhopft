package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.config.MIN_DISTANCE_VIRUS_CARDS
import com.schmarrntisch.appbase.config.VIRUS_CATEGORIES
import com.schmarrntisch.appbase.model.Category
import com.schmarrntisch.appbase.model.PicoloCard
import com.schmarrntisch.cardpreparation.CardInflationService
import com.schmarrntisch.cardpreparation.model.UninflatedPicoloCard

internal class CardInflationServiceImpl : CardInflationService {
    override fun inflateCards(uninflatedCardStack: List<UninflatedPicoloCard>): List<PicoloCard> {
        val virusEndCards = mutableListOf<VirusEndCard>()
        val inflatedCardStack = mutableListOf<PicoloCard>()

        for (i in uninflatedCardStack.indices) {
            inflatedCardStack.insertVirusEndCards(virusEndCards, i)
            val uninflatedCard = uninflatedCardStack[i]
            val inflatedCards = inflateCard(uninflatedCard)
            when (uninflatedCard.category) {
                in VIRUS_CATEGORIES -> {
                    inflatedCardStack.add(inflatedCards[0])
                    val virusEndIndex =
                        getVirusEndIndex(uninflatedCardStack.size, i)
                    virusEndCards.add(VirusEndCard(inflatedCards[1], virusEndIndex))
                }

                else -> {
                    inflatedCards.forEach { inflatedCard ->
                        inflatedCardStack.add(inflatedCard)
                    }
                }

            }
        }
        return inflatedCardStack
    }

    private fun MutableList<PicoloCard>.insertVirusEndCards(
        virusEndCards: MutableList<VirusEndCard>,
        index: Int
    ) {
        var matchingVirusEndCards = virusEndCards.filter { it.index == index }
        while (matchingVirusEndCards.any()) {
            val curMatch = matchingVirusEndCards.first()
            add(curMatch.card)
            virusEndCards.remove(curMatch)
            matchingVirusEndCards = virusEndCards.filter { it.index == index }
        }
    }

    private fun inflateCard(
        uninflatedCard: UninflatedPicoloCard
    ): List<PicoloCard> {
        val inflatedCards = mutableListOf<PicoloCard>()
        uninflatedCard.texts.forEach { currentText ->
            inflatedCards.add(
                PicoloCard(text = currentText)
            )
        }

        if (uninflatedCard.category in listOf(Category.VIRUS, Category.GROUPVIRUS)
            && inflatedCards.size != 2
        ) {
            throw IllegalStateException("Card with id ${uninflatedCard.id} is of category ${uninflatedCard.category.name} and has ${inflatedCards.size} texts where 2 are expected!")
        }

        return inflatedCards
    }

    private fun getVirusEndIndex(numberCards: Int, startIndex: Int): Int {
        if (MIN_DISTANCE_VIRUS_CARDS > numberCards) {
            throw IllegalStateException("Card stack of $numberCards cards can not fulfill min distance requirement of virus cards ($MIN_DISTANCE_VIRUS_CARDS)")
        }
        return ((startIndex + MIN_DISTANCE_VIRUS_CARDS) until numberCards).random()
    }

    private data class VirusEndCard(
        val card: PicoloCard,
        val index: Int
    )
}