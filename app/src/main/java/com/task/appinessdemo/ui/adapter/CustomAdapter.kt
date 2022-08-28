package com.task.appinessdemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.appinessdemo.R
import com.task.appinessdemo.databinding.LayoutDataBinding
import com.task.appinessdemo.models.ContactData
import com.task.appinessdemo.ui.listener.EventListener

class CustomAdapter(
    var data: ArrayList<ContactData.Heirarchy>,
    var mEventListener: EventListener<ContactData.Heirarchy>
) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = DataBindingUtil.inflate<LayoutDataBinding>(
            inflater,
            R.layout.layout_data, parent, false
        )
        return MyViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    private fun getItem(p: Int): ContactData.Heirarchy {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = with(holder.itemBinding) {
        val item = getItem(position)
        item.apply {
            textViewName.text = contactName
            textViewDesignation.text = designationName
        }
        imageViewMessage.setOnClickListener {
            mEventListener.onItemClick(position, item, it)
        }
        imageViewCall.setOnClickListener {
            mEventListener.onItemClick(position, item, it)
        }
        root.setOnClickListener {
            mEventListener.onItemClick(position, item, it)
        }
    }

    inner class MyViewHolder(internal var itemBinding: LayoutDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

}