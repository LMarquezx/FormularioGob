package com.FormularioGob.FormularioGob.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.*;
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

        InputStream template = new ClassPathResource("word/solicitud_template1.docx").getInputStream();
        XWPFDocument doc = new XWPFDocument(template);
        String fechaCompleta = params.get("fecha");
        String fechaNacimiento = params.get("fechaNacimiento");
        //String fechaConSecundaria = params.get("fechaConSecundaria");
        
        // Descomponer fechas
        descomponerFecha(fechaCompleta, params, "dSol", "mSol", "aSol");
        descomponerFecha(fechaNacimiento, params, "diaNa", "mesNa","anioNa");
        //descomponerFecha(fechaConSecundaria, params, "diaConSec", "mesConSec","anioConSec");
        
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
                            System.out.println("DEBUG MARCADOR DETECTADO: " + originalText);
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
        // Debug: mostrar todos los parámetros recibidos
        System.out.println("=== DEBUG PARAMETROS RECIBIDOS ===");
        params.forEach((key, value) -> System.out.println(key + " = " + value));
        System.out.println("================================");
        
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
        System.out.println("DEBUG SEMESTRE - Valor recibido: '" + semestre + "'");
        params.put("1", "1".equals(semestre) ? "⚫" : "");
        params.put("2", "2".equals(semestre) ? "⚫" : "");
        params.put("3", "3".equals(semestre) ? "⚫" : "");
        params.put("4", "4".equals(semestre) ? "⚫" : "");
        params.put("5", "5".equals(semestre) ? "⚫" : "");
        params.put("6", "6".equals(semestre) ? "⚫" : "");
        System.out.println("DEBUG SEMESTRE - Marcadores creados: 1=" + params.get("1") + 
                          ", 2=" + params.get("2") + ", 3=" + params.get("3") + 
                          ", 4=" + params.get("4") + ", 5=" + params.get("5") + ", 6=" + params.get("6"));
        
        // Procesar área propedéutica
        String areaPropedeutica = params.get("areaPropedeutica");
        System.out.println("DEBUG AREA PROPEDEUTICA - Valor recibido: '" + areaPropedeutica + "'");
        params.put("QB", "QB".equals(areaPropedeutica) ? "⚫" : "");
        params.put("EA", "EA".equals(areaPropedeutica) ? "⚫" : "");
        params.put("HC", "HC".equals(areaPropedeutica) ? "⚫" : "");
        params.put("FM", "FM".equals(areaPropedeutica) ? "⚫" : "");
        System.out.println("DEBUG AREA PROPEDEUTICA - Marcadores creados: QB=" + params.get("QB") + 
                          ", EA=" + params.get("EA") + ", HC=" + params.get("HC") + ", FM=" + params.get("FM"));
        
        // Procesar grupo
        String grupo = params.get("grupo");
        System.out.println("DEBUG GRUPO - Valor recibido: '" + grupo + "'");
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
        System.out.println("DEBUG GRUPO - Marcadores creados: A=" + params.get("A") + 
                          ", B=" + params.get("B") + ", C=" + params.get("C") + 
                          ", D=" + params.get("D") + ", E=" + params.get("E") + 
                          ", F=" + params.get("F") + ", G=" + params.get("G") + 
                          ", H=" + params.get("H") + ", I=" + params.get("I") + 
                          ", U=" + params.get("U"));
        
        // Procesar tipo de alumno de telebachillerato
        String tipoAlumnoTelebach = params.get("tipoAlumnoTelebach");
        System.out.println("DEBUG TIPO ALUMNO TELEBACH - Valor recibido: '" + tipoAlumnoTelebach + "'");
        params.put("REG", "REG".equals(tipoAlumnoTelebach) ? "⚫" : "");
        params.put("IREG", "IREG".equals(tipoAlumnoTelebach) ? "⚫" : "");
        params.put("REP", "REP".equals(tipoAlumnoTelebach) ? "⚫" : "");
        System.out.println("DEBUG TIPO ALUMNO TELEBACH - Marcadores creados: REG=" + params.get("REG") + 
                          ", IREG=" + params.get("IREG") + ", REP=" + params.get("REP"));
        
        // Procesar campos de la madre
        String sexoMadre = params.get("sexoMadre");
        System.out.println("DEBUG MADRE - Sexo recibido: '" + sexoMadre + "'");
        params.put("MM", "MASCULINO".equals(sexoMadre) ? "⚫" : "");
        params.put("FM", "FEMENINO".equals(sexoMadre) ? "⚫" : "");
        
        String tutorMadre = params.get("tutorMadre");
        System.out.println("DEBUG MADRE - Tutor recibido: '" + tutorMadre + "'");
        params.put("SM", "SI".equals(tutorMadre) ? "⚫" : "");
        params.put("NM", "NO".equals(tutorMadre) ? "⚫" : "");
        
        String sabeLeerMadre = params.get("sabeLeerMadre");
        System.out.println("DEBUG MADRE - Sabe leer recibido: '" + sabeLeerMadre + "'");
        params.put("SLM", "SI".equals(sabeLeerMadre) ? "⚫" : "");
        params.put("NLM", "NO".equals(sabeLeerMadre) ? "⚫" : "");
        
        // Procesar campos del padre
        String sexoPadre = params.get("sexoPadre");
        System.out.println("DEBUG PADRE - Sexo recibido: '" + sexoPadre + "'");
        params.put("MP", "MASCULINO".equals(sexoPadre) ? "⚫" : "");
        params.put("FP", "FEMENINO".equals(sexoPadre) ? "⚫" : "");
        
        String tutorPadre = params.get("tutorPadre");
        System.out.println("DEBUG PADRE - Tutor recibido: '" + tutorPadre + "'");
        params.put("SP", "SI".equals(tutorPadre) ? "⚫" : "");
        params.put("NP", "NO".equals(tutorPadre) ? "⚫" : "");
        
        String sabeLeerPadre = params.get("sabeLeerPadre");
        System.out.println("DEBUG PADRE - Sabe leer recibido: '" + sabeLeerPadre + "'");
        params.put("SLP", "SI".equals(sabeLeerPadre) ? "⚫" : "");
        params.put("NLP", "NO".equals(sabeLeerPadre) ? "⚫" : "");
        
        // Procesar campos del tutor
        String sexoTutor = params.get("sexoTutor");
        System.out.println("DEBUG TUTOR - Sexo recibido: '" + sexoTutor + "'");
        params.put("MT", "MASCULINO".equals(sexoTutor) ? "⚫" : "");
        params.put("FT", "FEMENINO".equals(sexoTutor) ? "⚫" : "");
        
        String sabeLeerTutor = params.get("sabeLeerTutor");
        System.out.println("DEBUG TUTOR - Sabe leer recibido: '" + sabeLeerTutor + "'");
        params.put("SLT", "SI".equals(sabeLeerTutor) ? "⚫" : "");
        params.put("NLT", "NO".equals(sabeLeerTutor) ? "⚫" : "");
        
        // Procesar parentesco del tutor
        String parentesco = params.get("parentesco");
        System.out.println("DEBUG TUTOR - Parentesco recibido: '" + parentesco + "'");
        params.put("PERNT", parentesco != null ? parentesco : "");
        
        // Procesar documentos presentados (checkboxes múltiples)
        if (documentos == null) documentos = new String[0];
        System.out.println("DEBUG DOCUMENTOS - Valores recibidos: " + String.join(",", documentos));
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
        System.out.println("DEBUG BECAS - Valores recibidos: " + String.join(",", becas));
        params.put("PROS", containsValue(becas, "PROS") ? "⚫" : "");
        params.put("CAES", containsValue(becas, "CAES") ? "⚫" : "");
        params.put("INGR", containsValue(becas, "INGR") ? "⚫" : "");
        params.put("PERM", containsValue(becas, "PERM") ? "⚫" : "");
        params.put("EXCE", containsValue(becas, "EXCE") ? "⚫" : "");
        String otraBeca = params.get("otraBeca");
        params.put("OTRABC", otraBeca != null ? otraBeca : "");
        
        // Procesar discapacidades del alumno (checkboxes múltiples)
        if (discapacidades == null) discapacidades = new String[0];
        System.out.println("DEBUG DISCAPACIDADES - Valores recibidos: " + String.join(",", discapacidades));
        params.put("CEGE", containsValue(discapacidades, "CEGE") ? "⚫" : "");
        params.put("SORD", containsValue(discapacidades, "SORD") ? "⚫" : "");
        params.put("MOTR", containsValue(discapacidades, "MOTR") ? "⚫" : "");
        params.put("VISU", containsValue(discapacidades, "VISU") ? "⚫" : "");
        params.put("AUDI", containsValue(discapacidades, "AUDI") ? "⚫" : "");
        String otraDiscapacidad = params.get("otraDiscapacidad");
        params.put("OTRADIS", otraDiscapacidad != null ? otraDiscapacidad : "");
        
        // Debug: mostrar valores procesados
        System.out.println("=== DEBUG CAMPOS PROCESADOS ===");
        System.out.println("masc = " + params.get("masc"));
        System.out.println("fem = " + params.get("fem"));
        System.out.println("mex = " + params.get("mex"));
        System.out.println("otra = " + params.get("otra"));
        System.out.println("certi = " + params.get("certi"));
        System.out.println("fcerti = " + params.get("fcerti"));
        System.out.println("escol = " + params.get("escol"));
        System.out.println("vea = " + params.get("vea"));
        System.out.println("mat = " + params.get("mat"));
        System.out.println("vesp = " + params.get("vesp"));
        System.out.println("noc = " + params.get("noc"));
        System.out.println("abi = " + params.get("abi"));
        System.out.println("sev = " + params.get("sev"));
        System.out.println("sep = " + params.get("sep"));
        System.out.println("otro = " + params.get("otro"));
        System.out.println("semestre = " + params.get("semestre"));
        System.out.println("1 = " + params.get("1"));
        System.out.println("2 = " + params.get("2"));
        System.out.println("3 = " + params.get("3"));
        System.out.println("4 = " + params.get("4"));
        System.out.println("5 = " + params.get("5"));
        System.out.println("6 = " + params.get("6"));
        System.out.println("areaPropedeutica = " + params.get("areaPropedeutica"));
        System.out.println("QB = " + params.get("QB"));
        System.out.println("EA = " + params.get("EA"));
        System.out.println("HC = " + params.get("HC"));
        System.out.println("FM = " + params.get("FM"));
        System.out.println("grupo = " + params.get("grupo"));
        System.out.println("A = " + params.get("A"));
        System.out.println("B = " + params.get("B"));
        System.out.println("C = " + params.get("C"));
        System.out.println("D = " + params.get("D"));
        System.out.println("E = " + params.get("E"));
        System.out.println("F = " + params.get("F"));
        System.out.println("G = " + params.get("G"));
        System.out.println("H = " + params.get("H"));
        System.out.println("I = " + params.get("I"));
        System.out.println("--- CAMPOS MADRE ---");
        System.out.println("sexoMadre = " + params.get("sexoMadre"));
        System.out.println("MM = " + params.get("MM"));
        System.out.println("FM = " + params.get("FM"));
        System.out.println("tutorMadre = " + params.get("tutorMadre"));
        System.out.println("SM = " + params.get("SM"));
        System.out.println("NM = " + params.get("NM"));
        System.out.println("sabeLeerMadre = " + params.get("sabeLeerMadre"));
        System.out.println("SLM = " + params.get("SLM"));
        System.out.println("NLM = " + params.get("NLM"));
        System.out.println("--- CAMPOS PADRE ---");
        System.out.println("sexoPadre = " + params.get("sexoPadre"));
        System.out.println("MP = " + params.get("MP"));
        System.out.println("FP = " + params.get("FP"));
        System.out.println("tutorPadre = " + params.get("tutorPadre"));
        System.out.println("SP = " + params.get("SP"));
        System.out.println("NP = " + params.get("NP"));
        System.out.println("sabeLeerPadre = " + params.get("sabeLeerPadre"));
        System.out.println("SLP = " + params.get("SLP"));
        System.out.println("NLP = " + params.get("NLP"));
        System.out.println("--- CAMPOS TUTOR ---");
        System.out.println("sexoTutor = " + params.get("sexoTutor"));
        System.out.println("MT = " + params.get("MT"));
        System.out.println("FT = " + params.get("FT"));
        System.out.println("sabeLeerTutor = " + params.get("sabeLeerTutor"));
        System.out.println("SLT = " + params.get("SLT"));
        System.out.println("NLT = " + params.get("NLT"));
        System.out.println("parentesco = " + params.get("parentesco"));
        System.out.println("PERNT = " + params.get("PERNT"));
        System.out.println("--- DOCUMENTOS PRESENTADOS ---");
        System.out.println("ACT = " + params.get("ACT"));
        System.out.println("CBC = " + params.get("CBC"));
        System.out.println("CRP = " + params.get("CRP"));
        System.out.println("CL = " + params.get("CL"));
        System.out.println("CSC = " + params.get("CSC"));
        System.out.println("CFI = " + params.get("CFI"));
        System.out.println("CRS = " + params.get("CRS"));
        System.out.println("OTROSDOCS = " + params.get("OTROSDOCS"));
        System.out.println("--- BECAS DEL ALUMNO ---");
        System.out.println("PROS = " + params.get("PROS"));
        System.out.println("CAES = " + params.get("CAES"));
        System.out.println("INGR = " + params.get("INGR"));
        System.out.println("PERM = " + params.get("PERM"));
        System.out.println("EXCE = " + params.get("EXCE"));
        System.out.println("OTRABC = " + params.get("OTRABC"));
        System.out.println("--- DISCAPACIDADES DEL ALUMNO ---");
        System.out.println("CEGE = " + params.get("CEGE"));
        System.out.println("SORD = " + params.get("SORD"));
        System.out.println("MOTR = " + params.get("MOTR"));
        System.out.println("VISU = " + params.get("VISU"));
        System.out.println("AUDI = " + params.get("AUDI"));
        System.out.println("OTRADIS = " + params.get("OTRADIS"));
        System.out.println("===============================");
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