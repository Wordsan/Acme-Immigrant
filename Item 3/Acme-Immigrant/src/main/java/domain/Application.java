package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "status") }, uniqueConstraints = { @UniqueConstraint(columnNames = {
		"id", "visa_id" }) })
public class Application extends DomainEntity {
	private String ticker;//
	private Date openedMoment;//
	private Date closedMoment;
	private Visa visa;//
	private Immigrant immigrant;//
	private String status;//
	private CreditCard creditCard;
	private Officer officer;//
	private Decision decision;
	private PersonalSection personalSection;//
	private Collection<ContactSection> contactSections;
	private Collection<SocialSection> socialSections;//
	private Collection<EducationSection> educationSections;
	private Collection<WorkSection> workSections;
	private Collection<Question> questions;
	private Collection<Application> linkedApplications;

	@Column(unique = true)
	@Pattern(regexp = "^[0-9]{6}[-][A-Z]{4}[0-9]{2}$")
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getOpenedMoment() {
		return this.openedMoment;
	}

	public void setOpenedMoment(final Date openedMoment) {
		this.openedMoment = openedMoment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getClosedMoment() {
		return this.closedMoment;
	}

	public void setClosedMoment(final Date closedMoment) {
		this.closedMoment = closedMoment;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Visa getVisa() {
		return this.visa;
	}

	public void setVisa(final Visa visa) {
		this.visa = visa;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Immigrant getImmigrant() {
		return this.immigrant;
	}

	public void setImmigrant(final Immigrant immigrant) {
		this.immigrant = immigrant;
	}

	// Se usa como indice
	@Valid
	@Pattern(regexp = ("OPENED|ACCEPTED|REJECTED|AWAITING"))
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@ManyToOne(optional = true)
	@Valid
	public Officer getOfficer() {
		return this.officer;
	}

	public void setOfficer(final Officer officer) {
		this.officer = officer;
	}

	@Valid
	@OneToOne(optional = true)
	@Cascade(value = org.hibernate.annotations.CascadeType.ALL)
	public PersonalSection getPersonalSection() {
		return this.personalSection;
	}

	public void setPersonalSection(final PersonalSection personalSection) {
		this.personalSection = personalSection;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public Collection<ContactSection> getContactSections() {
		return this.contactSections;
	}

	public void setContactSections(
			final Collection<ContactSection> contactSections) {
		this.contactSections = contactSections;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public Collection<SocialSection> getSocialSections() {
		return this.socialSections;
	}

	public void setSocialSections(final Collection<SocialSection> socialSections) {
		this.socialSections = socialSections;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public Collection<EducationSection> getEducationSections() {
		return this.educationSections;
	}

	public void setEducationSections(
			final Collection<EducationSection> educationSections) {
		this.educationSections = educationSections;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public Collection<WorkSection> getWorkSections() {
		return this.workSections;
	}

	public void setWorkSections(final Collection<WorkSection> workSections) {
		this.workSections = workSections;
	}

	@OneToMany(mappedBy = "application")
	@Valid
	@NotNull
	public Collection<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(final Collection<Question> questions) {
		this.questions = questions;
	}

	@Valid
	@OneToOne
	public Decision getDecision() {
		return this.decision;
	}

	public void setDecision(final Decision decision) {
		this.decision = decision;
	}

	@NotNull
	@ManyToMany
	public Collection<Application> getLinkedApplications() {
		return this.linkedApplications;
	}

	public void setLinkedApplications(
			final Collection<Application> linkedApplications) {
		this.linkedApplications = linkedApplications;
	}

}
