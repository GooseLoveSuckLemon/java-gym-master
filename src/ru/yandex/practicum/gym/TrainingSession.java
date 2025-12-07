package ru.yandex.practicum.gym;

import java.util.Objects;

public class TrainingSession {

    //группа
    private Group group;
    //тренер
    private Coach coach;
    //день недели
    private DayOfWeek dayOfWeek;
    //время начала занятия
    private TimeOfDay timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingSession trainingSession = (TrainingSession) o;
        return Objects.equals(group, trainingSession.group)
                && Objects.equals(coach, trainingSession.coach)
                    && Objects.equals(dayOfWeek, trainingSession.dayOfWeek)
                    && Objects.equals(timeOfDay, trainingSession.timeOfDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, coach, dayOfWeek, timeOfDay);
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }
}