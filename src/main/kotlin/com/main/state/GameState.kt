package com.main.state

import com.main.util.Entity

object GameState {
    var time = Entity(0xE66B14)
    var isInGame = false


    object TroupeState {
        val playerOne = Entity(0xDAD180)
        val playerTwo = Entity(0xDAE2A8)
        val playerThree = Entity(0)
        val playerFour = Entity(0)
        val playerFive = Entity(0)
        val playerSix = Entity(0)
        val playerSeven = Entity(0)
        val playerEight = Entity(0)
    }

    val playerState = Entity(0x1065EFC)
}