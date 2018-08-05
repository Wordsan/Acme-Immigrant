
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	private String	systemName;
	private String	phonePattern;


	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@Pattern(regexp = "^+[0-9]{1,3}([0-9]{0,3})[0-9]{4}$")
	public String getPhonePattern() {
		return this.phonePattern;
	}

	public void setPhonePattern(final String phonePattern) {
		this.phonePattern = phonePattern;
	}
}
