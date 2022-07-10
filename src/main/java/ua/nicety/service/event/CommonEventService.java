package ua.nicety.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nicety.database.entity.Day;
import ua.nicety.database.entity.Event;
import ua.nicety.database.repository.EventRepository;
import ua.nicety.http.dto.EventCreateEditDto;
import ua.nicety.http.dto.read.EventReadDto;
import ua.nicety.http.mapper.EventCreateEditMapper;
import ua.nicety.http.mapper.read.EventReadMapper;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service("common")
@RequiredArgsConstructor
public class CommonEventService implements EventService<Event, EventReadDto, EventCreateEditDto> {

    private final EventRepository<Event> commonEventRepository;
    private final EventCreateEditMapper eventCreateEditMapper;

    private final EventReadMapper eventReadMapper;


    public Event create(EventCreateEditDto eventDto) {
        return Optional.of(eventDto)
                .map(eventCreateEditMapper::map)
                .map(commonEventRepository::save).orElse(null);
    }

    @Transactional
    public Optional<Event> update(Long id, EventCreateEditDto eventDto) {
        return commonEventRepository.findById(id)
                .map(entity -> eventCreateEditMapper.map(eventDto, entity))
                .map(commonEventRepository::saveAndFlush);
    }

    @Transactional
    public boolean delete(Long id) {
        return commonEventRepository.findById(id)
                .map(entity -> {
                    commonEventRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return commonEventRepository.findById(id);
    }


    @Override
    public List<EventReadDto> findByScheduleId(String id) {
        return commonEventRepository.findByScheduleId(id)
                .stream().map(eventReadMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Day, List<EventReadDto>> findAllByName(String name, String scheduleId) {
        List<EventReadDto> events = findByScheduleId(scheduleId);
        return events.stream()
                .filter(eventReadDto -> eventReadDto.getName().equals(name))
                .sorted(Comparator.comparing((EventReadDto event) -> event.getDay().ordinal())
                        .thenComparing(EventReadDto::getTime))
                .collect(groupingBy(EventReadDto::getDay, LinkedHashMap::new, Collectors.toList()));
    }

    @Override
    public Map<Day, List<EventReadDto>> getMapEvents(String scheduleId) {
        List<EventReadDto> events = findByScheduleId(scheduleId);
        return events.stream()
                .sorted(Comparator.comparing((EventReadDto event) -> event.getDay().ordinal())
                        .thenComparing(EventReadDto::getTime))
                .collect(groupingBy(EventReadDto::getDay, LinkedHashMap::new, Collectors.toList()));
    }
}