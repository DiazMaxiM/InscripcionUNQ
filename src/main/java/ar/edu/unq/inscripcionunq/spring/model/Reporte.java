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

public class Reporte {
	private Encuesta encuesta;
	private TipoReporte tipoReporte;
	private byte[] binaryXLSGenerate;
	private static List<String[]> DATA;
	private List<String> comisiones = new ArrayList<String>();

    public Reporte(Encuesta encuesta, List<String> comisiones, String tipo) {
    	this.encuesta = encuesta;
    	this.tipoReporte = TipoReporte.getEnum(tipo);
    	this.comisiones = comisiones;
    }
    
    public Reporte(Encuesta encuesta, List<String> comisiones, TipoReporte tipo) {
    	this.encuesta = encuesta;
    	this.tipoReporte = tipo;
    	this.comisiones = comisiones;
    }
	
	public Reporte(Encuesta encuesta, String tipo) {
    	this.encuesta = encuesta;
    	this.tipoReporte = TipoReporte.getEnum(tipo);
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
		
		String nombreArchivo = this.encuesta.getNombre() + " - " + this.tipoReporte.getDescripcion() + ".xls";
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		byte [] outArray = outByteStream.toByteArray();
		binaryXLSGenerate = outArray;
	}
	
	public void generarReporteAlumnosInscriptosPorComision() throws Exception{
		DATA = new ArrayList<String[]>();
    	for (OfertaAcademica oferta : this.encuesta.getOfertasAcademicas()) {
    		int index = 0;
    		for (Comision comision : oferta.getComisiones()) {
    			
    			DATA.add(new String[] {
    		            	new String(oferta.getNombre()),
    		            	new String(comision.getNombre()),
    		            	new String(comision.getCupo().toString()),
    		            	new String(comisiones.get(index)),
    		        		});
    			index ++;
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
	
	public void generarReporteAlumnosSinProblemasDeCupo() throws Exception {
    	DATA = new ArrayList<String[]>();

    	for ( Estudiante estudiante : this.encuesta.getEstudiantes() ) {
			boolean cupoDisponible = true;
    		for (Comision comision : estudiante.getRegistroComisiones()) {
    			if (comision.getCupo() < 1 ) cupoDisponible = false;
			}
			if (cupoDisponible){
				DATA.add(new String[] {
					new String(estudiante.getNombre()),
				});
			}
		}
        String[] headers = new String[]{
			"Alumno sin problemas de cupo"
        };
		generarXls(headers, DATA);
    }
}