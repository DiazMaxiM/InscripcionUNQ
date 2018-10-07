package ar.edu.unq.inscripcionunq.spring.model;

import java.util.HashSet;
import java.util.Set;

public enum TipoPeriodo {
	MENSUAL, BIMESTRAL, TRIMESTRAL, CUATRIMESTRAL,SEMESTRAL, ANUAL;
	
	private static final  Set<String> values = new HashSet<>(TipoPeriodo.values().length);

    static{
        for(TipoPeriodo periodo: TipoPeriodo.values())
            values.add(periodo.name());
    }

    public static boolean contains( String value ){
        return values.contains(value);
    }

	public static String getCodigoPeriodo(TipoPeriodo duracion) {
		String codigoPeriodo;
		  switch (duracion) {
          case MENSUAL: 
              codigoPeriodo = "M";
              break;
          case BIMESTRAL: 
        	  codigoPeriodo = "B";
              break;
          case TRIMESTRAL:
        	  codigoPeriodo = "T";
        	  break;
          case CUATRIMESTRAL: 
        	  codigoPeriodo = "C";
              break;
          default:
              codigoPeriodo = "A";
              break;
      }
		  return codigoPeriodo;
	} 

}