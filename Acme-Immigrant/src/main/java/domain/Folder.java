package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	private String title;
	private String type;
	private Actor owner;
	private Collection<PrivateMessage> privateMessages;

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Valid
	@Pattern(regexp = ("Private|Public|In-box"))
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getOwner() {
		return this.owner;
	}

	public void setOwner(final Actor owner) {
		this.owner = owner;
	}

	@Valid
	@NotNull
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "folder")
	public Collection<PrivateMessage> getPrivateMessages() {
		return this.privateMessages;
	}

	public void setPrivateMessages(
			final Collection<PrivateMessage> privateMessages) {
		this.privateMessages = privateMessages;
	}

}
