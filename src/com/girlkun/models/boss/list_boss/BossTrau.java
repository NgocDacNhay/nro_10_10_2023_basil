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
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import java.util.Random;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;

/**
 * @@Stole By Arriety
 */
public class BossTrau extends Boss {

    public BossTrau() throws Exception {
        super(BossID.BOSS_TRAU, BossesData.BOSS_TRAU);
    }

    @Override
    public void reward(Player plKill) {
//        int[] itemDos = new int[]{698,695,696};
        int[] NRs = new int[]{569};
//        int randomDo = new Random().nextInt(itemDos.length);
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(50, 100)) {
            if (Util.isTrue(4, 5)) {
                Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 1, this.location.x, this.location.y, plKill.id));
                return;
            }
//            Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, itemDos[randomDo], 20, this.location.x, this.location.y, plKill.id));
        } else {
            Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }
    }
//    @Override
//    public void active() {
//        super.active(); //To change body of generated methods, choose Tools | Templates.
//        if (Util.canDoWithTime(st, 900000)) {
//            this.changeStatus(BossStatus.LEAVE_MAP);
//        }
//    }
     
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;
     @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlHutMp,10 )) {
                this.chat("Haha");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage/2);
            if (plAtt.timedua != null)
                    {
                    this.chat("Sống tử tế lên bạn nhé! Có dưa rồi mà còn tham lam");
                    return 0;
                    }
            if (damage > 100000000)
                    {
                    damage = 100000000;
                    }
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 50;
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
