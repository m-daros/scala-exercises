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

  "filterDotDot ( '/folder1/../folder2' )" should "return folder2" in {

    val tokens: Array [String] = Array ( "cd", "/folder1/../folder2" )

    val cdCommand: CdCommand = new CdCommand ( tokens )

    // Invoke the method under test
    val filteredTokens: Array [String] = cdCommand.filterDotDot ( "/folder1/../folder2".split ( FileSystemEntity.PATH_SEPARATOR ), new Array [String] ( 0 ) )

    println ( filteredTokens.mkString ( FileSystemEntity.PATH_SEPARATOR ) );

    filteredTokens.length should be ( 2 )

    filteredTokens ( 0 ) should be ( "" )
    filteredTokens ( 1 ) should be ( "folder2" )
  }

  "filterDotDot ( '/folder1/../folder2/folder3' )" should "return folder2/folder3" in {

    val tokens: Array [String] = Array ( "cd", "/folder1/../folder2/folder3" )

    val cdCommand: CdCommand = new CdCommand ( tokens )

    // Invoke the method under test
    val filteredTokens: Array [String] = cdCommand.filterDotDot ( "/folder1/../folder2/folder3".split ( FileSystemEntity.PATH_SEPARATOR ), new Array [String] ( 0 ) )

    println ( filteredTokens.mkString ( FileSystemEntity.PATH_SEPARATOR ) );

    filteredTokens.length should be ( 3 )

    filteredTokens ( 0 ) should be ( "" )
    filteredTokens ( 1 ) should be ( "folder2" )
    filteredTokens ( 2 ) should be ( "folder3" )
  }

  "findFolder " should "should find an existing folder" in {

    val childFolder1: Folder = new Folder ( "/folder1", "childFolder1" )
    val childFolder2: Folder = new Folder ( "/folder1", "childFolder2" )

    val folder1: Folder = new Folder ( "", "folder1", List ( childFolder1, childFolder2 ) )

    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val folderNamesInPath: Array [String] = Array ( "folder1", "childFolder1" )

    val cdCommand: CdCommand = new CdCommand ( "cd /folder1/childFolder1".split ( FileSystemEntity.PATH_SEPARATOR ) )

    // Invoke the method under test
    val foundChildFolder1: Folder = cdCommand.findFolder ( Array ( "folder1", "childFolder1" ), rootFolder )
    val foundChildFolder2: Folder = cdCommand.findFolder ( Array ( "folder1", "childFolder2" ), rootFolder )

    foundChildFolder1 should not be ( null )
    foundChildFolder1.name should be ( "childFolder1" )
    foundChildFolder1.parentPath should be ( "/folder1" )
    foundChildFolder1 should be ( childFolder1 )

    foundChildFolder2 should not be ( null )
    foundChildFolder2.name should be ( "childFolder2" )
    foundChildFolder2.parentPath should be ( "/folder1" )
    foundChildFolder2 should be ( childFolder2 )
  }

  "findFolder " should "should return null in case it's unable to find the required folder" in {

    val childFolder1: Folder = new Folder ( "/folder1", "childFolder1" )
    val childFolder2: Folder = new Folder ( "/folder1", "childFolder2" )

    val folder1: Folder = new Folder ( "", "folder1", List ( childFolder1, childFolder2 ) )

    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val folderNamesInPath: Array [String] = Array ( "folder1", "childFolder1" )

    val cdCommand: CdCommand = new CdCommand ( "cd /folder1/childFolder1".split ( FileSystemEntity.PATH_SEPARATOR ) )

    // Invoke the method under test
    val notFoundFolder: Folder = cdCommand.findFolder ( Array ( "folder1", "unknownChildFolder" ), rootFolder )
    val notFoundFolder2: Folder = cdCommand.findFolder ( Array ( "folder1", "unknownChildFolder2" ), rootFolder )

    // Assertions
    notFoundFolder should be ( null )
    notFoundFolder2 should be ( null )
  }
}