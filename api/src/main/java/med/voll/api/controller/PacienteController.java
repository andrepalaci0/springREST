package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    public ResponseEntity <DadosListagemPaciente> cadastro(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder)
    {
        var paciente = new Paciente(dados);
        repository.save(paciente);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemPaciente(paciente));
    }


    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> list (@PageableDefault(size=10, sort= {"nome"})Pageable page)
    {
        var aux = repository.findAllByAtivoTrue(page).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(aux);
    }

    @GetMapping("/{id}")
    public ResponseEntity <DadosListagemPaciente> detalhar(@PathVariable Long id)
    {
        Optional<Paciente> optPaciente = repository.findById(id);
        if(optPaciente.isEmpty()) return ResponseEntity.notFound().build();

        Paciente paciente = optPaciente.get();
        DadosListagemPaciente detalhamentoPaciente = new DadosListagemPaciente(paciente);
        return ResponseEntity.ok(detalhamentoPaciente);
    }


    @PutMapping
    @Transactional
    public ResponseEntity <DadosListagemPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados)
    {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInfos(dados);
        return  ResponseEntity.ok(new DadosListagemPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }

}
