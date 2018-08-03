package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Officer extends Actor {

	private Collection<Evaluation> evaluations;
	private Collection<Application> applications;
	private Supervisor supervisor;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "officer")
	public Collection<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(final Collection<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "officer")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@Valid
	@ManyToOne(optional = true)
	public Supervisor getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(final Supervisor supervisor) {
		this.supervisor = supervisor;
	}

}
