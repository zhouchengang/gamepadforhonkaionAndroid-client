package com.shoubinforhonkai.my

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.kongqw.rockerlibrary.view.RockerView
import com.kongqw.rockerlibrary.view.RockerView.OnShakeListener
import kotlinx.android.synthetic.main.activity_shoub.*
import org.json.JSONObject

class ShoubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_shoub)


        listenwhich(1,butt1)
        listenwhich(2,butt2)
        listenwhich(3,butt3)
        listenwhich(4,butt4)
        listenwhich(5,butt5)
        listenwhich(6,butt6)
        listenwhich(7,butt7)
        listenwhich(8,butt8)
        listenwhich(9,butt9)
        listenwhich(10,butt10)
        listenwhich(11,butt11)


        rockerView.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE)
        rockerView.setOnShakeListener(
            RockerView.DirectionMode.DIRECTION_8,
            object : OnShakeListener {
                override fun onStart() {
                }
                override fun direction(direction: RockerView.Direction) {
                    var direc :Int =getDirection(direction)
                    logtext2.setText("摇动方向 : " + direc);
                    object : Thread() {
                        override fun run() {
                            MainActivity.jWebSClientService.sendMsg(""+(200+direc))
                        }
                    }.start()
                }
                override fun onFinish() {
                    logtext2.setText("摇动结束" );
                    object : Thread() {
                        override fun run() {
                            MainActivity.jWebSClientService.sendMsg(""+200)
                        }
                    }.start()
                }
            })






        rockerView2.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE)
        rockerView2.setOnShakeListener(
            RockerView.DirectionMode.DIRECTION_8,
            object : OnShakeListener {
                override fun onStart() {
                }
                override fun direction(direction: RockerView.Direction) {
                    var direc :Int =getDirection(direction)
                    logtext2.setText("摇动方向 : " + direc);
                    object : Thread() {
                        override fun run() {
                            MainActivity.jWebSClientService.sendMsg(""+(300+direc))
                        }
                    }.start()
                }
                override fun onFinish() {
                    logtext2.setText("摇动结束" );
                    object : Thread() {
                        override fun run() {
                            MainActivity.jWebSClientService.sendMsg(""+300)
                        }
                    }.start()
                }
            })


    }

    fun listenwhich(num:Int,butt:MaterialButton){
        butt.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    logtext.setText("butt"+num+":"+"down")
                    object : Thread() {
                        override fun run() {
                            MainActivity.jWebSClientService.sendMsg(""+num)
                        }
                    }.start()
                }
                MotionEvent.ACTION_UP -> {
                    logtext.setText("butt"+num+":"+"up")
                    object : Thread() {
                        override fun run() {
                            MainActivity.jWebSClientService.sendMsg(""+(100+num))
                        }
                    }.start()
                }
            }
            true
        })
    }


    private fun getDirection(direction: RockerView.Direction): Int {
        var message: Int =0
        when (direction) {
            RockerView.Direction.DIRECTION_LEFT -> message = 7
            RockerView.Direction.DIRECTION_RIGHT -> message = 3
            RockerView.Direction.DIRECTION_UP -> message = 1
            RockerView.Direction.DIRECTION_DOWN -> message = 5
            RockerView.Direction.DIRECTION_UP_LEFT -> message = 8
            RockerView.Direction.DIRECTION_UP_RIGHT -> message = 2
            RockerView.Direction.DIRECTION_DOWN_LEFT -> message = 6
            RockerView.Direction.DIRECTION_DOWN_RIGHT -> message = 4
            else -> {
            }
        }
        return message
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        super.onResume()
    }
    companion object {
        fun createIntent(context: Context): Intent = Intent(context, ShoubActivity::class.java)
    }
}