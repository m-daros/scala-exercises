package mdaros.exercises.scala.chapter2.filesystem.command

import org.scalatest.{FlatSpec, Matchers}

class CommandSpec extends FlatSpec with Matchers {

  "Command" should "parse an undefined command line as UnknownCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "notDefinedCommand" )

    // Assertions
    command.isInstanceOf [ UnknownCommand ] should be ( true )
  }

  "Command" should "parse a string having 'cd' as first argument to a CdCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "cd something" )

    // Assertions
    command.isInstanceOf [ CdCommand ] should be ( true )

    val cdCommand: CdCommand = command.asInstanceOf [ CdCommand ]

    // Assertions on command arguments
    cdCommand.arguments.length  should be ( 2 )
    cdCommand.arguments ( 1 )   should be ( "something" )
  }

  "Command" should "parse a string having 'mkdir' as first argument to a MkDirCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "mkdir someFolder" )

    // Assertions
    command.isInstanceOf [ MkDirCommand ] should be ( true )

    val mkDirCommand: MkDirCommand = command.asInstanceOf [ MkDirCommand ]

    // Assertions on command arguments
    mkDirCommand.arguments.length  should be ( 2 )
    mkDirCommand.arguments ( 1 )   should be ( "someFolder" )
  }
}