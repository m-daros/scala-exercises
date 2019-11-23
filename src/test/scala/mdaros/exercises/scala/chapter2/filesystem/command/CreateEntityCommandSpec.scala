package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, Folder}
import mdaros.exercises.scala.chapter2.filesystem.state.State
import org.scalatest.{FlatSpec, Matchers}

class CreateEntityCommandSpec extends FlatSpec with Matchers {

  "apply" should "create a Folder inside the ROOT folder having the given name" in {

    val tokens: Array [String] = Array ( "mkdir", "myFolder" )

    val command: MkDirCommand = new MkDirCommand ( tokens )

    val rootFolder = Folder.empty ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )
    val initialState = State ( rootFolder, rootFolder, "" )

    // Invoke the method under test
    val newState: State = command.apply ( initialState )

    // Assertions
    newState.workingFolder.parentPath should be ( "" )
    newState.workingFolder.children.size should be ( 1 )
    newState.workingFolder.name should be ( Folder.ROOT_NAME )

    newState.rootFolder.parentPath should be ( "" )
    newState.rootFolder.children.size should be ( 1 )
    newState.rootFolder.name should be ( Folder.ROOT_NAME )

    val createdEntity = newState.workingFolder.children ( 0 )

    createdEntity.name should be ( "myFolder" )
    createdEntity.parentPath should be ( "/" )

    val createdFolder: Folder = createdEntity.asFolder ()

    createdFolder.children.size should be ( 0 )
  }

  "apply" should "create a File inside the ROOT folder having the given name" in {

    val tokens: Array [String] = Array ( "touch", "myFolder" )

    val command: TouchCommand = new TouchCommand ( tokens )

    val rootFolder = Folder.empty ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )
    val initialState = State ( rootFolder, rootFolder, "" )

    // Invoke the method under test
    val newState: State = command.apply ( initialState )

    // Assertions
    newState.workingFolder.parentPath should be ( "" )
    newState.workingFolder.children.size should be ( 1 )
    newState.workingFolder.name should be ( Folder.ROOT_NAME )

    newState.rootFolder.parentPath should be ( "" )
    newState.rootFolder.children.size should be ( 1 )
    newState.rootFolder.name should be ( Folder.ROOT_NAME )

    val createdEntity = newState.workingFolder.children ( 0 )

    createdEntity.name should be ( "myFolder" )
    createdEntity.parentPath should be ( "/" )

    val createdFile: File = createdEntity.asFile ()
  }

  "apply" should "create a Folder inside an arbitrary folder having the given name" in {

    val tokens: Array [String] = Array ( "mkdir", "myFolder" )

    val command: MkDirCommand = new MkDirCommand ( tokens )

    val workingFolder = Folder.empty ( "", "childFolder" )
    val rootFolder = new Folder ( "", "", List ( workingFolder ) )
    val initialState = State ( rootFolder, workingFolder, "" )

    // Invoke the method under test
    val newState: State = command.apply ( initialState )

    // Assertions
    newState.workingFolder.parentPath should be ( "" )
    newState.workingFolder.children.size should be ( 1 )
    newState.workingFolder.name should be ( "childFolder" )

    newState.rootFolder.parentPath should be ( "" )
    newState.rootFolder.children.size should be ( 1 )
    newState.rootFolder.name should be ( "" )

    val createdEntity = newState.workingFolder.children ( 0 )

    createdEntity.name should be ( "myFolder" )
    createdEntity.parentPath should be ( "/childFolder" )

    val createdFolder: Folder = createdEntity.asFolder ()

    createdFolder.children.size should be ( 0 )
  }
}