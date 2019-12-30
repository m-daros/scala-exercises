package mdaros.exercises.scala.chapter2.filesystem.command

import mdaros.exercises.scala.chapter2.filesystem.model.{File, Folder}
import mdaros.exercises.scala.chapter2.filesystem.state.State
import org.scalatest.{FlatSpec, Matchers}

class WriteContentCommandSpec extends FlatSpec with Matchers {

	"getFilePath ()" should "create an empty Folder" in {

		val tokens: Array [String] = Array ( "cat", "folder1/file2" )

		// Invoke the method under test
		val command: CatCommand = new CatCommand ( tokens )

		val filePath: String = command.getFilePath ( "folder1/file2", "/" )

		// Assertions
		filePath should be ( "/folder1/file2" )
	}
}