package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

abstract class WriteContentCommand ( arguments: Array [String] ) extends Command {

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  def writeToStdout ( state: State, words: Array [ String ], contentSeparator: String ) : State = {

    new State ( state.rootFolder, state.workingFolder, words.mkString ( contentSeparator ) )
  }

  protected def getFileName ( filePath: String ): String = {

    if ( ! filePath.contains ( FileSystemEntity.PATH_SEPARATOR ) ) {

      filePath
    }
    else {

      getFolderNamesInPath ( filePath ).last
    }
  }

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  def writeToFile ( state: State, fileName: String, words: Array[String], contentSeparator: String, overwrite: Boolean ): State = {

    val workingFolderPath: String = state.workingFolder.path ()
    val folderNamesInPath: Array [String] = getFolderNamesInPath ( workingFolderPath );
    val filePath: String = getFilePath ( fileName, workingFolderPath )
    val entity: FileSystemEntity = state.rootFolder.findDescendant ( filePath )

    if ( entity == null ) {

      // TODO se il fileName contiene / => occorre trovare il folder che conterrà il file, non è detto che sia il workingFolder
      val name: String = getFileName ( fileName )
      val targetFile: File = new File ( getContainingFolderPath ( filePath ), name, words.mkString ( contentSeparator ) )
      val newRoot = updateTree ( state.rootFolder, folderNames = getFolderNamesInPath ( targetFile.parentPath ), targetFile, ( folder: Folder, entity: FileSystemEntity ) => folder.addEntity ( entity ) )
      val newWorkingFolder: Folder = newRoot.findDescendant ( workingFolderPath ).asFolder () // TODO Controllare che sia un Folder e dare eccezione in caso contrario

      new State ( newRoot, newWorkingFolder, "" )
    }
    else if ( entity.getType ().equals ( "Folder" ) ) { // TODO Use constant for Type Directory 7 Type File or implements methods isFIle (), isFOlder ()

      throw new MyFileSystemException ( "Unable to echo contents to the item " + entity.path () + " because it's a Folder" )
    }
    else {

      // Get the old file, create new file
      val oldFile: File = entity.asFile ()
      val name: String = getFileName ( fileName )
      val newFile: File = new File ( oldFile.parentPath, name, buildNewContent ( oldFile.contents, words, contentSeparator, overwrite ) ) // TODO NON E' DETTO CHE SIA NEL workingFolder
      val newRoot = updateTree ( state.rootFolder, folderNames = folderNamesInPath, newFile, ( folder: Folder, entity: FileSystemEntity ) => folder.replaceEntity ( entity ) )
      val newWorkingFolder: Folder = newRoot.findDescendant ( workingFolderPath ).asFolder () // TODO Controllare che sia un Folder e dare eccezione in caso contrario

      new State ( newRoot, newWorkingFolder, "" )
    }
  }

  protected def getFilePath ( fileName: String, workingFolderPath: String ) = {

    if ( fileName.startsWith ( FileSystemEntity.PATH_SEPARATOR ) ) {

      fileName
    }
    else {

      workingFolderPath + FileSystemEntity.PATH_SEPARATOR + fileName
    }
  }

  protected def getContainingFolderPath ( filePath: String ) = {

    val folderNamesInPath = getFolderNamesInPath ( filePath )
    FileSystemEntity.PATH_SEPARATOR + folderNamesInPath.slice ( 0, folderNamesInPath.length - 1 ).mkString ( FileSystemEntity.PATH_SEPARATOR )
  }

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  // TODO Testare
  protected def getFolderNamesInPath ( path: String )  = {

    val splits: Array [ String ] = path.split ( FileSystemEntity.PATH_SEPARATOR )

    if ( splits.isEmpty ) {

      Array [ String ] ()
    }
    else {

      splits.tail
    }
  }

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  // TODO Copiato da CreateEntityCommand --> Portare in una classe comune
  def updateTree ( currentFolder: Folder, folderNames: Array [String], newEntity: FileSystemEntity, updater: ( Folder, FileSystemEntity ) => Folder ): Folder = {

    if ( folderNames.isEmpty ) {

      updater.apply ( currentFolder, newEntity );
    }
    else {

      val oldFolder: Folder = currentFolder.findDescendant ( folderNames.head ).asFolder ()
      currentFolder.replaceEntity ( updateTree ( oldFolder, folderNames.tail, newEntity, updater ) )
    }
  }

  private def buildNewContent ( oldContent: String, newContents: Array [String], contentSeparator: String, overwrite: Boolean ) = {

    if ( overwrite ) {

      newContents.mkString ( contentSeparator )
    }
    else {

      oldContent + "\n" + newContents.mkString ( contentSeparator )
    }
  }
}