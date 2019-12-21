package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, Folder}
import org.scalatest.{FlatSpec, Matchers}

class CatCommandSpec extends FlatSpec with Matchers {

  "CatCommand" should "parse successfully 2 tokens having first token 'cat'" in {

    val tokens: Array [String] = Array ( "cat", "myFile" )

    // Invoke the method under test
    val command: Command = CatCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ CatCommand ] should be ( true )
  }

  "CatCommand" should "parse 1 token 'cat' as MalformedCommand" in {

    val tokens: Array [String] = Array ( "cat" )

    // Invoke the method under test
    val command: Command = CatCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ MalformedCommand ] should be ( true )
  }

  /* TODO findFile è stato rimosso e al suo posto si usa il metodo findDescendant della classe Folder, --> Capire se spostare questo test per il metodo findDescendant della classe Folder
  "findFile" should "todo too todo" in {

    val myFile: File = new File ( "/folder1/childFolder1", "myFile" )

    val childFolder1: Folder = new Folder ( "/folder1", "childFolder1", List ( myFile ) )

    val folder1: Folder = new Folder ( "", "folder1", List ( childFolder1 ) )

    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )


    val tokens: Array [String] = Array ( "cat", "myFile" )
    val catCommand: CatCommand = new CatCommand ( tokens )

    // Invoke the method under test
    val file : File = catCommand.findFile ( entityNamesInPath = Array [ String ] ( "folder1", "childFolder1", "myFile" ), currentFolder = rootFolder )

    file should not be ( null )
  }
   */

}