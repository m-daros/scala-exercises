package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

class CdCommand ( val arguments: Array [String] ) extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    CdCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    try {
      // TMP
      //println ( "WORKING FOLDER: " + state.workingFolder.path() )

      // 1. Get the path of the destination folder
      val destinationFolderPath: String = getDestinationFolderPath ( arguments ( 1 ), state.workingFolder )

      // TMP
      //println ( "EVALUATED DESTINATION FOLDER: " + destinationFolderPath )

      val foldersNamesInPath: Array [String] = destinationFolderPath.split ( FileSystemEntity.PATH_SEPARATOR ) // TODO *** Exclude the first empty string if exists

      // 2. Find the destination folder, if exists
      var destinationFolder: Folder = state.workingFolder

      if ( ! foldersNamesInPath.isEmpty ) {

        // TODO throw exception if it's not a Folder
//        destinationFolder = state.rootFolder.findDescendant ( foldersNamesInPath.tail, state.rootFolder ).asFolder ()// Se lo tolgo prima, evitare qui di fare il tail
        destinationFolder = state.rootFolder.findDescendant ( foldersNamesInPath.tail.mkString ( FileSystemEntity.PATH_SEPARATOR ) ).asFolder ()// Se lo tolgo prima, evitare qui di fare il tail
      }

      if ( destinationFolder == null ) {

        // 3a. Notify the user if the specified folder doesn't exist
        state.setMessage ( "Unable to find folder at the given path " + arguments (1  ) )
      }
      else {

        // TMP
        //println ( "destinationFolder: " + destinationFolder.path () )

        // 3b. Change the state to change the working folder to be the destination folder
        new State ( state.rootFolder, destinationFolder, "" )
      }
    }
    catch  {

      case e: Exception => {

        new State ( state.rootFolder, state.workingFolder, s"Unable to cd to the required Folder ${arguments.last}" )
      }
    }
  }

  def getDestinationFolderPath ( str: String, workingFolder: Folder ): String = {

    if ( str.equals ( FileSystemEntity.PATH_SEPARATOR ) ) {

      ""
    }
    else {

      // Remove dots in the given str
      val path: String = str.split ( FileSystemEntity.PATH_SEPARATOR )
        .filter ( token => ! token.equals ( "." ) )
        .mkString ( FileSystemEntity.PATH_SEPARATOR )

      if ( path.startsWith ( FileSystemEntity.PATH_SEPARATOR ) ) {

        filterDotDot ( path.split ( FileSystemEntity.PATH_SEPARATOR ), new Array [String] ( 0 ) ).mkString ( FileSystemEntity.PATH_SEPARATOR )
      }
      else {

        if ( workingFolder.isRoot () ) {

          filterDotDot ( ( workingFolder.path ().substring ( 1 ) + FileSystemEntity.PATH_SEPARATOR + path ).split ( FileSystemEntity.PATH_SEPARATOR ), new Array [String] ( 0 ) ).mkString ( FileSystemEntity.PATH_SEPARATOR )
        }
        else {

          filterDotDot ( ( workingFolder.path () + FileSystemEntity.PATH_SEPARATOR + path ).split ( FileSystemEntity.PATH_SEPARATOR ), new Array [String] ( 0 ) ).mkString ( FileSystemEntity.PATH_SEPARATOR )
        }
      }
    }
  }

  @tailrec
  final def filterDotDot ( pathTokens: Array [String], accum: Array [String] ): Array [String] = {

    if ( pathTokens.isEmpty ) {

      accum
    }
    else if ( pathTokens.head.equals ( ".." ) ) {

      if ( accum.isEmpty || accum.tail.isEmpty ) {

        throw new MyFileSystemException ( "Unable to change dir to the given path" )
      }
      else {

        filterDotDot ( pathTokens.tail, accum.slice ( 0, accum.length - 1 ) )
      }
    }
    else {

      filterDotDot ( pathTokens.tail, accum :+ pathTokens.head )
    }
  }

  // TODO Portato nella classe Folder, provare ad usare quello
  /*
  @tailrec
  final def findFolder ( foldersNamesInPath: Array [String], currentFolder: Folder ): Folder = {

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
   */
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