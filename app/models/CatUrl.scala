package models

import db.MongoFactory
import play.api.libs.ws.WS
import db.{MongoFactory, MongoConnection}
import com.mongodb.casbah.WriteConcern
import com.mongodb.casbah.commons.Imports._
import com.novus.salat._
import org.bson.types.ObjectId
import models.mongoContext._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

import scala.concurrent.Future

case class CatUrl(url: String, imageType: String, category: String, _id:
ObjectId = new ObjectId())

object CatUrl {

  val urlCollection = MongoFactory.database("cat_urls")

  def create(category: String, imageType: String, number: Integer): Unit = {
    fetchCache(category, imageType, number).map(catUrl => {
      catUrl.foreach(i => {
        val dbObject = grater[CatUrl] asDBObject i
        urlCollection save(dbObject, WriteConcern.Safe)
      }
      )
    }
    )
  }

  var limit = 0

  def getRandom(category: String, imageType: String): Option[CatUrl] = {
    val q = MongoDBObject("category" -> category, "imageType" -> imageType)
    val catUrl = urlCollection.findOne(q)
    catUrl match {
      case Some(x) => {
        val ret = Some(grater[CatUrl].asObject(x))
        urlCollection.remove(x)
        return ret
      }
      case _ => {
        if (limit < 2) {
          Thread sleep limit * 500 + 500
          limit += 1
          create(category, imageType, 20)
          getRandom(category, imageType)
        } else {
          None
        }
      }
    }
  }

  def fetchCache(category: String, imageType: String, number: Integer):
  Future[List[CatUrl]] = {

    val catApiUrl: String = "http://thecatapi.com/api/images/get"
    val holder = WS.url(catApiUrl)
    val paramHolder = holder.withQueryString(
      "category" -> category,
      "format" -> "xml",
      "results_per_page" -> number.toString,
      "type" -> imageType
    )

    paramHolder.get().map(request =>
      (request.xml \\ "url").toList.map(url => {
          CatUrl(url.text, imageType, category)
        }))
  }
}

