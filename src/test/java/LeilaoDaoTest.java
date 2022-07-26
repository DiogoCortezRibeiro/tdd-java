import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LeilaoDaoTest {

    private LeilaoDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEnitytyManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    public void deveriaCadastrarUmLeilao() {
        Assert.assertNotNull(dao.buscarPorId(dao.salvar(criarLeilao()).getId()));
    }

    @Test
    public void deveriaAtualizarUmLeilao() {
        Leilao leilao = criarLeilao();
        leilao = dao.salvar(leilao);
        leilao.setNome("MOCHILA ATUALIZADA");
        leilao = dao.salvar(leilao);
        Assert.assertEquals("MOCHILA ATUALIZADA", dao.buscarPorId(leilao.getId()).getNome());
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "123");
        em.persist(usuario);
        return usuario;
    }

    private Leilao criarLeilao() {
        return new Leilao("MOCHILA", new BigDecimal("70"), LocalDate.now(), criarUsuario());
    }

}
