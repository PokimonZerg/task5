package com.example.task5.controllers.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FinalPosition(
        val id: String,
        val name: String,
        val price: BigDecimal,
        val regularPrice: BigDecimal
)