package com.ik.dragons.cli;

import com.ik.dragons.repository.DragonsRepositoryController;
import com.ik.dragons.repository.IDragonsRepository;
import com.ik.dragons.repository.entity.Mission;
import com.ik.dragons.repository.entity.MissionStatus;
import com.ik.dragons.repository.entity.Rocket;
import com.ik.dragons.repository.entity.RocketStatus;

import java.util.List;
import java.util.Map;

public class DragonsMain {

    public static void main(String[] args) {
        IDragonsRepository dragons = new DragonsRepositoryController();

        var redDragon = dragons.addNewRocket("Red Dragon");
        var dragonXl = dragons.addNewRocket("Dragon XL");
        var falconHeavy = dragons.addNewRocket("Falcon Heavy");
        var dragon1 = dragons.addNewRocket("Dragon 1");
        var dragon2 = dragons.addNewRocket("Dragon 2");

        var transit = dragons.addNewMission("Transit");
        dragons.addRocketToMission(redDragon, transit);
        dragons.addRocketToMission(dragonXl, transit);
        dragons.changeRocketStatus(dragonXl, RocketStatus.IN_SPACE);
        dragons.addRocketToMission(falconHeavy, transit);
        dragons.changeRocketStatus(falconHeavy, RocketStatus.IN_SPACE);
        dragons.changeMissionStatus(transit, MissionStatus.IN_PROGRESS);

        var luna1 = dragons.addNewMission("Luna1");
        dragons.addRocketToMission(dragon1, luna1);
        dragons.addRocketToMission(dragon2, luna1);
        dragons.changeRocketStatus(dragon2, RocketStatus.IN_REPAIR);

        var verticalLending = dragons.addNewMission("Vertical Landing");
        var mars = dragons.addNewMission("Mars");
        var luna2 = dragons.addNewMission("Luna2");

        var doubleLanding = dragons.addNewMission("Double Landing");
        dragons.changeMissionStatus(doubleLanding, MissionStatus.ENDED);

        Map<Mission, List<Rocket>> allMissions = dragons.getAllMissions();
        printAllMissions(allMissions, dragons);
    }

    private static void printAllMissions(Map<Mission, List<Rocket>> allMissions, IDragonsRepository dragons) {
        for (Mission m : allMissions.keySet()) {
            System.out.println(". " + m.name() + " - " + dragons.getMissionStatus(m) + " - " + " Dragons:  " + dragons.getMissionRockets(m).size());
            for (Rocket r : dragons.getMissionRockets(m)) {
                System.out.println("    . " + r.name() + " - " + dragons.getRocketStatus(r));
            }
        }
    }

}