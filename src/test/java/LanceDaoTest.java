import br.com.alura.leilao.dao.LanceDao;
import br.com.alura.leilao.model.Lance;
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
import java.util.ArrayList;
import java.util.List;

public class LanceDaoTest {
    private LanceDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEnitytyManager();
        this.dao = new LanceDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    public void deveriaRetornar70ComoMaior() {
        Leilao leilao = criarLeilao();
        Usuario user = criarUsuario();
        List<Lance> lance = new ArrayList<>();
        lance.add(new Lance(user, new BigDecimal("70")));
        leilao.setLances(lance);

        Assert.assertEquals(70, dao.buscarMaiorLanceDoLeilao(leilao));
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
