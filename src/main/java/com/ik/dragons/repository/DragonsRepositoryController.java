package com.ik.dragons.repository;

import com.ik.dragons.exceptions.AssignmentException;
import com.ik.dragons.repository.entity.Mission;
import com.ik.dragons.repository.entity.MissionStatus;
import com.ik.dragons.repository.entity.Rocket;
import com.ik.dragons.repository.entity.RocketStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DragonsRepositoryController implements IDragonsRepository {

    private final Map<Rocket, RocketStatus> rockets = new HashMap();
    private final Map<Mission, MissionStatus> missions = new HashMap();
    private final Map<Rocket, Mission> assignments = new HashMap();

    @Override
    public Rocket addNewRocket(String name) {
        if (rockets.keySet().stream().anyMatch(r -> r.name().equals(name)))
            return null;
        var rocket = new Rocket(name);
        rockets.put(rocket, RocketStatus.ON_GROUND);
        return rocket;
    }

    @Override
    public Mission addNewMission(String name) {
        if (missions.keySet().stream().anyMatch(m -> m.name().equals(name)))
            return null;
        var mission = new Mission(name);
        missions.put(mission, MissionStatus.SCHEDULED);
        return mission;
    }

    public RocketStatus getRocketStatus(Rocket rocket) {
        return rockets.get(rocket);
    }

    public MissionStatus getMissionStatus(Mission mission) {
        return missions.get(mission);
    }

    @Override
    public void addRocketToMission(Rocket rocket, Mission mission) {
        if (assignments.containsKey(rocket)) {
            throw new AssignmentException("Rocket is already assigned to a mission");
        }
        assignments.put(rocket, mission);
    }

    @Override
    public List<Rocket> getMissionRockets(Mission mission) {
        return assignments.entrySet().stream()
                .filter(entry -> entry.getValue().equals(mission))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public void changeRocketStatus(Rocket rocket, RocketStatus status) {
        if (status.equals(RocketStatus.ON_GROUND)) {
            throw new AssignmentException("Rocket can't be assigned to 'On ground'");
        }
        if (!assignments.containsKey(rocket)) {
            throw new AssignmentException("Rocket isn't assigned to a mission");
        }
        Mission mission = assignments.get(rocket);
        if (status.equals(RocketStatus.IN_REPAIR)) {
            missions.put(assignments.get(rocket), MissionStatus.PENDING);
        }
        rockets.put(rocket, status);
    }

    @Override
    public void changeMissionStatus(Mission mission, MissionStatus status) {
        switch (status) {
            case SCHEDULED:
                throw new AssignmentException("Mission can't be assigned to 'On ground'");
            case PENDING:
                handleMissionPendingStatus(mission);
                break;
            case IN_PROGRESS:
                handleMissionInProgressStatus(mission);
                break;
            case ENDED:
                handleMissionEndedStatus(mission);
                break;
        }
    }

    private void handleMissionPendingStatus(Mission mission) {
        if (!assignments.containsValue(mission)) {
            throw new AssignmentException("Mission must have at least one rocket assigned to set status to 'Pending'");
        }
        boolean anyInRepair = assignments.entrySet().stream()
                .filter(entry -> entry.getValue().equals(mission))
                .anyMatch(entry -> rockets.get(entry.getKey()) == RocketStatus.IN_REPAIR);
        if (!anyInRepair) {
            throw new AssignmentException("At least one rocket must be in repair to set status to 'In repair'");
        }
    }

    private void handleMissionInProgressStatus(Mission mission) {
        if (!assignments.containsValue(mission)) {
            throw new AssignmentException("Mission must have at least one rocket assigned to set status to 'In Progress'");
        }
        boolean allNotInRepair = assignments.entrySet().stream()
                .filter(entry -> entry.getValue().equals(mission))
                .allMatch(entry -> rockets.get(entry.getKey()) != RocketStatus.IN_REPAIR);
        if (!allNotInRepair) {
            throw new AssignmentException("No rocket can be in repair to set status to 'In Progress'");
        }
        missions.put(mission, MissionStatus.IN_PROGRESS);
    }

    private void handleMissionEndedStatus(Mission mission) {
        if (assignments.containsValue(mission)) {
            throw new AssignmentException("Mission cannot be 'Ended' while rockets are still assigned");
        }
        missions.put(mission, MissionStatus.ENDED);
    }

}
