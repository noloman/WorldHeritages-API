package me.manulorenzo.worldheritages_api.api.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.manulorenzo.worldheritages_api.api.data.model.Heritage
import java.io.BufferedReader
import java.io.File
import java.lang.reflect.Type
import java.net.URL

object ParsingManager {
    suspend fun parse(): List<Heritage> =
        withContext(Dispatchers.Default) {
            val classLoader: ClassLoader = ClassLoader.getSystemClassLoader()
            val resource: URL? = classLoader.getResource("heritages.json")
            resource?.let {
                val bufferedReader = File(it.file).bufferedReader()
                val data: String = bufferedReader.use { reader: BufferedReader -> reader.readText() }
                val type: Type = object : TypeToken<List<Heritage>>() {}.type
                return@withContext Gson().fromJson<List<Heritage>>(data, type)
            } ?: run { emptyList<Heritage>() }
        }
}