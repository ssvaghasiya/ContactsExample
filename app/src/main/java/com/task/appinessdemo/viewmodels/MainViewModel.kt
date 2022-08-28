package com.task.appinessdemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.appinessdemo.models.ContactData
import com.task.appinessdemo.repository.ContactRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: ContactRepository,
) : ViewModel() {

    val contactsLiveData: LiveData<ContactData?>
        get() = repository.contacts

    fun callApi() {
        viewModelScope.launch {
            repository.getContacts()
        }
    }

}

