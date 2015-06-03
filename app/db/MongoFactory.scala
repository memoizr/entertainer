package db

import com.mongodb.casbah.{MongoClient, MongoClientURI}
import com.typesafe.config.ConfigFactory

object MongoFactory {
  private val client = MongoClient("localhost", 27017)
  val database = client("entertainer_test")
}
