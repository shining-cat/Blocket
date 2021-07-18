package fr.shining_cat.blocketgithubapp.repositories.converters

import fr.shining_cat.blocketgithubapp.models.Owner
import fr.shining_cat.blocketgithubapp.remote.api.dto.OwnerDto
import fr.shining_cat.blocketgithubapp.remote.api.dto.RepoDto
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class RepoConverterTest {

    @MockK
    private lateinit var mockOwnerConverter: OwnerConverter

    @MockK
    private lateinit var mockOwner: Owner

    private lateinit var repoConverter: RepoConverter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repoConverter = RepoConverter(mockOwnerConverter)
        every { mockOwnerConverter.convertDtoToModel(any()) } returns mockOwner
    }

    @Test
    fun `test convert DTO to model`() {
        val dto = RepoDto(
            id = 123,
            nodeId = "test node id",
            name = "test name",
            owner = OwnerDto(
                login = "test owner login",
                id = 345,
                nodeId = "test owner node id",
                avatarUrl = "test avatar url"
            ),
            description = "test description",
            isForked = false,
            url = "test url",
            createdAt = "test date creation",
            updatedAt = "test dat update",
            gitUrl = "test git url",
            sshUrl = "test ssh url",
            stargazersCount = 5,
            watchersCount = 9,
            language = "test language",
            openIssuesCount = 3
        )
        //
        val modelList = repoConverter.convertDtoToModel(listOf(dto))
        val modelTested = modelList[0]
        //
        assertEquals(123, modelTested.id)
        assertEquals("test node id", modelTested.nodeId)
        assertEquals("test name", modelTested.name)
        assertNotNull(modelTested.owner)
        assertEquals("test description", modelTested.description)
        assertEquals(false, modelTested.isForked)
        assertEquals("test url", modelTested.url)
        assertEquals("test date creation", modelTested.createdAt)
        assertEquals("test dat update", modelTested.updatedAt)
        assertEquals("test git url", modelTested.gitUrl)
        assertEquals("test ssh url", modelTested.sshUrl)
        assertEquals(5, modelTested.stargazersCount)
        assertEquals(9, modelTested.watchersCount)
        assertEquals("test language", modelTested.language)
        assertEquals(3, modelTested.openIssuesCount)
    }

    @Test
    fun `test convert DTO with null values to model`() {
        val dto = RepoDto(
            id = null,
            nodeId = null,
            name = null,
            owner = null,
            description = null,
            isForked = null,
            url = null,
            createdAt = null,
            updatedAt = null,
            gitUrl = null,
            sshUrl = null,
            stargazersCount = null,
            watchersCount = null,
            language = null,
            openIssuesCount = null
        )
        //
        val modelList = repoConverter.convertDtoToModel(listOf(dto))
        val modelTested = modelList[0]
        //
        assertEquals(-1, modelTested.id)
        assertEquals("", modelTested.nodeId)
        assertEquals("", modelTested.name)
        assertNotNull(modelTested.owner)
        assertEquals("", modelTested.description)
        assertEquals(false, modelTested.isForked)
        assertEquals("", modelTested.url)
        assertEquals("", modelTested.createdAt)
        assertEquals("", modelTested.updatedAt)
        assertEquals("", modelTested.gitUrl)
        assertEquals("", modelTested.sshUrl)
        assertEquals(-1, modelTested.stargazersCount)
        assertEquals(-1, modelTested.watchersCount)
        assertEquals("", modelTested.language)
        assertEquals(-1, modelTested.openIssuesCount)

    }

}