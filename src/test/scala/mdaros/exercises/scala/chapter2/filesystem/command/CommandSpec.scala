package mdaros.exercises.scala.chapter2.filesystem.command

import org.scalatest.{FlatSpec, Matchers}

class CommandSpec extends FlatSpec with Matchers {

  "Command" should "parse an undefined command line as UnknownCommand" in {

    // Invoke the method under test
    val command: Command = Command.from ( "notDefinedCommand" )

    // Assertions
    command.isInstanceOf [ UnknownCommand ] should be ( true )
  }
}