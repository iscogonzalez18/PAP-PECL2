import scala.util.Random
class tablero(filas: Int, columnas: Int, nColores: Int) {
  val random = new Random()
  def inicializarTablero(): Unit = {
    val tablero = crearTablero(filas * columnas)
    imprimirTablero(tablero, -1, -1)
  }

  def crearTablero(n: Int) : List[Int] ={
    if(n == 0) Nil
    else crearTablero(n-1) :+ (random.nextInt(nColores) + 1)
  }

  def imprimirTablero(tablero: List[Int], fila: Int = 0, columna: Int = 0): Unit = {
    if (fila >= 0 && columna >= 0 && fila < filas && columna < columnas) { // Reemplazar 3 por el tamaño del tablero
      imprimirNumero( tablero.head ) // Imprimir el valor de la casilla con marco
      if (columna == columnas - 1) {
        println("|") // Imprimir el cierre del marco y una nueva línea después de cada fila
        if (fila < filas - 1) {
          for (i <- 0 until columnas) print("+---") // Imprimir el marco para las casillas intermedias
          println("+") // Imprimir el cierre del marco y una nueva línea después de cada fila
        }
        imprimirTablero(tablero.tail, fila + 1) // Llamada recursiva para la siguiente fila
      } else {
        imprimirTablero(tablero.tail, fila, columna + 1) // Llamada recursiva para la siguiente columna
      }
    } else if (fila == filas) {
      for (i <- 0 until columnas) print("+---") // Imprimir el marco para las casillas intermedias
      println("+\n") // Imprimir el cierre del marco y una nueva línea después de cada fila
    } else if ( fila == -1 && columna == -1) {
      println("\nCANDY CROSH SOGA")
      for (i <- 0 until columnas) print("+---") // Imprimir el marco para las casillas intermedias
      println("+") // Imprimir el cierre del marco y una nueva línea después de cada fila
      imprimirTablero(tablero)
    }
  }

  def imprimirNumero(n: Int): Unit = {
    // Definir los códigos de color ANSI para los diferentes colores
    n match {
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
