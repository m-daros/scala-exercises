package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State

class EchoCommand ( val arguments: Array [String] ) extends WriteContentCommand ( arguments ) {

  override def parse ( tokens: Array [String] ): Command = {

    EchoCommand.parse ( tokens )
  }

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  override def apply ( state: State ): State = {

    if ( arguments.length == 2 ) {

      // TODO Gestire i casi in cui fileName contiene / => quindi occorre estrarre l'array dei tokens
      writeToStdout ( state, arguments.tail, contentSeparator = " " )
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">" ) ) {

      // TODO Gestire i casi in cui fileName contiene / => quindi occorre estrarre l'array dei tokens
      writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = arguments.slice ( 1, arguments.length - 2 ), overwrite = true )
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">>" ) ) {

      // TODO Gestire i casi in cui fileName contiene / => quindi occorre estrarre l'array dei tokens
      writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = arguments.slice ( 1, arguments.length - 2 ), overwrite = false )
    }
    else {

      // TODO Gestire i casi in cui fileName contiene / => quindi occorre estrarre l'array dei tokens
      writeToStdout ( state, arguments.tail, contentSeparator = " " )
    }
  }
}

object EchoCommand {

  def parse ( arguments: Array [String] ): Command = {

    if ( arguments.length < 2 ) {

      new MalformedCommand ( arguments )
    }
    else {

      new EchoCommand ( arguments )
    }
  }
}