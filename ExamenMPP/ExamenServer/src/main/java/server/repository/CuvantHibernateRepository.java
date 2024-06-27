package server.repository;

import comun.domain.Cuvant;
import comun.domain.GameTable;
import comun.domain.User;
import comun.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.Optional;

public class CuvantHibernateRepository implements ICuvantRepository{
    public CuvantHibernateRepository() {
//        logger.info("Initializing UserHibernateRepository");
    }


    @Override
    public Optional<Cuvant> findOne(Long aLong) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            return Optional.ofNullable(session.createQuery("from Cuvant where id = :id", Cuvant.class)
                    .setParameter("id", aLong)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Iterable<Cuvant> getAll() {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            return session.createQuery("from Cuvant", Cuvant.class).getResultList();
        }
    }

    @Override
    public void delete(Long aLong) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Cuvant user = session.createQuery("from Cuvant where id = :id", Cuvant.class)
                    .setParameter("id", aLong)
                    .getSingleResultOrNull();
            if(user != null){
                session.remove(user);
                session.flush();
            }
        });
    }

    @Override
    public void save(Cuvant entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
    }

    @Override
    public void update(Cuvant entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if(session.find(Cuvant.class, entity.getId()) != null){
                session.merge(entity);
                session.flush();
            }
        });
    }
}
