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

    private var value: MockedBody<*>? = null

    private val lock = Semaphore(1)

    fun <T> get(): MockedBody<T> {
        recording = false
        return value.also { value = null } as MockedBody<T>?
            ?: throw IllegalStateException("Nothing is recording")
    }

    fun <T : Any> verify(times: Int = 1, block: () -> T) {
        lock.runLock {
            verifying = true
            var effectiveCalls = 0
            try {
                repeat(times) {
                    block()
                    effectiveCalls++
                }
            } catch (e: CallException) {
                throw CallException(e, times, effectiveCalls)
            }
            verifying = false
        }
    }

    fun <T> setBody(block: () -> T, value: MockedBody<T>) {
        lock.runLock {
            recording = true
            this.value = value
            block()
            recording = false
        }
    }

    @Deprecated(message = "Use 'justReturn(value) on { mock.call() }'")
    fun <T> setReturnValue(block: () -> T, value: T) {
        setBody(block) { value }
    }

    class Returner<T>(private val value: MockedBody<T>) {
        infix fun on(function: () -> T) {
            MockTool.setBody(function, value)
        }
    }

    fun <T> justReturn(value: T): Returner<T> {
        return Returner { value }
    }

    fun <T> justDo(value: MockedBody<T>): Returner<T> {
        return Returner(value)
    }
}

internal class CallException(private val className: String, private val methodName: String, times: Int = -1, effectiveCalls: Int = -1) : RuntimeException("Excepting $className.$methodName(...) to be called $times times, but was called only $effectiveCalls times!") {
    constructor(cause: CallException, excepted: Int, effectiveCalls: Int) : this(cause.className, cause.methodName, excepted, effectiveCalls)
}

typealias MockedBody<T> = () -> T?

class MethodCall(val method: Method, val args: Array<out Any>?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MethodCall

        if (method != other.method) return false
        if (args != null) {
            if (other.args == null) return false
            if (!args.contentEquals(other.args)) return false
        } else if (other.args != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = method.hashCode()
        result = 31 * result + (args?.contentHashCode() ?: 0)
        return result
    }
}

class MockedInstance : InvocationHandler {

    private val values = mutableMapOf<MethodCall, MockedBody<Any>>()

    private val calls = mutableMapOf<Method, Int>()

    override fun invoke(mock: Any, method: Method, args: Array<out Any>?): Any? =
        when {
            MockTool.verifying -> {
                val count = calls[method]
                when (count) {
                    null -> throw CallException(method.declaringClass.name, method.name)
                    0 -> throw CallException(method.declaringClass.name, method.name)
                    else -> {
                        calls[method] = count - 1
                        defaultValueFor(method.returnType)
                    }
                }
            }
            MockTool.recording -> {
                val methodCall = MethodCall(method, args)
                values[methodCall] = MockTool.get()
                defaultValueFor(method.returnType)
            }
            else -> {
                val last = calls.getOrDefault(method, 0)
                calls[method] = last + 1

                val methodCall = MethodCall(method, args)
                values[methodCall]?.invoke() ?: defaultValueFor(method.returnType)
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
