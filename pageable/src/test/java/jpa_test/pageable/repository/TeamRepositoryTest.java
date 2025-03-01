package jpa_test.pageable.repository;

import jakarta.persistence.EntityManager;
import jpa_test.pageable.entity.Member;
import jpa_test.pageable.entity.Team;
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
public class TeamRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private TeamJpaRepository repository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    Member createMember(String name, Team team) {
        return Member.builder()
                .name(name)
                .team(team)
                .build();
    }

    Team createTeam(String name) {
        return Team.builder()
                .name(name)
                .build();
    }

    @BeforeEach
    void setUp() {
        String name = "name";
        for (int i = 1; i <= 10; i++) {
            Team team = createTeam(name + i);

            for(int j = 1; j <= 3; j++) {
                Member member = createMember(name + i + j, team);
                em.persist(member);
                team.addMember(member);
            }
            em.persist(team);
        }
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("1:N 페치 조인 + 페이징, applying in memory Log 확인 (Assertion X)")
    void test() {
        log.info("test start");
        String name = "name";
        PageRequest pageRequest = PageRequest.of(1, 5);
        repository.findTeamsByNameContains(name, pageRequest);
    }
}
