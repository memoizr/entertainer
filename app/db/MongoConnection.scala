package db

import com.mongodb.casbah.{MongoDB, MongoClient}

object MongoConnection {

  val mongoClient = MongoClient("localhost", 27017)
  val db: MongoDB = mongoClient("entertainer_test")

  def ensureConnection(): MongoDB = {
    db
  }
}
