package com.bycorp.moviemanagement.exception;

import com.bycorp.moviemanagement.dto.response.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({
            ObjectNotFoundException.class, //el obj no existe en bbdd
            MethodArgumentTypeMismatchException.class, //el tipo de dato enviado es diferente al que deberia enviar
            MethodArgumentNotValidException.class, //el saveDto es invalido
            HttpRequestMethodNotSupportedException.class, //el metodo no esta soportado
            HttpMediaTypeNotSupportedException.class, //el tipo de medios enviado no lo soporta la aplicacion
            HttpMessageNotReadableException.class, //el medio enviado no se sabe que vaina es
            DuplicateRatingException.class, //usuario ya califico esa pelicula
            Exception.class
    })
    public ResponseEntity<ApiError> handleAllExceptions(Exception exception,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response) {

        LocalDateTime timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if(exception instanceof ObjectNotFoundException objectNotFoundException) {
            return this.handleObjectNotFoundException(objectNotFoundException, request, response, timestamp);

        } else if(exception instanceof InvalidPasswordException invalidPasswordException) {
            return this.handleInvalidPasswordException(invalidPasswordException, request, response, timestamp);

        } else if(exception instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return this.handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException, request, response, timestamp);

        }else if(exception instanceof MethodArgumentNotValidException methodArgumentNotValidException){
            return this.handleMethodArgumentNotValidException(methodArgumentNotValidException, request,response,timestamp);

        }else if(exception instanceof HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException){
            return this.handleHttpRequestMethodNotSupportedException(httpRequestMethodNotSupportedException, request, response, timestamp);

        }else if(exception instanceof HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {
            return this.handleHttpMediaTypeNotSupportedException(httpMediaTypeNotSupportedException, request, response, timestamp);

        }else if(exception instanceof HttpMessageNotReadableException httpMessageNotReadableException){
            return this.handleHttpMessageNotReadableException(httpMessageNotReadableException, request, response, timestamp);

        }else if(exception instanceof DuplicateRatingException duplicateRatingException) {
            return this.handleDuplicateRatingException(duplicateRatingException, request, response, timestamp);
        }else {
            return this.handleGenericException(exception, request, response, timestamp);
        }
    }

    private ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int status = HttpStatus.BAD_REQUEST.value();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Error reading the HTTP message body. make sure the request is correctly formatted and contains valid data",
                httpMessageNotReadableException.getMessage(),
                timestamp,
                null
        );

        return ResponseEntity.status(status).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException,
                                                                              HttpServletRequest request,
                                                                              HttpServletResponse response,
                                                                              LocalDateTime timestamp) {
        int status = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();

        ApiError apiError = new ApiError(
          status,
          request.getRequestURL().toString(),
          request.getMethod(),
          "unsupported media type: the server is unable to process the requested entity in the format provided in the request." +
                  "supported media types are: " + httpMediaTypeNotSupportedException.getSupportedMediaTypes() + " and you send: "
                  + httpMediaTypeNotSupportedException.getContentType() ,
          httpMediaTypeNotSupportedException.getMessage(),
                timestamp,
                null
        );

        return ResponseEntity.status(status).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException,
                                                                                  HttpServletRequest request,
                                                                                  HttpServletResponse response,
                                                                                  LocalDateTime timestamp) {
        int status = HttpStatus.METHOD_NOT_ALLOWED.value();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Mehod not allowed. Check the HTTP method of your request.",
                httpRequestMethodNotSupportedException.getMessage(),
                timestamp,
                null
        );

        return ResponseEntity.status(status).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                           HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           LocalDateTime timestamp) {

        int status = HttpStatus.BAD_REQUEST.value();

        List<ObjectError> errors = methodArgumentNotValidException.getAllErrors();
        List<String> details = errors.stream().map( error -> {

            if(error instanceof FieldError fieldError){
                return fieldError.getField().concat(": " + fieldError.getDefaultMessage());
            }

            return error.getDefaultMessage();
        }).toList();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "the request contains invalid or incomplete parameters" +
                        "please verify and provide the required information before trying again",
                methodArgumentNotValidException.getMessage(),
                timestamp,
                details
        );

        return ResponseEntity.status(status).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException,
                                                                               HttpServletRequest request,
                                                                               HttpServletResponse response,
                                                                               LocalDateTime timestamp) {
        int status = HttpStatus.BAD_REQUEST.value();
        Object valueRejected = methodArgumentTypeMismatchException.getValue();
        String propertyName = methodArgumentTypeMismatchException.getPropertyName();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid request: the provided value '"+valueRejected+"' does not have the expected data type for the "+propertyName,
                methodArgumentTypeMismatchException.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(status).body(apiError);
    }

    private ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException,
                                                                    HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    LocalDateTime timestamp) {
        int status = HttpStatus.BAD_REQUEST.value();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid password, the provided password does not meet the required criteria, "+ invalidPasswordException.getCause(),
                invalidPasswordException.getMessage(),
                timestamp,
                null
        );

        return ResponseEntity.status(status).body(apiError);
    }

    private ResponseEntity<ApiError> handleObjectNotFoundException(ObjectNotFoundException objectNotFoundException,
                                                                   HttpServletRequest request,
                                                                   HttpServletResponse response,
                                                                   LocalDateTime timestamp) {
        int status = HttpStatus.NOT_FOUND.value();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "the requested info could not be found, please check the url or try another search"+ objectNotFoundException.getCause(),
                objectNotFoundException.getMessage(),
                timestamp,
                null
        );

        return ResponseEntity.status(status).body(apiError);
    }

    private ResponseEntity<ApiError> handleGenericException(Exception exception, HttpServletRequest request,
                                                            HttpServletResponse response, LocalDateTime timestamp) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oops, something went wrong, please try again later, "+ exception.getCause(),
                exception.getMessage(),
                timestamp,
                null
        );

        return ResponseEntity.status(status).body(apiError);
    }

    public ResponseEntity<ApiError> handleDuplicateRatingException(DuplicateRatingException duplicateRatingException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp){
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT.value(),
                request.getRequestURL().toString(),
                request.getMethod(),
                duplicateRatingException.getMessage(),
                duplicateRatingException.getMessage(),
                timestamp,
                null
        );

        return ResponseEntity.badRequest().body(apiError);
    }
}
