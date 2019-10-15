package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.Folder
import mdaros.exercises.scala.chapter2.filesystem.state.State

class PwdCommand extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    PwdCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    val workingFolder: Folder = state.workingFolder
    state.setMessage ( workingFolder.path () )
  }
}

object PwdCommand {

  def parse ( tokens: Array [String] ): PwdCommand = {

    new PwdCommand ()
  }
}