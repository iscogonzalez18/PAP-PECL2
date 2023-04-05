object CundyCroshSoga {

    def main(args: Array[String]): Unit = {
        println("Introduce las opciones de ejecución (ej: css -a 2 10 50):")
        val opciones = scala.io.StdIn.readLine()
        // creamos el objeto de la clase
        val opcionesJuego:datosCundyCroshSoga = new datosCundyCroshSoga(opciones)
        opcionesJuego.filtrarDatos()
    }
}