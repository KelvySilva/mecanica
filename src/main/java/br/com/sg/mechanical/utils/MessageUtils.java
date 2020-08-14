package br.com.sg.mechanical.utils;

import br.com.sg.mechanical.error.MessageDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.time.Instant;
import java.util.Date;

public class MessageUtils {

    public static MessageDetails createFieldErrorMessage(Errors errors) {
        String errorMessage = FieldErrorMessageFormatter.format(errors);
        return MessageDetails.Builder.aMessageDetails()
                .title("Erro de validação")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format(errorMessage))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static MessageDetails createDataIntegrityErrorMessage(DataIntegrityViolationException ex) {
        return MessageDetails.Builder.aMessageDetails()
                .title("Erro de integridade")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("%s já existe!.", FieldErrorMessageFormatter.getConstraintField(ex)))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static MessageDetails createResourceListIsEmptyMessage() {
        return MessageDetails.Builder.aMessageDetails()
                .title("Não encontrado!")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("Nenhum item cadastrado."))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static MessageDetails createResourceEntityNotPresentMessage(Long id) {
        return MessageDetails.Builder.aMessageDetails()
                .title("Não encontrado!")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("Entidade não encontrada para o ID: %s.", id))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static MessageDetails createRequestConflictErrorMessage() {
        return MessageDetails.Builder.aMessageDetails()
                .title("Divergencia de dados")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("O ID na URL da requisição é diferente do informado no corpo da requisição."))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static MessageDetails createRequestBodyIsMissingMessage() {
        return MessageDetails.Builder.aMessageDetails()
                .title("Não foi possível realizar a ação.")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("O corpo da requisição precisa ser informado."))
                .developerMessage("DEVELOPMENT")
                .build();
    }
    public static MessageDetails createInvalidFormatErrorMessage(String value, String attr, String fields) {
        return MessageDetails.Builder.aMessageDetails()
                .title("Não foi possível realizar a ação.")
                .status(HttpStatus.CONFLICT.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("O valor [%s] não é aceito para o atributo [%s]. Os valores válidos são: %s", value,attr, fields))
                .developerMessage("DEVELOPMENT")
                .build();
    }

    public static MessageDetails createDeleteSuccessMessage(String entity) {
        return MessageDetails.Builder.aMessageDetails()
                .title("Não foi possível realizar a ação.")
                .status(HttpStatus.OK.value())
                .timestamp(Date.from(Instant.now()).getTime())
                .detail(String.format("%s deletado com sucesso!", entity))
                .developerMessage("DEVELOPMENT")
                .build();
    }

}
