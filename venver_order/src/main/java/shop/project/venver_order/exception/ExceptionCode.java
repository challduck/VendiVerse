package shop.project.venver_order.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionCode {

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER_01", "이미 존재하는 이메일입니다."),
    INCORRECT_ID_OR_PASSWORD(HttpStatus.BAD_REQUEST, "USER_02", "아이디 또는 비밀번호가 일치하지 않습니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "USER_03", "이메일을 찾을 수 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER_04", "사용자를 찾을 수 없습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "USER_05", "기존 비밀번호가 일치하지 않습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "USER_06", "회원 정보가 일치하지 않습니다."),

    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "PRODUCT_01", "상품 정보를 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT_STOCK(HttpStatus.NOT_FOUND, "PRODUCT_02", "상품 재고 정보를 찾을 수 없습니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_03", "상품 재고가 부족합니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_01", "유효하지 않은 토큰입니다."),
    NOT_VALID_EMAIL(HttpStatus.BAD_REQUEST, "TOKEN_02", "유효하지 않은 이메일입니다.");

    private HttpStatus status;
    private String code;
    private String message;

    ExceptionCode(HttpStatus status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
