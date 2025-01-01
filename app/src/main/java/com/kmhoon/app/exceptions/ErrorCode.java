package com.kmhoon.app.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    GENERIC_ERROR("SYS-0001", "시스템이 요청을 완료할 수 없습니다. 시스템 지원 담당자에게 연락하세요"),
    HTTP_MEDIATYPE_NOT_SUPPORTED("SYS-0002", "요청한 미디어 타입을 지원하지 않습니다."),
    HTTP_MESSAGE_NOT_WRITABLE("SYS-0003", "'Accept' 헤더가 존재하지 않습니다."),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE("SYS-0004", "요청한 'Accept' 헤더 값을 지원하지 않습니다."),
    JSON_PARSE_ERROR("SYS-0005", "요청 페이로드가 유효한 JSON 객체이어야 합니다."),
    HTTP_MESSAGE_NOT_READABLE("SYS-0006", "요청 페이로드는 유효한 JSON 또는 XML 객체여야 합니다.");

    private final String errCode;
    private final String errMsgKey;
}