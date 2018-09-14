package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Visa;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Integer> {

	// Puede que haya que dividirla en dos y unirla en el servicio
	@Query("select v from Visa v where v.clase like %?1% or v.description like %?1%")
	List<Visa> searchVisaFromKeyWordClase(String keyWord);

	// Ojo, si es el precio en euros devuelve ? en vez de €
	@Query("select avg(v.price), min(v.price), max(v.price), stddev(v.price) from Visa v")
	String[] priceStadistics();

	@Query("select avg(v.requirements.size), min(v.requirements.size), max(v.requirements.size), stddev(v.requirements.size) from Visa v")
	Double[] requirementsStadistics();

	@Query("select v from Visa v where v.abrogated = false")
	List<Visa> getAvailableVisas();
}
