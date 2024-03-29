package mdaros.exercises.scala.chapter2.filesystem.model

import java.io.FileNotFoundException

import scala.annotation.tailrec

class Folder ( override val parentPath: String, override val name: String, val children: List [ FileSystemEntity ] = List () ) extends FileSystemEntity ( parentPath, name ) {

  def isRoot (): Boolean = {

    parentPath.equals ( Folder.ROOT_PARENT_PATH )
  }

  protected def getFolderNamesInPath ( folderPath: String ) = {

    folderPath.split ( FileSystemEntity.PATH_SEPARATOR ).filter ( e => ! e.equals ( "" ) ) // TODO REMOCVE first if is an empty string
  }

  protected def getPath ( folderNamesInPath: Array [String] ) : String = {

//    FileSystemEntity.PATH_SEPARATOR + folderNamesInPath.mkString ( FileSystemEntity.PATH_SEPARATOR )
    folderNamesInPath.mkString ( FileSystemEntity.PATH_SEPARATOR )
  }

  def replaceEntity ( entity: FileSystemEntity ): Folder = {

    new Folder ( parentPath, name, children.filter ( child => ! child.name.equals ( entity.name ) ) :+ entity )
  }

  def findChild ( name: String ): FileSystemEntity  = {

    val filtered: List [FileSystemEntity] = children.filter ( e => e.name.equals ( name ) )

    if ( filtered.isEmpty ) {

      return null; // TODO Usare Optional
    }
    else {

      return filtered.head
    }
  }

  def addEntity ( entity: FileSystemEntity ): Folder = {

    if ( hasEntity ( entity.name ) ) {

      throw new MyFileSystemException ( "Folder " + path () + " already contains an entity with name" + entity.name )
    }

    new Folder ( parentPath, name, children :+ entity )
  }

  def removeEntity ( entityName: String ): Folder = {

    if ( ! hasEntity ( entityName ) ) {

      throw new MyFileSystemException ( "Folder " + path () + " does not contain an entity with name " + entityName + " unable to remove the entity" )
    }

    new Folder ( parentPath, name, children.filter ( child => ! child.name.equals ( entityName ) ) )
  }

  @tailrec
  final def findDescendant ( folderPath: String ): FileSystemEntity  = {

    if ( this.path ().equals ( folderPath ) ) {

      this
    }
    else {

      val folderNamesInPath: Array [String] = getFolderNamesInPath ( folderPath )

      if ( folderNamesInPath.isEmpty ) {

        null
      }
      else if ( folderNamesInPath.length == 1 ) {

        findChild ( folderNamesInPath.head )
      }
      else {

        // TODO return null se findChild non trova nulla, anzichè dare NullPointerException
        val child: FileSystemEntity = findChild ( folderNamesInPath.head )

        if ( null == child ) {

          null
        }
        else {

          // TODO dare eccezione se non è un Folder
          child.asFolder ().findDescendant ( getPath ( folderNamesInPath.tail ) )
        }
      }
    }
  }

  def hasEntity ( name: String ): Boolean = {

    children.map ( e => e.name ).contains ( name )
  }

  override def isFolder (): Boolean = {

    true
  }

  override def isFile (): Boolean = {

    false
  }

  override def asFolder (): Folder = {

    this
  }

  override def asFile (): File = {

    throw new MyFileSystemException ( "Folder " + parentPath + "/" + name + ". Folders cannot be converted to File" )
  }

  override def getType (): String = {

    "Folder"
  }
}

object Folder {

  val ROOT_PARENT_PATH = ""
  val ROOT_NAME = ""

  def empty ( parentPath: String, name: String ): Folder = {

    new Folder ( parentPath, name, List [ FileSystemEntity ] () )
  }
}