package com.example.retrofitsimplegetrequest.repository

import com.example.retrofitsimplegetrequest.api.RetrofitInstance
import com.example.retrofitsimplegetrequest.model.Post
import retrofit2.Response

class Repository {

    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getPost()
    }

    suspend fun getPost2(number: Int): Response<Post> {
        return RetrofitInstance.api.getPost2(number)
    }

}