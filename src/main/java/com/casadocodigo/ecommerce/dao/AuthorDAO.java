/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casadocodigo.ecommerce.dao;

import com.casadocodigo.ecommerce.models.Author;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vyniciuspontes
 */
@RequestScoped
public class AuthorDAO {
    
    @PersistenceContext(unitName = "ecommercePU")
    private EntityManager entityManager;
    
    public Author findById(Integer id){
        Query query = entityManager.createNamedQuery("Author.findById",  Author.class);
        query.setParameter("id", id);
        List results = query.getResultList();
        Author foundEntity = null;
        if (!results.isEmpty()) {
            // ignores multiple results
            foundEntity = (Author) results.get(0);
        }
        return foundEntity;
    }
    
    public List<Author> list(){
        return entityManager.createNamedQuery("Author.findAll").getResultList();
    }
}
