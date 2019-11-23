package mdaros.exercises.scala.chapter2.filesystem.model

import scala.annotation.tailrec

class Folder ( override val parentPath: String, override val name: String, val children: List [ FileSystemEntity ] = List () ) extends FileSystemEntity ( parentPath, name ) {

  def isRoot (): Boolean = {

    parentPath.equals ( Folder.ROOT_PARENT_PATH )
  }

  def replaceEntity ( folderName: String, entity: Folder ): Folder = {

    new Folder ( parentPath, name, children.filter ( child => ! child.name.equals ( entity.name ) ) :+ entity )   // TODO ???????
  }

  def findEntity ( name: String ): FileSystemEntity  = {

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

      throw new MyFileSystemException ( "Folder " + parentPath + " already contains an entity with name" + entity.name )
    }

    new Folder ( parentPath, name, children :+ entity )
  }

  @tailrec
  final def findDescendant ( folderNamesInPath: Array [String] ): Folder  = {

    if ( folderNamesInPath.isEmpty ) {

      this
    }
    else {

      findEntity ( folderNamesInPath.head ).asFolder ().findDescendant ( folderNamesInPath.tail )
    }
  }

  def hasEntity ( name: String ): Boolean = {

    children.map ( e => e.name ).contains ( name )
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