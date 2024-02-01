package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaMedicoAtivo implements ValidadorAgendamentoConsulta
{

    @Autowired
    private MedicoRepository repository;

    public void valida(DadosAgendamentoConsulta dados){
        if(dados.idMedico() == null) return;
        if(!repository.findAtivoById(dados.idMedico()))
            throw new ValidationException("Consulta não pode ser agendada com médico inativo");
    }
}
