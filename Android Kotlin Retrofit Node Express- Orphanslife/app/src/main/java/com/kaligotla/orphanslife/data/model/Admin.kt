package com.kaligotla.orphanslife.data.model

import com.google.gson.Gson
import java.io.Serializable

class Admin : Serializable {

    var admin_id = 0
    var admin_name: String? = null
    var admin_dob: String? = null
    var admin_gender: String? = null
    var admin_govt_id_type: String? = null
    var admin_govt_id: String? = null
    var admin_mobile: String? = null
    var admin_email: String? = null
    var admin_password: String? = null
    var address: String? = null
    private var location: Location? = null
    private var role: Role? = null
    private var orphanage: Orphanage? = null
    var image: String? = null
    var created_at: String? = null
    var updated_at: String? = null

    data class Admin(
        var admin_id: Int,
        var admin_name: String,
        var admin_dob: String,
        var admin_gender: String,
        var admin_govt_id_type: String,
        var admin_govt_id: String,
        var admin_mobile: String,
        var admin_email: String,
        var admin_password: String,
        var address: String,
        var location: Location,
        var role: Role,
        var orphanage: Orphanage,
        var image: String,
        var created_at: String,
        var updated_at: String
    )

    fun fromJson(adminFromDB: String?): Admin {
        val gson = Gson()
        return gson.fromJson(adminFromDB, Admin::class.java)
    }

    constructor() {}
    constructor(admin_id: Int) {
        this.admin_id = admin_id
    }

    constructor(
        admin_name: String?,
        admin_dob: String?,
        admin_gender: String?,
        admin_mobile: String?,
        admin_email: String?,
        admin_password: String?
    ) {
        this.admin_name = admin_name
        this.admin_dob = admin_dob
        this.admin_gender = admin_gender
        this.admin_mobile = admin_mobile
        this.admin_email = admin_email
        this.admin_password = admin_password
    }

    constructor(
        admin_id: Int,
        admin_name: String?,
        admin_dob: String?,
        admin_gender: String?,
        admin_govt_id_type: String?,
        admin_govt_id: String?,
        admin_mobile: String?,
        admin_email: String?,
        admin_password: String?,
        address: String?,
        location: Location?,
        role: Role?,
        orphanage: Orphanage?,
        image: String?
    ) {
        this.admin_id = admin_id
        this.admin_name = admin_name
        this.admin_dob = admin_dob
        this.admin_gender = admin_gender
        this.admin_govt_id_type = admin_govt_id_type
        this.admin_govt_id = admin_govt_id
        this.admin_mobile = admin_mobile
        this.admin_email = admin_email
        this.admin_password = admin_password
        this.address = address
        this.location = location
        this.role = role
        this.orphanage = orphanage
        this.image = image
    }
}
