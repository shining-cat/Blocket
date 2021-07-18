package fr.shining_cat.blocketgithubapp.remote.api.dto

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import fr.shining_cat.blocketgithubapp.AbstractTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class RepoDtoTest : AbstractTest() {

    @Test
    fun `test parse json to RepoDto`() {

        val jsonString = readFile("json/github_repos_test_file.json")

        val moshiBuilder = Moshi.Builder()
            .build()
        val type = Types.newParameterizedType(
            List::class.java,
            RepoDto::class.java
        )
        val jsonAdapter: JsonAdapter<List<RepoDto>> = moshiBuilder.adapter(
            type
        )
        val repoDtoList: List<RepoDto>? = jsonAdapter.fromJson(jsonString)
        //
        assertNotNull(repoDtoList)
        repoDtoList as List<RepoDto>
        assertEquals(5, repoDtoList.size)
        //
        val testedRepo: RepoDto = repoDtoList[1]
        assertEquals(186446164, testedRepo.id)
        assertEquals("MDEwOlJlcG9zaXRvcnkxODY0NDYxNjQ=", testedRepo.nodeId)
        assertEquals("everyday_kt", testedRepo.name)
        assertNotNull(testedRepo.owner)
        assertEquals("The Kotlin version!", testedRepo.description)
        assertEquals(false, testedRepo.isForked)
        assertEquals("https://api.github.com/repos/shining-cat/everyday_kt", testedRepo.url)
        assertEquals("2019-05-13T15:23:16Z", testedRepo.createdAt)
        assertEquals("2021-07-15T12:22:12Z", testedRepo.updatedAt)
        assertEquals("git://github.com/shining-cat/everyday_kt.git", testedRepo.gitUrl)
        assertEquals("git@github.com:shining-cat/everyday_kt.git", testedRepo.sshUrl)
        assertEquals(1, testedRepo.stargazersCount)
        assertEquals(1, testedRepo.watchersCount)
        assertEquals("Kotlin", testedRepo.language)
        assertEquals(0, testedRepo.openIssuesCount)

    }
}