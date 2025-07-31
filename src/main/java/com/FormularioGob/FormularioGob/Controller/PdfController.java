package com.FormularioGob.FormularioGob.Controller;

import com.FormularioGob.FormularioGob.model.Solicitud;
import com.FormularioGob.FormularioGob.service.SolicitudService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.Map;

@Controller
public class PdfController {

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping("/")
    public String showForm(Model model) {
        return "form";
    }

    @PostMapping("/generate-pdf")
    public void generateDocx(@RequestParam Map<String, String> params, 
                           @RequestParam(value = "documentos", required = false) String[] documentos,
                           @RequestParam(value = "becas", required = false) String[] becas,
                           @RequestParam(value = "discapacidades", required = false) String[] discapacidades,
                           HttpServletResponse response) throws Exception {

        // Guardar en base de datos ANTES de generar el documento
        try {
            Solicitud solicitudGuardada = solicitudService.guardarSolicitud(params, documentos, becas, discapacidades);
            params.put("solicitudId", solicitudGuardada.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStream template = new ClassPathResource("word/solicitud_template1.docx").getInputStream();
        XWPFDocument doc = new XWPFDocument(template);
        String fechaCompleta = params.get("fecha");
        String fechaNacimiento = params.get("fechaNacimiento");
        
        // Descomponer fechas
        descomponerFecha(fechaCompleta, params, "dSol", "mSol", "aSol");
        descomponerFecha(fechaNacimiento, params, "diaNa", "mesNa","anioNa");
        
        // Procesar campos de selección para marcar círculos
        procesarCamposSeleccion(params, documentos, becas, discapacidades);

        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        StringBuilder fullText = new StringBuilder();
                        for (XWPFRun run : paragraph.getRuns()) {
                            String text = run.getText(0);
                            if (text != null) {
                                fullText.append(text);
                            }
                        }

                        String originalText = fullText.toString();

                        // Solo procesar si contiene algún marcador
                        boolean contieneMarcador = params.keySet().stream()
                                .anyMatch(key -> originalText.contains("{" + key + "}"));

                        if (contieneMarcador) {
                            String replaced = originalText;
                            for (Map.Entry<String, String> entry : params.entrySet()) {
                                replaced = replaced.replace("{" + entry.getKey() + "}", entry.getValue());
                            }

                            // Limpiar runs y agregar solo el texto reemplazado
                            for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
                                paragraph.removeRun(i);
                            }

                            XWPFRun newRun = paragraph.createRun();
                            newRun.setFontSize(7);
                            newRun.setFontFamily("Arial");
                            newRun.setText(replaced);
                        }
                    }
                }
            }
        }


        // Preparar respuesta
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=solicitud_generada.docx");
        doc.write(response.getOutputStream());
        doc.close();
    }
    
    private void procesarCamposSeleccion(Map<String, String> params, String[] documentos, String[] becas, String[] discapacidades) {
        // Procesar género
        String genero = params.get("genero");
        params.put("masc", "MASC".equals(genero) ? "⚫" : "");
        params.put("fem", "FEM".equals(genero) ? "⚫" : "");
        
        // Procesar nacionalidad
        String nacionalidad = params.get("nacionalidad");
        params.put("mex", "MEX".equals(nacionalidad) ? "⚫" : "");
        params.put("otra", "OTRA".equals(nacionalidad) ? "⚫" : "");
        
        // Procesar tipo de alumno
        String tipoAlumno = params.get("tipoAlumno");
        params.put("certi", "CON_CERT".equals(tipoAlumno) ? "⚫" : "");
        params.put("fcerti", "FALTA_CERT".equals(tipoAlumno) ? "⚫" : "");
        
        // Procesar modalidad
        String modalidad = params.get("modalidad");
        params.put("escol", "ESCOLARIZADA".equals(modalidad) ? "⚫" : "");
        params.put("vea", "VEA".equals(modalidad) ? "⚫" : "");
        
        // Procesar turno
        String turno = params.get("turno");
        params.put("mat", "MAT".equals(turno) ? "⚫" : "");
        params.put("vesp", "VESP".equals(turno) ? "⚫" : "");
        params.put("noc", "NOC".equals(turno) ? "⚫" : "");
        params.put("abi", "ABIERTO".equals(turno) ? "⚫" : ""); 
        
        // Procesar dependencia
        String dependencia = params.get("dependencia");
        params.put("sev", "SEV".equals(dependencia) ? "⚫" : "");
        params.put("sep", "SEP".equals(dependencia) ? "⚫" : "");
        params.put("otro", "OTRO".equals(dependencia) ? "⚫" : "");
        
        // Procesar semestre a cursar
        String semestre = params.get("semestre");
        params.put("1", "1".equals(semestre) ? "⚫" : "");
        params.put("2", "2".equals(semestre) ? "⚫" : "");
        params.put("3", "3".equals(semestre) ? "⚫" : "");
        params.put("4", "4".equals(semestre) ? "⚫" : "");
        params.put("5", "5".equals(semestre) ? "⚫" : "");
        params.put("6", "6".equals(semestre) ? "⚫" : "");
        
        // Procesar área propedéutica
        String areaPropedeutica = params.get("areaPropedeutica");
        params.put("QB", "QB".equals(areaPropedeutica) ? "⚫" : "");
        params.put("EA", "EA".equals(areaPropedeutica) ? "⚫" : "");
        params.put("HC", "HC".equals(areaPropedeutica) ? "⚫" : "");
        params.put("FM", "FM".equals(areaPropedeutica) ? "⚫" : "");
        
        // Procesar grupo
        String grupo = params.get("grupo");
        params.put("A", "A".equals(grupo) ? "⚫" : "A");
        params.put("B", "B".equals(grupo) ? "⚫" : "B");
        params.put("C", "C".equals(grupo) ? "⚫" : "C");
        params.put("D", "D".equals(grupo) ? "⚫" : "D");
        params.put("E", "E".equals(grupo) ? "⚫" : "E");
        params.put("F", "F".equals(grupo) ? "⚫" : "F");
        params.put("G", "G".equals(grupo) ? "⚫" : "G");
        params.put("H", "H".equals(grupo) ? "⚫" : "H");
        params.put("I", "I".equals(grupo) ? "⚫" : "I");
        params.put("U", "U".equals(grupo) ? "⚫" : "U");
        
        // Procesar tipo de alumno de telebachillerato
        String tipoAlumnoTelebach = params.get("tipoAlumnoTelebach");
        params.put("REG", "REG".equals(tipoAlumnoTelebach) ? "⚫" : "");
        params.put("IREG", "IREG".equals(tipoAlumnoTelebach) ? "⚫" : "");
        params.put("REP", "REP".equals(tipoAlumnoTelebach) ? "⚫" : "");
        
        // Procesar campos de la madre
        String sexoMadre = params.get("sexoMadre");
        params.put("MM", "MASCULINO".equals(sexoMadre) ? "⚫" : "");
        params.put("FM", "FEMENINO".equals(sexoMadre) ? "⚫" : "");
        
        String tutorMadre = params.get("tutorMadre");
        params.put("SM", "SI".equals(tutorMadre) ? "⚫" : "");
        params.put("NM", "NO".equals(tutorMadre) ? "⚫" : "");
        
        String sabeLeerMadre = params.get("sabeLeerMadre");
        params.put("SLM", "SI".equals(sabeLeerMadre) ? "⚫" : "");
        params.put("NLM", "NO".equals(sabeLeerMadre) ? "⚫" : "");
        
        // Procesar campos del padre
        String sexoPadre = params.get("sexoPadre");
        params.put("MP", "MASCULINO".equals(sexoPadre) ? "⚫" : "");
        params.put("FP", "FEMENINO".equals(sexoPadre) ? "⚫" : "");
        
        String tutorPadre = params.get("tutorPadre");
        params.put("SP", "SI".equals(tutorPadre) ? "⚫" : "");
        params.put("NP", "NO".equals(tutorPadre) ? "⚫" : "");
        
        String sabeLeerPadre = params.get("sabeLeerPadre");
        params.put("SLP", "SI".equals(sabeLeerPadre) ? "⚫" : "");
        params.put("NLP", "NO".equals(sabeLeerPadre) ? "⚫" : "");
        
        // Procesar campos del tutor
        String sexoTutor = params.get("sexoTutor");
        params.put("MT", "MASCULINO".equals(sexoTutor) ? "⚫" : "");
        params.put("FT", "FEMENINO".equals(sexoTutor) ? "⚫" : "");
        
        String sabeLeerTutor = params.get("sabeLeerTutor");
        params.put("SLT", "SI".equals(sabeLeerTutor) ? "⚫" : "");
        params.put("NLT", "NO".equals(sabeLeerTutor) ? "⚫" : "");
        
        // Procesar parentesco del tutor
        String parentesco = params.get("parentesco");
        params.put("PERNT", parentesco != null ? parentesco : "");
        
        // Procesar documentos presentados (checkboxes múltiples)
        if (documentos == null) documentos = new String[0];
        params.put("ACT", containsValue(documentos, "ACT") ? "⚫" : "");
        params.put("CBC", containsValue(documentos, "CBC") ? "⚫" : "");
        params.put("CRP", containsValue(documentos, "CRP") ? "⚫" : "");
        params.put("CL", containsValue(documentos, "CL") ? "⚫" : "");
        params.put("CSC", containsValue(documentos, "CSC") ? "⚫" : "");
        params.put("CFI", containsValue(documentos, "CFI") ? "⚫" : "");
        params.put("CRS", containsValue(documentos, "CRS") ? "⚫" : "");
        String otrosDocumentos = params.get("otrosDocumentos");
        params.put("OTROSDOCS", otrosDocumentos != null ? otrosDocumentos : "");
        
        // Procesar becas del alumno (checkboxes múltiples)
        if (becas == null) becas = new String[0];
        params.put("PROS", containsValue(becas, "PROS") ? "⚫" : "");
        params.put("CAES", containsValue(becas, "CAES") ? "⚫" : "");
        params.put("INGR", containsValue(becas, "INGR") ? "⚫" : "");
        params.put("PERM", containsValue(becas, "PERM") ? "⚫" : "");
        params.put("EXCE", containsValue(becas, "EXCE") ? "⚫" : "");
        String otraBeca = params.get("otraBeca");
        params.put("OTRABC", otraBeca != null ? otraBeca : "");
        
        // Procesar discapacidades del alumno (checkboxes múltiples)
        if (discapacidades == null) discapacidades = new String[0];
        params.put("CEGE", containsValue(discapacidades, "CEGE") ? "⚫" : "");
        params.put("SORD", containsValue(discapacidades, "SORD") ? "⚫" : "");
        params.put("MOTR", containsValue(discapacidades, "MOTR") ? "⚫" : "");
        params.put("VISU", containsValue(discapacidades, "VISU") ? "⚫" : "");
        params.put("AUDI", containsValue(discapacidades, "AUDI") ? "⚫" : "");
        String otraDiscapacidad = params.get("otraDiscapacidad");
        params.put("OTRADIS", otraDiscapacidad != null ? otraDiscapacidad : "");
    }
    
    private void descomponerFecha(String fecha, Map<String, String> params, String diaKey, String mesKey, String anioKey) {
        if (fecha != null && !fecha.isEmpty()) {
            try {
                String[] partes = fecha.split("-");
                if (partes.length == 3) {
                    String anio = partes[0];
                    String mes = partes[1];
                    String dia = partes[2];

                    params.put(diaKey, dia);
                    params.put(mesKey, mes);
                    params.put(anioKey, anio);
                }
            } catch (Exception e) {
                System.out.println("Error al procesar la fecha: " + fecha);
            }
        }
    }
    
    private boolean containsValue(String[] array, String value) {
        if (array == null || value == null) {
            return false;
        }
        for (String item : array) {
            if (value.equals(item.trim())) {
                return true;
            }
        }
        return false;
    }
}