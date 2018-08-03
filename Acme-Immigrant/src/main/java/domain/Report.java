package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	private Immigrant immigrant;
	private Investigator investigator;
	private String text;
	private String pictures;
	private Officer officer;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Immigrant getImmigrant() {
		return this.immigrant;
	}

	public void setImmigrant(final Immigrant immigrant) {
		this.immigrant = immigrant;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Officer getOfficer() {
		return this.officer;
	}

	public void setOfficer(final Officer officer) {
		this.officer = officer;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Investigator getInvestigator() {
		return this.investigator;
	}

	public void setInvestigator(final Investigator investigator) {
		this.investigator = investigator;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

}
