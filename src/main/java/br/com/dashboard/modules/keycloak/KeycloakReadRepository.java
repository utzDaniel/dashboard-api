package br.com.dashboard.modules.keycloak;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class KeycloakReadRepository {

    private static final String USER_SQL = """
            SELECT
                u.ID
            FROM USER_ENTITY u
            INNER JOIN REALM r ON u.REALM_ID = r.ID
            WHERE r.NAME = ? AND u.USERNAME = ?
            """;

    private final JdbcTemplate keycloakJdbcTemplate;

    public KeycloakReadRepository(@Qualifier("keycloakJdbcTemplate") JdbcTemplate keycloakJdbcTemplate) {
        this.keycloakJdbcTemplate = keycloakJdbcTemplate;
    }

    public Optional<String> findUserId(String realmName, String username) {
        return keycloakJdbcTemplate.query(
                USER_SQL, (rs, rowNum) -> rs.getString("ID"),
                realmName,
                username
        ).stream().findFirst();
    }

}