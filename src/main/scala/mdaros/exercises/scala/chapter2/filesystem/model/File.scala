package mdaros.exercises.scala.chapter2.filesystem.model

class File ( override val parentPath: String, override val name: String, val contents: String = "" ) extends FileSystemEntity ( parentPath, name ) {

  override def asFolder (): Folder = {

    throw new MyFileSystemException ( "File " + parentPath + "/" + name + ". Files cannot be converted to Folder" )
  }

  override def asFile(): File = {

    this
  }

  override def getType (): String = {

    "File"
  }
}

object File {

  def empty ( parentPath: String, name: String ): File = {

    new File ( parentPath, name, "" )
  }
}