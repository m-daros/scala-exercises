package mdaros.exercises.scala.chapter2.filesystem.command

import java.io.FileNotFoundException

import mdaros.exercises.scala.chapter2.filesystem.model.{File, FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

class CatCommand ( val arguments: Array [String] ) extends WriteContentCommand ( arguments ) {

  override def parse ( tokens: Array [String] ): Command = {

    CatCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    try {

      if ( arguments.length == 2 ) {

        // arguments.tail contains only one element
        val contents: Array [String] = arguments.tail.map ( fileName => extractFileContent ( state, fileName ) )
        writeToStdout ( state, contents, contentSeparator = "\n" )
      }
      else if ( arguments ( arguments.length - 2 ).equals ( ">" ) ) { // TODO Blocco quasi identico al caso >>

        val inputFilePaths: Array [String] = arguments.slice ( 1, arguments.length - 2 )
        val filesContents: Array [String] = inputFilePaths.map ( filePath => extractFileContent ( state, filePath ) )
        writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = filesContents, contentSeparator = "\n", overwrite = true )
      }
      else if ( arguments  ( arguments.length - 2 ).equals ( ">>" ) ) { // TODO Blocco quasi identico al caso >

        val inputFilePaths: Array [String] = arguments.slice ( 1, arguments.length - 2 )
        val filesContents: Array [String] = inputFilePaths.map ( filePath => extractFileContent ( state, filePath ) )
        writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = filesContents, contentSeparator = "\n", overwrite = false )
      }
      else {

        val contents: Array [String] = arguments.tail.map ( fileName => extractFileContent ( state, fileName ) )
        writeToStdout ( state, contents, contentSeparator = "\n" )
      }
    }
    catch {

        case e: Exception => {

          state.setMessage ( e.getMessage )
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

    // TODO Gestire casi come cat file1 > file2 file3 come MalformedCommand

    if ( tokens.length < 2 ) {

      new MalformedCommand ( tokens )
    }
    else if ( tokens.contains ( ">" ) && tokens.indexOf ( ">" ) < tokens.length - 2 ) {

      new MalformedCommand ( tokens )
    }
    else if ( tokens.contains ( ">>" ) && tokens.indexOf ( ">>" ) < tokens.length - 2 ) {

      new MalformedCommand ( tokens )
    }
    else {

      new CatCommand ( tokens )
    }
  }
}