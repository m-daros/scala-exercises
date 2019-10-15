package mdaros.exercises.scala.chapter2.filesystem.command
import mdaros.exercises.scala.chapter2.filesystem.state.State

case class MalformedCommand ( tokens: Array [String] ) extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    MalformedCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    state.setMessage (  tokens.mkString ( " " ) + " Malformed command" )
  }
}

object MalformedCommand {

  def parse ( tokens: Array [String] ): Command = {

    new MalformedCommand ( tokens )
  }
}