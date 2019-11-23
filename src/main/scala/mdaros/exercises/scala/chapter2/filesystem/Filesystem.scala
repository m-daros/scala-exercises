package mdaros.exercises.scala.chapter2.filesystem

import java.util.Scanner

import mdaros.exercises.scala.chapter2.filesystem.command.Command
import mdaros.exercises.scala.chapter2.filesystem.model.Folder
import mdaros.exercises.scala.chapter2.filesystem.state.State

object Filesystem extends App {

  val commandLineScanner = new Scanner ( System.in )
  val rootFolder = Folder.empty ( Folder.ROOT_PARENT_PATH, Folder.ROOT_NAME )

  var state = State ( rootFolder, rootFolder )
  state.show ()

  while ( true ) {

    state = state.setMessage ( "" ) // Cleanup status from previous command

    val commandLine = commandLineScanner.nextLine ()
    state = Command.from ( commandLine ).apply ( state )
    state.show ()

    // TODO TMP
//    println ( "STATE rootFolder: " + state.rootFolder.path () + ", workingFolder: " + state.workingFolder.path () )
  }
}