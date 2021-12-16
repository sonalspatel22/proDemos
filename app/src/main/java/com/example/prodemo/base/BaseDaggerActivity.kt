package com.example.prodemo.base

import android.app.ActivityManager
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.prodemo.R
import com.example.prodemo.ui.viewmodel.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


abstract class BaseDaggerActivity : DaggerAppCompatActivity() {

    companion object {
        var isAppInForeground = false
    }

    private var alertDialog: AlertDialog? = null
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

//    fun showProgress(message: String) {
//        val view = LayoutInflater.from(this).inflate(R.layout.layout_progress, null)
////        view.get.text = message
//        val alertDialogBuilder = AlertDialog.Builder(this).setView(view).setCancelable(true)
//        alertDialog = alertDialogBuilder.show()
//    }

    fun hideProgress() {
        alertDialog?.dismiss()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showToast(message: String) {
        findViewById<View>(android.R.id.content).makeSnack(message)
    }

    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }



    fun askPermission(permission: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permission, requestCode)
    }

    internal inner class ForegroundCheckTask : AsyncTask<Context, Void, Boolean>() {

        override fun doInBackground(vararg params: Context): Boolean {
            val context = params[0].applicationContext
            return isAppOnForeground(context)
        }

        private fun isAppOnForeground(context: Context): Boolean {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val appProcesses = activityManager.runningAppProcesses ?: return false
            val myKM = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if (!myKM.inKeyguardRestrictedInputMode()) {
                val packageName = context.packageName
                for (appProcess in appProcesses) {
                    if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName == packageName) {
                        return true
                    }
                }
            }
            return false
        }

        override fun onPostExecute(aBoolean: Boolean) {
            super.onPostExecute(aBoolean)
            isAppInForeground = aBoolean
        }
    }

    open fun onForeground() {}



//    open fun getDialogProgressBar(message: String): AlertDialog {
//        val alert = AlertDialog.Builder(this).setView(R.layout.layout_progress).create()
//        alert.findViewById<TextView>(R.id.progressTitle)?.text = message
//        return alert
//    }


}