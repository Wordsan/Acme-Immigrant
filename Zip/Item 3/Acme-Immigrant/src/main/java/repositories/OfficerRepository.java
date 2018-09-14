package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Officer;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Integer> {

	@Query("select avg(o.applications.size), min(o.applications.size), max(o.applications.size), "
			+ "stddev(o.applications.size) from Officer o")
	Double[] applicationsSizeStadistics();

	@Query("select a from Officer a where a.userAccount.id = ?1")
	Officer getOfficerFromUAId(int UAId);
}
