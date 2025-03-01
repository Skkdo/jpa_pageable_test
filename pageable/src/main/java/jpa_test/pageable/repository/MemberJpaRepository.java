package jpa_test.pageable.repository;

import jpa_test.pageable.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m JOIN FETCH m.team WHERE m.name LIKE %:name%")
    Page<Member> findMembersByNameContains(String name, Pageable pageable);
}
