# Proyecto 3: Mundo Cubo

## Descripción

Alfonso José es un gran fanático del popular juego _Mundo Cubo_. En este juego, todo es un cubo, el agua, la madera, la tierra, etc. Alfonso José es tan fanático del juego que quiere construir su propia atlantis, pero no sabe cuántos cubos de agua necesita para llenar la ciudad lo más posible.

Es por esto que Alfonso José le pide ayuda a los estudiantes de la Universidad Simón Bolívar para que le ayuden a calcular la cantidad de cubos de agua que necesita para llenar la ciudad lo más posible.

## Estructura de Archivos

- **AlfonsoJose.java**: Implementa la clase **AlfonsoJose**, cuyo método `main` lee el archivo _atlantis.txt_ y devuelve la cantidad de cubos de agua que se necesitan para llenar el mundo cubo lo más posible.
- **atlantis.txt**: Archivo que contiene una matriz que representa la altura de las torres de cubos que conforman la ciudad. El formato del archivo es el siguiente:

  ```
  3 3 4 4 4 2
  3 1 3 2 2 4
  7 3 1 6 4 1
  ...
  ```

  Donde cada número representa la altura de una torre de cubos en la ciudad en la posición correspondiente de la matriz. Por ejemplo, el número 3 en la posición (0, 0) de la matriz representa una torre de cubos de altura 3 en la posición (0, 0) de la ciudad.

## Compilación y Ejecución

Para compilar el programa, se debe ejecutar el siguiente comando en la terminal:

```java
javac AlfonsoJose.java
```

Para ejecutar el programa, se debe ejecutar el siguiente comando en la terminal:

```java
java AlfonsoJose
```

> [!IMPORTANT]
> Debe existir un archivo _atlantis.txt_ en el mismo directorio que el resto del proyecto, con el formato descrito en la sección anterior.

## Explicación de la Solución Propuesta (con Complejidades)

Para resolver el problema, se implementó la clase `AlfonsoJose`, la cual contiene un método `main` que lee el archivo _atlantis.txt_ y devuelve la cantidad de cubos de agua que se necesitan para llenar el mundo cubo lo más posible. Primeramente, se lee el archivo y se almacena en una matriz de enteros. Luego, se crea un grafo dirigido con la misma cantidad de vértices que la matriz, donde cada vértice representa una posición de la matriz. Luego, se recorre la matriz y se agregan las aristas correspondientes al grafo, de manera que los
arcos estan definidos de la siguiente manera:

    Para todo par de vértices (i, j) y (k, l) en el grafo, existe un arco de (i, j) a (k, l) si y solo si la altura de la torre de cubos en la posición (i, j) es mayor que la altura de la torre de cubos en la posición (k, l) y (i, j) y (k, l) son adyacentes en la matriz.

Luego, se calculan las componentes fuertemente conexas del grafo usando el metodo `componentesFuertementeConexas`. Posteriormente, se crea un grafo reducido a partir del grafo original usando el método `reducirGrafo`, en el cual cada componente fuertemente conexa del grafo original se convierte en un vértice del grafo reducido. Luego, se calculan los cubos máximos de agua que se pueden almacenar en cada componente fuertemente conexa usando el método `cubosMaximos`. 

Dentro de `cubosMaximos`, iteramos hasta que el grafo reducido no tenga vértices sumideros, es decir, vértices que no tengan arcos salientes pero sí arcos entrantes. En cada iteración, se calculan los vértices sumideros del grafo reducido usando el método `getSumideros`, e iteramos sobre los vértices sumideros. Si el vértice sumidero es un borde, entonces se elimina del grafo reducido y continuamos con el siguiente vértice sumidero. 

Si el vértice sumidero no es un borde, entonces se calcula el predecesor mínimo del vértice sumidero usando el método `predecesorMin`, luego actualizamos nuestra variable de resultado sumándole el costo del predecesor mínimo del vértice sumidero menos el sumidero, por el tamaño de la componente fuertemente conexa del vértice sumidero calculado usando el método `getSizeCFC`. Posteriormente, se actualiza el costo de la componente fuertemente conexa del vértice sumidero usando el método `actualizarCostoCFC`. Finalmente, se unen las componentes fuertemente conexas del vertice sumidero y su predecesor mínimo usando el método `unirCFC`.

Al no tener vértices sumideros, podemos entender que la ciudad está llena de agua lo más posible, por lo que devolvemos el resultado.

| Método                        | Complejidad       |
| ----------------------------- | ------------------ |
| colorear                      | O(1)               |
| imprimirCC                    | O(\|CC\|)          |
| imprimirMatriz                | O(\|V\|)           |
| leerArchivo                   | O(\|V\|)           |
| construirGrafo                | O(\|V\|*\|E\|)     |
| componentesFuertementeConexas | O(\|V\|*\|E\|)     |
| dfs                           | O(\|V\|*\|E\|)     |
| simetrico                     | O(\|V\|*\|E\|)     |
| reducirGrafo                  | O(\|V\|*\|E\|)     |
| perteneceAComponente          | O(\|V\|)           |
| cubosMaximos                  | O(\|V\|*\|E\|)     |
| esBorde                       | O(1)               |
| hayUnBorde                    | O(\|V\|)           |
| getSumideros                  | O(\|V\|*\|E\|)     |
| predecesorMin                 | O(\|E\|)           |
| getSizeCFC                    | O(\|V\|)           |
| actualizarCostoCFC            | O(\|V\|)           |
| unirCFC                       | O(\|V\|)           |
| buscarComponente              | O(\|V\|)           |

> [!IMPORTANT]
> Se tiene que \|V\| es la cantidad de vértices del grafo original, y \|E\| es la cantidad de arcos del grafo original. Además, \|CC\| es la cantidad de componentes fuertemente conexas del grafo original.

> [!NOTE] 
> Más detalles sobre la complejidad de cada método se encuentran en los comentarios del código de la clase `AlfonsoJose`.

## Grupo de Laboratorio

- Juan Cuevas [@cuevasrja](https://github.com/cuevasrja) (19-10056).
- Luis Isea [@lmisea](https://github.com/lmisea) (19-10175). NO TRABAJÓ EN EL PROYECTO.
