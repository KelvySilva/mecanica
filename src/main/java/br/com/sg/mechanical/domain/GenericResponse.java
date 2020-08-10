package br.com.sg.mechanical.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class GenericResponse implements Serializable {

    private LocalDateTime timestamp = LocalDateTime.now();
    private String title;
    private String details;
    private HttpStatus status;


    public static final class GenericResponseBuilder {
        private LocalDateTime timestamp =LocalDateTime.now();
        private String title;
        private String details;
        private HttpStatus status;

        private GenericResponseBuilder() {
        }

        public static GenericResponseBuilder aGenericResponse() {
            return new GenericResponseBuilder();
        }

        public GenericResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public GenericResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public GenericResponseBuilder details(String details) {
            this.details = details;
            return this;
        }

        public GenericResponseBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public GenericResponse build() {
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.details = this.details;
            genericResponse.status = this.status;
            genericResponse.timestamp = this.timestamp;
            genericResponse.title = this.title;
            return genericResponse;
        }
    }
}
