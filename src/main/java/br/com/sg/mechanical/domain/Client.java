package br.com.sg.mechanical.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client extends Person {

    @Enumerated(EnumType.STRING)
    private TYPE type;

    @OneToMany(mappedBy = "client")
    private List<Vehicle> vehicles;

    public enum TYPE {

        PF("PESSOA_FÍSICA"),
        PJ("PESSOA_JURÍDICA");

        private String desc;

        TYPE(String desc) {
            this.desc = desc;
        }
    }
}
