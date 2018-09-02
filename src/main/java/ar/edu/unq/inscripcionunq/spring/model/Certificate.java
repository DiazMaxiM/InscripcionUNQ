package ar.edu.unq.inscripcionunq.spring.model;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class Certificate {

	public byte[] getBinaryPDF() throws DocumentException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfWriter.getInstance(document, byteArrayOutputStream);
		document.open();

		document.close();

		return byteArrayOutputStream.toByteArray();

	}

}
