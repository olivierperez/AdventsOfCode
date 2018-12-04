package fr.o80.day4

import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("4.1 => ${exercise_4_1()}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("4.2 => ${exercise_4_2()}") }.also { println("Time: ${it}ms") }
}

fun exercise_4_1(): Int {
    val records = mutableListOf<Record>()
    var lastRecord: Record? = null
    day4input.split('\n')
            .map { Regex("\\[1518-(\\d+-\\d+) (\\d+):(\\d+)] (.+)").find(it)!!.groupValues }
            .sortedBy { found ->
                found[1] + found[2] + found[3]
            }
            .onEach { found ->
                val date = found[1]
                val minutes = found[3]
                val msg = found[4]

                when {
                    msg.startsWith("Guard") -> {
                        val guard = Regex("Guard #(\\d+) begins shift").find(msg)!!.groupValues[1]
                        lastRecord = Record(guard).also { records.add(it) }

                    }
                    msg == "falls asleep" -> {
                        lastRecord!!.from = minutes.toInt()
                    }
                    msg == "wakes up" -> {
                        val from = lastRecord!!.from!!
                        val to = minutes.toInt()
                        val sleep = Sleep(from, to)
                        lastRecord!!.sleeps.add(sleep)
                    }
                }
                lastRecord!!.date = date
            }

    val groupedByGuard = records.groupBy(Record::guard)

    val maxSleepingGuard = groupedByGuard
            .mapValues { (_, records) ->
                records.fold(0) { acc, record ->
                    val time = record.sleeps.fold(0) { a, sleep -> a + sleep.time }
                    acc + time
                }
            }
            .maxBy { (_, time) -> time }
            ?.key!!

//    println("Guard: $maxSleepingGuard")

    val sleepindMinutes = mutableMapOf<Int, Int>()
    records.filter { record -> record.guard == maxSleepingGuard }
            .flatMap { record -> record.sleeps }
            .onEach { sleep ->
                for (h in 0 until 60) {
                    if (h in sleep.from until sleep.to) {
                        val previous = sleepindMinutes.getOrDefault(h, 0)
                        sleepindMinutes[h] = previous + 1
                    }
                }
            }

    val (hour, time) = sleepindMinutes.maxBy { (k, v) -> v }!!
//    println("Sleeping: $hour => ${time}h")
    return maxSleepingGuard.toInt() * hour
}

fun exercise_4_2(): Int {
    val records = mutableListOf<Record>()
    var lastRecord: Record? = null
    day4input.split('\n')
            .map { Regex("\\[1518-(\\d+-\\d+) (\\d+):(\\d+)] (.+)").find(it)!!.groupValues }
            .sortedBy { found ->
                found[1] + found[2] + found[3]
            }
            .onEach { found ->
                val date = found[1]
                val minutes = found[3]
                val msg = found[4]

                when {
                    msg.startsWith("Guard") -> {
                        val guard = Regex("Guard #(\\d+) begins shift").find(msg)!!.groupValues[1]
                        lastRecord = Record(guard).also { records.add(it) }

                    }
                    msg == "falls asleep" -> {
                        lastRecord!!.from = minutes.toInt()
                    }
                    msg == "wakes up" -> {
                        val from = lastRecord!!.from!!
                        val to = minutes.toInt()
                        val sleep = Sleep(from, to)
                        lastRecord!!.sleeps.add(sleep)
                    }
                }
                lastRecord!!.date = date
            }

    val sleepingMinutesByGuard = mutableMapOf<String, MutableMap<Int, Int>>()

    records.groupBy(Record::guard)
            .onEach { (guard, records) ->
                sleepingMinutesByGuard[guard] = mutableMapOf()
                records.flatMap { record -> record.sleeps }
                        .onEach { sleep ->
                            for (h in 0 until 60) {
                                if (h in sleep.from until sleep.to) {
                                    val previous = sleepingMinutesByGuard[guard]!!.getOrDefault(h, 0)
                                    sleepingMinutesByGuard[guard]!![h] = previous + 1
                                }
                            }
                        }
            }

    sleepingMinutesByGuard
            .mapValues { (_, sleeps) ->
                if (sleeps.isEmpty()) {
                    Pair(-1, -1)
                } else {
                    val hour = sleeps.maxBy { (_, sleep) -> sleep }?.key ?: 0
                    Pair(hour, sleeps[hour])
                }
            }
            .maxBy { (_, pair) ->
                val (_, time) = pair
                time ?: 0
            }?.let { result ->
                val id = result.key.toInt()
                val hour = result.value.first
                println("Guard: $id => ${hour}h")
                return id * hour
            }
    return -1
}

data class Record(val guard: String, var date: String? = null, var from: Int? = null, var sleeps: MutableList<Sleep> = mutableListOf())
data class Sleep(val from: Int, val to: Int) {
    val time = to - from
}