package com.example.data

import com.example.data.remote.RemoteApi
import com.example.data.remote.services.MoviesApiService
import org.junit.Before
import org.junit.Test

class RemoteTest {

    private lateinit var remoteApi: RemoteApi
    private lateinit var apiService: MoviesApiService

    @Before
    fun initTest(){
        remoteApi = RemoteApi()
        apiService = remoteApi.createService(MoviesApiService::class.java)
    }

    @Test
    fun testFetchFromRemoteApiHappy(){
        apiService.fetchMovies(1).subscribe { t, e -> assert(t != null && e == null)}
    }

    @Test
    fun testFetchFromRemoteApiFail(){
        apiService.fetchMovies(1000).subscribe { t, e -> assert(t == null && e != null) }
    }
}