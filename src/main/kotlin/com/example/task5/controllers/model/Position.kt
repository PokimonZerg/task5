package com.example.task5.controllers.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Position(
        val itemId: String,
        val quantity: Int
)