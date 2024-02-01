package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaConsultaUnicaDia implements ValidadorAgendamentoConsulta{
    @Autowired
    private ConsultaRepository repository;


    public void valida(DadosAgendamentoConsulta dados){
        if(repository.existsByPacienteIdAndDataBetween(dados.idPaciente(),
                dados.data().withHour(7),
                dados.data().withHour(18)))
            throw new ValidationException("Paciente ja possui uma consulta no dia");
    }
}
