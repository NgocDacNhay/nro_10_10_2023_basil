package com.girlkun.models.map.gas;

import com.girlkun.models.boss.BossID;
//import com.girlkun.models.boss.bdkb.TrungUyXanhLo;
import com.girlkun.models.boss.list_boss.gas.DrLyChee;
import com.girlkun.models.boss.list_boss.gas.HaChiJack;
import com.girlkun.models.item.Item;
//import static com.girlkun.models.map.bando.BanDoKhoBau.TIME_BAN_DO_KHO_BAU;
import static com.girlkun.models.map.gas.Gas.TIME_KHI_GAS;
import com.girlkun.models.player.Player;
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
public class GasService {

    public static GasService i;

    public GasService() {

    }

    public static GasService gI() {
        if (i == null) {
            i = new GasService();
        }
        return i;
    }
    
    public void update(Player player){
        if (player.isPl() == true && player.clan.khiGas != null
                && player.clan.timeOpenKhiGas != 0){
            if(Util.canDoWithTime(player.clan.timeOpenKhiGas, TIME_KHI_GAS)){
                ketthucGas(player);
                player.clan.khiGas = null;
            }
        }
    }
    
     private void kickOutOfGas(Player player) {
        if (MapService.gI().isMapKhiGas(player.zone.map.mapId)) {
            Service.gI().sendThongBao(player, "Trận đại chiến đã kết thúc, tàu vận chuyển sẽ đưa bạn về nhà");
            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
        }
    }

    private void ketthucGas(Player player) {
        List<Player> playersMap = player.zone.getPlayers();
        for (int i = playersMap.size() - 1; i >= 0; i--) {
            Player pl = playersMap.get(i);
            kickOutOfGas(pl);
        }
    }


    public void openKhiGa(Player player, int level) {
        if (level >= 1 && level <= 100000) {
            if (player.clan != null && player.clan.khiGas == null) {
                Item item = InventoryServiceNew.gI().findItemBag(player, 611);
                if (item != null && item.quantity > 0) {
                    Gas gas = null;
                    for (Gas gas1 : Gas.KHI_GAS) {
                        if (!gas1.isOpened) {
                            gas = gas1;
                            break;
                        }
                    }
                    if (gas != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                        InventoryServiceNew.gI().sendItemBags(player);
                        gas.openKhiGa(player, player.clan, level);
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
                            new DrLyChee(player.clan.khiGas.getMapById(148),level,(int) 1,(int) 1,BossID.DR_LYCHEE);
//                           
                            new HaChiJack(player.clan.khiGas.getMapById(148),level,(int) 1,(int) 2, player);
//                            new TrungUyXanhLo(player.clan.banDoKhoBau.getMapById(137), player.clan.banDoKhoBau.level, (int) dame, (int) hp);
                        } catch (Exception e) {
                            Logger.logException(GasService.class, e, "Lỗi init boss");
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Bản đồ kho báu Khí Gas, vui lòng quay lại sau");
                    }
                } else {
                    Service.getInstance().sendThongBao(player, "Yêu cầu có bản đồ khí Gas");
                }
            } else {
                Service.getInstance().sendThongBao(player, "Không thể thực hiện");
            }
        } else {
            Service.getInstance().sendThongBao(player, "Không thể thực hiện");
        }
    }
}
