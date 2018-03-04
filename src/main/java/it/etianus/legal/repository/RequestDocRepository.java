package it.etianus.legal.repository;

import it.etianus.legal.domain.RequestDoc;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestDocRepository extends JpaRepository<RequestDoc, Long> {

}
