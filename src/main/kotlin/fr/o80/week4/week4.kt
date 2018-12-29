package fr.o80.week4

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object MockTool {
    var recording = false
        private set

    private var value: MockedBody? = null

    internal var verifying = false

    fun record(v: MockedBody) {
        value = v
        recording = true
    }

    fun get(): MockedBody {
        recording = false
        return value.also { value = null } ?: throw IllegalStateException("Nothing is recording")
    }

    fun verify() {
        verifying = true
    }

    fun verified() {
        verifying = false
    }
}

typealias MockedBody = () -> Any?

class MockHandler : InvocationHandler {

    private val values = mutableMapOf<Method, MockedBody>()

    private val calls = mutableListOf<Method>()

    override fun invoke(mock: Any, method: Method, args: Array<out Any>?): Any? =
        when {
            MockTool.verifying -> {
                if (method !in calls) {
                    MockTool.verified()
                    throw IllegalStateException("Excepting ${method.declaringClass.name}.${method.name}(...) to be called, but it wasn't!")
                }
                MockTool.verified()
                defaultValueFor(method.returnType)
            }
            MockTool.recording -> {
                values[method] = MockTool.get()
                defaultValueFor(method.returnType)
            }
            else -> {
                calls.add(method)
                values[method]?.let(MockedBody::invoke) ?: defaultValueFor(method.returnType)
            }
        }

    private fun defaultValueFor(returnType: Class<*>?): Any? = when (returnType) {
        Byte::class.java -> 0.toByte()
        Char::class.java -> 0.toChar()
        Int::class.java -> 0
        Long::class.java -> 0L
        Float::class.java -> 0f
        Double::class.java -> 0f.toDouble()
        Function::class.java -> {
        }
        else -> throw IllegalArgumentException("Return type \"$returnType\" not mockable, because I'm lazy.")
    }

}

inline fun <reified T> mock(): T {
    val clazz = T::class.java

    return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz), MockHandler()) as T
}

inline fun <T : Any> setReturnValue(block: () -> T, value: T) {
    MockTool.record { value }
    block()
}

inline fun <T : Any> setBody(block: () -> T, noinline value: MockedBody) {
    MockTool.record(value)
    block()
}

inline fun <T : Any> verify(block: () -> T) {
    MockTool.verify()
    block()
}