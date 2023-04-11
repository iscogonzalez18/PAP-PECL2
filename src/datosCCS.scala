case class datosCCS(opciones: String, modo: String, nColores: Int, filas: Int, columnas: Int){
  def pedirCoordenada(): (Int, Int) = {
    // pedimos las coordenadas de la casilla a marcar
    println("Seleccione casilla (fila columna):")
    val coordenadas = scala.io.StdIn.readLine().split(" ").filter(_.nonEmpty)
    if (coordenadas.length != 2 || !coordenadas(0).forall(_.isDigit) || !coordenadas(1).forall(_.isDigit)) {
      println("Error en la entrada de datos")
      throw new Error("Error en la entrada de datos")
    }
    (coordenadas(0).toInt, coordenadas(1).toInt)
  }
}
object datosCCS {
  def apply(opciones: String): datosCCS = {
    val datos = filtrarDatos(opciones)
    if (datos(1).toInt == 1) new datosCCS(opciones, datos(0), 4, datos(2).toInt, datos(3).toInt)
    else new datosCCS(opciones, datos(0), 6, datos(2).toInt, datos(3).toInt)
  }
}

def filtrarDatos(opciones: String) : List[String] = {
  // filtramos la entrada de datos
  val opcionesFiltradas = opciones.split(" ").filter(_.nonEmpty)

  // comprobamos que la entrada de datos es correcta
  if (opcionesFiltradas.length != 5){
    println("Error en la entrada de datos")
    throw new Error("Error en la entrada de datos")
  }
  val listaOpciones = List(opcionesFiltradas(1).substring(1, 2), opcionesFiltradas(2), opcionesFiltradas(3), opcionesFiltradas(4))
  
  if ((listaOpciones.head != "a" && listaOpciones.head != "m") || !listaOpciones(1).forall(_.isDigit) || listaOpciones(1).toInt > 2 || listaOpciones(1).toInt < 1 || !listaOpciones(2).forall(_.isDigit) || !listaOpciones(3).forall(_.isDigit)) {
    println("Error en la entrada de datos")
    throw new Error("Error en la entrada de datos")
  }
  listaOpciones
}
