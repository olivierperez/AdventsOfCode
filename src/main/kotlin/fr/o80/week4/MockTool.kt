package fr.o80.week4

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
