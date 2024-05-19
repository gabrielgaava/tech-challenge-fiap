package com.fiap.techchallenge.domain.exception;

import java.util.UUID;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String entity, UUID id) {
        super("Entity " + entity + "with id " + id.toString() + "not found.");
    }

}
