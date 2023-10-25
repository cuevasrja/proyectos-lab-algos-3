# Proyecto 1: Grafos con Listas de Adyacencia

## Descripción

En este proyecto se implementará la representación de un Grafo a través de **listas de adyacencia** (adjacency lists). Esta implementación vive en la clase _AdjacencyListGraph_ basada en la interface _Graph_ dada en clase por el profesor Leonardo López.

## Estructura de Archivos

- **Graph.java**: Archivo que contiene la interface _Graph_ dada en clase por el profesor.
- **AdjacencyListGraph.java**: Archivo que contiene la clase _AdjacencyListGraph_ que implementa la representación de un Grafo a través de listas de adyacencia.

## Explicación de la Solución Propuesta (con Complejidades)

La primera decisión que tomamos fue implementar una lista de adyacencia usando HashMap, en lugar de implementar una lista de listas. Esto se debe a que un HashMap ofrece métodos que son de complejidad O(1), como _put_, _get_ y _remove_. Por lo que la complejidad de los métodos de la lista de adyacencia se verá beneficiada. En cambio, una lista de listas no ofrece la misma eficiencia y los métodos de la lista de adyacencia tendrían una complejidad mayor.

Además, un HashMap nos permite mapear cada vértice a una lista, por lo que nos mantenemos fieles al TAD lista de adyacencia, que es el objetivo de este proyecto.

No obstante, lo más interesante de nuestra implementación de una lista de adyacencia es que decidimos no usar un HashMap, sino dos.
Esto con el objetivo de contrarrestar unos de los puntos débiles de las listas de adyacencia, que es la búsqueda de los predecesores de un vértice. Para esto, usamos un HashMap que mapea cada vértice a una lista de sus predecesores, y otro que mapea cada vértice a una lista de sus sucesores. De esta manera, la búsqueda de los predecesores de un vértice es de complejidad O(1), y la búsqueda de los sucesores de un vértice es de complejidad O(1) también.

Esto se logra prácticamente duplicando la cantidad de memoria usada, pero obtenemos una mejora en la complejidad y en tiempo de ejecución, que consideramos vale la pena. Además, la complejidad de los otros métodos no se ve afectada, lo que contribuye a nuestra decisión de usar dos HashMaps.

Como agregado, también implementamos el método _**toString**_ para poder imprimir el grafo de una manera más legible. A continuación, se explica un poco más en detalle la complejidad de cada método:

1. Método _**add**_: Como la clase _AdjacencyListGraph_ utiliza dos HashMaps, entonces no se agrega el vértice a un HashMap de vértices.
   Sino que se agrega a dos HashMaps, uno donde cada vértice se mapea a una lista de sus predecesores, y otro donde cada vértice se mapea a una lista de sus sucesores.
   Esto es de complejidad O(1) para cada HashMap, por lo que la complejidad total es **O(1)**.

2. Método _**connect**_: En el HashMap de sucesores al vértice _from_ se le mapea el vértice _to_,
   y en el HashMap de predecesores al vértice _to_ se le mapea el vértice _from_.
   Esto es de complejidad O(1) para cada HashMap, por lo que la complejidad total es **O(1)**.

3. Método _**disconnect**_: El HashMap de sucesores, nos regresa la lista de sucesores del vértice _from_,
   y en esa lista buscamos el vértice _to_ y lo removemos. El HashMap de predecesores, nos regresa la lista de
   predecesores del vértice _to_, y en esa lista buscamos el vértice _from_ y lo removemos. Esto es de complejidad
   O(n) para cada lista, donde n es la cantidad de vértices del grafo. Por lo que la complejidad total es **O(n)**.

4. Método _**contains**_: Se busca el vértice en cualquiera de los dos HashMaps, lo que es de complejidad **O(1)**.
   En particular, nosotros lo buscamos en el HashMap de sucesores, pero se puede buscar en cualquiera de los dos. Ya que al agregar un vértice, se agrega a ambos HashMaps, entonces si está en uno, también está en el otro.

5. Método _**getInwardEdges**_: Acá es donde se ve la ventaja de usar dos HashMaps. En el HashMap de predecesores al vértice _v_
   se le mapea una lista de sus predecesores, por lo que simplemente se retorna esa lista. Esto es de complejidad **O(1)**.

