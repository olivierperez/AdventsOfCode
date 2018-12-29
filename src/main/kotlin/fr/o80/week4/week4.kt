package fr.o80.week4

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.Semaphore

object MockTool {

    internal var recording = false
        private set

    internal var verifying = false
        private set

    private var value: MockedBody? = null

    private val lock = Semaphore(1)

    fun get(): MockedBody {
        recording = false
        return value.also { value = null } ?: throw IllegalStateException("Nothing is recording")
    }

    fun <T : Any> verify(block: () -> T) {
        lock.runLock {
            verifying = true
            block()
            verifying = false
        }
    }

    fun <T> setBody(block: () -> T, value: MockedBody) {
        lock.runLock {
            recording = true
            this.value = value
            block()
            recording = false
        }
    }

    fun <T> setReturnValue(block: () -> T, value: T) {
        setBody(block) { value }
    }

}

typealias MockedBody = () -> Any?

class MockedInstance : InvocationHandler {

    private val values = mutableMapOf<Method, MockedBody>()

    private val calls = mutableListOf<Method>()

    override fun invoke(mock: Any, method: Method, args: Array<out Any>?): Any? =
        when {
            MockTool.verifying -> {
                if (method !in calls) {
                    throw IllegalStateException("Excepting ${method.declaringClass.name}.${method.name}(...) to be called, but it wasn't!")
                }
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
    return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz), MockedInstance()) as T
}
