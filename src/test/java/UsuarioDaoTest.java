import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEnitytyManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    public void deveriaEncontrarUsuarioCadastrado() {
        assertNotNull(dao.buscarPorUsername(criarUsuario().getNome()));
    }

    @Test
    public void naoDeveriaEncontrarUsuarioNaoCadastrado() {
        criarUsuario();
        Assert.assertThrows(NoResultException.class, () -> dao.buscarPorUsername("beltrano"));
    }

    @Test
    public void deveriaRemoverUmUsuario() {
        Usuario user = criarUsuario();
        dao.deletar(user);
        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("fulano"));
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "123");
        em.persist(usuario);

        return usuario;
    }

}
