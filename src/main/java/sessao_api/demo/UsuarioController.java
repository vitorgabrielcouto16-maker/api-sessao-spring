package sessao_api.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping ("/usuario")
public class UsuarioController {
    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }
    @PostMapping

    public Usuario cadastrar (@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }
    @PostMapping("/login")

    public String login (@RequestBody Usuario usuario, HttpSession sessao){
        Optional<Usuario> resultado = repository.findByEmail(usuario.getEmail());

        if (resultado.isEmpty()){
            return "Email não encontrado";
        }
        Usuario salvo = resultado.get();

        if (!salvo.getSenha().equals(usuario.getSenha())){
            return "Senha incorreta";
        }
        sessao.setAttribute("QuemEntrou", salvo.getNome());
        sessao.setMaxInactiveInterval(60);
        return "Login realizado com sucesso";
    }
    @GetMapping("/acesso-restrito")
    public String acessoRestrito (HttpSession sessao){
        Object login = sessao.getAttribute("QuemEntrou");

        if (login == null){
            return "Usuario sem login";
        }
        return "Acesso autorizado";
    }

}
