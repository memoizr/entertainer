package models

import db.{MongoFactory, MongoConnection}
import com.mongodb.casbah.WriteConcern
import com.mongodb.casbah.commons.Imports._
import com.novus.salat._
import org.bson.types.ObjectId
import models.mongoContext._

case class BotSession(authToken: String,
                      email: String,
                      _id: ObjectId)

object BotSession {

  val botSessionCollection = MongoFactory.database("bot_sessions")

  def create(authToken: String, email: String, serverId: String): BotSession = {
    val botSession = new BotSession(authToken, email, new ObjectId(serverId))
    val dBObject = grater[BotSession] asDBObject botSession
    botSessionCollection save(dBObject, WriteConcern.Safe)

    grater[BotSession] asObject dBObject
  }

  def get(serverId: ObjectId): Option[BotSession] = {
    val id = MongoDBObject("_id" -> serverId)
    val obj = botSessionCollection.findOne(id)
    obj match {
      case Some(x) => Some(grater[BotSession].asObject(x))
      case _ => None
    }
  }

  def getByEmail(email: String): Option[BotSession] = {
    val id = MongoDBObject("email" -> email)
    val obj = botSessionCollection.findOne(id)
    obj match {
      case Some(x) => Some(grater[BotSession].asObject(x))
      case _ => None
    }
  }

  def all(): List[BotSession] = {
    val results = botSessionCollection.find()
    val sessions = for (item <- results) yield grater[BotSession].asObject(item)
    sessions.toList
  }
}
