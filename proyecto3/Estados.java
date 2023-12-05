enum Estado {
    VACIO,
    LLENO,
    DESCONOCIDO
}

public class Estados {
    private int h;
    private Boolean fillable;
    private Estado status;

    public Estados(Estado status) {
        this.h = 0;
        this.fillable = false;
        this.status = status;
    }

    public int getH() {
        return this.h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Boolean getFillable() {
        return this.fillable;
    }

    public void setFillable(Boolean fillable) {
        this.fillable = fillable;
    }

    public Estado getStatus() {
        return this.status;
    }

    public void setStatus(Estado status) {
        this.status = status;
    }

    public String toString() {
        return "(H: " + this.h + " Fillable: " + this.fillable + " Status: " + this.status + ")";
    }
}
