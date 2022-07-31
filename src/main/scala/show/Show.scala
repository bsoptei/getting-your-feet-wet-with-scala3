package show

trait Show[A]:
  extension (a: A) def show: String
