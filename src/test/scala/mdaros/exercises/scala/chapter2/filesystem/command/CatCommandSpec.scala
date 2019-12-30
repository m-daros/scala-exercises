package mdaros.exercises.scala.chapter2.filesystem.command

import java.io.FileNotFoundException

import mdaros.exercises.scala.chapter2.filesystem.model.{File, FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State
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

  "cat folder2/file3" should "output the content the child folder2/file3 of the working folder" in {

    val tokens: Array [String] = Array ( "cat", "folder2/file3" )

    val file3: File = new File ( "/folder1/folder2", "file3", "Content of file3" )
    val folder2: Folder = new Folder ( "/folder1", "folder2", List ( file3 ) )
    val folder1: Folder = new Folder ( "/", "folder1", List ( folder2 ) )
    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val command: Command = CatCommand.parse ( tokens )

    // Invoke the method under test
    val state: State = command.apply ( new State ( rootFolder, folder1, "" ) )

    state.workingFolder.children.size should be ( 1 )
    state.workingFolder.children.head.getType () should be ( "Folder" ) // TODO Usare costante per tipo Folder

    val folder2FromState: Folder = state.workingFolder.children.head.asFolder ()

    folder2FromState.children.size should be ( 1 )
    folder2FromState.children.head.getType () should be ( "File" ) // TODO Usare costante per tipo File

    val foundFile3: File = folder2FromState.children.head.asFile ()

    foundFile3.contents should be ( "Content of file3" )
  }

  "cat /folder1/folder2/file3" should " output the content of file /folder1/folder2/file3 of the working folder" in {

    val tokens: Array [String] = Array ( "cat", "/folder1/folder2/file3" )

    val file3: File = new File ( "/folder1/folder2", "file3", "Content of file3" )
    val folder2: Folder = new Folder ( "/folder1", "folder2", List ( file3 ) )
    val folder1: Folder = new Folder ( "/", "folder1", List ( folder2 ) )
    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val command: Command = CatCommand.parse ( tokens )

    // Invoke the method under test
    val state: State = command.apply ( new State ( rootFolder, folder1, "" ) )

    state.workingFolder.children.size should be ( 1 )
    state.workingFolder.children.head.getType () should be ( "Folder" ) // TODO Usare costante per tipo Folder

    val folder2FromState: Folder = state.workingFolder.children.head.asFolder ()

    folder2FromState.children.size should be ( 1 )
    folder2FromState.children.head.getType () should be ( "File" ) // TODO Usare costante per tipo File

    val foundFile3: File = folder2FromState.children.head.asFile ()

    foundFile3.contents should be ( "Content of file3" )
  }

  "cat /folder1/folder2/unknownFile" should " output a FileNotFound error message" in {

    val tokens: Array [String] = Array ( "cat", "/folder1/folder2/unknownFile" )

    val file3: File = new File ( "/folder1/folder2", "file3", "Content of file3" )
    val folder2: Folder = new Folder ( "/folder1", "folder2", List ( file3 ) )
    val folder1: Folder = new Folder ( "/", "folder1", List ( folder2 ) )
    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val command: Command = CatCommand.parse ( tokens )

    // Invoke the method under test
    val state: State = command.apply ( new State ( rootFolder, folder1, "" ) )

    state.workingFolder.children.size should be ( 1 )
    state.workingFolder.children.head.getType () should be ( "Folder" ) // TODO Usare costante per tipo Folder

    val folder2FromState: Folder = state.workingFolder.children.head.asFolder ()

    folder2FromState.children.size should be ( 1 )
    folder2FromState.children.head.getType () should be ( "File" ) // TODO Usare costante per tipo File

    val foundFile3: File = folder2FromState.children.head.asFile ()

    state.commandOutput should be ( "Impossibile trovare il file /folder1/folder2/unknownFile" )
  }

  "cat file1 file2 > file3" should " write the contents of file1 and file2 inside file3 separated by \n" in {

    val tokens: Array [String] = Array ( "cat", "file1", "file2", ">", "file3" )

    val file1: File = new File ( "/folder1/folder2", "file1", "Content of file1" )
    val file2: File = new File ( "/folder1/folder2", "file2", "Content of file2" )
    val folder2: Folder = new Folder ( "/folder1", "folder2", List ( file1, file2 ) )
    val folder1: Folder = new Folder ( "/", "folder1", List ( folder2 ) )
    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val command: Command = CatCommand.parse ( tokens )

    // Invoke the method under test
    val state: State = command.apply ( new State ( rootFolder, folder2, "" ) )

    state.workingFolder.children.size should be ( 3 )

    val child1: FileSystemEntity = state.workingFolder.children ( 0 )
    val child2: FileSystemEntity = state.workingFolder.children ( 1 )
    val child3: FileSystemEntity = state.workingFolder.children ( 2 )

    child1.getType () should be ( "File" ) // TODO Usare costante per tipo Folder
    child1.name should be ( "file1" )

    child2.getType () should be ( "File" ) // TODO Usare costante per tipo Folder
    child2.name should be ( "file2" )

    child3.getType () should be ( "File" ) // TODO Usare costante per tipo Folder
    child3.name should be ( "file3" )

    val file3 = child3.asFile ()

    file3.contents should be ( "Content of file1\nContent of file2" )
  }

  "cat file1 file2 >> file3" should " append the contents of file1 and file2 to file3 separated by \n" in {

    val tokens: Array [String] = Array ( "cat", "file1", "file2", ">>", "file3" )

    val file1: File = new File ( "/folder1/folder2", "file1", "Content of file1" )
    val file2: File = new File ( "/folder1/folder2", "file2", "Content of file2" )
    val file3: File = new File ( "/folder1/folder2", "file3", "Content of file3" )
    val folder2: Folder = new Folder ( "/folder1", "folder2", List ( file1, file2, file3 ) )
    val folder1: Folder = new Folder ( "/", "folder1", List ( folder2 ) )
    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val command: Command = CatCommand.parse ( tokens )

    // Invoke the method under test
    val state: State = command.apply ( new State ( rootFolder, folder2, "" ) )

    state.workingFolder.children.size should be ( 3 )

    val child1: FileSystemEntity = state.workingFolder.children ( 0 )
    val child2: FileSystemEntity = state.workingFolder.children ( 1 )
    val child3: FileSystemEntity = state.workingFolder.children ( 2 )

    child1.getType () should be ( "File" ) // TODO Usare costante per tipo Folder
    child1.name should be ( "file1" )

    child2.getType () should be ( "File" ) // TODO Usare costante per tipo Folder
    child2.name should be ( "file2" )

    child3.getType () should be ( "File" ) // TODO Usare costante per tipo Folder
    child3.name should be ( "file3" )

    val foundFile3 = child3.asFile ()

    foundFile3.contents should be ( "Content of file3\nContent of file1\nContent of file2" )
  }

  "cat file1 file2" should "output the content the children file1 and file2 of the working folder" in {

    val tokens: Array [String] = Array ( "cat", "file1", "file2" )

    val file1: File = new File ( "/folder1/folder2", "file1", "Content of file1" )
    val file2: File = new File ( "/folder1/folder2", "file2", "Content of file2" )
    val folder2: Folder = new Folder ( "/folder1", "folder2", List ( file1, file2 ) )
    val folder1: Folder = new Folder ( "/", "folder1", List ( folder2 ) )
    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val command: Command = CatCommand.parse ( tokens )

    // Invoke the method under test
    val state: State = command.apply ( new State ( rootFolder, folder2, "" ) )

    state.workingFolder.children.size should be ( 2 )
    state.workingFolder.children ( 0 ).getType () should be ( "File" ) // TODO Usare costante per tipo File
    state.workingFolder.children ( 1 ).getType () should be ( "File" ) // TODO Usare costante per tipo File

    state.commandOutput should be ( "Content of file1\nContent of file2" )
  }

  "cat folder1/file1" should "tell taht folder1/file1 doesn't exist" in {

    val tokens: Array [String] = Array ( "cat", "folder1/file1" )

    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )

    val command: Command = CatCommand.parse ( tokens )

    // Invoke the method expecting an exception
    val newState: State = command.apply ( new State ( rootFolder, rootFolder, "" ) )

    // Assertions
    newState.commandOutput should be ( "Impossibile trovare il file folder1/file1" )
  }

  "cat file1 file2" should "tell that file1 doesn't exist" in {

    val tokens: Array [String] = Array ( "cat", "file1", "file2" )

    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )

    val command: Command = CatCommand.parse ( tokens )

    // Invoke the method expecting an exception
    val newState: State = command.apply ( new State ( rootFolder, rootFolder, "" ) )

    // Assertions
    newState.commandOutput should be ( "Impossibile trovare il file file1" )
  }

  "cat folder1 folder2" should "tell that folder1 is not a File" in {

    val tokens: Array [String] = Array ( "cat", "folder1", "folder2" )

    val folder1: Folder = new Folder ( "/", "folder1" )
    val folder2: Folder = new Folder ( "/", "folder2" )
    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1, folder2 ) )

    val command: Command = CatCommand.parse ( tokens )

    // Invoke the method under test
    val state: State = command.apply ( new State ( rootFolder, rootFolder, "" ) )

    // Assertions
    state.commandOutput should be ( "Impossibile recuperare il contenuto di folder1 perchè non è un File" )
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