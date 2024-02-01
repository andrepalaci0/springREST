package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacienteAtivo implements ValidadorAgendamentoConsulta{
    @Autowired
    private PacienteRepository repository;

    public void valida(DadosAgendamentoConsulta dados){
        if(!repository.findAtivoById(dados.idPaciente()))
            throw new ValidationException("Paciente não existe");
    }
}
