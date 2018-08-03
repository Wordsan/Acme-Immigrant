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
public class Investigator extends Actor {

	private Collection<Immigrant> immigrants;
	private Collection<Report> reports;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "investigator")
	public Collection<Immigrant> getImmigrants() {
		return this.immigrants;
	}

	public void setImmigrants(final Collection<Immigrant> immigrants) {
		this.immigrants = immigrants;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "investigator")
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

}
