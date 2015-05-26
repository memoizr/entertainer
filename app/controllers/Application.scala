package controllers

import db.MongoConnection
import play.api.mvc._
import models.{Task, BotSession}

import scala.util.Random

object Application extends Controller {

  def index = Action {
//    val session = new BotSession("id", "tokenBar")
//    session.
//    val db = MongoConnection.ensureConnection()
//    val collection = db.getCollection("sessions")
//    println(collection.find().count())
    Task.create("new task " + Random.nextInt(100))

//    for (i <- TaskDAO.all()) {
////      println(i.label)
//    }

    Task.all()

    Ok("Your new application is ready.")
  }
}