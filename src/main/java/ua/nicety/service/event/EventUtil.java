package ua.nicety.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.nicety.database.entity.event.BaseEvent;

@Component
@RequiredArgsConstructor
public class EventUtil {

    private final CommonEventService commonEventService;
    private final MeetingEventService meetingEventService;
    private final GoalEventService goalEventService;

    public EventService<? extends BaseEvent, ?> getEventService(String typeEvent){
        return switch (typeEvent) {
            case "common" ->  commonEventService;
            case "meeting" -> meetingEventService;
            case "goal" ->    goalEventService;
            default -> null;
        };
    }
}
