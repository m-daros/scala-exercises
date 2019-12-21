package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{FileSystemEntity, Folder}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

abstract class WriteContentCommand(tokens: Array [String] ) extends Command {

  override def apply ( state: State ): State = {

    val workingFolder: Folder = state.workingFolder

    @tailrec
    def applyHelper ( state: State, tokens: Array [String] ): State = {

      if ( tokens.isEmpty ) {

        state
      }
      else {

        applyHelper ( evaluateToken ( state, tokens.head ), tokens.tail )
      }
    }

    def evaluateToken ( state: State, token: String ): State = {

      if ( workingFolder.hasEntity ( token ) ) {

        state.setMessage ( state.commandOutput + "\n" + token + " already exists" )
      }
      else {

        doCreateEntity ( state, token )
      }
    }

    applyHelper ( state, tokens.tail ) // Discard first token since it's the command name
  }

  def updateTree ( currentFolder: Folder, folderNames: Array [String], newEntity: FileSystemEntity ): Folder = {

    if ( folderNames.isEmpty ) {

      currentFolder.addEntity ( newEntity )
    }
    else {

      val oldFolder: Folder = currentFolder.findEntity ( folderNames.head ).asFolder ()
      currentFolder.replaceEntity ( updateTree ( oldFolder, folderNames.tail, newEntity ) )
    }
  }

  def doCreateEntity ( state: State, folderName: String ): State = {

    val workingFolder: Folder = state.workingFolder
    val folderPath: String = workingFolder.path ()

    // 1. Get all the folders in fullPath
    val folderNamesInPath: Array [String] = getFolderNamesInPath ( folderPath )

    // 2. Create new entity into workingFolder
    val newEntity: FileSystemEntity = createEntity ( folderPath, folderName )

    // 3. Update the whole folder structure starting from ROOT to fullPath
    val newRootFolder: Folder = updateTree ( state.rootFolder, folderNamesInPath, newEntity )

    // 4. Find new working folder instance given workingFolder full path in the NEW folder structure
    val newWorkingFolder: Folder = newRootFolder.findDescendant ( folderNamesInPath )

    new State ( newRootFolder, newWorkingFolder, state.commandOutput )
  }

  protected def getFolderNamesInPath ( folderPath: String ) = {

    folderPath.split ( FileSystemEntity.PATH_SEPARATOR ).filter ( e => ! e.equals ( "" ) ) // TODO REMOCVE first if is an empty string
  }

  def createEntity ( folderPath: String, entityName: String ): FileSystemEntity
}