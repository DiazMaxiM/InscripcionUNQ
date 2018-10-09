package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OrderBy;

@Entity(name = "Periodo")
public class Periodo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	@OrderBy("codigo DESC")
	private String codigo;
	private Integer anho;
	private Integer numero;
	@Enumerated(EnumType.STRING)
	private TypeStatus estado = TypeStatus.ENABLED;
	@Enumerated(EnumType.STRING)
	private TipoPeriodo duracion;
	
	public Periodo() {
	}

	public Periodo(Integer anho, Integer numero, TipoPeriodo duracion) {
		this.anho = anho;
		this.numero = numero;
		this.duracion = duracion;
		this.generarCodigo();
	}



	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getAnho() {
		return anho;
	}


	public void setAnho(Integer anho) {
		this.anho = anho;
	}


	public TypeStatus getEstado() {
		return estado;
	}

	public void setEstado(TypeStatus estado) {
		this.estado = estado;
	}

	public Integer getNumero() {
		return numero;
	}


	public void setNumero(Integer numero) {
		this.numero = numero;
	}


	public void generarCodigo() {
		String codigoGenerado = TipoPeriodo.getCodigoPeriodo(this.duracion) +
				this.numero + "-" + anho;
		this.setCodigo(codigoGenerado);
		
	}	
	
	public TipoPeriodo getDuracion() {
		return duracion;
	}


	public void setDuracion(TipoPeriodo duracion) {
		this.duracion = duracion;
	}
}