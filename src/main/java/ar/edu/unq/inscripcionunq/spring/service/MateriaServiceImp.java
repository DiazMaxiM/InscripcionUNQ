package ar.edu.unq.inscripcionunq.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.dao.CarreraDao;
import ar.edu.unq.inscripcionunq.spring.dao.MateriaDao;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteMateriaConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.HorarioInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;

@Service
@Transactional
public class MateriaServiceImp extends GenericServiceImp<Materia> implements MateriaService {

	@Autowired
	CarreraDao carreraDaoImp;

	@Autowired
	MateriaDao materiaDaoImp;

	@Override
	@Transactional
	public List<Materia> getMateriasParaCarreras(List<Carrera> carreras) {
		return ((MateriaDao) genericDao).getMateriasParaCarreras(carreras);
	}

	@Override
	public List<MateriaSistemaJson> getMateriasJson() {
		List<Materia> materias = materiaDaoImp.getMaterias();
		return materias.stream().map(m -> this.crearMateriaJson(m)).collect(Collectors.toList());
	}

	private MateriaSistemaJson crearMateriaJson(Materia materia) {
		List<CarreraJson> carrerasJson = new ArrayList<>();
		materia.getCarreras().forEach((carrera) -> {
			CarreraJson carreraJson = new CarreraJson(carrera);
			carrerasJson.add(carreraJson);
		});

		return new MateriaSistemaJson(materia.getId(), materia.getCodigo(), materia.getNombre(), materia.getHoras(),
				materia.getCreditos(), carrerasJson, TipoEstado.esEstadoHabiltado(materia.getEstado()));

	}

	@Override
	public void actualizarMateria(MateriaSistemaJson materiaJson) throws FormatoNumeroIdException,
			MateriaNoExisteException, ExisteMateriaConElMismoCodigoException, CodigoInvalidoException,
			NombreInvalidoException, EstadoInvalidoException, DescripcionInvalidaException, HorarioInvalidoException {
		Materia materiaNueva = this.mapearMateriaDesdeJson(materiaJson);
		Materia materiaOriginal = null;

		try {
			materiaOriginal = this.get(materiaJson.id);
		} catch (NumberFormatException e) {
			throw new FormatoNumeroIdException();
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new MateriaNoExisteException();
		}

		if (materiaNueva != null) {
			this.actualizarInformacionDeLaMateria(materiaOriginal, materiaNueva);
		}
	}

	private void actualizarInformacionDeLaMateria(Materia materiaOriginal, Materia materiaNueva)
			throws ExisteMateriaConElMismoCodigoException, CodigoInvalidoException, NombreInvalidoException,
			EstadoInvalidoException, DescripcionInvalidaException, HorarioInvalidoException {
		if (!materiaOriginal.getCodigo().equals(materiaNueva.getCodigo())) {
			validarSiExisteMateriaConElMismoCodigo(materiaNueva.getCodigo());
		}
		materiaOriginal.actualizarMateria(materiaNueva);
		this.save(materiaOriginal);
	}

	private Materia mapearMateriaDesdeJson(MateriaSistemaJson materiaJson) {
		TipoEstado estado = materiaJson.estado ? TipoEstado.ENABLED : TipoEstado.DISABLED;
		List<Carrera> carreras = new ArrayList<>();
		materiaJson.carreras.forEach((carreraJson) -> {
			TipoEstado estadoCarrera = carreraJson.habilitada ? TipoEstado.ENABLED : TipoEstado.DISABLED;
			Carrera carrera = new Carrera(carreraJson.codigo, carreraJson.descripcion);
			carrera.setEstado(estadoCarrera);
			Carrera carreraOriginal = carreraDaoImp.get(carreraJson.id);
			carreraOriginal.actualizarInformacion(carrera);
			carreras.add(carreraOriginal);
		});
		return new Materia(materiaJson.codigo, materiaJson.nombre, materiaJson.horas, materiaJson.creditos, carreras,
				estado);

	}

	@Override
	public void agregarNuevaMateria(MateriaSistemaJson materiaJson)
			throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException,
			ExisteMateriaConElMismoCodigoException, FormatoNumeroIdException, MateriaNoExisteException {
		Materia nuevaMateria = this.mapearMateriaDesdeJson(materiaJson);
		this.validarSiExisteMateriaConElMismoCodigo(nuevaMateria.getCodigo());
		this.save(nuevaMateria);
	}

	@Override
	public void validarSiExisteMateriaConElMismoCodigo(String codigo) throws ExisteMateriaConElMismoCodigoException {
		Materia materia = materiaDaoImp.encontrarMateriaConElMismoCodigo(codigo);
		if (materia != null) {
			throw new ExisteMateriaConElMismoCodigoException();
		}
	}

	public Materia getMateriaPorCodigo(String codigo) {
		return materiaDaoImp.encontrarMateriaConElMismoCodigo(codigo);
	}

	@Override
	@Transactional
	public List<MateriaSistemaJson> getMateriasParaCarrera(String idCarrera) {
		Long id = new Long(idCarrera);
		List<Materia> materias = ((MateriaDao) genericDao).getMateriasParaCarrera(id);

		return materias.stream().map(m -> this.crearMateriaJson(m)).collect(Collectors.toList());
	}
}