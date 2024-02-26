package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.cardpreparation.CardFillService
import com.schmarrntisch.cardpreparation.config.MULTIPLE_CHOICE_END_DELIMITER
import com.schmarrntisch.cardpreparation.config.MULTIPLE_CHOICE_MID_DELIMITER
import com.schmarrntisch.cardpreparation.config.MULTIPLE_CHOICE_START_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_RANGE_END_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_RANGE_MID_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_RANGE_START_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_SIP_END_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_SIP_MID_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_SIP_START_DELIMITER
import com.schmarrntisch.cardpreparation.model.UnfilledUnterhopftCard
import com.schmarrntisch.cardpreparation.model.UninflatedUnterhopftCard
import timber.log.Timber
import java.security.InvalidParameterException

internal class CardFillServiceImpl : CardFillService {
    override fun fillCards(
        unfilledCards: List<UnfilledUnterhopftCard>,
        players: List<String>
    ): List<UninflatedUnterhopftCard> {
        val cards = mutableListOf<UninflatedUnterhopftCard>()
        unfilledCards.forEach { unfilledCard ->
            if (players.size < unfilledCard.numberPlayers) {
                Timber.e("Received card with ${unfilledCard.numberPlayers} required while only ${players.size} are available. This should not happen!")
                return@forEach
            }
            val texts = fillTexts(unfilledCard, players)
            cards.add(
                UninflatedUnterhopftCard(
                    id = unfilledCard.cardId,
                    category = unfilledCard.category,
                    texts = texts,
                    numberPlayers = unfilledCard.numberPlayers
                )
            )
        }

        return cards
    }

    private fun List<String>.fillInPlayers(
        numberPlayersInCard: Int,
        players: List<String>
    ): List<String> {
        val playersInRandomOrder = players.shuffled()
        val filledInTexts = mutableListOf<String>()
        forEach {
            var curText = it
            for (i in 0 until numberPlayersInCard) {
                curText = fillInPlayer(curText, i, playersInRandomOrder[i])
            }
            filledInTexts.add(curText)
        }
        return filledInTexts
    }

    private fun fillTexts(
        unfilledCard: UnfilledUnterhopftCard,
        players: List<String>
    ): List<String> {
        return unfilledCard.texts
            .fillInPlayers(unfilledCard.numberPlayers, players)
            .fillDrinkNumberRanges(unfilledCard.cardId)
            .fillMultipleChoiceFields(unfilledCard.cardId)
            .fillNumberRanges(unfilledCard.cardId)
            .replaceCustomLineBreaks()
    }

    private fun fillInPlayer(text: String, playerIndex: Int, player: String): String {
        return text.replace("{$playerIndex}", player)
    }

    private fun fillPlaceholder(
        texts: List<String>,
        cardId: Int,
        startDelimiter: String,
        endDelimiter: String,
        replaceFun: (String, Int, Int, Int) -> String
    ): List<String> {
        val modifiedTexts = mutableListOf<String>()
        texts.forEach { text ->
            var curText = text
            while (curText.contains(startDelimiter)) {
                if (startDelimiter.length != endDelimiter.length) {
                    throw IllegalStateException("Delimiters $startDelimiter and $endDelimiter have different lengths!")
                }
                val delimiterOffset = startDelimiter.length - 1
                if (!curText.contains(endDelimiter)) {
                    throw InvalidParameterException("Text with id $cardId has no matching numbers of $startDelimiter and $endDelimiter")
                }
                val startIndex = curText.indexOf(startDelimiter)
                val endIndex = curText.indexOf(endDelimiter) + delimiterOffset
                curText = replaceFun(curText, startIndex, endIndex, delimiterOffset)
            }
            modifiedTexts.add(curText)
        }
        return modifiedTexts
    }

    private fun List<String>.fillDrinkNumberRanges(cardId: Int): List<String> {
        return fillPlaceholder(
            this,
            cardId,
            NUMBER_SIP_START_DELIMITER,
            NUMBER_SIP_END_DELIMITER,
            ::replaceDrinkNumberRange
        )
    }

    private fun List<String>.fillNumberRanges(cardId: Int): List<String> {
        return fillPlaceholder(
            this,
            cardId,
            NUMBER_RANGE_START_DELIMITER,
            NUMBER_RANGE_END_DELIMITER,
            ::replaceNumberRange
        )
    }

    private fun List<String>.fillMultipleChoiceFields(cardId: Int): List<String> {
        return fillPlaceholder(
            this,
            cardId,
            MULTIPLE_CHOICE_START_DELIMITER,
            MULTIPLE_CHOICE_END_DELIMITER,
            ::replaceMultipleChoice
        )
    }

    private fun replaceDrinkNumberRange(
        text: String,
        startIndex: Int,
        endIndex: Int,
        delimiterOffset: Int
    ): String {
        val fullSubstring = text.substring(startIndex, endIndex + 1)
        val innerSubstring =
            fullSubstring.extractInnerSubstring(delimiterOffset)
        val (intervalStart, intervalEnd) = innerSubstring.extractInterval(
            NUMBER_SIP_MID_DELIMITER
        )
        val randomNumber = (intervalStart..intervalEnd).random()
        val drinkText = if (randomNumber == 1) "Schluck" else "Schlücke"

        return text.replace(fullSubstring, "${randomNumber.toString()} $drinkText")
    }

    private fun replaceNumberRange(
        text: String,
        startIndex: Int,
        endIndex: Int,
        delimiterOffset: Int
    ): String {
        val fullSubstring = text.substring(startIndex, endIndex + 1)
        val innerSubstring =
            fullSubstring.extractInnerSubstring(delimiterOffset)
        val (intervalStart, intervalEnd) = innerSubstring.extractInterval(
            NUMBER_RANGE_MID_DELIMITER
        )
        val randomNumber = (intervalStart..intervalEnd).random()

        return text.replace(fullSubstring, randomNumber.toString())
    }

    private fun replaceMultipleChoice(
        text: String,
        startIndex: Int,
        endIndex: Int,
        delimiterOffset: Int
    ): String {
        val fullSubstring = text.substring(startIndex, endIndex + 1)
        val innerSubstring =
            fullSubstring.extractInnerSubstring(delimiterOffset)
        val choices = innerSubstring.split(MULTIPLE_CHOICE_MID_DELIMITER)
        val randomIndex = (choices.indices).random()

        return text.replace(fullSubstring, choices[randomIndex])
    }

    private fun List<String>.replaceCustomLineBreaks(): List<String> {
        val modifiedTexts = mutableListOf<String>()
        forEach() {
            modifiedTexts.add(it.replace("\\n", "\n"))
        }
        return modifiedTexts
    }

    private fun String.extractInnerSubstring(
        delimiterOffset: Int
    ): String = substring(delimiterOffset + 1, length - delimiterOffset - 1)

    private fun String.extractInterval(delimiter: String): Pair<Int, Int> {
        val splittedInnerSubstring = split(delimiter)
        return Pair(splittedInnerSubstring[0].toInt(), splittedInnerSubstring[1].toInt())
    }
}