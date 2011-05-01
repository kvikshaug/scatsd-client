import sbt._

class ScatsDClientProject(info: ProjectInfo) extends DefaultProject(info) {
  override def managedStyle = ManagedStyle.Maven
  val keyFile = new java.io.File("/home/murray/.ssh/id_rsa")
  val publishTo = Resolver.ssh("ScatsD Client", "spittle", "/var/www/sites/mvn.kvikshaug.no/") as("murray", keyFile)
}

