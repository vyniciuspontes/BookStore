/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casadocodigo.ecommerce.infra;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 *
 * @author vyniciuspontes
 */
@ApplicationScoped
public class FacesContextProducer {
    
    @Produces
    @RequestScoped
    public FacesContext get(){
        return FacesContext.getCurrentInstance();
    }
}
