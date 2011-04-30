package no.kvikshaug

import java.net._

object StatsD {

  var address: Option[InetAddress] = None
  var port: Option[Int] = None

  def setHost(address: InetAddress, port: Int) {
    this.address = Some(address)
    this.port = Some(port)
  }

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
    val packet = new DatagramPacket(payload, payload.length, address.get, port.get)
    socket.send(packet)
  }
}

