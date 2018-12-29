package fr.o80.week4

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object MockTool {
    var recording = false
        private set

    private var value: Any? = null

    fun record(v: Any) {
        value = v
        recording = true
    }

    fun get(): Any {
        recording = false
        return value.also { value = null } ?: throw IllegalStateException("Nothing is recording")
    }
}

class MockHandler : InvocationHandler {
    private val values = mutableMapOf<Method, Any>()

    override fun invoke(mock: Any, method: Method, args: Array<out Any>?): Any {
        if (MockTool.recording) {
            values[method] = MockTool.get()
        }
        return values[method]!!
    }

}

inline fun <reified T> mock(): T {
    val clazz = T::class.java

    return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz), MockHandler()) as T
}

inline fun <T : Any> setReturnValue(block: () -> T, value: T) {
    MockTool.record(value)
    block()
}