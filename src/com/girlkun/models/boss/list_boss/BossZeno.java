/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlkun.models.boss.list_boss;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.Random;

/**
 * @@Stole By Arriety
 */
public class BossZeno extends Boss {

    public BossZeno() throws Exception {
        super(BossID.BOSS_ZENO, BossesData.BOSS_ZENO);
    }

    @Override
    public void reward(Player plKill) {
        int[] NRs = new int[]{722, 1079};
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(70, 100)) {
                Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 30, this.location.x, this.location.y, plKill.id));               
            }
        else if (Util.isTrue(25, 100)){
            Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, 1132, 1, this.location.x, this.location.y, plKill.id));
        }
            else {
            Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, 611, 1, this.location.x, this.location.y, plKill.id));
        }
        }
    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 3000000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }
     
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;
     @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 100000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }
}
