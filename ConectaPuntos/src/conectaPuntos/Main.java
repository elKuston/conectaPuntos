package conectaPuntos;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		//ejemplo();
		//prueba(0,10,20,20);
		ejemplo();

	}
	
	/**
	 * Resolución del ejemplo original propuesto por Teorema Pi ( https://twitter.com/TeoremaPi/status/1246159918832418816 )
	 */
	
	public static void ejemplo() {
		Set<Pair<Integer, Integer>> rojos = new HashSet<>();
		
		rojos.add(new Pair<Integer, Integer>(5,2));
		rojos.add(new Pair<Integer, Integer>(1,1));
		rojos.add(new Pair<Integer, Integer>(3,6));
		rojos.add(new Pair<Integer, Integer>(1,7));
		
		EstadoConectaPuntos e = new EstadoConectaPuntos(rojos, 7, 9);
		
		AgenteConectaPuntos agente = new AgenteConectaPuntos(e);

		long start = System.currentTimeMillis();
		List<EstadoConectaPuntos> solucion = agente.aMono();
		
		long elapsed = System.currentTimeMillis()-start;
		
		System.out.println("SOLUCION:");
		System.out.println(solucion.get(solucion.size()-1));
		System.out.println("Tiempo: "+elapsed+" ms");
	}
	/**
	 * 
	 * @param semilla la semilla para el random. Usar la misma semilla hará que se genere siempre el mismo mapa
	 * @param nRojos el número de puntos rojos 
	 * @param tamX el ancho del tablero
	 * @param tamY el alto del tablero
	 */
	
	public static void prueba(int semilla, int nRojos, int tamX, int tamY) {
		Set<Pair<Integer, Integer>> rojos = new HashSet<>();
		Random r = new Random(semilla);
		for(int i=0;i<nRojos;i++) {
			rojos.add(new Pair<>(r.nextInt(tamX), r.nextInt(tamY)));
		}
		System.out.println("Tamano malla: "+tamX+"x"+tamY+"\nn puntos rojos: "+nRojos);
		EstadoConectaPuntos e = new EstadoConectaPuntos(rojos, tamX, tamY);
		AgenteConectaPuntos agente = new AgenteConectaPuntos(e);
		long start = System.currentTimeMillis();
		List<EstadoConectaPuntos> caminoSolucion = agente.aMono();
		
		long elapsed = System.currentTimeMillis()-start;
		System.out.println("Tiempo: "+elapsed+" ms");
		System.out.println(caminoSolucion.get(caminoSolucion.size()-1));
	}

}
