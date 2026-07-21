package com.example.MangaDex.data.local.di

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Regression test for the data-loss bug: MIGRATION_1_2 used to DROP the
 * favorite_manga table before recreating it, wiping every saved favorite on
 * upgrade. It must now preserve existing rows across the schema change.
 */
@RunWith(AndroidJUnit4::class)
class MigrationTest {

    private val testDbName = "migration-test-db"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java
    )

    @Test
    fun migrate1To2_preservesExistingFavorites() {
        // Arrange: build the v1 schema by hand (coverArt instead of coverFileName)
        // and seed it with a row, the way the app looked before v2 shipped.
        helper.createDatabase(testDbName, 1).apply {
            execSQL(
                """
                CREATE TABLE favorite_manga (
                    id TEXT NOT NULL,
                    userId TEXT,
                    userStatus TEXT NOT NULL,
                    title TEXT,
                    altTitles TEXT,
                    coverArt TEXT,
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
                """.trimIndent()
            )
            insertFavorite(this, id = "manga-1", userId = "user-1", title = "One Piece")
            insertFavorite(this, id = "manga-2", userId = "user-1", title = "Naruto")
            close()
        }

        // Act: run the real migration under test.
        val migrated: SupportSQLiteDatabase =
            helper.runMigrationsAndValidate(testDbName, 2, true, MIGRATION_1_2)

        // Assert: both rows survived the upgrade instead of being wiped.
        migrated.query("SELECT id, title FROM favorite_manga ORDER BY id").use { cursor ->
            assertEquals(2, cursor.count)
            cursor.moveToFirst()
            assertEquals("manga-1", cursor.getString(cursor.getColumnIndexOrThrow("id")))
            assertEquals("One Piece", cursor.getString(cursor.getColumnIndexOrThrow("title")))
            cursor.moveToNext()
            assertEquals("manga-2", cursor.getString(cursor.getColumnIndexOrThrow("id")))
            assertEquals("Naruto", cursor.getString(cursor.getColumnIndexOrThrow("title")))
        }
    }

    private fun insertFavorite(db: SupportSQLiteDatabase, id: String, userId: String, title: String) {
        db.execSQL(
            """
            INSERT INTO favorite_manga (id, userId, userStatus, title, coverArt)
            VALUES (?, ?, 'NONE', ?, '{}')
            """.trimIndent(),
            arrayOf(id, userId, title)
        )
    }
}