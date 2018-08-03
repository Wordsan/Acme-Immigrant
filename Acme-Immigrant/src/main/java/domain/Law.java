package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Law extends DomainEntity {

	private String title;
	private String text;
	private Date createdAt;
	private Country country;
	private Date abrogatedAt;
	private Collection<Requirement> requirements;
	private Collection<Law> relatedLaws;

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	@NotNull
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getAbrogatedAt() {
		return this.abrogatedAt;
	}

	public void setAbrogatedAt(final Date abrogatedAt) {
		this.abrogatedAt = abrogatedAt;
	}

	@ManyToMany
	@NotNull
	@Valid
	public Collection<Law> getRelatedLaws() {
		return this.relatedLaws;
	}

	public void setRelatedLaws(final Collection<Law> relatedLaws) {
		this.relatedLaws = relatedLaws;
	}

	@NotEmpty
	@Valid
	@OneToMany(mappedBy = "law")
	public Collection<Requirement> getRequirements() {
		return this.requirements;
	}

	public void setRequirements(final Collection<Requirement> requirements) {
		this.requirements = requirements;
	}

}
