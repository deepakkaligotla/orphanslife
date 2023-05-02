package com.kaligotla.orphanslife.data.model

import java.io.Serializable


class Orphanage : Serializable {
    var id = 0
    var type: String? = null
    var address: String? = null
    var location: Location? = null
    var contact_person: String? = null
    var mobile: String? = null
    var phone: String? = null
    var email: String? = null
    var in_home = 0
    var adoptable = 0
    var boys = 0
    var girls = 0
    var orphanage_image: String? = null
    var created_at: String? = null
    var updated_at: String? = null

    data class Orphanage(
        var id: Int,
        var type: String,
        var address: String,
        var location: Location,
        var contact_person: String,
        var mobile: String,
        var phone: String,
        var email: String,
        var in_home: Int,
        var adoptable: Int,
        var boys: Int,
        var girls: Int,
        var orphanage_image: String,
        var created_at: String,
        var updated_at: String
    )

    constructor() {}
    constructor(id: Int) {
        this.id = id
    }

    constructor(address: String?) {
        this.address = address
    }

    constructor(
        type: String?,
        address: String?,
        location: Location?,
        contact_person: String?,
        mobile: String?,
        phone: String?,
        email: String?,
        in_home: Int,
        adoptable: Int,
        boys: Int,
        girls: Int,
        orphanage_image: String?
    ) {
        this.type = type
        this.address = address
        this.location = location
        this.contact_person = contact_person
        this.mobile = mobile
        this.phone = phone
        this.email = email
        this.in_home = in_home
        this.adoptable = adoptable
        this.boys = boys
        this.girls = girls
        this.orphanage_image = orphanage_image
    }
}
