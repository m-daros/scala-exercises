package mdaros.exercises.scala.chapter2

object Chapter2 extends App {

  val carloCollodi = new Writer ( "Carlo", "Collodi", 1826 )

  val pinocchio = new Novel ( "Pinocchio", 1883, carloCollodi )
}