import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AlfonsoJose {
    public static void main(String[] args) {
        String archivo = "atlantis.txt";
        int[][] matriz = leerArchivo(archivo);
        System.out.println("Matriz original:");
        for (int i = 0; i < matriz.length; i++){
            for (int j = 0; j < matriz[i].length; j++){
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }

        int filas = matriz.length;
        int columnas = matriz[0].length;
        // Creamos la matriz de estados con los valores inicializados
        Estados[][] estados = matrizEstados(filas, columnas);

        // Ejecutamos Floyd-Warshall para saber donde se pueden colocar bloques de agua sin que se inunde la ciudad
        int max = floydWarshall(matriz, estados, filas, columnas);

        System.out.println("Matriz final:");
        for (int i = 0; i < matriz.length; i++){
            for (int j = 0; j < matriz[i].length; j++){
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Matriz de estados:");
        for (int i = 0; i < estados.length; i++){
            for (int j = 0; j < estados[i].length; j++){
                System.out.println(i + ", " + j + ": " +estados[i][j].toString());
            }
        }

        System.out.println("Se pueden colocar " + max + " bloques de agua");
    }

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
        Estados[][] estados = new Estados[filas][columnas];
        for (int i = 0; i < estados.length; i++){
            for (int j = 0; j < estados[i].length; j++){
                if (esBorde(i, j, filas, columnas)) {
                    estados[i][j] = new Estados(Estado.VACIO);
                }
                else{
                    estados[i][j] = new Estados(Estado.DESCONOCIDO);
                }
            }
        }
        return estados;
    }

    /**
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
            if (arriba > matriz[i][j] && abajo > matriz[i][j] && izquierda > matriz[i][j] && derecha > matriz[i][j]) {
                int hMin = Math.min(Math.min(arriba, abajo), Math.min(izquierda, derecha));
                estados[i][j].setStatus(Estado.LLENO);
                estados[i][j].setFillable(true);
                estados[i][j].setH(hMin - matriz[i][j]);
                matriz[i][j] = hMin;
                mutableInt[0] += estados[i][j].getH();
            }
            else {
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
        int[] mutableInt = new int[1];
        mutableInt[0] = 0;

        for (int i = 1; i < filas - 1; i++){
            for (int j = 1; j < columnas - 1; j++){
                if (estados[i][j].getStatus() == Estado.DESCONOCIDO) {
                    fill(matriz, estados, i, j, mutableInt);
                }
            }
        }

        return mutableInt[0];
    }
}