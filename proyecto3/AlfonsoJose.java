import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AlfonsoJose {
    public static void main(String[] args) {
        String archivo = "atlantis.txt";
        int[][] matriz = leerArchivo(archivo);
        System.out.println("Matriz original:");
        imprimirMatriz(matriz);

        int filas = matriz.length;
        int columnas = matriz[0].length;
        // Creamos la matriz de estados con los valores inicializados
        Estados[][] estados = matrizEstados(filas, columnas);

        // Ejecutamos Floyd-Warshall para saber donde se pueden colocar bloques de agua sin que se inunde la ciudad
        int max = floydWarshall(matriz, estados, filas, columnas);

        System.out.println("Matriz final:");
        imprimirMatriz(matriz);

        System.out.println("Matriz de estados:");
        imprimirEstados(estados);

        System.out.println("Se pueden colocar " + max + " bloques de agua");
    }

    /**
     * Imprime la matriz
     * Complejidad: O(|V|) donde |V| es la cantidad de vertices del grafo
     * @param matriz
     */
    public static void imprimirMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++){
            for (int j = 0; j < matriz[i].length; j++){
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Imprime la matriz de estados
     * Complejidad: O(|V|) donde |V| es la cantidad de vertices del grafo
     * @param estados
     */
    public static void imprimirEstados(Estados[][] estados) {
        for (int i = 0; i < estados.length; i++){
            for (int j = 0; j < estados[i].length; j++){
                System.out.println(i + ", " + j + ": " +estados[i][j].toString());
            }
        }
    }

    /**
     * Revisa si la posicion actual es un borde
     * @param i
     * @param j
     * @param filas
     * @param columnas
     * @return true si es borde, false si no
     */
    public static Boolean esBorde(int i, int j, int filas, int columnas) {
        return (i == 0 || i == filas - 1 || j == 0 || j == columnas - 1);
    }

    /**
     * Lee el archivo y devuelve la matriz de enteros con los datos (n*m)
     * Complejidad: O(|V|) donde |V| es la cantidad de vertices del grafo
     * @param path
     * @return matriz
    */
    public static int[][] leerArchivo(String path) {
        int[][] matriz = null;
        try {
            File archivo = new File(path);
            Scanner sc = new Scanner(archivo);
            // Leemos la cantidad de lineas del archivo y esas son las filas de la matriz
            int filas = 0;
            while (sc.hasNextLine()) {
                filas++;
                sc.nextLine();
            }
            sc.close();
            // Leemos la cantidad de columnas del archivo y esas son las columnas de la matriz
            sc = new Scanner(archivo);
            String linea = sc.nextLine();
            sc.close();
            sc = new Scanner(archivo);
            String[] valores = linea.split(" ");
            int columnas = valores.length;
            matriz = new int[filas][columnas];
            // Llenamos la matriz con los datos del archivo
            int i = 0;
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                valores = linea.split(" ");
                for (int j = 0; j < valores.length; j++) {
                    matriz[i][j] = Integer.parseInt(valores[j]);
                }
                i++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo");
        }
        return matriz;
    }

    /**
     * Crea la matriz de estados
     * Complejidad: O(|V|) donde |V| es la cantidad de vertices del grafo
     * @param filas
     * @param columnas
     * @return estados
    */
    public static Estados[][] matrizEstados(int filas, int columnas) {
        // Creamos la matriz de estados con los valores inicializados
        Estados[][] estados = new Estados[filas][columnas];
        // Llenamos la matriz de estados con los valores inicializados
        for (int i = 0; i < estados.length; i++){
            for (int j = 0; j < estados[i].length; j++){
                // Si es borde, el estado es VACIO
                if (esBorde(i, j, filas, columnas)) {
                    estados[i][j] = new Estados(Estado.VACIO);
                }
                // Si no es borde, el estado es DESCONOCIDO
                else{
                    estados[i][j] = new Estados(Estado.DESCONOCIDO);
                }
            }
        }
        return estados;
    }

    /**
     * Coloca bloques de agua en la posicion actual y en las posiciones adyacentes si es posible
     * Complejidad: O(?)
     * @param matriz
     * @param estados
     * @param i
     * @param j
     * @param mutableInt
    */
    public static void fill(int[][] matriz, Estados[][] estados, int i, int j, int[] mutableInt) {
        if (!esBorde(i, j, matriz.length, matriz[0].length)){
            // Revisamos si el bloque de agua se puede colocar en la posicion actual (Si los adyacentes son mas altos o si se puede color agua en ellos)
            int arriba = matriz[i - 1][j];
            int abajo = matriz[i + 1][j];
            int izquierda = matriz[i][j - 1];
            int derecha = matriz[i][j + 1];
            // Si se puede colocar el bloque de agua en la posicion actual, se coloca
            if (arriba > matriz[i][j] && abajo > matriz[i][j] && izquierda > matriz[i][j] && derecha > matriz[i][j]) {
                // Tomamos la altura minima de los adyacentes
                int hMin = Math.min(Math.min(arriba, abajo), Math.min(izquierda, derecha));
                // Cambiamos el estado de la posicion actual a LLENO
                estados[i][j].setStatus(Estado.LLENO);
                estados[i][j].setFillable(true);
                // Calculamos la diferencia de altura entre la posicion actual y la altura minima de los adyacentes
                estados[i][j].setH(hMin - matriz[i][j]);
                matriz[i][j] = hMin;
                // Sumamos la diferencia de altura a la cantidad de bloques de agua que se pueden colocar
                mutableInt[0] += estados[i][j].getH();
            }
            else {
                // ! Este caso no funciona bien
                // TODO: Revisar este caso
                estados[i][j].setStatus(Estado.VACIO);
                int hMin = Math.min(Math.min(arriba, abajo), Math.min(izquierda, derecha));
                int minI;
                int minJ;
                if (hMin == arriba) {
                    minI = i - 1;
                    minJ = j;
                }
                else if (hMin == abajo) {
                    minI = i + 1;
                    minJ = j;
                }
                else if (hMin == izquierda) {
                    minI = i;
                    minJ = j - 1;
                }
                else {
                    minI = i;
                    minJ = j + 1;
                }
                if (estados[minI][minJ].getStatus() == Estado.DESCONOCIDO) {
                    fill(matriz, estados, minI, minJ, mutableInt);
                    fill(matriz, estados, i, j, mutableInt);
                }
                else if (estados[minI][minJ].getStatus() == Estado.LLENO) {
                    // Revisamos si se pueden llenar los adyacentes
                    int arriba2 = (minI - 1 == i && minJ == j) ? Integer.MAX_VALUE : matriz[minI - 1][minJ];
                    int abajo2 = (minI + 1 == i && minJ == j) ? Integer.MAX_VALUE : matriz[minI + 1][minJ];
                    int izquierda2 = (minJ - 1 == j && minI == i) ? Integer.MAX_VALUE : matriz[minI][minJ - 1];
                    int derecha2 = (minJ + 1 == j && minI == i) ? Integer.MAX_VALUE : matriz[minI][minJ + 1];
                    arriba = (i-1 == minI && j == minJ) ? Integer.MAX_VALUE : matriz[i - 1][j];
                    abajo = (i+1 == minI && j == minJ) ? Integer.MAX_VALUE : matriz[i + 1][j];
                    izquierda = (j-1 == minJ && i == minI) ? Integer.MAX_VALUE : matriz[i][j - 1];
                    derecha = (j+1 == minJ && i == minI) ? Integer.MAX_VALUE : matriz[i][j + 1];
                    // Si se pueden llenar los adyacentes, se llena la posicion actual
                    // Omitimos la posicion actual porque ya sabemos que se puede llenar
                    if (arriba2 > matriz[minI][minJ] && abajo2 > matriz[minI][minJ] && izquierda2 > matriz[minI][minJ] && derecha2 > matriz[minI][minJ] && arriba > matriz[i][j] && abajo > matriz[i][j] && izquierda > matriz[i][j] && derecha > matriz[i][j]) {
                        int hMin2 = Math.min(Math.min(Math.min(arriba2, abajo2), Math.min(izquierda2, derecha2)), Math.min(Math.min(arriba, abajo), Math.min(izquierda, derecha)));
                        estados[i][j].setFillable(true);
                        estados[i][j].setH(hMin2 - matriz[i][j]);
                        estados[i][j].setStatus(Estado.LLENO);
                        matriz[i][j] = hMin2;
                        mutableInt[0] += estados[i][j].getH();
                        estados[minI][minJ].setFillable(true);
                        int diferencia = hMin2 - matriz[minI][minJ];
                        estados[minI][minJ].setH(estados[minI][minJ].getH() + diferencia);
                        matriz[minI][minJ] = hMin2;
                        mutableInt[0] += diferencia;
                    }
                    else {
                        estados[i][j].setFillable(false);
                        estados[i][j].setH(estados[minI][minJ].getH());
                        estados[i][j].setStatus(Estado.VACIO);
                    }
                }
                else {
                    estados[i][j].setFillable(false);
                    estados[i][j].setH(estados[minI][minJ].getH());
                }
            }
        }
    }


    /**
     * Ejecuta el algoritmo de Floyd-Warshall para saber donde se pueden colocar bloques de agua sin que se inunde la ciudad
     * Complejidad: O(|V|^3) donde |V| es la cantidad de vertices del grafo
     * @param matriz
     * @param estados
     * @param filas
     * @param columnas
     * @return max (la cantidad maxima de bloques de agua que se pueden colocar)
    */
    public static int floydWarshall(int[][] matriz, Estados[][] estados, int filas, int columnas) {
        // Inicializamos la matriz de distancias
        int[] mutableInt = new int[1];
        mutableInt[0] = 0;

        // Recorremos la matriz
        for (int i = 1; i < filas - 1; i++){
            for (int j = 1; j < columnas - 1; j++){
                // Si la posicion actual es un borde, no se puede colocar un bloque de agua
                if (estados[i][j].getStatus() != Estado.VACIO) {
                    fill(matriz, estados, i, j, mutableInt);
                }
            }
        }

        return mutableInt[0];
    }
}