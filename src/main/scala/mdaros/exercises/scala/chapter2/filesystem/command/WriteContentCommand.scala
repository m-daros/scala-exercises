package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

abstract class WriteContentCommand ( arguments: Array [String] ) extends Command {

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  def writeToStdout ( state: State, words: Array [ String ], contentSeparator: String ) : State = {

    new State ( state.rootFolder, state.workingFolder, words.mkString ( contentSeparator ) )
  }

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  def writeToFile ( state: State, fileName: String, words: Array [ String ], overwrite: Boolean ): State = {

    val folderNamesInPath: Array [String] = getFolderNamesInPath ( state.workingFolder.path () );
    val entity: FileSystemEntity = state.workingFolder.findDescendant ( folderNamesInPath :+ fileName )
//    val entity: FileSystemEntity = state.workingFolder.findChild ( fileName )

    if ( entity == null ) {

      val targetFile: File = new File ( state.workingFolder.path (), fileName, words.mkString ( " " ) )
      val newRoot = updateTree ( state.rootFolder, folderNames = folderNamesInPath, targetFile, ( folder: Folder, entity: FileSystemEntity ) => folder.addEntity ( entity ) )
      val newWorkingFolder: Folder = newRoot.findDescendant ( folderNamesInPath ).asFolder () // TODO Controllare che sia un Folder e dare eccezione in caso contrario

      new State ( newRoot, newWorkingFolder, "" )
    }
    else if ( entity.getType ().equals ( "Folder" ) ) { // TODO Use constant for Type Directory 7 Type File or implements methods isFIle (), isFOlder ()

      throw new MyFileSystemException ( "Unable to echo contents to the item " + entity.path () + " because it's a Folder" )
    }
    else {

      // Get the old file, create new file
      val oldFile: File = entity.asFile ()
      val newFile: File = new File ( state.workingFolder.path (), fileName, buildNewContent ( oldFile.contents, words, overwrite ) )
      val newRoot = updateTree ( state.rootFolder, folderNames = folderNamesInPath, newFile, ( folder: Folder, entity: FileSystemEntity ) => folder.replaceEntity ( entity ) )
      val newWorkingFolder: Folder = newRoot.findDescendant ( folderNamesInPath ).asFolder () // TODO Controllare che sia un Folder e dare eccezione in caso contrario

      new State ( newRoot, newWorkingFolder, "" )
    }
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

//      val oldFolder: Folder = currentFolder.findLocalEntity ( folderNames.head ).asFolder ()
      val oldFolder: Folder = currentFolder.findDescendant ( Array ( folderNames.head ) ).asFolder ()
      currentFolder.replaceEntity ( updateTree ( oldFolder, folderNames.tail, newEntity, updater ) )
    }
  }

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  private def buildNewContent ( oldContent: String, newContents: Array [String], overwrite: Boolean ) = {

    if ( overwrite ) {

      newContents.mkString ( " " )
    }
    else {

      oldContent + "\n" + newContents.mkString ( " " )
    }
  }
}