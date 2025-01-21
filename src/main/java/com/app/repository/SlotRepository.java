package com.app.repository;

import com.app.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    @Query("SELECT s FROM Slot s " +
            "WHERE s.booked = false " +
            "AND s.startDate >= :startDate " +
            "AND s.endDate < :endDate " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM Slot booked " +
            "    WHERE booked.booked = true " +
            "    AND booked.salesManager.id = s.salesManager.id " +
            "    AND booked.startDate < s.endDate " +
            "    AND booked.endDate > s.startDate " +
            ")")
    List<Slot> findAvailableSlotsWithinRange(
            @Param("startDate") ZonedDateTime startDate,
            @Param("endDate") ZonedDateTime endDate
    );
}
