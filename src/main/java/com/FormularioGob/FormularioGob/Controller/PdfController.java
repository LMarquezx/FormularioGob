package com.FormularioGob.FormularioGob.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
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
    public void generatePdf(
            @RequestParam Map<String, String> params,
            HttpServletResponse response) throws Exception {

        // Carga plantilla
        ClassPathResource pdfRes = new ClassPathResource("pdf/solicitud_template.pdf");
        InputStream in = pdfRes.getInputStream();
        PDDocument doc = PDDocument.load(in);
        PDPage page = doc.getPage(0);

        // Fuente
        ClassPathResource fontRes = new ClassPathResource("fonts/OpenSans-Regular.ttf");
        PDType0Font font = PDType0Font.load(doc, fontRes.getInputStream());

        // Dibuja campos
        PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);
        cs.setFont(font, 12);

        // Ejemplo coordenadas: ajustar X,Y según plantilla
        cs.beginText(); cs.newLineAtOffset(100, 640);
        cs.showText(params.getOrDefault("nombre", ""));
        cs.endText();

        cs.beginText(); cs.newLineAtOffset(300, 640);
        cs.showText(params.getOrDefault("apellido1", ""));
        cs.endText();

        // ... más campos ...
        cs.close();

        // Envía PDF en respuesta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=solicitud_rellena.pdf");
        doc.save(response.getOutputStream());
        doc.close();
    }
}