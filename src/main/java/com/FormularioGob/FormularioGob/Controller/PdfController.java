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
    public void generateDocx(@RequestParam Map<String, String> params, HttpServletResponse response) throws Exception {

        InputStream template = new ClassPathResource("word/solicitud_template.docx").getInputStream();
        XWPFDocument doc = new XWPFDocument(template);
        String fechaCompleta = params.get("fecha");
        String fechaNacimiento = params.get("fechaNacimiento");
        descomponerFecha(fechaCompleta, params, "dSol", "mSol", "aSol");
        descomponerFecha(fechaNacimiento, params, "diaNa", "mesNa","anioNa");

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
}