package DAO;

import Model.EntidadeBase;
import Util.ConnectionFactory;

import javax.persistence.EntityManager;

public class DaoGenerico<Obj extends EntidadeBase>{

    private static EntityManager manager = ConnectionFactory.getEntityManager();

    public void create(Obj obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

    public void delete(Obj obj){
        manager.getTransaction().begin();
        obj = manager.merge(obj);
        manager.remove(obj);
        manager.getTransaction().commit();
    }

    public void update(Obj obj){
        manager.getTransaction().begin();
        obj = manager.merge(obj);
        manager.persist(obj);
        manager.getTransaction().commit();
    }

}
