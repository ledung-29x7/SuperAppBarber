package com.example.SuperAppBarber.staff.repository.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.example.SuperAppBarber.common.enums.DayOffStatus;
import com.example.SuperAppBarber.staff.model.DayOffEntity;

import jakarta.persistence.criteria.Predicate;

public class DayOffSpecification {

    public static Specification<DayOffEntity> search(
            UUID salonId,
            LocalDate fromDate,
            LocalDate toDate,
            DayOffStatus status,
            UUID staffId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("salonId"), salonId));
            predicates.add(cb.isNull(root.get("deletedAt")));

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (staffId != null) {
                predicates.add(cb.equal(root.get("staffId"), staffId));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("endDate"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("startDate"), toDate));
            }

            query.orderBy(cb.asc(root.get("startDate")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
