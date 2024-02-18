package com.schmarrntisch.cardpreparation

import com.schmarrntisch.cardpreparation.config.MULTIPLE_CHOICE_END_DELIMITER
import com.schmarrntisch.cardpreparation.config.MULTIPLE_CHOICE_MID_DELIMITER
import com.schmarrntisch.cardpreparation.config.MULTIPLE_CHOICE_START_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_RANGE_END_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_RANGE_MID_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_RANGE_START_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_SIP_END_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_SIP_MID_DELIMITER
import com.schmarrntisch.cardpreparation.config.NUMBER_SIP_START_DELIMITER
import com.schmarrntisch.cardpreparation.model.UnfilledPicoloCard
import com.schmarrntisch.cardpreparation.model.UninflatedPicoloCard
import timber.log.Timber
import java.security.InvalidParameterException

internal interface CardFillService {
    fun fillCards(
        unfilledCards: List<UnfilledPicoloCard>,
        players: List<String>
    ): List<UninflatedPicoloCard>
}