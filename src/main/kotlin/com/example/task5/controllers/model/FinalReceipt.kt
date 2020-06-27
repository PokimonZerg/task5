package com.example.task5.controllers.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FinalReceipt(
        val total: BigDecimal,
        val discount: BigDecimal,
        val positions: List<FinalPosition>
)