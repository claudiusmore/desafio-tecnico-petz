package br.com.claudio.petz.resouces;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfilter.filter.FieldFilterSetting;

import br.com.claudio.petz.model.Cliente;
import br.com.claudio.petz.model.Pet;
import br.com.claudio.petz.services.ClienteService;
import br.com.claudio.petz.services.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/petz/pets")
@Api(value="API REST Pets")
@CrossOrigin(origins="*")
public class PetResource {
	
	@Autowired
	private PetService petService;

	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Retorna um Pet")
	@FieldFilterSetting(fields = {"cliente"})
	public ResponseEntity<Pet> find(@PathVariable Integer id) {
		Pet obj = petService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value="/{id_cliente}", method=RequestMethod.POST, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Insere um Pet")
	@FieldFilterSetting(fields = {"cliente"})
	public ResponseEntity<Pet> insert(@PathVariable("id_cliente") Integer id_cliente, @Valid @RequestBody Pet obj) {
		Cliente cli = clienteService.find(id_cliente);
		obj.setCliente(cli);
		obj = petService.insert(obj);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Altera um Pet")
	@FieldFilterSetting(fields = {"cliente"})
	public ResponseEntity<Void> update(@Valid @RequestBody Pet obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = petService.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Exclui um Pet")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		petService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method=RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Retorna uma lista de Pets")
	@FieldFilterSetting(fields = {"cliente"})
	public ResponseEntity<List<Pet>> findAll() {
		List<Pet> list = petService.findAll();
		return ResponseEntity.ok().body(list);
	}
}
