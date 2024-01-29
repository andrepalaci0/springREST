package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController{
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastro(@RequestBody @Valid DadosCadastroMedico dados)
    {
        repository.save(new Medico(dados));
    }


    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size=10, sort= {"nome"}) Pageable page){
        return repository.findAllByAtivoTrue(page).map(DadosListagemMedico::new);
        //na request, para definir o n° de atributos listados, adiciona=se /medico?size=n°
        //por padrão o spring retorna 20
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInfos(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id)
    {
        var medico = repository.getReferenceById(id);
        medico.excluir();

    }

}
