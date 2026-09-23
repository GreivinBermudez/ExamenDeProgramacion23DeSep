package examen.actividades.model;

public class ActividadVirtual extends Actividad {

    public ActividadVirtual(String codigo, String nombre, double tarifaBase, int cupoTotal, int inscritos) {
        super(codigo, nombre, tarifaBase, cupoTotal, inscritos);
    }

    @Override
    public double calcularTarifaFinal() {
        return getTarifaBase();
    }

    @Override
    public TipoActividad getTipo() {
        return TipoActividad.VIRTUAL;
    }
}
