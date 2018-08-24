package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String name;
	private Category parent;
	private Collection<Category> childs;
	private Collection<Visa> visas;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getParent() {
		return this.parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

	@Valid
	@OneToMany(mappedBy = "parent")
	@NotNull
	public Collection<Category> getChilds() {
		return this.childs;
	}

	public void setChilds(final Collection<Category> childs) {
		this.childs = childs;
	}

	@OneToMany(mappedBy = "category")
	@Valid
	@NotNull
	public Collection<Visa> getVisas() {
		return this.visas;
	}

	public void setVisas(final Collection<Visa> visas) {
		this.visas = visas;
	}

}
