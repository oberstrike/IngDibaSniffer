package com.main.state

import com.main.util.Entity

object PlayerSate {

    object Building {
        var wood = Entity(0x017ED188)
        var sawmill = Entity(0x01B2F8D4)
        var stone = Entity(0x017ECF70)
        var medium = Entity(0x01B2FA04)
    }


    object Settler {
        var archery = Entity(0x017EB05C)
        var sword = Entity(0x017EB054)
    }
}
