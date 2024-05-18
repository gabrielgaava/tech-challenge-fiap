package com.fiap.techchallenge.domain.exception;

import java.util.UUID;

public class EntityNotFound extends Exception {

    public EntityNotFound(String entity, UUID id) {
        super("Entity " + entity + "with id " + id.toString() + "not found.");
    }

}
