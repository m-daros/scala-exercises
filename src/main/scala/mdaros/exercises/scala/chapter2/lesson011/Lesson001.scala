package mdaros.exercises.scala.chapter2.lesson011

object Lesson001 extends App {

  val carloCollodi = new Writer ( "Carlo", "Collodi", 1826 )

  val pinocchio = new Novel ( "Pinocchio", 1883, carloCollodi )
  val pinocchioRel2 = pinocchio.copy ( 1885 )

  println ( s"${pinocchio.name} was written by ${carloCollodi.fullName ()}? ${pinocchio.isWrittenBy ( carloCollodi )}" )
  println ( s"${carloCollodi.fullName ()} age when he written ${pinocchio.name}: ${pinocchio.authorAge()}" )
  println ( s"${carloCollodi.fullName ()} age when he written ${pinocchioRel2.name} (second release): ${pinocchioRel2.authorAge()}" )
}