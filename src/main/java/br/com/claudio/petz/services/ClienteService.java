package br.com.claudio.petz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.claudio.petz.model.Cliente;
import br.com.claudio.petz.repositories.ClienteRepository;
import br.com.claudio.petz.repositories.exceptions.DataIntegrityException;
import br.com.claudio.petz.repositories.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository cli;
	
	public Cliente find(Integer id) {
		Optional<Cliente> opt = cli.findById(id);
		return opt.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente Não Encontrado! Id: "+id));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return cli.save(obj);
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return cli.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			cli.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui Pets cadastrados");
		}
	}
	
	public List<Cliente> findAll() {
		return cli.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return cli.findAll(pageRequest);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}


}
