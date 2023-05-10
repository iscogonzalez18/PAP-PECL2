import interfaz.{InicioJuego, Juego, Tablero}

import javax.swing._

object Main {

  def main(args: Array[String]): Unit = {
    // Se crea la ventana de inicio
    val inicio = new InicioJuego()

    // Se espera a que el usuario seleccione el modo de juego
    while (!inicio.getAccionCompletada) {
      Thread.sleep(100)
    }
    //se oculta la ventana de inicio
    inicio.ocultar()

    // Se obtienen los datos de la ventana de inicio
    val modo = inicio.getModo()
    val nColores = inicio.getNColores()
    val filas = inicio.getFilas()
    val columnas = inicio.getColumnas()

    // Se crea el tablero con los datos obtenidos
    val tablero  = new Tablero(filas, columnas, nColores,modo)
  }
}