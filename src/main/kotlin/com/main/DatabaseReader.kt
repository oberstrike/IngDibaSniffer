package com.main

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

class DatabaseReader(private val databaseFile: File,
                     private val databasePassword: String) {


    fun getCredentials(): Credentials {
        val lines = databaseFile.readLines()
        val firstLine = lines[0]
        val decrypt = ChCrypto.aesDecrypt(firstLine, databasePassword)
        val content = csvReader {
            delimiter = ';'
        }.readAll(decrypt).first()
        return Credentials(
                zugangsnummer = content[0],
                pin = content[1],
                kontonummer = content[3],
                key = content[2]
        )

    }

}


