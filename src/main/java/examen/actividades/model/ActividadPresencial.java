package examen.actividades.model;

public class ActividadPresencial extends Actividad {

    private static final double RECARGO_MATERIALES = 1500;

    public ActividadPresencial(String codigo, String nombre, double tarifaBase, int cupoTotal, int inscritos) {
        super(codigo, nombre, tarifaBase, cupoTotal, inscritos);
    }

    @Override
    public double calcularTarifaFinal() {
        return getTarifaBase() + RECARGO_MATERIALES;
    }

    @Override
    public TipoActividad getTipo() {
        return TipoActividad.PRESENCIAL;
    }
}
