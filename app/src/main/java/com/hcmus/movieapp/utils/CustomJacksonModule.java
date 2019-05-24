package com.hcmus.movieapp.utils;

import com.hcmus.movieapp.models.Ticket;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomJacksonModule extends SimpleModule {

    public CustomJacksonModule() {
        addKeyDeserializer(Ticket.class, new CustomDeserializer());
    }
}
