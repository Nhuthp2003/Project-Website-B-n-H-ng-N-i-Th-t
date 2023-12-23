package com.arphor.entity;

import lombok.Data;

@Data
public class Token {

	private String email;
    private String value;

    // Các getter và setter

    public Token(String email, String value) {
        this.email = email;
        this.value = value;
    }
}
