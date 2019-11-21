package de.upb.cs.swt.delphi.core.model

import java.net.{URI, URLEncoder}
import java.nio.charset.StandardCharsets

case class MavenIdentifier(val repository: Option[String], val groupId: String, val artifactId: String, val version: Option[String]) extends Identifier {

  def toUniqueString: String = {
    repository.getOrElse(MavenIdentifier.DefaultRepository) + ":" + groupId + ":" + artifactId + ":" + version.getOrElse("")
  }

  override val toString: String = groupId + ":" + artifactId + ":" + version

  def toJarLocation : URI = {
    constructArtifactBaseUri().resolve(encode(artifactId) + "-" + encode(version.getOrElse("")) + ".jar")
  }

  def toPomLocation : URI = {
    constructArtifactBaseUri().resolve(encode(artifactId) + "-" + encode(version.getOrElse("")) + ".pom")
  }

  private def constructArtifactBaseUri(): URI =
    new URI(repository.getOrElse(MavenIdentifier.DefaultRepository))
      .resolve(encode(groupId).replace('.', '/') + "/")
      .resolve(encode(artifactId) + "/")
      .resolve(encode(version.getOrElse("")) + "/")

  private def encode(input : String) : String =
    URLEncoder.encode(input, StandardCharsets.UTF_8.toString())
}

object MavenIdentifier {
  private val DefaultRepository = "http://repo1.maven.org/maven2/"

  private implicit def wrapOption[A](value : A) : Option[A] = Some(value)

  def apply(s: String): Option[MavenIdentifier] = {
    val splitString: Array[String] = s.split(':')
    if (splitString.length < 2 || splitString.length > 3) return None

    MavenIdentifier(None, splitString(0), splitString(1), if (splitString.length < 3) None else splitString(2))

  }
}