package fr.o80.week2.lib

@DslMarker
annotation class Injection

@Injection
inline fun module(block: InjectModule.() -> Unit) = InjectModule().apply(block)

@Injection
inline fun <reified T : Any> InjectModule.factory(noinline generator: () -> T) {
    val className = T::class.qualifiedName
            ?: throw IllegalArgumentException("A non-real class cannot be injected")
    generators[className] = Prototype(generator)
}

@Injection
inline fun <reified T : Any> InjectModule.singleton(noinline generator: () -> T) {
    val className = T::class.qualifiedName
            ?: throw IllegalArgumentException("A non-real class cannot be injected")
    generators[className] = Singleton(generator)
}
