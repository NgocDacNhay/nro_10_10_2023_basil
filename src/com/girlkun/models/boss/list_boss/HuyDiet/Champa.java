package com.girlkun.models.boss.list_boss.HuyDiet;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.Random;


public class Champa extends Boss {
    private long lasttimehakai;
    private int timehakai;

    public Champa() throws Exception {
        super(Util.randomBossId(), BossesData.THAN_HUY_DIET_CHAMPA);
    }

    @Override
    public void reward(Player plKill) {
//        byte randomDo = (byte) new Random().nextInt(Manager.itemIds_CT.length); // Có -1 k hiểu
        int[] manhhiem = new int[]{1069,1070,1066, 1067, 1068};
        int[] dats = new int[]{1074, 1075, 1076, 1077, 1078, 1079, 1080, 1081, 1082, 1083, 1084, 1085, 1086};
        int randommanhhiem = new Random().nextInt(manhhiem.length);
        int randomda = new Random().nextInt(dats.length);
           if (Util.isTrue(50, 100)) {
            Service.getInstance().dropItemMap(this.zone, Util.manhTS(zone, manhhiem[randommanhhiem], Util.nextInt(200, 400), this.location.x, this.location.y, plKill.id));
            plKill.diemsk += 1;
        } else {
            Service.getInstance().dropItemMap(this.zone, Util.manhTS(zone, dats[randomda], 1, this.location.x, this.location.y, plKill.id));
            plKill.diemsk += 1;
        }
//        if (Util.isTrue(10, 100)) {           
//                itemMap = Util.ratiItem(zone, Manager.itemIds_ManhTShiem[randomManhTShiem], Util.nextInt(10, 20), this.location.x, this.location.y, plKill.id);        
//        } else {
//            Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, Manager.itemIds_ManhTSthuong[randomManhTSthuong], Util.nextInt(20, 40), this.location.x, this.location.y, plKill.id));
//        }
//        itemMap.options.add(new Item.ItemOption(30, 1));
//        Service.getInstance().dropItemMap(this.zone, ItemMap);
    }
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (Util.isTrue(1, 10000) && plAtt != null) {//tỉ lệ hụt của thiên sứ
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
//    @Override
//    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
//        if (!this.isDie()) {
//            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
//                this.chat("Xí hụt");
//                return 0;
//            }
//            damage = this.nPoint.subDameInjureWithDeff(damage);
//            if (!piercing && effectSkill.isShielding) {
//                if (damage > nPoint.hpMax) {
//                    EffectSkillService.gI().breakShield(this);
//                }
//                damage = 1;
//            }
//            this.nPoint.subHP(damage);
//            if (isDie()) {
//                this.setDie(plAtt);
//                die(plAtt);
//            }
//            return damage;
//        } else {
//            return 0;
//        }
//    }


//    @Override
//    public void active() {
//
//        if (this.typePk == ConstPlayer.NON_PK) {
//            this.changeToTypePK();
//        }
//        this.huydiet();
//        this.attack();
////        super.active(); //To change body of generated methods, choose Tools | Templates.
////        if (Util.canDoWithTime(st, 1000000)) {
////            this.changeStatus(BossStatus.LEAVE_MAP);
//        }
//
//
////    @Override
////    public void joinMap() {
////        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
////        st = System.currentTimeMillis();
////    }
////    private long st;
//
//    private void huydiet() {
//        if (!Util.canDoWithTime(this.lasttimehakai, this.timehakai) || !Util.isTrue(1, 100)) {
//            return;
//        }
//        Player pl = this.zone.getRandomPlayerInMap();
//        if (pl == null || pl.isDie()) {
//            return;
//        }
//        this.nPoint.dameg += (pl.nPoint.dame * 5 / 100);
//        this.nPoint.hpg += (pl.nPoint.hp * 2 / 100);
//        this.nPoint.critg++;
//        this.nPoint.calPoint();
//        PlayerService.gI().hoiPhuc(this, pl.nPoint.hp, 0);
//        pl.injured(null, Util.TamkjllGH(pl.nPoint.hpMax), true, false);
//        Service.getInstance().sendThongBao(pl, "Bạn vừa bị " + this.name + " cho bay màu");
//        this.chat(2, "Hắn ta mạnh quá,coi chừng " + pl.name + ",tên " + this.name + " hắn không giống như những kẻ thù trước đây");
//        this.chat("Thật là yếu ớt " + pl.name);
//        this.lasttimehakai = System.currentTimeMillis();
//        this.timehakai = Util.nextInt(20000, 30000);
//    }



}


