package com.task.appinessdemo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.appinessdemo.MyApp
import com.task.appinessdemo.R
import com.task.appinessdemo.databinding.ActivityMainBinding
import com.task.appinessdemo.models.ContactData
import com.task.appinessdemo.ui.adapter.CustomAdapter
import com.task.appinessdemo.ui.listener.EventListener
import com.task.appinessdemo.utils.Utils.setProgressDialog
import com.task.appinessdemo.viewmodels.MainViewModel
import com.task.appinessdemo.viewmodels.MainViewModelFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CustomAdapter
    var contactList = ArrayList<ContactData.Heirarchy>()
    var allContactList = ArrayList<ContactData.Heirarchy>()
    var dialog: AlertDialog? = null

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        (application as MyApp).applicationComponent.inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        dialog = setProgressDialog(this, "Loading..")

        setObserver()
        initView()
    }

    private fun initView() {
        setAdapter()
        handleSearch()

    }

    override fun onResume() {
        super.onResume()
        callApi()
    }

    private fun callApi() {
        dialog?.show()
        mainViewModel.callApi()
    }


    private fun setObserver() {
        mainViewModel.contactsLiveData.observe(this, Observer {
            dialog?.dismiss()
            if (it?.statusCode == 0) {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            } else {
                setContactData(it)
            }
        })
    }

    private fun setContactData(contactData: ContactData?) {
        contactList.clear()
        allContactList.clear()
        contactData?.dataObject?.get(0)?.myHierarchy?.get(0)?.heirarchyList?.let { it1 ->
            contactList.addAll(
                it1
            )
            allContactList.addAll(
                it1
            )
        }

        adapter.notifyDataSetChanged()
    }

    private fun setAdapter() = with(binding) {
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = CustomAdapter(contactList, object : EventListener<ContactData.Heirarchy> {
            override fun onItemClick(pos: Int, item: ContactData.Heirarchy, view: View) {
                when (view.id) {
                    R.id.imageViewCall -> {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${item.contactNumber}")
                        startActivity(intent)
                    }
                    R.id.imageViewMessage -> {
                        val smsUri = Uri.parse("smsto:${item.contactNumber}")
                        val smsIntent = Intent(Intent.ACTION_SENDTO, smsUri)
                        smsIntent.putExtra("sms_body", "Hello")
                        startActivity(smsIntent)
                    }
                }
            }
        })
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun handleSearch() = with(binding) {
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    fun filter(text: String) {
        val temp: MutableList<ContactData.Heirarchy> = ArrayList()
        for (d in allContactList) {
            if (d.contactName?.lowercase()?.contains(text.lowercase()) == true) {
                temp.add(d)
            }
        }
        contactList.clear()
        contactList.addAll(temp)
        adapter.notifyDataSetChanged()
    }
}



















