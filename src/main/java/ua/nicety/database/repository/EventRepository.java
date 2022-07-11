package ua.nicety.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.nicety.database.entity.event.Event;

import java.util.List;

public interface EventRepository<E> extends JpaRepository<E, Long>  {

    @Query(nativeQuery = true, value = "SELECT * FROM E e where e.schedule_id = :scheduleId")
    List<E> findByScheduleId(String scheduleId);

    @Query(nativeQuery = true, value = "SELECT * FROM E e where e.name = :name")
    List<E> findAllByName(String name);
}
