package show

trait Show[A]:
  extension (a: A) def show: String

object Show:
  def derived[A]: Show[A] = 
    new Show[A]:
      extension (a: A) def show: String = extractFields(a).toString
  
  private def extractFields[A](obj: A) =
    val clazz = obj.getClass
    val className = clazz.getSimpleName

    val fields = clazz.getDeclaredFields.init.map(field => 
      field.setAccessible(true)
      s"${field.getName} -> ${field.get(obj).toString}"
    ).mkString(",")

    s"$className($fields)"
