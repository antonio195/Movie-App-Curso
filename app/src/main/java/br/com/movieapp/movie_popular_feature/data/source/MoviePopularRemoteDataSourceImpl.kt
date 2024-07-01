package br.com.movieapp.movie_popular_feature.data.source

import br.com.movieapp.framework.data.remote.MovieService
import br.com.movieapp.framework.data.remote.response.MovieResponse
import br.com.movieapp.framework.paging.MoviePagingSource
import br.com.movieapp.movie_popular_feature.domain.source.MoviePopularRemoteDataSource

class MoviePopularRemoteDataSourceImpl constructor(
    private val service: MovieService
) : MoviePopularRemoteDataSource {

    override fun getPopularMoviesPagingSource(): MoviePagingSource {
        return MoviePagingSource(this)
    }

    override suspend fun getPopularMovies(page: Int): MovieResponse {
        return service.getPopularMovies(page)
    }
}