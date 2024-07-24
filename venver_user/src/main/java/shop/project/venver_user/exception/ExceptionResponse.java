package shop.project.venver_user.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionResponse extends RuntimeException {
    private final String message;
    private final String code;
    private final HttpStatus status;

    public ExceptionResponse(ExceptionCode code){
        this(code, null);
    }

    public ExceptionResponse(ExceptionCode codes, Throwable cause){
        super(codes.getMessage(), cause);
        this.message = codes.getMessage();
        this.code = codes.getCode();
        this.status = codes.getStatus();
    }

}
