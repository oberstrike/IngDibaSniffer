package com.main

import com.main.game.MainContext
import com.main.game.Settler4Class
import com.main.overlay.OverlayApp
import com.sun.tools.javac.Main
import kotlinx.coroutines.*
import tornadofx.launch

fun main(args: Array<String>) {
    MainContext.mainJob = GlobalScope.launch(Dispatchers.IO) {
        val settler4Class = Settler4Class()
        while (isActive){
            delay(1000)
            settler4Class.readProcess()
        }
    }
    launch<OverlayApp>()
}

