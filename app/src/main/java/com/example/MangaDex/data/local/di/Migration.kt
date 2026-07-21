package com.example.MangaDex.data.local.di

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * v1 -> v2: `coverArt` (Map<String, String>) was replaced by `coverFileName`
 * (String). Rebuild the table under the new schema instead of dropping it, so
 * existing favorites survive the upgrade; only the old cover art map has no
 * equivalent column and is dropped (coverFileName is refetched from the
 * server afterwards).
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("ALTER TABLE favorite_manga RENAME TO favorite_manga_old")

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

        db.execSQL("""
            INSERT INTO favorite_manga (
                id, userId, userStatus, title, altTitles, coverFileName,
                description, lastVolume, lastChapter, publicationDemographic,
                status, year, contentRating, latestUploadedChapter, tags,
                state, createdAt, updatedAt
            )
            SELECT
                id, userId, userStatus, title, altTitles, NULL,
                description, lastVolume, lastChapter, publicationDemographic,
                status, year, contentRating, latestUploadedChapter, tags,
                state, createdAt, updatedAt
            FROM favorite_manga_old
        """.trimIndent())

        db.execSQL("DROP TABLE favorite_manga_old")
    }
}