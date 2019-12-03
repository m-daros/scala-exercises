package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

class CatCommand ( val arguments: Array [String] ) extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    CatCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    ??? // TODO
  }

  // TODO PORTARE IN UNA CLASSE COMUNE
  @tailrec
  final def findFolder ( foldersNamesInPath: Array [String], currentFolder: Folder ): Folder = {

    if ( foldersNamesInPath.isEmpty ) {

      currentFolder
    }
    else if ( ! currentFolder.hasEntity ( foldersNamesInPath.head ) ) {

        null // TODO USARE Option
    }
    else {

        findFolder ( foldersNamesInPath.tail, currentFolder.findEntity ( foldersNamesInPath.head ).asFolder () )
    }
  }
}

object CatCommand {

  def parse ( tokens: Array [String] ): Command = {

    if ( tokens.length < 2 ) {

      new MalformedCommand ( tokens )
    }
    else {

      new CatCommand ( tokens )
    }
  }
}