package com.FormularioGob.FormularioGob.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "solicitudes")
public class Solicitud {
    
    @Id
    private String id;
    
    // Datos generales
    private String nombrePlantel;
    private String claveTurno;
    private String localidad;
    private String fecha;
    private String anioEscolar;
    private String folio1;
    private String folio2;
    private String folio3;
    private String folio4;
    private String folio5;
    private String folio6;
    
    // Datos del solicitante
    private String nombreAlumno;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String lugarNacimiento;
    private String fechaNacimiento;
    private String tipoSangre;
    private String lenguaMaterna;
    private String curp;
    private String genero;
    private String nacionalidad;
    
    // Datos de contacto
    private String domicilio;
    private String localidadSolicitante;
    private String codigoPostal;
    private String telefonoSolicitante;
    private String correoSolicitante;
    
    // Datos de secundaria
    private String nombrePlantelProcedencia;
    private String localidadProcedencia;
    private String fechaConSecundaria;
    private String tipoAlumno;
    private String modalidad;
    private String turno;
    private String dependencia;
    
    // Datos de telebachillerato
    private String nombrePlantelTelebach;
    private String claveTelebach;
    private String localidadTelebach;
    private String semestre;
    private String areaPropedeutica;
    private String grupo;
    private String tipoAlumnoTelebach;
    
    // Datos de la madre
    private String nombreMadre;
    private String curpMadre;
    private String fechaNacMadre;
    private String estadoCivilMadre;
    private String domicilioMadre;
    private String localidadMadre;
    private String entidadMadre;
    private String codPostalMadre;
    private String telMadre;
    private String ocupacionMadre;
    private String nivelEstudiosMadre;
    private String gradoMadre;
    private String sexoMadre;
    private String tutorMadre;
    private String sabeLeerMadre;
    
    // Datos del padre
    private String nombrePadre;
    private String curpPadre;
    private String fechaNacPadre;
    private String estadoCivilPadre;
    private String domicilioPadre;
    private String localidadPadre;
    private String entidadPadre;
    private String codPostalPadre;
    private String telPadre;
    private String ocupacionPadre;
    private String nivelEstudiosPadre;
    private String gradoPadre;
    private String sexoPadre;
    private String tutorPadre;
    private String sabeLeerPadre;
    
    // Datos del tutor
    private String nombreTutor;
    private String curpTutor;
    private String fechaNacTutor;
    private String estadoCivilTutor;
    private String domicilioTutor;
    private String localidadTutor;
    private String entidadTutor;
    private String codPostalTutor;
    private String telTutor;
    private String ocupacionTutor;
    private String nivelEstudiosTutor;
    private String gradoTutor;
    private String parentesco;
    private String sexoTutor;
    private String sabeLeerTutor;
    
    // Documentos, becas y discapacidades
    private List<String> documentos;
    private String otrosDocumentos;
    private List<String> becas;
    private String otraBeca;
    private List<String> discapacidades;
    private String otraDiscapacidad;
    
    // Metadatos
    private LocalDateTime fechaCreacion;
    private String nombreDocumentoGenerado;
    
    // Constructor vacío
    public Solicitud() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNombrePlantel() {
        return nombrePlantel;
    }
    
    public void setNombrePlantel(String nombrePlantel) {
        this.nombrePlantel = nombrePlantel;
    }
    
    public String getClaveTurno() {
        return claveTurno;
    }
    
    public void setClaveTurno(String claveTurno) {
        this.claveTurno = claveTurno;
    }
    
    public String getLocalidad() {
        return localidad;
    }
    
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getAnioEscolar() {
        return anioEscolar;
    }
    
    public void setAnioEscolar(String anioEscolar) {
        this.anioEscolar = anioEscolar;
    }
    
    public String getFolio1() {
        return folio1;
    }
    
    public void setFolio1(String folio1) {
        this.folio1 = folio1;
    }
    
    public String getFolio2() {
        return folio2;
    }
    
    public void setFolio2(String folio2) {
        this.folio2 = folio2;
    }
    
    public String getFolio3() {
        return folio3;
    }
    
    public void setFolio3(String folio3) {
        this.folio3 = folio3;
    }
    
    public String getFolio4() {
        return folio4;
    }
    
    public void setFolio4(String folio4) {
        this.folio4 = folio4;
    }
    
    public String getFolio5() {
        return folio5;
    }
    
    public void setFolio5(String folio5) {
        this.folio5 = folio5;
    }
    
    public String getFolio6() {
        return folio6;
    }
    
    public void setFolio6(String folio6) {
        this.folio6 = folio6;
    }
    
