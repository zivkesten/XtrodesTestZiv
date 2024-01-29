package com.zivkesten.xtrodestestziv

import android.util.Log
import java.io.IOException
import java.io.InputStream

class FileParser {

    var packets: MutableList<Packet> = mutableListOf<Packet>()

    companion object {
        const val PACKET_START = 13
        const val PACKET_END = 10
    }

    @Throws(IOException::class)
    fun findAndParsePackets(inputStream: InputStream): List<Packet> {
        var currentPacket = mutableListOf<Int>()
        var lengthOfPacket = 0
        var currentByte = inputStream.read()

        while (currentByte != -1) {
            if (currentByte == PACKET_START) {
                if (currentPacket.isNotEmpty()) {
                    // Handle the case where PACKET_END wasn't found before the next PACKET_START
                    val numberOfRecords = extractNumberOfRecords(currentPacket, lengthOfPacket)
                    packets.add(Packet(currentPacket, numberOfRecords))
                    currentPacket = mutableListOf()
                }
            }

            currentPacket.add(currentByte)

            if (currentByte == PACKET_END) {
                val numberOfRecords = extractNumberOfRecords(currentPacket, lengthOfPacket)
                packets.add(Packet(currentPacket, numberOfRecords))
                currentPacket = mutableListOf()
            } else if (currentPacket.size % 6 == 0) {
                //lengthOfPacket = determinePacketLength(inputStream)
            }

            currentByte = inputStream.read()
        }

        // Handle any remaining bytes in the buffer
        if (currentPacket.isNotEmpty()) {
            val numberOfRecords = extractNumberOfRecords(currentPacket, lengthOfPacket)
            packets.add(Packet(currentPacket, numberOfRecords))
        }

        Log.d("Zivi", "Total packets: ${packets.size}")
        return packets
    }

    private fun extractNumberOfRecords(packet: List<Int>, lengthOfPacket: Int): Int {
        val index = 6 + lengthOfPacket + 2 // 6 initial bytes, length of packet, 2 bytes offset
        return if (index < packet.size) packet[index] else -1
    }

    private fun determinePacketLength(inputStream: InputStream): Int {
        Log.d("Zivi", "determinePacketLength")
        val lengthByte1 = inputStream.read()
        var length = lengthByte1
        if (lengthByte1 >= 256) {
            val lengthByte2 = inputStream.read()
            length = lengthByte1 + lengthByte2 * 256
        }
        Log.d("Zivi", "length $length")
        return length
    }
}
