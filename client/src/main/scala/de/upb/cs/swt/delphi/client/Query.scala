package de.upb.cs.swt.delphi.client

import spray.json.DefaultJsonProtocol

case class Query(query : String, limit : Option[Int] = Some(50))

object QueryJson extends DefaultJsonProtocol {
  implicit val queryRequestFormat = jsonFormat2(Query)
}
