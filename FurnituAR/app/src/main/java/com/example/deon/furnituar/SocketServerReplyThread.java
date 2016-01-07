package com.example.deon.furnituar;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by deon on 07/01/16.
 */
public class SocketServerReplyThread extends Thread {
    private Socket hostThreadSocket;
    private String jsonOutput;

    SocketServerReplyThread(Socket socket, String jsonInput) {
        hostThreadSocket = socket;
        jsonOutput = jsonInput;
    }

    @Override
    public void run() {
        OutputStream outputStream;

        try {
            outputStream = hostThreadSocket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(jsonOutput);
            printStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
