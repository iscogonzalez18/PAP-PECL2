object CundyCroshSoga {

    def main(args: Array[String]): Unit = {
        println("Introduce las opciones de ejecución (ej: css -a 2 10 50):")
        // creamos el objeto de la clase
        //val opcionesJuego = datosCCS(scala.io.StdIn.readLine())
        val opcionesJuego = datosCCS("css -a 1 2 4")

        //creamos el tablero
        val juego = new juego(opcionesJuego.filas, opcionesJuego.columnas, opcionesJuego.nColores, opcionesJuego.modo)
        val tablero = juego.inicializarTablero()
        juego.partida(tablero, opcionesJuego, 3, 0)
    }
}