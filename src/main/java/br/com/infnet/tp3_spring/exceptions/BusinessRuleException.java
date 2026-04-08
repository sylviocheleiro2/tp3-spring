package br.com.infnet.tp3_spring.exceptions;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
