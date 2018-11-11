package ar.edu.unq.inscripcionunq.spring.model;

public enum TipoEstadoIncidencia {
	
	ABIERTA("Abierta"), 
	EN_PROCESO("En Proceso"), 
	CERRADA("Cerrada");

	private final String estado;

	TipoEstadoIncidencia(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return this.estado;
	}

	public static TipoEstadoIncidencia getEnum(String value) {
        if(value == null)
            throw new IllegalArgumentException();
        for(TipoEstadoIncidencia v : values())
            if(value.equalsIgnoreCase(v.getEstado())) return v;
        throw new IllegalArgumentException();
    }
}