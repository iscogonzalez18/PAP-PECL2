import scala.util.Random
class juego(filas: Int, columnas: Int, nColores: Int, modo: String) {
  val random = new Random()

  // se empieza la partida y se desarrolla recursivamente
  def partida( tablero: List[Int], datos: datosCCS, nVidas: Int, nPuntos: Int ): Unit = {
    if (nVidas > 0){
      val coordenadas = datos.pedirCoordenada()
      println("Fila: " + coordenadas(0) + " Columna: " + coordenadas(1) + " n: " + tablero((coordenadas(0) - 1) * columnas + (coordenadas(1) - 1)))
      val tableroActualizado = borrarSeleccion(tablero, coordenadas(0), coordenadas(1), tablero((coordenadas(0) - 1) * columnas + (coordenadas(1) - 1)))
      val puntos = nPuntos + contarCeros(tableroActualizado)
      if (puntos == 1){
        imprimirTablero(tableroActualizado, -1, -1, nVidas - 1, puntos)
        partida(tableroActualizado, datos, nVidas - 1, puntos)
      }
      else {
        imprimirTablero(tableroActualizado, -1, -1, nVidas, puntos)
        partida(tableroActualizado, datos, nVidas, puntos)
      }
    } else {
      println("GAME OVER")
      println("PUNTUACION: " + nPuntos)
    }
  }

  def borrarSeleccion( tablero: List[Int], fila:Int, columna: Int, n: Int): List[Int] = {
    println("Fila: " + fila + " Columna: " + columna + " n: " + n + " filas " + filas + " columnas " + columnas)
    if (tablero((fila - 1) * columnas + (columna - 1)) == n) {
      val tableroActualizado = tablero.updated((fila - 1) * columnas + (columna - 1), 0)
      // Arriba
      if (fila > 1 && tablero(((fila - 1) - 1) * columnas + (columna - 1)) == n) {
        borrarSeleccion(tableroActualizado, fila - 1, columna, n).updated((fila - 1 - 1) * columnas + (columna - 1), 0)
      }
      // Abajo
      if ((fila - 1) < filas - 1 && tablero(((fila - 1) + 1) * columnas + (columna - 1)) == n) {
        borrarSeleccion(tableroActualizado, fila + 1, columna, n).updated((fila - 1 + 1) * columnas + (columna - 1), 0)
      }
      // Izquierda
      if ((columna - 1) > 1 && tablero((fila - 1) * columnas + (columna - 1) - 1) == n) {
        borrarSeleccion(tableroActualizado, fila, columna - 1, n).updated((fila - 1) * columnas + (columna - 1 - 1), 0)
      }
      // Derecha
      if ((columna - 1) < columnas - 1 && tablero((fila - 1) * columnas + (columna - 1) + 1) == n) {
        borrarSeleccion(tableroActualizado, fila, columna + 1, n).updated((fila - 1) * columnas + (columna - 1 + 1), 0)
      }
      tableroActualizado
    } else {
        tablero
    }
  }

  def contarCeros(tablero: List[Int]): Int = {
    if ( tablero == Nil) 0
    else if (tablero.head == 0) 1 + contarCeros(tablero.tail)
    else contarCeros(tablero.tail)
  }



  def inicializarTablero(): List[Int] = {
    val tablero = crearTablero(filas * columnas)
    imprimirTablero(tablero, -1, -1, 3, 0)
    tablero
  }

  def crearTablero(n: Int) : List[Int] ={
    if(n == 0) Nil
    else crearTablero(n-1) :+ (random.nextInt(nColores) + 1)
  }

  def imprimirTablero(tablero: List[Int], fila: Int = 0, columna: Int = 0, vidas: Int, puntos: Int): Unit = {
    if (fila >= 0 && columna >= 0 && fila < filas && columna < columnas) { // Reemplazar 3 por el tamaño del tablero
      imprimirNumero( tablero.head ) // Imprimir el valor de la casilla con marco
      if (columna == columnas - 1) {
        println("|") // Imprimir el cierre del marco y una nueva línea después de cada fila
        if (fila < filas - 1) {
          for (i <- 0 until columnas) print("+---") // Imprimir el marco para las casillas intermedias
          println("+") // Imprimir el cierre del marco y una nueva línea después de cada fila
        }
        imprimirTablero(tablero.tail, fila + 1, 0, vidas, puntos) // Llamada recursiva para la siguiente fila
      } else {
        imprimirTablero(tablero.tail, fila, columna + 1, vidas, puntos) // Llamada recursiva para la siguiente columna
      }
    } else if (fila == filas) {
      for (i <- 0 until columnas) print("+---") // Imprimir el marco para las casillas intermedias
      println("+") // Imprimir el cierre del marco y una nueva línea después de cada fila
      println("VIDAS: " + vidas + " | PUNTOS: " + puntos + "\n")
    } else if ( fila == -1 && columna == -1) {
      println("\n\nCANDY CROSH SOGA")
      for (i <- 0 until columnas) print("+---") // Imprimir el marco para las casillas intermedias
      println("+") // Imprimir el cierre del marco y una nueva línea después de cada fila
      imprimirTablero(tablero, 0, 0, vidas, puntos) // Llamada recursiva para la siguiente fila
    }
  }

  def imprimirNumero(n: Int): Unit = {
    // Definir los códigos de color ANSI para los diferentes colores
    n match {
      case 0 => print(s"| \u001B[90mX\u001B[0m ")
      case 1 => print(s"| \u001B[32m1\u001B[0m ")
      case 2 => print(s"| \u001B[35m2\u001B[0m ")
      case 3 => print(s"| \u001B[36m3\u001B[0m ")
      case 4 => print(s"| \u001B[33m4\u001B[0m ")
      case 5 => print(s"| \u001B[34m5\u001B[0m ")
      case 6 => print(s"| \u001B[31m6\u001B[0m ")
      case 7 => print(s"| \u001B[90mB\u001B[0m ")
      case 8 => print(s"| \u001B[33;91mT\u001B[0m ")
      case 10 => print(s"|\u001B[1;37mR1\u001B[0m ")
      case 20 => print(s"|\u001B[1;32mR2\u001B[0m ")
      case 30 => print(s"|\u001B[1;33mR3\u001B[0m ")
      case 40 => print(s"|\u001B[1;34mR4\u001B[0m ")
      case 50 => print(s"|\u001B[1;35mR5\u001B[0m ")
      case 60 => print(s"|\u001B[1;36mR6\u001B[0m ")
    }
  }

}
