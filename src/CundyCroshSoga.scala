object CundyCroshSoga {

    def main(args: Array[String]): Unit = {
        println("Introduce las opciones de ejecución (ej: css -a 2 10 50):")
        // creamos el objeto de la clase
        //val opcionesJuego = datosCCS(scala.io.StdIn.readLine())
        val opcionesJuego = datosCCS("css -a 1 4 4")

        //creamos el tablero
        val juego = new juego(opcionesJuego.filas, opcionesJuego.columnas, opcionesJuego.nColores)
        val tablero = juego.inicializarTablero()
        if (opcionesJuego.modo == "a") juego.partidaAutomatica(tablero, opcionesJuego, 3, 0)
        else juego.partidaManual(tablero, opcionesJuego, 3, 0)
    }
}