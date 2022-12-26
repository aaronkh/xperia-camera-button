package xyz.aaronkh.xperiahelper

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import xyz.aaronkh.xperiahelper.CameraHelperForegroundService.Companion.START_ACTION
import xyz.aaronkh.xperiahelper.CameraHelperForegroundService.Companion.STOP_ACTION

class CameraAccessibilityService: AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if(event.eventType != 8) return
        val intent = Intent(this, CameraHelperForegroundService::class.java)
        if(isLockButtonShown(rootInActiveWindow)) {
            intent.action = START_ACTION
        } else {
            intent.action = STOP_ACTION
        }
        startForegroundService(intent)
    }

    override fun onInterrupt() { }

    fun isLockButtonShown(node: AccessibilityNodeInfo?): Boolean {
        if(node == null) return false
        for(i in 0 until node.childCount) {
            if(node.getChild(i)?.text.toString() == "Lock") {
                return true
            }
        }
        return false
    }
}