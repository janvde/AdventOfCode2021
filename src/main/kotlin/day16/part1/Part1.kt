package day16.part1

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

    fun parse(input: String) : Pair<Packet, Int> {
        val version = input.substring(0,3).toInt(2)
        val typeId = input.substring(3,6).toInt(2)
        if(typeId == 4){
            val data = input.substring(6)

            val end = data.windowed(5,5)
                .indexOfFirst { it.startsWith("0") }.inc() * 5

            val value = data.substring(0, end).windowed(5,5)
                .map { it.drop(1) }
                .joinToString("")
                .toLong(2)

            return Pair(Packet.Literal(version,typeId, value), end + 6)
        }
        else {
            val subPackets = mutableListOf<Pair<Packet, Int>>()
            val lengthType = input.get(6).digitToInt()
             return if(lengthType == 0){
                //the next 15 bits are a number that represents the total length in bits of the sub-packets contained by this packet.
                val subPacketLength = input.substring(7, 22).toInt(2)

                while(subPackets.sumOf { it.second } < subPacketLength){
                    val subPacket = parse(input.substring(22 + subPackets.sumOf { it.second }))
                    subPackets.add(subPacket)
                }
                 Pair(Packet.Operator(version, typeId, lengthType, subPackets.map { it.first }), 6+1+15+subPackets.sumOf { it.second })
            } else {
                //the next 11 bits are a number that represents the number of sub-packets immediately contained by this packet
                val numberOfSubPackets = input.substring(7, 7+11).toInt(2)

                repeat(numberOfSubPackets) {
                    val subPacket = parse(input.substring(7+11+subPackets.sumOf { it.second }))
                    subPackets.add(subPacket)
                }
                 Pair(Packet.Operator(version, typeId, lengthType, subPackets.map { it.first }), 6+1+11+subPackets.sumOf { it.second })
            }


        }
    }

    fun sumOfVersionNumbers(packet: Packet) : Int {
        return when(packet){
            is Packet.Literal -> packet.version
            is Packet.Operator -> packet.version + packet.packets.sumOf { sumOfVersionNumbers(it) }
        }
    }

    val binary = readInput().hexToBinaryString()
    val packets = parse(binary)
    val sum = sumOfVersionNumbers(packets.first)

    println(sum)

}

sealed class Packet(version: Int, typeId: Int){
    data class Literal(val version: Int, val typeId: Int, val value: Long) : Packet(version, typeId)
    data class Operator(val version: Int, val typeId: Int, val lengthType: Int, val packets: List<Packet>) : Packet(version, typeId)
}

fun readInput(): String = File("src/main/kotlin/day16/input.txt").readLines().first()

//110 100 01010