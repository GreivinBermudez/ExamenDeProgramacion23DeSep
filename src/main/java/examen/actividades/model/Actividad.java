package examen.actividades.model;

public abstract class Actividad {

    private String codigo;
    private String nombre;
    private double tarifaBase;
    private int cupoTotal;
    private int inscritos;

    public Actividad(String codigo, String nombre, double tarifaBase, int cupoTotal, int inscritos) {
        String codigoLimpio = codigo == null ? "" : codigo.trim();
        String nombreLimpio = nombre == null ? "" : nombre.trim();

        if (codigoLimpio.isEmpty() || codigoLimpio.contains(";") || codigoLimpio.contains("\n")
                || codigoLimpio.contains("\r")) {
            throw new IllegalArgumentException("El código no puede estar vacío ni contener ';' o saltos de línea.");
        }
        if (nombreLimpio.isEmpty() || nombreLimpio.contains(";") || nombreLimpio.contains("\n")
                || nombreLimpio.contains("\r")) {
            throw new IllegalArgumentException("El nombre no puede estar vacío ni contener ';' o saltos de línea.");
        }
        if (!Double.isFinite(tarifaBase) || tarifaBase <= 0) {
            throw new IllegalArgumentException("La tarifa base debe ser un número finito mayor que cero.");
        }
        if (cupoTotal <= 0) {
            throw new IllegalArgumentException("El cupo total debe ser un número entero mayor que cero.");
        }
        if (inscritos < 0 || inscritos > cupoTotal) {
            throw new IllegalArgumentException("Los inscritos deben estar entre cero y el cupo total.");
        }

        this.codigo = codigoLimpio;
        this.nombre = nombreLimpio;
        this.tarifaBase = tarifaBase;
        this.cupoTotal = cupoTotal;
        this.inscritos = inscritos;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public int getCupoTotal() {
        return cupoTotal;
    }

    public int getInscritos() {
        return inscritos;
    }

    public int getCuposDisponibles() {
        return cupoTotal - inscritos;
    }

    public void inscribir() {
        if (getCuposDisponibles() <= 0) {
            throw new IllegalStateException("La actividad " + codigo + " no tiene cupos disponibles.");
        }
        inscritos++;
    }

    public abstract double calcularTarifaFinal();

    public abstract TipoActividad getTipo();
}
