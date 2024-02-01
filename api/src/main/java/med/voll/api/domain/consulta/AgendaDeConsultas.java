package med.voll.api.domain.consulta;

import jakarta.validation.ValidationException;
import lombok.SneakyThrows;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validations.ValidadorAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadores;

    @SneakyThrows
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados)
    {
        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidationException("Paciente não existe");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidationException("Medico não existe");
        }

        validadores.forEach(v -> v.valida(dados));

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = escolheMedico(dados);
        if(medico == null) throw new ValidationException("Não ha medicos disponiveis na data");
        var consulta = new Consulta(null, medico, paciente, dados.data());
         consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);

    }

    private Medico escolheMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null ) return medicoRepository.getReferenceById(dados.idMedico());
        if(dados.especialidade() == null) throw new ValidationException("Especialidade não foi enviada");
        return medicoRepository.randomMedico(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if(dados.data().isBefore(LocalDateTime.now().plusHours(24))){
            throw new ValidationException("Consulta só pode ser cancelada até uma hora antes");
        }
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancela();
    }

    public Page<DadosListagemConsulta> consultasAtivas(Pageable page){
        return consultaRepository.findAllByCanceladaFalse(page).map(DadosListagemConsulta::new);
    }
}
