package ar.edu.unq.inscripcionunq.spring.model;

public enum TipoReporte {
	
	ALUMNOSPORCOMISION("Cantidad de alumnos inscriptos por comisión"), 
	ALUMNOSSINPROBLEMASDECUPO("Lista de alumnos sin problemas de cupo"),
	REPORTEGENERAL("Reporte general de encuesta");

	private final String descripcion;

	TipoReporte(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public static TipoReporte getEnum(String value) {
        if(value == null)
            throw new IllegalArgumentException();
        for(TipoReporte v : values())
            if(value.equalsIgnoreCase(v.getDescripcion())) return v;
        throw new IllegalArgumentException();
    }
}