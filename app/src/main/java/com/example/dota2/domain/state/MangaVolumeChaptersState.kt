package com.example.dota2.domain.state

import android.icu.text.CaseMap
import com.example.dota2.domain.model.server.ChapterModel

data class MangaVolumeChaptersState(
    val chapter: String? = null,
    val title: String? = null,
    val translations: List<ChapterModel> = emptyList(),
)
