package com.korotkov.chrome;

import com.korotkov.util.WebSocketUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Controller implements Runnable {
    private final BufferedReader in;
    private final OutputStream out;

    public Controller(InputStream inputStream, OutputStream outputStream) {
        in = new BufferedReader(new InputStreamReader(inputStream));
        out = outputStream;
    }

    public static Controller accept(final int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);
        final Socket socket = serverSocket.accept();
        return new Controller(socket.getInputStream(), socket.getOutputStream());
    }

    public void run() {
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process() throws IOException {
        String input;
        while ((input = in.readLine()) != null) {
            System.out.println(input);
        }
    }

    public void sendUrl(String msg) {
        send('0' + msg);
    }

    public void send(String msg) {
        final byte[] data;
        try {
            data = WebSocketUtil.wrapMessage(msg.getBytes("UTF-8"));
            out.write(data);
            out.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handShake() {
        try {
            final List<String> response = new ArrayList<String>();
            String input;
            while ((input = in.readLine()) != null) {
                response.add(input);
            }
            out.write(WebSocketUtil.getHandShakeResponse(response));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
