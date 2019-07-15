package com.example.flashlight

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TorchService : Service() {
    private val torch: Torch by lazy {
        Torch(this)
    }

    private var isRunning = false       // 위젯에서 실행할 경우

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            // 앱에서 실행할 경우
            "on" -> {
                torch.flashOn()
                isRunning = true    // 위젯에서 실행할 경우
            }
            "off" -> {
                torch.flashOff()
                isRunning = false   // 위젯에서 실행할 경우
            }

            // 위젯에서 실행할 경우
            else -> {
                isRunning = !isRunning
                if(isRunning) {
                    torch.flashOn()
                }
                else {
                    torch.flashOff()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
