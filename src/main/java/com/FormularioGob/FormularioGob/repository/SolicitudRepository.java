package com.FormularioGob.FormularioGob.repository;

import com.FormularioGob.FormularioGob.model.Solicitud;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolicitudRepository extends MongoRepository<Solicitud, String> {
    
    // Buscar por CURP del solicitante
    List<Solicitud> findByCurp(String curp);
    
    // Buscar por nombre completo del solicitante
    List<Solicitud> findByNombreAlumnoContainingIgnoreCase(String nombre);
    
    // Buscar solicitudes por fecha de creación
    List<Solicitud> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Buscar por plantel
    List<Solicitud> findByNombrePlantelContainingIgnoreCase(String nombrePlantel);
    
    // Buscar por semestre
    List<Solicitud> findBySemestre(String semestre);
    
    // Buscar por área propedéutica
    List<Solicitud> findByAreaPropedeutica(String areaPropedeutica);
    
    // Buscar las solicitudes más recientes
    @Query(value = "{}", sort = "{ fechaCreacion : -1 }")
    List<Solicitud> findAllOrderByFechaCreacionDesc();
    
    // Contar solicitudes por plantel
    long countByNombrePlantel(String nombrePlantel);
    
    // Buscar solicitudes por año escolar
    List<Solicitud> findByAnioEscolar(String anioEscolar);
}
