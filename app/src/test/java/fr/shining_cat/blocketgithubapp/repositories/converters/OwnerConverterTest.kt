package fr.shining_cat.blocketgithubapp.repositories.converters

import fr.shining_cat.blocketgithubapp.remote.api.dto.OwnerDto
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OwnerConverterTest {

    private lateinit var ownerConverter: OwnerConverter

    @Before
    fun setUp() {
        ownerConverter = OwnerConverter()
    }

    @Test
    fun `test convert DTO to model`() {
        val dto = OwnerDto(
            login = "test owner login",
            id = 345,
            nodeId = "test owner node id",
            avatarUrl = "test avatar url"
        )
        //
        val modelTested = ownerConverter.convertDtoToModel(dto)
        //
        assertEquals("test owner login", modelTested.login)
        assertEquals(345, modelTested.id)
        assertEquals("test owner node id", modelTested.nodeId)
        assertEquals("test avatar url", modelTested.avatarUrl)
    }

    @Test
    fun `test convert DTO with null values to model`() {
        val dto = OwnerDto(
            login = null,
            id = null,
            nodeId = null,
            avatarUrl = null
        )
        //
        val modelTested = ownerConverter.convertDtoToModel(dto)
        //
        assertEquals("", modelTested.login)
        assertEquals(-1, modelTested.id)
        assertEquals("", modelTested.nodeId)
        assertEquals("", modelTested.avatarUrl)
    }

}