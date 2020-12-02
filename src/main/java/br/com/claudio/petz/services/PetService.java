package br.com.claudio.petz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.claudio.petz.model.Pet;
import br.com.claudio.petz.repositories.PetRepository;
import br.com.claudio.petz.repositories.exceptions.DataIntegrityException;
import br.com.claudio.petz.repositories.exceptions.ObjectNotFoundException;

@Service
public class PetService {
	
	@Autowired
	private PetRepository pet;
	
	public Pet find(Integer id) {
		Optional<Pet> opt = pet.findById(id);
		return opt.orElseThrow(() -> new ObjectNotFoundException(
				"pet não encontrado! Id: "+id));
	}
	
	public Pet insert(Pet obj) {
		obj.setId(null);
		return pet.save(obj);
	}
	
	public Pet update(Pet obj) {
		Pet newObj = find(obj.getId());
		updateData(newObj, obj);
		return pet.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			pet.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o pet");
		}
	}
	
	public List<Pet> findAll() {
		return pet.findAll();
	}
	
	public Page<Pet> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return pet.findAll(pageRequest);
	}
	
	private void updateData(Pet newObj, Pet obj) {
		newObj.setNome(obj.getNome());
		newObj.setRaca(obj.getRaca());
		newObj.setIdade(obj.getIdade());
	}

}
