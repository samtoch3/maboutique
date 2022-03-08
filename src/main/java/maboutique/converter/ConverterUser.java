package maboutique.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoUser;
import maboutique.data.User;

@Named
@RequestScoped
public class ConverterUser implements Converter<User> {

	// Champs
		@Inject
		private DaoUser daoUser;

		// Actions
		@Override
		public User getAsObject(FacesContext context, UIComponent component, String id) {
			if (id == null || id.isEmpty()) {
				return null;
			}

			try {
				return daoUser.retrouver(Integer.valueOf(id));
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(String.format("Identifiant de l'utilisateur invalide : %s", id)),
						e);
			}
		}

		@Override
		public String getAsString(FacesContext context, UIComponent component, User item) {
			if (item == null) {
				return "";
			}
			return String.valueOf(item.getId());

		}

}
