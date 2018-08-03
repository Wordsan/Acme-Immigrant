package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Supervisor extends Actor {

	private Collection<Officer> officers;
	private Collection<Evaluation> evaluations;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "supervisor")
	public Collection<Officer> getOfficers() {
		return this.officers;
	}

	public void setOfficers(final Collection<Officer> officers) {
		this.officers = officers;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "supervisor")
	public Collection<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(final Collection<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

}
