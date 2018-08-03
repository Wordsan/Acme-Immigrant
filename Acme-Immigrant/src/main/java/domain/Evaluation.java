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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Evaluation extends DomainEntity {

	private String text;
	private String mark;
	private Date dateOf;
	private Officer officer;
	private Supervisor supervisor;

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Valid
	@Pattern(regexp = ("F|C|B|A|A+"))
	public String getMark() {
		return this.mark;
	}

	public void setMark(final String mark) {
		this.mark = mark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	@NotNull
	public Date getDateOf() {
		return this.dateOf;
	}

	public void setDateOf(final Date dateOf) {
		this.dateOf = dateOf;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Officer getOfficer() {
		return this.officer;
	}

	public void setOfficer(final Officer officer) {
		this.officer = officer;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Supervisor getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(final Supervisor supervisor) {
		this.supervisor = supervisor;
	}

}
