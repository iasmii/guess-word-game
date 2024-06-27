package server.repository;

import comun.domain.GameTable;
import comun.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.Optional;

public class GameTableHibernateRepository implements IGameTableRepository{
    @Override
    public Optional<GameTable> findOne(Long aLong) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            return Optional.ofNullable(session.createQuery("from GameTable where id = :id", GameTable.class)
                    .setParameter("id", aLong)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Iterable<GameTable> getAll() {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            return session.createQuery("from GameTable", GameTable.class).getResultList();
        }
    }

    @Override
    public void save(GameTable entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
    }

    @Override
    public void delete(Long aLong) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            GameTable gameTable = session.createQuery("from GameTable where id = :id", GameTable.class)
                    .setParameter("id", aLong)
                    .getSingleResultOrNull();
            if(gameTable != null){
                session.remove(gameTable);
                session.flush();
            }
        });
    }

    @Override
    public void update(GameTable entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if(session.find(GameTable.class, entity.getId()) != null) {
                session.merge(entity);
                session.flush();
            }
        });
    }
}
