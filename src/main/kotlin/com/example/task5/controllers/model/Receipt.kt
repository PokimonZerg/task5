package com.example.task5.controllers.model

data class Receipt(
        val shopId: Int?,
        val loyaltyCard: Boolean,
        val positions: List<Position>
)