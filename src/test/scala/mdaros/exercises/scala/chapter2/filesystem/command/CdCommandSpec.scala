package mdaros.exercises.scala.chapter2.filesystem.command

import org.scalatest.{FlatSpec, Matchers}

class CdCommandSpec extends FlatSpec with Matchers {

  "CdCommand" should "parse successfully 2 tokens having first token 'cd'" in {

    val tokens: Array [String] = Array ( "cd", "myFolder" )

    // Invoke the method under test
    val command: Command = CdCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ CdCommand ] should be ( true )
  }

  "CdCommand" should "parse 1 single 'cd' token as MalformedCommand" in {

    val tokens: Array [String] = Array ( "cd" )

    // Invoke the method under test
    val command: Command = CdCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ MalformedCommand ] should be ( true )
  }
}