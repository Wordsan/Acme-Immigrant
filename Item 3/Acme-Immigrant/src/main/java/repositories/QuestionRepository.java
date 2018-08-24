package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Query("select q from Question q where q.immigrant.userAccount.id = ?1")
	List<Question> selectQuestionFromImmigrantUserAccount(Integer id);
}
