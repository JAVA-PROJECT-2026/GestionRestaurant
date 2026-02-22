/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
/**
 *
 * @author Ali Barry
 */


public class FacturePDFGenerator {

    public static String genererPDF(String numeroCommande, String contenu) throws Exception {

        File dossier = new File("factures");
        if (!dossier.exists()) {
            dossier.mkdirs();
        }

        String chemin = "factures/facture_" + numeroCommande + ".pdf";

        Document document = new Document(new Rectangle(500, 600));
        PdfWriter.getInstance(document, new FileOutputStream(chemin));

        document.open();

        Font font = new Font(Font.FontFamily.COURIER, 12);
        document.add(new Paragraph(contenu, font));

        document.close();

        return chemin;
    }
}