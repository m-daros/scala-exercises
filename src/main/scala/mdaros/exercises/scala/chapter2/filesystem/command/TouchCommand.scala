package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, FileSystemEntity}
import mdaros.exercises.scala.chapter2.filesystem.state.State

class TouchCommand ( tokens: Array [String] ) extends CreateEntityCommand ( tokens ) {

  override def parse ( tokens: Array [String] ): Command = {

    TouchCommand.parse ( tokens )
  }

  override def createEntity ( folderPath: String, entityName: String ): FileSystemEntity = {

    File.empty ( folderPath, entityName )
  }
}

object TouchCommand {

  // TODO Portare in una classe comune ?
  def parse ( tokens: Array [String] ): Command = {

    if ( tokens.length < 2 ) {

      new MalformedCommand ( tokens )
    }
    else {

      new TouchCommand ( tokens )
    }
  }
}