package fr.o80.week2.example

import fr.o80.week2.lib.*

fun main(args: Array<String>) {

    val module = module {
        factory(::FactoryInjectable) // Will create a new instance for any injection
        scoped(::ScopedInjectable) // Will create a single instance for all injections from this module
        singleton(::SingletonInjectable) // Will create a single instance, no matter the module

        factory { DependingInjectable(get(), get()) } // Example for injection in constructor
    }
    println(MainObject(module).toString())
    println("\n===================\n")

    val module2 = module {
        dependsOn(module) // If module2 cannot provide a instance, it will ask it dependencies
        factory(::SingletonInjectable) // For this example, we replace a Singleton by a Factory
    }
    println(MainObject(module2).toString())
    println("\n===================\n")

    val module3 = module {
        factory(::FactoryInjectable)
        scoped(::ScopedInjectable)
        singleton(::SingletonInjectable)
        factory { DependingInjectable(get(), get()) }
    }
    println(MainObject(module3).toString())

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
