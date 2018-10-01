package ar.edu.unq.inscripcionunq.spring.model;

import java.util.HashSet;
import java.util.Set;

public enum TypeStatus {
	ENABLED,
	DISABLED;
	
	private static final  Set<String> values = new HashSet<>(TypeStatus.values().length);

    static{
        for(TypeStatus estado: TypeStatus.values())
            values.add(estado.name());
    }

    public static boolean contains( String value ){
        return values.contains(value);
    }
    
    public static boolean esEstadoHabiltado(TypeStatus estado) {
		return TypeStatus.ENABLED == estado;
	}
}