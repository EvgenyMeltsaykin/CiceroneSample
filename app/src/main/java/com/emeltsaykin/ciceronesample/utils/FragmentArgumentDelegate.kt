package com.emeltsaykin.ciceronesample.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class FragmentArgumentDelegate<T> : ReadWriteProperty<Fragment, T> {
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        val args = thisRef.arguments ?: Bundle().also { thisRef.arguments = it }
        args.put(property.name, value)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return thisRef.arguments?.get(property.name) as T
    }
}

@Suppress("UNCHECKED_CAST")
class FragmentNullableArgumentDelegate<T> : ReadWriteProperty<Fragment, T?> {
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
        val args = thisRef.arguments ?: Bundle().also { thisRef.arguments = it }
        value?.let {
            args.put(property.name, it)
        } ?: args.remove(property.name)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        return thisRef.arguments?.get(property.name) as? T
    }
}

fun <T : Any> Fragment.fragmentArgument(): ReadWriteProperty<Fragment, T> =
    FragmentArgumentDelegate()

fun <T : Any> Fragment.fragmentNullableArgument(): ReadWriteProperty<Fragment, T?> =
    FragmentNullableArgumentDelegate()

@Suppress("UNCHECKED_CAST")
fun <T> Bundle.put(key: String, value: T) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Short -> putShort(key, value)
        is Long -> putLong(key, value)
        is Byte -> putByte(key, value)
        is ByteArray -> putByteArray(key, value)
        is Char -> putChar(key, value)
        is CharArray -> putCharArray(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Float -> putFloat(key, value)
        is Bundle -> putBundle(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        is Array<*> -> putParcelableArray(key, value as Array<Parcelable>)
        is ArrayList<*> -> putParcelableArrayList(key, value as ArrayList<Parcelable>)
        else -> throw IllegalStateException("Type of property $key is not supported")
    }
}
