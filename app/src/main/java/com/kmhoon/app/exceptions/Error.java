package com.kmhoon.app.exceptions;

import lombok.*;

/**
 * <p>에러 반환 Dto</p>
 * <p>해당 객체 속성들은 다음과 같이 작성한다.</p>
 * 1. errorCode = HTTP 오류 코드와 다른 애플리케이션 오류 코드 <br/>
 * 2. message = 문제에 대한 간단하고 사람이 읽을 수 있는 요약 <br/>
 * 3. status = 문제 발생 시 기존 서버에 의해 설정된 HTTP 상태 코드 <br/>
 * 4. url = 오류를 발생시킨 요청의 URL <br/>
 * 5. reqMethod = 오류를 발생시킨 요청의 메서드
 *
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Error {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String message;
    private Integer status;
    @Builder.Default
    private String url = "이용 불가합니다.";
    @Builder.Default
    private String reqMethod  = "이용 불가합니다.";
}
