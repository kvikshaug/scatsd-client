package no.kvikshaug

import java.net._

object StatsD {

  var address: Option[InetAddress] = None
  var port: Option[Int] = None
  val socket = new DatagramSocket

  def setHost(address: InetAddress, port: Int) {
    this.address = Some(address)
    this.port = Some(port)
  }

  // wrapper methods for java
  def count(stat: String, value: Double): Unit = count(stat, value, 0)
  def time(stat: String, value: Double): Unit = count(stat, value, 0)

  def count(stat: String, value: Double, interval: Double = 0) = send(format("%s|%s|%s|count", stat, value, interval))
  def retain(stat: String, value: Double) = send(format("%s|%s|0|retain", stat, value))
  def time(stat: String, value: Double, interval: Double = 0) = send(format("%s|%s|%s|time", stat, value, interval))

  def send(data: String) {
    val payload = data.getBytes
    val packet = new DatagramPacket(payload, payload.length, address.get, port.get)
    socket.send(packet)
  }
}

