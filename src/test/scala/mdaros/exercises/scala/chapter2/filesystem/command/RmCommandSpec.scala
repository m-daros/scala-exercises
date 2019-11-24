package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{FileSystemEntity, Folder}
import org.scalatest.{FlatSpec, Matchers}

class RmCommandSpec extends FlatSpec with Matchers {

  "RmCommand" should "parse successfully 2 tokens having first token 'rm'" in {

    val tokens: Array [String] = Array ( "rm", "myFolder" )

    // Invoke the method under test
    val command: Command = RmCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ RmCommand ] should be ( true )
  }

  "RmCommand" should "parse 1 single 'rm' token as MalformedCommand" in {

    val tokens: Array [String] = Array ( "rm" )

    // Invoke the method under test
    val command: Command = RmCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ MalformedCommand ] should be ( true )
  }
}