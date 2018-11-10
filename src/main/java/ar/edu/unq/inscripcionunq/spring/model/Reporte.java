package ar.edu.unq.inscripcionunq.spring.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.SystemException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ar.edu.unq.inscripcionunq.spring.exception.ExceptionGeneric;
import ar.edu.unq.inscripcionunq.spring.exception.ExceptionSystem;
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

    public void generarReporte() throws IOException {
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
	
	public void generarXls(String[] headers, List<String[]> data, CellRangeAddress mergeCells ) throws IOException{
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Reporte");
		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		//font.setFontName("Serif");
		font.setBold(true);
		headerStyle.setFont(font);
		
		HSSFRow headerRow = sheet.createRow(0);
		
		for (int i = 0; i < headers.length; ++i) {
				String header = headers[i];
				HSSFCell cell = headerRow.createCell(i);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(header);
		}

		if (!data.isEmpty()){
			for (int i = 0; i < data.size(); ++i) {
				HSSFRow dataRow = sheet.createRow(i + 1);
				String[] dat =  data.get(i);
				for (int e = 0; e < dat.length; ++e) {
					dataRow.createCell(e).setCellValue(dat[e]);
				}
			}
		}
		
		if (mergeCells != null) sheet.addMergedRegion(mergeCells);
		

	    
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
	    /*for (int x = 0; x < sheet.getRow(0).getPhysicalNumberOfCells(); x++) {
	        sheet.setColumnWidth(x, 20*256);
	    }*/
	    
		workbook.write(outByteStream);
		byte [] outArray = outByteStream.toByteArray();
		binaryXLSGenerate = outArray;
		outByteStream.flush();
		outByteStream.close();
		workbook.close();

	}
	
	public void generarReporteAlumnosSinProblemasDeCupo() throws IOException {
    	DATA = new ArrayList<String[]>();
    	List<String> linea;
    	int nro = 0;
    	
    	for ( Estudiante estudiante : this.encuesta.getEstudiantes() ) {
			boolean cupoDisponible = true;
			linea = new ArrayList<String>();
			linea.add(estudiante.getNombre());
			linea.add(estudiante.getEmail());
			
    		for (Comision comision : estudiante.getRegistroComisiones()) {
    			if (comision.getCupo() < 1 ) cupoDisponible = false;
    			linea.add(comision.getNombre());
			}
    		
    		if (estudiante.getRegistroComisiones().isEmpty()) cupoDisponible = false;
    		
			if (cupoDisponible){
				DATA.add(linea.toArray(new String[linea.size()-1]));
				if (linea.size() > nro) nro = linea.size(); 
			}
		}
    	
    	if(DATA.size() == 0) throw new IOException("Sin datos para generar el reporte");
    	
        String[] headers = new String[]{
			"Nombre alumno",
			"E-mail",
			"Comisiones inscripto"
        };
        
        CellRangeAddress cellMerge = null;
        
        if ( nro > 3 ){
        	cellMerge = new CellRangeAddress(0,0,2,nro-1);
        }
		generarXls(headers, DATA, cellMerge);
    }

	
	public void generarReporteAlumnosInscriptosPorComision() throws IOException{
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
		
    	if (DATA.size() == 0) throw new IOException("Sin datos para generar el reporte");
    	
        String[] headers = new String[]{
                "Nombre Oferta",
                "Nombre Comision",
                "Cupo",
                "Nro de inscriptos"
        };
        CellRangeAddress cellMerge = null;
		this.generarXls(headers, DATA, cellMerge);
	}
}