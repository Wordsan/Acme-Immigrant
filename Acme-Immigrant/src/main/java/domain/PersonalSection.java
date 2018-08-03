package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalSection extends DomainEntity {

	private String fullNames;
	private String birthPlace;
	private Date birthDate;
	private String picture;

	@NotBlank
	public String getFullNames() {
		return this.fullNames;
	}

	public void setFullNames(final String fullNames) {
		this.fullNames = fullNames;
	}

	@NotBlank
	public String getBirthPlace() {
		return this.birthPlace;
	}

	public void setBirthPlace(final String birthPlace) {
		this.birthPlace = birthPlace;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	@Past
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	@URL
	@NotBlank
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

}
