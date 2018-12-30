package fr.o80.week4

internal class CallException(
    private val className: String,
    private val methodName: String,
    times: Int = -1,
    effectiveCalls: Int = -1
) : RuntimeException("Excepting $className.$methodName(...) to be called $times times, but was called only $effectiveCalls times!") {

    constructor(cause: CallException, excepted: Int, effectiveCalls: Int) : this(cause.className, cause.methodName, excepted, effectiveCalls)

}
