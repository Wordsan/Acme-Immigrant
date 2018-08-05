
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	private String	systemName;
	private String	phonePattern;
	private String	banner;
	private String	welcomeMessageES;
	private String	welcomeMessageEN;


	/*
	 * The customisation includes the name of the system,
	 * its banner, and the welcome message that is shown on the main page.
	 */

	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	//@Pattern(regexp = "^+[0-9]{1,3}([0-9]{0,3})[0-9]{4}$")
	public String getPhonePattern() {
		return this.phonePattern;
	}

	public void setPhonePattern(final String phonePattern) {
		this.phonePattern = phonePattern;
	}

	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public String getWelcomeMessageES() {
		return this.welcomeMessageES;
	}

	public void setWelcomeMessageES(final String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}

	public String getWelcomeMessageEN() {
		return this.welcomeMessageEN;
	}

	public void setWelcomeMessageEN(final String welcomeMessageEN) {
		this.welcomeMessageEN = welcomeMessageEN;
	}
}
