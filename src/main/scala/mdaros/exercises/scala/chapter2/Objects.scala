package mdaros.exercises.scala.chapter2

object Objects extends App {

  object Person {

    val NUM_EYES = 2

    def from ( name: String, father: Person, mother: Person ) : Person = {

      new Person ( name, father.surname )
    }
  }

  class Person ( val name: String, val surname : String ) {

  }

  val john = new Person ( "John", "Doe" )
  val mary = new Person ( "Mary", "Roberts" )

  val bobby = Person.from ( "Bobby", john, mary )

  println ( s"Hi, I'm ${bobby.name} ${bobby.surname}" )
}