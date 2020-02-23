package com.neovisionaries.ws.client;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class HandshakeReader {
    private static final String ACCEPT_MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private final WebSocket mWebSocket;

    public HandshakeReader(WebSocket websocket) {
        this.mWebSocket = websocket;
    }

    public Map<String, List<String>> readHandshake(WebSocketInputStream input, String key) throws WebSocketException {
        StatusLine statusLine = readStatusLine(input);
        Map<String, List<String>> headers = readHttpHeaders(input);
        validateStatusLine(statusLine, headers, input);
        validateUpgrade(statusLine, headers);
        validateConnection(statusLine, headers);
        validateAccept(statusLine, headers, key);
        validateExtensions(statusLine, headers);
        validateProtocol(statusLine, headers);
        return headers;
    }

    private StatusLine readStatusLine(WebSocketInputStream input) throws WebSocketException {
        try {
            String line = input.readLine();
            if (line == null || line.length() == 0) {
                throw new WebSocketException(WebSocketError.STATUS_LINE_EMPTY, "The status line of the opening handshake response is empty.");
            }
            try {
                return new StatusLine(line);
            } catch (Exception e) {
                throw new WebSocketException(WebSocketError.STATUS_LINE_BAD_FORMAT, "The status line of the opening handshake response is badly formatted. The status line is: " + line);
            }
        } catch (IOException e2) {
            throw new WebSocketException(WebSocketError.OPENING_HANDSHAKE_RESPONSE_FAILURE, "Failed to read an opening handshake response from the server: " + e2.getMessage(), e2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0016  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Map<java.lang.String, java.util.List<java.lang.String>> readHttpHeaders(com.neovisionaries.ws.client.WebSocketInputStream r10) throws com.neovisionaries.ws.client.WebSocketException {
        /*
            r9 = this;
            java.util.TreeMap r3 = new java.util.TreeMap
            java.util.Comparator r5 = java.lang.String.CASE_INSENSITIVE_ORDER
            r3.<init>(r5)
            r0 = 0
        L_0x0008:
            java.lang.String r4 = r10.readLine()     // Catch:{ IOException -> 0x001e }
            if (r4 == 0) goto L_0x0014
            int r5 = r4.length()
            if (r5 != 0) goto L_0x003e
        L_0x0014:
            if (r0 == 0) goto L_0x001d
            java.lang.String r5 = r0.toString()
            r9.parseHttpHeader(r3, r5)
        L_0x001d:
            return r3
        L_0x001e:
            r2 = move-exception
            com.neovisionaries.ws.client.WebSocketException r5 = new com.neovisionaries.ws.client.WebSocketException
            com.neovisionaries.ws.client.WebSocketError r6 = com.neovisionaries.ws.client.WebSocketError.HTTP_HEADER_FAILURE
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "An error occurred while HTTP header section was being read: "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r2.getMessage()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r5.<init>(r6, r7, r2)
            throw r5
        L_0x003e:
            r5 = 0
            char r1 = r4.charAt(r5)
            r5 = 32
            if (r1 == r5) goto L_0x004b
            r5 = 9
            if (r1 != r5) goto L_0x0059
        L_0x004b:
            if (r0 == 0) goto L_0x0008
            java.lang.String r5 = "^[ \t]+"
            java.lang.String r6 = " "
            java.lang.String r4 = r4.replaceAll(r5, r6)
            r0.append(r4)
            goto L_0x0008
        L_0x0059:
            if (r0 == 0) goto L_0x0062
            java.lang.String r5 = r0.toString()
            r9.parseHttpHeader(r3, r5)
        L_0x0062:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r4)
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.neovisionaries.ws.client.HandshakeReader.readHttpHeaders(com.neovisionaries.ws.client.WebSocketInputStream):java.util.Map");
    }

    private void parseHttpHeader(Map<String, List<String>> headers, String header) {
        String[] pair = header.split(":", 2);
        if (pair.length >= 2) {
            String name = pair[0].trim();
            String value = pair[1].trim();
            List<String> list = headers.get(name);
            if (list == null) {
                list = new ArrayList<>();
                headers.put(name, list);
            }
            list.add(value);
        }
    }

    private void validateStatusLine(StatusLine statusLine, Map<String, List<String>> headers, WebSocketInputStream input) throws WebSocketException {
        if (statusLine.getStatusCode() != 101) {
            throw new OpeningHandshakeException(WebSocketError.NOT_SWITCHING_PROTOCOLS, "The status code of the opening handshake response is not '101 Switching Protocols'. The status line is: " + statusLine, statusLine, headers, readBody(headers, input));
        }
    }

    private byte[] readBody(Map<String, List<String>> headers, WebSocketInputStream input) {
        int length = getContentLength(headers);
        if (length <= 0) {
            return null;
        }
        try {
            byte[] body = new byte[length];
            input.readBytes(body, length);
            return body;
        } catch (Throwable th) {
            return null;
        }
    }

    private int getContentLength(Map<String, List<String>> headers) {
        try {
            return Integer.parseInt((String) headers.get("Content-Length").get(0));
        } catch (Exception e) {
            return -1;
        }
    }

    private void validateUpgrade(StatusLine statusLine, Map<String, List<String>> headers) throws WebSocketException {
        List<String> values = headers.get("Upgrade");
        if (values == null || values.size() == 0) {
            throw new OpeningHandshakeException(WebSocketError.NO_UPGRADE_HEADER, "The opening handshake response does not contain 'Upgrade' header.", statusLine, headers);
        }
        for (String value : values) {
            String[] elements = value.split("\\s*,\\s*");
            int length = elements.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    if (!"websocket".equalsIgnoreCase(elements[i])) {
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
        throw new OpeningHandshakeException(WebSocketError.NO_WEBSOCKET_IN_UPGRADE_HEADER, "'websocket' was not found in 'Upgrade' header.", statusLine, headers);
    }

    private void validateConnection(StatusLine statusLine, Map<String, List<String>> headers) throws WebSocketException {
        List<String> values = headers.get("Connection");
        if (values == null || values.size() == 0) {
            throw new OpeningHandshakeException(WebSocketError.NO_CONNECTION_HEADER, "The opening handshake response does not contain 'Connection' header.", statusLine, headers);
        }
        for (String value : values) {
            String[] elements = value.split("\\s*,\\s*");
            int length = elements.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    if (!"Upgrade".equalsIgnoreCase(elements[i])) {
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
        throw new OpeningHandshakeException(WebSocketError.NO_UPGRADE_IN_CONNECTION_HEADER, "'Upgrade' was not found in 'Connection' header.", statusLine, headers);
    }

    private void validateAccept(StatusLine statusLine, Map<String, List<String>> headers, String key) throws WebSocketException {
        List<String> values = headers.get("Sec-WebSocket-Accept");
        if (values == null) {
            throw new OpeningHandshakeException(WebSocketError.NO_SEC_WEBSOCKET_ACCEPT_HEADER, "The opening handshake response does not contain 'Sec-WebSocket-Accept' header.", statusLine, headers);
        }
        try {
            if (!Base64.encode(MessageDigest.getInstance("SHA-1").digest(Misc.getBytesUTF8(key + ACCEPT_MAGIC))).equals(values.get(0))) {
                throw new OpeningHandshakeException(WebSocketError.UNEXPECTED_SEC_WEBSOCKET_ACCEPT_HEADER, "The value of 'Sec-WebSocket-Accept' header is different from the expected one.", statusLine, headers);
            }
        } catch (Exception e) {
        }
    }

    private void validateExtensions(StatusLine statusLine, Map<String, List<String>> headers) throws WebSocketException {
        List<String> values = headers.get("Sec-WebSocket-Extensions");
        if (values != null && values.size() != 0) {
            List<WebSocketExtension> extensions = new ArrayList<>();
            for (String value : values) {
                String[] elements = value.split("\\s*,\\s*");
                int length = elements.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        String element = elements[i];
                        WebSocketExtension extension = WebSocketExtension.parse(element);
                        if (extension == null) {
                            throw new OpeningHandshakeException(WebSocketError.EXTENSION_PARSE_ERROR, "The value in 'Sec-WebSocket-Extensions' failed to be parsed: " + element, statusLine, headers);
                        }
                        String name = extension.getName();
                        if (!this.mWebSocket.getHandshakeBuilder().containsExtension(name)) {
                            throw new OpeningHandshakeException(WebSocketError.UNSUPPORTED_EXTENSION, "The extension contained in the Sec-WebSocket-Extensions header is not supported: " + name, statusLine, headers);
                        }
                        extension.validate();
                        extensions.add(extension);
                        i++;
                    }
                }
            }
            validateExtensionCombination(statusLine, headers, extensions);
            this.mWebSocket.setAgreedExtensions(extensions);
        }
    }

    private void validateExtensionCombination(StatusLine statusLine, Map<String, List<String>> headers, List<WebSocketExtension> extensions) throws WebSocketException {
        WebSocketExtension pmce = null;
        for (WebSocketExtension extension : extensions) {
            if (extension instanceof PerMessageCompressionExtension) {
                if (pmce == null) {
                    pmce = extension;
                } else {
                    throw new OpeningHandshakeException(WebSocketError.EXTENSIONS_CONFLICT, String.format("'%s' extension and '%s' extension conflict with each other.", new Object[]{pmce.getName(), extension.getName()}), statusLine, headers);
                }
            }
        }
    }

    private void validateProtocol(StatusLine statusLine, Map<String, List<String>> headers) throws WebSocketException {
        String protocol;
        List<String> values = headers.get("Sec-WebSocket-Protocol");
        if (values != null && (protocol = values.get(0)) != null && protocol.length() != 0) {
            if (!this.mWebSocket.getHandshakeBuilder().containsProtocol(protocol)) {
                throw new OpeningHandshakeException(WebSocketError.UNSUPPORTED_PROTOCOL, "The protocol contained in the Sec-WebSocket-Protocol header is not supported: " + protocol, statusLine, headers);
            }
            this.mWebSocket.setAgreedProtocol(protocol);
        }
    }
}
