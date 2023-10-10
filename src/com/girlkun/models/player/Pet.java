package com.girlkun.models.player;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.services.MapService;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.skill.Skill;
import com.girlkun.utils.SkillUtil;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import com.girlkun.network.io.Message;
import com.girlkun.server.Manager;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.SkillService;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.services.func.UseItem;
import com.girlkun.utils.TimeUtil;

public class Pet extends Player {

    private static final short ARANGE_CAN_ATTACK = 300;
    private static final short ARANGE_ATT_SKILL1 = 30;
    private static final short ARANGE_ATT_SKILL2 = 200;

    private static final short[][] PET_ID = {{285, 286, 287}, {288, 289, 290}, {282, 283, 284}, {304, 305, 303}};

    public static final byte FOLLOW = 0;
    public static final byte PROTECT = 1;
    public static final byte ATTACK = 2;
    public static final byte GOHOME = 3;
    public static final byte FUSION = 4;

    public Player master;
    public byte status = 0;

    public byte typePet;
    public boolean isTransform;

    public long lastTimeDie;

    private boolean goingHome;

    private Mob mobAttack;
    private Player playerAttack;

    private static final int TIME_WAIT_AFTER_UNFUSION = 5000;
    private long lastTimeUnfusion;

    public byte getStatus() {
        return this.status;
    }

    public Pet(Player master) {
        this.master = master;
        this.isPet = true;
    }

    public void changeStatus(byte status) {
        if (goingHome || master.fusion.typeFusion != 0 || (this.isDie() && status == FUSION)) {
            Service.getInstance().sendThongBao(master, "Không thể thực hiện");
            return;
        }
        Service.getInstance().chatJustForMe(master, this, getTextStatus(status));
        if (status == GOHOME) {
            goHome();
        } else if (status == FUSION) {
            fusion(false);
        }
        this.status = status;
    }

    public void joinMapMaster() {
        if (status != GOHOME && status != FUSION && !isDie()) {
            this.location.x = master.location.x + Util.nextInt(-10, 10);
            this.location.y = master.location.y;
            ChangeMapService.gI().goToMap(this, master.zone);
            this.zone.load_Me_To_Another(this);
        }
    }