6. Método _**getOutwardEdges**_: En el HashMap de sucesores al vértice _v_ se le mapea una lista de sus sucesores,
   por lo que simplemente se retorna esa lista. Esto es de complejidad O(1).

7. Método _**getVerticesConnectedTo**_: Se crea una lista, y se recorre la lista de sucesores y la lista
   de predecesores del vértice _v_, y se agregan a la lista creada todos los vértices que de estas listas.
   Luego se seleccionan los vértices no repetidos de la lista creada y se retorna esa lista.
   Esto es de complejidad O(n) por cada lista (la de predecesores y la de sucesores), donde n es la cantidad
   de vértices del grafo. Y devolver los vértices no repetidos es de complejidad O(n) también.
   Por lo que la complejidad total es **O(n)**.

8. Método _**getAllVertices**_: Se retorna el conjunto de llaves de cualquiera de los dos HashMaps, lo que es de complejidad **O(1)**.
   En particular, nosotros lo retornamos del HashMap de sucesores, pero se puede retornar del HashMap de predecesores también.

9. Método _**remove**_: Para remover el vértice _v_, tenemos que removerlo de ambos HashMaps y remover todos los lados
   en los que incide. Para esto, recorremos la lista de predecesores del vértice _v_ y removemos el vértice _v_ de la lista de sucesores de cada uno de sus predecesores. Luego, recorremos la lista de sucesores del vértice _v_ y removemos el vértice _v_ de la lista de predecesores de cada uno de sus sucesores. Finalmente, removemos el vértice _v_ de ambos HashMaps.
   Para la complejidad, tenemos que recorrer m listas donde m es la cantidad de lados que inciden en el vértice _v_, y en el peor de los casos, las listas tienen tamaño n, donde n es la cantidad de vértices del grafo. Por lo que la complejidad total es **O(m\*n)**.

10. Método _**size**_: Recordemos que el tamaño de un HashMap es la cantidad de llaves que tiene. Y en este caso,
    las llaves son los vértices. Por lo que simplemente retornamos el tamaño de cualquiera de los dos HashMaps,
    lo que es de complejidad **O(1)**.
    En particular, nosotros lo retornamos del HashMap de sucesores, pero se puede retornar del HashMap de predecesores también.

11. Método _**subgraph**_: Para retornar el subgrafo inducido por el conjunto de vértices _vertices_, simplemente creamos
    un nuevo grafo y agregamos todos los vértices de _vertices_ al nuevo grafo. Luego, recorremos todos los vértices de
    _vertices_ y para cada uno, recorremos su lista de sucesores y agregamos los lados que inciden en el vértice _v_ y en
    otro vértice de _vertices_ al nuevo grafo. Finalmente, retornamos el nuevo grafo.
    Para la complejidad, tenemos que recorrer n listas donde n es la cantidad de vértices de _vertices_, y en el peor de los casos,
    las listas tienen tamaño m, donde m es la cantidad de lados que inciden en el vértice _v_.
    Por lo que la complejidad total es **O(m\*n)**.

12. Método _**toString**_: Este método simplemente recorre todos los vértices del grafo y los imprime junto con sus sucesores.
    Esto es de complejidad **O(n)**, donde n es la cantidad de vértices del grafo.

Dibujamos una tabla con la complejidad de cada método:

| Método                 | Complejidad |
| ---------------------- | ----------- |
| add                    | O(1)        |
| connect                | O(1)        |
| disconnect             | O(n)        |
| contains               | O(1)        |
| getInwardEdges         | O(1)        |
| getOutwardEdges        | O(1)        |
| getVerticesConnectedTo | O(n)        |
| getAllVertices         | O(1)        |
| remove                 | O(m\*n)     |
| size                   | O(1)        |
| subgraph               | O(m\*n)     |
| toString               | O(n)        |

**Nota**: m es la cantidad de lados que inciden en el vértice _v_, y n es la cantidad de vértices del grafo.

## Grupo de Laboratorio

- Juan Cuevas [@cuevasrja](https://github.com/cuevasrja) (19-10056).
- Luis Isea [@lmisea](https://github.com/lmisea) (19-10175).
