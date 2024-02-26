package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.config.MIN_DISTANCE_VIRUS_CARDS
import com.schmarrntisch.appbase.config.VIRUS_CATEGORIES
import com.schmarrntisch.appbase.model.Category
import com.schmarrntisch.appbase.model.UnterhopftCard
import com.schmarrntisch.cardpreparation.CardInflationService
import com.schmarrntisch.cardpreparation.model.UninflatedUnterhopftCard

internal class CardInflationServiceImpl : CardInflationService {
    override fun inflateCards(uninflatedCardStack: List<UninflatedUnterhopftCard>): List<UnterhopftCard> {
        val virusEndCards = mutableListOf<VirusEndCard>()
        val inflatedCardStack = mutableListOf<UnterhopftCard>()

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

    private fun MutableList<UnterhopftCard>.insertVirusEndCards(
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
        uninflatedCard: UninflatedUnterhopftCard
    ): List<UnterhopftCard> {
        val inflatedCards = mutableListOf<UnterhopftCard>()
        uninflatedCard.texts.forEach { currentText ->
            inflatedCards.add(
                UnterhopftCard(text = currentText, id = uninflatedCard.id)
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
        val card: UnterhopftCard,
        val index: Int
    )
}