    public void goHome() {
        if (this.status == GOHOME) {
            return;
        }
        goingHome = true;
        new Thread(() -> {
            try {
                Pet.this.status = Pet.ATTACK;
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            ChangeMapService.gI().goToMap(this, MapService.gI().getMapCanJoin(this, master.gender + 21, -1));
            this.zone.load_Me_To_Another(this);
            Pet.this.status = Pet.GOHOME;
            goingHome = false;
        }).start();
    }

    private String getTextStatus(byte status) {
        switch (status) {
            case FOLLOW:
                return "Ok con theo sư phụ";
            case PROTECT:
                return "Á..À!!Mày Dám Đánh Sư Phụ Tao À, con sẽ bảo vệ sư phụ";
            case ATTACK:
                return "Ok sư phụ, Thích Thì Đấm, Đụng Thì Chạm";
            case GOHOME:
                return "Ok con về, bibi sư phụ";
            default:
                return "";
        }
    }
    
      public void fusion2(boolean porata) {
        if (this.isDie()) {
            Service.getInstance().sendThongBao(master, "Không thể thực hiện");
            return;
        }
        if (Util.canDoWithTime(lastTimeUnfusion, TIME_WAIT_AFTER_UNFUSION)) {
            if (porata) {
                master.fusion.typeFusion = ConstPlayer.HOP_THE_PORATA2;
            }
            this.status = FUSION;
            ChangeMapService.gI().exitMap(this);
            fusionEffect(master.fusion.typeFusion);
            Service.getInstance().Send_Caitrang(master);
            master.nPoint.calPoint();
            master.nPoint.setFullHpMp();
            Service.getInstance().point(master);
        } else {
            Service.getInstance().sendThongBao(this.master, "Vui lòng đợi "
                    + TimeUtil.getTimeLeft(lastTimeUnfusion, TIME_WAIT_AFTER_UNFUSION / 1000) + " nữa");
        }
    }
      public void fusion3(boolean porata) {
        if (this.isDie()) {
            Service.getInstance().sendThongBao(master, "Không thể thực hiện");
            return;
        }
        if (Util.canDoWithTime(lastTimeUnfusion, TIME_WAIT_AFTER_UNFUSION)) {
            if (porata) {
                master.fusion.typeFusion = ConstPlayer.HOP_THE_PORATA3;
            }
            this.status = FUSION;
            ChangeMapService.gI().exitMap(this);
            fusionEffect(master.fusion.typeFusion);
            Service.getInstance().Send_Caitrang(master);
            master.nPoint.calPoint();
            master.nPoint.setFullHpMp();
            Service.getInstance().point(master);
        } else {
            Service.getInstance().sendThongBao(this.master, "Vui lòng đợi "
                    + TimeUtil.getTimeLeft(lastTimeUnfusion, TIME_WAIT_AFTER_UNFUSION / 1000) + " nữa");
        }
    }

    public void fusion(boolean porata) {
        if (this.isDie()) {
            Service.getInstance().sendThongBao(master, "Không thể thực hiện");
            return;
        }
        if (Util.canDoWithTime(lastTimeUnfusion, TIME_WAIT_AFTER_UNFUSION)) {
            if (porata) {
                master.fusion.typeFusion = ConstPlayer.HOP_THE_PORATA;
            } else {
                master.fusion.lastTimeFusion = System.currentTimeMillis();
                master.fusion.typeFusion = ConstPlayer.LUONG_LONG_NHAT_THE;
                ItemTimeService.gI().sendItemTime(master, master.gender == ConstPlayer.NAMEC ? 3901 : 3790, Fusion.TIME_FUSION / 1000);
            }
            this.status = FUSION;
            ChangeMapService.gI().exitMap(this);
            fusionEffect(master.fusion.typeFusion);
            Service.getInstance().Send_Caitrang(master);
            master.nPoint.calPoint();
            master.nPoint.setFullHpMp();
            Service.getInstance().point(master);
        } else {
            Service.getInstance().sendThongBao(this.master, "Vui lòng đợi "
                    + TimeUtil.getTimeLeft(lastTimeUnfusion, TIME_WAIT_AFTER_UNFUSION / 1000) + " nữa");
        }
    }

    public void unFusion() {
        master.fusion.typeFusion = 0;
        this.status = PROTECT;
        Service.getInstance().point(master);
        joinMapMaster();
        fusionEffect(master.fusion.typeFusion);
        Service.getInstance().Send_Caitrang(master);
        Service.getInstance().point(master);
        this.lastTimeUnfusion = System.currentTimeMillis();
    }

    private void fusionEffect(int type) {
        Message msg;
        try {
            msg = new Message(125);
            msg.writer().writeByte(type);
            msg.writer().writeInt((int) master.id);
            Service.getInstance().sendMessAllPlayerInMap(master, msg);
            msg.cleanup();
        } catch (Exception e) {

        }
    }

    public long lastTimeMoveIdle;
    private int timeMoveIdle;
    public boolean idle;

    private void moveIdle() {
        if (status == GOHOME || status == FUSION) {
            return;
        }
        if (idle && Util.canDoWithTime(lastTimeMoveIdle, timeMoveIdle)) {
            int dir = this.location.x - master.location.x <= 0 ? -1 : 1;
            PlayerService.gI().playerMove(this, master.location.x
                    + Util.nextInt(dir == -1 ? 30 : -50, dir == -1 ? 50 : 30), master.location.y);
            lastTimeMoveIdle = System.currentTimeMillis();
            timeMoveIdle = Util.nextInt(5000, 8000);
        }
    }

    private long lastTimeMoveAtHome;
    private byte directAtHome = -1;

    @Override
    public void update() {
        try {
            super.update();
            increasePoint(); //cộng chỉ số
            updatePower(); //check mở skill...
            if (isDie()) {
                if (System.currentTimeMillis() - lastTimeDie > 15000) {
                    Service.getInstance().hsChar(this, nPoint.hpMax, nPoint.mpMax);
                } else {
                    return;
                }
            }

            if (justRevived && this.zone == master.zone) {
                Service.getInstance().chatJustForMe(master, this, "Sư phụ ơi, con đây nè!");
                justRevived = false;
            }

            if (this.zone == null || this.zone != master.zone) {
                joinMapMaster();
            }
            if (master.isDie() || this.isDie() || effectSkill.isHaveEffectSkill()) {
                return;
            }

            moveIdle();
            switch (status) {
                case FOLLOW:
                    followMaster(10);
                    break;
                case PROTECT:
                    if (useSkill3() || useSkill4()) { //|| useSkill5()) {
                        break;
                    }
                    mobAttack = findMobAttack();
                    if (mobAttack != null) {
                        int disToMob = Util.getDistance(this, mobAttack);
                        if (disToMob <= ARANGE_ATT_SKILL1) {
                            //đấm
                            this.playerSkill.skillSelect = getSkill(1);
                            if (SkillService.gI().canUseSkillWithCooldown(this)) {
                                if (SkillService.gI().canUseSkillWithMana(this)) {
                                    PlayerService.gI().playerMove(this, mobAttack.location.x + Util.nextInt(-15, 15), mobAttack.location.y);
                                    SkillService.gI().useSkill(this, playerAttack, mobAttack,null);
                                } else {
                                    askPea();
                                    UseItem.eatPea(this.master);
                                }
                            }
                        } else if (disToMob <= 200) {
                            //chưởng
                            this.playerSkill.skillSelect = getSkill(2);
                            if (this.playerSkill.skillSelect.skillId != -1) {
                                if (SkillService.gI().canUseSkillWithCooldown(this)) {
                                    if (SkillService.gI().canUseSkillWithMana(this)) {
                                        SkillService.gI().useSkill(this, playerAttack, mobAttack,null);
                                    } else {
                                        askPea();
                                        UseItem.eatPea(this.master);
                                    }
                                }
                            }
                        }

                    } else {
                        idle = true;
                    }

                    break;
                case ATTACK:
                    if (useSkill3() || useSkill4() ) { //|| useSkill5()) {
                        break;
                    }
                    mobAttack = findMobAttack();
                    if (mobAttack != null) {
                        int disToMob = Util.getDistance(this, mobAttack);
                        if (disToMob <= ARANGE_ATT_SKILL1/2) {
                            this.playerSkill.skillSelect = getSkill(1);
                            if (SkillService.gI().canUseSkillWithCooldown(this)) {
                                if (SkillService.gI().canUseSkillWithMana(this)) {
                                    PlayerService.gI().playerMove(this, mobAttack.location.x + Util.nextInt(-60, 60), mobAttack.location.y);
                                    SkillService.gI().useSkill(this, playerAttack, mobAttack,null);
                                } else {
                                    askPea();
                                    UseItem.eatPea(this.master);
                                }
                            }
                        } else {
                            this.playerSkill.skillSelect = getSkill(2);
                            if (this.playerSkill.skillSelect.skillId != -1) {
                                if (SkillService.gI().canUseSkillWithMana(this)) {
                                    SkillService.gI().useSkill(this, playerAttack, mobAttack,null);
                                }
                            } else {
                                this.playerSkill.skillSelect = getSkill(1);
                                if (SkillService.gI().canUseSkillWithCooldown(this)) {
                                    if (SkillService.gI().canUseSkillWithMana(this)) {
                                        PlayerService.gI().playerMove(this, mobAttack.location.x + Util.nextInt(-20, 20), mobAttack.location.y);
                                        SkillService.gI().useSkill(this, playerAttack, mobAttack,null);
                                    } else {
                                        askPea();
                                        UseItem.eatPea(this.master);
                                    }
                                }
                            }
                        }

                    } else {
                        idle = true;
                    }
                    break;

                case GOHOME:
                    if (this.zone != null && (this.zone.map.mapId == 21 || this.zone.map.mapId == 22 || this.zone.map.mapId == 23)) {
                        if (System.currentTimeMillis() - lastTimeMoveAtHome <= 5000) {
                            return;
                        } else {
                            if (this.zone.map.mapId == 21) {
                                if (directAtHome == -1) {

                                    PlayerService.gI().playerMove(this, 250, 336);
                                    directAtHome = 1;
                                } else {
                                    PlayerService.gI().playerMove(this, 200, 336);
                                    directAtHome = -1;
                                }
                            } else if (this.zone.map.mapId == 22) {
                                if (directAtHome == -1) {
                                    PlayerService.gI().playerMove(this, 500, 336);
                                    directAtHome = 1;
                                } else {
                                    PlayerService.gI().playerMove(this, 452, 336);
                                    directAtHome = -1;
                                }
                            } else if (this.zone.map.mapId == 22) {
                                if (directAtHome == -1) {
                                    PlayerService.gI().playerMove(this, 250, 336);
                                    directAtHome = 1;
                                } else {
                                    PlayerService.gI().playerMove(this, 200, 336);
                                    directAtHome = -1;
                                }
                            }
                            Service.getInstance().chatJustForMe(master, this, "H2O + C12H22O11 -> Uống ngọt lắm sư phụ ạ!");
                            lastTimeMoveAtHome = System.currentTimeMillis();
                        }
                    }
                    break;
            }
        } catch (Exception e) {
//            Logger.logException(Pet.class, e);
        }
    }

    private long lastTimeAskPea;

    public void askPea() {
        if (Util.canDoWithTime(lastTimeAskPea, 10000)) {
            Service.getInstance().chatJustForMe(master, this, "Sư phụ ơi cho con đậu thần đi, con đói sắp chết rồi !!");
            lastTimeAskPea = System.currentTimeMillis();
        }
    }

    private int countTTNL;

    private boolean useSkill3() {
        try {
            playerSkill.skillSelect = getSkill(3);
            if (playerSkill.skillSelect.skillId == -1) {
                return false;
            }
            switch (this.playerSkill.skillSelect.template.id) {
                case Skill.THAI_DUONG_HA_SAN:
                    if (SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)) {
                        SkillService.gI().useSkill(this, null, null,null);
                        Service.getInstance().chatJustForMe(master, this, "Bất ngờ chưa ông già");
                        return true;
                    }
                    return false;
                case Skill.TAI_TAO_NANG_LUONG:
                    if (this.effectSkill.isCharging && this.countTTNL < Util.nextInt(3, 5)) {
                        this.countTTNL++;
                        return true;
                    }
                    if (SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)
                            && (this.nPoint.getCurrPercentHP() <= 20 || this.nPoint.getCurrPercentMP() <= 20)) {
                        SkillService.gI().useSkill(this, null, null,null);
                        this.countTTNL = 0;
                        return true;
                    }
                    return false;
                case Skill.KAIOKEN:
                    if (SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)) {
                        mobAttack = this.findMobAttack();
                        if (mobAttack == null) {
                            return false;
                        }
                        int dis = Util.getDistance(this, mobAttack);
                        if (dis > ARANGE_ATT_SKILL1) {
                            PlayerService.gI().playerMove(this, mobAttack.location.x, mobAttack.location.y);
                        } else {
                            if (SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)) {
                                PlayerService.gI().playerMove(this, mobAttack.location.x + Util.nextInt(-20, 20), mobAttack.location.y);
                            }
                        }
                        SkillService.gI().useSkill(this, playerAttack, mobAttack,null);
                        getSkill(1).lastTimeUseThisSkill = System.currentTimeMillis();
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private boolean useSkill4() {
        try {
            this.playerSkill.skillSelect = getSkill(4);
            if (this.playerSkill.skillSelect.skillId == -1) {
                return false;
            }
            switch (this.playerSkill.skillSelect.template.id) {
                case Skill.BIEN_KHI:
                    if (!this.effectSkill.isMonkey && SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)) {
                        SkillService.gI().useSkill(this, null, null,null);
                        return true;
                    }
                    return false;
                case Skill.KHIEN_NANG_LUONG:
                    if (!this.effectSkill.isShielding && SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)) {
                        SkillService.gI().useSkill(this, null, null,null);
                        return true;
                    }
                    return false;
                case Skill.DE_TRUNG:
                    if (this.mobMe == null && SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)) {
                        SkillService.gI().useSkill(this, null, null,null);
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

//========================BETA SKILL5=====================
//    private boolean useSkill5() {
//        try {
//            this.playerSkill.skillSelect = getSkill(5);
//            if (this.playerSkill.skillSelect.skillId == -1) {
//                return false;
//            }
//            switch (this.playerSkill.skillSelect.template.id) {
//                case Skill.THOI_MIEN:
//                    if (!this.effectSkill.isThoiMien && SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)) {
//                        SkillService.gI().useSkill(this, null, null);
//                        return true;
//                    }
//                    return false;
//                case Skill.DICH_CHUYEN_TUC_THOI:
//                    if (!this.effectSkill.isBlindDCTT && SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)) {
//                        SkillService.gI().useSkill(this, null, null);
//                        return true;
//                    }
//                    return false;
//                case Skill.SOCOLA:
//                    if (this.effectSkill.isSocola && SkillService.gI().canUseSkillWithCooldown(this) && SkillService.gI().canUseSkillWithMana(this)) {
//                        SkillService.gI().useSkill(this, null, null);
//                        return true;
//                    }
//                    return false;
//                default:
//                    return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }

    //====================================================
    private long lastTimeIncreasePoint;

    private void increasePoint() {
        if (this.nPoint != null && Util.canDoWithTime(lastTimeIncreasePoint, 500)) {
            if (Util.isTrue(1, 100)) {
                this.nPoint.increasePointPet((byte) 3, (short) 1);
            } else {
                byte type = (byte) Util.nextInt(0, 2);
                short point = (short) Util.nextInt(100);
                this.nPoint.increasePointPet(type, (short)point);
            }
            lastTimeIncreasePoint = System.currentTimeMillis();
        }
    }

    public void followMaster() {
        if (this.isDie() || effectSkill.isHaveEffectSkill()) {
            return;
        }
        switch (this.status) {
            case ATTACK:
                if ((mobAttack != null && Util.getDistance(this, master) <= 1500)) {
                    break;
                }
            case FOLLOW:
            case PROTECT:
                followMaster(10);
                break;
        }
    }

    private void followMaster(int dis) {
        int mX = master.location.x;
        int mY = master.location.y;
        int disX = this.location.x - mX;
        if (Math.sqrt(Math.pow(mX - this.location.x, 2) + Math.pow(mY - this.location.y, 2)) >= dis) {
            if (disX < 0) {
                this.location.x = mX - Util.nextInt(0, dis);
            } else {
                this.location.x = mX + Util.nextInt(0, dis);
            }
            this.location.y = mY;
            PlayerService.gI().playerMove(this, this.location.x, this.location.y);
        }
    }

    public short getAvatar() {
        if (this.typePet == 1) {
            return 297; // Ma Bư
        } else if (this.typePet == 2) {
            return 879; // Black Gohan Rose
        } else if (this.typePet == 3) {
            return 1318; // Supper Broly
        } else if (this.typePet == 3) {
            return 946;
            }else if (this.typePet == 4) {
            return 264 ; // Xên Con
            } else if (this.typePet == 5) {
            return 790 ; // Đôremon
            } else if (this.typePet == 6) {
            return 802 ; // XuKa
            } else if (this.typePet == 7) {
            return 850 ; // Xêko
            } else if (this.typePet == 8) {
            return 847 ; //Chaien
            } else if (this.typePet == 9) {
            return 844 ; // Nobita 
            } else if (this.typePet == 10) {
            return 1270 ; // Captain America
            } else if (this.typePet == 11) {
            return 1276 ; // Thần Thor
            } else if (this.typePet == 12) {
            return 1273 ; //Spider Man
            } else if (this.typePet == 13) {
            return 1279 ; // Thanos
            } else if (this.typePet == 14) {
            return 1282 ; // Dr.Strange
            } else if (this.typePet == 15) {
            return 1288 ; // Gohan SSJ5
            } else if (this.typePet == 16) {
            return 2030 ; // Kamioren
            } else if (this.typePet == 17) {
            return 1312 ; // Super Zamasu White
            } else if (this.typePet == 18) {
            return 1315 ; // Hearts Gold
            } else if (this.typePet == 19) {
            return 2024 ; // Cumber
            } else if (this.typePet == 20) {
            return 2027 ; //Super Cumber
            } else if (this.typePet == 21) {
            return 1213 ; // Zeno
            } else if (this.typePet == 22) {
            return 1216 ; // Thần Zeno 1
            } else if (this.typePet == 23) {
            return 1219 ; //Thần Zeno 2
            } else if (this.typePet == 24) {
            return 1222 ; // Germa 1
            } else if (this.typePet == 25) {
            return 1225 ; // Germa 2
            } else if (this.typePet == 26) {
            return 1228 ; //Thần Zeno 3
            } else if (this.typePet == 27) {
            return 1231 ; // Goku Vô Cực
            } else if (this.typePet == 28) {
            return 1234 ; //Jiren
            } else if (this.typePet == 29) {
            return 1258 ; //Gojo
            } else if (this.typePet == 30) {
            return 903; // Zamasu
            } else if (this.typePet == 31) {
            return 508 ; // Bill
            } else if (this.typePet == 32) {
            return 838 ; //  Whis
            } else if (this.typePet == 33) {
            return 1324 ; // Baby Vegeta
        }else {
            return PET_ID[3][this.gender];
        }
    }

    @Override
    public short getHead() {
        if (effectSkill.isMonkey) {
            return (short) ConstPlayer.HEADMONKEY[effectSkill.levelMonkey - 1];
        } else if (effectSkill.isSocola) {
            return 412;
        } else if (this.typePet == 1) {
            return 297;
        } else if (this.typePet == 2) {
            return 879;
        }else if (this.typePet == 3) {
            return 1318;
        }else if (this.typePet == 4) {
            return 264 ;
            } else if (this.typePet == 5) {
            return 790 ;
            } else if (this.typePet == 6) {
            return 802 ;
            } else if (this.typePet == 7) {
            return 850 ;
            } else if (this.typePet == 8) {
            return 847 ;
            } else if (this.typePet == 9) {
            return 844 ;
            } else if (this.typePet == 10) {
            return 1270 ;
            } else if (this.typePet == 11) {
            return 1276 ;
            } else if (this.typePet == 12) {
            return 1273 ;
            } else if (this.typePet == 13) {
            return 1279 ;
            } else if (this.typePet == 14) {
            return 1282 ;
            } else if (this.typePet == 15) {
            return 1288 ;
            } else if (this.typePet == 16) {
            return 2030 ;
            } else if (this.typePet == 17) {
            return 1312 ;
            } else if (this.typePet == 18) {
            return 1315 ;
            } else if (this.typePet == 19) {
            return 2024 ;
            } else if (this.typePet == 20) {
            return 2027 ;
            } else if (this.typePet == 21) {
            return 1213 ;
            } else if (this.typePet == 22) {
            return 1216 ;
            } else if (this.typePet == 23) {
            return 1219 ;
            } else if (this.typePet == 24) {
            return 1222 ;
            } else if (this.typePet == 25) {
            return 1222 ;
            } else if (this.typePet == 26) {
            return 1228 ;
            } else if (this.typePet == 27) {
            return 1231 ;
            } else if (this.typePet == 28) {
            return 1234 ;
            } else if (this.typePet == 29) {
            return 1258 ;
            } else if (this.typePet == 30) {
            return 903;
            } else if (this.typePet == 31) {
            return 508 ;
            } else if (this.typePet == 32) {
            return 838 ;
            } else if (this.typePet == 33) {
            return 1324 ;
        } else if (inventory.itemsBody.get(5).isNotNullItem()) {
            int part = inventory.itemsBody.get(5).template.head;
            if (part != -1) {
                return (short) part;
            }
        }
        if (this.nPoint.power < 1500000) {
            return PET_ID[this.gender][0];
        } else {
            return PET_ID[3][this.gender];
        }
    }

    @Override
    public short getBody() {
        if (effectSkill.isMonkey) {
            return 193;
        } else if (effectSkill.isSocola) {
            return 413;
        } else if (this.typePet == 1 && !this.isTransform) {
            return 298;
        } else if (this.typePet == 2 && !this.isTransform) {
            return 880;
        }else if (this.typePet == 3 && !this.isTransform) {
            return 1319;
        } else if (this.typePet == 4 && !this.isTransform) {
            return 265 ;
            } else if (this.typePet == 5 && !this.isTransform) {
            return 791 ;
            } else if (this.typePet == 6&& !this.isTransform) {
            return 803 ;
            } else if (this.typePet == 7&& !this.isTransform) {
            return 851  ;
            } else if (this.typePet == 8&& !this.isTransform) {
            return 848 ;
            } else if (this.typePet == 9&& !this.isTransform) {
            return 845 ;
            } else if (this.typePet == 10&& !this.isTransform) {
            return 1271 ;
            } else if (this.typePet == 11&& !this.isTransform) {
            return 1277 ;
            } else if (this.typePet == 12&& !this.isTransform) {
            return 1274 ;
            } else if (this.typePet == 13&& !this.isTransform) {
            return 1280 ;
            } else if (this.typePet == 14&& !this.isTransform) {
            return 1283 ;
            } else if (this.typePet == 15&& !this.isTransform) {
            return 1289 ;
            } else if (this.typePet == 16&& !this.isTransform) {
            return 2031 ;
            } else if (this.typePet == 17&& !this.isTransform) {
            return 1313 ;
            } else if (this.typePet == 18&& !this.isTransform) {
            return 1316 ;
            } else if (this.typePet == 19&& !this.isTransform) {
            return 2025 ;
            } else if (this.typePet == 20&& !this.isTransform) {
            return 2028 ;
            } else if (this.typePet == 21&& !this.isTransform) {
            return 1214 ;
            } else if (this.typePet == 22&& !this.isTransform) {
            return 1217 ;
            } else if (this.typePet == 23&& !this.isTransform) {
            return 1220 ;
            } else if (this.typePet == 24&& !this.isTransform) {
            return 1223 ;
            } else if (this.typePet == 25&& !this.isTransform) {
            return 1223 ;
            } else if (this.typePet == 26&& !this.isTransform) {
            return 1229 ;
            } else if (this.typePet == 27&& !this.isTransform) {
            return 1232 ;
            } else if (this.typePet == 28&& !this.isTransform) {
            return 1235 ;
            } else if (this.typePet == 29&& !this.isTransform) {
            return 1259 ;
            } else if (this.typePet == 30&& !this.isTransform) {
            return 904 ;
            } else if (this.typePet == 31&& !this.isTransform) {
            return 509  ;
            } else if (this.typePet == 32&& !this.isTransform) {
            return 839  ;
            } else if (this.typePet == 33&& !this.isTransform) {
            return 1325  ;
            
        } else if (inventory.itemsBody.get(5).isNotNullItem()) {
            int body = inventory.itemsBody.get(5).template.body;
            if (body != -1) {
                return (short) body;
            }
        }
        if (inventory.itemsBody.get(0).isNotNullItem()) {
            return inventory.itemsBody.get(0).template.part;
        }
        if (this.nPoint.power < 1500000) {
            return PET_ID[this.gender][1];
        } else {
            return (short) (gender == ConstPlayer.NAMEC ? 59 : 57);
        }
    }

    @Override
    public short getLeg() {
        if (effectSkill.isMonkey) {
            return 194;
        } else if (effectSkill.isSocola) {
            return 414;
        } else if (this.typePet == 1 && !this.isTransform) {
            return 299;
        } else if (this.typePet == 2 && !this.isTransform) {
            return 881;
        }else if (this.typePet == 3 && !this.isTransform) {
            return 1320;
        } else if (this.typePet == 4 && !this.isTransform) {
            return 266;
            } else if (this.typePet == 5 && !this.isTransform) {
            return 792;
            } else if (this.typePet == 6&& !this.isTransform) {
            return 804;
            } else if (this.typePet == 7&& !this.isTransform) {
            return 852;
            } else if (this.typePet == 8&& !this.isTransform) {
            return 849;
            } else if (this.typePet == 9&& !this.isTransform) {
            return 846;
            } else if (this.typePet == 10&& !this.isTransform) {
            return 1272;
            } else if (this.typePet == 11&& !this.isTransform) {
            return 1278;
            } else if (this.typePet == 12&& !this.isTransform) {
            return 1275;
            } else if (this.typePet == 13&& !this.isTransform) {
            return 1281;
            } else if (this.typePet == 14&& !this.isTransform) {
            return 1284;
            } else if (this.typePet == 15&& !this.isTransform) {
            return 1290;
            } else if (this.typePet == 16&& !this.isTransform) {
            return 2032;
            } else if (this.typePet == 17&& !this.isTransform) {
            return 1314;
            } else if (this.typePet == 18&& !this.isTransform) {
            return 1317;
            } else if (this.typePet == 19&& !this.isTransform) {
            return 2026;
            } else if (this.typePet == 20&& !this.isTransform) {
            return 2029;
            } else if (this.typePet == 21&& !this.isTransform) {
            return 1215;
            } else if (this.typePet == 22&& !this.isTransform) {
            return 1218;
            } else if (this.typePet == 23&& !this.isTransform) {
            return 1221;
            } else if (this.typePet == 24&& !this.isTransform) {
            return 1224;
            } else if (this.typePet == 25&& !this.isTransform) {
            return 1224;
            } else if (this.typePet == 26&& !this.isTransform) {
            return 1230;
            } else if (this.typePet == 27&& !this.isTransform) {
            return 1233;
            } else if (this.typePet == 28&& !this.isTransform) {
            return 1236;
            } else if (this.typePet == 29&& !this.isTransform) {
            return 1260;
            } else if (this.typePet == 30&& !this.isTransform) {
            return 905;
            } else if (this.typePet == 31&& !this.isTransform) {
            return 510;
            } else if (this.typePet == 32&& !this.isTransform) {
            return 840 ;
            } else if (this.typePet == 33&& !this.isTransform) {
            return 1326;
        } else if (inventory.itemsBody.get(5).isNotNullItem()) {
            int leg = inventory.itemsBody.get(5).template.leg;
            if (leg != -1) {
                return (short) leg;
            }
        }
        if (inventory.itemsBody.get(1).isNotNullItem()) {
            return inventory.itemsBody.get(1).template.part;
        }

        if (this.nPoint.power < 1500000) {
            return PET_ID[this.gender][2];
        } else {
            return (short) (gender == ConstPlayer.NAMEC ? 60 : 58);
        }
    }

    private Mob findMobAttack() {
        int dis = ARANGE_CAN_ATTACK;
        Mob mobAtt = null;
        for (Mob mob : zone.mobs) {
            if (mob.isDie()) {
                continue;
            }
            int d = Util.getDistance(this, mob);
            if (d <= dis) {
                dis = d;
                mobAtt = mob;
            }
        }
        return mobAtt;
    }

    //Sức mạnh mở skill đệ
    private void updatePower() {
        if (this.playerSkill != null) {
            switch (this.playerSkill.getSizeSkill()) {
                case 1:
                    if (this.nPoint.power >= 150000000) {
                        openSkill2();
                    }
                    break;
                case 2:
                    if (this.nPoint.power >= 1500000000) {
                        openSkill3();
                    }
                    break;
                case 3:
                    if (this.nPoint.power >= 20000000000L) {
                        openSkill4();
                    }
                    break;
                //case 4:
//                case 4:
//                    if (this.nPoint.power >= 60000000000L) {
//                        openSkill5();
//                    }
//                    break;
            }
        }
    }

    public void openSkill2() {
        Skill skill = null;
        int tiLeKame = 40;
        int tiLeMasenko = 30;
        int tiLeAntomic = 30;

        int rd = Util.nextInt(1, 100);
        if (rd <= tiLeKame) {
            skill = SkillUtil.createSkill(Skill.KAMEJOKO, 1);
        } else if (rd <= tiLeKame + tiLeMasenko) {
            skill = SkillUtil.createSkill(Skill.MASENKO, 1);
        } else if (rd <= tiLeKame + tiLeMasenko + tiLeAntomic) {
            skill = SkillUtil.createSkill(Skill.ANTOMIC, 1);
        }
        skill.coolDown = 1000;
        this.playerSkill.skills.set(1, skill);
    }

    public void openSkill3() {
        Skill skill = null;
        int tiLeTDHS = 30;
        int tiLeTTNL = 30;
        int tiLeKOK = 40;

        int rd = Util.nextInt(1, 100);
        if (rd <= tiLeTDHS) {
            skill = SkillUtil.createSkill(Skill.THAI_DUONG_HA_SAN, 1);
        } else if (rd <= tiLeTDHS + tiLeTTNL) {
            skill = SkillUtil.createSkill(Skill.TAI_TAO_NANG_LUONG, 1);
        } else if (rd <= tiLeTDHS + tiLeTTNL + tiLeKOK) {
            skill = SkillUtil.createSkill(Skill.KAIOKEN, 1);
        }
        this.playerSkill.skills.set(2, skill);
    }

    private void openSkill4() {
        Skill skill = null;
        int tiLeBienKhi = 30;
        int tiLeDeTrung = 30;
        int tiLeKNL = 40;

        int rd = Util.nextInt(1, 100);
        if (rd <= tiLeBienKhi) {
            skill = SkillUtil.createSkill(Skill.BIEN_KHI, 1);
        } else if (rd <= tiLeBienKhi + tiLeDeTrung) {
            skill = SkillUtil.createSkill(Skill.DE_TRUNG, 1);
        } else if (rd <= tiLeBienKhi + tiLeDeTrung + tiLeKNL) {
            skill = SkillUtil.createSkill(Skill.KHIEN_NANG_LUONG, 1);
        }
        this.playerSkill.skills.set(3, skill);
    }

//    private void openSkill5() {
//        Skill skill = null;
//        int tiLeThoiMien = 10; //khi
//        int tiLeSoCoLa = 70; //detrung
//        int tiLeDCTT = 20; //khienNl
//
//        int rd = Util.nextInt(1, 100);
//        if (rd <= tiLeThoiMien) {
//            skill = SkillUtil.createSkill(Skill.SOCOLA, 1);
//        } else if (rd <= tiLeThoiMien + tiLeSoCoLa) {
//            skill = SkillUtil.createSkill(Skill.QUA_CAU_KENH_KHI, 1);
//        } else if (rd <= tiLeThoiMien + tiLeSoCoLa + tiLeDCTT) {
//            skill = SkillUtil.createSkill(Skill.DICH_CHUYEN_TUC_THOI, 1);
//        }
//        this.playerSkill.skills.set(4, skill);
//    }
//    private Skill getSkill(int i) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    }
//    ========================================================

    private Skill getSkill(int indexSkill) {
        return this.playerSkill.skills.get(indexSkill - 1);
    }

    public void transform() {
        if (this.typePet == 1) {
            this.isTransform = !this.isTransform;
            Service.getInstance().Send_Caitrang(this);
            Service.getInstance().chat(this, "Bố Mày Là Bư Nè !! Bư..Bư..Bư..Ma..Nhân..Bư....");
        }
        if (this.typePet == 2) {
            this.isTransform = !this.isTransform;
            Service.getInstance().Send_Caitrang(this);
            Service.getInstance().chat(this, "Tao là thần");
        }
    }

    @Override
    public void dispose() {
        this.mobAttack = null;
        this.master = null;
        super.dispose();
    }
}
