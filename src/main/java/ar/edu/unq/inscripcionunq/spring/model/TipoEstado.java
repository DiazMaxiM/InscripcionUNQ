package ar.edu.unq.inscripcionunq.spring.model;

import java.util.HashSet;
import java.util.Set;

public enum TipoEstado {
	
	ENABLED,
	DISABLED;
	
	private static final  Set<String> values = new HashSet<>(TipoEstado.values().length);

    static{
        for(TipoEstado estado: TipoEstado.values())
            values.add(estado.name());
    }

    public static boolean contains( String value ){
        return values.contains(value);
    }
    
    public static boolean esEstadoHabiltado(TipoEstado estado) {
		return TipoEstado.ENABLED == estado;
	}
}