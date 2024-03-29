package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State
import org.scalatest.{FlatSpec, Matchers}

class EchoCommandSpec extends FlatSpec with Matchers {

  "EchoCommand" should "parse successfully 2 tokens having first token 'echo'" in {

    val tokens: Array [String] = Array ( "echo", "something" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ EchoCommand ] should be ( true )
  }

  "EchoCommand" should "parse 1 single 'echo' token as MalformedCommand" in {

    val tokens: Array [String] = Array ( "echo" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    // Assertions
    command.isInstanceOf [ MalformedCommand ] should be ( true )
  }

  "EchoCommand.apply ()" should "echo to stdout when invoked with 2 arguments" in {

    val tokens: Array [String] = Array ( "echo", "something" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    val rootFolder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )

    val state: State = command.apply ( new State ( rootFolder, rootFolder, "" ) )

    // Assertions
    state.commandOutput should be ( "something" )
    state.rootFolder    should be ( rootFolder )
    state.workingFolder should be ( rootFolder )
  }

  "EchoCommand.apply ()" should "echo to stdout when invoked with more than 2 arguments without '>' or '>>' operator" in {

    val tokens: Array [String] = Array ( "echo", "something", "somethingelse" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    val rootFolder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )

    val state: State = command.apply ( new State ( rootFolder, rootFolder, "" ) )

    // Assertions
    state.commandOutput should be ( "something somethingelse" )
    state.rootFolder    should be ( rootFolder )
    state.workingFolder should be ( rootFolder )
  }

  "EchoCommand.apply ()" should "echo to the file having name equals the last argument when invoked with more than 2 arguments having '>' before the last argument" in {

    val tokens: Array [String] = Array ( "echo", "something", "somethingelse", ">", "someFile" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    val folder1 = new Folder ( Folder.ROOT_PARENT_PATH, "folder1" )
    val rootFolder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val state: State = command.apply ( new State ( rootFolder, folder1, "" ) )

    // Assertions
    state.commandOutput           should be ( "" )
    state.rootFolder.name         should be ( Folder.ROOT_NAME )
    state.rootFolder.parentPath   should be ( Folder.ROOT_PARENT_PATH )

    val children: List [ FileSystemEntity ] = state.workingFolder.children

    // Assertions on children
    children.length should be ( 1 )

    children ( 0 ).isInstanceOf [ File ] should be ( true )

    val file: File = children ( 0 ).asInstanceOf [ File ]

    file.contents  should be ( "something somethingelse" )
  }

  "EchoCommand.apply ()" should "echo to the file having name equals the last argument when invoked with more than 2 arguments having '>>' before the last argument" in {

    val tokens: Array [String] = Array ( "echo", "something", "somethingelse", ">>", "someFile" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    val folder1 = new Folder ( Folder.ROOT_PARENT_PATH, "folder1" )
    val rootFolder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val state: State = command.apply ( new State ( rootFolder, folder1, "" ) )

    // Assertions
    state.commandOutput           should be ( "" )
    state.rootFolder.name         should be ( Folder.ROOT_NAME )
    state.rootFolder.parentPath   should be ( Folder.ROOT_PARENT_PATH )

    val children: List [ FileSystemEntity ] = state.workingFolder.children

    // Assertions on children
    children.length should be ( 1 )

    children ( 0 ).isInstanceOf [ File ] should be ( true )

    val file: File = children ( 0 ).asInstanceOf [ File ]

    file.contents  should be ( "something somethingelse" )
  }

  "EchoCommand.apply ()" should "echo to the file (existing file) having name equals the last argument when invoked with more than 2 arguments having '>>' before the last argument" in {

    val tokens: Array [String] = Array ( "echo", "something", "somethingelse", ">>", "someFile" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    val someFile = new File ( Folder.ROOT_PARENT_PATH + FileSystemEntity.PATH_SEPARATOR + "folder1", "someFile", "first row" )
    val folder1 = new Folder ( Folder.ROOT_PARENT_PATH, "folder1", List ( someFile ) )
    val rootFolder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val state: State = command.apply ( new State ( rootFolder, folder1, "" ) )

    // Assertions
    state.commandOutput           should be ( "" )
    state.rootFolder.name         should be ( Folder.ROOT_NAME )
    state.rootFolder.parentPath   should be ( Folder.ROOT_PARENT_PATH )

    val children: List [ FileSystemEntity ] = state.workingFolder.children

    // Assertions on children
    children.length should be ( 1 )

    children ( 0 ).isInstanceOf [ File ] should be ( true )

    val file: File = children ( 0 ).asInstanceOf [ File ]

    file.contents  should be ( "first row\nsomething somethingelse" )
  }

  "EchoCommand.apply ()" should "throw an eception if we try to redirect the output to a Folder" in {

    val tokens: Array [String] = Array ( "echo", "something", "somethingelse", ">>", "folder2" )

    // Invoke the method under test
    val command: Command = EchoCommand.parse ( tokens )

    val folder2 = new Folder ( Folder.ROOT_PARENT_PATH + FileSystemEntity.PATH_SEPARATOR + "folder1", "folder2" )
    val folder1 = new Folder ( Folder.ROOT_PARENT_PATH, "folder1", List ( folder2 ) )
    val rootFolder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val exception = intercept [ Exception ] {

      // Invoke the method expecting an exception
      command.apply ( new State ( rootFolder, folder1, "" ) )
    }

    // Assertions
    exception.isInstanceOf [ MyFileSystemException ] should be ( true )

    val myFileSystemException = exception.asInstanceOf [ MyFileSystemException ]

    myFileSystemException.message.contains ( "/folder1/folder2" )   should be ( true )
  }

  "EchoCommand.apply ()" should "on ROOT folder as working folder, echo to the file having name equals the last argument when invoked with more than 2 arguments having '>>' before the last argument" in {

    val tokens: Array [String] = Array ( "echo", "something", "somethingelse", ">", "someFile" )

    val command: Command = EchoCommand.parse ( tokens )

    val rootFolder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )

    // Invoke the method under test
    val state: State = command.apply ( new State ( rootFolder, rootFolder, "" ) )

    // Assertions
    state.commandOutput           should be ( "" )
    state.rootFolder.name         should be ( Folder.ROOT_NAME )
    state.rootFolder.parentPath   should be ( Folder.ROOT_PARENT_PATH )

    val children: List [ FileSystemEntity ] = state.workingFolder.children

    // Assertions on children
    children.length should be ( 1 )

    children ( 0 ).isInstanceOf [ File ] should be ( true )

    val file: File = children ( 0 ).asInstanceOf [ File ]

    file.contents  should be ( "something somethingelse" )
  }

  "echo something somethingelse > folder2/file3" should " write something somethingelse content on file /folder1/folder2/file3" in {

    val tokens: Array [String] = Array ( "echo", "something", "somethingelse", ">", "folder2/file3" )

    val folder2: Folder = new Folder ( "/folder1", "folder2" )
    val folder1: Folder = new Folder ( "/", "folder1", List ( folder2 ) )
    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val command: Command = EchoCommand.parse ( tokens )

    // Invoke the method under test
    val state: State = command.apply ( new State ( rootFolder, folder1, "" ) )

    state.workingFolder.children.size should be ( 1 )
    state.workingFolder.children.head.getType () should be ( "Folder" ) // TODO Usare costante per tipo Folder

    val folder2FromState: Folder = state.workingFolder.children.head.asFolder ()

    folder2FromState.children.size should be ( 1 )
    folder2FromState.children.head.getType () should be ( "File" ) // TODO Usare costante per tipo File

    val file3: File = folder2FromState.children.head.asFile ()

    file3.contents should be ( "something somethingelse" )
  }
}