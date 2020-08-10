package br.com.sg.mechanical.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends Person {

    private String username;

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
