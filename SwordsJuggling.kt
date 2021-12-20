package com.bignerdranch.nyethack

import java.lang.Exception

fun main() {
    var swordsJuggling: Int? = null
    var isJugglingProficient = (1..3).shuffled().last() == 3
    if (isJugglingProficient) {
        swordsJuggling = 2
    }

    try {
        proficiencyCheck(swordsJuggling)
        swordsJuggling = swordsJuggling!!.plus(1)
    } catch (e: Exception) {
        println(e)
    }
    proficiencyCheck(swordsJuggling)

    println("You juggle $swordsJuggling sword!")
}

fun proficiencyCheck(swordJuggling: Int?) {
    checkNotNull(swordJuggling, {"com.bignerdranch.nyethack.Player cannot juggle swords."})
}

class UnskilledSwordJugglerException() :
        IllegalStateException("com.bignerdranch.nyethack.Player cannot juggle swords.")
