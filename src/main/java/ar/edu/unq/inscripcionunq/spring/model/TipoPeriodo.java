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
    
    public static int getCantidadDePeriodo(String value) {
        int cantidadPeriodo;
        TipoPeriodo tipo = TipoPeriodo.MENSUAL;
        if(value == null) throw new IllegalArgumentException();
        for(TipoPeriodo v : values())
            if(value.equalsIgnoreCase(v.name())) tipo = v;

		  switch (tipo) {
          case MENSUAL: 
              cantidadPeriodo = 12;
              break;
          case BIMESTRAL: 
        	  cantidadPeriodo = 6;
              break;
          case TRIMESTRAL:
        	  cantidadPeriodo = 4;
        	  break;
          case CUATRIMESTRAL: 
        	  cantidadPeriodo = 3;
              break;
          case SEMESTRAL: 
        	  cantidadPeriodo = 2;
              break;
          default:
              cantidadPeriodo = 1;
              break;
      }
		  return cantidadPeriodo;
	}

}