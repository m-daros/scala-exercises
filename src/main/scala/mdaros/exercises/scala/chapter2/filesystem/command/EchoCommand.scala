package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, FileSystemEntity, Folder, MyFileSystemException}
import mdaros.exercises.scala.chapter2.filesystem.state.State

class EchoCommand ( val arguments: Array [String] ) extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    EchoCommand.parse ( tokens )
  }

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  override def apply ( state: State ): State = {

    if ( arguments.length == 2 ) {

      writeToStdout ( state, arguments.tail )
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">" ) ) {

      writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = arguments.slice ( 1, arguments.length - 2 ), overwrite = true )
    }
    else if ( arguments ( arguments.length - 2 ).equals ( ">>" ) ) {

      writeToFile ( state: State, fileName = arguments ( arguments.length - 1 ), words = arguments.slice ( 1, arguments.length - 2 ), overwrite = false )
    }
    else {

      writeToStdout ( state, arguments.tail )
    }
  }

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  def writeToStdout ( state: State, words: Array [ String ] ) : State = {

    new State ( state.rootFolder, state.workingFolder, words.mkString ( " " ) )
  }

  // TODO Può essere estratta logica condivisa con il comando CatCommand
  def writeToFile ( state: State, fileName: String, words: Array [ String ], overwrite: Boolean ): State = {

    val entity: FileSystemEntity = state.workingFolder.findEntity ( fileName )

    val folderNamesInPath: Array [String] = getFolderNamesInPath ( state.workingFolder.path () );

    if ( entity == null ) {

      val targetFile: File = new File ( state.workingFolder.path (), fileName, words.mkString ( " " ) )
      val newRoot = updateTree ( state.rootFolder, folderNames = folderNamesInPath, targetFile, ( folder: Folder, entity: FileSystemEntity ) => folder.addEntity ( entity ) )
      val newWorkingFolder: Folder = newRoot.findDescendant ( folderNamesInPath )

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
      val newWorkingFolder: Folder = newRoot.findDescendant ( folderNamesInPath )

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

      val oldFolder: Folder = currentFolder.findEntity ( folderNames.head ).asFolder ()
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

object EchoCommand {

  def parse ( arguments: Array [String] ): Command = {

    if ( arguments.length < 2 ) {

      new MalformedCommand ( arguments )
    }
    else {

      new EchoCommand ( arguments )
    }
  }
}