package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Access(AccessType.PROPERTY)
public class Immigrant extends Actor {

	// private Collection<Visa> visas;
	private Investigator investigator;
	private Collection<CreditCard> creditCards;
	private Collection<Question> questions;
	private Collection<Application> applications;
	private Collection<Report> reports;

	// @Valid
	// @NotNull
	// @ManyToMany
	// public Collection<Visa> getVisas() {
	// return this.visas;
	// }
	//
	// public void setVisas(final Collection<Visa> visas) {
	// this.visas = visas;
	// }

	@Valid
	@ManyToOne
	public Investigator getInvestigator() {
		return this.investigator;
	}

	public void setInvestigator(final Investigator investigator) {
		this.investigator = investigator;
	}

	@Valid
	@NotNull
	@ElementCollection
	@Cascade(value = CascadeType.ALL)
	public Collection<CreditCard> getCreditCards() {
		return this.creditCards;
	}

	public void setCreditCards(final Collection<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "immigrant")
	public Collection<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(final Collection<Question> questions) {
		this.questions = questions;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "immigrant")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "immigrant")
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

}
