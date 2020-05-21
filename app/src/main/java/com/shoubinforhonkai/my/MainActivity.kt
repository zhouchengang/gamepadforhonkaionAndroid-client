package com.shoubinforhonkai.my

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var client: JWebSocketClient
    private lateinit var binder: JWebSocketClientService.JWebSocketClientBinder
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            //Log.e("MainActivity", "服务与活动成功绑定")
            binder = iBinder as JWebSocketClientService.JWebSocketClientBinder
            jWebSClientService = binder.getService()
            client = jWebSClientService.client
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            //Log.e("MainActivity", "服务与活动成功断开")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this@MainActivity
        //启动服务
        startJWebSClientService()
        //绑定服务
        bindService()
        //bug留存，旋转屏幕会重新连接

        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        buttone.setOnClickListener {
            Log.v("Main","Buttone clicked");
            startActivity(ShoubActivity.createIntent(this))
        }


    }
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {

        //if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        super.onResume()
    }

    /**
     * 启动服务（websocket客户端服务）
     */
    private fun startJWebSClientService() {
        var intent = Intent(mContext, JWebSocketClientService::class.java)
        startService(intent)
    }
    /**
     * 绑定服务
     */
    private fun bindService() {
        val bindIntent = Intent(mContext, JWebSocketClientService::class.java)
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE)
    }
    companion object {
        lateinit var mContext: Context
        lateinit var jWebSClientService :JWebSocketClientService
    }
}

