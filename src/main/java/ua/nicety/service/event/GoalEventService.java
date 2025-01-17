package ua.nicety.service.event;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nicety.database.entity.event.Goal;
import ua.nicety.database.repository.EventRepository;
import ua.nicety.http.dto.EventCreateEditDto;
import ua.nicety.http.dto.read.GoalReadDto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("goal")
@RequiredArgsConstructor
public class GoalEventService implements EventService<Goal, GoalReadDto> {

    private final EventRepository<Goal> repository;
    private final ModelMapper modelMapper;

    public Goal create(EventCreateEditDto eventDto) {
        return Optional.of(eventDto)
                .map(createDto -> modelMapper.map(createDto, Goal.class))
                .map(repository::save).orElse(null);
    }

    @Transactional
    public Optional<Goal> update(Long id, EventCreateEditDto eventDto) {
        return repository.findById(id)
                .map(entity -> {
                    Goal mappedEntity = modelMapper.map(eventDto, Goal.class);
                    mappedEntity.setId(entity.getId());
                    return mappedEntity;
                })
                .map(repository::saveAndFlush);
    }

    @Transactional
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<Goal> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<GoalReadDto> findByScheduleId(String id) {
        return repository.findByScheduleId(id)
                .stream().map(event -> modelMapper.map(event, GoalReadDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Map<LocalDateTime, List<GoalReadDto>> findAllByName(String name, String scheduleId) {
        List<GoalReadDto> events = findByScheduleId(scheduleId);
        return events.stream()
                .filter(eventReadDto -> eventReadDto.getName().equals(name))
                .sorted()
                .collect(Collectors.groupingBy(LocalDateTime::from, TreeMap::new, Collectors.toList()));
    }

    @Override
    public Map<LocalDateTime, List<GoalReadDto>> getMapEvents(String scheduleId) {
        List<GoalReadDto> events = findByScheduleId(scheduleId);

        return events.stream()
                .sorted()
                .collect(Collectors.groupingBy(LocalDateTime::from, TreeMap::new, Collectors.toList()));
    }
}
