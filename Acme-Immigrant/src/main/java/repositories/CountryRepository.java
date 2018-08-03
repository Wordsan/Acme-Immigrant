package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

	@Query("select avg(c.laws.size), min(c.laws.size), max(c.laws.size), "
			+ "stddev(c.laws.size) from Country c")
	Double[] lawStadistics();
}
