package mdaros.exercises.scala.chapter2.filesystem

import java.util.Scanner

import mdaros.exercises.scala.chapter2.filesystem.command.Command
import mdaros.exercises.scala.chapter2.filesystem.model.Folder
import mdaros.exercises.scala.chapter2.filesystem.state.State

import scala.io.Source

object Filesystem extends App {

  val rootFolder = Folder.empty ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )
  val initialState: State = State ( rootFolder, rootFolder )

  initialState.show ()

  Source.stdin.getLines ().foldLeft ( initialState ) ( ( currentState: State, commandLine: String ) => {

    val state: State = currentState.setMessage ( "" ) // Cleanup previous output
    val newState: State = Command.from ( commandLine ).apply ( state )
    newState.show ()

    newState
  } )
}