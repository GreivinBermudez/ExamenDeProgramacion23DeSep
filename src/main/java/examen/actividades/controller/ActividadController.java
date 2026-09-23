package examen.actividades.controller;

import examen.actividades.model.Actividad;
import examen.actividades.model.TipoActividad;
import examen.actividades.repository.Repositorio;
import examen.actividades.repository.RepositorioActividadTxt;
import examen.actividades.service.ActividadService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class ActividadController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTarifaBase;
    @FXML private TextField txtCupoTotal;
    @FXML private TextField txtCodigoConsulta;
    @FXML private ComboBox<TipoActividad> cmbTipo;
    @FXML private TextArea txaResultados;
    @FXML private Label lblMensaje;

    private ActividadService servicio;

    @FXML
    private void initialize() {
        iniciar();
    }

    public void iniciar() {
        Repositorio<Actividad> repositorio = new RepositorioActividadTxt("actividades.txt");
        servicio = new ActividadService(repositorio);
        cmbTipo.getItems().setAll(TipoActividad.values());
        try {
            servicio.cargarDatos();
            mostrarTodas();
        } catch (IOException e) {
            lblMensaje.setText("No se pudieron cargar los datos. Revise la ruta y los permisos.");
        }
    }

    @FXML
    private void registrar() {
        try {
            double tarifaBase = Double.parseDouble(txtTarifaBase.getText().trim());
            int cupoTotal = Integer.parseInt(txtCupoTotal.getText().trim());
            servicio.registrarActividad(txtCodigo.getText(), txtNombre.getText(),
                    cmbTipo.getValue(), tarifaBase, cupoTotal);
            lblMensaje.setText("Actividad registrada correctamente.");
            mostrarTodas();
        } catch (NumberFormatException e) {
            lblMensaje.setText("La tarifa y el cupo deben ser números válidos (el cupo sin decimales).");
        } catch (IllegalArgumentException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void buscar() {
        try {
            Actividad actividad = servicio.buscarPorCodigo(txtCodigoConsulta.getText());
            if (actividad == null) {
                txaResultados.clear();
                lblMensaje.setText("Actividad no encontrada.");
            } else {
                txaResultados.setText(formatear(actividad));
                lblMensaje.setText("Actividad encontrada.");
            }
        } catch (IllegalArgumentException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void inscribir() {
        try {
            servicio.inscribir(txtCodigoConsulta.getText());
            lblMensaje.setText("Inscripción realizada correctamente.");
            mostrarTodas();
        } catch (IllegalArgumentException | IllegalStateException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void mostrarTodas() {
        mostrarLista(servicio.listarActividades());
    }

    @FXML
    private void limpiar() {
        txtCodigo.clear();
        txtNombre.clear();
        txtTarifaBase.clear();
        txtCupoTotal.clear();
        txtCodigoConsulta.clear();
        cmbTipo.getSelectionModel().clearSelection();
    }

    @FXML
    private void guardarDatos() {
        try {
            servicio.guardarDatos();
            lblMensaje.setText("Datos guardados correctamente.");
        } catch (IOException e) {
            lblMensaje.setText("No se pudieron guardar los datos. Revise la ruta y los permisos.");
        }
    }

    private void mostrarLista(List<Actividad> lista) {
        if (lista.isEmpty()) {
            txaResultados.setText("No hay actividades registradas.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Actividad actividad : lista) {
            sb.append(formatear(actividad));
        }
        txaResultados.setText(sb.toString());
    }
    private String formatear(Actividad actividad) {
        return String.format(
                "Código: %s | Nombre: %s | Tipo: %s | Tarifa final: %.2f | Cupo: %d | Inscritos: %d | Disponibles: %d%n",
                actividad.getCodigo(),
                actividad.getNombre(),
                actividad.getTipo(),
                actividad.calcularTarifaFinal(),
                actividad.getCupoTotal(),
                actividad.getInscritos(),
                actividad.getCuposDisponibles());
    }

}
