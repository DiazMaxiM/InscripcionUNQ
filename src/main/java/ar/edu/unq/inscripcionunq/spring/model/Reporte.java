package ar.edu.unq.inscripcionunq.spring.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

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
	        case REPORTEGENERAL:
	        	generarReporteGeneral();
	        	break;
	        default:
	        	new ReporteNoExisteException();
    	}
    }
	
	public void generarXls(String[] headers, List<String[]> data, CellRangeAddress mergeCells ) throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, this.tipoReporte.getDescripcion());
		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		font.setBold(true);
		headerStyle.setFont(font);
		HSSFCellStyle styleGreen = workbook.createCellStyle();
		styleGreen.setFont(font);
		styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleGreen.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		styleGreen.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);

		List<Integer> columnas = new ArrayList<Integer>();
		
		HSSFRow headerRow = sheet.createRow(0);
		
		for (int i = 0; i < headers.length; ++i) {
				String header = headers[i];
				columnas.add(i, headers[i].length());
				HSSFCell cell = headerRow.createCell(i);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(header);
		}

		if (!data.isEmpty()){
			for (int i = 0; i < data.size(); ++i) {
				HSSFRow dataRow = sheet.createRow(i + 1);
				String[] dat =  data.get(i);
				for (int e = 0; e < dat.length; ++e) {
					HSSFCell cell = dataRow.createCell(e);
					if (this.tipoReporte == TipoReporte.REPORTEGENERAL && dat[e]== "X")
					cell.setCellStyle(styleGreen);
					cell.setCellValue(dat[e]);
					if ( columnas.size() < e + 1) columnas.add(e, dat[e].length());
					if ( columnas.get(e) < dat[e].length()) columnas.set(e, dat[e].length());

				}
			}
		}
		
		if (mergeCells != null) sheet.addMergedRegion(mergeCells);
		
		for (int i = 0 ; i < columnas.size(); i++) {
			sheet.setColumnWidth(i, columnas.get(i) * 256);
		}
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
	    
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
			linea.add(estudiante.getApellido());
			linea.add(estudiante.getEmail());
			
    		for (Comision comision : estudiante.getRegistroComisiones()) {
    			if (comision.getCupo() < getNroDeInscriptoPorComision(this.encuesta, comision) ) cupoDisponible = false;
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
			"Apellido alumno",
			"E-mail",
			"Comisiones inscripto"
        };
        
        CellRangeAddress cellMerge = null;
        
        if ( nro > 4 ){
        	cellMerge = new CellRangeAddress(0,0,3,nro-1);
        }
		generarXls(headers, DATA, cellMerge);
    }
	
	public int getNroDeInscriptoPorComision(Encuesta encuesta, Comision comision) {
		int inscriptos = 0;
		
		for (Estudiante estudiante : encuesta.getEstudiantes()) {
			for (Comision comisionEstudiante : estudiante.getRegistroComisiones()) {
				if (comisionEstudiante == comision) inscriptos++;
			}
		}   
		return inscriptos;
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
                "Oferta",
                "Comisión",
                "Cupo",
                "Nro de inscriptos"
        };
        CellRangeAddress cellMerge = null;
		this.generarXls(headers, DATA, cellMerge);
	}
	
	public void generarReporteGeneral() throws IOException {
		DATA = new ArrayList<String[]>();
    	
		List<String> linea = new ArrayList<String>();
		linea.add("Nombre");
		linea.add("Apellido");
		linea.add("DNI");
		linea.add("E-mail");
		
		List<Materia> columnasMaterias = new ArrayList<Materia>();
		
    	for (OfertaAcademica oferta : this.encuesta.getOfertasAcademicas()) {
    		for (Comision comision : oferta.getComisiones()) {
    			if(!columnasMaterias.contains(comision.getMateria())) {
    			
    				columnasMaterias.add(comision.getMateria());
					linea.add(comision.getMateria().getNombre());
    			}
    		}
    	} 
		
		String[] headers = linea.toArray(new String[linea.size()]);
		String estadoMateria = new String();
		
		List<Estudiante> estudiantes = this.encuesta.getEstudiantes();
		Collections.sort(estudiantes, Comparator.comparing(Estudiante::getApellido));
		
    	for (Estudiante estudiante : estudiantes) {
			linea = new ArrayList<String>();
			linea.add(estudiante.getNombre());
			linea.add(estudiante.getApellido());
			linea.add(estudiante.getDni());
			linea.add(estudiante.getEmail());
			
			HashMap<String, String> mc = this.materiaDeComisiones(estudiante.getRegistroComisiones());
			
			for (Materia columnaMateria : columnasMaterias) {
				estadoMateria = " ";
				for (Materia materiaAprobada : estudiante.getMateriasAprobadas()) {
					if (materiaAprobada.equals(columnaMateria))estadoMateria = "X";
				}
				if (mc.containsKey(columnaMateria.getNombre())) estadoMateria = mc.get(columnaMateria.getNombre());
				
				linea.add(estadoMateria);
			}
			DATA.add(linea.toArray(new String[linea.size()-1]));
		}		
        CellRangeAddress cellMerge = null;
		this.generarXls(headers, DATA, cellMerge);
	}
	
	private HashMap<String, String> materiaDeComisiones(List<Comision> comisiones){
		HashMap<String, String> mc = new HashMap<String, String>();
		for(Comision c : comisiones) {
			mc.put(c.getMateria().getNombre(), c.getNombre());
		}
		return mc;
	}
}