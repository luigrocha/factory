package org.crsoft.users.exeption;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.users.vo.BaseResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Global Controller Exception Handler
 *
 * @author jyepez
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * Method Argument Not ValidException.
     *
     * @param request   HttpServletRequest
     * @param exception MethodArgumentNotValidException
     * @return ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponseVo> handleMethodArgumentNotValid(
            HttpServletRequest request,
            MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(error.getField().concat(": ").concat(error.getDefaultMessage()));
        }
        log.error("Error handler {}:{}", exception.getMessage(), exception);
        return buildResponse(errors, HttpStatus.BAD_REQUEST);
    }


    /**
     * Method Exception.
     *
     * @param request   ResponseEntity
     * @param exception Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseVo> handleMethodException(
            HttpServletRequest request, Exception exception) {
        log.error("Error handler {}:{}", exception.getMessage(), exception);
        return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Build Response.
     *
     * @param errors List
     * @param status HttpStatus
     * @return ResponseEntity
     */
    private ResponseEntity<BaseResponseVo> buildResponse(List<String> errors,
                                                         HttpStatus status) {
        BaseResponseVo response = BaseResponseVo.builder().errors(errors)
                .code(status.value())
                .message(status.getReasonPhrase()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Method ConstraintViolationException.
     *
     * @param request   HttpServletRequest
     * @param exception MethodArgumentNotValidException
     * @return ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<BaseResponseVo> handleConstraintViolation(
            HttpServletRequest request,
            ConstraintViolationException exception) {
        log.error("Error handler {}:{}", exception.getMessage(), exception);
        return buildResponse(null, HttpStatus.BAD_REQUEST);
    }
}
