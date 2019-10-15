package mdaros.exercises.scala.chapter2.filesystem.command
import mdaros.exercises.scala.chapter2.filesystem.model.{FileSystemEntity, Folder}
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.annotation.tailrec

case class MkDirCommand ( tokens: Array [String] ) extends Command {

  override def parse ( tokens: Array [String] ): Command = {

    MkDirCommand.parse ( tokens )
  }

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

      if ( workingFolder.hasEntry ( token ) ) {

        state.setMessage ( state.commandOutput + "\n" + token + " already exists" )
      }
      else {

        doMkdir ( state, token )
      }
    }

    applyHelper ( state, tokens.tail ) // Discard first token since it's the command name
  }

  def updateTree ( currentFolder: Folder, folderNames: Array [String], newEntry: Folder ): Folder = {

    if ( folderNames.isEmpty ) {

      currentFolder.addEntity ( newEntry )
    }
    else {

      val oldEntry: Folder = currentFolder.findEntity ( folderNames.head ).asFolder ()
      currentFolder.replaceEntity ( oldEntry.name, updateTree ( oldEntry, folderNames.tail, newEntry ) )
    }
  }

  def doMkdir ( state: State, folderName: String ): State = {

    val workingFolder = state.workingFolder
    val folderPath = workingFolder.path ()

    // 1. Get all the folders in fullPath
    val folderNamesInPath: Array [String] = folderPath.split ( FileSystemEntity.PATH_SEPARATOR )

    // 2. Create new Folder into workingFolder
    val newFolder = Folder.empty ( folderPath, folderName )

    // 3. Update the whole folder structure starting from ROOT to fullPath
    val newRootFolder: Folder = updateTree ( state.rootFolder, folderNamesInPath, newFolder )

    // 4. Find new working folder INSTANCE given workingFolder full path in the NEW folder structure
    val newWorkingFolder: Folder = newRootFolder.findDescendant ( folderNamesInPath )
   
    State ( newRootFolder, newWorkingFolder, state.commandOutput )
  }
}

object MkDirCommand {

  def parse ( tokens: Array [String] ): Command = {

      if ( tokens.size < 2 ) {

        new MalformedCommand ( tokens )
      }
      else {

        new MkDirCommand ( tokens )
      }
    }
}