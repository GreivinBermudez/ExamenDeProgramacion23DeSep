package examen.actividades.repository;

import java.io.IOException;
import java.util.List;

public interface Repositorio<T> {

    List<T> cargarTodos() throws IOException;

    void guardarTodos(List<T> elementos) throws IOException;
}
