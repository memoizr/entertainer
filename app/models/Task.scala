package models

import db.{MongoFactory, MongoConnection}

import com.mongodb.casbah.WriteConcern
import com.mongodb.casbah.commons.Imports._
import com.novus.salat._
import org.bson.types.ObjectId
import models.mongoContext._

case class Tasks(label: String, _id: ObjectId= new ObjectId)

object Task {

  val taskCollection = MongoFactory.database("tasks")

  def create(label: String): Tasks = {
    val task = Tasks(label)
    val dbObject = grater[Tasks].asDBObject(task)
    taskCollection.save(dbObject, WriteConcern.Safe)
    grater[Tasks].asObject(dbObject)
  }

  def all(): List[Tasks] = {
    val results = taskCollection.find()
    val tasks = for (item <- results) yield grater[Tasks].asObject(item)
    tasks.toList
  }
}