package fr.o80.kotlin2018.week4

import java.lang.reflect.Method

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
