package org.sportradar.football.worldcup.exceptions;

public class DoesNotExistsException extends Exception {

    public DoesNotExistsException(String error) {
        super(error);
    }
}
