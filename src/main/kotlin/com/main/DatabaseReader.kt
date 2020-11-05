package com.main

import org.linguafranca.pwdb.kdbx.KdbxCreds
import org.linguafranca.pwdb.kdbx.dom.DomDatabaseWrapper
import java.io.InputStream

class DatabaseReader(dataBaseInputStream: InputStream,
                     databasePassword: String) {

    private val credentials = KdbxCreds(databasePassword.toByteArray())


    private val database: DomDatabaseWrapper = DomDatabaseWrapper.load(credentials, dataBaseInputStream)

    fun getCredentials(): Credentials {
        val visitor = TestVisitor()
        database.visit(visitor)
        return visitor.credentials
    }

}


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
