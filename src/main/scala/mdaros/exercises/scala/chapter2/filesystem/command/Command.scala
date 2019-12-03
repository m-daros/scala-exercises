package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.state.State

trait Command {

  def parse ( tokens: Array [String] ): Command

  def apply ( state: State ): State
}

object Command {

  def from ( commandLine: String ): Command = {

    val tokens: Array [String] = commandLine.trim ().split ( " " )

    // TODO dispatch to other commamkdirnds based on commandLine contents ...

    if ( commandLine.trim ().isEmpty () ) {

      EmptyCommand.parse ( tokens );
    }
    else if ( "mkdir".equals ( tokens ( 0 ).trim () ) ) {

      MkDirCommand.parse ( tokens )
    }
    else if ( "touch".equals ( tokens ( 0 ).trim () ) ) {

      TouchCommand.parse ( tokens )
    }
    else if ( "ls".equals ( tokens ( 0 ).trim () ) ) {

      LsCommand.parse ( tokens )
    }
    else if ( "pwd".equals ( tokens ( 0 ).trim () ) ) {

      PwdCommand.parse ( tokens )
    }
    else if ( "cd".equals ( tokens ( 0 ).trim () ) ) {

      CdCommand.parse ( tokens )
    }
    else if ( "rm".equals ( tokens ( 0 ).trim () ) ) {

      RmCommand.parse ( tokens )
    }
    else if ( "echo".equals ( tokens ( 0 ).trim () ) ) {

      EchoCommand.parse ( tokens )
    }
    else if ( "cat".equals ( tokens ( 0 ).trim () ) ) {

      CatCommand.parse ( tokens )
    }
    else {

      UnknownCommand.parse ( tokens )
    }
  }
}