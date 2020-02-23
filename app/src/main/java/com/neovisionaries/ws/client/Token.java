package com.neovisionaries.ws.client;

import com.tencent.smtt.sdk.TbsListener;

class Token {
    Token() {
    }

    public static boolean isValid(String token) {
        if (token == null || token.length() == 0) {
            return false;
        }
        int len = token.length();
        for (int i = 0; i < len; i++) {
            if (isSeparator(token.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSeparator(char ch) {
        switch (ch) {
            case 9:
            case ' ':
            case '\"':
            case '(':
            case ')':
            case ',':
            case '/':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case '[':
            case '\\':
            case ']':
            case TbsListener.ErrorCode.DOWNLOAD_RETRYTIMES302_EXCEED /*123*/:
            case TbsListener.ErrorCode.DOWNLOAD_THROWABLE /*125*/:
                return true;
            default:
                return false;
        }
    }

    public static String unquote(String text) {
        if (text == null) {
            return null;
        }
        int len = text.length();
        return (len >= 2 && text.charAt(0) == '\"' && text.charAt(len + -1) == '\"') ? unescape(text.substring(1, len - 1)) : text;
    }

    public static String unescape(String text) {
        if (text == null) {
            return null;
        }
        if (text.indexOf(92) < 0) {
            return text;
        }
        int len = text.length();
        boolean escaped = false;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char ch = text.charAt(i);
            if (ch != '\\' || escaped) {
                escaped = false;
                builder.append(ch);
            } else {
                escaped = true;
            }
        }
        return builder.toString();
    }
}
