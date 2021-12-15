package com.xayappz.coroutinesx

import retrofit2.http.GET

interface MyApi {
    @GET("/comments")
    fun getComments(): retrofit2.Call<List<Comment>>

    //   we can replace fun getComments(): retrofit2.Call<List<Comment>> -> fun getComments():Response<List<Comment>>

}