package mdaros.exercises.scala.chapter2.filesystem

import java.util.Scanner

import mdaros.exercises.scala.chapter2.filesystem.command.Command
import mdaros.exercises.scala.chapter2.filesystem.model.Folder
import mdaros.exercises.scala.chapter2.filesystem.state.State

object Filesystem extends App {

  val commandLineScanner = new Scanner ( System.in )
  val rootFolder = Folder.ROOT

  var state = State ( Folder.ROOT, Folder.ROOT )
  state.show ()

  while ( true ) {

    state = state.setMessage ( "" ) // Cleanup status from previous command

    val commandLine = commandLineScanner.nextLine ()
    state = Command.from ( commandLine ).apply ( state )
    state.show ()
  }
}