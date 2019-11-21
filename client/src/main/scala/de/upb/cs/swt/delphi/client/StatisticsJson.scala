package de.upb.cs.swt.delphi.client

import spray.json.DefaultJsonProtocol

object StatisticsJson extends DefaultJsonProtocol {
  implicit val statisticsFormat = jsonFormat2(Statistics)
}
