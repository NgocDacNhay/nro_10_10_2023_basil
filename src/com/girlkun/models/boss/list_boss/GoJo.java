/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlkun.models.boss.list_boss;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.boss.list_boss.doanh_trai.NinjaClone;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;


public class GoJo extends Boss {
    
    private boolean calledNinja = false;
    private boolean calledNinja2 = false;
    private boolean calledNinja3 = false;


    public GoJo() throws Exception {
        super(BossID.BOSS_GOJO, BossesData.BOSS_GOJO);
    }

    @Override
    public void reward(Player plKill) {
        Service.getInstance().changeFlag(plKill, 8);
    if (Util.isTrue(10, 100)) {
        ItemMap it = new ItemMap(this.zone, 1248, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                this.location.y - 24), plKill.id);
        it.options.add(new Item.ItemOption(77, Util.nextInt(20,40)));
        it.options.add(new Item.ItemOption(103, Util.nextInt(20,40)));
        it.options.add(new Item.ItemOption(50, Util.nextInt(20,40)));
        it.options.add(new Item.ItemOption(5, Util.nextInt(5,20)));
        it.options.add(new Item.ItemOption(209, 1)); 
        Service.getInstance().dropItemMap(this.zone, it);
        } else {ItemMap it2 = new ItemMap(this.zone, 1249, 10, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), plKill.id);
            Service.getInstance().dropItemMap(this.zone, it2);}
    }

//    @Override
//    public void active() {
//        super.active(); //To change body of generated methods, choose Tools | Templates.
//        if (Util.canDoWithTime(st, 2500000)) {
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