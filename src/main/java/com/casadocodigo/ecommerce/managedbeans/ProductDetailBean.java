/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casadocodigo.ecommerce.managedbeans;

import com.casadocodigo.ecommerce.dao.BookDAO;
import com.casadocodigo.ecommerce.models.Book;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author vyniciuspontes
 */
@Model
public class ProductDetailBean {

    @Inject
    private BookDAO bookDAO;
    private Book book;
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
    public void loadBook() {
        this.book = bookDAO.findById(id);
    }

    public Book getBook() {
        return book;
    }
}
