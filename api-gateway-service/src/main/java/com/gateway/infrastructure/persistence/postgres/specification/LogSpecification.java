package com.gateway.infrastructure.persistence.postgres.specification;

import com.gateway.application.dto.criteria.LogSearchCriteria;
import com.gateway.infrastructure.persistence.postgres.entity.LogJpaEntity;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LogSpecification {

    public static Specification<LogJpaEntity> withCriteria(LogSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Only add condition if field is NOT null (and not blank for strings)
            if (criteria.getDate() != null && !criteria.getDate().isBlank()) {
                try {
                    LocalDateTime date = LocalDateTime.parse(criteria.getDate(),
                            DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
                    LocalDateTime endOfDay = date.toLocalDate().plusDays(1).atStartOfDay();

                    log.info("Searching for logs between {} and {}", startOfDay, endOfDay);
                    log.info("Date field type: {}", root.get("date").getJavaType().getName());

                    Predicate datePredicate = cb.between(root.get("date"), startOfDay, endOfDay);
                    log.info("Generated date predicate: {}", datePredicate);

                    predicates.add(datePredicate);
                } catch (Exception e) {
                    log.error("Failed to process date criteria: {}", criteria.getDate(), e);
                }
            }

            if (isNonBlank(criteria.getMessage())) {
                predicates.add(cb.like(
                        cb.lower(root.get("message")),
                        "%" + criteria.getMessage().toLowerCase() + "%"
                ));
            }

            if (isNonBlank(criteria.getLevel())) {
                predicates.add(cb.equal(
                        cb.lower(root.get("level")),
                        criteria.getLevel().toLowerCase()
                ));
            }

            if (criteria.getStatusCode() != null && criteria.getStatusCode() != 0) {
                predicates.add(cb.equal(
                        root.get("statusCode"),
                        criteria.getStatusCode()));
            }

            if (isNonBlank(criteria.getError())) {
                predicates.add(cb.like(
                        cb.lower(root.get("error")),
                        "%" + criteria.getError().toLowerCase() + "%"
                ));
            }

            // If no filters → return "always true" predicate
            if (!predicates.isEmpty()) {
                log.info("Final predicates: {}", predicates);
                return cb.and(predicates.toArray(new Predicate[0]));
            }
            return cb.conjunction();
        };
    }

    private static boolean isNonBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }
}