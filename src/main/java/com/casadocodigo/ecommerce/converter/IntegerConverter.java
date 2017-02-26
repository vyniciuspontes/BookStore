/** 
 * 
 */
package com.casadocodigo.ecommerce.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/** Resolves Faces conversion from String to Integer. Avoid the "javax.servlet.ServletException: 
 * java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Integer"
 * @author vyniciuspontes
 */
public class IntegerConverter implements Converter{

    	/**
    *	<p>The	standard	converter	id	for	this	converter.</p>
    */
    public static final	String	CONVERTER_ID	=	
    "javax.faces.Integer";
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return (Object) value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return (String) value;
    }
    
}
