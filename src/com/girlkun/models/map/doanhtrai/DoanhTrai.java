package com.girlkun.models.map.doanhtrai;

import com.girlkun.models.clan.Clan;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.player.Player;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;


@Data
public class DoanhTrai {
    
    //bang hội đủ số người mới đc mở
    public static final List<DoanhTrai> DOANH_TRAI;
    public static final int N_PLAYER_CLAN = 0;
    //số người đứng cùng khu
    public static final int N_PLAYER_MAP = 0;
    public static final int AVAILABLE = 150;
    public static final int TIME_DOANH_TRAI = 1800000;
    
    public boolean timePickDragonBall;

    static {
        DOANH_TRAI = new ArrayList<>();
        for (int i = 0; i < AVAILABLE; i++) {
            DOANH_TRAI.add(new DoanhTrai(i));
        }
    }

    private int id;
    private List<Zone> zones;
    private Clan clan;
    public boolean isOpened;
    
    private long lastTimeOpen;

    public DoanhTrai(int id) {
        this.id = id;
        this.zones = new ArrayList<>();
    }

    public void addZone(Zone zone) {
        this.zones.add(zone);
    }

    public Zone getMapById(int mapId) {
        for (Zone zone : this.zones) {
            if (zone.map.mapId == mapId) {
                return zone;
            }
        }
        return null;
    }

    public void openDoanhTrai(Player player) {
        this.lastTimeOpen = System.currentTimeMillis();
        this.clan = player.clan;
        player.clan.doanhTrai = this;
        player.clan.playerOpenDoanhTrai = player.name;
        player.clan.lastTimeOpenDoanhTrai = this.lastTimeOpen;
        player.clan.timeOpenDoanhTrai = this.lastTimeOpen;
        //Khởi tạo quái, boss
        this.init();
        //Đưa thành viên vào doanh trại
        for (Player pl : player.clan.membersInGame) {
            if (pl == null || pl.zone == null || !player.zone.equals(pl.zone)) {
                continue;
            }
            ChangeMapService.gI().changeMapInYard(pl, 53, -1, 60);
            ItemTimeService.gI().sendTextDoanhTrai(pl);
        }
    }

    private void init(){
        long totalDame = 0;
        long totalHp = 0;
        for(Player pl : this.clan.membersInGame){
            totalDame += pl.nPoint.dame;
            totalHp += pl.nPoint.hpMax;
        }
        
        
        //Hồi sinh quái
        for(Zone zone : this.zones){
            for(Mob mob : zone.mobs){
                mob.point.dame = Util.TamkjllGH(totalHp / 20);
                mob.point.maxHp = Util.TamkjllGH(totalDame * 20);
                mob.hoiSinh();
            }
        }
    }

public void DropNgocRong() {
        for (Zone zone : zones) {
            ItemMap itemMap = null;

            switch (zone.map.mapId) {
                case 53:
                    itemMap = new ItemMap(zone, Util.nextInt(17,20), 1, 917, 384, -1);
                    itemMap.isDoanhTraiBall = true;
                    break;
                case 58:
                    itemMap = new ItemMap(zone, Util.nextInt(17, 20), 1, 658, 336, -1);
                    itemMap.isDoanhTraiBall = true;
                    break;
                case 59:
                    itemMap = new ItemMap(zone, Util.nextInt(17, 20), 1, 675, 240, -1);
                    itemMap.isDoanhTraiBall = true;
                    break;
                case 60:
                    itemMap = new ItemMap(zone, Util.nextInt(17, 20), 1, Util.nextInt(725, 1241), 384, -1);
                    itemMap.isDoanhTraiBall = true;
                    break;
                case 61:
                    itemMap = new ItemMap(zone, Util.nextInt(17, 20), 1, 789, 264, -1);
                    itemMap.isDoanhTraiBall = true;
                    break;
                case 62:
                    itemMap = new ItemMap(zone, Util.nextInt(17, 20), 1, Util.nextInt(197, 1294), 384, -1);
                    itemMap.isDoanhTraiBall = true;
                    break;
                case 55:
                    itemMap = new ItemMap(zone, Util.nextInt(17, 20), 1, 422, 288, -1);
                    itemMap.isDoanhTraiBall = true;
                    break;
                case 56:
                    itemMap = new ItemMap(zone, Util.nextInt(17, 20), 1, 789, 312, -1);
                    itemMap.isDoanhTraiBall = true;
                    break;
                case 54:
                    itemMap = new ItemMap(zone, Util.nextInt(17, 20), 1, 211, 1228, -1);
                    itemMap.isDoanhTraiBall = true;
                    break;
            }
        }

    }

    private void sendTextDoanhTrai() {
        for (Player pl : this.clan.membersInGame) {
            ItemTimeService.gI().sendTextDoanhTrai(pl);
        }
    }
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
