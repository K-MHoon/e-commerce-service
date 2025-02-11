package com.kmhoon.app.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class CardAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String errMsgKey;
    private final String errorCode;

    public CardAlreadyExistsException(ErrorCode code) {
        super(code.getErrMsgKey());
        this.errMsgKey = code.getErrMsgKey();
        this.errorCode = code.getErrCode();
    }

    public CardAlreadyExistsException(final String message) {
        super(message);
        this.errMsgKey = ErrorCode.CARD_ALREADY_EXISTS.getErrMsgKey();
        this.errorCode = ErrorCode.CARD_ALREADY_EXISTS.getErrCode();
    }
}
