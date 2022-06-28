package com.Evelyndamayantiambarita.challengechapter5.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Evelyndamayantiambarita.challengechapter5.model.movie.DataMovieDetail
import com.Evelyndamayantiambarita.challengechapter5.network.TMDBApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel : ViewModel() {
    val shouldShowMovieDetail: MutableLiveData<DataMovieDetail> = MutableLiveData()

    fun getMovieDetail(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                TMDBApiClient.instanceTMDB.getMovieDetails(
                    id.toInt(),
                    "0fbaf8c27d542bc99bfc67fb877e3906"
                )
            withContext(Dispatchers.IO) {
                if (response.isSuccessful) {
                    // transformasi atau mapping dari response ke model
                    val movieDetailsResponse = response.body()
                    val data = DataMovieDetail(
                        id = movieDetailsResponse!!.id,
                        image = "https://image.tmdb.org/t/p/w500"+movieDetailsResponse.poster_path,
                        title = movieDetailsResponse.title,
                        vote_average = movieDetailsResponse.vote_average,
                        overview = movieDetailsResponse.overview
                    )
                    shouldShowMovieDetail.postValue(data)
                }
            }
        }
    }
}