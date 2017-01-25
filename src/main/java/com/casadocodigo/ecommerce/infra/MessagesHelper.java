/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casadocodigo.ecommerce.infra;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author vyniciuspontes
 */
public class MessagesHelper {
    
    @Inject
    private FacesContext facesContext;
    
    public void addFlash(FacesMessage facesMessage){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        facesContext.addMessage(null, 
            facesMessage);
    }
}
