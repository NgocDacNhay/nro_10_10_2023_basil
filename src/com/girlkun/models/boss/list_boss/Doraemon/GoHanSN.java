package com.girlkun.models.boss.list_boss.Doraemon;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import java.util.Random;


public class GoHanSN extends Boss {

    public GoHanSN() throws Exception {
        super(BossID.BOSS_GoHanSN, BossesData.BOSS_GoHanSN);
    }
    @Override
    public void reward(Player plKill) {
        int[] NL = new int[]{1250,1232};
        int[] VPRen = new int[]{1259,1266};
        int randomNL = new Random().nextInt(NL.length);
        int randomVPRen = new Random().nextInt(VPRen.length);
        if (Util.isTrue(30, 100)) {
            if (Util.isTrue(4, 5)) {
                Service.getInstance().dropItemMap(this.zone, Util.ratiItem(zone, 1265, (Util.nextInt(1,3)), this.location.x, this.location.y, plKill.id));
                return;
            } else{
            Service.getInstance().dropItemMap(this.zone, Util.ratiItem(zone, VPRen[randomVPRen], 1, this.location.x, this.location.y, plKill.id));
            }
        }else {
            if (Util.isTrue(4, 5)) {
                Service.getInstance().dropItemMap(this.zone, Util.ratiItem(zone, 1249, (Util.nextInt(100,150)), this.location.x, this.location.y, plKill.id));
                return;
            } else{
            Service.getInstance().dropItemMap(this.zone, Util.ratiItem(zone, 1250, (Util.nextInt(100,150)), this.location.x, this.location.y, plKill.id));
            }
        }
    }

//   @Override
//    public void active() {
//        super.active(); //To change body of generated methods, choose Tools | Templates.
//        if(Util.canDoWithTime(st,1500000)){
//            this.changeStatus(BossStatus.LEAVE_MAP);
//        }
//    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st= System.currentTimeMillis();
    }
    private long st;

//    @Override
//    public void wakeupAnotherBossWhenDisappear() {
//        if (this.parentBoss == null) {
//            return;
//        }
//        for (Boss boss : this.parentBoss.bossAppearTogether[this.parentBoss.currentLevel]) {
//            if (boss.id == BossID.XEKO && boss.isDie()) {
//                this.parentBoss.changeToTypePK();
//                return;
//            }
//        }
//        
//    }
       @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (Util.isTrue(20, 100) && plAtt != null) {//tỉ lệ hụt của thiên sứ
            Util.isTrue(this.nPoint.tlNeDon, 100000);
            if (Util.isTrue(1, 100)) {
                this.chat("Hãy để bản năng tự vận động");
                this.chat("Tránh các động tác thừa");
            } else if (Util.isTrue(1, 100)) {
                this.chat("Chậm lại,các ngươi quá nhanh rồi");
                this.chat("Chỉ cần hoàn thiện nó!");
                this.chat("Các ngươi sẽ tránh được mọi nguy hiểm");
            } else if (Util.isTrue(1, 100)) {
                this.chat("Đây chính là bản năng vô cực");
            }
            damage = 0;

        }
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon/100, 100000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage/10;
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

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
