package br.com.sg.mechanical.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

public class FieldErrorMessageFormatter {

    public static String format(Errors errors) {
        return errors.getFieldErrors()
                .stream()
                .map(e -> String.format("Campo %s %s", e.getField().toUpperCase(), e.getDefaultMessage()))
                .collect(Collectors.joining(", "));
    }

    public static String getConstraintField(DataIntegrityViolationException ex) {
        int beginIndex = ex.getMostSpecificCause().toString().indexOf("Key");
        int endIndex = ex.getMostSpecificCause().toString().indexOf("already");
        String causeString = ex.getMostSpecificCause().toString();
        return causeString.substring(beginIndex, endIndex);
    }

}
