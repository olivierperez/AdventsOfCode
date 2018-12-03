package fr.o80.week2.example

import fr.o80.week2.lib.*

fun main(args: Array<String>) {
    val module = module {
        factory(::FactoryInjectable)
        singleton(::SingletonInjectable)
        scoped(::ScopedInjectable)
        factory { DependingInjectable(get(), get()) }
    }
    println(MainObject(module).toString())

    println("\n===================\n")

    val module2 = module {
        factory(::FactoryInjectable)
        singleton(::SingletonInjectable)
        scoped(::ScopedInjectable)
        factory { DependingInjectable(get(), get()) }
    }
    println(MainObject(module2).toString())

}

class MainObject(module: InjectModule) {

    private val factoryOne: FactoryInjectable = module.get()

    private val factoryTwo: FactoryInjectable by module.inject()

    private val singletonOne: SingletonInjectable = module.get()

    private val singletonTwo: SingletonInjectable by module.inject()

    private val scopedOne: ScopedInjectable = module.get()

    private val scopedTwo: ScopedInjectable by module.inject()

    private val dependingInjectable: DependingInjectable by module.inject()

    override fun toString(): String {
        return """Factory =>
    One: $factoryOne
    Two: $factoryTwo

Scoped =>
    One: $scopedOne
    Two: $scopedTwo

Singleton =>
    One: $singletonOne
    Two: $singletonTwo

Dependency =>
    $dependingInjectable"""
    }

}
