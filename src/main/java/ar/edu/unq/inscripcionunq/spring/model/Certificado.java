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

public class Certificado {
	private Estudiante estudiante;
	private byte[] binaryPDFGenerate;
	private String nombreArchivo;

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public byte[] getBinaryPDF() {
		return binaryPDFGenerate;
	}

	public void generarPDF() throws DocumentException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Document documento = new Document(PageSize.A4, 50, 50, 50, 50);
		if (nombreArchivo == null) {
			PdfWriter.getInstance(documento, byteArrayOutputStream);
		} else {
			try {
				PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		documento.open();

		Image img1 = null;
		try {
			img1 = Image.getInstance("./src/main/resources/logo_unqui.png");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		img1.scaleAbsolute(150f, 50f);
		// document.add(img1);
		Chunk glue = new Chunk(new VerticalPositionMark());
		Paragraph p = new Paragraph();
		p.add(new Chunk(glue));
		LocalDateTime fechaActual = LocalDateTime.now();
		p.add(fechaActual.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
		documento.add(p);
		documento.add(img1);
		Font fuente = new Font(FontFamily.HELVETICA, 14, Font.BOLD);

		p = new Paragraph("CERTIFICADO DE PREINSCRIPCION", fuente);
		p.setAlignment(Element.ALIGN_CENTER);
		DottedLineSeparator dottedline = new DottedLineSeparator();
		dottedline.setOffset(-5);
		dottedline.setGap(0.1f);
		p.add(dottedline);
		documento.add(p);
		documento.add(new Paragraph(" "));
		documento.add(new Paragraph("Estudiante: " + estudiante.getApellido() + " " + estudiante.getNombre()));
		documento.add(new Paragraph(" "));
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
		List<Comision> comisionesInscripto = estudiante.getRegistroComisiones();
		for (Comision comision : comisionesInscripto) {
			cell = new PdfPCell(new Phrase(comision.getMateria().getNombre()));
			cell.setBorder(Rectangle.BOX);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(comision.getNombre()));
			cell.setBorder(Rectangle.BOX);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(comision.getHorariosString()));
			cell.setBorder(Rectangle.BOX);
			table.addCell(cell);

		}

		documento.add(table);

		documento.close();

		binaryPDFGenerate = byteArrayOutputStream.toByteArray();

	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;

	}

}
