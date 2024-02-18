package com.schmarrntisch.appbase

import android.content.Context
import java.io.IOException
import java.io.InputStream

    interface CardsFileRepository {
        fun getFileContent(): String
    }

    internal class CardsFileRepositoryImpl(
        private val context: Context
    ) : CardsFileRepository {
        override fun getFileContent(): String {
            try {
                val inputStream = context.resources.openRawResource(R.raw.cards)
                return inputStream.convertToString()
            } catch (e: java.lang.Exception) {
                throw IOException( "Error when reading ressource file: ${e.message}")
            }
        }

        private fun InputStream.convertToString(): String {
            return bufferedReader().use { it.readText() }  // defaults to UTF-8
        }
    }