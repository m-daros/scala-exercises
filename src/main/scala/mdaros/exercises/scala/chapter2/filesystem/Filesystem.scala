package mdaros.exercises.scala.chapter2.filesystem

import java.util.Scanner

import mdaros.exercises.scala.chapter2.filesystem.command.Command
import mdaros.exercises.scala.chapter2.filesystem.model.Folder
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.io.Source

object Filesystem extends App {

  /* OLD
  val commandLineScanner = new Scanner ( System.in )
  val rootFolder = Folder.empty ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )

  var state = State ( rootFolder, rootFolder )
  state.show ()

  while ( true ) {

    state = state.setMessage ( "" ) // Cleanup status from previous command

    val commandLine = commandLineScanner.nextLine ()
    state = Command.from ( commandLine ).apply ( state )
    state.show ()
  }
   */

  val rootFolder = Folder.empty ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )
  val initialState: State = State ( rootFolder, rootFolder )

  initialState.show ()

  Source.stdin.getLines ().foldLeft ( initialState ) ( ( currentState: State, commandLine: String ) => {

    val newState: State = Command.from ( commandLine ).apply ( currentState )
    newState.show ()

    newState
  })
}