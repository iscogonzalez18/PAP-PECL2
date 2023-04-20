import scala.:+
import scala.util.Random
class juego(filas: Int, columnas: Int, nColores: Int) {
  val random = new Random()

  // se empieza la partida y se desarrolla recursivamente
  def partidaManual( tablero: List[Int], datos: datosCCS, nVidas: Int, nPuntos: Int ): Unit = {
    // Se comprueba el numero de vidas restantes
    if (nVidas > 0){
      // Se piden las coordenadas de la casilla a seleccionar
      val coordenadas = datos.pedirCoordenada()
      // Se actualiza el tablero con las casillas adyacentes que sean iguales a la seleccionada dando el valor 0
      val (tableroActualizado,especial) = if(0 < tablero((coordenadas._1 - 1) * columnas + (coordenadas._2 - 1)) && tablero((coordenadas._1 - 1) * columnas + (coordenadas._2 - 1)) < 7) {
        (borrarSeleccion(tablero, coordenadas._1, coordenadas._2, tablero((coordenadas._1 - 1) * columnas + (coordenadas._2 - 1))), false)
      } else {
        (borrarSeleccionEspecial( tablero, coordenadas._1, coordenadas._2, tablero((coordenadas._1 - 1) * columnas + (coordenadas._2 - 1))), true)
      }
      // Se cuentan los 0 que hay en el tablero
      val puntos = nPuntos + contarCeros(tableroActualizado)
      // Si solo se ha conseguido un punto se resta una vida
      if (puntos - nPuntos == 1){
        imprimirTablero(tableroActualizado, -1, -1, nVidas - 1, puntos)
        // Se llama recursivamente a la funcion partida
        partidaManual(gravedad(tableroActualizado, nVidas - 1, puntos), datos, nVidas - 1, puntos)
      }
      else if (puntos - nPuntos < 5){
        imprimirTablero(tableroActualizado, -1, -1, nVidas, puntos)
        // Se llama recursivamente a la funcion partida
        partidaManual(gravedad(tableroActualizado, nVidas, puntos), datos, nVidas, puntos)
      }
      else {
        imprimirTablero(tableroActualizado, -1, -1, nVidas, puntos)
        val tableroEspecial = if(!especial){
          insertarEspecial(tableroActualizado, puntos - nPuntos, coordenadas._1, coordenadas._2)
        } else {
          tableroActualizado
        }
        // se inserta la casilla especial: bomba, tnt o rompecabezas
        // Se llama recursivamente a la funcion partida
        partidaManual(gravedad(tableroEspecial, nVidas, puntos), datos, nVidas, puntos)
      }
    } else {
      println("GAME OVER")
      println("PUNTUACION: " + nPuntos)
    }
  }

