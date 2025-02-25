package com.ik.dragons.repository;

import com.ik.dragons.repository.entity.Mission;
import com.ik.dragons.repository.entity.MissionStatus;
import com.ik.dragons.repository.entity.Rocket;
import com.ik.dragons.repository.entity.RocketStatus;

import java.util.HashMap;
import java.util.Map;

public class DragonsRepositoryController implements IDragonsRepository {

    private final Map<Rocket, RocketStatus> rockets = new HashMap();
    private final Map<Mission, MissionStatus> missions = new HashMap();


    @Override
    public Rocket addNewRocket(String name) {
        var rocket = new Rocket(name);
        rockets.put(rocket, RocketStatus.ON_GROUND);
        return rocket;
    }

    @Override
    public Mission addNewMission(String name) {
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

}
