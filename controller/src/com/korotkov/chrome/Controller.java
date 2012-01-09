package com.korotkov.chrome;

import net.tootallnate.websocket.WebSocket;
import net.tootallnate.websocket.WebSocketServer;

import java.io.IOException;

public class Controller extends WebSocketServer {
    public Controller(int port) {
        super(port);
    }

    @Override
    public void onClientOpen(WebSocket webSocket) {
        System.out.println("open: " + webSocket.toString());
    }

    @Override
    public void onClientClose(WebSocket webSocket) {
        System.out.println("close: " + webSocket.toString());
    }

    @Override
    public void onClientMessage(WebSocket webSocket, String s) {
        System.out.println("msg: " + s);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    public void sendUrl(String msg) throws IOException {
        sendToAll('0' + msg);
    }
}
