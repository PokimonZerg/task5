package com.example.task5.repository

import com.example.task5.repository.model.Item
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File

@Service
class ItemsRepository {

    fun listItems(): List<Item> {
        val bootstrapSchema = CsvSchema.emptySchema().withHeader()
        val mapper = CsvMapper().registerModule(KotlinModule())
        val stream = ItemsRepository::class.java.getResourceAsStream("/items.csv")
        val readValues: MappingIterator<Item> = mapper.readerFor(Item::class.java).with(bootstrapSchema).readValues(stream)
        return readValues.readAll()
    }

    fun listGroups(): List<Item> {
        val bootstrapSchema = CsvSchema.emptySchema().withHeader()
        val mapper = CsvMapper()
        val stream = ItemsRepository::class.java.getResourceAsStream("/groups.csv")
        val readValues: MappingIterator<Item> = mapper.readerFor(Item::class.java).with(bootstrapSchema).readValues(stream)
        return readValues.readAll()
    }
}