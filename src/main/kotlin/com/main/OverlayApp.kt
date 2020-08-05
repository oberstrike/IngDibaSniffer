package com.main

import com.main.overlay.BasicView
import tornadofx.*
import kotlin.reflect.KClass


class OverlayApp : App() {

    override val primaryView: KClass<out UIComponent>
        get() = BasicView::class

}

/*
class Client : WebSocketClient(URI.create("http://localhost:8080/queue/markus")) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        send("Hello!")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println(reason)
        println("closing")
    }

    override fun onMessage(message: String?) {
        println("received: $message")
    }

    override fun onError(ex: Exception?) {
        ex?.printStackTrace()
    }
}

 */

