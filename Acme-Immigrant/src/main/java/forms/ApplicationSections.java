package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import domain.CreditCard;

public class ApplicationSections {
	private int id;
	private int visaId;
	private CreditCard creditCard;
	private String fullNames;
	private String birthPlace;
	private Date birthDate;
	private String picture;
	private String nickname;
	private String socialNetwork;
	private String linkProfile;

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVisaId() {
		return this.visaId;
	}

	public void setVisaId(final int visaId) {
		this.visaId = visaId;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

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

	@NotBlank
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	@NotBlank
	public String getSocialNetwork() {
		return this.socialNetwork;
	}

	public void setSocialNetwork(final String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	@URL
	@NotBlank
	public String getLinkProfile() {
		return this.linkProfile;
	}

	public void setLinkProfile(final String linkProfile) {
		this.linkProfile = linkProfile;
	}

}
