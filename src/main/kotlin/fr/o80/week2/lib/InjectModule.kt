package fr.o80.week2.lib

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class InjectModule {

    val generators: MutableMap<String, Generator<*>> = mutableMapOf()

    val dependsOn: MutableSet<InjectModule> = mutableSetOf()

    inline fun <reified T : Any> inject(): ReadOnlyProperty<Any, T> = object : ReadOnlyProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T = get()
    }

    inline fun <reified T : Any> get(): T {
        val className = T::class.qualifiedName
                ?: throw IllegalArgumentException("Cannot inject a non-real class")

        return findByName<T>(className)
                ?: dependsOn.firstOrNull { it.contains(className) }?.findByName(className)
                ?: throw IllegalArgumentException("$className doesn't provide the right type")
    }

    inline fun <reified T : Any> findByName(className: String): T? {
        val provider = generators[className]
                ?: throw IllegalArgumentException("$className is not declared as injectable")

        return provider.get() as? T
    }

    fun contains(className: String): Boolean =
            generators.containsKey(className)

}


abstract class Generator<T : Any> {
    abstract fun get(): T
}

class Prototype<T : Any>(private val block: () -> T) : Generator<T>() {
    override fun get() = block()

    fun invoke(): T = block()
}

class Singleton<T : Any>(private val key: String, private val block: () -> T) : Generator<T>() {

    override fun get(): T {
        val value = cached[key]
        return when (value) {
            null -> block().also { cached[key] = it }
            else -> value as T
        }
    }

    companion object {
        private var cached: MutableMap<String, Any> = mutableMapOf()
    }
}

class Scoped<T : Any>(private val block: () -> T) : Generator<T>() {
    private var cached: T? = null

    override fun get(): T {
        val value = cached
        return when (value) {
            null -> block().also { cached = it }
            else -> value
        }
    }
}
