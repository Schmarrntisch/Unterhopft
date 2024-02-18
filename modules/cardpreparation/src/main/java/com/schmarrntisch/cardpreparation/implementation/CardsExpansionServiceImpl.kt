package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.model.Category
import com.schmarrntisch.cardpreparation.CardExpansionService
import com.schmarrntisch.cardpreparation.config.COLUMN_DELIMITER
import com.schmarrntisch.cardpreparation.config.LINE_DELIMITER
import com.schmarrntisch.cardpreparation.model.UnfilledPicoloCard

internal class CardsExpansionServiceImpl(
    private val cardsFileRepository: com.schmarrntisch.appbase.CardsFileRepository
) : CardExpansionService {
    private var fileContentByLines: List<String>

    init {
        try {
            val fileContent = cardsFileRepository.getFileContent()
            fileContentByLines = fileContent.split(LINE_DELIMITER)
        } catch (e: java.lang.Exception) {
            throw IllegalStateException("Failed to read in cards text file")
        }
    }

    override fun provideCards(): Map<Category, List<UnfilledPicoloCard>> {
        val cards = mutableListOf<UnfilledPicoloCard>()
        fileContentByLines.subList(1, fileContentByLines.size).forEach {
            val curCard = lineToCard(it)

            cards.add(curCard)
        }
        val groupedCards = cards.groupBy { it.category }
        if (groupedCards.keys.size > Category.entries.size) {
            throw IllegalStateException("Number of categories ${groupedCards.keys.size} is larger than number of categories ${Category.values().size}!")
        }
        return groupedCards
    }

    private fun lineToCard(line: String): UnfilledPicoloCard {
        val columns = line.split(COLUMN_DELIMITER)
        val texts = columns.subList(2, columns.size)
        return UnfilledPicoloCard(
            cardId = columns[0].toInt(),
            category = getCategoryForIdentifier(columns[1]),
            texts = texts,
            numberPlayers = calculateNumberOfPlayersInCard(texts = texts)
        )
    }

    private fun calculateNumberOfPlayersInCard(texts: List<String>): Int {
        var curMax = 0
        texts.forEach {
            curMax = Integer.max(curMax, calculateNumberOfPlayersInText(it))
        }
        return curMax
    }

    private fun calculateNumberOfPlayersInText(text: String): Int {
        var index = 0
        while (text.contains("{$index}")) {
            index++
        }
        return index
    }

    private fun getCategoryForIdentifier(identifier: String): Category {
        for (category in Category.values()) {
            if (category.name == identifier) {
                return category
            }
        }
        throw IllegalStateException("No category found for identifier $identifier")
    }
}