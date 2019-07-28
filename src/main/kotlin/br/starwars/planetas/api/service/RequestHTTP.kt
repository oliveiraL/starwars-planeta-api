package br.starwars.planetas.api.service

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import java.io.BufferedReader
import java.io.InputStreamReader

object RequestHTTP {
    fun get(url: String): JsonObject {

        val getRequest = HttpGet(url)
        val httpClient = HttpClientBuilder.create().build()
        getRequest.addHeader("accept", "application/json")
        val response = httpClient.execute(getRequest)

        if (response.statusLine.statusCode != 200) {
            throw RuntimeException("Failed : HTTP error code : " + response.statusLine.statusCode)
        }

        val bufferedReader = BufferedReader(
                InputStreamReader(response.entity.content))

        val stringBuilder = StringBuilder()
        var line = bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = bufferedReader.readLine()
        }

        val jsonObject = deserialize(stringBuilder.toString())
        bufferedReader.close()

        return jsonObject
    }


    fun deserialize(json: String): JsonObject {
        val gson = Gson()
        return gson.fromJson(json, JsonObject::class.java)
    }
}
