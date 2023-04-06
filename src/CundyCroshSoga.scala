object CundyCroshSoga {

    def main(args: Array[String]): Unit = {
        println("Introduce las opciones de ejecución (ej: css -a 2 10 50):")
        // creamos el objeto de la clase
        //val opcionesJuego = datosCCS(scala.io.StdIn.readLine())
        val opcionesJuego = datosCCS("css -a 2 4 20")

        //creamos el tablero
        val tablero = new tablero(opcionesJuego.filas, opcionesJuego.columnas, opcionesJuego.nColores)
        tablero.inicializarTablero()
    }
}