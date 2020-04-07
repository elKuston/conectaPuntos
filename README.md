# conectaPuntos
Se busca resolver el problema de conectar varios puntos en una cuadrícula utilizando el menor número de pasos posible. [Ver publicación original en Twitter]( https://twitter.com/TeoremaPi/status/1246159918832418816 ).

Para ello se aplica el algoritmo de búsqueda en grafos A* donde cada nodo es una posición del tablero. 
Los sucesores de un tablero son los tableros a los que se puede transitar añadiendo una casilla verde :

- Al lado de otra casilla verde, si las hay
- Al lado de una casilla roja en caso de que no haya verdes, o de que esa casilla roja haya sido alcanzada ya por alguna verde.

Como función heurística se utiliza la suma de las distancias mínimas de una casilla roja a cualquier verde. Este heurístico NO es monótono (existen casos en los que la función puede disminuir o aumentar en más de una unidad, pero el coste será de 1, por ejemplo si dos puntos rojos están en la misma dirección respecto a los verdes) ni admisible (el coste estimado por la función heurística no siempre será menor al coste real). Es por esto que aunque funciona en muchos casos, no está garantizado que la solución sea siempre óptima. Aún no se ha decidido un heurístico mejor que cumpla las propiedades mencionadas anteriormente.



Nótese que no todo el código fue implementado por mí, parte del mismo pertenece a profesores de la UMA (se puede ver en los ficheros la anotación @author para más información)
