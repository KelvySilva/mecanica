package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.convert.DurationUnit;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

@ApiModel(value = "Objeto Ordem de Serviço")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ServiceOrder extends AbstractEntity {

    public String getUpTime() {
        return DurationFormatUtils.formatDuration(upTime.toMillis(), "MM:dd:HH:mm:ss");
    }

    @ApiModelProperty(hidden = true)
    @ApiIgnore
    @JsonIgnore
    public Duration getDurationIdleTime() {
        return idleTime;
    }

    public String getIdleTime() {
        return DurationFormatUtils.formatDuration(idleTime.toMillis(), "MM:dd:HH:mm:ss");
    }

    @ApiModelProperty(hidden = true)
    @ApiIgnore
    @JsonIgnore
    public Duration getDurationUpTime() {
        return upTime;
    }

    @ApiModelProperty("Descrição do problema pelo cliente.")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private String description;

    @ApiModelProperty("Descrição do problema pelo mecânico após a análise.")
    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    private String analysisResult;

    @ApiModelProperty(hidden = true)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ApiModelProperty(hidden = true)
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ApiModelProperty(hidden = true)
    private Duration upTime = Duration.ZERO;;

    @ApiModelProperty(hidden = true)
    private Duration idleTime = Duration.ZERO;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty("Status da OS")
    private STATUS status;

    @ManyToOne
    @ApiModelProperty("Veículo ao qual a OS está vinculada")
    private Vehicle vehicle;

    @ManyToOne
    @ApiModelProperty(value = "Funcionário responsável pelo trabalho.")
    private Employee employee;

    @ApiModel
    public enum STATUS {

        IN_ANALYSIS("EM_ANALISE"),
        ANALYSED("ANALISADO"),
        AWAITING_ANALYSIS("AGUARDANDO_ANALISE"),
        AWAITING_APPROVAL("AGUARDANDO_APROVACAO"),
        APPROVED("APROVADO"),
        IN_PROGRESS("EM_ANDAMENTO"),
        FINISHED("FINALIZADO"),
        CANCELED("CANCELADO");

        private String desc;

        STATUS(String desc) {
            this.desc = desc;
        }
    }

    @Override
    public String toString() {
        return "ServiceOrder{" +
                "description=" + description +
                ", analysisResult=" + analysisResult +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                ", upTime=" + upTime +
                ", idleTime=" + idleTime +
                ", status=" + status +
                ", vehicle=" + vehicle +
                ", employee=" + employee +
                '}';
    }
}
