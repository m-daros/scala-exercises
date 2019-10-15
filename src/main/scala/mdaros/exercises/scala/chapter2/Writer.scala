package mdaros.exercises.scala.chapter2

class Writer (fullName: String, surname: String, val year: Int ) {

  def fullName () : String = {

    fullName + " " + surname
  }
}