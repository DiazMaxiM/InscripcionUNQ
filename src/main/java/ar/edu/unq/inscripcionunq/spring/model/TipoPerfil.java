package ar.edu.unq.inscripcionunq.spring.model;

import java.util.HashSet;
import java.util.Set;

public enum TipoPerfil {
   
	ESTUDIANTE, 
	ADMINISTRADOR;

	private static final  Set<String> values = new HashSet<>(TipoPerfil.values().length);

    static{
        for(TipoPerfil perfil: TipoPerfil.values())
            values.add(perfil.name());
    }

    public static boolean contains( String value ){
        return values.contains(value);
    }
}