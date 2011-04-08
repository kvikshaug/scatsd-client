package no.kvikshaug

import java.net._

object StatsD {
  // TODO load from config
  val host = InetAddress.getByAddress(Array(127, 0, 0, 1))
  val port = 2003

  val random = new java.util.Random

  def send(data: String, sampleRate: Double = 1): Unit = {
    if(sampleRate < 1 && sampleRate < random.nextDouble) {
      return
    }
    val socket = new DatagramSocket(port)
    val payload = if(sampleRate == 1) {
      data.getBytes
    } else {
      (data + "|@" + sampleRate).getBytes
    }
    val packet = new DatagramPacket(payload, payload.length, host, port)
    socket.send(packet)
  }
}

