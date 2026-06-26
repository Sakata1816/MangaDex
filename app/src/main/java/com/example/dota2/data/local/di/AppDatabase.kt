package com.example.dota2.data.local.di

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.dota2.data.local.DAO.FavoriteMangaDao
import com.example.dota2.data.local.entity.FavoriteMangaEntity
import com.example.dota2.data.local.entity.MangaStatus
import com.example.dota2.domain.model.server.TagAttributesModel
import com.example.dota2.domain.model.server.TagModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

@Database(entities = [FavoriteMangaEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMangaDao(): FavoriteMangaDao
}





class Converter {

    private val gson = Gson()


    @TypeConverter
    fun fromStatus(status: MangaStatus?): String {
        return status?.name ?: MangaStatus.NONE.name
    }


    @TypeConverter
    fun toStatus(value: String?): MangaStatus {
        return try {
            MangaStatus.valueOf(value ?: MangaStatus.NONE.name)
        } catch (e: Exception) {
            MangaStatus.NONE // 👈 защита от краша
        }
    }


    @TypeConverter
    fun fromMap(map: Map<String, String>?): String {
        return map?.let { gson.toJson(it) } ?: ""
    }


    @TypeConverter
    fun toMap(value: String?): Map<String, String>? {
        if (value.isNullOrEmpty()) return emptyMap()

        val type = object : TypeToken<Map<String, String>>() {}.type

        return gson.fromJson(value, type)

    }


    @TypeConverter
    fun fromMapAny(map: Map<String, Any>?): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toMapAny(json: String?): Map<String, Any> {
        if (json.isNullOrBlank()) return emptyMap()

        val type = object : TypeToken<Map<String, Any>>() {}.type

        return gson.fromJson(json, type)
    }


    @TypeConverter
    fun fromListMap(list: List<Map<String, String>>?): String {
        return list?.let { gson.toJson(it) } ?: ""
    }


    @TypeConverter
    fun toListMap(value: String?): List<Map<String, String>>? {
        if (value.isNullOrEmpty()) return emptyList()

        val type = object : TypeToken<List<Map<String, String>>>() {}.type

        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return list?.let { gson.toJson(it) } ?: ""
    }


    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value.isNullOrEmpty()) return emptyList()

        val type = object : TypeToken<List<String>>() {}.type

        return gson.fromJson(value, type)
    }


}

fun List<TagModel>.toString(): List<String?> {
    return this.map { it.attributes?.name?.get("en")}
}

fun List<String>.toTagModels(): List<TagModel> {
    return map { name ->
        TagModel(
            id = null,
            type = null,
            attributes = TagAttributesModel(
                name = mapOf("en" to name),
                description = null,
                group = null,
                version = null
            ),
            relationships = null
        )
    }
}
