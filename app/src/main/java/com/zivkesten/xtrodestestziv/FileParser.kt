package com.zivkesten.xtrodestestziv

import android.util.Log
import java.io.IOException
import java.io.InputStream

class FileParser {

    var a0Records: List<Record> = mutableListOf<Record>()

    var first = true

    companion object {
        const val PACKET_START = 13
        const val PACKET_END = 10
    }

    @Throws(IOException::class)
    fun findAndParsePackets(inputStream: InputStream): List<Record> {
        val records = mutableListOf<Record>()
        val currentRecord = mutableListOf<Int>()
        var currentByte = inputStream.read()
        var packetType = -1
        var packetLength = 0

        while (currentByte != -1) {
            if (currentByte == PACKET_START) {
                currentRecord.clear()
                packetType = -1
                packetLength = 0
            }

            currentRecord.add(currentByte)

            if (currentRecord.size == 2) {
                packetType = currentRecord[1]
                if (first) Log.d("Zivi", "packetType $packetType")
            } else if (currentRecord.size == 10) {
                val ninthByte = currentRecord[8]
                val tenthByte = currentRecord[9]
                packetLength = tenthByte * 256 + ninthByte
                if (first) Log.d("Zivi", "packetLength $packetLength")
            }

            if (currentRecord.size >= 10 + packetLength && currentRecord[currentRecord.size - 3] == 0x00) {
                if (currentRecord.last() == PACKET_END) {
                    if (first) Log.d("Zivi", "PACKET_END $packetLength")

                    records.add(Record(packetType, currentRecord.toList())) // '0' for numberOfRecords if it's still required
                    currentRecord.clear()
                }
            }

            currentByte = inputStream.read()
            first = false
        }
       // Log.d("Zivi", "packets $packets")
        a0Records = a0Records.toMutableList().also { aoRecords ->
            aoRecords.addAll(records.filter { it.type != 160 })
        }
        return a0Records
    }
}
