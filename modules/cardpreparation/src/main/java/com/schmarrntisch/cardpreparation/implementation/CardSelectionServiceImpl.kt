package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.config.MIN_DISTANCE_VIRUS_CARDS
import com.schmarrntisch.appbase.config.VIRUS_CATEGORIES
import com.schmarrntisch.appbase.model.Category
import com.schmarrntisch.cardpreparation.CardSelectionService
import com.schmarrntisch.cardpreparation.model.UnfilledUnterhopftCard
import timber.log.Timber

internal class CardSelectionServiceImpl : CardSelectionService {
    override fun selectCardsForGame(
        cardsByCategory: Map<Category, List<UnfilledUnterhopftCard>>,
        numberOfPlayers: Int
    ): List<UnfilledUnterhopftCard> {
        return CardSelectionServiceImplLogic(numberOfPlayers).selectCardsForGame(cardsByCategory)
    }
}

private class CardSelectionServiceImplLogic(
    private val numberPlayers: Int
) {
    fun selectCardsForGame(
        cardsByCategory: Map<Category, List<UnfilledUnterhopftCard>>
    ): List<UnfilledUnterhopftCard> {
        val selectedCardsByCategory = selectCardsByCategory(cardsByCategory)
        return finalizeCardStack(selectedCardsByCategory)
    }

    private fun selectCardsByCategory(cardsByCategory: Map<Category, List<UnfilledUnterhopftCard>>): HashMap<Category, List<UnfilledUnterhopftCard>> {
        val selectedCardsByCategory = HashMap<Category, List<UnfilledUnterhopftCard>>()
        Category.entries.forEach { category ->
            selectedCardsByCategory[category] =
                selectCardsOfCategory(category, cardsByCategory[category] ?: listOf())
        }
        return selectedCardsByCategory
    }

    private fun selectCardsOfCategory(
        category: Category,
        unfilledCardsOfCategory: List<UnfilledUnterhopftCard>
    ): List<UnfilledUnterhopftCard> {
        val cardsFilteredByPlayers =
            unfilledCardsOfCategory.filter { it.numberPlayers <= numberPlayers }
        if (cardsFilteredByPlayers.size < category.frequency) {
            Timber.e(
                "Category ${category.name} has ${cardsFilteredByPlayers.size} cards with the required player count of $numberPlayers, but ${category.frequency} cards are required for this category"
            )
        }

        return cardsFilteredByPlayers
            .shuffled()
            .sortedBy { it.usageCount }
            .subList(0, Integer.min(category.frequency, cardsFilteredByPlayers.size))
    }

    private fun finalizeCardStack(cardsByCategory: Map<Category, List<UnfilledUnterhopftCard>>): List<UnfilledUnterhopftCard> {
        val cardStack = mutableListOf<UnfilledUnterhopftCard>()
        Category.entries.forEach { category ->
            when (category) {
                in VIRUS_CATEGORIES -> {
                    // Must be done at the end
                }

                else -> { // all regular text cards
                    val cardsOfCategory = cardsByCategory[category] ?: listOf()
                    if (cardsOfCategory.isEmpty()) {
                        Timber.e(
                            "Category ${category.name} has 0 cards for player count of $numberPlayers"
                        )
                    }
                    cardsOfCategory.forEach {
                        cardStack.add(it)
                    }
                }
            }
        }
        val shuffledCardStack = shuffleWithoutNeighboringCategories(cardStack)

        VIRUS_CATEGORIES.forEach { virusCategory ->
            val cardsOfCategory = cardsByCategory[virusCategory] ?: listOf()
            if (cardsOfCategory.isEmpty()) {
                Timber.e(
                    "Category ${virusCategory.name} has 0 cards for player count of $numberPlayers"
                )
            }
            cardsOfCategory.forEach {
                shuffledCardStack.addVirusCard(it)
            }
        }

        return shuffledCardStack
    }

    private fun shuffleWithoutNeighboringCategories(input: List<UnfilledUnterhopftCard>): MutableList<UnfilledUnterhopftCard> {
        val mutableInput = input.shuffled().toMutableList()
        val shuffledList = mutableListOf<UnfilledUnterhopftCard>()

        while (mutableInput.isNotEmpty()) {
            var changed = false

            val iterator = mutableInput.iterator()
            while (iterator.hasNext()) {
                if (shuffledList.insertCard(iterator.next())) {
                    iterator.remove()
                    changed = true
                }
            }
            if (!changed) {
                Timber.e("Unable to create shuffled list of cards where no two cards with the same category are neighbors - Using a randomly shuffled list without conditions")
                return input.shuffled().toMutableList()
            }
        }
        return shuffledList
    }

    private fun MutableList<UnfilledUnterhopftCard>.insertCard(card: UnfilledUnterhopftCard): Boolean {
        if (isEmpty() || get(0).category != card.category) {
            add(0, card)
            return true
        } else if (get(size - 1).category != card.category) {
            add(card)
            return true
        }
        for (i in 0 until size - 1) {
            if (get(i).category != card.category && get(i + 1).category != card.category) {
                add(i + 1, card)
                return true
            }
        }
        return false
    }

    private fun MutableList<UnfilledUnterhopftCard>.addVirusCard(virusCard: UnfilledUnterhopftCard) {
        val startIndex = getVirusStartIndex(size)
        add(startIndex, virusCard)
    }

    private fun getVirusStartIndex(numberCards: Int): Int {
        if (MIN_DISTANCE_VIRUS_CARDS > numberCards) {
            throw IllegalStateException("Card stack of $numberCards cards can not fulfill min distance requirement of virus cards ($MIN_DISTANCE_VIRUS_CARDS)")
        }
        return (0..(numberCards - MIN_DISTANCE_VIRUS_CARDS)).random()
    }
}