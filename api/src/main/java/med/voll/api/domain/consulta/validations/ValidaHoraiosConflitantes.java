package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaHoraiosConflitantes implements ValidadorAgendamentoConsulta{
    @Autowired
    private ConsultaRepository repository;

    public void valida(DadosAgendamentoConsulta dados){
        if(repository.existsByMedicoIdAndData(dados.idMedico(), dados.data()))
            throw new ValidationException("Médico possui uma consulta nessa hora");
    }
}
