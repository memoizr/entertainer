package controllers

import db.MongoConnection

import play.api.mvc._
import models.{Task, BotSession}

import scala.util.Random

object Application extends Controller {

  def index = Action {
    Task.create("new task " + Random.nextInt(100))

    for (i <- Task.all()) {
      println(i.label)
    }

    Ok("Your new application is ready.")
  }
}
