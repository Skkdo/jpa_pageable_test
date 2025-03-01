package jpa_test.pageable.repository;

import jpa_test.pageable.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long> {

    Page<Book> findBooksByNameContains(String name, Pageable pageable);
}
