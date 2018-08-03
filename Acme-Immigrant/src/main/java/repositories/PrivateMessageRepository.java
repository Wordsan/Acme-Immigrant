package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PrivateMessage;

@Repository
public interface PrivateMessageRepository extends
		JpaRepository<PrivateMessage, Integer> {

}
