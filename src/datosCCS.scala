case class datosCundyCroshSoga(opciones: String, modo: String, dificultad: Int, filas: Int, columnas: Int)
object datosCundyCroshSoga {
  def apply(opciones: String): datosCundyCroshSoga = {
    val datos = filtrarDatos(opciones)
    datosCundyCroshSoga(opciones, datos(0), datos(1).toInt, datos(2).toInt, datos(3).toInt)
  }
}

def filtrarDatos(opciones: String) : List[String] = {
  // filtramos la entrada de datos
  val opcionesFiltradas = opciones.split(" ").filter(_.nonEmpty)
  var listaOpciones: List[String] = List()
  // comprobamos que la entrada de datos es correcta
  if (opcionesFiltradas.length != 5){
    println("Error en la entrada de datos")
    throw new Error("Error en la entrada de datos")
  } else {
    listaOpciones = List(opcionesFiltradas(1).substring(1, 2), opcionesFiltradas(2), opcionesFiltradas(3), opcionesFiltradas(4))
  }
  if ((listaOpciones.head != "a" && listaOpciones.head != "m") || !listaOpciones(1).forall(_.isDigit) || listaOpciones(1).toInt > 2 || listaOpciones(1).toInt < 1 || !listaOpciones(2).forall(_.isDigit) || !listaOpciones(3).forall(_.isDigit)) {
    println("Error en la entrada de datos")
    throw new Error("Error en la entrada de datos")
  }
  listaOpciones
}
