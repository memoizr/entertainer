package controllers

import org.bson.types.ObjectId
import play.api.mvc._
import models._
import scala.util.Random

object Application extends Controller {

  val categories = Array("caturday", "hats", "sunglasses")
  val imageTypes = Array("jpg", "gif")

  def index = Action {

    for (i <- Task.all()) {
      println(i.label)
    }

    Ok("Your new application is ready.")
  }

  def createPost = Action {
    println("start")
    val url = pickRandomUrl()
    if (!url.isEmpty) {
      val serverId = "555c691c77d5166b8f000001"
      val botsession = BotSession.get(new ObjectId(serverId))
      if (botsession.isEmpty) {
        Login.loginAs("test@test.com", "testtest")
      } else {
        CatPost.createPost(url.get.url, url.get.category, botsession.get
          .authToken)
      }
    }
    Ok("Post was created")
  }

  def pickRandomUrl(): Option[CatUrl] = {
    val randCat = Random.nextInt(categories.length)
    val randType = if (Random.nextFloat() > 0.8) 1 else 0
    CatUrl.getRandom(categories(randCat), imageTypes(randType))
  }
}
