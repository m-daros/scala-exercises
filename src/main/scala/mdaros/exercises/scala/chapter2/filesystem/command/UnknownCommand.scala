package mdaros.exercises.scala.chapter2.filesystem.command
import mdaros.exercises.scala.chapter2.filesystem.state.State

class UnknownCommand ( val tokens: Array [String] ) extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    UnknownCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    state.setMessage ( tokens.mkString ( " " ) + " Unknown command" )
  }
}

object UnknownCommand {

  def parse ( tokens: Array [String] ): Command = {

    new UnknownCommand ( tokens )
  }
}