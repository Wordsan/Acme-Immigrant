package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationSection extends DomainEntity {

	private String degreeName;
	private String institution;
	private Date awarded;
	private String level;

	@NotBlank
	public String getDegreeName() {
		return this.degreeName;
	}

	public void setDegreeName(final String degreeName) {
		this.degreeName = degreeName;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getAwarded() {
		return this.awarded;
	}

	public void setAwarded(final Date awarded) {
		this.awarded = awarded;
	}

	@Valid
	@Pattern(regexp = ("NONE|ELEMENTARY|PRIMARY|SECONDARY|HIGH|BACHELOR|UNIVERSITYDEGREE|MASTER|DOCTORATE"))
	public String getLevel() {
		return this.level;
	}

	public void setLevel(final String level) {
		this.level = level;
	}

}
