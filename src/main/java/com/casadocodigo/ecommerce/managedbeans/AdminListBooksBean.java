/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casadocodigo.ecommerce.managedbeans;

import com.casadocodigo.ecommerce.dao.BookDAO;
import com.casadocodigo.ecommerce.models.Book;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author vyniciuspontes
 */

@Model
public class AdminListBooksBean {
    
    @Inject
    private BookDAO bookDAO;
    private List<Book> books = new ArrayList<>();

    @PostConstruct
    private void loadObjects() {
        this.books = bookDAO.list();
        /*HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null,
                new FacesMessage(request.getParameter("sucesso")));*/
    }

    public List<Book> getBooks() {
        return books;
    }
}
