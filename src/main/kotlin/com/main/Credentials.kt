package com.main

data class Credentials(
        var kontonummer: String = "",
        var pin: String = "",
        var zugangsnummer: String = "",
        var key: String = ""
) {
    class Builder {
        private val credentials: Credentials = Credentials()

        fun kontonummer(kontonummer: String) = apply { credentials.kontonummer = kontonummer }

        fun pin(pin: String) = apply { credentials.pin = pin }

        fun zugangsnummer(zugangsnummer: String) = apply { credentials.zugangsnummer = zugangsnummer }

        fun key(key: String) = apply { credentials.key = key }

        fun build() = credentials

    }

}
