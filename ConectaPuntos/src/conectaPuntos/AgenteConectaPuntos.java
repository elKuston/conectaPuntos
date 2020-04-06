package conectaPuntos;

import java.util.Set;

import agentesolitario.AgenteSolitario;
//random comment
public class AgenteConectaPuntos extends AgenteSolitario<EstadoConectaPuntos>{

	protected AgenteConectaPuntos(EstadoConectaPuntos e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean esFinal(EstadoConectaPuntos e) {
		return h(e)==0;
	}
	
	@Override
	public int h(EstadoConectaPuntos e) {
		int sum = 0;
		
		for(Pair<Integer, Integer> roja : EstadoConectaPuntos.getRojos()) {
			sum+=distanciaMinima(roja, e);
		}
		
		return sum-EstadoConectaPuntos.getRojos().size();
	}
	
	private int distanciaMinima(Pair<Integer, Integer> casilla, EstadoConectaPuntos e) {
		Set<Pair<Integer, Integer>> verdes = e.getVerdes();
		int min = Integer.MAX_VALUE;
		for(Pair<Integer, Integer> v : verdes) {
			min = Math.min(min, manhattan(casilla,v));
		}
		
		return min;
		
	}
	
	private int manhattan(Pair<Integer, Integer> casilla1, Pair<Integer, Integer> casilla2) {
		return Math.abs(casilla1.getFirst()-casilla2.getFirst())+Math.abs(casilla1.getSecond()-casilla2.getSecond());
	}
	
	

}
