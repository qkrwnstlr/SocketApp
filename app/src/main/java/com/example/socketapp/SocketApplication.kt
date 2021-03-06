package com.example.socketapp

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketApplication {
    companion object {
        private lateinit var socket: Socket
        fun get(): Socket {
            try {
                // [uri]부분은 "http://X.X.X.X:3000" 꼴로 넣어주는 게 좋다.
                socket = IO.socket("[uri]")
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            return socket
        }
    }
}