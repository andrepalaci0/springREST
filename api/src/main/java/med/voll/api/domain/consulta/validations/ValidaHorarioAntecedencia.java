package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class ValidaHorarioAntecedencia implements ValidadorAgendamentoConsulta{
    public void valida(DadosAgendamentoConsulta dados)
    {
        if(Duration.between(LocalDateTime.now(), dados.data()).toMinutes() < 30)
            throw new ValidationException("Consulta deve ser agendada com pelo menos 30min de antecedencia");
    }
}

