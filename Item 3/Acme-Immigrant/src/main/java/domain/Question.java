package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Question extends DomainEntity {

	private String statement;
	private Date madeMoment;
	private Application application;
	private String answer;
	private Date answerMoment;
	private Immigrant immigrant;

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	@NotNull
	public Date getMadeMoment() {
		return this.madeMoment;
	}

	public void setMadeMoment(final Date madeMoment) {
		this.madeMoment = madeMoment;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Application getApplication() {
		return this.application;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getAnswerMoment() {
		return this.answerMoment;
	}

	public void setAnswerMoment(final Date answerMoment) {
		this.answerMoment = answerMoment;
	}

	@Valid
	@NotNull
	@ManyToOne
	public Immigrant getImmigrant() {
		return this.immigrant;
	}

	public void setImmigrant(final Immigrant immigrant) {
		this.immigrant = immigrant;
	}

}
