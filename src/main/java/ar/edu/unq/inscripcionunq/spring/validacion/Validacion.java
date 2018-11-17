package ar.edu.unq.inscripcionunq.spring.validacion;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import ar.edu.unq.inscripcionunq.spring.exception.AnhoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ComisionSinHorariosException;
import ar.edu.unq.inscripcionunq.spring.exception.CupoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.DniInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.HorarioInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriasEquivalentesException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NumeroInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PerfilInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.Horario;
import ar.edu.unq.inscripcionunq.spring.model.Incidencia;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.model.TipoPerfil;
import ar.edu.unq.inscripcionunq.spring.model.TipoPeriodo;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public class Validacion {
	
	private Validacion() {
		throw new IllegalStateException("Validacion class");
	}
	
	public static boolean stringVacio(String string) {
		return StringUtils.isEmpty(string);
	}
	
	public static boolean esEmailValido(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(email);
	}
	
	private static void nombreValido(String nombre) throws NombreInvalidoException {
		if(stringVacio(nombre)){
			throw new NombreInvalidoException();
		}
	}
	
	private static boolean apellidoValido(String apellido) throws ApellidoInvalidoException {
		if(stringVacio(apellido)){
			throw new ApellidoInvalidoException();
		}
		return true;
	}
	
	private static void emailValido(String email) throws EmailInvalidoException{
		if(!esEmailValido(email)){
			throw new EmailInvalidoException();
		}
	}

	public static void validarEstudiante(Estudiante estudiante) throws NombreInvalidoException, EmailInvalidoException, 
	ApellidoInvalidoException {
		nombreValido(estudiante.getNombre());
		apellidoValido(estudiante.getApellido());
		emailValido(estudiante.getEmail());
	}

	public static void validarCarrera(Carrera carrera) throws DescripcionInvalidaException, CodigoInvalidoException, 
	EstadoInvalidoException {
		descripcionValida(carrera.getDescripcion());
		codigoValido(carrera.getCodigo());
		estadoValido(carrera.getEstado());
	}

	private static void estadoValido(TipoEstado estado) throws EstadoInvalidoException {
		if(!TipoEstado.contains(estado.name())){
			throw new EstadoInvalidoException();
		}
	}

	private static void codigoValido(String codigo) throws CodigoInvalidoException {
		if(stringVacio(codigo)) {
			throw new CodigoInvalidoException();
		}
	}

	private static void descripcionValida(String descripcion) throws DescripcionInvalidaException {
		if (stringVacio(descripcion)) {
			throw new DescripcionInvalidaException();
		}
	}

	public static void validarOfertaAcademica(OfertaAcademica oferta) throws DescripcionInvalidaException, 
	 EstadoInvalidoException, CodigoInvalidoException {
		descripcionValida(oferta.getDescripcion());
		estadoValido(oferta.getEstado());
		validarCarrera(oferta.getCarrera());
		validarCodigoInvalido(oferta.getCarrera().getCodigo(), oferta.getPeriodo().getCodigo());
	}

	private static void validarCodigoInvalido(String codigoCarrera, String codigoPeriodo) throws CodigoInvalidoException {
		if(stringVacio(codigoCarrera) || stringVacio(codigoPeriodo)){
			throw new CodigoInvalidoException();
		}
	}
	
	private static void horarioValido(String horario) throws HorarioInvalidoException {
		if(stringVacio(horario)){
			throw new HorarioInvalidoException();
		}
	}
	
	public static void validarCarreras(List<Carrera> carreras) throws DescripcionInvalidaException, 
	CodigoInvalidoException, EstadoInvalidoException {
		for (Carrera carrera : carreras) {
			validarCarrera(carrera);
		}
	}
	
	public static void validarMateria(Materia materia) throws CodigoInvalidoException, NombreInvalidoException, 
	EstadoInvalidoException, DescripcionInvalidaException, HorarioInvalidoException {
		codigoValido(materia.getCodigo());
		nombreValido(materia.getNombre());
		horarioValido(String.valueOf(materia.getHoras()));
		validarCarreras(materia.getCarreras());
		estadoValido(materia.getEstado());
	}

	public static void validarPeriodo(Periodo periodo) throws AnhoInvalidoException, NumeroInvalidoException, PeriodoInvalidoException {
		anoValido(periodo.getAnho());
		numeroValido(periodo.getNumero());
		tipoPeriodoValido(periodo.getDuracion());
		
	}

	private static void tipoPeriodoValido(TipoPeriodo tipoṔeriodo) throws PeriodoInvalidoException {
		if(!TipoPeriodo.contains(tipoṔeriodo.name())){
			throw new PeriodoInvalidoException();
		}
	}

	private static void numeroValido(Integer numero) throws NumeroInvalidoException {
		if(!esNumeroValido(numero)) {
			throw new NumeroInvalidoException();
		}
	}
	
	private static boolean esNumeroValido(Integer numero) {
		return numero != null || numero instanceof Integer && numero > 0;
	}

	private static void anoValido(Integer anho) throws AnhoInvalidoException {
		if(!esNumeroValido(anho)) {
			throw new AnhoInvalidoException();
		}
	}

	public static void validarComision(Comision comision) throws PeriodoInvalidoException, MateriaNoExisteException, NombreInvalidoException, CupoInvalidoException, ComisionSinHorariosException {
		conMateria(comision.getMateria());
		conPeriodo(comision.getPeriodo());
	    nombreValido(comision.getNombre());
	    cupoValido(comision.getCupo());
	    horariosValidos(comision.getHorarios());
	}

	private static void conPeriodo(Periodo periodo) throws PeriodoInvalidoException {
		if(periodo == null) {
			throw new PeriodoInvalidoException();
		}
	}

	private static void conMateria(Materia materia) throws MateriaNoExisteException {
		if(materia == null) {
			throw new MateriaNoExisteException();
		}
	}

	private static void horariosValidos(List<Horario> horarios) throws ComisionSinHorariosException {
		if(horarios.isEmpty()) {
			throw new ComisionSinHorariosException();
		}
	}

	private static void cupoValido(Integer cupo) throws CupoInvalidoException {
		if(!esNumeroValido(cupo)) {
			throw new CupoInvalidoException();
		}
	}

	public static void validarUsuario(Usuario usuario) throws EmailInvalidoException, NombreInvalidoException, ApellidoInvalidoException, DniInvalidoException {
	   validarEmail(usuario.getEmail());
	   nombreValido(usuario.getNombre());
	   apellidoValido(usuario.getApellido());
	   dniValido(usuario.getDni());
	}

	private static void dniValido(String dni) throws DniInvalidoException {
		if(stringVacio(dni)) {
			throw new DniInvalidoException();
		}
		
	}

	public static void validarPassword(String password) throws PasswordInvalidoException {
		if(stringVacio(password)) {
			throw new PasswordInvalidoException();
		}	
	}

	public static void validarEmail(String email) throws EmailInvalidoException {
		if(!esEmailValido(email)) {
			throw new EmailInvalidoException();
		}
	}

	public static void validarPerfil(String perfil) throws PerfilInvalidoException {
		if(!TipoPerfil.contains(perfil)){
			throw new PerfilInvalidoException();
		}
	}

	public static void validarPerfiles(List<String> perfiles) throws PerfilInvalidoException {
		for(String perfil : perfiles) {
			validarPerfil(perfil);
		}
	}

	public static void validarEquivalencia(Materia materiaOrigen, Materia materiaDestino) throws MateriasEquivalentesException {
		if(materiaOrigen.equals(materiaDestino)) {
			throw new MateriasEquivalentesException();
		}
		
	}

	public static void validarIncidencia(Incidencia incidencia) throws EmailInvalidoException {
		validarEmail(incidencia.getEmailDelReportante());
		
	}
}