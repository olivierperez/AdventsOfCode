package fr.o80.week4

import java.util.concurrent.Semaphore

internal fun Semaphore.runLock(block: () -> Unit) {
    acquire()
    block()
    release()
}
