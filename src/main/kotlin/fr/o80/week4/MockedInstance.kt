package fr.o80.week4

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

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

    private fun defaultValueFor(returnType: Class<*>): Any? = when {
        returnType == Byte::class.java -> 0.toByte()
        returnType == Char::class.java -> 0.toChar()
        returnType == Int::class.java -> 0
        returnType == Long::class.java -> 0L
        returnType == Float::class.java -> 0f
        returnType == Double::class.java -> 0f.toDouble()
        returnType.isJavaClass() -> null
        returnType == Function::class.java -> {
        }
        else -> throw IllegalArgumentException("Return type \"$returnType\" not mockable, because I'm lazy.")
    }

    private fun Class<*>.isJavaClass(): Boolean {
        return this.genericSuperclass != null
    }

}
