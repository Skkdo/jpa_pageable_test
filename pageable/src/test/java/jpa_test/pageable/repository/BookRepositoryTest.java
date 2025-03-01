package jpa_test.pageable.repository;

import jakarta.persistence.EntityManager;

import jpa_test.pageable.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private BookJpaRepository repository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    Book createBook(String name) {
        return Book.builder()
                .name(name)
                .build();
    }

    @BeforeEach
    void setUp() {
        String name = "book";
        for (int i = 1; i <= 10; i++) {
            Book book = createBook(name + i);
            repository.save(book);
        }
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("pageable SQL 문 변화, 쿼리 limit + count Log 확인 (Assertion X)")
    void test() {
        log.info("test start");
        PageRequest pageRequest = PageRequest.of(1, 5);
        repository.findBooksByNameContains("book", pageRequest);
        em.flush();
        em.clear();
    }
}
