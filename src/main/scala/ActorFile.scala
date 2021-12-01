import akka.actor.{Actor, ActorSystem, Props}

import java.io.File
import java.nio.file.{Path, Paths}
import scala.concurrent.duration.DurationInt
import scala.io.Source

object ActorFileModule extends App {

  val actorSystem = ActorSystem("ActorFileResponse")
  println(actorSystem.name)

  object ActorFile {
    case class FileCreate(Filename: String, FilePath: Path)
    case class FileDelete(Filename: String, FilePath: Path)
    case class FileModify(Filename: String, FilePath: Path, FileContent: File)
  }
  class ActorFile extends Actor {

    import ActorFile._

    override def receive: Receive = {
      case FileCreate(Filename: String, FilePath: Path) => val fileshare = new File(".").listFiles
        for(file -> fileshare) println(file)
      case FileDelete(Filename: String, FilePath: Path) => ???
      case FileModify(Filename: String, FilePath: Path, FileContent: File) => ???
    }
  }
  val ActorFileSystem = actorSystem.actorOf(Props[ActorFile], "ActorFile")
   ActorFileSystem ! new File("new.txt")
  }

