package mdaros.exercises.scala.chapter2.filesystem.command
import mdaros.exercises.scala.chapter2.filesystem.state.State

class LsCommand ( val arguments: Array [String] ) extends Command {

  override def parse (tokens: Array [String] ): Command = {

    LsCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    state.setMessage ( state.workingFolder.children.mkString ( "\n" ) )
  }
}

object LsCommand {

  def parse ( tokens: Array [String] ): LsCommand = {

    new LsCommand ( tokens )
  }
}