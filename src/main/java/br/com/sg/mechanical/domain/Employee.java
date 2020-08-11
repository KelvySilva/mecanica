package br.com.sg.mechanical.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends Person {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
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
