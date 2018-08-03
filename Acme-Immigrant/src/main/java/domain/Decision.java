package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Decision extends DomainEntity {

	private Date madeMoment;
	private boolean accepted;
	private Application application;
	private String comments;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	@Past
	public Date getMadeMoment() {
		return this.madeMoment;
	}

	public void setMadeMoment(final Date madeMoment) {
		this.madeMoment = madeMoment;
	}

	public boolean isAccepted() {
		return this.accepted;
	}

	public void setAccepted(final boolean accepted) {
		this.accepted = accepted;
	}

	@Valid
	@NotNull
	@OneToOne
	public Application getApplication() {
		return this.application;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

}
