import interfaz.{Ejemplo, InicioJuego}

import javax.swing._

object Main {

  def main(args: Array[String]): Unit = {
    val inicio = new InicioJuego()

    while (!inicio.getAccionCompletada) {
      Thread.sleep(100)
    }
    inicio.ocultar()

    val modo = inicio.getModo()
    val nColores = inicio.getNColores()
    val filas = inicio.getFilas()
    val columnas = inicio.getColumnas()
    println(modo)
    println(nColores)
    println(filas)
    println(columnas)
    inicio.ocultar()

    val juego=new Juego(filas, columnas, nColores)
    //val tablero = new Tablero()
    val ejemplo = new Ejemplo(filas, columnas, nColores)
  }
}