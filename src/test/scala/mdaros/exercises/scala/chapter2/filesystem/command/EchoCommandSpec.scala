package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.Folder
import mdaros.exercises.scala.chapter2.filesystem.state.State
import org.scalatest.{FlatSpec, Matchers}

class EchoCommandSpec extends FlatSpec with Matchers {

  "EchoCommand" should "parse successfully 2 tokens having first token 'echo'" in {

    val tokens: Array [String] = Array ( "echo", "something" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ EchoCommand ] should be ( true )
  }

  "EchoCommand" should "parse 1 single 'echo' token as MalformedCommand" in {

    val tokens: Array [String] = Array ( "echo" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ MalformedCommand ] should be ( true )
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

  "EchoCommand.apply ()" should "echo to stdout when invoked with 2 arguments" in {

    val tokens: Array [String] = Array ( "echo", "something" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    val rootFolder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )

    val state: State = command.apply ( new State ( rootFolder, rootFolder, "" ) )

    // Assertions
    state.commandOutput should be ( "something" )
    state.rootFolder    should be ( rootFolder )
    state.workingFolder should be ( rootFolder )
  }
}