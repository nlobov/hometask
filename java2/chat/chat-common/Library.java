package ru.geekbrains.java2.chat.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Library {
    /*
    * /auth_request§login§password
    * /auth_accept§nickname
    * /auth_error
    * /msg_format_error
    * /broadcast§msg
    * */
    public static final String DELIMITER = "§";
    public static final String AUTH_REQUEST = "/auth_request";
    public static final String AUTH_ACCEPT = "/auth_accept";
    public static final String AUTH_DENIED = "/auth_denied";
    public static final String MSG_FORMAT_ERROR = "/msg_format_error";
    // если мы вдруг не поняли, что за сообщение и не смогли разобрать
    public static final String TYPE_BROADCAST = "/bcast";
    // то есть сообщение, которое будет посылаться всем

    public static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss: ");

    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DELIMITER + login + DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + DELIMITER + nickname;
    }

    public static String getAuthDenied() {
        return dateFormat.format(System.currentTimeMillis())+ AUTH_DENIED;
    }

    public static String getMsgFormatError(String message) {
        return dateFormat.format(System.currentTimeMillis())+ MSG_FORMAT_ERROR + DELIMITER + message;
    }

    public static String getTypeBroadcast(String src, String message) {
        return dateFormat.format(System.currentTimeMillis())+ TYPE_BROADCAST + DELIMITER
                 + src + DELIMITER + message;
    }
    public static String getMsgFormat(String nickname, String message) {
        return dateFormat.format(System.currentTimeMillis())+ TYPE_BROADCAST +  DELIMITER + nickname + ": "  + message;
    }
}
