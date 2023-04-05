object CundyCroshSoga {

    def main(args: Array[String]): Unit = {
        println("Introduce las opciones de ejecución (ej: css -a 2 10 50):")
        // creamos el objeto de la clase
        val opcionesJuego = datosCundyCroshSoga(scala.io.StdIn.readLine())
        println("To string " + opcionesJuego.filas + " " + opcionesJuego.columnas + " " + opcionesJuego.dificultad + " " + opcionesJuego.modo)
    }
}