package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends Person {

    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String username;

    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String password;

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
