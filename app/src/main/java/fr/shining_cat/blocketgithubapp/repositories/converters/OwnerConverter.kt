package fr.shining_cat.blocketgithubapp.repositories.converters

import fr.shining_cat.blocketgithubapp.models.Owner
import fr.shining_cat.blocketgithubapp.remote.api.dto.OwnerDto

class OwnerConverter {

    fun convertDtoToModel(owner: OwnerDto?): Owner {
        return Owner(
            login = owner?.login ?: "",
            id = owner?.id ?: -1,
            nodeId = owner?.nodeId ?: "",
            avatarUrl = owner?.avatarUrl ?: ""
        )

    }
}