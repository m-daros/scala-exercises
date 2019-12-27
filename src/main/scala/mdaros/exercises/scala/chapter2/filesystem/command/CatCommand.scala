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

      val fileName: String = arguments.tail.mkString ( FileSystemEntity.PATH_SEPARATOR )
      val filePath: String = getFilePath ( fileName, state.workingFolder.path () )
      val entity: FileSystemEntity = state.rootFolder.findDescendant ( filePath ) // TODO Dare eccezione se non è un File

      if ( null == entity ) {

        state.setMessage ( "Unable to find " + filePath )
      }
      else {

        writeToStdout ( state, Array ( entity.asFile ().contents ), contentSeparator = "\n" )
      }
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">" ) ) {

      val inputFileNames: Array [ String ] = arguments.slice ( 1, arguments.length - 2 )

      // TODO ... find dei files il cui path è contenuto in words

      writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = inputFileNames, overwrite = true )
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">>" ) ) {

      val inputFileNames: Array [ String ] = arguments.slice ( 1, arguments.length - 2 )

      // TODO ... find dei files il cui path è contenuto in words

      writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = inputFileNames, overwrite = false )
    }
    else {

      // TODO Gestire i casi in cui fileName contiene / => quindi occorre estrarre l'array dei tokens
      // TODO Gestire i casi in cui non si trova il file
      val contents: Array [String] = arguments.tail.map ( fileName => state.workingFolder.findDescendant ( fileName ).asFile () ).map ( file => file.contents )

      writeToStdout ( state, contents, contentSeparator = "\n" )
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