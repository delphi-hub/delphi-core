package de.upb.cs.swt.delphi.client

import com.softwaremill.sttp._
import com.softwaremill.sttp.sprayJson._
import spray.json._

import scala.util.Try
import de.upb.cs.swt.delphi.core.model.Artifact
import de.upb.cs.swt.delphi.client.QueryJson._

abstract class DelphiClient(baseUri : String) {
  def search(query : Query, prettyPrint : Boolean = false) : Try[Seq[SearchResult]]
   /* = {
    val queryParams = prettyPrint match {
      case true => Map("pretty" -> "")
      case false => Map()
    }
    val searchUri = uri"${baseUri}/search?$queryParams"

    val request = sttp.body(query.toJson).post(searchUri)

    //val (res, time) = processRequest(request)
    //res.foreach(processResults(_, time))
  }*/

  def features() : Try[Seq[FieldDefinition]]

  def retrieve(identifier : String) : Try[Artifact]

  def version() : Try[String]

  def statistics() : Try[Statistics]
}
