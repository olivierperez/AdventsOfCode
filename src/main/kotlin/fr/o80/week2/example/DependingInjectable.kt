package fr.o80.week2.example

class DependingInjectable(
        private val scopedInjectable: ScopedInjectable,
        private val singletonInjectable: SingletonInjectable
) {

    override fun toString(): String = """${super.toString()}
    scoped: $scopedInjectable
    singleton: $singletonInjectable"""
}