package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r where r.immigrant.id = ?1")
	List<Report> selectReportsFromImmigrantId(Integer immigrantId);

	@Query("select r from Report r where r.officer.id = ?1")
	List<Report> reportsOfAnOfficer(int officerId);
}
