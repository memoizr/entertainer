package models

import com.google.common.io.BaseEncoding
import play.api.libs.ws.WS
import config.Config._
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

case class Login(email: String, password: String)

object Login {

  def loginAs(email: String, password: String): Unit = {

    val encodedPassword = BaseEncoding.base64().encode(password.getBytes())

    val url = baseUrl + "/sessions/login"

    val futureResult = WS.url(url).post(Map(
      "email" -> Seq(email),
      "password" -> Seq(encodedPassword)
    )).map(response => {
      createNewSession(
        (response.json \ "authentication_token").as[String],
        (response.json \ "email").as[String],
        (response.json \  "server_id").as[String]
        )
    })
  }

  def createNewSession(authToken: String, email: String, serverId: String): Unit = {
    BotSession.create(authToken, email, serverId)
  }
}

