package com.example.advancedsearchdemo.specifications;

import com.example.advancedsearchdemo.entity.Client;
import com.example.advancedsearchdemo.entity.Country;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ClientSpecifications {
    public static Specification<Client> advancedFilter(
            String name,
            String countryCode,
            LocalDate fromDate,
            LocalDate toDate
            ) {
        return (root, query, cb) -> {

            // 'root' is the entry point to the entity and joins

            List<Predicate> predicates = new ArrayList<>();

            // name LIKE %name%
            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            // createdDate BETWEEN from AND to
            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), fromDate.atStartOfDay()));
            }
            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdDate"), toDate.atTime(LocalTime.MAX)));
            }

            // Join: client.country.code = ?
            if (countryCode != null && !countryCode.isEmpty()) {
                Join<Client, Country> countryJoin = root.join("country", JoinType.LEFT);
                predicates.add(cb.equal(cb.lower(countryJoin.get("code")), countryCode.toLowerCase()));
            }

            // 'query' customization: remove duplicates if joins are used
            query.distinct(true);

            // You could also add `fetch` joins for eager loading in some contexts
            // Note: fetch only works in certain cases and is ignored in count queries
            // root.fetch("country", JoinType.LEFT); // optional

            // 'cb' is used to combine predicates
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
