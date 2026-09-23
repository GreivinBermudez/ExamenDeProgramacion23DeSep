package examen.actividades.repository;

import examen.actividades.model.Actividad;
import examen.actividades.model.ActividadPresencial;
import examen.actividades.model.ActividadVirtual;
import examen.actividades.model.TipoActividad;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class RepositorioActividadTxt implements Repositorio<Actividad> {

    private String rutaArchivo;

    public RepositorioActividadTxt(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public List<Actividad> cargarTodos() throws IOException {
        Path archivo = Path.of(rutaArchivo);
        List<Actividad> actividades = new ArrayList<>();

        if (Files.notExists(archivo)) {
            return actividades;
        }

        for (String linea : Files.readAllLines(archivo, StandardCharsets.UTF_8)) {
            if (linea.isBlank()) {
                continue;
            }
            actividades.add(convertirDesdeLinea(linea));
        }
        return actividades;
    }

    @Override
    public void guardarTodos(List<Actividad> elementos) throws IOException {
        Path archivo = Path.of(rutaArchivo);

        List<String> lineas = new ArrayList<>();
        for (Actividad actividad : elementos) {
            lineas.add(convertirALinea(actividad));
        }

        Files.write(archivo, lineas, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private String convertirALinea(Actividad actividad) {
        return String.join(";",
                actividad.getTipo().name(),
                actividad.getCodigo(),
                actividad.getNombre(),
                String.valueOf(actividad.getTarifaBase()),
                String.valueOf(actividad.getCupoTotal()),
                String.valueOf(actividad.getInscritos())
        );
    }

    private Actividad convertirDesdeLinea(String linea) {
        String[] datos = linea.split(";", -1);
        if (datos.length != 6) {
            throw new IllegalArgumentException("Línea inválida: " + linea);
        }

        TipoActividad tipo = TipoActividad.valueOf(datos[0]);
        String codigo = datos[1];
        String nombre = datos[2];
        double tarifaBase = Double.parseDouble(datos[3]);
        int cupoTotal = Integer.parseInt(datos[4]);
        int inscritos = Integer.parseInt(datos[5]);

        if (tipo == TipoActividad.PRESENCIAL) {
            return new ActividadPresencial(codigo, nombre, tarifaBase, cupoTotal, inscritos);
        }
        return new ActividadVirtual(codigo, nombre, tarifaBase, cupoTotal, inscritos);
    }
}

