package com.styeeqan.community.common.exception;

import com.styeeqan.community.common.constant.ServerStatusCode;

public class CustomizeException extends RuntimeException {

    private final ServerStatusCode error;

    public CustomizeException(ServerStatusCode error) {
        this.error = error;
    }

    public ServerStatusCode getError() {
        return error;
    }
}
