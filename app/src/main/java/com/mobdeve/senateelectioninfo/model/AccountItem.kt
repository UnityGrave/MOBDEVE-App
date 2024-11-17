package com.mobdeve.senateelectioninfo.model

class AccountItem(var accountID: Int, var username: String, var password: String) {
    companion object {
        private const val DEFAULT_ID = -1
    }

    constructor(username: String, password: String) : this(DEFAULT_ID, username, password)
    constructor() : this(DEFAULT_ID, "Blank", "Blank")
}