package xyz.aaronkh.xperiahelper

import android.content.Context
import android.os.SystemClock
import android.hardware.input.IInputManager
import android.view.KeyEvent
import rikka.shizuku.Shizuku
import rikka.shizuku.ShizukuBinderWrapper
import rikka.shizuku.SystemServiceHelper


class ShizukuUtils {

    companion object {
        fun requestPermissions() {
            Shizuku.requestPermission(151)
        }

        private val iInputManager: IInputManager by lazy {
            val binder =
                ShizukuBinderWrapper(SystemServiceHelper.getSystemService(Context.INPUT_SERVICE))
            IInputManager.Stub.asInterface(binder)
        }

        fun injectEvent(keyCode: Int) {
            val eventTime = SystemClock.uptimeMillis()

            val keyEvent = KeyEvent(
                eventTime,
                eventTime,
                KeyEvent.ACTION_DOWN,
                keyCode,
                0,
                0,
                0,
                0
            )

            iInputManager.injectInputEvent(keyEvent, 0)
                val upEvent = KeyEvent.changeAction(keyEvent, KeyEvent.ACTION_UP)
                iInputManager.injectInputEvent(upEvent, 0)
            }
    }

}