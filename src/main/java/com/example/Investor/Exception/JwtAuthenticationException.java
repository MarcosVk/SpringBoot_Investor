package com.example.Investor.Exception;


public class JwtAuthenticationException extends RuntimeException  {
    public JwtAuthenticationException(String message){
        super(message);
    }
}
