package juego;


/**
 * Juego Cuatro en L�nea
 * 
 * Reglas:
 * 
 * 		...
 *
 */
public class CuatroEnLinea {
	
	private String jugadorRojo;
	private String jugadorAmarillo;
	private Casillero[][] tablero;
	private Casillero jugadorActual = Casillero.ROJO;	
	private boolean esPrimerJugador = true;
	private int[] ultimoCasillero = { 0, 0 };
	
	/**
	 * pre : 'filas' y 'columnas' son mayores o iguales a 4 y filas son menores a 8 y columnas menores a 16.
	 * post: empieza el juego entre el jugador que tiene fichas rojas, identificado como 
	 * 		 'jugadorRojo' y el jugador que tiene fichas amarillas, identificado como
	 * 		 'jugadorAmarillo'. 
	 * 		 Todo el tablero est� vac�o.
	 * 
	 * @param filas : cantidad de filas que tiene el tablero.
	 * @param columnas : cantidad de columnas que tiene el tablero.
	 * @param jugadorRojo : nombre del jugador con fichas rojas.
	 * @param jugadorAmarillo : nombre del jugador con fichas amarillas.
	 */
	public CuatroEnLinea(int filas, int columnas, String jugadorRojo, String jugadorAmarillo) {
	
		if (jugadorRojo.equals("")||jugadorAmarillo.equals("")){
			throw new Error ("Ambos jugadores deben tener un nombre.");
		}
		if((filas<4 || columnas<4)||(filas > 8 || columnas > 16)){
			throw new Error("Medidas del tablero invalidas, el tablero debe ser 4x4 como m�nimo y 8x16 como maximo." );
		}
		tablero = new Casillero[filas][columnas];
		
		for(int i=0; i < filas ; i++){
			for(int j=0 ; j < columnas; j++){
				tablero[i][j] = Casillero.VACIO;
			}
		}
		this.jugadorRojo = jugadorRojo;
		this.jugadorAmarillo = jugadorAmarillo;

	}
	/**
	 * post: devuelve la cantidad m�xima de fichas que se pueden apilar en el tablero.
	 */
	public int contarFilas() {

		return tablero.length;
	}
	/**
	 * post: devuelve la cantidad m�xima de fichas que se pueden alinear en el tablero.
	 */
	public int contarColumnas() {
		
		return tablero[0].length;
	}

	/**
	 * pre : fila est� en el intervalo [1, contarFilas()],
	 * 		 columnas est� en el intervalo [1, contarColumnas()].
	 * post: indica qu� ocupa el casillero en la posici�n dada por fila y columna.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Casillero obtenerCasillero(int fila, int columna) {
		
		return 	tablero[fila-1][columna-1];
	}
	
	/**
	 * pre : el juego no termin�, columna est� en el intervalo [1, contarColumnas()]
	 * 		 y a�n queda un Casillero.VACIO en la columna indicada. 
	 * post: deja caer una ficha en la columna indicada.
	 * 
	 * @param columna
	 */
	public void soltarFichaEnColumna(int columna) {
		
		int ultimaFila = tablero.length - 1;
        columna --;

        if (!termino()){
            while(ultimaFila > 0 && tablero[ultimaFila][columna] != Casillero.VACIO){
                ultimaFila--;
            }
            if (ultimaFila >= 0 && tablero[0][columna] == Casillero.VACIO) {
                if (esPrimerJugador) {
                    tablero[ultimaFila][columna] = Casillero.ROJO;
                    
                } else {
                    tablero[ultimaFila][columna] = Casillero.AMARILLO;
                }
               
                // Guardo el ultimo casillero y cambio de jugador
                ultimoCasillero[0] = ultimaFila;
                ultimoCasillero[1] = columna;
                

                // Si el juego no termina con la ficha colocada, cambio el
                // jugador
                
                esFichaGanadora ( ultimaFila,  columna);
                if (!termino()) {
                    esPrimerJugador = !esPrimerJugador;
                    
                }
            }
        }
	}
   
