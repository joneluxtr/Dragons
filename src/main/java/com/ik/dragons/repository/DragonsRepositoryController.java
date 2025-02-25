package com.ik.dragons.repository;

import com.ik.dragons.repository.entity.Mission;
import com.ik.dragons.repository.entity.MissionStatus;
import com.ik.dragons.repository.entity.Rocket;
import com.ik.dragons.repository.entity.RocketStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DragonsRepositoryController implements IDragonsRepository {

    private final Map<Rocket, RocketStatus> rockets = new HashMap();
    private final Map<Mission, MissionStatus> missions = new HashMap();


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

    }

    @Override
    public List<Rocket> getMissionRockets(Mission mission) {
        return List.of();
    }

}
