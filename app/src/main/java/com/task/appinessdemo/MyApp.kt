package com.task.appinessdemo

import android.app.Application
import com.task.appinessdemo.di.ApplicationComponent
import com.task.appinessdemo.di.DaggerApplicationComponent

class MyApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}