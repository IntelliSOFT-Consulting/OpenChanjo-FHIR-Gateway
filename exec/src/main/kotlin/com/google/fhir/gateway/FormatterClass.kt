package com.google.fhir.gateway

import jakarta.servlet.http.HttpServletRequest
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FormatterClass() {

    fun readRequestBody(request: HttpServletRequest): String? {
        try {
            val stringBuilder = StringBuilder()
            BufferedReader(InputStreamReader(request.inputStream, "utf-8")).use { reader ->
                var line: String?
                while ((reader.readLine().also { line = it }) != null) {
                    stringBuilder.append(line)
                }
            }
            return stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return null // or throw an exception here depending on your needs.
        }
    }

}