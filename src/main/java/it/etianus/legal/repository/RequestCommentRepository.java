package it.etianus.legal.repository;

import it.etianus.legal.domain.RequestComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestCommentRepository extends JpaRepository<RequestComment, Long> {

}
