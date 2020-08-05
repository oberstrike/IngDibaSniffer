package com.main.util


data class Entity(val offset: Int) {
    var value: Int = 0
}

operator fun Entity.plus(baseAddress: Long): Long {
    return baseAddress + offset
}


operator fun Long.plus(entity: Entity): Long {
    return this + entity.offset
}