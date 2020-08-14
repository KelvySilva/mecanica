package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))
public class Person extends AbstractEntity {

    @ApiModelProperty("Nome da pessoa")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String name;

    @ApiModelProperty("Endereço da pessoa")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String address;

    @ApiModelProperty("Número (do endereço da pessoa)")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String addressNumber;

    @ApiModelProperty("Cidade da pessoa")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String city;

    @ApiModelProperty("Estado onde a pessoa reside")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String state;

    @ApiModelProperty("CPF da pessoa")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    @Column(unique = true)
    private String cpf;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime cratedAtDate;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime lastUpdate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getName().equals(person.getName()) &&
                getAddress().equals(person.getAddress()) &&
                getAddressNumber().equals(person.getAddressNumber()) &&
                getCity().equals(person.getCity()) &&
                getState().equals(person.getState()) &&
                getCpf().equals(person.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress(), getAddressNumber(), getCity(), getState(), getCpf());
    }
}
