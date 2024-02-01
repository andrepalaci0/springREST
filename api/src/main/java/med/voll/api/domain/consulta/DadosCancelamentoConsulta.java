package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.MotivosCancelamento;

import java.time.LocalDateTime;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,

        @NotNull
        MotivosCancelamento motivo
) {
}
