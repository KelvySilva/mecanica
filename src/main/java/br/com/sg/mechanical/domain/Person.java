package br.com.sg.mechanical.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person extends AbstractEntity {

    private String name;

    private String address;

    private String addressNumber;

    private String city;

    private String state;

    @CreationTimestamp
    private LocalDateTime cratedAtDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;


}
