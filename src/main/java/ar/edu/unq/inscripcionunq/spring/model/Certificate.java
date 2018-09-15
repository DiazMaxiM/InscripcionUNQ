package ar.edu.unq.inscripcionunq.spring.model;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

public class Certificate {

	private Student estudiante;
	private byte[] binaryPDFGenerate;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getBynaryPDF() {
		return binaryPDFGenerate;
	}

	public void generatePDF() throws DocumentException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		if (fileName == null) {
			PdfWriter.getInstance(document, byteArrayOutputStream);
		} else {
			try {
				PdfWriter.getInstance(document, new FileOutputStream(fileName));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		document.open();

		Image img1 = null;
		try {
			img1 = Image.getInstance("./src/main/resources/logo_unqui.png");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		img1.scaleAbsolute(150f, 50f);
		// document.add(img1);
		Chunk glue = new Chunk(new VerticalPositionMark());
		Paragraph p = new Paragraph();
		p.add(new Chunk(glue));
		LocalDateTime fechaActual = LocalDateTime.now();
		p.add(fechaActual.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
		document.add(p);
		document.add(img1);
		Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD);

		p = new Paragraph("CERTIFICADO DE PREINSCRIPCION", font);
		p.setAlignment(Element.ALIGN_CENTER);
		DottedLineSeparator dottedline = new DottedLineSeparator();
		dottedline.setOffset(-5);
		dottedline.setGap(0.1f);
		p.add(dottedline);
		document.add(p);
		document.add(new Paragraph(" "));
		document.add(new Paragraph("Estudiante: " + estudiante.getLastName() + " " + estudiante.getName()));
		document.add(new Paragraph(" "));
		PdfPTable table = new PdfPTable(3);
		table.setPaddingTop(1f);
		table.setWidthPercentage(100f);
		PdfPCell cell = new PdfPCell(new Phrase("Materia"));
		cell.setBorder(Rectangle.BOX);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Comision"));
		cell.setBorder(Rectangle.BOX);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Horarios"));
		cell.setBorder(Rectangle.BOX);
		table.addCell(cell);
		List<Commission> comisionesInscripto = estudiante.getCommissionsRegistration();
		for (Commission commission : comisionesInscripto) {
			cell = new PdfPCell(new Phrase(commission.getSubject().getName()));
			cell.setBorder(Rectangle.BOX);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(commission.getName()));
			cell.setBorder(Rectangle.BOX);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(commission.getIntervalosString()));
			cell.setBorder(Rectangle.BOX);
			table.addCell(cell);

		}

		document.add(table);

		document.close();

		binaryPDFGenerate = byteArrayOutputStream.toByteArray();

	}

	public void setEstudiante(Student student) {
		this.estudiante = student;

	}

}
