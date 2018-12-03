package fr.o80.week2.example

import fr.o80.week2.lib.InjectModule
import fr.o80.week2.lib.factory
import fr.o80.week2.lib.module
import fr.o80.week2.lib.singleton

fun main(args: Array<String>) {
    val module = module {
        factory(::FactoryInjectable)
        singleton(::SingletonInjectable)
    }
    println(MainObject(module).toString())
}

class MainObject(module: InjectModule) {

    private val factoryOne: FactoryInjectable = module.get()

    private val factoryTwo: FactoryInjectable by module.inject()

    private val singletonOne: SingletonInjectable = module.get()

    private val singletonTwo: SingletonInjectable by module.inject()

    override fun toString(): String {
        return """
Factory =>
    One: $factoryOne
    Two: $factoryTwo

Singleton =>
    One: $singletonOne
    Two: $singletonTwo
"""
    }

}