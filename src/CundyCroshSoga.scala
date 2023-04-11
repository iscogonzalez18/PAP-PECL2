object CundyCroshSoga {

    def main(args: Array[String]): Unit = {
        println("Introduce las opciones de ejecución (ej: css -a 2 10 50):")
        // creamos el objeto de la clase
        //val opcionesJuego = datosCCS(scala.io.StdIn.readLine())
        val opcionesJuego = datosCCS("css -a 2 5 5")

        //creamos el tablero
        val juego = new tablero(opcionesJuego.filas, opcionesJuego.columnas, opcionesJuego.nColores)
        val tablero = juego.inicializarTablero()
        juego.partida(tablero, opcionesJuego, 3, 0)
    }
}