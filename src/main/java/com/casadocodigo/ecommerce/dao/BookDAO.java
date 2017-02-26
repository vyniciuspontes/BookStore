package com.casadocodigo.ecommerce.dao;

import com.casadocodigo.ecommerce.models.Book;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author vyniciuspontes
 */
@RequestScoped
public class BookDAO {
    
    @PersistenceContext(unitName = "ecommercePU")
    private EntityManager entityManager;
    
    public void save(Book book){
        entityManager.persist(book);
    }
    
    public List<Book> list(){
        
        return entityManager.createQuery("SELECT distinct(b) FROM Book b join fetch b.authorList").getResultList();
    }
}
