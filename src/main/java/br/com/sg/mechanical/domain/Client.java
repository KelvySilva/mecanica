package br.com.sg.mechanical.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client extends Person {

    @Enumerated(EnumType.STRING)
    private TYPE type;


    @JsonBackReference
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
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
