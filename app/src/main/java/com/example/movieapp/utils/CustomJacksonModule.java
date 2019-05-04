package com.example.movieapp.utils;

import com.example.movieapp.models.Ticket;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomJacksonModule extends SimpleModule {

    public CustomJacksonModule() {
        addKeyDeserializer(Ticket.class, new CustomDeserializer());
    }
}
