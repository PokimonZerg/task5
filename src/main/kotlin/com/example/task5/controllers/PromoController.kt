package com.example.task5.controllers

import com.example.task5.controllers.model.ErrorView
import com.example.task5.controllers.model.FinalReceipt
import com.example.task5.controllers.model.Promo
import com.example.task5.controllers.model.Receipt
import com.example.task5.services.BranchException
import com.example.task5.services.PromoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class PromoController(val promoService: PromoService) {

    @PostMapping("/promo")
    fun promo(@RequestBody promo: Promo) {
        promoService.storePromo(promo)
    }

    @PostMapping("/receipt")
    fun receipt(@RequestBody receipt: Receipt): FinalReceipt {
        return promoService.calculateReceipt(receipt)
    }

    @ExceptionHandler(BranchException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleException(e: BranchException): ErrorView {
        return ErrorView(e.message ?: "")
    }
}