package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Requirement extends DomainEntity {

	private Law law;
	private String title;
	private String description;
	private boolean abrogated;
	private Collection<Visa> visas;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Law getLaw() {
		return this.law;
	}

	public void setLaw(final Law law) {
		this.law = law;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public boolean isAbrogated() {
		return this.abrogated;
	}

	public void setAbrogated(final boolean abrogated) {
		this.abrogated = abrogated;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Visa> getVisas() {
		return this.visas;
	}

	public void setVisas(final Collection<Visa> visas) {
		this.visas = visas;
	}

}
