package io.metis.personal.adapters.mitarbeiter.rest;

import io.metis.personal.application.mitarbeiter.MitarbeiterNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class MitarbeiterExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MitarbeiterNotFoundException.class)
    void handle(MitarbeiterNotFoundException ex) {
        // spring erledigt den Rest für uns
    }

}
