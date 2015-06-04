package controllers

import org.bson.types.ObjectId
import jobs._
import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import models._
import scala.concurrent.Await
import scala.io.Source
import scala.util.Random
import scala.concurrent.duration._

case class Entertainer(email: String, password: String)

object Application extends Controller {

  val categories = Array("caturday", "hats", "sunglasses")
  val imageTypes = Array("jpg", "gif")

  def index = Action {
    Ok("Your new application is ready.")
  }

  def createPost = Action {
    println("start")
    val entertainer = getRandomEntertainer
    val url = pickRandomUrl()
    if (!url.isEmpty) {
      val botsession = BotSession.getByEmail(entertainer.email)
      if (botsession.isEmpty) {
        val awaitable = Login.loginAs(entertainer.email, entertainer.password)
        val session = Await.result[BotSession](awaitable, 3 seconds)
        CatPost.createPost(url.get.url, url.get.category, session.authToken)
      } else {
        CatPost.createPost(url.get.url, url.get.category, botsession.get
          .authToken)
      }
    }

    Ok("Post was created")
  }


  def getRandomEntertainer: Entertainer = {
    val filename = "app/assets/credentials.json"

    val fileContents = Source.fromFile(filename).getLines().mkString

    val jsonContents = Json.parse(fileContents)

    implicit val reader = Json.reads[Entertainer]

    val entertainers = (jsonContents \ "entertainers").as[List[Entertainer]]

    entertainers(Random.nextInt(entertainers.length))
  }

  def startPostingJob = Action {

    PosterJob.startJob()
    Ok("Job started")
  }

  def cancelPostingJob = Action {

    PosterJob.cancelJob()
    Ok("Job cancelled")
  }

  def pickRandomUrl(): Option[CatUrl] = {
    val randCat = Random.nextInt(categories.length)
    val randType = if (Random.nextFloat() > 0.8) 1 else 0
    CatUrl.getRandom(categories(randCat), imageTypes(randType))
  }
}
