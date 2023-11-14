# Proyecto 2: Mundo Chiquito

## Descripción

En el popular juego de cartas _JCC_ (Juego de Cartas Coleccionables) **Duelo de cartas mostro**, existen dos clases de cartas, cartas **mostro** y cartas **conjuro**.

Las cartas mostro representan a criaturas que combaten en nombre de su duelista. Las cartas mostro tienen distintas **características** que las distinguen. Cada mostro tiene un **nombre** único, un **nivel** representado por un núumero entero entre 1 y 12, una magnitud de **poder** representada por un número entero múltiplo de 50 y un **atributo** que puede ser cualquiera entre **AGUA, FUEGO, VIENTO, TIERRA, LUZ, OSCURIDAD** o **DIVINO**.

Las cartas conjuro son más sencillas, cada una contiene instrucciones que el jugador de turno que la utiliza debe aplicar. Existe una carta conjuro muy particular llamada **Mundo Chiquito** cuyo texto es el siguiente:

> Mundo Chiquito
>
> Muéstrale a tu oponente una carta **mostro** de tu mano, elige un **mostro** de tu mazo que comparta
> exactamente una característica (nivel, poder o atributo) con el **mostro** revelado de tu mano y
> muéstraselo a tu oponente. Luego agrega desde tu mazo a tu mano un **mostro** que comparta
> exactamente una característica (nivel, poder o atributo) con el mostro revelado de tu mazo.

Es por ello que los jugadores competitivos de **Duelo de cartas de mostro** acudieron a los estudiantes de la **Universidad Simón Bolívar** para desarrollar un algoritmo que les ayude a encontrar todas las posibles combinaciones de cartas mostro que satisfacen las condiciones de Mundo chiquito.

## Estructura de Archivos

- **CartaMostro.java**: Implementa la clase **CartaMostro**, que tiene un atributo privado por cada característica de una carta mostro (nombre, nivel, poder, atributo), el correspondiente constructor y los métodos getter de cada característica.
- **AdjencyListGraphProj2.java**: Implementa la clase **AdjencyListGraphProj2**, que es la representación de un grafo no dirigido, donde cada carta es un vértice y cada arista representa una relación entre dos cartas mostro que comparten una sola característica en común.
- **GraphProj2.java**: Implementa la interfaz **GraphProj2**, que define los métodos que debe implementar un grafo no dirigido para este proyecto.
- **MundoChiquito.java**: Archivo principal del proyecto, que contiene el método main y el algoritmo que resuelve el problema planteado. Se encarga de leer el archivo _deck.csv_ y construir el grafo no dirigido correspondiente, luego ejecuta el algoritmo que resuelve el problema y finalmente imprime la solución en la terminal.
- **deck.csv**: Archivo que contiene el deck de cartas mostro del jugador que ejecuta el programa. El formato del archivo es el siguiente:

  ```
  Nombre,Nivel,Poder,Atributo
  Nombre1,Nivel1,Poder1,Atributo1
  Nombre2,Nivel2,Poder2,Atributo2
  Nombre3,Nivel3,Poder3,Atributo3
  ...
  ```

  Es importante agregar la primera línea con los nombres de las características de las cartas mostro, para que el programa pueda leer el archivo correctamente.

  Es decir, se empiezan a leer cartas a partir de la segunda línea, y cada característica de la carta mostro está separada por una coma.

  Nótese que es una sola carta mostro por línea, y no se deben poner cartas repetidas. Ya que el algoritmo toma en cuenta la posibilidad de cartas repetidas de por sí y no es necesario ponerlas en el archivo.

## Compilación y Ejecución

Para compilar el programa, se debe ejecutar el siguiente comando en la terminal:

```java
javac MundoChiquito.java
```

Para ejecutar el programa, se debe ejecutar el siguiente comando en la terminal:

```java
java MundoChiquito
```

**Nota**: Debe existir un archivo _deck.csv_ en el mismo directorio que el resto del proyecto, con el formato descrito en la sección anterior.

