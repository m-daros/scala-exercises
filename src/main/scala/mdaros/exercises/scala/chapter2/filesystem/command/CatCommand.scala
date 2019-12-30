package mdaros.exercises.scala.chapter2.filesystem.command

import java.io.FileNotFoundException

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

      val entity: FileSystemEntity = findEntity ( path = arguments.last, state.workingFolder.path (), state.rootFolder ) // TODO Dare eccezione se non è un File

      if ( null == entity ) {

        state.setMessage ( "Impossibile trovare il file " + arguments.last )
      }
      else {

        writeToStdout ( state, Array ( entity.asFile ().contents ), contentSeparator = "\n" )
      }
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">" ) ) { // TODO Blocco quasi identico al caso >>

      try {

        val inputFilePaths: Array [ String ] = arguments.slice ( 1, arguments.length - 2 )

        // TODO Gestire casi nei quali non si trova il file
        // TODO Gestire casi nei quali la entity é un Folder e non un File
        val filesContents: Array [String] = inputFilePaths.map ( filePath => extractFileContent ( state, filePath ) )

        writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = filesContents, contentSeparator = "\n" , overwrite = true )
      }
      catch {

        case e: Exception => {

          state.setMessage ( e.getMessage )
        }
      }
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">>" ) ) { // TODO Blocco quasi identico al caso >

      try {

        val inputFilePaths: Array [ String ] = arguments.slice ( 1, arguments.length - 2 )

        // TODO Gestire casi nei quali non si trova il file
        // TODO Gestire casi nei quali la entity é un Folder e non un File

        val filesContents: Array [String] = inputFilePaths.map ( filePath => extractFileContent ( state, filePath ) )

        writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = filesContents, contentSeparator = "\n" , overwrite = false )
      }
      catch {

        case e: Exception => {

          state.setMessage ( e.getMessage )
        }
      }
    }
    else {

      try {

        val contents: Array [String] = arguments.tail.map ( fileName => extractFileContent ( state, fileName ) )

        writeToStdout ( state, contents, contentSeparator = "\n" )
      }
      catch {

        case e: Exception => {

          state.setMessage ( e.getMessage )
        }
      }
    }
  }

  protected def extractFileContent ( state: State, filePath: String ): String = {

    val entity: FileSystemEntity = findEntity ( filePath, state.workingFolder.path (), state.rootFolder )

    if ( null == entity ) {

      throw new FileNotFoundException ( "Impossibile trovare il file " + filePath )
    }
    else if ( ! entity.isFile () ) {

      throw new UnsupportedOperationException ( "Impossibile recuperare il contenuto di " + filePath + " perchè non è un File" )
    }
    else {

      entity.asFile ().contents // TODO Dare eccezione se non è un File
    }
  }

  protected def findEntity ( path: String, workingFolderPath: String, rootFolder: Folder ): FileSystemEntity = {

    val filePath: String = getFilePath ( path, workingFolderPath )

    rootFolder.findDescendant ( filePath )
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