package com.task.appinessdemo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ContactData {
    @SerializedName("statusCode")
    @Expose
    var statusCode = 0

    @SerializedName("status")
    @Expose
    var status = false

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("dataObject")
    @Expose
    var dataObject: List<DataObject>? = null

    class DataObject {
        @SerializedName("myHierarchy")
        @Expose
        var myHierarchy: List<MyHierarchy>? = null
    }

    class Heirarchy {
        @SerializedName("contactName")
        @Expose
        var contactName: String? = null

        @SerializedName("contactNumber")
        @Expose
        var contactNumber: String? = null

        @SerializedName("designationName")
        @Expose
        var designationName: String? = null
    }

    class MyHierarchy {
        @SerializedName("heirarchyList")
        @Expose
        var heirarchyList: List<Heirarchy>? = null
    }
}