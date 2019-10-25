package mdaros.exercises.scala.chapter2.filesystem.model

import mdaros.exercises.scala.chapter2.filesystem.command.{Command, MkDirCommand}
import org.scalatest.exceptions.TestFailedException
import org.scalatest.{FlatSpec, Matchers}

class FolderSpec extends FlatSpec with Matchers {

  "isRoot ()" should "return true or false based on the fact that the Folder is the ROOT" in {

    val nonRootFolder: Folder = new Folder ( "/folder1/folder2", "myFolder" )
    val rootFolder: Folder = new Folder ( "", "" )

    // Invoke and assertions
    nonRootFolder.isRoot () should be ( false )

    // Invoke and assertions
    rootFolder.isRoot () should be ( true )
  }

  "getType ()" should "return 'Folder'" in {

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder" )

    // Invoke and assertions
    folder.getType () should be ( "Folder" )
  }

  "asFolder ()" should "return the object itself" in {

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder" )

    // Invoke and assertions
    folder.asFolder ().hashCode () should be ( folder.hashCode () )
  }

  "asFile ()" should "thrown MyFileSystemException" in {

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder" )

    val exception = intercept [ Exception ] {

      // Invoke the method expecting an exception
      folder.asFile ()
    }

    // Assertions
    exception.isInstanceOf [ MyFileSystemException ] should be ( true )

    val myFileSystemException = exception.asInstanceOf [ MyFileSystemException ]

    myFileSystemException.message.contains ( "Folders cannot be converted to File" )  should be ( true )
    myFileSystemException.message.contains ( "/folder1/folder2" )                     should be ( true )
  }

  "hasEntity ( name )" should "return true if the Folder has ha child with the given name" in {

    val child1: Folder = new Folder ( "/folder1/folder2/myFolder", "child1" )
    val child2: Folder = new Folder ( "/folder1/folder2/myFolder", "child2" )

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder", List ( child1, child2 ) )

    // Invoke and assertions
    folder.hasEntity  ( "child1" ) should be ( true )
    folder.hasEntity  ( "child2" ) should be ( true )
    folder.hasEntity  ( "child3" ) should be ( false )
    folder.hasEntity  ( "child" ) should be ( false )
  }

  "addEntity ( entity )" should "add an entity with the given name if it doesn't exist" in {

    val child1: Folder = new Folder ( "/folder1/folder2/myFolder", "child1" )
    val child2: Folder = new Folder ( "/folder1/folder2/myFolder", "child2" )

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder", List ( child1, child2 ) )

    val child3: Folder = new Folder ( "/folder1/folder2/myFolder", "child3" )

    // Invoke and assertions
    val newFolder: Folder = folder.addEntity ( child3 )

    newFolder.children.contains ( child1 ) should be ( true )
    newFolder.children.contains ( child2 ) should be ( true )
    newFolder.children.contains ( child3 ) should be ( true )
  }

  "addEntity ( entity )" should "throws MyFileSystemException if an entity with the given name already exists" in {

    val child1: Folder = new Folder ( "/folder1/folder2/myFolder", "child1" )
    val child2: Folder = new Folder ( "/folder1/folder2/myFolder", "child2" )

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder", List ( child1, child2 ) )

    val exception = intercept [ Exception ] {

      // Invoke the method expecting an exception
      folder.addEntity ( child2 )
    }

    // Assertions
    exception.isInstanceOf [ MyFileSystemException ] should be ( true )

    val myFileSystemException = exception.asInstanceOf [ MyFileSystemException ]

    myFileSystemException.message.contains ( "already contains an entity" )  should be ( true )
    myFileSystemException.message.contains ( folder.parentPath )             should be ( true )
    myFileSystemException.message.contains ( "child2" )                      should be ( true )
  }

  "findEntity ( name )" should "should return the child having the given name if it exists" in {

    val childFolder1: Folder = new Folder ( "/folder1/folder2/myFolder", "child1" )
    val childFolder2: Folder = new Folder ( "/folder1/folder2/myFolder", "child2" )
    val childFile3: File = new File ( "/folder1/folder2/myFolder", "child3" )

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder", List ( childFolder1, childFolder2, childFile3 ) )

    // Invoke method under test
    val foundChild1: Folder = folder.findEntity ( "child1" ).asFolder ()
    val foundChild2: Folder = folder.findEntity ( "child2" ).asFolder ()
    val foundChild3: File   = folder.findEntity ( "child3" ).asFile ()

    // Assertions
    foundChild1.parentPath  should be ( "/folder1/folder2/myFolder" )
    foundChild1.name        should be ( "child1" )
    foundChild1.hashCode () should be ( childFolder1.hashCode () )

    foundChild2.parentPath  should be ( "/folder1/folder2/myFolder" )
    foundChild2.name        should be ( "child2" )
    foundChild2.hashCode () should be ( childFolder2.hashCode () )

    foundChild3.parentPath  should be ( "/folder1/folder2/myFolder" )
    foundChild3.name        should be ( "child3" )
    foundChild3.hashCode () should be ( childFile3.hashCode () )
  }

  "findEntity ( name )" should "should return null if doesn't exist a child having the given name " in {

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder" )

    // Invoke method under test
    val foundChild1: FileSystemEntity = folder.findEntity ( "child1" )
    val foundChild2: FileSystemEntity = folder.findEntity ( "child2" )
    val foundChild3: FileSystemEntity = folder.findEntity ( "child3" )

    // Assertions
    foundChild1 should be ( null )
    foundChild2 should be ( null )
    foundChild3 should be ( null )
  }
}