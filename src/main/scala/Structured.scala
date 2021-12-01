import Structured.Events.{FileCreated, FileDeleted, FileModified, StartObserving, StopObserving, StoppedObserving}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import java.io.File
import java.sql.Timestamp

object Structured {

  object Events{
    case class StartObserving(directory:File)
    case object StopObserving
    case class StoppedObserving(directory:File)
    case class FileCreated(fqn:String,timestamp:Timestamp)
    case class FileDeleted(fqn:String,timestamp: Timestamp)
    case class FileModified(fqn:String,timestamp: Timestamp)
  }

  class FileActor(coreActor:ActorRef) extends Actor {

    override def receive: Receive = {
      case StartObserving(directory) => ???
      case StopObserving => ???
    }

    private def onFileCreate(fqn:String,timestamp: Timestamp):Unit = coreActor ! FileCreated(fqn,timestamp)
    private def onFileDelete(fqn:String,timestamp: Timestamp):Unit = coreActor ! FileDeleted(fqn,timestamp)
    private def onFileModify(fqn:String,timestamp: Timestamp):Unit = coreActor ! FileModified(fqn,timestamp)

  }

  class CoreActor extends Actor{
    override def receive: Receive = {
      case f:FileCreated => println(f) //send this to a stream
      case f:FileDeleted => println(f) // send to stream
      case f:FileModified => println(f) // send to stream
    }
  }

  val actorSystem = ActorSystem("my_logic")
  val observee = actorSystem.actorOf(Props(new CoreActor),"core_actor")
  val observer=actorSystem.actorOf(Props(new FileActor(observee)),"dir_observer")
  observer ! StartObserving(new File("some_dir"))
  //do something
  observer ! StopObserving

}
