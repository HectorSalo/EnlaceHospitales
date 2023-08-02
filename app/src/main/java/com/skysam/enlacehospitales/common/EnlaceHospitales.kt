package com.skysam.enlacehospitales.common

import android.app.Application
import android.content.Context
import androidx.lifecycle.asLiveData
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.skysam.enlacehospitales.dataClasses.Member

class EnlaceHospitales: Application() {
    companion object {
        private lateinit var mRequestQueue: RequestQueue
        lateinit var appContext: Context
        private var currentMember: Member? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Preferences.getNotificationStatus().asLiveData().observeForever {
            /*if (it) Notifications.subscribeToNotifications()
            else Notifications.unsubscribeToNotifications()*/
        }
    }

    object EnlaceHospitales {
        fun getContext(): Context = appContext

        private fun getmRequestQueue(): RequestQueue {
            mRequestQueue = Volley.newRequestQueue(appContext)
            return mRequestQueue
        }

        fun <T> addToReqQueue(request: Request<T>) {
            request.retryPolicy =
                DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            getmRequestQueue().add(request)
        }

        fun setCurrentUser(member: Member) {
            currentMember = member
        }

        fun getCurrentUser(): Member = currentMember!!
    }
}