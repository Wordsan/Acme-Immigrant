package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ContactSection extends DomainEntity {

	private String emailAddress;
	private String phoneNumber;
	private String pagerNumber;

	@NotBlank
	@Email
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPagerNumber() {
		return this.pagerNumber;
	}

	public void setPagerNumber(final String pagerNumber) {
		this.pagerNumber = pagerNumber;
	}
}
