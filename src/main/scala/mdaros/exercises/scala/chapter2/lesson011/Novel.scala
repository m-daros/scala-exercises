package mdaros.exercises.scala.chapter2.lesson011

class Novel ( val name: String, val year: Int, val author: Writer ) {

  def authorAge (): Int = {

    this.year - this.author.year
  }

  def isWrittenBy ( author: Writer ): Boolean = {

    this.author ==  ( author )
  }

  def copy ( newYear: Int ): Novel = {

    new Novel ( this.name, newYear, this.author )
  }
}