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
    JSON_PARSE_ERROR(   "SYS-0005", "요청 페이로드가 유효한 JSON 객체이어야 합니다."),
    HTTP_MESSAGE_NOT_READABLE("SYS-0006", "요청 페이로드는 유효한 JSON 또는 XML 객체여야 합니다."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("SYS-0007", "요청한 HTTP 메서드는 지원하지 않습니다."),
    CONSTRAINT_VIOLATION("SYS-0008", "검증에 실패했습니다."),
    ILLEGAL_ARGUMENT_EXCEPTION("SYS-0009", "잘못된 데이터를 요청했습니다."),
    RESOURCE_NOT_FOUND("SYS-0010", "요청한 리소스를 찾을 수 없습니다."),
    CUSTOMER_NOT_FOUND("SYS-0011", "고객을 찾을 수 없습니다."),
    ITEM_NOT_FOUND("SYS-0012", "물품을 찾을 수 없습니다."),
    GENERIC_ALREADY_EXISTS("SYS-0013", "이미 존재합니다."),
    GENERIC_STATUS_ERROR("SYS-0014", "상태 문제가 발생했습니다."),
    CARD_ALREADY_EXISTS("SYS-0015", "카드가 이미 존재합니다."),

    ;

    private final String errCode;
    private final String errMsgKey;
}
