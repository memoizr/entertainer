package models

import play.api.libs.ws.WS
import config.Config._
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

case class CatPost(imageUrl: String, category: String)

object CatPost {

  def createPost(imageUrl: String, category: String): Unit = {

    val url = baseUrl + "/sessions/login"

    val futureResult = WS.url(url).post(Map(
      "imageUrl" -> Seq(imageUrl),
      "category" -> Seq(category)
    )).map(response => {
      println(response.json)
    })
  }
}
