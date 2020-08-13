package br.com.sg.mechanical.utils;

import br.com.sg.mechanical.error.ErrorDetails;
import br.com.sg.mechanical.utils.FieldErrorMessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.time.Instant;
import java.util.Date;

public class ErrorUtils {

    public static ErrorDetails createFieldErrorMessage(Errors errors) {
        String errorMessage = FieldErrorMessageFormatter.format(errors);
        return ErrorDetails.Builder.anErrorDetails()
                .title("Erro de validação")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format(errorMessage))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static ErrorDetails createDataIntegrityErrorMessage(String field) {
        return ErrorDetails.Builder.anErrorDetails()
                .title("Erro de integridade")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("%s já existe!.", field))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static ErrorDetails createResourceListIsEmptyMessage() {
        return ErrorDetails.Builder.anErrorDetails()
                .title("Não encontrado!")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("Nenhum item cadastrado."))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static ErrorDetails createResourceEntityNotPresentMessage(Long id) {
        return ErrorDetails.Builder.anErrorDetails()
                .title("Não encontrado!")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("Entidade não encontrada para o ID: %s.", id))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static ErrorDetails createRequestConflictErrorMessage() {
        return ErrorDetails.Builder.anErrorDetails()
                .title("Divergencia de dados")
                .status(HttpStatus.CONFLICT.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("O ID na URL da requisição é diferente do informado no corpo da requisição."))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static ErrorDetails createRequestBodyIsMissingMessage() {
        return ErrorDetails.Builder.anErrorDetails()
                .title("Não foi possível realizar a ação.")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("O corpo da requisição precisa ser informado."))
                .developerMessage("DEVELOPMENT")
                .build();
    }
    public static ErrorDetails createInvalidFormatErrorMessage(String value, String attr, String fields) {
        return ErrorDetails.Builder.anErrorDetails()
                .title("Não foi possível realizar a ação.")
                .status(HttpStatus.CONFLICT.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("O valor [%s] não é aceito para o atributo [%s]. Os valores válidos são: %s", value,attr, fields))
                .developerMessage("DEVELOPMENT")
                .build();
    }

}