    public String getNombreAlumno() {
        return nombreAlumno;
    }
    
    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }
    
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
    
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    
    public String getLugarNacimiento() {
        return lugarNacimiento;
    }
    
    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getTipoSangre() {
        return tipoSangre;
    }
    
    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }
    
    public String getLenguaMaterna() {
        return lenguaMaterna;
    }
    
    public void setLenguaMaterna(String lenguaMaterna) {
        this.lenguaMaterna = lenguaMaterna;
    }
    
    public String getCurp() {
        return curp;
    }
    
    public void setCurp(String curp) {
        this.curp = curp;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getNacionalidad() {
        return nacionalidad;
    }
    
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    
    public String getDomicilio() {
        return domicilio;
    }
    
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    
    public String getLocalidadSolicitante() {
        return localidadSolicitante;
    }
    
    public void setLocalidadSolicitante(String localidadSolicitante) {
        this.localidadSolicitante = localidadSolicitante;
    }
    
    public String getCodigoPostal() {
        return codigoPostal;
    }
    
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    
    public String getTelefonoSolicitante() {
        return telefonoSolicitante;
    }
    
    public void setTelefonoSolicitante(String telefonoSolicitante) {
        this.telefonoSolicitante = telefonoSolicitante;
    }
    
    public String getCorreoSolicitante() {
        return correoSolicitante;
    }
    
    public void setCorreoSolicitante(String correoSolicitante) {
        this.correoSolicitante = correoSolicitante;
    }
    
    public String getNombrePlantelProcedencia() {
        return nombrePlantelProcedencia;
    }
    
    public void setNombrePlantelProcedencia(String nombrePlantelProcedencia) {
        this.nombrePlantelProcedencia = nombrePlantelProcedencia;
    }
    
    public String getLocalidadProcedencia() {
        return localidadProcedencia;
    }
    
    public void setLocalidadProcedencia(String localidadProcedencia) {
        this.localidadProcedencia = localidadProcedencia;
    }
    
    public String getFechaConSecundaria() {
        return fechaConSecundaria;
    }
    
    public void setFechaConSecundaria(String fechaConSecundaria) {
        this.fechaConSecundaria = fechaConSecundaria;
    }
    
    public String getTipoAlumno() {
        return tipoAlumno;
    }
    
    public void setTipoAlumno(String tipoAlumno) {
        this.tipoAlumno = tipoAlumno;
    }
    
    public String getModalidad() {
        return modalidad;
    }
    
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    
    public String getTurno() {
        return turno;
    }
    
    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    public String getDependencia() {
        return dependencia;
    }
    
    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }
    
    public String getNombrePlantelTelebach() {
        return nombrePlantelTelebach;
    }
    
    public void setNombrePlantelTelebach(String nombrePlantelTelebach) {
        this.nombrePlantelTelebach = nombrePlantelTelebach;
    }
    
    public String getClaveTelebach() {
        return claveTelebach;
    }
    
    public void setClaveTelebach(String claveTelebach) {
        this.claveTelebach = claveTelebach;
    }
    
    public String getLocalidadTelebach() {
        return localidadTelebach;
    }
    
    public void setLocalidadTelebach(String localidadTelebach) {
        this.localidadTelebach = localidadTelebach;
    }
    
    public String getSemestre() {
        return semestre;
    }
    
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
    
    public String getAreaPropedeutica() {
        return areaPropedeutica;
    }
    
    public void setAreaPropedeutica(String areaPropedeutica) {
        this.areaPropedeutica = areaPropedeutica;
    }
    
    public String getGrupo() {
        return grupo;
    }
    
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    
    public String getTipoAlumnoTelebach() {
        return tipoAlumnoTelebach;
    }
    
    public void setTipoAlumnoTelebach(String tipoAlumnoTelebach) {
        this.tipoAlumnoTelebach = tipoAlumnoTelebach;
    }
    
    public String getNombreMadre() {
        return nombreMadre;
    }
    
    public void setNombreMadre(String nombreMadre) {
        this.nombreMadre = nombreMadre;
    }
    
    public String getCurpMadre() {
        return curpMadre;
    }
    
    public void setCurpMadre(String curpMadre) {
        this.curpMadre = curpMadre;
    }
    
    public String getFechaNacMadre() {
        return fechaNacMadre;
    }
    
    public void setFechaNacMadre(String fechaNacMadre) {
        this.fechaNacMadre = fechaNacMadre;
    }
    
    public String getEstadoCivilMadre() {
        return estadoCivilMadre;
    }
    
    public void setEstadoCivilMadre(String estadoCivilMadre) {
        this.estadoCivilMadre = estadoCivilMadre;
    }
    
    public String getDomicilioMadre() {
        return domicilioMadre;
    }
    
    public void setDomicilioMadre(String domicilioMadre) {
        this.domicilioMadre = domicilioMadre;
    }
    
    public String getLocalidadMadre() {
        return localidadMadre;
    }
    
    public void setLocalidadMadre(String localidadMadre) {
        this.localidadMadre = localidadMadre;
    }
    
    public String getEntidadMadre() {
        return entidadMadre;
    }
    
    public void setEntidadMadre(String entidadMadre) {
        this.entidadMadre = entidadMadre;
    }
    
    public String getCodPostalMadre() {
        return codPostalMadre;
    }
    
    public void setCodPostalMadre(String codPostalMadre) {
        this.codPostalMadre = codPostalMadre;
    }
    
    public String getTelMadre() {
        return telMadre;
    }
    
    public void setTelMadre(String telMadre) {
        this.telMadre = telMadre;
    }
    
    public String getOcupacionMadre() {
        return ocupacionMadre;
    }
    
    public void setOcupacionMadre(String ocupacionMadre) {
        this.ocupacionMadre = ocupacionMadre;
    }
    
    public String getNivelEstudiosMadre() {
        return nivelEstudiosMadre;
    }
    
    public void setNivelEstudiosMadre(String nivelEstudiosMadre) {
        this.nivelEstudiosMadre = nivelEstudiosMadre;
    }
    
    public String getGradoMadre() {
        return gradoMadre;
    }
    
    public void setGradoMadre(String gradoMadre) {
        this.gradoMadre = gradoMadre;
    }
    
    public String getSexoMadre() {
        return sexoMadre;
    }
    
    public void setSexoMadre(String sexoMadre) {
        this.sexoMadre = sexoMadre;
    }
    
    public String getTutorMadre() {
        return tutorMadre;
    }
    
    public void setTutorMadre(String tutorMadre) {
        this.tutorMadre = tutorMadre;
    }
    
    public String getSabeLeerMadre() {
        return sabeLeerMadre;
    }
    
    public void setSabeLeerMadre(String sabeLeerMadre) {
        this.sabeLeerMadre = sabeLeerMadre;
    }
    
    public String getNombrePadre() {
        return nombrePadre;
    }
    
    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }
    
    public String getCurpPadre() {
        return curpPadre;
    }
    
    public void setCurpPadre(String curpPadre) {
        this.curpPadre = curpPadre;
    }
    
    public String getFechaNacPadre() {
        return fechaNacPadre;
    }
    
    public void setFechaNacPadre(String fechaNacPadre) {
        this.fechaNacPadre = fechaNacPadre;
    }
    
    public String getEstadoCivilPadre() {
        return estadoCivilPadre;
    }
    
    public void setEstadoCivilPadre(String estadoCivilPadre) {
        this.estadoCivilPadre = estadoCivilPadre;
    }
    
    public String getDomicilioPadre() {
        return domicilioPadre;
    }
    
    public void setDomicilioPadre(String domicilioPadre) {
        this.domicilioPadre = domicilioPadre;
    }
    
    public String getLocalidadPadre() {
        return localidadPadre;
    }
    
    public void setLocalidadPadre(String localidadPadre) {
        this.localidadPadre = localidadPadre;
    }
    
    public String getEntidadPadre() {
        return entidadPadre;
    }
    
    public void setEntidadPadre(String entidadPadre) {
        this.entidadPadre = entidadPadre;
    }
    
    public String getCodPostalPadre() {
        return codPostalPadre;
    }
    
    public void setCodPostalPadre(String codPostalPadre) {
        this.codPostalPadre = codPostalPadre;
    }
    
    public String getTelPadre() {
        return telPadre;
    }
    
    public void setTelPadre(String telPadre) {
        this.telPadre = telPadre;
    }
    
    public String getOcupacionPadre() {
        return ocupacionPadre;
    }
    
    public void setOcupacionPadre(String ocupacionPadre) {
        this.ocupacionPadre = ocupacionPadre;
    }
    
    public String getNivelEstudiosPadre() {
        return nivelEstudiosPadre;
    }
    
    public void setNivelEstudiosPadre(String nivelEstudiosPadre) {
        this.nivelEstudiosPadre = nivelEstudiosPadre;
    }
    
    public String getGradoPadre() {
        return gradoPadre;
    }
    
    public void setGradoPadre(String gradoPadre) {
        this.gradoPadre = gradoPadre;
    }
    
    public String getSexoPadre() {
        return sexoPadre;
    }
    
    public void setSexoPadre(String sexoPadre) {
        this.sexoPadre = sexoPadre;
    }
    
    public String getTutorPadre() {
        return tutorPadre;
    }
    
    public void setTutorPadre(String tutorPadre) {
        this.tutorPadre = tutorPadre;
    }
    
    public String getSabeLeerPadre() {
        return sabeLeerPadre;
    }
    
    public void setSabeLeerPadre(String sabeLeerPadre) {
        this.sabeLeerPadre = sabeLeerPadre;
    }
    
    public String getNombreTutor() {
        return nombreTutor;
    }
    
    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }
    
    public String getCurpTutor() {
        return curpTutor;
    }
    
    public void setCurpTutor(String curpTutor) {
        this.curpTutor = curpTutor;
    }
    
    public String getFechaNacTutor() {
        return fechaNacTutor;
    }
    
    public void setFechaNacTutor(String fechaNacTutor) {
        this.fechaNacTutor = fechaNacTutor;
    }
    
    public String getEstadoCivilTutor() {
        return estadoCivilTutor;
    }
    
    public void setEstadoCivilTutor(String estadoCivilTutor) {
        this.estadoCivilTutor = estadoCivilTutor;
    }
    
    public String getDomicilioTutor() {
        return domicilioTutor;
    }
    
    public void setDomicilioTutor(String domicilioTutor) {
        this.domicilioTutor = domicilioTutor;
    }
    
    public String getLocalidadTutor() {
        return localidadTutor;
    }
    
    public void setLocalidadTutor(String localidadTutor) {
        this.localidadTutor = localidadTutor;
    }
    
    public String getEntidadTutor() {
        return entidadTutor;
    }
    
    public void setEntidadTutor(String entidadTutor) {
        this.entidadTutor = entidadTutor;
    }
    
    public String getCodPostalTutor() {
        return codPostalTutor;
    }
    
    public void setCodPostalTutor(String codPostalTutor) {
        this.codPostalTutor = codPostalTutor;
    }
    
    public String getTelTutor() {
        return telTutor;
    }
    
    public void setTelTutor(String telTutor) {
        this.telTutor = telTutor;
    }
    
    public String getOcupacionTutor() {
        return ocupacionTutor;
    }
    
    public void setOcupacionTutor(String ocupacionTutor) {
        this.ocupacionTutor = ocupacionTutor;
    }
    
    public String getNivelEstudiosTutor() {
        return nivelEstudiosTutor;
    }
    
    public void setNivelEstudiosTutor(String nivelEstudiosTutor) {
        this.nivelEstudiosTutor = nivelEstudiosTutor;
    }
    
    public String getGradoTutor() {
        return gradoTutor;
    }
    
    public void setGradoTutor(String gradoTutor) {
        this.gradoTutor = gradoTutor;
    }
    
    public String getParentesco() {
        return parentesco;
    }
    
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
    
    public String getSexoTutor() {
        return sexoTutor;
    }
    
    public void setSexoTutor(String sexoTutor) {
        this.sexoTutor = sexoTutor;
    }
    
    public String getSabeLeerTutor() {
        return sabeLeerTutor;
    }
    
    public void setSabeLeerTutor(String sabeLeerTutor) {
        this.sabeLeerTutor = sabeLeerTutor;
    }
    
    public List<String> getDocumentos() {
        return documentos;
    }
    
    public void setDocumentos(List<String> documentos) {
        this.documentos = documentos;
    }
    
    public String getOtrosDocumentos() {
        return otrosDocumentos;
    }
    
    public void setOtrosDocumentos(String otrosDocumentos) {
        this.otrosDocumentos = otrosDocumentos;
    }
    
    public List<String> getBecas() {
        return becas;
    }
    
    public void setBecas(List<String> becas) {
        this.becas = becas;
    }
    
    public String getOtraBeca() {
        return otraBeca;
    }
    
    public void setOtraBeca(String otraBeca) {
        this.otraBeca = otraBeca;
    }
    
    public List<String> getDiscapacidades() {
        return discapacidades;
    }
    
    public void setDiscapacidades(List<String> discapacidades) {
        this.discapacidades = discapacidades;
    }
    
    public String getOtraDiscapacidad() {
        return otraDiscapacidad;
    }
    
    public void setOtraDiscapacidad(String otraDiscapacidad) {
        this.otraDiscapacidad = otraDiscapacidad;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public String getNombreDocumentoGenerado() {
        return nombreDocumentoGenerado;
    }
    
    public void setNombreDocumentoGenerado(String nombreDocumentoGenerado) {
        this.nombreDocumentoGenerado = nombreDocumentoGenerado;
    }
}
