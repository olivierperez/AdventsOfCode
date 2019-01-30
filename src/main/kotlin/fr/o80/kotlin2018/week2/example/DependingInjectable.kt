package fr.o80.kotlin2018.week2.example

class DependingInjectable(
        private val scopedInjectable: ScopedInjectable,
        private val singletonInjectable: Singletonable
) {

    override fun toString(): String = """${super.toString()}
    scoped: $scopedInjectable
    singleton: $singletonInjectable"""
}