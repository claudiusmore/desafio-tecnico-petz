package br.com.claudio.petz.resouces;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfilter.EnableJsonFilter;
import com.jfilter.filter.FieldFilterSetting;

import br.com.claudio.petz.model.Cliente;
import br.com.claudio.petz.services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/petz/clientes")
@Api(value="API REST Clientes")
@CrossOrigin(origins="*")
@ComponentScan({"com.jfilter.components"})
@EnableJsonFilter
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Retorna um Cliente")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method=RequestMethod.POST, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Insere um Cliente")
	@FieldFilterSetting(fields = {"pets"})
	public ResponseEntity<@Valid Cliente> insert(@Valid @RequestBody Cliente obj) {
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Altera um Cliente")
	public ResponseEntity<Void> update(@Valid @RequestBody Cliente obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Exclui um Cliente")
	@FieldFilterSetting(fields = {"pets"})
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method=RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	@ApiOperation(value="Retorna uma lista de Clientes")
	public ResponseEntity<List<Cliente>> findAll() {
		List<Cliente> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
}
