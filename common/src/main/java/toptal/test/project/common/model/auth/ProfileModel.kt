package toptal.test.project.common.model.auth

import java.util.Date

enum class Role {
    USER, MANAGER, ADMIN, UNKNOWN
}

data class ProfileModel(
    val id: String,
    val role: Role,
    val email: String,
    val username: String,
    val created: Date
)
