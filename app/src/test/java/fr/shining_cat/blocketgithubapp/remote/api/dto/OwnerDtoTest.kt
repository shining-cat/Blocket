package fr.shining_cat.blocketgithubapp.remote.api.dto

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import fr.shining_cat.blocketgithubapp.AbstractTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class OwnerDtoTest : AbstractTest() {

    @Test
    fun `test parse json to OwnerDto`() {

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
        val testedRepo: RepoDto = repoDtoList[1]
        val testedOwner = testedRepo.owner
        assertNotNull(testedOwner)
        testedOwner as OwnerDto
        //
        assertEquals("shining-cat", testedOwner.login)
        assertEquals(19568399, testedOwner.id)
        assertEquals("MDQ6VXNlcjE5NTY4Mzk5", testedOwner.nodeId)
        assertEquals("https://avatars.githubusercontent.com/u/19568399?v=4", testedOwner.avatarUrl)
    }
}