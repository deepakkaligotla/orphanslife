package com.kaligotla.orphanslife.data.model

import java.io.Serializable


class Location : Serializable {
    var id = 0
    var pincode = 0
    var area: String? = null
    var city: String? = null
    var district: String? = null
    var state: String? = null

    constructor() {}
    constructor(id: Int) {
        this.id = id
    }

    constructor(pincode: Int, area: String?, city: String?, district: String?, state: String?) {
        this.pincode = pincode
        this.area = area
        this.city = city
        this.district = district
        this.state = state
    }
}

