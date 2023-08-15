package mdaros.exercises.scala.chapter2.lesson011

class Writer ( val name: String, val surname: String, val year: Int ) {

  def fullName () : String = {

    name + " " + surname
  }
}