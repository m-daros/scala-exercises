package mdaros.exercises.scala.chapter2

class Novel ( name: String, year: Int, val author: Writer ) {

  def authorAge (): Int = {

    this.author.year - year
  }

  def isWrittenBy ( author: Writer ): Boolean = {

    this.author ==  ( author )
  }

  def copy ( newYear: Int ): Novel = {

    new Novel ( this.name, newYear, this.author )
  }
}