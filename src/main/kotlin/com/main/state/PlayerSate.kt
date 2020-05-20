package com.main.state

import com.main.util.Entity

object PlayerSate {

    object ResourceState {
        val wood = Entity(0xDAD97C)
        val stone = Entity(0xDAAD5C)
        var woodCounter = 0
    }

    object Building {
        var wood = Entity(0xDAD188)
        var sawmill = Entity(0x01B2F8D4)
        var stone = Entity(0x017ECF70)
        var medium = Entity(0x01B2FA04)
    }


    object Settler {
        var archery = Entity(0x017EB05C)
        var sword = Entity(0x017EB054)
    }
}
