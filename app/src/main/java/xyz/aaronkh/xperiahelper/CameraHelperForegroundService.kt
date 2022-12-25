package xyz.aaronkh.xperiahelper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import androidx.core.app.NotificationCompat

class CameraHelperForegroundService: Service() {
    private var isNotificationShown = false
    private var inflatedView: View? = null
    private var windowManager: WindowManager? = null

    override fun onCreate() {
        super.onCreate()
        showNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action == START_ACTION) startService()
        else stopService()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showNotification() {
        if(isNotificationShown) return
        val packageId = "xyz.aaronkh"
        val channel = NotificationChannel(packageId, packageId, NotificationManager.IMPORTANCE_NONE)
        val notificationService = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationService.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, packageId)
            .setOngoing(true)
            .setContentTitle("service running")
            .setContentText("service running")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        isNotificationShown = true
        startForeground(2, notification)
    }

    private fun startService() {
        showNotification()
        if(inflatedView != null) return

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        inflatedView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.camera_target_window, null)

        inflatedView?.setOnClickListener {
            ShizukuUtils.injectEvent(KeyEvent.KEYCODE_VOLUME_UP)
        }

        attachWindow(inflatedView!!)
    }

    private fun stopService() {
        windowManager?.removeView(inflatedView)
        inflatedView?.invalidate()
        inflatedView = null
        isNotificationShown = false
        stopForeground(true)
        stopSelf()
    }

    private fun attachWindow(view: View) {
        val paramFloat = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)
        paramFloat.gravity = Gravity.BOTTOM or Gravity.START
        paramFloat.x = 24
        paramFloat.y = 400

        windowManager!!.addView(view, paramFloat)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        val START_ACTION = "START"
        val STOP_ACTION = "STOP"
    }
}