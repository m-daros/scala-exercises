package mdaros.exercises.scala.chapter2.filesystem.command

import org.scalatest.{FlatSpec, Matchers}

class CommandSpec extends FlatSpec with Matchers {

  "Command" should "parse an undefined command line as an UnknownCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "notDefinedCommand" )

    // Assertions
    command.isInstanceOf [ UnknownCommand ] should be ( true )
  }

  "Command" should "parse an empty command line as an EmptyCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "" )

    // Assertions
    command.isInstanceOf [ EmptyCommand ] should be ( true )
  }

  "Command" should "parse a well known command having wrong parameters as a MalformedCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "cd someFolder someOtherFolder" )

    // Assertions
    command.isInstanceOf [ MalformedCommand ] should be ( true )
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

  "Command" should "parse a string having 'echo' as first argument to a EchoCommand" in {

    // Invoke the method under test
    val command1: Command = Command.from ( "echo something" )

    // Assertions
    command1.isInstanceOf [ EchoCommand ] should be ( true )

    val echoCommand1: EchoCommand = command1.asInstanceOf [ EchoCommand ]

    // Assertions on command arguments
    echoCommand1.arguments.length  should be ( 2 )
    echoCommand1.arguments ( 1 )   should be ( "something" )

    // Invoke the method under test
    val command2: Command = Command.from ( "echo something somethingelse" )

    // Assertions
    command2.isInstanceOf [ EchoCommand ] should be ( true )

    val echoCommand2: EchoCommand = command2.asInstanceOf [ EchoCommand ]

    // Assertions on command arguments
    echoCommand2.arguments.length  should be ( 3 )
    echoCommand2.arguments ( 1 )   should be ( "something" )
    echoCommand2.arguments ( 2 )   should be ( "somethingelse" )
  }

  "Command" should "parse a string having 'rm' as first argument to a EchoCommand" in {

    // Invoke the method under test
    val command1: Command = Command.from ( "rm something" )

    // Assertions
    command1.isInstanceOf [ RmCommand ] should be ( true )

    val rmCommand1: RmCommand = command1.asInstanceOf [ RmCommand ]

    // Assertions on command arguments
    rmCommand1.arguments.length  should be ( 2 )
    rmCommand1.arguments ( 1 )   should be ( "something" )

    // Invoke the method under test
    val command2: Command = Command.from ( "rm something somethingelse" )

    // Assertions
    command2.isInstanceOf [ RmCommand ] should be ( true )

    val rmCommand2: RmCommand = command2.asInstanceOf [ RmCommand ]

    // Assertions on command arguments
    rmCommand2.arguments.length  should be ( 3 )
    rmCommand2.arguments ( 1 )   should be ( "something" )
    rmCommand2.arguments ( 2 )   should be ( "somethingelse" )
  }

  "Command" should "parse a string having 'ls' as first argument to a LsCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "ls someFolder" )

    // Assertions
    command.isInstanceOf [ LsCommand ] should be ( true )

    val lsCommand: LsCommand = command.asInstanceOf [ LsCommand ]

    // Assertions on command arguments
    lsCommand.arguments.length  should be ( 2 )
    lsCommand.arguments ( 1 )   should be ( "someFolder" )
  }

  "Command" should "parse a string having 'touch' as first argument to a MkDirCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "touch someFile" )

    // Assertions
    command.isInstanceOf [ TouchCommand ] should be ( true )

    val touchCommand: TouchCommand = command.asInstanceOf [ TouchCommand ]

    // Assertions on command arguments
    touchCommand.arguments.length  should be ( 2 )
    touchCommand.arguments ( 1 )   should be ( "someFile" )
  }

  "Command" should "parse the string 'pwd' to a PwdCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "pwd" )

    // Assertions
    command.isInstanceOf [ PwdCommand ] should be ( true )

    val pwdCommand: PwdCommand = command.asInstanceOf [ PwdCommand ]

    // Assertions on command arguments
    pwdCommand.arguments.length  should be ( 1 )
  }

  "Command" should "parse the string 'cat' to a CatCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "cat someFile" )

    // Assertions
    command.isInstanceOf [ CatCommand ] should be ( true )

    val catCommand: CatCommand = command.asInstanceOf [ CatCommand ]

    // Assertions on command arguments
    catCommand.arguments.length  should be ( 2 )
    catCommand.arguments ( 1 )   should be ( "someFile" )
  }
}