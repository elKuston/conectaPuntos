package conectaPuntos;

import java.util.Set;

import agentesolitario.AgenteSolitario;

public class AgenteConectaPuntos extends AgenteSolitario<EstadoConectaPuntos>{

	protected AgenteConectaPuntos(EstadoConectaPuntos e) {
		super(e);
	}

	@Override
	public boolean esFinal(EstadoConectaPuntos e) {
		return sumaDistanciasMinimas(e)==0; //El estado es final si la suma de las distancias es 0
	}
	
	
	@Override
	public int h(EstadoConectaPuntos e) {
		return sumaDistanciasMinimas(e);//Heurístico temporal hasta que se encuentre uno monótono.
	}
	
	
	/**
	 * NOTA IMPORTANTE: este heurístico no es monótono. Esto implica que aunque en muchos casos pueda funcionar, NO ESTÁ GARANTIZADO que el camino encontrado sea de coste mínimo
	 * 
	 * @param e el estado actual
	 * @return la suma de las distancias mínimas entre un punto rojo y cualquier punto verde
	 */
	private int sumaDistanciasMinimas(EstadoConectaPuntos e) {
		int sum = 0;
		
		Set<Pair<Integer, Integer>> rojos = EstadoConectaPuntos.getRojos();
		
		for(Pair<Integer, Integer> roja : rojos) {
			sum+=distanciaMinima(roja, e);
		}
		
		return sum-rojos.size();//Le quitamos el numero de rojos porque tal y como está calculada la distancia, dos casillas adyacentes están a distancia 1, no 0.
		
	}
	
	/**
	 * 
	 * @param casilla casilla roja de la que se quiere hallar la distancia mínima
	 * @param e el estado actual
	 * @return la distancia mínima entre la casilla indicada y cualquier casilla verde
	 */
	private int distanciaMinima(Pair<Integer, Integer> casilla, EstadoConectaPuntos e) {
		Set<Pair<Integer, Integer>> verdes = e.getVerdes();
		int min = Integer.MAX_VALUE;
		for(Pair<Integer, Integer> v : verdes) {
			min = Math.min(min, manhattan(casilla,v));
		}
		
		return min;
		
	}
	/**
	 * 
	 * @param casilla1 la primera casilla
	 * @param casilla2 la segunda casilla
	 * @return La distania Manhattan entre las casilas indicadas
	 */
	private int manhattan(Pair<Integer, Integer> casilla1, Pair<Integer, Integer> casilla2) {
		return Math.abs(casilla1.getFirst()-casilla2.getFirst())+Math.abs(casilla1.getSecond()-casilla2.getSecond());
	}
	
	

}
