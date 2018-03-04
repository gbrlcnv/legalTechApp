package it.etianus.legal.repository;

import it.etianus.legal.domain.RequestStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {

}
