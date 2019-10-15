package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.state.State

class EmptyCommand extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    EmptyCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    state.setMessage ( "" )
  }
}

object EmptyCommand {

  def parse ( tokens: Array [String] ): Command = {

    new EmptyCommand ()
  }
}