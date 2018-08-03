package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	private String address;

	private UserAccount userAccount;

	// ---------------Getters y Setters con ConstraintS------------------

	@NotBlank
	public String getName() {
		return this.name;

	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String emailAddress) {
		this.email = emailAddress;
	}

	// TODO: @Pattern(regexp = "\\d+")
	// TODO: Phone numbers should adhere to "+CC (AC) PN", "+CC PN" or "PN"
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		final String res = "- Name: " + this.getName() + "\n" + "- Surname: "
				+ this.getSurname() + "\n" + "- Email Address: "
				+ this.getEmail() + "\n" + "- Phone Number: "
				+ this.getPhoneNumber() + "\n" + "- Address:"
				+ this.getAddress();
		return res;
	}

	// -----------------------------------------------------

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
