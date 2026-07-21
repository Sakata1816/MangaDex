package com.example.dota2.data.local.di

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("DROP TABLE IF EXISTS favorite_manga")

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS favorite_manga (
                id TEXT NOT NULL,
                userId TEXT,
                userStatus TEXT NOT NULL,
                title TEXT,
                altTitles TEXT,
                coverFileName TEXT,
                description TEXT,
                lastVolume TEXT,
                lastChapter TEXT,
                publicationDemographic TEXT,
                status TEXT,
                year INTEGER,
                contentRating TEXT,
                latestUploadedChapter TEXT,
                tags TEXT,
                state TEXT,
                createdAt TEXT,
                updatedAt TEXT,
                PRIMARY KEY(id)
            )
        """.trimIndent())
    }
}