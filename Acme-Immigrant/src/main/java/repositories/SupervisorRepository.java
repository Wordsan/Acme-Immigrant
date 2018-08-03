package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Supervisor;

@Repository
public interface SupervisorRepository extends
		JpaRepository<Supervisor, Integer> {

}
