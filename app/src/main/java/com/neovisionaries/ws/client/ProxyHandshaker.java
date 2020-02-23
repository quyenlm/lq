package com.neovisionaries.ws.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

class ProxyHandshaker {
    private static final String RN = "\r\n";
    private final String mHost;
    private final int mPort;
    private final ProxySettings mSettings;
    private final Socket mSocket;

    public ProxyHandshaker(Socket socket, String host, int port, ProxySettings settings) {
        this.mSocket = socket;
        this.mHost = host;
        this.mPort = port;
        this.mSettings = settings;
    }

    public void perform() throws IOException {
        sendRequest();
        receiveResponse();
    }

    private void sendRequest() throws IOException {
        byte[] requestBytes = Misc.getBytesUTF8(buildRequest());
        OutputStream output = this.mSocket.getOutputStream();
        output.write(requestBytes);
        output.flush();
    }

    private String buildRequest() {
        String host = String.format("%s:%d", new Object[]{this.mHost, Integer.valueOf(this.mPort)});
        StringBuilder builder = new StringBuilder().append("CONNECT ").append(host).append(" HTTP/1.1").append(RN).append("Host: ").append(host).append(RN);
        addHeaders(builder);
        addProxyAuthorization(builder);
        return builder.append(RN).toString();
    }

    private void addHeaders(StringBuilder builder) {
        for (Map.Entry<String, List<String>> header : this.mSettings.getHeaders().entrySet()) {
            String name = header.getKey();
            for (String value : header.getValue()) {
                if (value == null) {
                    value = "";
                }
                builder.append(name).append(": ").append(value).append(RN);
            }
        }
    }

    private void addProxyAuthorization(StringBuilder builder) {
        String id = this.mSettings.getId();
        if (id != null && id.length() != 0) {
            String password = this.mSettings.getPassword();
            if (password == null) {
                password = "";
            }
            builder.append("Proxy-Authorization: Basic ").append(Base64.encode(String.format("%s:%s", new Object[]{id, password}))).append(RN);
        }
    }

    private void receiveResponse() throws IOException {
        InputStream input = this.mSocket.getInputStream();
        readStatusLine(input);
        skipHeaders(input);
    }

    private void readStatusLine(InputStream input) throws IOException {
        String statusLine = Misc.readLine(input, "UTF-8");
        if (statusLine == null || statusLine.length() == 0) {
            throw new IOException("The response from the proxy server does not contain a status line.");
        }
        String[] elements = statusLine.split(" +", 3);
        if (elements.length < 2) {
            throw new IOException("The status line in the response from the proxy server is badly formatted. The status line is: " + statusLine);
        } else if (!"200".equals(elements[1])) {
            throw new IOException("The status code in the response from the proxy server is not '200 Connection established'. The status line is: " + statusLine);
        }
    }

    private void skipHeaders(InputStream input) throws IOException {
        int count = 0;
        while (true) {
            int ch = input.read();
            if (ch == -1) {
                throw new EOFException("The end of the stream from the proxy server was reached unexpectedly.");
            } else if (ch == 10) {
                if (count != 0) {
                    count = 0;
                } else {
                    return;
                }
            } else if (ch != 13) {
                count++;
            } else {
                int ch2 = input.read();
                if (ch2 == -1) {
                    throw new EOFException("The end of the stream from the proxy server was reached unexpectedly after a carriage return.");
                } else if (ch2 != 10) {
                    count += 2;
                } else if (count != 0) {
                    count = 0;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public String getProxiedHostname() {
        return this.mHost;
    }
}
