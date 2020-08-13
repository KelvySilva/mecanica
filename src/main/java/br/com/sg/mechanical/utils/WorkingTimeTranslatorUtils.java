package br.com.sg.mechanical.utils;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.hibernate.validator.internal.util.logging.formatter.DurationFormatter;
import org.springframework.boot.convert.DurationFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class WorkingTimeTranslatorUtils {

    public static String translate(LocalDateTime fromDateTime) {
        LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );

        long months = tempDateTime.until( LocalDateTime.now(), ChronoUnit.MONTHS );
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( LocalDateTime.now(), ChronoUnit.DAYS );
        tempDateTime = tempDateTime.plusDays( days );


        long hours = tempDateTime.until( LocalDateTime.now(), ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( LocalDateTime.now(), ChronoUnit.MINUTES );
        tempDateTime = tempDateTime.plusMinutes( minutes );

        return String.format("%s meses, %s dias, %s horas, %s minutos",months,days,hours,minutes);
    }

    public static Duration translateToTime(LocalDateTime fromDateTime) {
        return Duration.between(fromDateTime, LocalDateTime.now());
    }

    public static Duration sum(Duration durationActual, Duration durationToAdd) {
        Duration durationUpdated = durationActual.plus(durationToAdd);
        return durationUpdated;
    }

}
