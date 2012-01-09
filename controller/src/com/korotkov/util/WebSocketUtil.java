package com.korotkov.util;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class WebSocketUtil {
    public static byte[] wrapMessage(byte[] msg) {
        final byte[] result = new byte[msg.length + 2];
        result[0] = (byte) 0x00;
        System.arraycopy(msg, 0, result, 1, msg.length);
        result[result.length - 1] = (byte) 0xFF;
        return result;
    }

    public static byte[] unwrapMessage(byte[] msg) {
        final byte[] result = new byte[msg.length - 2];
        System.arraycopy(msg, 1, result, 0, result.length);
        return result;
    }

    public static byte[] getHandShakeResponse(List<String> response) {
        final StringBuilder result = new StringBuilder();
        result.append("HTTP/1.1 101 Switching Protocols");
        result.append("\r\n");
        result.append("Upgrade: websocket");
        result.append("\r\n");
        result.append("Connection: Upgrade");
        result.append("\r\n");
        for (String s : response) {
            if (!s.startsWith("Sec-WebSocket-Key:")) {
                continue;
            }
            final String key = s.substring("Sec-WebSocket-Key: ".length());
            result.append("Sec-WebSocket-Accept: ");
            try {
                result.append(getAccept(key));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (EncoderException e) {
                e.printStackTrace();
            }
            result.append("\r\n");
        }
        result.append("Sec-WebSocket-Protocol: chat");
        result.append("\r\n");
        result.append("\r\n");
        try {
            return result.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static String getAccept(String key) throws NoSuchAlgorithmException, EncoderException {
        key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        byte[] bytes = key.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(bytes);
        bytes = md.digest();
        final Base64 base64 = new Base64();
        bytes = base64.encode(bytes);
        return new String(bytes);
    }
}
