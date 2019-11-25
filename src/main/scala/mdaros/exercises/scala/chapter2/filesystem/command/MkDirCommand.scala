package mdaros.exercises.scala.chapter2.filesystem.command
import mdaros.exercises.scala.chapter2.filesystem.model.{FileSystemEntity, Folder}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

// TODO Gestire casi del tipo mkdir /folder1/folder2 (o impedire trattandolo come caso non gestito)

case class MkDirCommand ( val arguments: Array [String] ) extends CreateEntityCommand ( arguments ) {

  override def parse ( tokens: Array [String] ): Command = {

    MkDirCommand.parse ( tokens )
  }

  override def createEntity ( folderPath: String, entityName: String ) : FileSystemEntity = {

    Folder.empty ( folderPath, entityName )
  }
}

object MkDirCommand {

  // TODO Portare check in una classe comune ?
  def parse ( tokens: Array [String] ): Command = {

      if ( tokens.length < 2 ) {

        new MalformedCommand ( tokens )
      }
      else {

        new MkDirCommand ( tokens )
      }
    }
}