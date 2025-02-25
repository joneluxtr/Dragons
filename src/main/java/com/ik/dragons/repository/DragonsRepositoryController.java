package com.ik.dragons.repository;

import com.ik.dragons.repository.entity.Mission;
import com.ik.dragons.repository.entity.MissionStatus;
import com.ik.dragons.repository.entity.Rocket;
import com.ik.dragons.repository.entity.RocketStatus;

public class DragonsRepositoryController implements IDragonsRepository {

    @Override
    public Rocket addNewRocket(String name) {
        return null;
    }

    @Override
    public Mission addNewMission(String name) {
        return null;
    }

    public RocketStatus getRocketStatus(Rocket rocket) {
        return null;
    }

    public MissionStatus getMissionStatus(Mission mission) {
        return null;
    }

}
