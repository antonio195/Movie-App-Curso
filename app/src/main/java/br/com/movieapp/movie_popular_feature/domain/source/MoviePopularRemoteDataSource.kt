package br.com.movieapp.movie_popular_feature.domain.source

import br.com.movieapp.framework.data.remote.response.MovieResponse
import br.com.movieapp.framework.paging.MoviePagingSource

interface MoviePopularRemoteDataSource {

    fun getPopularMoviesPagingSource(): MoviePagingSource

    suspend fun getPopularMovies(page: Int): MovieResponse
}