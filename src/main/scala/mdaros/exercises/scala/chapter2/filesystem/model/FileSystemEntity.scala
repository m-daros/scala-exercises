package mdaros.exercises.scala.chapter2.filesystem.model

import mdaros.exercises.scala.chapter2.filesystem.model.Folder.empty

abstract class FileSystemEntity ( val parentPath: String, val name: String ) {

  def asFolder (): Folder

  def asFile (): File

  def getType (): String

  def path () = {

    if ( parentPath.equals ( "/" ) ) {

      FileSystemEntity.PATH_SEPARATOR + name
    }
    else {

      parentPath + FileSystemEntity.PATH_SEPARATOR + name
    }
  }

  override def toString: String = {

    this.name + " [" + getType () + "]"
  }
}

object FileSystemEntity {

  val ROOT_FOLDER_PATH: String = "/"
//  val ROOT_FOLDER_PATH: String = ""
  val PATH_SEPARATOR: String   = "/"
}