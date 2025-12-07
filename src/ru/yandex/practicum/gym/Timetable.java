package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        Group group = trainingSession.getGroup();
        Coach coach = trainingSession.getCoach();
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(dayOfWeek)) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (!daySchedule.containsKey(timeOfDay)) {
            daySchedule.put(timeOfDay, new ArrayList<>());
        }

        daySchedule.get(timeOfDay).add(trainingSession);
    }

    public TreeMap<TimeOfDay,ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        if (daySchedule == null || daySchedule.isEmpty()) {
            return null;
        }
        return daySchedule;
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        if (daySchedule == null) {
            return null;
        }
        ArrayList<TrainingSession> sessions = daySchedule.get(timeOfDay);
        if (sessions == null) {
            return null;
        }
        return sessions;
    }
}
