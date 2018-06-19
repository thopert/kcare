package at.qe.sepm.skeleton.ui.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.services.UserService;

@Component
@Scope("application")
public class CustomValidators {
	@Autowired
	UserService userService;

	public void validateUserName(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
		String username = (String) value;
		if (!this.userService.isUsernameFree(username)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Username bereits in Verwendung!");
			throw new ValidatorException(msg);
		}
	}

	public void validateTime(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
		String time = (String) value;
		
		if (time.length() != 5) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Zeitformat inkorrekt: HH:mm!");
			throw new ValidatorException(msg);
		}
		
		String nums[] = time.split(":");
		
		if (nums.length != 2) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Zeitformat inkorrekt: HH:mm!");
			throw new ValidatorException(msg);
		}

		Long parsing[] = new Long[2];
		for (int i = 0; i < 2; i++) {
			try {
				parsing[i] = Long.parseLong(nums[i]);
			} catch (NumberFormatException e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Zeitdaten inkorrekt!");
				throw new ValidatorException(msg);
			}
		}

		if (parsing[0] < 0 || parsing[0] > 23) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Zeitdaten inkorrekt!");
			throw new ValidatorException(msg);
		}
		if (parsing[1] < 0 || parsing[1] > 59) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Zeitdaten inkorrekt!");
			throw new ValidatorException(msg);
		}

	}

}
