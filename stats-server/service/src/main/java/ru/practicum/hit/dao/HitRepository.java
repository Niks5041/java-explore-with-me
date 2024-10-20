package ru.practicum.hit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.hit.model.HitStats;

import java.time.LocalDateTime;
import java.util.Collection;

public interface HitRepository extends JpaRepository<HitStats, Integer> {
    Collection<HitStats> findByTimestampBeforeAndTimestampAfterAndUriIn(
            LocalDateTime end,
            LocalDateTime start,
            String[] uris
    );

    Collection<HitStats> findByTimestampBeforeAndTimestampAfter(
            LocalDateTime end,
            LocalDateTime start
    );


    @Query("SELECT COUNT(h) FROM HitStats h WHERE h.timestamp < :end AND h.timestamp > :start")
    Integer countByTimestampBeforeAndTimestampAfter(@Param("start") LocalDateTime start,
                                                    @Param("end") LocalDateTime end);



    @Query("SELECT COUNT(h) FROM HitStats h WHERE h.timestamp < :end AND h.timestamp > :start AND h.uri IN :uris")
    Integer countByTimestampBeforeAndTimestampAfterAndUriIn(@Param("start") LocalDateTime start,
                                                            @Param("end") LocalDateTime end,
                                                            @Param("uris") String[] uris);

    @Query(value = "SELECT DISTINCT ON (h.ip) * FROM hits h" +
            " WHERE h.timestamp < :end AND h.timestamp > :start" +
            " AND h.uri IN (:uris) ORDER BY h.ip, h.timestamp", nativeQuery = true)
    Collection<HitStats> findDistinctHitsNative(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") String[] uris
    );

    @Query("SELECT COUNT(DISTINCT h.ip) FROM HitStats h WHERE h.timestamp < :end AND h.timestamp > :start AND h.uri IN :uris")
    Integer countDistinctIpsByTimestampBeforeAndTimestampAfterAndUriIn(@Param("start") LocalDateTime start,
                                                                       @Param("end") LocalDateTime end,
                                                                       @Param("uris") String[] uris);
}


