package models

import db.{MongoFactory, MongoConnection}

import com.mongodb.CommandResult
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.WriteConcern
import com.mongodb.casbah.commons.Imports._
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._
import com.mongodb.casbah.commons.conversions.scala._
import org.bson.types.ObjectId
import play.api.Play
import mongoContext2._

case class Tasks(label: String, _id: ObjectId= new ObjectId)

object Task {

//  implicit val ctx = new Context {
//    val name = "Custom_ClassLoader"
//    override val typeHintStrategy = StringTypeHintStrategy(when =
//    TypeHintFrequency.WhenNecessary, typeHint = TypeHint)
//  }

  val taskCollection = MongoFactory.database("tasks")

  def create(label: String): Tasks = {
    val task = Tasks(label)
    val dbObject = grater[Tasks].asDBObject(task)
    taskCollection.save(dbObject, WriteConcern.Safe)
    grater[Tasks].asObject(dbObject)

  }

  def all(): Unit = {
//    val results = taskCollection.find()
//    val tasks = for (item <- results) yield grater[Tasks].asObject(item)
//    tasks.toList
  }
}