package com.example.socketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socketapp.databinding.ActivityMainBinding
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var socket: Socket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        socket = SocketApplication.get()
        socket.connect()

        socket.emit("send message","")
        socket.on("get message",onMessage)
    }
    var onMessage = Emitter.Listener { args ->
        val obj = JSONObject(args[0].toString())
        var text: String
        Thread(object : Runnable{
            override fun run() {
                runOnUiThread(Runnable {
                    kotlin.run {
                        text = "" + obj.get("name") + ": " + obj.get("message")
                    }
                })
            }
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off("get message", onMessage)
    }
}