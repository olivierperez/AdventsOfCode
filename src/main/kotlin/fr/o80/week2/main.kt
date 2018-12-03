package fr.o80.week2

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
        return "Factory => One: $factoryOne, Two: $factoryTwo\nSingleton => One: $singletonOne, Two: $singletonTwo"
    }

}