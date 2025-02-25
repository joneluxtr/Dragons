package com.ik.dragons.repository;

import com.ik.dragons.repository.entity.Rocket;
import com.ik.dragons.repository.entity.RocketStatus;
import com.ik.dragons.repository.entity.MissionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IDragonsRepositoryTest {

    IDragonsRepository dragons;

    @BeforeEach
    void init() {
        dragons = new DragonsRepositoryController();
    }

    @Test
    public void testCreateNewRocket() {
        var rocket = dragons.addNewRocket("Red Dragon");
        assertEquals("Red Dragon", rocket.name());
        assertEquals(RocketStatus.ON_GROUND, dragons.getRocketStatus(rocket));
    }

    @Test
    public void testCreateNewMission() {
        var mission = dragons.addNewMission("Transit");
        assertEquals("Transit", mission.name());
        assertEquals(MissionStatus.SCHEDULED, dragons.getMissionStatus(mission));
    }

    @Test
    public void testCreateFewRockets() {
        var rocket1 = dragons.addNewRocket("Red Dragon 1");
        var rocket2 = dragons.addNewRocket("Red Dragon 2");
        assertEquals("Red Dragon 1", rocket1.name());
        assertEquals("Red Dragon 2", rocket2.name());
    }

    @Test
    public void testCreateFewMissions() {
        var mission1 = dragons.addNewMission("Transit 1");
        var mission2 = dragons.addNewMission("Transit 2");
        assertEquals("Transit 1", mission1.name());
        assertEquals("Transit 2", mission2.name());
    }

    @Test
    public void testDuplicateNewRocket() {
        var rocket = dragons.addNewRocket("Red Dragon");
        var duplicateRocket = dragons.addNewRocket("Red Dragon");
        assertNull(duplicateRocket);
    }

    @Test
    public void testDuplicateNewMission() {
        var mission = dragons.addNewMission("Transit");
        var duplicateMission = dragons.addNewMission("Transit");
        assertNull(duplicateMission);
    }

    @Test
    void testAddRocketToMission() {
        var rocket = dragons.addNewRocket("Red Dragon");
        var mission = dragons.addNewMission("Transit");
        dragons.addRocketToMission(rocket, mission);
        List<Rocket> rockets = dragons.getMissionRockets(mission);
        assertEquals(1, rockets.size());
        assertTrue(rockets.contains(rocket));
    }

    @Test
    void removeRocketFromMission() {
        var rocket = dragons.addNewRocket("Red Dragon");
        var mission = dragons.addNewMission("Transit");
        dragons.addRocketToMission(rocket, mission);
        dragons.getMissionRockets(mission).contains(rocket);
        dragons.removeRocketFromMission(rocket);
        assertFalse(dragons.getMissionRockets(mission).contains(rocket));
    }
}