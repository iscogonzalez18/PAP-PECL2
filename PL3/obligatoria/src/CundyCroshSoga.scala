object CundyCroshSoga {

    def main(args: Array[String]): Unit = {
        // pedimos los datos de la partida
        val jugador = pedirJugador()
        val modo = pedirModo()
        val nColores = pedirDificultad()
        val filas = pedirFilas()
        val columnas = pedirColumnas()

        // creamos el juego
        val dificultad = if (nColores == 4) 1 else 2
        val juego = new juego(filas, columnas, nColores, dificultad, jugador)
        //creamos el tablero
        val tablero = juego.inicializarTablero()
        // si el juego es automático, se ejecuta el método partidaAutomatica de la clase juego
        if (modo == "a") juego.partidaAutomatica(tablero, 3, 0)
        // si el juego es manual, se ejecuta el método partidaManual de la clase juego
        else juego.partidaManual(tablero, 3, 0)
    }

    // método para pedir por pantalla el nombre del jugador
    def pedirJugador () : String = {
        println("Introduzca su nombre:")
        val jugador = scala.io.StdIn.readLine()
        if (jugador == "") {
            println("Error: valor no válido")
            pedirJugador()
        }
        jugador
    }
    
    // método para pedir por pantalla el modo de la partida
    def pedirModo () : String = {
        println("Seleccione modo de juego (a: automático, m: manual):")
        val modo = scala.io.StdIn.readLine()
        if (modo != "a" && modo != "m") {
            println("Error: valor no válido")
            pedirModo()
        }
        modo
    }

    // método para pedir por pantalla la dificultad de la partida
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

    // método para pedir por pantalla el número de filas del tablero
    def pedirFilas () : Int = {
        println("Seleccione número de filas:")
        val filas = scala.io.StdIn.readLine()
        if (!filas.forall(_.isDigit)) {
            println("Error: valor no válido")
            pedirFilas()
        }
        filas.toInt
    }

    // método para pedir por pantalla el número de columnas del tablero
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