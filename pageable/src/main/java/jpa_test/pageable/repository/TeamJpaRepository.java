package jpa_test.pageable.repository;

import jpa_test.pageable.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamJpaRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t JOIN FETCH t.members WHERE t.name LIKE %:name%")
    Page<Team> findTeamsByNameContains(String name, Pageable pageable);
}
