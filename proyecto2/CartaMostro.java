public class CartaMostro{
    private String nombre;
    private int nivel;
    private int poder;
    private String tipo;

    // Definir el constructor
    public CartaMostro(String nombre, int nivel, int poder, String tipo){
        this.nombre = nombre;
        this.nivel = nivel;
        this.poder = poder;
        this.tipo = tipo;
    }

    // Definir los getters
    public String getNombre(){
        return this.nombre;
    }

    public int getNivel(){
        return this.nivel;
    }

    public int getPoder(){
        return this.poder;
    }

    public String getTipo(){
        return this.tipo;
    }

    // Definir los setters
    public void setNombre(String nombre){
        if (nombre == null || nombre.length() == 0){
            System.out.println("El nombre no puede ser nulo o vacio");
            return;
        }
        this.nombre = nombre;
    }

    public void setNivel(int nivel){
        if (nivel < 1 || nivel > 12){
            System.out.println("El nivel debe estar entre 1 y 12");
            return;
        }
        this.nivel = nivel;
    }

    public void setPoder(int poder){
        if (poder % 50 != 0){
            System.out.println("El poder debe ser multiplo de 50");
            return;
        }
        this.poder = poder;
    }

    public void setTipo(String tipo){
        if (tipo == null || tipo.length() == 0){
            System.out.println("El tipo no puede ser nulo o vacio");
            return;
        }
        this.tipo = tipo;
    }

    
    public String toString(){
        return this.nombre;
    }
}