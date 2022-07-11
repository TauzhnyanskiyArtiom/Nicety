package ua.nicety.http.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.nicety.database.entity.Event;
import ua.nicety.database.entity.Goal;
import ua.nicety.database.entity.Schedule;
import ua.nicety.http.dto.EventCreateEditDto;
import ua.nicety.service.schedule.ScheduleService;

@Component
@RequiredArgsConstructor
public class GoalCreateEditMapper implements Mapper<EventCreateEditDto, Goal> {

    private final ScheduleService scheduleService;

    @Override
    public Goal map(EventCreateEditDto object) {
        Goal event = new Goal();
        copy(object, event);

        return event;
    }
    public Goal map(EventCreateEditDto fromObject, Goal toObject) {
        copy(fromObject, toObject);
        return toObject;
    }


    private void copy(EventCreateEditDto object, Goal event) {
        event.setName(object.getName());
        event.setDescription(object.getDescription());
        event.setSmiles(object.getSmiles());
        event.setColor(object.getColor());
        event.setSchedule(getSchedule(object.getScheduleId()));
    }

    private Schedule getSchedule(String id){
        return scheduleService.getById(id);
    }
}