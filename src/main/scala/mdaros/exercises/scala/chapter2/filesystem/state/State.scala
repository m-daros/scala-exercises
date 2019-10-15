package mdaros.exercises.scala.chapter2.filesystem.state

import mdaros.exercises.scala.chapter2.filesystem.model.Folder

class State ( val rootFolder: Folder, val workingFolder: Folder, val commandOutput: String ) {

  def setMessage ( message: String ): State = {

    State.apply ( rootFolder, workingFolder, message )
  }

  def show (): Unit = {

    println ( commandOutput )
    print ( State.COMMAND_PROPMPT )
  }
}

object State {

  val COMMAND_PROPMPT: String = "$ "

  def apply ( rootFolder: Folder, workingFolder: Folder, commandOutput: String = "" ) = {

    new State ( rootFolder, workingFolder, commandOutput )
  }
}