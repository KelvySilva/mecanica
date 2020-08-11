package br.com.sg.mechanical.utils;

import org.springframework.validation.Errors;

import java.util.stream.Collectors;

public class FieldErrorMessageFormater {

    public static String format(Errors errors) {
        return errors.getFieldErrors()
                .stream()
                .map(e -> String.format("Campo %s %s", e.getField(), e.getDefaultMessage()))
                .collect(Collectors.joining(", "));
    }

}
