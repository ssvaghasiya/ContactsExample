package com.task.appinessdemo.retrofit

import com.task.appinessdemo.models.ContactData
import retrofit2.Response
import retrofit2.http.GET

interface APIInterface {

    @GET("getMyList")
    suspend fun getContacts(): Response<ContactData>
}