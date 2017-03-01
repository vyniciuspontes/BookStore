/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casadocodigo.ecommerce.managedbeans;

import com.casadocodigo.ecommerce.dao.BookDAO;
import com.casadocodigo.ecommerce.models.Book;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author vyniciuspontes
 */
@Model
public class HomeBean {

    @Inject
    private BookDAO bookDao;

    public List<Book> lastReleases() {
        return bookDao.lastReleases();
    }

    public List<Book> olderBooks() {
        return bookDao.olderBooks();
    }
}
