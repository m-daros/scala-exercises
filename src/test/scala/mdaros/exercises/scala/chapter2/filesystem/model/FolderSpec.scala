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

  "findChild ( name )" should "should return the child having the given name if it exists" in {

    val childFolder1: Folder = new Folder ( "/folder1/folder2/myFolder", "child1" )
    val childFolder2: Folder = new Folder ( "/folder1/folder2/myFolder", "child2" )
    val childFile3: File = new File ( "/folder1/folder2/myFolder", "child3" )

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder", List ( childFolder1, childFolder2, childFile3 ) )

    // Invoke method under test
    val foundChild1: Folder = folder.findChild ( "child1" ).asFolder ()
    val foundChild2: Folder = folder.findChild ( "child2" ).asFolder ()
    val foundChild3: File   = folder.findChild ( "child3" ).asFile ()

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

  "findChild ( name )" should "should return null if doesn't exist a child having the given name " in {

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder" )

    // Invoke method under test
    val foundChild1: FileSystemEntity = folder.findChild ( "child1" )
    val foundChild2: FileSystemEntity = folder.findChild ( "child2" )
    val foundChild3: FileSystemEntity = folder.findChild ( "child3" )

    // Assertions
    foundChild1 should be ( null )
    foundChild2 should be ( null )
    foundChild3 should be ( null )
  }

  "findChild ( name )" should "throw an exception if the working folder doe not contains the folder we want to remove" in {

    val child1: Folder = new Folder ( "/folder1/folder2/myFolder", "child1" )
    val child2: Folder = new Folder ( "/folder1/folder2/myFolder", "child2" )

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder", List ( child1, child2 ) )

    val exception = intercept [ Exception ] {

      // Invoke the method expecting an exception
      folder.removeEntity ( "child3" )
    }

    // Assertions
    exception.isInstanceOf [ MyFileSystemException ] should be ( true )

    val myFileSystemException = exception.asInstanceOf [ MyFileSystemException ]

    myFileSystemException.message.contains ( "/folder1/folder2/myFolder" )   should be ( true )
    myFileSystemException.message.contains ( "child3" )                      should be ( true )
  }

  "removeEntity ( name )" should "not have the removed child" in {

    val childFolder1: Folder = new Folder ( "/folder1/folder2/myFolder", "childFolder1" )
    val childFolder2: Folder = new Folder ( "/folder1/folder2/myFolder", "childFolder2" )
    val childFile3: File     = new File ( "/folder1/folder2/myFolder", "childFile3" )

    val folder: Folder = new Folder ( "/folder1/folder2", "myFolder", List ( childFolder1, childFolder2, childFile3 ) )

    // Invoke method under test
    val updatedFolder1: Folder = folder.removeEntity ( "childFolder1" )

    updatedFolder1.children.size should be ( 2 )
    updatedFolder1.children ( 0 ) should be ( childFolder2 )
    updatedFolder1.children ( 1 ) should be ( childFile3 )

    // Invoke method under test
    val updatedFolder2: Folder = updatedFolder1.removeEntity ( "childFolder2" )

    updatedFolder2.children.size should be ( 1 )
    updatedFolder2.children ( 0 ) should be ( childFile3 )

    // Invoke method under test
    val updatedFolder3: Folder = updatedFolder2.removeEntity ( "childFile3" )

    updatedFolder3.children.size should be ( 0 )
  }

  "findEntity " should "should find an existing folder" in {

    val childFolder1: Folder = new Folder ( "/folder1", "childFolder1" )
    val childFolder2: Folder = new Folder ( "/folder1", "childFolder2" )

    val folder1: Folder = new Folder ( "", "folder1", List ( childFolder1, childFolder2 ) )

    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    // Invoke the method under test
    val foundChildFolder1: Folder = rootFolder.findDescendant ( "/folder1/childFolder1" ).asFolder ()
    val foundChildFolder2: Folder = rootFolder.findDescendant ( "/folder1/childFolder2" ).asFolder ()

    foundChildFolder1 should not be ( null )
    foundChildFolder1.name should be ( "childFolder1" )
    foundChildFolder1.parentPath should be ( "/folder1" )
    foundChildFolder1 should be ( childFolder1 )

    foundChildFolder2 should not be ( null )
    foundChildFolder2.name should be ( "childFolder2" )
    foundChildFolder2.parentPath should be ( "/folder1" )
    foundChildFolder2 should be ( childFolder2 )
  }

  "findEntity " should "should return null in case it's unable to find the required folder" in {

    val childFolder1: Folder = new Folder ( "/folder1", "childFolder1" )
    val childFolder2: Folder = new Folder ( "/folder1", "childFolder2" )

    val folder1: Folder = new Folder ( "", "folder1", List ( childFolder1, childFolder2 ) )

    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME, List ( folder1 ) )

    val folderNamesInPath: Array [String] = Array ( "folder1", "childFolder1" )

    // Invoke the method under test
    val notFoundFolder: FileSystemEntity = rootFolder.findDescendant (  "/folder1/unknownChildFolder" )
    val notFoundFolder2: FileSystemEntity = rootFolder.findDescendant ( "/folder1/unknownChildFolder2" )

    // Assertions
    notFoundFolder should be ( null )
    notFoundFolder2 should be ( null )
  }

  ///////

  "findDescendant " should "should return null in case it's unable to find the required folder" in {

    val rootFolder: Folder = new Folder ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )

    val folderNamesInPath: Array [String] = Array ( "folder1", "childFolder1" )

    // Invoke the method under test
    val descendant: FileSystemEntity = rootFolder.findDescendant (  "/folder1/file2" )

    // Assertions
    descendant should be ( null )
  }
}