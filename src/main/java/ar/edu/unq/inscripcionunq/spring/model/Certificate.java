package ar.edu.unq.inscripcionunq.spring.model;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Certificate {

	public byte[] getBinaryPDF() throws DocumentException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfWriter.getInstance(document, byteArrayOutputStream);
		document.open();
		document.add(new Paragraph("This page will NOT be followed by a blank page!"));
		document.newPage();
		// we don't add anything to this page: newPage() will be ignored
		document.newPage();
		document.add(new Paragraph("This page will be followed by a blank page!"));
		document.newPage();
		document.newPage();
		document.add(new Paragraph("The previous page was a blank page!"));
		// step 5
		document.close();

		return byteArrayOutputStream.toByteArray();

	}

}
