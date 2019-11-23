package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{FileSystemEntity, Folder}
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

  "getDestinationFolderPath" should "remove instances of . into the given path" in {

    val tokens: Array [String] = Array ( "cd", "/folder1/./folder2/./folder3" )

    // Invoke the method under test
    val cdCommand: CdCommand = new CdCommand ( tokens )

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder" )

    val destinationFolderPath: String = cdCommand.getDestinationFolderPath ( "/folder1/./folder2/./folder3", folder )

    // Assertions
    destinationFolderPath should be ( "/folder1/folder2/folder3" )
  }

  "getDestinationFolderPath ( '/folder1/./folder2/./folder3/..' )" should " be /folder1/folder2" in {

    val tokens: Array [String] = Array ( "cd", "/folder1/./folder2/./folder3/.." )

    // Invoke the method under test
    val cdCommand: CdCommand = new CdCommand ( tokens )

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder" )

    val destinationFolderPath: String = cdCommand.getDestinationFolderPath ( "/folder1/./folder2/./folder3/..", folder )

    // Assertions
    destinationFolderPath should be ( "/folder1/folder2" )
  }

  /* FIXME
  "filterDotDot ( 'folder1/../folder2' )" should "return folder2" in {

    val tokens: Array [String] = Array ( "cd", "folder1/../folder2" )

    val cdCommand: CdCommand = new CdCommand ( tokens )

    // Invoke the method under test
    val filteredTokens: Array [String] = cdCommand.filterDotDot ( "folder1/../folder2".split ( FileSystemEntity.PATH_SEPARATOR ), new Array [String] ( 0 ) )

    println ( filteredTokens.mkString ( FileSystemEntity.PATH_SEPARATOR ) );

    filteredTokens.length should be ( 1 )

    filteredTokens.head should be ( "folder2" )
  }
   */

  /* FIXME
  "filterDotDot ( 'folder1/../folder2/folder3' )" should "return folder2/folder3" in {

    val tokens: Array [String] = Array ( "cd", "folder1/../folder2/folder3" )

    val cdCommand: CdCommand = new CdCommand ( tokens )

    // Invoke the method under test
    val filteredTokens: Array [String] = cdCommand.filterDotDot ( "folder1/../folder2/folder3".split ( FileSystemEntity.PATH_SEPARATOR ), new Array [String] ( 0 ) )

    println ( filteredTokens.mkString ( FileSystemEntity.PATH_SEPARATOR ) );

    filteredTokens.length should be ( 2 )

    filteredTokens ( 0 ) should be ( "folder2" )
    filteredTokens ( 1 ) should be ( "folder3" )
  }
   */
}