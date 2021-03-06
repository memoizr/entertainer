package jobs

import play.api.Logger
import play.api.libs.concurrent.Akka
import akka.actor._
import play.api.libs.ws.WS
import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.Play.current

object PosterJob {
  val Tack = false
  var running = false

  def cancelJob(): Unit = {
    running = false
  }

  def startJob() {

    val tickActor = Akka.system.actorOf(Props(new Actor {
      running = true

      def receive = {
        case Tack => {
          if (running) {
            WS.url("http://0.0.0.0:9000/create_post").get().map(r => println(r
              .body))
          }
        }
      }
    }))

    Akka.system.scheduler.schedule(0 seconds, 7 seconds,
      tickActor, Tack)
  }
}
