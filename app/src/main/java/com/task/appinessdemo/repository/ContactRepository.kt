package com.task.appinessdemo.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.appinessdemo.models.ContactData
import com.task.appinessdemo.retrofit.APIInterface
import com.task.appinessdemo.utils.NetworkResult
import com.task.appinessdemo.utils.SafeApiCall
import retrofit2.Response
import javax.inject.Inject

class ContactRepository @Inject constructor(private val APIInterface: APIInterface) {

    private val _contacts = MutableLiveData<ContactData?>()
    val contacts: LiveData<ContactData?>
        get() = _contacts

    suspend fun getContacts() {
        val result: NetworkResult<Response<ContactData>> = SafeApiCall.safeApiCall {
            APIInterface.getContacts()
        }
        when (result) {
            is NetworkResult.Success -> {
                if (result.data.isSuccessful && result.data.body() != null) {
                    _contacts.postValue(result.data.body())
                }
            }
            is NetworkResult.Error -> { //Handle error
                Log.e("Error", result.message.toString())
                _contacts.postValue(ContactData().apply {
                    statusCode = 0
                    status = false
                    message = result.message.toString()
                })
            }
        }

    }

}