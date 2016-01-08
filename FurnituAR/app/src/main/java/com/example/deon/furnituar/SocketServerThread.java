package com.example.deon.furnituar;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by deon on 07/01/16.
 */
public class SocketServerThread extends Thread {
    static final int SOCKET_SERVER_PORT = 43770;
    private SampleCamActivity sampleCamActivity;
    private String jsonInput;

    public SocketServerThread(SampleCamActivity callingActivity) {
        sampleCamActivity = callingActivity;
    }
    private void getJSON() {
        jsonInput = sampleCamActivity.getJSON();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(SOCKET_SERVER_PORT);

            while(true) {
                getJSON();
                Socket socket = serverSocket.accept();
                SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(socket, jsonInput);
                socketServerReplyThread.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
