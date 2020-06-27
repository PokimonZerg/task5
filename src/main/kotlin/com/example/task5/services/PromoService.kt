package com.example.task5.services

import com.example.task5.controllers.model.*
import com.example.task5.repository.ItemsRepository
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class PromoService(val itemsRepository: ItemsRepository) {

    private lateinit var promo: Promo

    fun storePromo(promo: Promo) {
        this.promo = promo
    }

    private fun findBestDiscount(shopId: Int?, card: Boolean): BigDecimal? {
        if (card) {
            return promo.loyaltyCardRules?.filter { it.shopId == null || it.shopId == -1 }
                    ?.maxBy { it.discount }?.discount
        }
        else {
            return promo.loyaltyCardRules?.filter { it.shopId == shopId }
                    ?.maxBy { it.discount }?.discount
        }
    }

    private fun calculateDiscount(price: BigDecimal, discount: BigDecimal): BigDecimal {
        return price.multiply(discount)
    }

    fun calculateReceipt(receipt: Receipt): FinalReceipt {

        val items = itemsRepository.listItems()
        val groups = itemsRepository.listGroups()
        val positions = receipt.positions
        var total: BigDecimal = BigDecimal.ZERO
        var totalDiscount: BigDecimal = BigDecimal.ZERO

        val finalPositions = positions.map { p ->
            val item = items.firstOrNull { i -> i.id == p.itemId }
            if (item != null) {
                val discount = findBestDiscount(receipt.shopId, receipt.loyaltyCard)
                val regularPrice = item.price!!.toBigDecimal().multiply(p.quantity.toBigDecimal())
                val price = if (discount == null) regularPrice else regularPrice.minus(calculateDiscount(regularPrice, discount))
                totalDiscount = totalDiscount.plus(if (discount == null) BigDecimal.ZERO else calculateDiscount(regularPrice, discount))
                //val price = item.price!!.toBigDecimal().multiply(p.quantity.toBigDecimal())
                total = total.plus(price)
                FinalPosition(
                        id = p.itemId,
                        name = item.name!!,
                        price = price,
                        regularPrice = regularPrice
                )
            }
            else throw IllegalStateException("Беду не ждали")
        }

        val finalReceipt = FinalReceipt(
                total.setScale(2, RoundingMode.HALF_EVEN),
                totalDiscount.setScale(2, RoundingMode.HALF_EVEN),
                finalPositions.map { it.copy(
                        price = it.price.setScale(2, RoundingMode.HALF_EVEN),
                        regularPrice = it.regularPrice.setScale(2, RoundingMode.HALF_EVEN)
                ) })

        return finalReceipt
    }
}