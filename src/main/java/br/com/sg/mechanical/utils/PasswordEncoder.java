package br.com.sg.mechanical.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static String encode(String string) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(string);
    }

//    public static void main(String[] args) {
//        System.out.println(       PasswordEncoder.encode("aj[lo12po"));
//    }
}
