package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{FileSystemEntity, Folder}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

class CdCommand ( tokens: Array [String] ) extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    CdCommand.parse ( tokens )
  }

  def getDestinationFolderPath ( str: String, workingFolder: Folder ): String = {

    if ( str.startsWith ( FileSystemEntity.PATH_SEPARATOR ) ) {

      str
    }
    else {

      if ( workingFolder.isRoot () ) {

        workingFolder.path ().substring ( 1 ) + FileSystemEntity.PATH_SEPARATOR + str
      }
      else {

        workingFolder.path () + FileSystemEntity.PATH_SEPARATOR + str
      }
    }
  }

  @tailrec
  final def findFolder ( foldersNamesInPath: Array [String], currentFolder: Folder ): Folder = {

    // TMP
//    println ( "foldersNamesInPath length: " + foldersNamesInPath.length )
    //println ( "foldersNamesInPath: " + foldersNamesInPath.mkString ( ", " ) )
    //println ( "currentFolder.path: " + currentFolder.path () )

    if ( foldersNamesInPath.isEmpty ) {

      currentFolder
    }
    else if ( ! currentFolder.hasEntity ( foldersNamesInPath.head ) ) {

        null
    }
    else {

        findFolder ( foldersNamesInPath.tail, currentFolder.findEntity ( foldersNamesInPath.head ).asFolder () )
    }
  }

  override def apply ( state: State ): State = {

    // TMP
    //println ( "WORKING FOLDER: " + state.workingFolder.path() )

    // 1. Get the path of the destination folder
    val destinationFolderPath: String = getDestinationFolderPath ( tokens ( 1 ), state.workingFolder )

    // TMP
    //println ( "EVALUATED DESTINATION FOLDER: " + destinationFolderPath )

    val foldersNamesInPath: Array [ String ] = destinationFolderPath.split ( FileSystemEntity.PATH_SEPARATOR ) // TODO *** Exclude the first empty string if exists

    // 2. Find the destination folder, if exists
    var destinationFolder: Folder = state.workingFolder

    if ( ! foldersNamesInPath.isEmpty ) {

      destinationFolder = findFolder ( foldersNamesInPath.tail, state.rootFolder ) // Se lo tolgo prima, evitare qui di fare il tail
    }

    if ( destinationFolder == null ) {

      // 3a. Notify the user if the specified folder doesn't exist
      state.setMessage ( "Unable to find folder at the given path " + tokens ( 1 ) )
    }
    else {

      // TMP
      //println ( "destinationFolder: " + destinationFolder.path () )

      // 3b. Change the state to change the working folder to be the destination folder
      new State ( state.rootFolder, destinationFolder, "" )
    }
  }
}

object CdCommand {

  def parse ( tokens: Array [String] ): Command = {

    if ( tokens.length != 2 ) {

      new MalformedCommand ( tokens )
    }
    else {

      new CdCommand ( tokens )
    }
  }
}