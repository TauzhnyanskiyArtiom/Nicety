package ua.nicety.http.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ua.nicety.database.entity.Day;
import ua.nicety.http.validation.annotation.ScheduleValidate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventCreateEditDto {

    @NotBlank(message = "Please write the name of the event")
    String name;

    String description;

    String smiles;

    String color;

    Day day;

    @NotNull(message = "Please write time of the event")
    LocalTime time;

    @ScheduleValidate
    String scheduleId;
}
