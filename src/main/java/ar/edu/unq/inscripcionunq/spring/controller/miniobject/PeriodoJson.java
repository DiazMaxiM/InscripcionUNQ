package ar.edu.unq.inscripcionunq.spring.controller.miniobject;


import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.model.TipoPeriodo;

public class PeriodoJson {
	
	public  Long id;
	public String codigo;
	public Integer anho;
	public String tipoPeriodo;
	public Integer numero;
	private TipoPeriodo tipoṔeriodo;
	
	public PeriodoJson() {
		super();
	
	}
	
	public PeriodoJson(Periodo periodo) {
		this.id = periodo.getId();
		this.codigo = periodo.getCodigo();
		this.anho = periodo.getAnho();
		this.tipoPeriodo = periodo.getDuracion().name();
		this.numero = periodo.getNumero();
		
	}
	
	

}