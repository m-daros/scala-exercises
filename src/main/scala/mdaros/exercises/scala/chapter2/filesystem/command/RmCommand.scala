package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{FileSystemEntity, Folder}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

case class RmCommand ( val arguments: Array [String] ) extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    MkDirCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    val workingFolder: Folder = state.workingFolder

    @tailrec
    def applyHelper ( state: State, tokens: Array [String] ): State = {

      if ( tokens.isEmpty ) {

        state
      }
      else {

        applyHelper ( evaluateToken ( state, tokens.head ), tokens.tail )
      }
    }

    def evaluateToken ( state: State, token: String ): State = {

      if ( ! workingFolder.hasEntity ( token ) ) {

        state.setMessage ( state.commandOutput + "\n" + token + " does not exist" )
      }
      else {

        doRemoveEntity ( state, token )
      }
    }

    applyHelper ( state, arguments.tail ) // Discard first token since it's the command name
  }

  def doRemoveEntity ( state: State, entityName: String ): State = {

    val newWorkingFolder = state.workingFolder.removeEntity ( entityName )

    new State ( state.rootFolder, newWorkingFolder, "" )
  }
}

object RmCommand {

  // TODO Portare check in una classe comune ?
  def parse ( tokens: Array [String] ): Command = {

    if ( tokens.length < 2 ) {

      new MalformedCommand ( tokens )
    }
    else {

      new RmCommand ( tokens )
    }
  }
}