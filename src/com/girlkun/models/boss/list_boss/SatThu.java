package com.girlkun.models.boss.list_boss;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.boss.list_boss.doanh_trai.NinjaClone;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.map.challenge.MartialCongressService;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;


public class SatThu extends Boss {
    private long lastUpdate = System.currentTimeMillis();
    private long timeJoinMap;
    protected Player playerAtt;
    private int timeLive = 200000000;
    public SatThu(Zone zone , int dame, int hp,int id) throws Exception {
        super(id, new BossData(
                "Samurai Bóng Đêm", //name
                ConstPlayer.TRAI_DAT, //gender
                new short[]{123, 124, 125, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
                1000000, //dame
                new long[]{100000000000L}, //hp
                new int[]{169}, //map join
                new int[][]{
                {Skill.DEMON, 3, 1000}, {Skill.DEMON, 6, 1000}, {Skill.DRAGON, 7, 1000}, {Skill.DRAGON, 1, 1000}, {Skill.GALICK, 5, 1000},
                {Skill.KAMEJOKO, 7, 10000}, {Skill.KAMEJOKO, 6, 10000}, {Skill.KAMEJOKO, 5, 10000}, {Skill.KAMEJOKO, 4, 10000}, {Skill.KAMEJOKO, 3, 10000}, {Skill.KAMEJOKO, 2, 10000},{Skill.KAMEJOKO, 1, 10000},
              {Skill.ANTOMIC, 1, 10000},  {Skill.ANTOMIC, 2, 10000},  {Skill.ANTOMIC, 3, 10000},{Skill.ANTOMIC, 4, 10000},  {Skill.ANTOMIC, 5, 10000},{Skill.ANTOMIC, 6, 10000},  {Skill.ANTOMIC, 7, 10000},
                {Skill.MASENKO, 1, 10000}, {Skill.MASENKO, 5, 10000}, {Skill.MASENKO, 6, 10000},},
                new String[]{"|-1|Hiếu Lỏ!!!"}, //text chat 1
                new String[]{"|-1|Đạt Lỏ!!!"}, //text chat 2
                new String[]{"|-1|Nhựt Anh Lỏ!!!"}, //text chat 3
                10
        ));
        this.zone = zone;
    }
    @Override
    public void reward(Player plKill) {
        if (Util.isTrue(1, 200)) {
        ItemMap it = new ItemMap(this.zone, 1248, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                this.location.y - 24), plKill.id);
        it.options.add(new Item.ItemOption(77, Util.nextInt(20,40)));
        it.options.add(new Item.ItemOption(103, Util.nextInt(20,40)));
        it.options.add(new Item.ItemOption(50, Util.nextInt(20,40)));
        it.options.add(new Item.ItemOption(5, Util.nextInt(5,20)));
        it.options.add(new Item.ItemOption(209, 1)); 
        Service.getInstance().dropItemMap(this.zone, it);
        } else {ItemMap it2 = new ItemMap(this.zone, 1249, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), plKill.id);
            Service.getInstance().dropItemMap(this.zone, it2);}
    }
//    @Override
//    public void active() {
//     if (this.typePk == ConstPlayer.NON_PK) {
//            this.changeToTypePK();
//        } 
//       try {
//            switch (this.bossStatus) {
//                case RESPAWN:
//                    this.respawn();
//                    this.changeStatus(BossStatus.JOIN_MAP);
//                case JOIN_MAP:
//                    joinMap();
//                    if (this.zone != null) {
//                        changeStatus(BossStatus.ACTIVE);
//                        timeJoinMap = System.currentTimeMillis();
//                        this.typePk = 3;
//                        MartialCongressService.gI().sendTypePK(playerAtt, this);
//                        PlayerService.gI().changeAndSendTypePK(playerAtt, ConstPlayer.PK_PVP);
//                        this.changeStatus(BossStatus.ACTIVE);
//                    }
//                    break;
//                case ACTIVE:
//                    if (this.playerSkill.prepareTuSat || this.playerSkill.prepareLaze || this.playerSkill.prepareQCKK) {
//                        break;
//                    } else {
//                        this.attack();
//                    }
//                    break;
//            }
//            if (Util.canDoWithTime(lastUpdate, 1000)) {
//                lastUpdate = System.currentTimeMillis();
//                if (timeLive > 0) {
//                    timeLive--;
//                } else {
//                    super.leaveMap();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void active() {
    super.active();
    }
     @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 100)) {
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


