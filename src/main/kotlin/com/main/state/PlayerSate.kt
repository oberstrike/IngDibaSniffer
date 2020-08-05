package com.main.state

import com.main.util.Entity

object PlayerSate {

    object ResourceState {
        val planks = Entity(0xDAD97C)

        val stone = Entity(0xDAAD5C)
        var usedPlanksCounter = 0
        var usedStoneCounter = 0

        val rawWoodAllTime = Entity(0xDADA68)
        val rawWood = Entity(0xDAE02C)
        var usedRawWood = 0

    }

    object Building {
        var wood = Entity(0xDAD188)
        var sawmill = Entity(0x01B2F8D4)
        var stone = Entity(0x017ECF70)
        var medium = Entity(0x01B2FA04)
    }


    object Settler {
        val freeSettler = Entity(0xDAAD50)
        val worker = Entity(0xDAAF08)
        val planer = Entity(0xDAAEF8)

        val numberOfFreeBeds = Entity(0xDAAD54)

        var archery = Entity(0x017EB05C)
        var sword = Entity(0x017EB054)
    }
}
