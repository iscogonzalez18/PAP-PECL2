case class datosCundyCroshSoga(opciones: String) {
  require(opcionesFiltradas.length != 5)
  
  def filtrarDatos(): List[String] = {
    // filtramos la entrada de datos
    val opcionesFiltradas = opciones.split(" ").filter(_.nonEmpty)
    var listaOpciones: List[String] = List()
    // comprobamos que la entrada de datos es correcta
    if () {
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
}
