package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.Folder
import org.scalatest.{FlatSpec, Matchers}

class MkDirCommandSpec extends FlatSpec with Matchers {

  "MkDirCommand" should "parse successfully 2 tokens having first token 'mkdir'" in {

    val tokens: Array [String] = Array ( "mkdir", "myFolder" )

    // Invoke the method under test
    val command: Command = MkDirCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ MkDirCommand ] should be ( true )
  }

  "MkDirCommand" should "parse successfully 3 tokens having first token 'mkdir'" in {

    val tokens: Array [String] = Array ( "mkdir", "myFolder1", "myFolder2" )

    // Invoke the method under test
    val command: Command = MkDirCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ MkDirCommand ] should be ( true )
  }

  "MkDirCommand" should "parse 1 single 'mkdir' token as MalformedCommand" in {

    val tokens: Array [String] = Array ( "mkdir" )

    // Invoke the method under test
    val command: Command = MkDirCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ MalformedCommand ] should be ( true )
  }

  "MkDirCommand.createEntity ()" should "create an empty Folder" in {

    val tokens: Array [String] = Array ( "mkdir", "myFolder" )

    // Invoke the method under test
    val command: MkDirCommand = new MkDirCommand ( tokens )

    val folder: Folder = command.createEntity ( "/folder1", "myFolder" ).asInstanceOf [ Folder ]

    // Assertions
    folder.name             should be ( "myFolder" )
    folder.parentPath       should be ( "/folder1" )
    folder.children.isEmpty should be ( true )
  }
}