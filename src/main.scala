@main
def main(): Unit = {

  // pedimos los datos de entrada
  def pedirDatos(): List[String] = {
    println("Introduce las opciones de ejecución (ej: css -a 2 10 50):")
    val opciones = scala.io.StdIn.readLine()
    // filtramos la entrada de datos
    val opcionesFiltradas = opciones.split(" ").filter(_.nonEmpty)
    var listaOpciones: List[String] = List()
    // comprobamos que la entrada de datos es correcta
    if (opcionesFiltradas.length != 5) {
      println("Error en la entrada de datos")
      throw new Error("Error en la entrada de datos")
    } else {
      listaOpciones = List(opcionesFiltradas(1).substring(1, 2), opcionesFiltradas(2), opcionesFiltradas(3), opcionesFiltradas(4))
    }
    if ( (listaOpciones.head != "a" && listaOpciones.head != "m") || !listaOpciones(1).forall(_.isDigit) || listaOpciones(1).toInt > 2 || listaOpciones(1).toInt < 1 || !listaOpciones(2).forall(_.isDigit) || !listaOpciones(3).forall(_.isDigit) ) {
      println("Error en la entrada de datos")
      throw new Error("Error en la entrada de datos")
    }
    listaOpciones
  }

  // creamos el objeto de la clase
  val css = pedirDatos()
  println(css)

}