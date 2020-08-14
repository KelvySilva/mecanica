package br.com.sg.mechanical.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@ApiModel("Objeto Cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client extends Person {


    @JsonBackReference
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    @ApiModelProperty(value = "Veículos que o cliente possui", hidden = true)
    private List<Vehicle> vehicles;




}
