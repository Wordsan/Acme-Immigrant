package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends
		JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.closedMoment is null and a.immigrant.userAccount.id = ?1")
	List<Application> selectApplicationsByImmigrantUANotClosed(
			Integer immigrantUAId);

	@Query("select a from Application a where a.immigrant.userAccount.id = ?1 and a.status = ?2")
	List<Application> selectApplicationFromStatus(Integer UAId, String status);

	@Query("select a from Application a where a.officer.userAccount.id = ?1 and a.status = ?2")
	List<Application> applicationFromOfficerStatus(int UAId, String status);

	@Query("select a from Application a where a.officer.userAccount.id = ?1")
	List<Application> applicationFromOfficer(int UAId);

	@Query("select a from Application a where a.officer is null and a.status = 'AWAITING'")
	List<Application> selectApplicationsNotAssigned();

	@Query("select a from Application a where a.officer.userAccount.id = ?1")
	List<Application> selectApplicationsByOfficerUAId();

	@Query("select DATEDIFF(a.decision.madeMoment, a.closedMoment) from Application a")
	List<Integer> dateDifference();

	@Query("select avg(DATEDIFF(a.decision.madeMoment, a.closedMoment)), min(DATEDIFF(a.decision.madeMoment, a.closedMoment)), max(DATEDIFF(a.decision.madeMoment, a.closedMoment)), "
			+ "stddev(DATEDIFF(a.decision.madeMoment, a.closedMoment)) from Application a")
	Double[] dateStadistics();

	@Query("select avg(DATEDIFF(a.decision.madeMoment, a.closedMoment)), stddev(DATEDIFF(a.decision.madeMoment, a.closedMoment)) from Application a where a.visa.id = ?1")
	Double[] dateDifferenceByAVisa(Integer visaId);

	@Query("select a from Application a where a.ticker = ?1")
	Application getApplicationByTicker(String ticker);
}
