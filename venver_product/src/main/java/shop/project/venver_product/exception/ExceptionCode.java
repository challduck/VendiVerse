package shop.project.venver_product.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionCode {


    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "PRODUCT_01", "상품 정보를 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT_STOCK(HttpStatus.NOT_FOUND, "PRODUCT_02", "상품 재고 정보를 찾을 수 없습니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_03", "상품 재고가 부족합니다.");

    private HttpStatus status;
    private String code;
    private String message;

    ExceptionCode(HttpStatus status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
