# Library

- [Library](lib/InjectModule.kt)
- [DSL](lib/injection.dsl.kt)

# Examples

## Common

Here is the object which have dependencies.

```kotlin
class MainObject(module: InjectModule) {
    private val factoryOne: FactoryInjectable = module.get()
    private val factoryTwo: FactoryInjectable by module.inject()

    private val singletonOne: SingletonInjectable = module.get()
    private val singletonTwo: SingletonInjectable by module.inject()

    private val scopedOne: ScopedInjectable = module.get()
    private val scopedTwo: ScopedInjectable by module.inject()

    private val dependingInjectable: DependingInjectable by module.inject()
}
```

## Simple injection

First we define the scope of all the dependencies, then we can create the object which needs these.

```kotlin
fun main() {
    val module = module {
        factory(::FactoryInjectable) // Will create a new instance for any injection
        scoped(::ScopedInjectable) // Will create a single instance for all injections from this module
        singleton(::SingletonInjectable) // Will create a single instance, no matter the module

        factory { DependingInjectable(get(), get()) } // Example for injection in constructor
    }

    val o = MainObject(module)
}
```

## Module dependencies

A module can depend on another module to work.

```kotlin
fun main() {
    val module = module {
        factory(::FactoryInjectable) // Will create a new instance for any injection
        scoped(::ScopedInjectable) // Will create a single instance for all injections from this module
        singleton(::SingletonInjectable) // Will create a single instance, no matter the module

        factory { DependingInjectable(get(), get()) } // Example for injection in constructor
    }

    val module2 = module {
        dependsOn(module) // If module2 cannot provide a instance, it will ask it dependencies
        factory(::SingletonInjectable) // For this example, we replace a Singleton by a Factory
    }

    val o = MainObject(module)
}
```

## Full example

- [Package](example/)
- [fun main()](example/main.kt)
