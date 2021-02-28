package Akka

import akka.actor.Actor
import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

class HelloActor extends Actor{
  def receive = {
    case "Hello" => println("Wolrd")
    case "How are you?" => sender ! "I'm fine thank you!"
  }
}

object hello extends App{
  val system = ActorSystem("system")
  val actor = system.actorOf(Props[HelloActor])

  implicit val timeout = Timeout(5 seconds)
  val reply = actor ? "How are you?"

  reply.onComplete{
    case Success(msg:String)=> println("reply from actor: " + msg)
  }
}
