package maboutique.converter;


import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoCategorie;
import maboutique.data.Categorie;

@Named
@RequestScoped
public class ConverterCategorie implements Converter<Categorie> {

	// Champs
	@Inject
	private DaoCategorie daoCategorie;

	// Actions
	@Override
	public Categorie getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null || id.isEmpty()) {
			return null;
		}

		try {
			return daoCategorie.retrouver(Integer.valueOf(id));
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(String.format("Identifiant de catégorie invalide : %s", id)),
					e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Categorie item) {
		if (item == null) {
			return "";
		}
		return String.valueOf(item.getId());

	}

}
