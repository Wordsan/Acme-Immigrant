
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Immigrant;

@Repository
public interface ImmigrantRepository extends JpaRepository<Immigrant, Integer> {

	@Query("select avg(i.applications.size), min(i.applications.size), max(i.applications.size), " + "stddev(i.applications.size) from Immigrant i")
	Double[] applicationsSizeStadistics();

	@Query("select a from Immigrant a where a.userAccount.id = ?1")
	Immigrant getImmigrantFromUAId(int UAId);

	@Query("select a.immigrant from Officer o join o.applications a where o.id = ?1 and a.immigrant.investigator = null")
	List<Immigrant> immigrantsNotInvestigated(int officerId);
}
