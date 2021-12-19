package day16.part2

import java.io.File

fun main(args: Array<String>) {

    fun Char.hexToBinary(): String {
        val i = Integer.parseInt(this.toString(), 16)
        return String.format(
            "%" + 4 + "s",
            Integer.toBinaryString(i)
        ).replace(" ".toRegex(), "0")
    }

    fun String.hexToBinaryString(): String {
        return map { it.hexToBinary() }.joinToString("")
    }

    fun parse(input: String): Pair<Packet, Int> {
        val version = input.substring(0, 3).toInt(2)
        val typeId = input.substring(3, 6).toInt(2)
        if (typeId == 4) {
            val data = input.substring(6)
            val end = data.windowed(5, 5)
                .indexOfFirst { it.startsWith("0") }.inc() * 5

            val value = data.substring(0, end)
                .windowed(5, 5)
                .joinToString("") { it.drop(1) }
                .toLong(2)

            return Pair(Packet.Literal(version, typeId, value), end + 6)
        } else {
            val subPackets = mutableListOf<Pair<Packet, Int>>()
            val lengthType = input[6].digitToInt()
            return if (lengthType == 0) {
                //the next 15 bits are a number that represents the total length in bits of the sub-packets contained by this packet.
                val subPacketLength = input.substring(7, 22).toInt(2)

                while (subPackets.sumOf { it.second } < subPacketLength) {
                    val subPacket = parse(input.substring(22 + subPackets.sumOf { it.second }))
                    subPackets.add(subPacket)
                }
                Pair(
                    Packet.Operator(version, typeId, lengthType, subPackets.map { it.first }),
                    6 + 1 + 15 + subPackets.sumOf { it.second }
                )
            } else {
                //the next 11 bits are a number that represents the number of sub-packets immediately contained by this packet
                val numberOfSubPackets = input.substring(7, 7 + 11).toInt(2)

                repeat(numberOfSubPackets) {
                    val subPacket = parse(input.substring(7 + 11 + subPackets.sumOf { it.second }))
                    subPackets.add(subPacket)
                }
                Pair(
                    Packet.Operator(version, typeId, lengthType, subPackets.map { it.first }),
                    6 + 1 + 11 + subPackets.sumOf { it.second }
                )
            }
        }
    }

    fun run(packet: Packet): Long {
        return when (packet) {
            is Packet.Literal -> packet.value
            is Packet.Operator -> with(packet.packets) {
                when (packet.typeId) {
                    0 -> sumOf { run(it) }
                    1 -> fold(1L) { acc, packet -> acc * run(packet) }
                    2 -> minOf { run(it) }
                    3 -> maxOf { run(it) }
                    5 -> if (run(this[0]) > run(this[1])) 1L else 0L
                    6 -> if (run(this[0]) < run(this[1])) 1L else 0L
                    7 -> if (run(this[0]) == run(this[1])) 1L else 0L
                    else -> {
                        throw Throwable("bummer")
                    }
                }
            }
        }
    }

    val binary = readInput().hexToBinaryString()
    val packets = parse(binary)
    val sum = run(packets.first)

    println(sum)

}

sealed class Packet(version: Int, typeId: Int) {
    data class Literal(val version: Int, val typeId: Int, val value: Long) : Packet(version, typeId)
    data class Operator(val version: Int, val typeId: Int, val lengthType: Int, val packets: List<Packet>) :
        Packet(version, typeId)
}

fun readInput(): String = File("src/main/kotlin/day16/input.txt").readLines().first()