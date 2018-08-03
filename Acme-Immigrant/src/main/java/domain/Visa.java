package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Visa extends DomainEntity {

	private String clase;
	private String description;
	private String price;
	private Category category;
	private Country country;
	private Collection<Requirement> requirements;
	private Collection<Application> applications;
	private boolean abrogated = false;

	@NotBlank
	public String getClase() {
		return this.clase;
	}

	public void setClase(final String clase) {
		this.clase = clase;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getPrice() {
		return this.price;
	}

	public void setPrice(final String price) {
		this.price = price;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Requirement> getRequirements() {
		return this.requirements;
	}

	public void setRequirements(final Collection<Requirement> requirements) {
		this.requirements = requirements;
	}

	@OneToMany(mappedBy = "visa")
	@Valid
	@NotNull
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	public boolean isAbrogated() {
		return this.abrogated;
	}

	public void setAbrogated(final boolean abrogated) {
		this.abrogated = abrogated;
	}

}
