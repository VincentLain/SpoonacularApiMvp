package com.vincent.lain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MenuResponse {
    @SerializedName("totalMenuItems")
    @Expose
    var totalMenuItems: Int? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("offset")
    @Expose
    var offset: Int? = null

    @SerializedName("number")
    @Expose
    var number: Int? = null

    @SerializedName("menuItems")
    @Expose
    var menuItems: List<Menu>? = null

    constructor() {}

    constructor(
        totalMenuItems: Int?,
        type: String?,
        offset: Int?,
        number: Int?,
        menuItems: List<Menu>?
    ) {
        this.totalMenuItems = totalMenuItems
        this.type = type
        this.offset = offset
        this.number = number
        this.menuItems = menuItems
    }


}