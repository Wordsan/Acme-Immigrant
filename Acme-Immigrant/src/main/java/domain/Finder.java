package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private Actor owner;
	private String keyword;
	private Collection<PrivateMessage> results;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getOwner() {
		return this.owner;
	}

	public void setOwner(final Actor owner) {
		this.owner = owner;
	}

	@NotBlank
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Valid
	@NotNull
	@OneToMany
	public Collection<PrivateMessage> getResults() {
		return this.results;
	}

	public void setResults(final Collection<PrivateMessage> results) {
		this.results = results;
	}

}
