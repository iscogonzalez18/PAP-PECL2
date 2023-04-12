import scala.util.Random
class juego(filas: Int, columnas: Int, nColores: Int, modo: String) {
  val random = new Random()

  // se empieza la partida y se desarrolla recursivamente
  def partida( tablero: List[Int], datos: datosCCS, nVidas: Int, nPuntos: Int ): Unit = {
    // Se comprueba el numero de vidas restantes
    if (nVidas > 0){
      // Se piden las coordenadas de la casilla a seleccionar
      val coordenadas = datos.pedirCoordenada()
      // Se actualiza el tablero con las casillas adyacentes que sean iguales a la seleccionada dando el valor 0
      val tableroActualizado = borrarSeleccion(tablero, coordenadas(0), coordenadas(1), tablero((coordenadas(0) - 1) * columnas + (coordenadas(1) - 1)))
      // Se cuentan los 0 que hay en el tablero
      val puntos = nPuntos + contarCeros(tableroActualizado)
      // Si solo se ha conseguido un punto se resta una vida
      if (puntos == 1){
        imprimirTablero(tableroActualizado, -1, -1, nVidas - 1, puntos)
        // Se llama recursivamente a la funcion partida
        partida(tableroActualizado, datos, nVidas - 1, puntos)
      }
      else {
        imprimirTablero(tableroActualizado, -1, -1, nVidas, puntos)
        // Se llama recursivamente a la funcion partida
        partida(tableroActualizado, datos, nVidas, puntos)
      }
    } else {
      println("GAME OVER")
      println("PUNTUACION: " + puntos)
    }
  }

  def borrarSeleccion(tablero: List[Int], fila:Int, columna: Int, n: Int): List[Int] = {
    //lo que se va a devolver es tableroActualizado
    //al principio, se comprueba si el número que queremos borrar está en la casilla de la fila y columna que le hemos pasado
    //si no está, pasa al else de abajo de la función
    //si está, se borra el numero actualizando el tablero
    val tableroActualizado = if (tablero((fila - 1) * columnas + (columna - 1)) == n) {
      val tablero1 = tablero.updated((fila - 1) * columnas + (columna - 1), 0)
      // Se comprueba posicion de arriba
      // si nos interesa porque es el mismo numero que buscamos se vuelve a llamar a la funcion y se actualizará
      // si no, tablero2 se quedará con el valor de tablero1
      val tablero2 = if (fila > 1 && tablero(((fila - 1) - 1) * columnas + (columna - 1)) == n) {
        borrarSeleccion(tablero1, fila - 1, columna, n).updated((fila - 1 - 1) * columnas + (columna - 1), 0)
      } else {
        tablero1
      }
      // Se comprueba posicion de abajo
      // si nos interesa porque es el mismo numero que buscamos se vuelve a llamar a la funcion y se actualizará
      // si no, tablero3 se quedará con el valor de tablero2
      val tablero3 = if ((fila - 1) < filas - 1 && tablero(((fila - 1) + 1) * columnas + (columna - 1)) == n) {
        borrarSeleccion(tablero2, fila + 1, columna, n).updated((fila - 1 + 1) * columnas + (columna - 1), 0)
      } else {
        tablero2
      }
      // Se comprueba posicion de izquierda
      // si nos interesa porque es el mismo numero que buscamos se vuelve a llamar a la funcion y se actualizará
      // si no, tablero4 se quedará con el valor de tablero3
      val tablero4 = if ((columna - 1) > 1 && tablero((fila - 1) * columnas + (columna - 1) - 1) == n) {
        borrarSeleccion(tablero3, fila, columna - 1, n).updated((fila - 1) * columnas + (columna - 1 - 1), 0)
      } else {
        tablero3
      }
      // Se comprueba posicion de derecha
      // si nos interesa porque es el mismo numero que buscamos se vuelve a llamar a la funcion y se actualizará
      // ya no almacenamos una nueva variable ya que sea lo que sea devolverá o lo que nos devuelve la función o el valor de tablero4
      if ((columna - 1) < columnas - 1 && tablero((fila - 1) * columnas + (columna - 1) + 1) == n) {
        borrarSeleccion(tablero4, fila, columna + 1, n).updated((fila - 1) * columnas + (columna - 1 + 1), 0)
      } else {
        tablero4
      }
    } else {
      tablero
    }
    //la funcion devuelve el tablero actualizado que hemos ido actualizando en todos los casos y llamdas anteriores
    tableroActualizado
}

