package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.state.State

class EchoCommand ( val arguments: Array [String] ) extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    EchoCommand.parse ( tokens )
  }

  override def apply ( state: State ): State = {

    if ( arguments.length == 2 ) {

      new State ( state.rootFolder, state.workingFolder, arguments ( 1 ) )
    }
    else {

      ??? // TODO ...
    }
    /*
  - arguments > 2
    - arguments ( length - 2 ) = ">"
      -> echo to the file arguments ( length - 1 ) overwriting file

    - arguments ( length - 2 ) = ">>"
      -> echo to the file arguments ( length - 1 ) appending to file

    - arguments ( length - 2 ) != = ">" && != ">>"
      -> echo all arguments
 */
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