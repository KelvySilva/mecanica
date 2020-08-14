package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("Objeto Funcionário")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends Person {

    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    @ApiModelProperty("Nome de Usuário do sistema")
    private String username;

    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    @ApiModelProperty("Senha de acesso ao sistema")
    private String password;

    @ApiModelProperty("Tipo que corresponde a função qu ele executa na empresa.")
    @Enumerated(EnumType.STRING)
    private TYPE type;

    public enum TYPE {

        ATD("ATENDENTE"),
        MEC("MECÂNICA");

        private String desc;

        TYPE(String desc) {
            this.desc = desc;
        }
    }

}
