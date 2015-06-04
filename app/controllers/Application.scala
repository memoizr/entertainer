package controllers

import org.bson.types.ObjectId
import play.api.mvc._
import models._

object Application extends Controller {

  def index = Action {

    for (i <- Task.all()) {
      println(i.label)
    }

    Ok("Your new application is ready.")
  }

  def createPost = Action {
    val url = pickRandomUrl("space", "jpg")
    val serverId = "555c691c77d5166b8f000001"
    val botsession = BotSession.get(new ObjectId(serverId))
    if (botsession.isEmpty) {
      Login.loginAs("test@test.com", "testtest")
    } else {
      CatPost.createPost(url.url, url.category, botsession.get.authToken)
      println("no")
    }
    Ok("Post was created")
  }

  def pickRandomUrl(category: String, imageType: String): CatUrl = {
    CatUrl.getRandom(category, imageType)
  }
}
