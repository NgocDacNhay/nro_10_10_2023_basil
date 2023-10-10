package com.girlkun.models.map.bdkb;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.list_boss.doanh_trai.TrungUyXanhLo;
//import com.girlkun.models.boss.bdkb.TrungUyXanhLo;
import com.girlkun.models.item.Item;
import com.girlkun.models.player.Player;
import static com.girlkun.models.map.bdkb.Bdkb.TIME_BDKB;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;
import java.util.List;

/**
 *
 * @author BTH
 *
 */
public class BdkbService {
 


    public static BdkbService i;

    public BdkbService() {

    }

    public static BdkbService gI() {
        if (i == null) {
            i = new BdkbService();
        }
        return i;
    }
    
    public void update(Player player){
        if (player.isPl() == true && player.clan.bdkb != null
                && player.clan.timeOpenBdkb != 0){
            if(Util.canDoWithTime(player.clan.timeOpenBdkb, TIME_BDKB)){
                ketthucBdkb(player);
                player.clan.bdkb = null;
            }
        }
    }
    
     private void kickOutOfBdkb(Player player) {
        if (MapService.gI().isMapBanDoKhoBau(player.zone.map.mapId)) {
            Service.gI().sendThongBao(player, "Trận đại chiến đã kết thúc, tàu vận chuyển sẽ đưa bạn về nhà");
            ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 250);
        }
    }

    private void ketthucBdkb(Player player) {
        List<Player> playersMap = player.zone.getPlayers();
        for (int i = playersMap.size() - 1; i >= 0; i--) {
            Player pl = playersMap.get(i);
            kickOutOfBdkb(pl);
        }
    }


    public void openBanDoKhoBau(Player player, int level) {
        if (level >= 1 && level <= 100000) {
            if (player.clan != null && player.clan.bdkb == null) {
                Item item = InventoryServiceNew.gI().findItemBag(player, 611);
                if (item != null && item.quantity > 0) {
                    Bdkb bdkb  = null;
                    for (Bdkb bdkb1 : Bdkb.BDKB) {
                        if (!bdkb1.isOpened) {
                            bdkb = bdkb1;
                            break;
                        }
                    }
                    if ( bdkb != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                        InventoryServiceNew.gI().sendItemBags(player);
                        bdkb.openBanDoKhoBau(player, player.clan, level);
                        try {
                            long totalDame = 0;
                            long totalHp = 0;
                            for (Player play : player.clan.membersInGame) {
                                totalDame += play.nPoint.dame;
                                totalHp += play.nPoint.hpMax;
                            }
                            long dame = (totalHp / 20) * (level);
                            long hp = (totalDame * 10) * (level);
                            if (dame >= 2000000000L) {
                                dame = 2000000000L;
                            }
                            if (hp >= 2000000000L) {
                                hp = 2000000000L;
                            }
                            new TrungUyXanhLo(player.clan.bdkb.getMapById(137), (int) dame, (int) hp);
                        } catch (Exception e) {
                            Logger.logException(BdkbService.class, e, "Lỗi init boss");
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Bản đồ kho báu, vui lòng quay lại sau");
                    }
                } else {
                    Service.getInstance().sendThongBao(player, "Yêu cầu có bản đồ kho báu");
                }
            } else {
                Service.getInstance().sendThongBao(player, "Không thể thực hiện");
            }
        } else {
            Service.getInstance().sendThongBao(player, "Không thể thực hiện");
        }
    }


    
}