## Explicación de la Solución Propuesta (con Complejidades)

Para resolver el problema planteado, se propuso utilizar un algoritmo inspirado en la técnica de **Backtracking**. El algoritmo que resuelve el problema se encuentra en el método `findCombinations` de la clase `MundoChiquito`.

_Backtracking_ es una técnica de programación que se basa en la búsqueda exhaustiva de todas las posibles soluciones a un problema, y que va descartando las soluciones que no satisfacen las restricciones del problema. Es decir, se va construyendo una solución candidata, y si esta no satisface las restricciones del problema, se descarta y se construye otra solución candidata. Este proceso se repite hasta que se encuentren todas las soluciones candidatas que satisfagan las restricciones del problema, probando todas las posibles combinaciones de soluciones candidatas.

Así, para la resolución de este problema, empezamos por crear una lista de carta mostro que representa el mazo y creamos un grafo no dirigido para representar la conexión entre cartas que solo tengan una característica en común entre sí, (esto es un grafo no dirigido, ya que es una relación recíproca). Luego llamamos al método `findCombinations` con el grafo como parámetro, que a su vez llama al método `findCombinationsRec` que es su método recursivo auxiliar.

El método `findCombinationsRec` busca todas las posibles combinaciones de cartas mostro que satisfacen las condiciones de Mundo Chiquito. Para ello, en vez de buscar todas las posibles combinaciones de cartas mostro, busca todas las posibles combinaciones de cartas mostro que estén conectadas en el grafo. Es decir, empieza con cualquier carta mostro del mazo, y luego busca todas las posibles combinaciones de segundas cartas mostro que estén conectadas con esa primera carta mostro, y para cada una de esas segundas cartas mostro, busca todas las posibles combinaciones de terceras cartas mostro que estén conectadas con la segunda carta mostro. Una vez añadidas todas estas posibilidades, cambia la primera carta por otra del mazo y repite el proceso. Esto se repite hasta que se hayan probado todas las posibles combinaciones de cartas mostro que estén conectadas entre sí.

Con esto logramos reducir espacio de búsqueda y así reducir la complejidad del caso promedio y del mejor caso del algoritmo. La complejidad del peor caso no se puede reducir, ya que en el peor caso todas las cartas mostro están conectadas entre sí, y por lo tanto se deben probar todas las posibles combinaciones de cartas mostro.

La otra diferencia que hay con un algoritmo de _Backtracking_ tradicional, es que no hace falta probar que la solución candidata satisfaga las restricciones del problema, ya que el grafo se encarga de eso. Es decir, si la solución candidata no satisface las restricciones del problema, entonces el grafo no tendrá una arista entre dos cartas mostro de la solución candidata, y por lo tanto no se considerará como una solución válida.

Por eso decimos que nos inspiramos en la técnica de _Backtracking_, ya que no es un algoritmo de _Backtracking_ tradicional, pero se basa en la misma idea, pero con algunas modificaciones para reducir la complejidad del caso promedio y del mejor caso. Para esto se usa el método `cartasValidas` que proporciona la lista de cartas mostro con la que se debe construir o completar la solución candidata.

Dibujamos una tabla con la complejidad de cada método:

| Método              | Complejidad |
| ------------------- | ----------- |
| cargarMazo          | O(\|V\|)    |
| createDeckGraph     | O(\|V\|)    |
| conectarCartas      | O(\|V\|^2)  |
| unaCaractEnComun    | O(1)        |
| findCombinations    | O(\|V\|^3)  |
| findCombinationsRec | O(\|V\|^3)  |
| cartasValidas       | O(1)        |

Donde `|V|` es la cardinalidad del grafo no dirigido del mazo, es decir, la cantidad de cartas mostro en el mazo.

## Grupo de Laboratorio

- Juan Cuevas [@cuevasrja](https://github.com/cuevasrja) (19-10056).
- Luis Isea [@lmisea](https://github.com/lmisea) (19-10175).
