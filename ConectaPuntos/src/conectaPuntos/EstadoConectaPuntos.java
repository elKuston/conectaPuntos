package conectaPuntos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mundosolitario.OverrideHashCode;
import mundosolitario.RepresentacionEstadoOptimizacion;

public class EstadoConectaPuntos extends OverrideHashCode implements RepresentacionEstadoOptimizacion<EstadoConectaPuntos>{
	//Constantes varias, que usar constantes siempre ayuda jeje
	
	//Caracteres para la representaci�n del estado 
	public static final char CASILLA_ROJA = 'R';
	public static final char CASILLA_VERDE = 'v';
	public static final char CASILLA_VACIA = '*';
	
	
	//Los incrementos que hay que realizarle a la posicion de una casilla en x e y para obtener sus 4-vecinos
	private static final int[] incrX = {1,0,-1,0}; //Right, up, left, down
	private static final int[] incrY = {0, -1, 0, 1}; //Right, up, left, down
	
	//Estaticos para no tener que almacenarlos en cada estado, y ahorrar as� algo de memoria 
	private static Set<Pair<Integer, Integer>> rojos;	//Coordenadas (x,y) de los puntos rojos
	
	private static int tamX, tamY;
	
	private Set<Pair<Integer,Integer>> verdes;	//Coordendas (x,y) de los puntos verdes
	
	
	
	public EstadoConectaPuntos(Set<Pair<Integer, Integer>> rojos, int x, int y) {
		this.verdes = new HashSet<>();
		EstadoConectaPuntos.rojos = new HashSet<>(rojos);
		EstadoConectaPuntos.tamX = x;
		EstadoConectaPuntos.tamY = y;
	}
	
	private EstadoConectaPuntos(Set<Pair<Integer, Integer>> verdes) {
		this.verdes = new HashSet<>(verdes);
		//this.verdes = verdes;
	}
	
	
	/**
	 * @return calcula los sucesores de un estado
	 */
	
	@Override
	public List<EstadoConectaPuntos> calculaSucesores() {
		List<EstadoConectaPuntos> sucesores = new ArrayList<>();
		
		/*Set<Pair<Integer, Integer>> validas;
		if(verdes.size()==0) {//Si no hay verdes, se colocan al lado de las rojas
			validas = rojos;
		}else {//Si hay verdes, se colocan al lado de las verdes
			validas = verdes;
		}*/
		
		//Primero cargamos las casillas a las cuales se le puede colocar un verde al lado. Aqu� seguro que se puede optimizar much�simo
		Set<Pair<Integer, Integer>> validas; 
		if(verdes.size()==0) {// Si no hay verdes, se puede colocar una casilla verde al lado de cualquier casilla roja
			validas = new HashSet<>(rojos);
		}else {// Si hay verdes, podemos colocar casillas verdes al lado de cualquier otra casilla verde o de cualquier casilla roja a la que se haya llegado (es decir, tiene un vecino verde).
			//Esto �ltimo es porque si permiti�ramos colocar una casilla verde al lado de una roja a la que no se haya llegado, el algoritmo acabar�a colocando simplemente una verde al lado de cada roja
			validas = new HashSet<>(verdes);
			for(Pair<Integer, Integer> p : rojos) {
				int first = p.getFirst();
				int second = p.getSecond();
				boolean valida = false;
				int i=0;
				while(!valida && i<incrX.length) {
					Pair<Integer, Integer> probando = new Pair<>(first+incrX[i], second+incrY[i]);
					if(verdes.contains(probando)) {
						validas.add(p);
						valida = true;
					}
					i++;
				}
			}
		}
		
		//Ahora calculamos los sucesores 
		for(Pair<Integer, Integer> p : validas) {//Para cada casilla valida
			int first = p.getFirst();
			int second = p.getSecond();
			for(int i=0;i<incrX.length;i++) {//calculamos todos sus vecinos
				Pair<Integer, Integer> probando = new Pair<>(first+incrX[i], second+incrY[i]);
				if(casillaLibre(probando)) {// Y si est� libre, generamos un sucesor con ese vecino coloreado de verde
					Set<Pair<Integer, Integer>> verdesSuc = new HashSet<>(verdes);
					verdesSuc.add(probando);
					sucesores.add(new EstadoConectaPuntos(verdesSuc));
				}
			}
		}
		
		
		
		return sucesores;
	}
	
	private boolean casillaLibre(Pair<Integer, Integer> casilla) {
		return casillaEnTablero(casilla) && !rojos.contains(casilla) && !verdes.contains(casilla);
	}
	
	private boolean casillaEnTablero(Pair<Integer, Integer> casilla) {
		return casilla.getFirst()>=0 && casilla.getFirst()<tamX && casilla.getSecond()>=0 && casilla.getSecond() < tamY;
	}

	@Override
	public int costeArco(EstadoConectaPuntos eDestino) {
		return 1;//El coste de los arcos es constante, siempre 1, ya que en cada movimiento solo colocamos una ficha
	}


	public Set<Pair<Integer, Integer>> getVerdes() {
		return verdes;
	}
	
	public static Set<Pair<Integer, Integer>> getRojos() {
		return rojos;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((verdes == null) ? 0 : verdes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadoConectaPuntos other = (EstadoConectaPuntos) obj;
		if (verdes == null) {
			if (other.verdes != null)
				return false;
		} else if (!verdes.equals(other.verdes))
			return false;
		return true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<tamY;i++) {
			for(int j=0;j<tamY;j++) {
				Pair<Integer, Integer> p = new Pair<>(j,i);
				if(rojos.contains(p)) {
					sb.append(CASILLA_ROJA);
				}else if(verdes.contains(p)) {
					sb.append(CASILLA_VERDE);
				}else {
					sb.append(CASILLA_VACIA);
				}
				sb.append(" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	

}
