package com.girlkun.models.boss.list_boss;

import com.girlkun.models.boss.*;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

/**
 * @Stole By Arriety
 */
public class NhanBan extends Boss {

    public NhanBan(int bossID, BossData bossData, Zone zone) throws Exception {
        super(bossID, bossData);
        this.zone = zone;
    }

    @Override
    public void reward(Player plKill) {
        //vật phẩm rơi khi diệt boss tâm ma
        ItemMap it = new ItemMap(this.zone, Util.nextInt(1099, 1102), Util.nextInt(10, 50), this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                this.location.y - 24), plKill.id);
        Service.getInstance().dropItemMap(this.zone, it);
     
        int a=0;
        for (int i=0; i<10; i++)
                {
                    ItemMap it1 = new ItemMap(this.zone, 579, 1, this.location.x + a, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), -1);
            Service.getInstance().dropItemMap(this.zone, it1);
            a+=2;
                }
        if(Util.isTrue(33, 100)){
            ItemMap it2 = new ItemMap(this.zone, 611, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                this.location.y - 24), plKill.id);
        Service.getInstance().dropItemMap(this.zone, it2);
        }
    }
    @Override
    public void active() {
        super.active();
    }

    @Override
    public void joinMap() {
        super.joinMap();
    }

    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
    }
}
