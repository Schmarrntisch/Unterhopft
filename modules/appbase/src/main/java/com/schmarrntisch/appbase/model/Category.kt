package com.schmarrntisch.appbase.model

import com.schmarrntisch.appbase.config.FREQUENCY_CARDPARTNER
import com.schmarrntisch.appbase.config.FREQUENCY_CARDSPEED
import com.schmarrntisch.appbase.config.FREQUENCY_CONDITION
import com.schmarrntisch.appbase.config.FREQUENCY_COUNTING
import com.schmarrntisch.appbase.config.FREQUENCY_GROUPACTION
import com.schmarrntisch.appbase.config.FREQUENCY_GROUPVIRUS
import com.schmarrntisch.appbase.config.FREQUENCY_PARTNERACTION
import com.schmarrntisch.appbase.config.FREQUENCY_PLAYERACTION
import com.schmarrntisch.appbase.config.FREQUENCY_RULE
import com.schmarrntisch.appbase.config.FREQUENCY_SPEED
import com.schmarrntisch.appbase.config.FREQUENCY_TABU
import com.schmarrntisch.appbase.config.FREQUENCY_TONGUETWISTER
import com.schmarrntisch.appbase.config.FREQUENCY_VIRUS
import com.schmarrntisch.appbase.config.FREQUENCY_VOTING

enum class Category(val identifier: String, val frequency: Int) {
    VOTING("VOTING", FREQUENCY_VOTING),
    COUNTING("COUNTING", FREQUENCY_COUNTING),
    CONDITION("CONDITION", FREQUENCY_CONDITION),
    GROUPACTION("GROUPACTION", FREQUENCY_GROUPACTION),
    GROUPVIRUS("GROUPVIRUS", FREQUENCY_GROUPVIRUS),
    CARDSPEED("CARDSPEED", FREQUENCY_CARDSPEED),
    CARDPARTNER("CARDPARTNER", FREQUENCY_CARDPARTNER),
    PARTNERACTION("PARTNERACTION", FREQUENCY_PARTNERACTION),
    RULE("RULE", FREQUENCY_RULE),
    PLAYERACTION("PLAYERACTION", FREQUENCY_PLAYERACTION),
    TABU("TABU", FREQUENCY_TABU),
    VIRUS("VIRUS", FREQUENCY_VIRUS),
    TONGUETWISTER("TONGUETWISTER", FREQUENCY_TONGUETWISTER),
    SPEED("SPEED", FREQUENCY_SPEED),
}