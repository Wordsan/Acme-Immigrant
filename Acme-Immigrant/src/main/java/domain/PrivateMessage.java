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
public class PrivateMessage extends DomainEntity {

	private Folder folder;
	private String subject;
	private Date MadeMoment;
	private String body;
	private Actor sender;
	private String attachments;

	@Valid
	@NotNull
	@ManyToOne
	public Folder getFolder() {
		return this.folder;
	}

	public void setFolder(final Folder folder) {
		this.folder = folder;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	@NotNull
	public Date getMadeMoment() {
		return this.MadeMoment;
	}

	public void setMadeMoment(final Date madeMoment) {
		this.MadeMoment = madeMoment;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

}