	private boolean esFichaGanadora ( int fila, int columna){
		Casillero ultimoColor = tablero[fila][columna];
		
		if( (cuatroEnLineaHorizontal( ultimoColor, fila, columna) == true )||
				(cuatroEnLineaverticalAbajo( ultimoColor, fila, columna)  == true ) || 
				(DiagonalPositiva( ultimoColor, fila, columna) == true) || (diagonalNegativa (ultimoColor,fila,columna) == true) ){
			return true;
		}else{
			return false;
		}
		
//				
//		
//		k = fila - 1;
//		j = columna - 1;
//		
//		while ( (cantidadDeColores < 4) && (j >=0) && (k > 0) && (k < tablero.length) && (tablero[k][j] == ultimoColor) ){
//			cantidadDeColores++;
//			j--;
//			k--;
//			if(cantidadDeColores == 4){
//				ganoAlguien = true;
//			}
//		}
		
	
	}
	//                                                                     0           5
	private boolean cuatroEnLineaHorizontal (Casillero ultimoColor  ,int fila, int columna){
        int fichasIguales = 1;
        int j;
        boolean hayGanador = false;

//		HORIZONTAL IZQUIERDA        
        if(columna > 0){
        	j = columna - 1;

        	while(tablero[fila][j] == ultimoColor && j > 0){            	           	
        		fichasIguales++;
        		j--;
        		if(columna == 3 && fichasIguales == 3 && tablero[fila][0] == ultimoColor){    	
        			fichasIguales++;
        		}
        		if(fichasIguales >= 4){
        			System.out.println("gano");
        			hayGanador = true;
        		} 
        	}
        }
        
//	 	HORIZONTAL DERECHA

        j = columna + 1;       

        if( j < tablero[0].length){

        	while ( j <  tablero[0].length && tablero[fila][j] == ultimoColor){
        		fichasIguales++;
        		j++;
        		if(fichasIguales >= 4){
        			System.out.println("gano");
        			hayGanador = true;

        		}

        	}

        }

        return hayGanador;
    }

//																			0			
	private boolean cuatroEnLineaverticalAbajo(Casillero ultimoColor, int fila, int columna){
		int fichasIguales = 1;
		int i = fila + 1;
		//vertical para abajo
		if(fila <= 3){
			while (i < tablero.length && tablero[i][columna] == ultimoColor ){

				fichasIguales++;
				i++;
				if(fichasIguales >=4){
					System.out.println("gano");
					return true;
				}
			}
		}
		return false;
	}
	
	
	private boolean DiagonalPositiva(Casillero ultimoColor, int fila, int columna){
		int fichasIguales = 1;
		int i, j;
		boolean ganadorHorizontal = false;
		
		if (fila > 0) {
			
			i = fila - 1;
			j = columna + 1;
//	A LA DERECHA Y ARRIBA
			while (i >= 0 && j < tablero[0].length && tablero[i][j]== ultimoColor){
				fichasIguales++;
				if(fichasIguales >= 4){
					ganadorHorizontal = true;
					System.out.println("gano");
				}
				i--;
				j++;
				
			}
			if(columna > 0){
				i = fila + 1;
				j = columna - 1;
//	A LA IZQUIERDA Y ABAJO
				while(i < tablero.length && j >= 0 && tablero[i][j] == ultimoColor){
					fichasIguales ++;
					i++;
					j--;
					
					if(fichasIguales >= 4){
						ganadorHorizontal = true;
						System.out.println("gano");
					}
				}
			}
			
			
		}
		
		return ganadorHorizontal;
	}
	private boolean diagonalNegativa (Casillero ultimoColor, int fila, int columna){
		int i,j;
		int fichasIguales = 1;
		boolean ganadorDiagonal = false;

		// PROBLEMA -1
		// A LA IZQUIERDA Y ARRIBA		
		if (fila >0 && columna < tablero[0].length){
			i= fila-1;
			j= columna-1;	

			while (i > 0 && j < tablero[0].length && tablero[i][j]== ultimoColor){
				fichasIguales++;
				i--;
				j--;
				if(fichasIguales >= 4){
					ganadorDiagonal = true;
					System.out.println("gano");
				}
			}
		}
		// PROBLEMA 7
		// ABAJO Y A LA DERECHA
		if((columna > 0 && columna <= tablero[0].length-4) && fila < tablero.length-1){
			i = fila+1;
			j = columna +1;

			while(i < tablero.length && j >=0 && tablero[i][j] == ultimoColor){
				fichasIguales++;
				i++;
				j++;
				if(fichasIguales >=4){
					ganadorDiagonal = true;
					System.out.println("gano");
				}
			}
		}
		return ganadorDiagonal;
	}
	
	/**
	 * post: indica si el juego termin� porque uno de los jugadores
	 * 		 gan� o no existen casilleros vac�os.
	 */
	public boolean termino() {
		boolean juegoFinalizado = false;
		int casillerosPintados = 0 ;

		for (int filas = 0 ; filas < tablero.length; filas++ ){
			for (int columnas = 0; columnas < (tablero[filas].length) ; columnas++){
				if(tablero[filas][columnas] != Casillero.VACIO){
					casillerosPintados++;
				}				

				if(casillerosPintados == ((tablero.length)*(tablero[filas].length) )){
					hayGanador();
					juegoFinalizado = true;
				}

			}	
		}	
		return juegoFinalizado;
	}

	/**
	 * post: indica si el juego termin� y tiene un ganador.
	 */
	public boolean hayGanador() {

		return false;
	}

	/**
	 * pre : el juego termin�.
	 * post: devuelve el nombre del jugador que gan� el juego.
	 */
	public String obtenerGanador() {

		return null;
	}
}
