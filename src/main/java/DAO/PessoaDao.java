package DAO;

import Model.Endereco;
import Model.Pessoa;
import Util.AliasToBeanNestedResultTransformer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PessoaDao {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("PostgreSQL");

    DaoGenerico<Pessoa> daoGenerico = new DaoGenerico<Pessoa>();

    ContatoDao daoContato = new ContatoDao();

    public void inserirPessoa(Pessoa pessoa) {
        daoGenerico.create(pessoa);
    }

    public Pessoa buscaPessoa(int id) {

        EntityManager em = factory.createEntityManager();
        Session session = em.unwrap(Session.class);

        Criteria criteria = session.createCriteria(Pessoa.class, "pessoa");
        criteria.createAlias("pessoa.endereco", "endereco");
        criteria.add(Restrictions.eq("id", id));

        ProjectionList projectionList = Projections.projectionList();

        //Pessoa
        projectionList.add(Projections.property("pessoa.id").as("id"));
        projectionList.add(Projections.property("pessoa.nome").as("nome"));
        projectionList.add(Projections.property("pessoa.email").as("email"));
        projectionList.add(Projections.property("pessoa.cpf").as("cpf"));
        projectionList.add(Projections.property("pessoa.escolaridade").as("escolaridade"));

        //Endereco
        projectionList.add(Projections.property("endereco.id").as("endereco.id"));
        projectionList.add(Projections.property("endereco.logradouro").as("endereco.logradouro"));
        projectionList.add(Projections.property("endereco.cidade").as("endereco.cidade"));
        projectionList.add(Projections.property("endereco.estado").as("endereco.estado"));
        projectionList.add(Projections.property("endereco.complemento").as("endereco.complemento"));

        criteria.setProjection(projectionList);
        criteria.setResultTransformer(new AliasToBeanNestedResultTransformer(Pessoa.class));

        Pessoa pessoa = (Pessoa) criteria.uniqueResult();

        pessoa.setContatosAlternativos(daoContato.listarContatos(id));

        return pessoa;

    }

    public void removePessoa(Pessoa pessoa) {
        daoGenerico.delete(pessoa);
    }

    public void editaPessoa(Pessoa pessoa) {
        daoGenerico.update(pessoa);
    }

    public List<HashMap<String, Object>> listaCriteriaPessoa() {

        EntityManager em = factory.createEntityManager();
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Pessoa.class, "pessoa");
        criteria.createAlias("pessoa.endereco", "endereco");
        criteria.createAlias("pessoa.contatosAlternativos", "contatos");

        ProjectionList projectionList = Projections.projectionList();

        //Pessoa
        projectionList.add(Projections.property("pessoa.id").as("idPessoa"));
        projectionList.add(Projections.property("pessoa.nome").as("nomePessoa"));
        projectionList.add(Projections.property("pessoa.email").as("emailPessoa"));
        projectionList.add(Projections.property("pessoa.cpf").as("cpfPessoa"));
        projectionList.add(Projections.property("pessoa.escolaridade").as("escolaridadePessoa"));

        //Endereco
        projectionList.add(Projections.property("endereco.id").as("idEndereco"));
        projectionList.add(Projections.property("endereco.logradouro").as("logradouro"));
        projectionList.add(Projections.property("endereco.cidade").as("cidade"));
        projectionList.add(Projections.property("endereco.estado").as("estado"));
        projectionList.add(Projections.property("endereco.complemento").as("complemento"));

        criteria.setProjection(projectionList);
        criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);

        List<HashMap<String, Object>> listaPessoas = criteria.list();
        em.close();

        return listaPessoas;
    }

//    public List<HashMap<String, Object>> listaCriteriaContatos(int id) {
//        EntityManager em = factory.createEntityManager();
//        Session session = em.unwrap(Session.class);
//        Criteria criteria = session.createCriteria(Contato.class, "contato");
//        criteria.add(Restrictions.eq("contato.pessoa.id", id));
//
//        ProjectionList projectionList = Projections.projectionList();
//
//        //Contato
//        projectionList.add(Projections.property("contato.id").as("idContato"));
//        projectionList.add(Projections.property("contato.nome").as("nomeContato"));
//        projectionList.add(Projections.property("contato.email").as("emailContato"));
//        projectionList.add(Projections.property("contato.telefone").as("telefoneContato"));
//
//        criteria.setProjection(projectionList);
//        criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
//
//        List<HashMap<String, Object>> listaContatos = criteria.list();
//        em.close();
//
//        return listaContatos;
//    }

    public List<Pessoa> listarPessoas() {
        List<Pessoa> pessoas = new ArrayList<>();

        for (HashMap<String, Object> map : listaCriteriaPessoa()) {
            if (!elementoDuplicado(pessoas, (Integer) map.get("idPessoa"))) {
                Endereco endereco = new Endereco();
                endereco.setId((Integer) map.get("idEndereco"));
                endereco.setLogradouro((String) map.get("logradouro"));
                endereco.setCidade((String) map.get("cidade"));
                endereco.setEstado((String) map.get("estado"));
                endereco.setComplemento((String) map.get("complemento"));

                Pessoa pessoa = new Pessoa();
                pessoa.setId((Integer) map.get("idPessoa"));
                pessoa.setNome((String) map.get("nomePessoa"));
                pessoa.setEmail((String) map.get("emailPessoa"));
                pessoa.setCpf((String) map.get("cpfPessoa"));
                pessoa.setEscolaridade((String) map.get("escolaridadePessoa"));
                pessoa.setEndereco(endereco);
                pessoa.setContatosAlternativos(daoContato.listarContatos(pessoa.getId()));
                pessoas.add(pessoa);
            }
        }
        return pessoas;
    }

//    public List<Contato> listarContatos(int id) {
//        List<Contato> contatos = new ArrayList<>();
//
//        for (HashMap<String, Object> map : listaCriteriaContatos(id)) {
//            Contato contato = new Contato();
//            contato.setId((Integer) map.get("idContato"));
//            contato.setNome((String) map.get("nomeContato"));
//            contato.setEmail((String) map.get("emailContato"));
//            contato.setTelefone((String) map.get("telefoneContato"));
//            contatos.add(contato);
//        }
//
//        return contatos;
//    }

    public boolean elementoDuplicado(List<Pessoa> pessoas, int id) {

        for (int i = 0; i < pessoas.size(); i++) {

            if (pessoas.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

}
