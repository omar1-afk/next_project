package com.noteam.next.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MeResponse(
         long id,
         String email
) { }
