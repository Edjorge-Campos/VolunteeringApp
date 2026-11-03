package com.example.primeiraapp;

import com.example.primeiraapp.domain.RegisterErrorMapper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterErrorMapperTest {

    @Test
    public void usaMensagemGenericaParaErroInesperado() {
        assertEquals(
                "Não foi possível criar a conta. Tenta novamente.",
                RegisterErrorMapper.toMessage(new IllegalStateException("erro interno"))
        );
    }
}
