package sessao_api.demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UsuarioRepository extends JpaRepository<Usuario,Long> {
}
