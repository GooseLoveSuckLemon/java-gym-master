package ru.yandex.practicum.test;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.gym.*;

import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        // Получаем TreeMap (всё расписание понедельника)
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> mondaySchedule =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        // Проверяем, что расписание не null и не пустое
        assertNotNull(mondaySchedule);
        assertFalse(mondaySchedule.isEmpty());

        // Проверяем, что есть запись на 13:00
        assertTrue(mondaySchedule.containsKey(new TimeOfDay(13, 0)));

        // Получаем список занятий на 13:00
        ArrayList<TrainingSession> monday1300Sessions = mondaySchedule.get(new TimeOfDay(13, 0));
        assertEquals(1, monday1300Sessions.size());
        assertTrue(monday1300Sessions.contains(singleTrainingSession));

        // Проверяем вторник (должно быть null)
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> tuesdaySchedule =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNull(tuesdaySchedule);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить понедельник: одно занятие в 13:00
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> mondaySchedule =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertNotNull(mondaySchedule);
        assertEquals(1, mondaySchedule.size()); // одна временная отметка

        ArrayList<TrainingSession> monday1300Sessions = mondaySchedule.get(new TimeOfDay(13, 0));
        assertEquals(1, monday1300Sessions.size());
        assertTrue(monday1300Sessions.contains(mondayChildTrainingSession));


        // Проверить четверг: два занятия (13:00 и 20:00)
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> thursdaySchedule =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertNotNull(thursdaySchedule);
        assertEquals(2, thursdaySchedule.size()); // две временные отметки

        ArrayList<TrainingSession> thursday1300Sessions = thursdaySchedule.get(new TimeOfDay(13, 0));
        ArrayList<TrainingSession> thursday2000Sessions = thursdaySchedule.get(new TimeOfDay(20, 0));

        assertEquals(1, thursday1300Sessions.size());
        assertEquals(1, thursday2000Sessions.size());

        assertEquals(thursdayChildTrainingSession, thursday1300Sessions.get(0));
        assertEquals(thursdayAdultTrainingSession, thursday2000Sessions.get(0));

        // Проверить вторник: нет занятий (должно быть null)
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> tuesdaySchedule =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNull(tuesdaySchedule);
    }


    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));


        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверить, что за понедельник в 13:00 вернулось одно занятие
        ArrayList<TrainingSession> monday1300Sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, monday1300Sessions.size());
        assertTrue(monday1300Sessions.contains(singleTrainingSession));

        // Проверить, что за понедельник в 14:00 не вернулось занятий
        ArrayList<TrainingSession> monday1400Sessions = timetable.getTrainingSessionsForDayAndTime
                (DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        // Сначала проверяем на null, потом на пустоту
        assertNull(monday1400Sessions);  // или:
        // assertTrue(monday1400Sessions == null || monday1400Sessions.isEmpty());
    }

    @Test
    void testAddNewTrainingSession_MultipleSessionsSameTime() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Йога для начинающих", Age.ADULT, 60);
        Group group2 = new Group("Силовая тренировка", Age.ADULT, 45);
        Coach coach = new Coach("Иванова", "Мария", "Петровна");

        TrainingSession session1 = new TrainingSession(
                group1, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(18, 30)
        );
        TrainingSession session2 = new TrainingSession(
                group2, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(18, 30)
        );

        // Добавляем два занятия на одно и то же время
        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        // Получаем расписание на среду
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> wednesdaySchedule =
                timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);

        // Проверяем, что время 18:30 присутствует
        assertTrue(wednesdaySchedule.containsKey(new TimeOfDay(18, 30)));

        ArrayList<TrainingSession> sessionsAt1830 = wednesdaySchedule.get(new TimeOfDay(18, 30));
        assertEquals(2, sessionsAt1830.size());

        // Проверяем, что оба занятия присутствуют
        assertTrue(sessionsAt1830.contains(session1));
        assertTrue(sessionsAt1830.contains(session2));
    }

    @Test
    void testGetTrainingSessionsForDay_EmptyDay() {
        Timetable timetable = new Timetable();

        // День без занятий
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> schedule =
                timetable.getTrainingSessionsForDay(DayOfWeek.SUNDAY);

        // Метод должен вернуть null, если день отсутствует или пуст
        assertNull(schedule);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime_TimeNotExists() {
        Timetable timetable = new Timetable();

        Group group = new Group("Зумба", Age.ADULT, 50);
        Coach coach = new Coach("Смирнова", "Анна", "Васильевна");

        TrainingSession session = new TrainingSession(
                group, coach, DayOfWeek.FRIDAY, new TimeOfDay(19, 00)
        );

        timetable.addNewTrainingSession(session);

        // Пытаемся получить занятия в пятницу в 20:00 (такого времени нет)
        ArrayList<TrainingSession> result = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.FRIDAY, new TimeOfDay(20, 00)
        );

        // Согласно контракту метода — возвращается null, если время не найдено
        assertNull(result);
    }
}

