package com.example.dota2.domain.useCase.author

import com.example.dota2.domain.model.server.*
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.AuthorContentType
import com.example.dota2.domain.state.toApiValue
import javax.inject.Inject



class AuthorsUseCases @Inject constructor(
    private val repository: MangaRepository
){

    suspend operator fun invoke(
        page:Int = 0,
        searchQuery: String? = null,
        sortDescending: Boolean = false,
        contentType: AuthorContentType
    ): Result<AuthorListResponceModel> {

        val offset = page * 10

        val order = if(sortDescending) "desc" else "asc"

        return repository.getAuthors(
            limit = 10,
            offset = offset,
            name = searchQuery,
            order = order,
            includes =  listOf(contentType.toApiValue())
        )
    }
}


