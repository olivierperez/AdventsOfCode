package fr.o80.week4

import java.lang.reflect.Proxy

inline fun <reified T> mock(): T {
    val clazz = T::class.java
    return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz), MockedInstance()) as T
}
