package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class ValidaHorarioFuncionamento implements ValidadorAgendamentoConsulta{

    public void valida(DadosAgendamentoConsulta dados)
    {
        if(dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                dados.data().getHour() < 7 ||
                dados.data().getHour() > 18
        ) throw new ValidationException("Data invalida");
    }
}
