package com.casadocodigo.ecommerce.dao;

import com.casadocodigo.ecommerce.models.Book;
import java.util.List;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author vyniciuspontes
 */
@Stateful
public class BookDAO {
    
    @PersistenceContext(unitName = "ecommercePU", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    
    public void save(Book book){
        entityManager.persist(book);
    }
    
    public List<Book> list(){
        
        return entityManager.createQuery("SELECT distinct(b) FROM Book b join fetch b.authorList").getResultList();
    }

    public List<Book> lastReleases() {
        return entityManager.createQuery("select b from Book b where b.releaseDate >= now()"
                + " order by b.id desc", Book.class).getResultList();
    }

    public List<Book> olderBooks() {
        return entityManager.
                createQuery("select b from Book b", Book.class).setMaxResults(20).getResultList();
    }

    public Book findById(Integer id) {
        return entityManager.find(Book.class, id);
    }
}