  // se empieza la partida y se desarrolla recursivamente
  def partidaAutomatica(tablero: List[Int], datos: datosCCS, nVidas: Int, nPuntos: Int): Unit = {
    // Se comprueba el numero de vidas restantes
    if (nVidas > 0) {
      // Se buscan las coordenadas ideales para explotar bloques, las que mas puntos den
      val visitados: List[Int] = List()
      val buscar = buscarCoordenadaIdeal(tablero,1,1,1,1,datos.filas,datos.columnas,0,visitados)
      val coordenadas = (buscar._1,buscar._2)
      println("Coordenadas seleccionadas: " + coordenadas._1 + " " + coordenadas._2)
      // Se actualiza el tablero con las casillas adyacentes que sean iguales a la seleccionada dando el valor 0
      val (tableroActualizado, especial) = if (0 < tablero((coordenadas._1 - 1) * columnas + (coordenadas._2 - 1)) && tablero((coordenadas._1 - 1) * columnas + (coordenadas._2 - 1)) < 7) {
        (borrarSeleccion(tablero, coordenadas._1, coordenadas._2, tablero((coordenadas._1 - 1) * columnas + (coordenadas._2 - 1))), false)
      } else {
        (borrarSeleccionEspecial(tablero, coordenadas._1, coordenadas._2, tablero((coordenadas._1 - 1) * columnas + (coordenadas._2 - 1))), true)
      }
      // Se cuentan los 0 que hay en el tablero
      val puntos = nPuntos + contarCeros(tableroActualizado)
      // Si solo se ha conseguido un punto se resta una vida
      if (puntos - nPuntos == 1) {
        imprimirTablero(tableroActualizado, -1, -1, nVidas - 1, puntos)
        // Se llama recursivamente a la funcion partida
        Thread.sleep(5000)
        partidaAutomatica(gravedad(tableroActualizado, nVidas - 1, puntos), datos, nVidas - 1, puntos)
      }
      else if (puntos - nPuntos < 5) {
        imprimirTablero(tableroActualizado, -1, -1, nVidas, puntos)
        // Se llama recursivamente a la funcion partida
        Thread.sleep(5000)
        partidaAutomatica(gravedad(tableroActualizado, nVidas, puntos), datos, nVidas, puntos)
      }
      else {
        imprimirTablero(tableroActualizado, -1, -1, nVidas, puntos)
        val tableroEspecial = if (!especial) {
          insertarEspecial(tableroActualizado, puntos - nPuntos, coordenadas._1, coordenadas._2)
        } else {
          tableroActualizado
        }
        // se inserta la casilla especial: bomba, tnt o rompecabezas
        // Se llama recursivamente a la funcion partida
        Thread.sleep(5000)
        partidaAutomatica(gravedad(tableroEspecial, nVidas, puntos), datos, nVidas, puntos)
      }
    } else {
      println("GAME OVER")
      println("PUNTUACION: " + nPuntos)
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
      val tablero3 = if (fila < filas && tablero(((fila - 1) + 1) * columnas + (columna - 1)) == n) {
        borrarSeleccion(tablero2, fila + 1, columna, n).updated((fila - 1 + 1) * columnas + (columna - 1), 0)
      } else {
        tablero2
      }
      // Se comprueba posicion de izquierda
      // si nos interesa porque es el mismo numero que buscamos se vuelve a llamar a la funcion y se actualizará
      // si no, tablero4 se quedará con el valor de tablero3
      val tablero4 = if (columna > 1 && tablero((fila - 1) * columnas + (columna - 1) - 1) == n) {
        borrarSeleccion(tablero3, fila, columna - 1, n).updated((fila - 1) * columnas + (columna - 1 - 1), 0)
      } else {
        tablero3
      }
      // Se comprueba posicion de derecha
      // si nos interesa porque es el mismo numero que buscamos se vuelve a llamar a la funcion y se actualizará
      // ya no almacenamos una nueva variable ya que sea lo que sea devolverá o lo que nos devuelve la función o el valor de tablero4
      if (columna < columnas && tablero((fila - 1) * columnas + (columna - 1) + 1) == n) {
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

  def borrarSeleccionEspecial(tablero: List[Int], fila:Int, columna: Int, n: Int): List[Int] = {
    val tableroEspecial = if (n == 7) {
      val tableroCuadrado = if (filas == columnas) tablero else rellenarTablero(tablero)
      traspuesta(bomba(tableroCuadrado, fila, columna))
    } else if (n == 8) {
      tnt(tablero, 0, fila, columna)
    } else {
      rompecabezas(tablero, n)
    }
    if (filas == columnas) tableroEspecial else quitarNueves(tableroEspecial)
  }

  // ponemos a cero todas las casillas de la fila y la columna de la casilla seleccionada recusivamente
  def bomba(tablero: List[Int], fila: Int, columna: Int): List[Int] = {
    val tableroBombaFila = filaBomba(tablero, fila, 1)
    val tableroBombaColumna = columnaBomba(tableroBombaFila, columna, 1)
    tableroBombaColumna
  }

  def filaBomba(tablero: List[Int], fila: Int, i: Int): List[Int] = {
    if (i == (mayor(filas, columnas) + 1)) Nil
    else if(fila == i) concatenarListas(generar0(columnas,mayor(filas, columnas) - columnas), filaBomba(deja(mayor(filas,columnas), tablero), fila, i + 1))
    else concatenarListas(toma(mayor(filas, columnas),tablero), filaBomba(deja(mayor(filas,columnas), tablero), fila, i + 1))
  }

  def columnaBomba(tablero: List[Int], columna: Int, i: Int): List[Int] = {
    if (i == (mayor(filas, columnas) + 1)) Nil
    else if(columna == i) concatenarListas(generar0(filas, mayor(filas,columnas) - filas), columnaBomba(tablero, columna, i + 1))
    else concatenarListas(getColumna(i,tablero), columnaBomba(tablero, columna, i + 1))
  }

  // pone a cero las casillas que se encuentren en un radio de 4 casillas de la casilla seleccionada
  def tnt(tablero: List[Int], pos: Int, fila: Int, columna: Int): List[Int] = {
    if (tablero == Nil) Nil
    else if(( pos / columnas + 1) > fila - 4 && ( pos / columnas + 1) < fila + 4 && (pos % columnas + 1) > columna - 4 && (pos % columnas + 1) < columna + 4) {
      concatenarListas(generar0(1,0), tnt(deja(1, tablero), pos + 1, fila, columna))
    }
    else concatenarListas(toma(1, tablero), tnt(deja(1, tablero), pos + 1, fila, columna))
  }

  def rompecabezas( tablero: List[Int], n: Int): List[Int] = {
    if (tablero == Nil) Nil
    else if (tablero.head == n / 10 || tablero.head == n) concatenarListas(generar0(1,0), rompecabezas(deja(1, tablero), n))
    else concatenarListas(toma(1, tablero), rompecabezas(deja(1, tablero), n))
  }

  def insertarEspecial(tablero: List[Int], n: Int, fila: Int, columna: Int): List[Int] = {
    // se comprueba la cantidad de ceros
    val tableroEspecial = if (n == 5) {
      // si solo hay un cero se inserta una bomba
      tablero.updated((fila - 1) * columnas + (columna - 1), 7)
    } else if (n == 6) {
      // si hay dos ceros se inserta una tnt
      tablero.updated((fila - 1) * columnas + (columna - 1), 8)
    } else if (n >= 7){
      // si hay tres o más ceros se inserta un rompecabezas
      tablero.updated((fila - 1) * columnas + (columna - 1), (random.nextInt(nColores) + 1) * 10)
    } else {
      tablero
    }
    tableroEspecial
  }

  def gravedad( tablero: List[Int], vidas: Int, puntos: Int): List[Int] = {
    val tableroCuadrado = if (filas == columnas) tablero else rellenarTablero(tablero)
    val tableroGravedad = gravedadColumnas(subirCeros, tableroCuadrado, 1)
    val tableroActualizado = randomNumeros(random.nextInt(nColores) + 1, traspuesta(tableroGravedad))
    // se vuelve a quitar el exceso de filas o columnas
    val tableroFinal = if (filas == columnas) tableroActualizado else quitarNueves(tableroActualizado)
    imprimirTablero(tableroFinal, -1, -1, vidas, puntos)
    tableroFinal
  }

  // quita las casillas rellenadas con 9 para obtener el tablero original
  def quitarNueves(tablero: List[Int]): List[Int] = {
    if (tablero == Nil) Nil
    else if (tablero.head == 9) quitarNueves(tablero.tail)
    else tablero.head :: quitarNueves(tablero.tail)
  }

  // devuelve el tablero cuadrado para poder aplicar la trasposicion
  def rellenarTablero(tablero: List[Int]): List[Int] = {
    if (filas > columnas) rellenarColumnas(tablero, 0, filas - columnas)
    else concatenarListas(tablero, generar9((columnas - filas) * columnas))
  }

  // rellena las columnas del tablero
  def rellenarColumnas(tablero: List[Int], i: Int, cantidad: Int): List[Int] = {
    if (i == mayor(filas, columnas)) Nil
    else concatenarListas(concatenarListas(getFila(i, tablero), generar9(cantidad)), rellenarColumnas(tablero, i + 1, cantidad))
  }

  def gravedadColumnas(f: List[Int] => List[Int], tablero: List[Int], i: Int): List[Int] = {
    if (i > mayor(filas, columnas)) Nil
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
    if (index > tamano(l) - 1) throw new Error("Out of Index")
    else l(index)
  }

  // coje una columna del tablero
  def getColumna(columna: Int, l: List[Int]): List[Int] = {
    if (columna > mayor(filas, columnas)) throw new Error("Columna out of index")
    else if (l == Nil) Nil
    else getElem(columna - 1, toma(mayor(filas, columnas), l)) :: getColumna(columna, deja(mayor(filas, columnas), l))
  }

  // coje una fila del tablero
  def getFila(fila: Int, l: List[Int]): List[Int] = {
    if (fila > tamano(l) / columnas - 1) throw new Error("Fila out of index")
    else if (fila == 0) toma(columnas, l)
    else getFila(fila - 1, deja(columnas, l))
  }

  // genera una lista de n elementos 0
  def generar0(n: Int, a: Int): List[Int] = {
    if (a == 0 && n == 0) Nil
    else if (n == 0) 9 :: generar0(n, a - 1)
    else 0 :: generar0(n - 1, a)
  }

  // genera una lista de n elementos 9
  def generar9(n: Int): List[Int] = {
    if (n == 0) Nil
    else generar9(n - 1) :+ 9
  }

  // pone los ceros al principio del array
  def subirCeros(columna: List[Int]): List[Int] = {
    subirCerosAux(columna, contarCeros(columna))
  }

  def subirCerosAux(columna: List[Int], n: Int): List[Int] = {
    if (columna == Nil) Nil
    else if (n > 0) 0 :: subirCerosAux(columna, n - 1)
    else if (columna.head == 0) subirCerosAux(columna.tail, n)
    else columna.head :: subirCerosAux(columna.tail, n)
  }

  def trasp_aux(matriz: List[Int], columnaActual: Int): List[Int] = {
    if (columnaActual > mayor(filas, columnas)) Nil
    else concatenarListas(getColumna(columnaActual, matriz),trasp_aux(matriz, columnaActual + 1))
  }

  def traspuesta(l: List[Int]): List[Int] = {
    if (l == Nil) Nil
    else trasp_aux(l, 1)
  }

  // se rellena la matriz con numeros aleatorios
  def randomNumeros(f: => Int, tablero: List[Int] ): List[Int] = {
    if (tablero == Nil) Nil
    else if (tablero.head == 0) f :: randomNumeros(f, tablero.tail)
    else tablero.head :: randomNumeros(f, tablero.tail)
  }

  // devuelve el entero mas alto
  def mayor(a: Int, b: Int): Int = {
    if (a > b) a
    else b
  }

  // devuelve el tamaño de una lista
  def tamano(lista: List[Int]): Int = lista match {
    case Nil => 0 // Caso base: lista vacía, tamaño es 0
    case _ :: tail => 1 + tamano(tail) // Caso recursivo: tamaño es 1 + tamaño de la cola
  }

  /*
  def numIgualesAdyacentes(tablero: List[Int], fila: Int, columna: Int, filas: Int, columnas: Int, num: Int): Int = {
    val filaComprobar = fila - 1
    val columnaComprobar = columna - 1
    val arriba = if (fila > 0) (filaComprobar - 1) * columnas + columnaComprobar else -1
    val abajo = if (fila < filas - 1) (filaComprobar + 1) * columnas + columnaComprobar else -1
    val izquierda = if (columna > 0) filaComprobar * columnas + (columnaComprobar - 1) else -1
    val derecha = if (columna < columnas - 1) filaComprobar * columnas + (columnaComprobar + 1) else -1



    val arribaIgual = if (arriba != -1 && tablero(arriba) == num) 1 + numIgualesAdyacentes(tablero, fila - 1, columna, filas, columnas, num) else 0
    val abajoIgual = if (abajo != -1 && tablero(abajo) == num) 1 + numIgualesAdyacentes(tablero, fila + 1, columna, filas, columnas, num) else 0
    val izquierdaIgual = if (izquierda != -1 && tablero(izquierda) == num) 1 + numIgualesAdyacentes(tablero, fila, columna - 1, filas, columnas, num) else 0
    val derechaIgual = if (derecha != -1 && tablero(derecha) == num) 1 + numIgualesAdyacentes(tablero, fila, columna + 1, filas, columnas, num) else 0

    arribaIgual + abajoIgual + izquierdaIgual + derechaIgual
  }*/

  def numIgualesAdyacentes(tablero: List[Int], fila: Int, columna: Int, filas: Int, columnas: Int, num: Int, previos: List[Int]): (Int, List[Int]) = {
    val actual = (fila - 1) * columnas + (columna - 1)
    val n = tablero(actual)
    val filaComprobar = fila - 1
    val columnaComprobar = columna - 1


    val arriba = if (fila > 1) (filaComprobar - 1) * columnas + columnaComprobar else -1
    val abajo = if (fila < filas) (filaComprobar + 1) * columnas + columnaComprobar else -1
    val izquierda = if (columna > 1) filaComprobar * columnas + (columnaComprobar - 1) else -1
    val derecha = if (columna < columnas) filaComprobar * columnas + (columnaComprobar + 1) else -1

    val pLista =  actual :: previos

    val adyacentesArriba =
      if (arriba != -1 && tablero(arriba) == num && !pLista.contains(arriba))
        (1 + numIgualesAdyacentes(tablero, fila - 1, columna, filas, columnas, n, pLista)._1, numIgualesAdyacentes(tablero, fila - 1, columna, filas, columnas, n, pLista)._2)
      else (0,pLista)

    val arribaIgual = adyacentesArriba._1
    val previosNuevo = adyacentesArriba._2

    val adyacentesAbajo =
      if (abajo != -1 && tablero(abajo) == num && !previosNuevo.contains(abajo))
        (1+ numIgualesAdyacentes(tablero, fila + 1, columna, filas, columnas, n, previosNuevo)._1, numIgualesAdyacentes(tablero, fila + 1, columna, filas, columnas, n, previosNuevo)._2)
      else (arribaIgual,previosNuevo)

    val abajoIgual = adyacentesAbajo._1
    val previosNuevo2 = adyacentesAbajo._2

    val adyacentesIzquierda =
      if (izquierda != -1 && tablero(izquierda) == num && !previosNuevo2.contains(izquierda))
        (1+ numIgualesAdyacentes(tablero, fila, columna - 1, filas, columnas, n, previosNuevo2)._1, numIgualesAdyacentes(tablero, fila, columna - 1, filas, columnas, n, previosNuevo2)._2)
      else (abajoIgual,previosNuevo2)

    val izquierdaIgual = adyacentesIzquierda._1
    val previosNuevo3 = adyacentesIzquierda._2

    val adyacentesDerecha =
      if (derecha != -1 && tablero(derecha) == num && !previosNuevo3.contains(derecha))
        (1+ numIgualesAdyacentes(tablero, fila, columna + 1, filas, columnas, n, previosNuevo3)._1, numIgualesAdyacentes(tablero, fila, columna + 1, filas, columnas, n, previosNuevo3)._2)
      else (izquierdaIgual,previosNuevo3)

    val derechaIgual = adyacentesDerecha._1
    val previosNuevo4 = adyacentesDerecha._2

    (derechaIgual, previosNuevo4)
  }


  def hipoteticosExplotados(tablero: List[Int], fila: Int, columna: Int): Int = {
    val n = tablero((fila-1) * columnas + (columna-1))
    if (n == 7) {
      val actualizado = bomba(tablero, fila, columna)
      contarCeros(actualizado)
    }
    else if (n == 8){
      val actualizado = tnt(tablero, 0, fila, columna)
      contarCeros(actualizado)
    }
    else if (n == 10) {
      val actualizado = rompecabezas(tablero, n)
      contarCeros(actualizado)
    }
    else if (n == 20){
      val actualizado = rompecabezas(tablero, n)
      contarCeros(actualizado)
    }
    else if (n == 30){
      val actualizado = rompecabezas(tablero, n)
      contarCeros(actualizado)
    }
    else if (n == 40){
      val actualizado = rompecabezas(tablero, n)
      contarCeros(actualizado)
    }
    else if (n == 50){
      val actualizado = rompecabezas(tablero, n)
      contarCeros(actualizado)
    }
    else if (n == 60){
      val actualizado = rompecabezas(tablero, n)
      contarCeros(actualizado)
    }
    else 0
  }

  def buscarCoordenadaIdeal (tablero: List[Int], filaIdeal: Int, columnaIdeal: Int, filaABuscar: Int, columnaABuscar: Int, filas: Int, columnas: Int, max: Int, visitados: List[Int]): (Int, Int, List[Int]) = {

    if (visitados.length == (filas * columnas)) {
      (filaIdeal, columnaIdeal, visitados) // se alcanzó el final del tablero, se devuelve la coordenada anterior
    } else {

      val tableroParaComprobar = tablero.toList
      val num = tablero((filaABuscar-1) * columnas + (columnaABuscar-1))
      val actual = (filaABuscar-1) * columnas + (columnaABuscar-1)
      val (adyacentesActual, lista) = if (num > 0 && num < 7 && !visitados.contains(actual)) numIgualesAdyacentes(tableroParaComprobar, filaABuscar, columnaABuscar, filas, columnas, num, visitados) else if (num==7 || num ==8 || num==10 || num == 20 || num == 30 || num == 40 || num == 50 || num == 60) (hipoteticosExplotados(tableroParaComprobar, filaABuscar, columnaABuscar), actual::visitados) else (0, visitados)

      if (adyacentesActual > max) {
        if (columnaABuscar == columnas) {
          buscarCoordenadaIdeal(tablero, filaABuscar, columnaABuscar, filaABuscar + 1, 1, filas, columnas, adyacentesActual,lista) // se busca en la siguiente fila
        } else {
          buscarCoordenadaIdeal(tablero, filaABuscar, columnaABuscar, filaABuscar, columnaABuscar + 1, filas, columnas, adyacentesActual,lista)
        }
      } else {
        if (columnaABuscar == columnas) {
          buscarCoordenadaIdeal(tablero, filaIdeal, columnaIdeal, filaABuscar + 1, 1, filas, columnas, max, lista) // se busca en la siguiente fila
        } else {
          buscarCoordenadaIdeal(tablero, filaIdeal, columnaIdeal, filaABuscar, columnaABuscar + 1, filas, columnas, max, lista)
        }
      }
    }
  }



  def contarCeros(tablero:List[Int]): Int = {
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
    if (fila >= 0 && columna > 0 && fila < filas && columna < columnas) {
      imprimirNumero(tablero.head) // Imprimir el valor de la casilla con marco
      if (columna == columnas - 1) {
        println("|") // Imprimir el cierre del marco y una nueva línea después de cada fila
        if (fila < filas - 1) {
          print("   ")
          for (i <- 0 until columnas) print("+---") // Imprimir el marco para las casillas intermedias
          println("+") // Imprimir el cierre del marco y una nueva línea después de cada fila
        }
        imprimirTablero(tablero.tail, fila + 1, 0, vidas, puntos) // Llamada recursiva para la siguiente fila
      } else {
        imprimirTablero(tablero.tail, fila, columna + 1, vidas, puntos) // Llamada recursiva para la siguiente columna
      }
    } else if (columna == 0 && fila < filas) {
      printf("%2d ", fila + 1) // Imprimir el número de la fila
      imprimirNumero(tablero.head)
      imprimirTablero(tablero.tail, fila, columna + 1, vidas, puntos) // Llamada recursiva para la siguiente columna
    } else if (fila == filas) {
      print("   ")
      for (i <- 0 until columnas) print("+---") // Imprimir el marco para las casillas intermedias
      println("+") // Imprimir el cierre del marco y una nueva línea después de cada fila
      print("  ")
      println("VIDAS: " + vidas + " | PUNTOS: " + puntos + "\n")
    } else if ( fila == -1 && columna == -1) {
      println("\n\nCANDY CROSH SOGA")
      print("  ")
      for (i <- 0 until columnas) printf("  %2d", i + 1) // Imprimir el número de la columna
      print("\n")
      print("   ")
      for (i <- 0 until columnas) print("+---") // Imprimir el marco para las casillas intermedias
      println("+") // Imprimir el cierre del marco y una nueva línea después de cada fila
      imprimirTablero(tablero, 0, 0, vidas, puntos) // Llamada recursiva para la siguiente fila
    }
  }

  def imprimirNumero(n: Int): Unit = {
    // Definir los códigos de color ANSI para los diferentes colores
    n match {
      case 0 => print(s"| \u001B[90mX\u001B[0m ")
      case 1 => print(s"|\u001B[48;5;33m 1 \u001B[0m")
      case 2 => print(s"|\u001B[48;5;196m 2 \u001B[0m")
      case 3 => print(s"|\u001B[48;5;208m 3 \u001B[0m")
      case 4 => print(s"|\u001B[48;5;35m 4 \u001B[0m")
      case 5 => print(s"|\u001B[48;5;130m 5 \u001B[0m")
      case 6 => print(s"|\u001B[48;5;226m 6 \u001B[0m")
      case 7 => print(s"| \u001B[90mB\u001B[0m ")
      case 8 => print(s"| \u001B[33;91mT\u001B[0m ")
      case 9 => print(s"| \u001B[1;31m#\u001B[0m ")
      case 10 => print(s"|\u001B[1;37mR1\u001B[0m ")
      case 20 => print(s"|\u001B[1;32mR2\u001B[0m ")
      case 30 => print(s"|\u001B[1;33mR3\u001B[0m ")
      case 40 => print(s"|\u001B[1;34mR4\u001B[0m ")
      case 50 => print(s"|\u001B[1;35mR5\u001B[0m ")
      case 60 => print(s"|\u001B[1;36mR6\u001B[0m ")
    }
  }

}
