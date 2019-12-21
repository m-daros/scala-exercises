package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

class CatCommand ( val arguments: Array [String] ) extends WriteContentCommand ( arguments ) {

  override def parse ( tokens: Array [String] ): Command = {

    CatCommand.parse ( tokens )
  }

  // TODO OPEN FILE / FILES TO RETRIEVE CONTENT
  override def apply ( state: State ): State = {

    if ( arguments.length == 2 ) {

//      val file: File = findFile ( arguments.tail, state.workingFolder )
      val file: File = state.workingFolder.findDescendant ( arguments.tail ).asFile () // TODO Dare eccezione se non è un File

      // TODO Gestire i casi in cui fileNAme contiene / => quindi occorre estrarre l'array dei tokens
      writeToStdout ( state, Array ( file.contents ), contentSeparator = "\n" )
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">" ) ) {

      // TODO ... find dei files il cui path è contenuto in words

      writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = arguments.slice ( 1, arguments.length - 2 ), overwrite = true )
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">>" ) ) {

      // TODO ... find dei files il cui path è contenuto in words

      writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = arguments.slice ( 1, arguments.length - 2 ), overwrite = false )
    }
    else {

      // TODO Gestire i casi in cui fileName contiene / => quindi occorre estrarre l'array dei tokens
      // TODO Gestire i casi in cui non si trova il file
//      val contents: Array [String] = arguments.tail.map ( fileName => findFile ( Array ( fileName ), state.workingFolder ) ).map ( file => file.contents )
      val contents: Array [String] = arguments.tail.map ( fileName => state.workingFolder.findDescendant ( Array ( fileName ) ).asFile () ).map (file => file.contents )

      writeToStdout ( state, contents, contentSeparator = "\n" )
    }
  }

  // TODO ...
  // TODO RIMUOVERE IN FAVORE DELLA findDescendant presente in Folder
  /*
  @tailrec
  final def findFile ( entityNamesInPath: Array [String], currentFolder: Folder ): File = {

    if ( entityNamesInPath.tail.isEmpty ) {

      currentFolder.findLocalEntity ( entityNamesInPath.head ).asFile ()
    }
//    else if ( ! currentFolder.hasEntity ( entityNamesInPath.head ) ) {
//
//        null // TODO USARE Option
//    }
    else {

        findFile ( entityNamesInPath.tail, currentFolder.findLocalEntity ( entityNamesInPath.head ).asFolder () )
    }
  }
   */
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