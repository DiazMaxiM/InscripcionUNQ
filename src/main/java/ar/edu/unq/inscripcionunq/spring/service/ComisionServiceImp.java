package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionJson;
import ar.edu.unq.inscripcionunq.spring.dao.ComisionDao;
import ar.edu.unq.inscripcionunq.spring.model.Comision;

@Service
@Transactional

public class ComisionServiceImp extends GenericServiceImp<Comision> implements ComisionService {
	@Autowired
	private ComisionDao comisionDaoImp;

	@Override
	public List<ComisionJson> getComisionParaMateriaEnEncuesta(String idMateria, String idEncuesta) {

		List<Comision> comisiones = comisionDaoImp.getComisionParaMateriaEnEncuesta(new Long(idMateria),
				new Long(idEncuesta));
		return comisiones.stream().map(commission -> new ComisionJson(commission)).collect(Collectors.toList());
	}

	@Override
	public List<Comision> getTodasLasComisionesDeMateriaEnEncuesta(String idEncuesta) {

		return comisionDaoImp.getTodasLasComisionesDeMateriaEnEncuesta(new Long(idEncuesta));
	}

}
