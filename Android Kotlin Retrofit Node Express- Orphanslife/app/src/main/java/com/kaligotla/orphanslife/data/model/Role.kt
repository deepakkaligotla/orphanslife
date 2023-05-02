package com.kaligotla.orphanslife.data.model

import java.io.Serializable

class Role : Serializable {
    var id = 0
    var role: String? = null
    var created_at: String? = null
    var updated_at: String? = null

    data class Role(
        var id: Int,
        var role: String,
        var created_at: String,
        var updated_at: String,
    )

    constructor() {}
    constructor(id: Int) {
        this.id = id
    }

    constructor(role: String?) {
        this.role = role
    }
}
