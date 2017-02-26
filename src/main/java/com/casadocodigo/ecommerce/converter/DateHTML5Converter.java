/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casadocodigo.ecommerce.converter;

import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author vyniciuspontes
 */
@FacesConverter(forClass=Date.class)
public class DateHTML5Converter implements Converter{

    private static final DateTimeConverter originalConverter = new DateTimeConverter();
    
    static {
        originalConverter.setPattern("yyyy-MM-dd");
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        Date date = (Date) originalConverter.getAsObject(context,
                component, value);
        if (date == null) {
            return null;
        }
        
        return date;
    
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    
        if (value == null)
            return null;
        
        Date date = (Date) value;
        return	originalConverter.getAsString(context, component, date.toString());
    }
    
}
