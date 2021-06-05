package com.mta.hwapplication.model

data class AuthResponse(val authentication_token: String, val person: Person, val message: String)

data class Person(val key: String, val display_name: String, val role: Role)

data class Role(val key: String, val rank: Int)
