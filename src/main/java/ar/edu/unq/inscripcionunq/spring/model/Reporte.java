package ar.edu.unq.inscripcionunq.spring.model;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import ar.edu.unq.inscripcionunq.spring.exception.ReporteNoExisteException;

public class Reporte {
	private Encuesta encuesta;
	private TipoReporte tipoReporte;
	private byte[] binaryXLSGenerate;
	private static List<String[]> DATA;

	public Reporte(Encuesta encuesta, TipoReporte tipo) {
    	this.encuesta = encuesta;
    	this.tipoReporte = tipo;
    }

	public byte[] getBinaryPDF() {
		return binaryXLSGenerate;
	}

    public void generarReporte() throws Exception {
    	switch (this.tipoReporte) {
	        case ALUMNOSPORCOMISION: 
	        	generarReporteAlumnosInscriptosPorComision();
	            break;
	        case ALUMNOSSINPROBLEMASDECUPO: 
	        	generarReporteAlumnosSinProblemasDeCupo();
	        	break;
	        default:
	        	new ReporteNoExisteException();
    	}
    }
	
	public void generarXls(String[] headers, List<String[]> data ) throws Exception{
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Reporte");
		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);

		HSSFRow headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; ++i) {
				String header = headers[i];
				HSSFCell cell = headerRow.createCell(i);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(header);
		}

		for (int i = 0; i < data.size(); ++i) {
			HSSFRow dataRow = sheet.createRow(i + 1);
			String[] dat =  data.get(i);
			for (int e = 0; e < dat.length; ++e) {
				dataRow.createCell(e).setCellValue(dat[e]);
			}
		}
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		byte [] outArray = outByteStream.toByteArray();
		binaryXLSGenerate = outArray;
	}
	
	public void generarReporteAlumnosSinProblemasDeCupo() throws Exception {
    	DATA = new ArrayList<String[]>();
    	List<Comision> comisionesEstudiante = new ArrayList<Comision>();
    	List<String> linea = new ArrayList<String>();
    	for ( Estudiante estudiante : this.encuesta.getEstudiantes() ) {
			boolean cupoDisponible = true;
			linea.add(estudiante.getNombre());
			linea.add(estudiante.getEmail());
    		for (Comision comision : estudiante.getRegistroComisiones()) {
    			if (comision.getCupo() < 1 ) cupoDisponible = false;
    			linea.add(comision.getNombre());
			}
			if (cupoDisponible){
				String[] dat = linea.toArray(new String[1]);
				DATA.add(linea.toArray(new String[linea.size()]));
			}
		}
        String[] headers = new String[]{
			"Nombre alumno",
			"E-mail",
			"Comisiones inscripto"
        };
		generarXls(headers, DATA);
    }

	
	public void generarReporteAlumnosInscriptosPorComision() throws Exception{
		DATA = new ArrayList<String[]>();
    	for (OfertaAcademica oferta : this.encuesta.getOfertasAcademicas()) {
    		Integer nroEstudiantes;
    		for (Comision comision : oferta.getComisiones()) {
				
    			nroEstudiantes = 0;
				
				for (Estudiante estudiante : encuesta.getEstudiantes()) {
					for (Comision comisionEstudiante : estudiante.getRegistroComisiones()) {
						if (comisionEstudiante == comision) nroEstudiantes++;
					}
				}    			
    			DATA.add(new String[] {
    		            	new String(oferta.getNombre()),
    		            	new String(comision.getNombre()),
    		            	new String(comision.getCupo().toString()),
    		            	new String(nroEstudiantes.toString()),
    		        		});
			}
		}
		
        String[] headers = new String[]{
                "Nombre Oferta",
                "Nombre Comision",
                "Cupo",
                "Nro de inscriptos"
        };
		this.generarXls(headers, DATA);
	}
}