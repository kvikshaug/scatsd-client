package no.kvikshaug

import java.net._

object StatsD {
  // TODO load from config
  val host = InetAddress.getByAddress(Array(127, 0, 0, 1))
  val port = 2003

  def send(data: String, sampleRate: Double = 1) {
    val socket = new DatagramSocket(port)
    val payload = data.getBytes
    val packet = new DatagramPacket(payload, payload.length, host, port)
    socket.send(packet)
  }
}

