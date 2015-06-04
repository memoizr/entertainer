package models

import play.api.libs.ws.WS
import config.Config._
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

case class CatPost(imageUrl: String, category: String)

object CatPost {

  def createPost(imageUrl: String, category: String, authToken: String): Unit
  = {

    val url = baseUrl + "/cat_post"

    val futureResult = WS.url(url).withHeaders("Auth-Token" -> authToken).post(Map(
      "image_url" -> Seq(imageUrl),
      "category" -> Seq(category)
    )).map(response => {
      println(response.json)
    })
  }
}
