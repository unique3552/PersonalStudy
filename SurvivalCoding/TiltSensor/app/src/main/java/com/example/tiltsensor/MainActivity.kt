package com.example.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var tiltView: TiltView

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            Log.d("MainActivity", "onSensorChanger: "
                    + "x : ${event.values[0]}"
                    + "y : ${event.values[1]}"
                    + "z : ${event.values[2]}")

            tiltView.onSensorEvent(event)
        }
    }

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        // 가로모드로 고정되게 하기
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        tiltView = TiltView(this)
        setContentView(tiltView)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