  def gravedad( tablero: List[Int] ): List[Int] = {
    val tableroGravedad = gravedadColumnas(subirCeros, tablero, 0)
    println("Gravedad Columnas")
    imprimirTablero(tableroGravedad, -1, -1, 3, 0)
    val tableroTraspuesto = traspuesta(tableroGravedad)
    println("Traspuesta")
    imprimirTablero(tableroTraspuesto, -1, -1, 3, 0)
    val tableroActualizado = randomNumeros(random.nextInt(nColores) + 1, tablero)
    println("Tablero Actualizado")
    imprimirTablero(tableroActualizado, -1, -1, 3, 0)
    tableroActualizado
  }

  def gravedadColumnas(f: List[Int] => List[Int], tablero: List[Int], i: Int): List[Int] = {
    if (i == columnas - 1) Nil
    else concatenarListas(f(getColumna(i, tablero)),gravedadColumnas(f, tablero, i + 1))
  }

  // toma los primeros n elementos de una lista
  def toma(n: Int, l: List[Int]): List[Int] = {
    if (n == 0) Nil
    else l.head :: toma(n - 1, l.tail)
  }

  // deja los primeros n elementos de una lista
  def deja(n: Int, l: List[Int]): List[Int] = {
    if (n == 1) l.tail
    else deja(n - 1, l.tail)
  }

  // obtener el elemento de una matriz segun index
  def getElem(index: Int, l: List[Int]): Int = {
    if (index > l.length - 1) throw new Error("Out of Index")
    else return l(index)
  }

  // coje una columna del tablero
  def getColumna(columna: Int, l: List[Int]): List[Int] = {
    if (columna > columnas - 1) throw new Error("Columna out of index")
    else if (l == Nil) Nil
    else getElem(columna, toma(8, l)) :: getColumna(columna, deja(8, l))
  }

  // pone los ceros al principio del array
  def subirCeros(tablero: List[Int]): List[Int] = {
    if (tablero == Nil) Nil
    else if (tablero.head == 0) 0 :: subirCeros(tablero.tail)
    else tablero.head :: subirCeros(tablero.tail)
  }

  def trasp_aux(matriz: List[Int], columnaActual: Int): List[Int] = {
    if (columnaActual > columnas - 1) Nil
    else concatenarListas(getColumna(columnaActual, matriz),trasp_aux(matriz, columnaActual + 1))
  }

  def traspuesta(l: List[Int]): List[Int] = {
    if (l == Nil) Nil
    else trasp_aux(l, 0)
  }

  def randomNumeros(f: => Int, tablero: List[Int] ): List[Int] = {
    if (tablero == Nil) Nil
    else if (tablero.head == 0) f :: randomNumeros(f, tablero.tail)
    else tablero.head :: randomNumeros(f, tablero.tail)
  }

  def numIgualesAdyacentes(tablero: List[Int], fila: Int, columna: Int, filas: Int, columnas: Int, num: Int): Int = {
    val actual = fila * columnas + columna
    val arriba = if (fila > 0) (fila - 1) * columnas + columna else -1
    val abajo = if (fila < filas - 1) (fila + 1) * columnas + columna else -1
    val izquierda = if (columna > 0) fila * columnas + (columna - 1) else -1
    val derecha = if (columna < columnas - 1) fila * columnas + (columna + 1) else -1
    
    val arribaIgual = if (arriba != -1 && tablero(arriba) == num) 1 + numIgualesAdyacentes(tablero, fila - 1, columna, filas, columnas, num) else 0
    val abajoIgual = if (abajo != -1 && tablero(abajo) == num) 1 + numIgualesAdyacentes(tablero, fila + 1, columna, filas, columnas, num) else 0
    val izquierdaIgual = if (izquierda != -1 && tablero(izquierda) == num) 1 + numIgualesAdyacentes(tablero, fila, columna - 1, filas, columnas, num) else 0
    val derechaIgual = if (derecha != -1 && tablero(derecha) == num) 1 + numIgualesAdyacentes(tablero, fila, columna + 1, filas, columnas, num) else 0
    
    arribaIgual + abajoIgual + izquierdaIgual + derechaIgual
  }


  //def buscarCoordenadaIdeal

  def contarCeros(tablero: List[Int]): Int = {
    if ( tablero == Nil) 0
    else if (tablero.head == 0) 1 + contarCeros(tablero.tail)
    else contarCeros(tablero.tail)
  }

    def concatenarListas[T](lista1: List[T], lista2: List[T]): List[T] = {
    def helper(l1: List[T], l2: List[T]): List[T] = l1 match {
      case Nil => l2
      case head :: tail => head :: helper(tail, l2)
    }
    helper(lista1, lista2)
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
