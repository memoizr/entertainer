package controllers

import db.MongoConnection
import play.api.mvc._
import models.BotSession

object Application extends Controller {

  def index = Action {
    val session = new BotSession("id", "tokenBar")
    session.save()
    val db = MongoConnection.ensureConnection()
    val collection = db.getCollection("sessions")
    println(collection.find().count())

    Ok("Your new application is ready.")
  }
}