package com.example.primeiraapp;

import com.example.primeiraapp.domain.RegisterValidator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RegisterValidatorTest {

    @Test
    public void rejeitaNomeVazio() {
        assertEquals("Introduz o teu nome.", RegisterValidator.validateProfile("", "Segunda"));
        assertEquals("Introduz o teu nome.", RegisterValidator.validateProfile(null, "Segunda"));
    }

    @Test
    public void rejeitaDisponibilidadeVazia() {
        assertEquals("Seleciona um dia de disponibilidade.",
                RegisterValidator.validateProfile("Ana", ""));
        assertEquals("Seleciona um dia de disponibilidade.",
                RegisterValidator.validateProfile("Ana", null));
    }

    @Test
    public void aceitaPerfilValido() {
        assertNull(RegisterValidator.validateProfile("Ana", "Segunda"));
    }
}
