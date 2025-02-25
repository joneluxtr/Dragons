package com.ik.dragons.repository;

import com.ik.dragons.repository.entity.RocketStatus;
import com.ik.dragons.repository.entity.MissionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}