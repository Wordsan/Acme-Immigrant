package forms;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Esta clase tiene como objetivo la creación de nuevos Immigrants, Officers o
 * Investigators o la edición de los datos personales de los usuarios ya
 * registrados
 * 
 */
public class FormActor {
	private int id;
	private String name;
	private String surname;
	private String email;
	private String address;
	private String phoneNumber;
	private String username;
	private String password;
	private String repassword;
	// Se comprobará que sea true a la hora de registrar nuevos Immigrants
	private boolean terms;

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

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

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Size(min = 5, max = 32)
	public String getRepassword() {
		return this.repassword;
	}

	public void setRepassword(final String repassword) {
		this.repassword = repassword;
	}

	public boolean isTerms() {
		return this.terms;
	}

	public void setTerms(final boolean terms) {
		this.terms = terms;
	}

}
