package com.ik.dragons.repository;

import com.ik.dragons.repository.entity.Mission;
import com.ik.dragons.repository.entity.MissionStatus;
import com.ik.dragons.repository.entity.Rocket;
import com.ik.dragons.repository.entity.RocketStatus;

import java.util.List;
import java.util.Map;

public interface IDragonsRepository {

    Rocket addNewRocket(String name);
    Mission addNewMission(String name);

    RocketStatus getRocketStatus(Rocket rocket);
    MissionStatus getMissionStatus(Mission mission);

    void addRocketToMission(Rocket rocket, Mission mission);
    List<Rocket> getMissionRockets(Mission mission);

    void changeRocketStatus(Rocket rocket, RocketStatus status);
    void changeMissionStatus(Mission mission, MissionStatus status);

    void removeRocketFromMission(Rocket rocket);

    Map<Mission, List<Rocket>> getAllMissions();

}
