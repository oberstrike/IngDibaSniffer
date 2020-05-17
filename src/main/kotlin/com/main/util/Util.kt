package com.main.util

import com.main.overlay.EntityFragment
import javafx.beans.property.IntegerProperty
import javafx.beans.property.Property
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.StringProperty
import org.jire.arrowhead.Process
import org.jire.arrowhead.get
import tornadofx.View
import tornadofx.stringBinding

data class Entity(val offset: Int) {
    var value: IntegerProperty = SimpleIntegerProperty(0)

    fun setValue(newValue: Int) {
        value.value = newValue
    }

}

fun Process.entity(entity: Entity): Int {
    return this[entity.offset]
}

fun View.entityFragment(name: String, entity: Entity): EntityFragment {
    return entityFragment(name, entity.value)
}

fun <T> View.entityFragment(
    name: String,
    property: Property<T>,
    converter: (T?) -> String = { it.toString() }
): EntityFragment {
    val map = mapOf(
        "property" to property.stringBinding { converter.invoke(it) },
        "propertyName" to name
    )

    return find(map)
}

fun View.entityFragment(name: String, property: StringProperty): EntityFragment {
    return entityFragment(name, property) { it.toString() }
}