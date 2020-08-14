package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("Objeto Veículo")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "licensePlate"))
public class Vehicle extends AbstractEntity {


    @ApiModelProperty("Marca do veículo")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String brand;

    @ApiModelProperty("Modelo do veículo")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String model;

    @ApiModelProperty("Ano de lançamento")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String releaseDate;

    @ApiModelProperty("Ano de fabricação")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String manufactureDate;

    @ApiModelProperty(value = "Placa do veículo")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    @Column(unique = true)
    private String licensePlate;

    @ApiModelProperty(value = "Cliente dono do veículo", notes = "O cliente deve ser previamente cadastrado")
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

}
