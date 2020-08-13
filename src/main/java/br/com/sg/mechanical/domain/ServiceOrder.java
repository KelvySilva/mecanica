package br.com.sg.mechanical.domain;

import br.com.sg.mechanical.constants.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.convert.DurationUnit;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ServiceOrder extends AbstractEntity {

    public String getUpTime() {
        return DurationFormatUtils.formatDuration(upTime.toMillis(), "MM:dd:HH:mm:ss");
    }

    public Duration getDurationIdleTime() {
        return idleTime;
    }
    public String getIdleTime() {
        return DurationFormatUtils.formatDuration(idleTime.toMillis(), "MM:dd:HH:mm:ss");
    }
    public Duration getDurationUpTime() {
        return upTime;
    }


    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @NotEmpty(message = ValidationMessages.NOT_EMPTY_MESSAGE)
    private StringBuilder description;

    @NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    private StringBuilder analysisResult;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    private Duration upTime = Duration.ZERO;;

    private Duration idleTime = Duration.ZERO;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private Employee employee;

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
