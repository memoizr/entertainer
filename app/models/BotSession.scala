package models

import com.mongodb.casbah.commons.MongoDBObject
import db.MongoConnection

class BotSession(server_id: String, authToken: String) {
  val db = MongoConnection.ensureConnection()
  val sessions = db("sessions")

  def save(): Unit = {
    val session = MongoDBObject("server_id" -> server_id, "authToken" -> authToken)
    sessions.insert(session)
  }
}
