/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casadocodigo.ecommerce.managedbeans;

import com.casadocodigo.ecommerce.dao.AuthorDAO;
import com.casadocodigo.ecommerce.dao.BookDAO;
import com.casadocodigo.ecommerce.infra.AmazonFileSaver;
import com.casadocodigo.ecommerce.infra.FileSaver;
import com.casadocodigo.ecommerce.infra.MessagesHelper;
import com.casadocodigo.ecommerce.models.Author;
import com.casadocodigo.ecommerce.models.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

/**
 *
 * @author vyniciuspontes
 */
@Model
public class AdminBooksBean {
    
    private static final Logger LOGGER = Logger.getLogger( AdminBooksBean.class.getName() );
    
    @Inject
    private Book product;
    
    @Inject
    private BookDAO bookDAO;
    
    @Inject
    private AuthorDAO authorDAO;
    
    @Inject
    private MessagesHelper messagesHelper;	
    
    private List<Author> authors = new ArrayList<>();
    
    private Part summary;
    
    @Inject
    private AmazonFileSaver fileSaver;

    public void setSummary(Part summary) {
        this.summary = summary;
    }

    public Part getSummary() {
        return summary;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
    private List<Integer> selectedAuthorsIds = new ArrayList<>();

    @PostConstruct
    public void loadObjects() {
        this.authors = authorDAO.list();
    }
    
    public BookDAO getBookDAO() {
        return bookDAO;
    }

    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Integer> getSelectedAuthorsIds() {
        return selectedAuthorsIds;
    }

    public void setSelectedAuthorsIds(List<Integer> selectedAuthorsIds) {
        this.selectedAuthorsIds = selectedAuthorsIds;
    }
    
    public Book getProduct() {
        return product;
    }
    
    @Transactional(dontRollbackOn = Exception.class)
    public String save() throws IOException {
        //populateBookAuthor();
        String sumaryPath = fileSaver.write("summaries", summary);
        product.setSummaryPath(sumaryPath);
        bookDAO.save(product);
        messagesHelper.addFlash(new FacesMessage("Cadastro realizado com sucesso !"));
        
        clearObjects();
        //return "/lista?faces-redirect=true&sucesso=Livro gravado com sucesso";
        return "/lista?faces-redirect=true";
    }
    
    private void clearObjects() {
        this.product = new Book();
        this.selectedAuthorsIds.clear();
    }
    
    private void populateBookAuthor() {
        
        selectedAuthorsIds.stream().
                map((id) -> authorDAO.findById(id)).
                forEach((a) -> { product.add(a);}
        );
    }
    
    
}
