package mdaros.exercises.scala.chapter2.filesystem.command

import org.scalatest.{FlatSpec, Matchers}

class CatCommandSpec extends FlatSpec with Matchers {

  "CatCommand" should "parse successfully 2 tokens having first token 'cat'" in {

    val tokens: Array [String] = Array ( "rm", "myFolder" )

    // Invoke the method under test
    val command: Command = CatCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ CatCommand ] should be ( true )
  }

  "CatCommand" should "parse 1 token 'cat' as MalformedCommand" in {

    val tokens: Array [String] = Array ( "rm" )

    // Invoke the method under test
    val command: Command = CatCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ MalformedCommand ] should be ( true )
  }
}