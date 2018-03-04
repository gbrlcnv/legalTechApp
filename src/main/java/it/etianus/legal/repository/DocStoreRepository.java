package it.etianus.legal.repository;

import it.etianus.legal.domain.DocStore;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DocStore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocStoreRepository extends JpaRepository<DocStore, Long> {

}
