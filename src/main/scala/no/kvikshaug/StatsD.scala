package no.kvikshaug

import java.net._

object StatsD {
  // Host/port to the StatsD server. These should be set by the application
  val host = InetAddress.getByName("127.0.0.1")
  val port = 2003

  val random = new java.util.Random

  def timing(stat: String, time: Double, sampleRate: Double = 1) = send(stat + ":" + time + "|ms", sampleRate)
  def increment(stat: String, sampleRate: Double = 1) = updateStats(stat, 1, sampleRate)
  def decrement(stat: String, sampleRate: Double = 1) = updateStats(stat, -1, sampleRate)

  def updateStats(stat: String, delta: Double, sampleRate: Double = 1) =
    send(stat + ":" + delta + "|c", sampleRate)

  def send(data: String, sampleRate: Double = 1): Unit = {
    if(sampleRate < 1 && sampleRate < random.nextDouble) {
      return
    }
    val socket = new DatagramSocket
    val payload = if(sampleRate == 1) {
      data.getBytes
    } else {
      (data + "|@" + sampleRate).getBytes
    }
    val packet = new DatagramPacket(payload, payload.length, host, port)
    socket.send(packet)
  }
}

