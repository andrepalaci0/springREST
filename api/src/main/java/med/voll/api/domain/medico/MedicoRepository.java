package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable page);
    @Query("select m from Medico m\n" +
            "where\n" +
            "m.ativo = true\n" +
            "and\n" +
            "m.especialidade = :especialidade\n" +
            "and\n" +
            "m.id not in(\n" +
            "    select c.medico.id from Consulta c\n" +
            "    where\n" +
            "    c.data = :data\n" +
            ")\n" +
            "order by rand()\n" +
            "limit 1\n")
    Medico randomMedico(Especialidade especialidade, LocalDateTime data);

    @Query("""
    select m.ativo
    from Medico m 
    where
    m.id = :id
""")
    boolean findAtivoById(Long id);
}
