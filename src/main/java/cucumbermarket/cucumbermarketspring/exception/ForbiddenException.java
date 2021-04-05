package cucumbermarket.cucumbermarketspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "잘못된 접근")
public class ForbiddenException extends RuntimeException {

}
