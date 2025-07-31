package com.FormularioGob.FormularioGob.service;

import com.FormularioGob.FormularioGob.model.Solicitud;
import com.FormularioGob.FormularioGob.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SolicitudService {
    
    @Autowired
    private SolicitudRepository solicitudRepository;
    
    public Solicitud guardarSolicitud(Map<String, String> params, String[] documentos, String[] becas, String[] discapacidades) {
        Solicitud solicitud = new Solicitud();
        
        // Mapear todos los campos del formulario
        mapearDatosGenerales(solicitud, params);
        mapearDatosSolicitante(solicitud, params);
        mapearDatosContacto(solicitud, params);
        mapearDatosSecundaria(solicitud, params);
        mapearDatosTelebachillerato(solicitud, params);
        mapearDatosMadre(solicitud, params);
        mapearDatosPadre(solicitud, params);
        mapearDatosTutor(solicitud, params);
        mapearDocumentosBecasDiscapacidades(solicitud, params, documentos, becas, discapacidades);
        
        // Generar nombre del documento
        String nombreDocumento = generarNombreDocumento(solicitud);
        solicitud.setNombreDocumentoGenerado(nombreDocumento);
        
        return solicitudRepository.save(solicitud);
    }
    
    private void mapearDatosGenerales(Solicitud solicitud, Map<String, String> params) {
        solicitud.setNombrePlantel(params.get("nombrePlantel"));
        solicitud.setClaveTurno(params.get("claveTurno"));
        solicitud.setLocalidad(params.get("localidad"));
        solicitud.setFecha(params.get("fecha"));
        solicitud.setAnioEscolar(params.get("anioEscolar"));
        solicitud.setFolio1(params.get("FOLIO1"));
        solicitud.setFolio2(params.get("FOLIO2"));
        solicitud.setFolio3(params.get("FOLIO3"));
        solicitud.setFolio4(params.get("FOLIO4"));
        solicitud.setFolio5(params.get("FOLIO5"));
        solicitud.setFolio6(params.get("FOLIO6"));
    }
    
    private void mapearDatosSolicitante(Solicitud solicitud, Map<String, String> params) {
        solicitud.setNombreAlumno(params.get("nombreAlumno"));
        solicitud.setApellidoPaterno(params.get("apellidoPaterno"));
        solicitud.setApellidoMaterno(params.get("apellidoMaterno"));
        solicitud.setLugarNacimiento(params.get("lugarNacimiento"));
        solicitud.setFechaNacimiento(params.get("fechaNacimiento"));
        solicitud.setTipoSangre(params.get("tipoSangre"));
        solicitud.setLenguaMaterna(params.get("lenguaMaterna"));
        solicitud.setCurp(params.get("curp"));
        solicitud.setGenero(params.get("genero"));
        solicitud.setNacionalidad(params.get("nacionalidad"));
    }
    
    private void mapearDatosContacto(Solicitud solicitud, Map<String, String> params) {
        solicitud.setDomicilio(params.get("domicilio"));
        solicitud.setLocalidadSolicitante(params.get("localidadSolicitante"));
        solicitud.setCodigoPostal(params.get("codigoPostal"));
        solicitud.setTelefonoSolicitante(params.get("telefonoSolicitante"));
        solicitud.setCorreoSolicitante(params.get("correoSolicitante"));
    }
    
    private void mapearDatosSecundaria(Solicitud solicitud, Map<String, String> params) {
        solicitud.setNombrePlantelProcedencia(params.get("nombrePlantelProcedencia"));
        solicitud.setLocalidadProcedencia(params.get("localidadProcedencia"));
        solicitud.setFechaConSecundaria(params.get("fechaConSecundaria"));
        solicitud.setTipoAlumno(params.get("tipoAlumno"));
        solicitud.setModalidad(params.get("modalidad"));
        solicitud.setTurno(params.get("turno"));
        solicitud.setDependencia(params.get("dependencia"));
    }
    
    private void mapearDatosTelebachillerato(Solicitud solicitud, Map<String, String> params) {
        solicitud.setNombrePlantelTelebach(params.get("nombrePlantelTelebach"));
        solicitud.setClaveTelebach(params.get("claveTelebach"));
        solicitud.setLocalidadTelebach(params.get("localidadTelebach"));
        solicitud.setSemestre(params.get("semestre"));
        solicitud.setAreaPropedeutica(params.get("areaPropedeutica"));
        solicitud.setGrupo(params.get("grupo"));
        solicitud.setTipoAlumnoTelebach(params.get("tipoAlumnoTelebach"));
    }
    
    private void mapearDatosMadre(Solicitud solicitud, Map<String, String> params) {
        solicitud.setNombreMadre(params.get("nombreMadre"));
        solicitud.setCurpMadre(params.get("curpMadre"));
        solicitud.setFechaNacMadre(params.get("fechaNacMadre"));
        solicitud.setEstadoCivilMadre(params.get("estadoCivilMadre"));
        solicitud.setDomicilioMadre(params.get("domicilioMadre"));
        solicitud.setLocalidadMadre(params.get("localidadMadre"));
        solicitud.setEntidadMadre(params.get("entidadMadre"));
        solicitud.setCodPostalMadre(params.get("codPostalMadre"));
        solicitud.setTelMadre(params.get("telMadre"));
        solicitud.setOcupacionMadre(params.get("ocupacionMadre"));
        solicitud.setNivelEstudiosMadre(params.get("nivelEstudiosMadre"));
        solicitud.setGradoMadre(params.get("gradoMadre"));
        solicitud.setSexoMadre(params.get("sexoMadre"));
        solicitud.setTutorMadre(params.get("tutorMadre"));
        solicitud.setSabeLeerMadre(params.get("sabeLeerMadre"));
    }
    
    private void mapearDatosPadre(Solicitud solicitud, Map<String, String> params) {
        solicitud.setNombrePadre(params.get("nombrePadre"));
        solicitud.setCurpPadre(params.get("curpPadre"));
        solicitud.setFechaNacPadre(params.get("fechaNacPadre"));
        solicitud.setEstadoCivilPadre(params.get("estadoCivilPadre"));
        solicitud.setDomicilioPadre(params.get("domicilioPadre"));
        solicitud.setLocalidadPadre(params.get("localidadPadre"));
        solicitud.setEntidadPadre(params.get("entidadPadre"));
        solicitud.setCodPostalPadre(params.get("codPostalPadre"));
        solicitud.setTelPadre(params.get("telPadre"));
        solicitud.setOcupacionPadre(params.get("ocupacionPadre"));
        solicitud.setNivelEstudiosPadre(params.get("nivelEstudiosPadre"));
        solicitud.setGradoPadre(params.get("gradoPadre"));
        solicitud.setSexoPadre(params.get("sexoPadre"));
        solicitud.setTutorPadre(params.get("tutorPadre"));
        solicitud.setSabeLeerPadre(params.get("sabeLeerPadre"));
    }
    
    private void mapearDatosTutor(Solicitud solicitud, Map<String, String> params) {
        solicitud.setNombreTutor(params.get("nombreTutor"));
        solicitud.setCurpTutor(params.get("curpTutor"));
        solicitud.setFechaNacTutor(params.get("fechaNacTutor"));
        solicitud.setEstadoCivilTutor(params.get("estadoCivilTutor"));
        solicitud.setDomicilioTutor(params.get("domicilioTutor"));
        solicitud.setLocalidadTutor(params.get("localidadTutor"));
        solicitud.setEntidadTutor(params.get("entidadTutor"));
        solicitud.setCodPostalTutor(params.get("codPostalTutor"));
        solicitud.setTelTutor(params.get("telTutor"));
        solicitud.setOcupacionTutor(params.get("ocupacionTutor"));
        solicitud.setNivelEstudiosTutor(params.get("nivelEstudiosTutor"));
        solicitud.setGradoTutor(params.get("gradoTutor"));
        solicitud.setParentesco(params.get("parentesco"));
        solicitud.setSexoTutor(params.get("sexoTutor"));
        solicitud.setSabeLeerTutor(params.get("sabeLeerTutor"));
    }
    
    private void mapearDocumentosBecasDiscapacidades(Solicitud solicitud, Map<String, String> params, 
                                                   String[] documentos, String[] becas, String[] discapacidades) {
        // Convertir arrays a listas
        if (documentos != null) {
            solicitud.setDocumentos(Arrays.asList(documentos));
        }
        if (becas != null) {
            solicitud.setBecas(Arrays.asList(becas));
        }
        if (discapacidades != null) {
            solicitud.setDiscapacidades(Arrays.asList(discapacidades));
        }
        
        // Campos de texto adicionales
        solicitud.setOtrosDocumentos(params.get("otrosDocumentos"));
        solicitud.setOtraBeca(params.get("otraBeca"));
        solicitud.setOtraDiscapacidad(params.get("otraDiscapacidad"));
    }
    
    private String generarNombreDocumento(Solicitud solicitud) {
        String nombre = solicitud.getNombreAlumno() != null ? solicitud.getNombreAlumno() : "SinNombre";
        String apellido = solicitud.getApellidoPaterno() != null ? solicitud.getApellidoPaterno() : "";
        String timestamp = LocalDateTime.now().toString().replaceAll("[^0-9]", "").substring(0, 12);
        
        return String.format("solicitud_%s_%s_%s.docx", 
                            nombre.replaceAll("[^a-zA-Z0-9]", ""), 
                            apellido.replaceAll("[^a-zA-Z0-9]", ""), 
                            timestamp);
    }
    
    // Métodos de consulta
    public List<Solicitud> obtenerTodasLasSolicitudes() {
        return solicitudRepository.findAllOrderByFechaCreacionDesc();
    }
    
    public Optional<Solicitud> obtenerSolicitudPorId(String id) {
        return solicitudRepository.findById(id);
    }
    
    public List<Solicitud> buscarPorCurp(String curp) {
        return solicitudRepository.findByCurp(curp);
    }
    
    public List<Solicitud> buscarPorNombre(String nombre) {
        return solicitudRepository.findByNombreAlumnoContainingIgnoreCase(nombre);
    }
    
    public List<Solicitud> buscarPorPlantel(String nombrePlantel) {
        return solicitudRepository.findByNombrePlantelContainingIgnoreCase(nombrePlantel);
    }
    
    public List<Solicitud> buscarPorSemestre(String semestre) {
        return solicitudRepository.findBySemestre(semestre);
    }
    
    public List<Solicitud> buscarPorAreaPropedeutica(String area) {
        return solicitudRepository.findByAreaPropedeutica(area);
    }
    
    public List<Solicitud> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return solicitudRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
    }
    
    public long contarSolicitudesPorPlantel(String nombrePlantel) {
        return solicitudRepository.countByNombrePlantel(nombrePlantel);
    }
    
    public void eliminarSolicitud(String id) {
        solicitudRepository.deleteById(id);
    }
}
