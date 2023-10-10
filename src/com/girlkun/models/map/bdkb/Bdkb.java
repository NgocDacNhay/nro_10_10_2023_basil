package com.girlkun.models.map.bdkb;
//import com.girlkun.models.boss.bdkb.TrungUyXanhLo;
import com.girlkun.models.clan.Clan;
import com.girlkun.models.map.TrapMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.player.Player;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 *
  @author BTH     public static final int MAX_AVAILABLE = 50;

 *
 */
public class Bdkb { 

    public static final long POWER_CAN_GO_TO_BDKB = 2000000000;

    public static final List<Bdkb> BDKB;
    public static final int MAX_AVAILABLE = 50;
    public static final int TIME_BDKB = 1800000;

    private Player player;

    static {
        BDKB = new ArrayList<>();
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            BDKB.add(new Bdkb(i));
        }
    }

    public int id;
    public int level;
    public final List<Zone> zones;

    public Clan clan;
    public boolean isOpened;
    private long lastTimeOpen;

    public Bdkb(int id) {
        this.id = id;
        this.zones = new ArrayList<>();
    }

    public void update() {
        if (this.isOpened) {
            if (Util.canDoWithTime(lastTimeOpen, TIME_BDKB)) {
                this.finish();
            }
        }
    }

    public void openBanDoKhoBau(Player plOpen, Clan clan, int level) {
        this.level = level;
        this.lastTimeOpen = System.currentTimeMillis();
        this.isOpened = true;
        this.clan = clan;
        this.clan.timeOpenBdkb = this.lastTimeOpen;
        this.clan.playerOpenBdkb = plOpen;
        this.clan.bdkb = this;

        resetBdkb();
        ChangeMapService.gI().goToBdkb(plOpen);
        sendTextBdkb();
    }

    private void resetBdkb() {
        for (Zone zone : zones) {
            for (Mob m : zone.mobs) {
                Mob.initMopBdkb(m, this.level);
                Mob.hoiSinhMob(m);
            }
        }
    }

    //kết thúc bản đồ kho báu
    public void finish() {
        List<Player> plOutDT = new ArrayList();
        for (Zone zone : zones) {
            List<Player> players = zone.getPlayers();
            for (Player pl : players) {
                plOutDT.add(pl);
            }

        }
        for (Player pl : plOutDT) {
            ChangeMapService.gI().changeMapBySpaceShip(pl, 5, -1, 384);
        }
        this.clan.bdkb = null;
        this.clan = null;
        this.isOpened = false;
    }


    public Zone getMapById(int mapId) {
        for (Zone zone : zones) {
            if (zone.map.mapId == mapId) {
                return zone;
            }
        }
        return null;
    }

    public static void addZone(int idBdkb, Zone zone) {
        BDKB.get(idBdkb).zones.add(zone);
    }

    private void sendTextBdkb() {
        for (Player pl : this.clan.membersInGame) {
            ItemTimeService.gI().sendTextBanDoKhoBau(pl);
        }
    }


    
}
