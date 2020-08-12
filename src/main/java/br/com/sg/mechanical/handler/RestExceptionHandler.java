package br.com.sg.mechanical.handler;

import br.com.sg.mechanical.domain.Employee;
import br.com.sg.mechanical.error.ErrorDetails;
import br.com.sg.mechanical.error.ResourceNotFoundDetails;
import br.com.sg.mechanical.error.ResourceNotFoundException;
import br.com.sg.mechanical.error.ValidationErrorDetails;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ResourceNotFoundDetails details = ResourceNotFoundDetails.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(exception.getMessage())
                .developerMessage(exception.getClass().getName())
                .build();
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }




    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String messages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        ValidationErrorDetails details = ValidationErrorDetails.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Erro de validação de campo")
                .detail("Erro de validação de campo")
                .developerMessage(exception.getClass().getName())
                .field(fields)
                .fieldMessage(messages)
                .build();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {

        if (exception instanceof HttpMessageNotReadableException) {

            if (exception.getCause() instanceof InvalidFormatException) {
                String pathReference = ((InvalidFormatException) exception.getCause()).getPathReference();
                String s = pathReference.substring(pathReference.indexOf("[")+1, pathReference.indexOf("]"));
                ErrorDetails details = ErrorDetails.Builder.anErrorDetails()
                        .timestamp(new Date().getTime())
                        .status(status.value())
                        .title("Não é possível realizar a ação.")
                        .detail("O campo "+s+" deve possuir um dos seguintes valores: "+ Arrays.toString(Employee.TYPE.values()))
                        .developerMessage(exception.getClass().getName())
                        .build();
                return new ResponseEntity<>(details, headers, status);
            }else {
                ErrorDetails details = ErrorDetails.Builder.anErrorDetails()
                        .timestamp(new Date().getTime())
                        .status(status.value())
                        .title("Não é possível realizar a ação.")
                        .detail(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .build();
                return new ResponseEntity<>(details, headers, status);
            }


        }else if (exception instanceof MethodArgumentTypeMismatchException) {
            ErrorDetails details = ErrorDetails.Builder.anErrorDetails()
                    .timestamp(new Date().getTime())
                    .status(status.value())
                    .title("Não é possível realizar a ação.")
                    .detail("Verifique os parametros informados.")
                    .developerMessage(exception.getClass().getName())
                    .build();
            return new ResponseEntity<>(details, headers, status);
        } else if (exception instanceof IllegalArgumentException) {
        ErrorDetails details = ErrorDetails.Builder.anErrorDetails()
                .timestamp(new Date().getTime())
                .status(status.value())
                .title("Não é possível realizar a ação.")
                .detail("Erro.")
                .developerMessage(exception.getClass().getName())
                .build();
        return new ResponseEntity<>(details, headers, status);
    }else {
            ErrorDetails details = ErrorDetails.Builder.anErrorDetails()
                    .timestamp(new Date().getTime())
                    .status(status.value())
                    .title("Não é possível realizar a ação.")
                    .detail("Verifique a requisição.")
                    .developerMessage(exception.getClass().getName())
                    .build();
            return new ResponseEntity<>(details, headers, status);
        }



    }

}

