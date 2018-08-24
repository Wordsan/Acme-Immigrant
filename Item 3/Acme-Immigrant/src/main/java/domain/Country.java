package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Country extends DomainEntity {

	private String name;
	private String ISOCode;
	private String flag;
	private String wikiPage;
	private Collection<Law> laws;
	private Collection<Visa> visas;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getISOCode() {
		return this.ISOCode;
	}

	public void setISOCode(final String iSOCode) {
		this.ISOCode = iSOCode;
	}

	@NotBlank
	@URL
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(final String flag) {
		this.flag = flag;
	}

	@NotBlank
	@URL
	public String getWikiPage() {
		return this.wikiPage;
	}

	public void setWikiPage(final String wikiPage) {
		this.wikiPage = wikiPage;
	}

	@OneToMany(mappedBy = "country")
	@Valid
	@NotNull
	public Collection<Law> getLaws() {
		return this.laws;
	}

	public void setLaws(final Collection<Law> laws) {
		this.laws = laws;
	}

	@OneToMany(mappedBy = "country")
	@Valid
	@NotNull
	public Collection<Visa> getVisas() {
		return this.visas;
	}

	public void setVisas(final Collection<Visa> visas) {
		this.visas = visas;
	}

}
