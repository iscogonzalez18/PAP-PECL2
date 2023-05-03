object CundyCroshSoga {

    def main(args: Array[String]): Unit = {
        println("Introduce las opciones de ejecución (ej: css -a 2 10 50):")

        // creamos el objeto de la clase
        //val opcionesJuego = datosCCS(scala.io.StdIn.readLine())
        val modo = pedirModo()
        val nColores = pedirDificultad()
        val filas = pedirFilas()
        val columnas = pedirColumnas()

        //creamos el tablero
        val juego = new juego(filas, columnas, nColores)
        val tablero = juego.inicializarTablero()
        if (modo == "a") juego.partidaAutomatica(tablero, 3, 0)
        else juego.partidaManual(tablero, 3, 0)
    }

    def pedirModo () : String = {
        println("Seleccione modo de juego (a: automático, m: manual):")
        val modo = scala.io.StdIn.readLine()
        if (modo != "a" && modo != "m") {
            println("Error: valor no válido")
            pedirModo()
        }
        modo
    }

    def pedirDificultad () : Int = {
        println("Seleccione dificultad (1: fácil, 2: difícil):")
        val dificultad = scala.io.StdIn.readLine()
        if (dificultad != "1" && dificultad != "2") {
            println("Error: valor no válido")
            pedirDificultad()
        }
        val entero = dificultad.toInt
        if (entero == 1) 4
        else 6
    }

    def pedirFilas () : Int = {
        println("Seleccione número de filas:")
        val filas = scala.io.StdIn.readLine()
        if (!filas.forall(_.isDigit)) {
            println("Error: valor no válido")
            pedirFilas()
        }
        filas.toInt
    }

    def pedirColumnas () : Int = {
        println("Seleccione número de columnas:")
        val columnas = scala.io.StdIn.readLine()
        if (!columnas.forall(_.isDigit)) {
            println("Error: valor no válido")
            pedirColumnas()
        }
        columnas.toInt
    }
}