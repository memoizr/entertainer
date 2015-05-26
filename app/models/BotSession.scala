package models

import db.{MongoFactory, MongoConnection}
import com.mongodb.casbah.WriteConcern
import com.mongodb.casbah.commons.Imports._
import com.novus.salat._
import org.bson.types.ObjectId
import models.mongoContext._

case class BotSession(authToken: String, _id: ObjectId = new ObjectId)

object BotSession {

  val botSessionCollection = MongoFactory.database("bot_sessions")

  def create(authToken: String, serverId: String): BotSession = {
    val botSession = new BotSession(authToken, new ObjectId(serverId))
    val dBObject = grater[BotSession] asDBObject botSession
    botSessionCollection save(dBObject, WriteConcern Safe)
    grater[BotSession] asObject dBObject
  }

  def get(serverId: String): Option[BotSession] = {
    val id = MongoDBObject("_id" -> serverId)
    val obj = botSessionCollection.findOne(id)
    obj match {
      case Some(x) => Some(grater[BotSession].asObject(x))
      case _ => None
    }
  }
}
