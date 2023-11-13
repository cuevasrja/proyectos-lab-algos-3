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

Dibujamos una tabla con la complejidad de cada método:

| Método | Complejidad |
| ------ | ----------- |
|        |             |

**Nota**:

## Grupo de Laboratorio

- Juan Cuevas [@cuevasrja](https://github.com/cuevasrja) (19-10056).
- Luis Isea [@lmisea](https://github.com/lmisea) (19-10175).
