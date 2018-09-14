package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Investigator;

@Repository
public interface InvestigatorRepository extends
		JpaRepository<Investigator, Integer> {

	@Query("select avg(i.immigrants.size), min(i.immigrants.size), max(i.immigrants.size), "
			+ "stddev(i.immigrants.size) from Investigator i")
	Double[] immigrantsSizeStadistics();

	@Query("select a from Investigator a where a.userAccount.id = ?1")
	Investigator getInvestigatorFromUAId(int UAId);
}
