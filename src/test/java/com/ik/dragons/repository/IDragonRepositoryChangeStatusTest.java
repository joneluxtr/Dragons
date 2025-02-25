package com.ik.dragons.repository;

import com.ik.dragons.exceptions.AssignmentException;
import com.ik.dragons.repository.entity.Mission;
import com.ik.dragons.repository.entity.MissionStatus;
import com.ik.dragons.repository.entity.Rocket;
import com.ik.dragons.repository.entity.RocketStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IDragonRepositoryChangeStatusTest {

    IDragonsRepository dragons;
    Rocket rocket;
    Mission mission;

    @BeforeEach
    void init() {
        dragons = new DragonsRepositoryController();
        rocket = dragons.addNewRocket("Red Dragon");
        mission = dragons.addNewMission("Transit");
    }

    @Test
    void changeRocketStatus_ToInSpace_IfMissionScheduled() {
        dragons.addRocketToMission(rocket, mission);
        dragons.changeRocketStatus(rocket, RocketStatus.IN_SPACE);
        assertEquals(RocketStatus.IN_SPACE, dragons.getRocketStatus(rocket));
    }

    @Test
    void changeRocketStatus_ToInRepair_IfMissionScheduled() {
        dragons.addRocketToMission(rocket, mission);
        dragons.changeRocketStatus(rocket, RocketStatus.IN_REPAIR);
        assertEquals(MissionStatus.PENDING, dragons.getMissionStatus(mission));
    }

    @Test
    void changeRocketStatus_ToInRepair_IfNoMission() {
        AssignmentException exception = assertThrows(AssignmentException.class
                , () -> dragons.changeRocketStatus(rocket, RocketStatus.IN_REPAIR));
        assertEquals("Rocket isn't assigned to a mission"
                , exception.getMessage());
    }

    @Test
    void changeRocketStatus_ToOnGround() {
        dragons.addRocketToMission(rocket, mission);
        AssignmentException exception = assertThrows(AssignmentException.class
                , () -> dragons.changeRocketStatus(rocket, RocketStatus.ON_GROUND));
        assertEquals("Rocket can't be assigned to 'On ground'"
                , exception.getMessage());
    }

    @Test
    void changeMissionStatus_ToScheduled() {
        dragons.addRocketToMission(rocket, mission);
        AssignmentException exception = assertThrows(AssignmentException.class
                , () -> dragons.changeMissionStatus(mission, MissionStatus.SCHEDULED));
        assertEquals("Mission can't be assigned to 'On ground'"
                , exception.getMessage());
    }

    @Test
    void changeMissionStatus_ToPending_IfDoesntHaveRocket() {
        AssignmentException exception = assertThrows(AssignmentException.class
                , () -> dragons.changeMissionStatus(mission, MissionStatus.PENDING));
        assertEquals("Mission must have at least one rocket assigned to set status to 'Pending'"
                , exception.getMessage());
    }

    @Test
    void changeMissionStatus_ToPending_IfDoesntHavePending() {
        dragons.addRocketToMission(rocket, mission);
        AssignmentException exception = assertThrows(AssignmentException.class
                , () -> dragons.changeMissionStatus(mission, MissionStatus.PENDING));
        assertEquals("At least one rocket must be in repair to set status to 'In repair'"
                , exception.getMessage());
    }

    @Test
    void changeMissionStatus_ToInProgress() {
        dragons.addRocketToMission(rocket, mission);
        dragons.changeRocketStatus(rocket, RocketStatus.IN_SPACE);
        dragons.changeMissionStatus(mission, MissionStatus.IN_PROGRESS);
        assertEquals(MissionStatus.IN_PROGRESS, dragons.getMissionStatus(mission));
    }

    @Test
    void changeMissionStatus_ToInProgress_IfDoesntHaveRocket() {
        AssignmentException exception = assertThrows(AssignmentException.class
                , () -> dragons.changeMissionStatus(mission, MissionStatus.IN_PROGRESS));
        assertEquals("Mission must have at least one rocket assigned to set status to 'In Progress'"
                , exception.getMessage());
    }

    @Test
    void changeMissionStatus_ToInProgress_IfHavePending() {
        dragons.addRocketToMission(rocket, mission);
        dragons.changeRocketStatus(rocket, RocketStatus.IN_REPAIR);
        AssignmentException exception = assertThrows(AssignmentException.class
                , () -> dragons.changeMissionStatus(mission, MissionStatus.IN_PROGRESS));
        assertEquals("No rocket can be in repair to set status to 'In Progress'"
                , exception.getMessage());
    }

    @Test
    void changeMissionStatus_ToEnded() {
        dragons.changeMissionStatus(mission, MissionStatus.ENDED);
        assertEquals(MissionStatus.ENDED, dragons.getMissionStatus(mission));
    }

    @Test
    void changeMissionStatus_ToEnded_IfDoesntHaveRocket() {
        dragons.addRocketToMission(rocket, mission);
        AssignmentException exception = assertThrows(AssignmentException.class
                , () -> dragons.changeMissionStatus(mission, MissionStatus.ENDED));
        assertEquals("Mission cannot be 'Ended' while rockets are still assigned"
                , exception.getMessage());
    }

}
