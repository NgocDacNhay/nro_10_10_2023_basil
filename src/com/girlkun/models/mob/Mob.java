package com.girlkun.models.mob;

import com.girlkun.consts.ConstMap;
import com.girlkun.consts.ConstMob;
import com.girlkun.consts.ConstTask;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;

import java.util.List;

import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Location;
import com.girlkun.models.player.Pet;
import com.girlkun.models.player.Player;
import com.girlkun.models.reward.ItemMobReward;
import com.girlkun.models.reward.MobReward;
import com.girlkun.network.io.Message;
import com.girlkun.server.Maintenance;
import com.girlkun.server.Manager;
import com.girlkun.server.ServerManager;
import com.girlkun.services.*;
import com.girlkun.services.func.Input;
import com.girlkun.utils.Util;

import java.util.ArrayList;
import java.util.Random;

public class Mob {

    public int id;
    public Zone zone;
    public int tempId;
    public String name;
    public int level;

    public MobPoint point;
    public MobEffectSkill effectSkill;
    public Location location;

    public byte pDame;
    public int pTiemNang;
    private long maxTiemNang;

    public long lastTimeDie;
    public int lvMob = 0;
    public int status = 5;

    public Mob(Mob mob) {
        this.point = new MobPoint(this);
        this.effectSkill = new MobEffectSkill(this);
        this.location = new Location();
        this.id = mob.id;
        this.tempId = mob.tempId;
        this.level = mob.level;
        this.point.setHpFull(mob.point.getHpFull());
        this.point.sethp(this.point.getHpFull());
        this.location.x = mob.location.x;
        this.location.y = mob.location.y;
        this.pDame = mob.pDame;
        this.pTiemNang = mob.pTiemNang;
        this.setTiemNang();
    }

    public Mob() {
        this.point = new MobPoint(this);
        this.effectSkill = new MobEffectSkill(this);
        this.location = new Location();
    }
    
     public static void initMopbKhiGas(Mob mob, int level) {
        if ( level <= 700){
        mob.point.dame = (level * 3250 * mob.level * 4) * 5;
        mob.point.maxHp = (level * 12472 * mob.level * 2 + level * 7263 * mob.tempId) * 5;
         }
        if ( level > 700 && level <= 10000 ){
        mob.point.dame = (level * 3250 * mob.level * 4) * 5;
        mob.point.maxHp = 2100000000;
         }
        if ( level > 10000 ){
        mob.point.dame = 2000000000;
        mob.point.maxHp = 2100000000;
         }
    } 

    public static void initMopBdkb(Mob mob, int level) {
        if ( level <= 109){
        mob.point.dame = (level * 1818);
        mob.point.maxHp = (level * 154000) * 5;
        }
        if ( level > 109 ){
        mob.point.dame = 200000;
        mob.point.maxHp = 16543210;
        }
    } 

     public static void hoiSinhMob(Mob mob) {
        mob.point.hp = mob.point.maxHp;
        mob.setTiemNang();
    //    mob.setTNSMMapBdkb();
        Message msg;
        try {
            msg = new Message(-13);
            msg.writer().writeByte(mob.id);
            msg.writer().writeByte(mob.tempId);
            msg.writer().writeByte(0); //level mob
            msg.writer().writeInt((mob.point.hp));
            Service.getInstance().sendMessAllPlayerInMap(mob.zone, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }
    public void setTiemNang() {
        this.maxTiemNang = (long) (this.point.getHpFull() * (this.pTiemNang + Util.nextInt(-2, 2)) / 100);
    }

    public void setTNSMMapBdkb(){
        if(this.zone.map.mapId >= 135 && this.zone.map.mapId <= 138){

            this.maxTiemNang = (long) (this.point.maxHp * (this.pTiemNang + Util.nextInt(-2, 2)) /10);

        }
        
    }

    private long lastTimeAttackPlayer;

    public boolean isDie() {
        return this.point.gethp() <= 0;
    }

    public synchronized void injured(Player plAtt, double damage, boolean dieWhenHpFull) {
        if (!this.isDie()) {
            if (damage >= this.point.hp) {
                damage = this.point.hp;
            }
            if (!dieWhenHpFull) {
                if (this.point.hp == this.point.maxHp && damage >= this.point.hp) {
                    damage = this.point.hp - 1;
                }
                if (this.tempId == 0 && damage > 10) {
                    damage = 10;
                }
            }
            this.point.hp -= damage;
            if (this.isDie()) {
                this.status = 0;
                this.sendMobDieAffterAttacked(plAtt, damage);
                TaskService.gI().checkDoneTaskKillMob(plAtt, this);
                TaskService.gI().checkDoneSideTaskKillMob(plAtt, this);
                this.lastTimeDie = System.currentTimeMillis();
            } else {
                this.sendMobStillAliveAffterAttacked(damage, plAtt != null ? plAtt.nPoint.isCrit : false);
            }
            if (plAtt != null) {
                Service.getInstance().addSMTN(plAtt, (byte) 2, getTiemNangForPlayer(plAtt, damage), true);
            }
        }
    }

    public static long smtnDeTu;

    public long levelBdkb(Player pl){
        long level = 1;
        if(pl.clan.bdkb.level <= 10){
            level = 2;
        } else if(pl.clan.bdkb.level <= 20){
            level = 3;
        } else if(pl.clan.bdkb.level <= 30){
            level = 4;
        } else if(pl.clan.bdkb.level <= 40){
            level = 5;
        } else if(pl.clan.bdkb.level <= 50){
            level = 6;
        } else if(pl.clan.bdkb.level <= 60){
            level = 7;
        } else if(pl.clan.bdkb.level <= 70){
            level = 8;
        } else if(pl.clan.bdkb.level <= 80){
            level = 9;
        } else if(pl.clan.bdkb.level <= 90){
            level = 10;
        } else if(pl.clan.bdkb.level <= 100){
            level = 11;
        } else if(pl.clan.bdkb.level <= 110){
            level = 15;
        }
        return level;
    }

    public long getTiemNangForPlayer(Player pl, double dame) {
        int levelPlayer = Service.getInstance().getCurrLevel(pl);
        int n = levelPlayer - this.level;
        double pDameHit = 0;
        if(this.zone.map.mapId >= 135 && this.zone.map.mapId <= 138){
            pDameHit = (double)Util.TamkjllGH(dame) * 200*levelBdkb(pl) / (point.getHpFull()/5);
        }
        else if (point.getHpFull() >= 100000000) {
            pDameHit = (double)Util.TamkjllGH(dame) * 200 / point.getHpFull();
        } else {
            pDameHit = (double)Util.TamkjllGH(dame) * 80 / point.getHpFull();
        }
        
        double tn1 = (double)pDameHit * maxTiemNang / 100;
        long tiemNang = (long)tn1;

        if (n >= 0) {
            for (int i = 0; i < n; i++) {
                long sub = tiemNang * 10 / 100;
                if (sub <= 0) {
                    sub = 1;
                }
                tiemNang -= sub;
            }
        } else {
            for (int i = 0; i < -n; i++) {
                long add = tiemNang * 10 / 100;
                if (add <= 0) {
                    add = 1;
                }
                tiemNang += add;
            }
        }
        if (tiemNang <= 0) {
            tiemNang = 1;
        }
        smtnDeTu = Util.TamkjllGH(pl.nPoint.calSMTNDeTu(tiemNang));
        tiemNang = Util.TamkjllGH(pl.nPoint.calSucManhTiemNang(tiemNang));
        if (pl.zone.map.mapId == 122 || pl.zone.map.mapId == 123 || pl.zone.map.mapId == 124 || pl.zone.map.mapId == 141 || pl.zone.map.mapId == 142 || pl.zone.map.mapId == 146) {
         
        tiemNang *= 2;
        smtnDeTu *= 2;
        }
        return tiemNang;
    }

    public void update() {
        if (this.tempId == 71) {
            try {
                Message msg = new Message(102);
                msg.writer().writeByte(5);
                msg.writer().writeShort(this.zone.getPlayers().get(0).location.x);
                Service.getInstance().sendMessAllPlayerInMap(zone, msg);
                msg.cleanup();
            } catch (Exception e) {
            }
        }

        if (this.isDie() && !Maintenance.isRuning) {
            switch (zone.map.type) {
                case ConstMap.MAP_DOANH_TRAI:
                    break;
                case ConstMap.MAP_KHI_GAS:
                    break;
                case ConstMap.MAP_BAN_DO_KHO_BAU:
                    break;
                default:
                    if (Util.canDoWithTime(lastTimeDie, 5000)) {
                        this.hoiSinh();
                        this.sendMobHoiSinh();
                    }
            }
        }
        effectSkill.update();
        attackPlayer();
    }

    private void attackPlayer() {
        if (!isDie() && !effectSkill.isHaveEffectSkill() && !(tempId == 0) && Util.canDoWithTime(lastTimeAttackPlayer, 2000)) {
            Player pl = getPlayerCanAttack();
            if (pl != null) {
//                MobService.gI().mobAttackPlayer(this, pl);
                this.mobAttackPlayer(pl);
            }
            this.lastTimeAttackPlayer = System.currentTimeMillis();
        }
    }

    private Player getPlayerCanAttack() {
        int distance = 100;
        Player plAttack = null;
        try {
            List<Player> players = this.zone.getNotBosses();
            for (Player pl : players) {
                if (!pl.isDie() && !pl.isBoss && !pl.effectSkin.isVoHinh) {
                    int dis = Util.getDistance(pl, this);
                    if (dis <= distance) {
                        plAttack = pl;
                        distance = dis;
                    }
                }
            }
        } catch (Exception e) {

        }
        return plAttack;
    }

    //**************************************************************************
    private void mobAttackPlayer(Player player) {
        int dameMob = this.point.getDameAttack();
        if (player.charms.tdDaTrau > System.currentTimeMillis()) {
            dameMob /= 2;
        }
        double dame = player.injured(null, dameMob, false, true);
        this.sendMobAttackMe(player, dame);
        this.sendMobAttackPlayer(player);
    }

    private void sendMobAttackMe(Player player, double dame) {
        if (!player.isPet && !player.isNewPet) {
            Message msg;
            try {
                msg = new Message(-11);
                msg.writer().writeByte(this.id);
                msg.writer().writeInt(Util.TamkjllGH(dame)); //dame
                player.sendMessage(msg);
                msg.cleanup();
            } catch (Exception e) {
            }
        }
    }

    private void sendMobAttackPlayer(Player player) {
        Message msg;
        try {
            msg = new Message(-10);
            msg.writer().writeByte(this.id);
            msg.writer().writeInt((int) player.id);
            msg.writer().writeInt(Util.TamkjllGH(player.nPoint.hp));
            Service.getInstance().sendMessAnotherNotMeInMap(player, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }
    
    public void hoiSinh() {
        this.status = 5;
        this.point.hp = this.point.maxHp;
        this.setTiemNang();
    }

    private void sendMobHoiSinh() {
        Message msg;
        try {
            msg = new Message(-13);
            msg.writer().writeByte(this.id);
            msg.writer().writeByte(this.tempId);
            msg.writer().writeByte(lvMob);
            msg.writer().writeInt(this.point.hp);
            Service.getInstance().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    //**************************************************************************
    private void sendMobDieAffterAttacked(Player plKill, double dameHit) {
        Message msg;
        try {
            msg = new Message(-12);
            msg.writer().writeByte(this.id);
            msg.writer().writeInt(Util.TamkjllGH(dameHit));
            msg.writer().writeBoolean(plKill.nPoint.isCrit); // crit
            List<ItemMap> items = mobReward(plKill, this.dropItemTask(plKill), msg);
            Service.getInstance().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
            hutItem(plKill, items);
        } catch (Exception e) {
        }
    }

    private void hutItem(Player player, List<ItemMap> items) {
        if (!player.isPet && !player.isNewPet) {
            if (player.charms.tdThuHut > System.currentTimeMillis()) {
                for (ItemMap item : items) {
                    if (item.itemTemplate.id != 590) {
                        ItemMapService.gI().pickItem(player, item.itemMapId, true);
                    }
                }
            }
        } else {
            if (((Pet) player).master.charms.tdThuHut > System.currentTimeMillis()) {
                for (ItemMap item : items) {
                    if (item.itemTemplate.id != 590) {
                        ItemMapService.gI().pickItem(((Pet) player).master, item.itemMapId, true);
                    }
                }
            }
        }
    }

    private List<ItemMap> mobReward(Player player, ItemMap itemTask, Message msg) {
//        nplayer
        List<ItemMap> itemReward = new ArrayList<>();
        try {
            if ((!player.isPet && player.setClothes.setDHD == 5) || (player.isPet && ((Pet) player).setClothes.setDHD == 5)) {
                byte random = 1;
                if (Util.isTrue(2, 100)) {
                    random = 2;
                }
                Item i = Manager.RUBY_REWARDS.get(Util.nextInt(0, Manager.RUBY_REWARDS.size() - 1));
                i.quantity = random;
                InventoryServiceNew.gI().addItemBag(player, i);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.getInstance().sendThongBao(player, "Bạn vừa nhận được " + random + " hồng ngọc");
            }

            itemReward = this.getItemMobReward(player, this.location.x + Util.nextInt(-10, 10),
                    this.zone.map.yPhysicInTop(this.location.x, this.location.y));
            if (itemTask != null) {
                itemReward.add(itemTask);
            }
            msg.writer().writeByte(itemReward.size()); //sl item roi
            for (ItemMap itemMap : itemReward) {
                msg.writer().writeShort(itemMap.itemMapId);// itemmapid
                msg.writer().writeShort(itemMap.itemTemplate.id); // id item
                msg.writer().writeShort(itemMap.x); // xend item
                msg.writer().writeShort(itemMap.y); // yend item
                msg.writer().writeInt((int) itemMap.playerId); // id nhan nat
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemReward;
    }

    public List<ItemMap> getItemMobReward(Player player, int x, int yEnd) {
        List<ItemMap> list = new ArrayList<>();
        MobReward mobReward = Manager.MOB_REWARDS.get(this.tempId);
        if (mobReward == null) {
            return list;
        }
        List<ItemMobReward> items = mobReward.getItemReward();
        List<ItemMobReward> golds = mobReward.getGoldReward();
        byte randomDo = (byte) new Random().nextInt(Manager.itemIds_TL.length - 1);
        byte randomVp = (byte) new Random().nextInt(Manager.itemSkien.length);
        if (!items.isEmpty()) {
            ItemMobReward item = items.get(Util.nextInt(0, items.size() - 1));
            ItemMap itemMap = item.getItemMap(zone, player, x, yEnd);
            if (itemMap != null) {
                list.add(itemMap);
            }
        }
        if (!golds.isEmpty()) {
            ItemMobReward gold = golds.get(Util.nextInt(0, golds.size() - 1));
            ItemMap itemMap = gold.getItemMap(zone, player, x, yEnd);
            if (itemMap != null) {
                list.add(itemMap);
            }
        }
        if (player.itemTime.isUseMayDo && Util.isTrue(8, 100) && this.tempId > 57 && this.tempId < 66) {
            list.add(new ItemMap(zone, 380, 1, x, player.location.y, player.id));
        }// vat phẩm rơi khi user maaáy dò adu hoa r o day ti code choa

        
        Item item = player.inventory.itemsBody.get(1); // Sự kiện quần bơi
        if (this.zone.map.mapId > 0){
            if(item.isNotNullItem()){
                if (item.template.id == 691){ // Id vật phẩm 691
                if (Util.isTrue(5, 100)) {    
                    list.add(new ItemMap(zone, Util.nextInt(695,698), 1, x, player.location.y, player.id));} // Rơi vật phẩm sự kiện 695 - 698
                }else if (item.template.id != 691 && item.template.id != 692 && item.template.id != 693){
                        if (Util.isTrue(0, 1)){
                           list.add(new ItemMap(zone, 76, 1, x, player.location.y, player.id)); // Rơi vàng 
                        }
                        
                    }
            }
            
        }if (this.zone.map.mapId > 0){
            if(item.isNotNullItem()){
                if (item.template.id == 692){
                    if (Util.isTrue(5, 100)) {    
                        list.add(new ItemMap(zone, Util.nextInt(695,698), 1, x, player.location.y, player.id));
                    }
                }else if (item.template.id != 691 && item.template.id != 692 && item.template.id != 693){
                    if (Util.isTrue(0, 1)){
                        list.add(new ItemMap(zone, 76, 1, x, player.location.y, player.id));
                    }
                }
            }
            
        }if (this.zone.map.mapId > 0){
            if(item.isNotNullItem()){
                if (item.template.id == 693){
                    if (Util.isTrue(5, 100)) {    
                        list.add(new ItemMap(zone, Util.nextInt(695,698), 1, x, player.location.y, player.id));
                    }
                }else if (item.template.id != 691 && item.template.id != 692 && item.template.id != 693){
                    if (Util.isTrue(0, 1)){
                        list.add(new ItemMap(zone, 76, 1, x, player.location.y, player.id)); 
                    }

                }
            }
        }
        // Rơi đá nâng cấp đồ
        if (this.zone.map.mapId >= 0){
            if (Util.isTrue(5, 100)){
                list.add(new ItemMap(zone, Util.nextInt(220,224), 1, x, player.location.y, player.id));
            }
        }
        //Roi Do Than Cold
//        if (!player.isPet && !player.isNewPet && !player.isNewPet1 && player.isBoss){
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 60000)){
                Item Quanthanlinh = ItemService.gI().createNewItem((short) (556));
                Quanthanlinh.itemOptions. add(new Item.ItemOption(22, Util.nextInt(55,65)));
                Quanthanlinh.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Quanthanlinh);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Quanthanlinh.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 60000)){
                Item Quanthanlinhxd = ItemService.gI().createNewItem((short) (560));
                Quanthanlinhxd.itemOptions.add(new Item.ItemOption(22, Util.nextInt(45,55)));
                Quanthanlinhxd.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Quanthanlinhxd);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Quanthanlinhxd.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 60000)){
                Item Quanthanlinhnm = ItemService.gI().createNewItem((short) (558));
                Quanthanlinhnm.itemOptions.add(new Item.ItemOption(22, Util.nextInt(45,60)));
                Quanthanlinhnm.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Quanthanlinhnm);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Quanthanlinhnm.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 60000)){
                Item Aothanlinh = ItemService.gI().createNewItem((short) (555));
                Aothanlinh.itemOptions.add(new Item.ItemOption(47, Util.nextInt(500,600)));
                Aothanlinh.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Aothanlinh);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Aothanlinh.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 60000)){
                Item Aothanlinhnm = ItemService.gI().createNewItem((short) (557));
                Aothanlinhnm.itemOptions.add(new Item.ItemOption(47, Util.nextInt(400,550)));
                Aothanlinhnm.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Aothanlinhnm);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Aothanlinhnm.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 60000)){
                Item Aothanlinhxd = ItemService.gI().createNewItem((short) (559));
                Aothanlinhxd.itemOptions.add(new Item.ItemOption(47, Util.nextInt(600,700)));
                Aothanlinhxd.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Aothanlinhxd);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Aothanlinhxd.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 100000)){
                Item Gangthanlinh = ItemService.gI().createNewItem((short) (562));
                Gangthanlinh.itemOptions.add(new Item.ItemOption(0, Util.nextInt(6000,7000)));
                Gangthanlinh.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Gangthanlinh);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Gangthanlinh.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 100000)){
                Item Gangthanlinhxd = ItemService.gI().createNewItem((short) (566));
                Gangthanlinhxd.itemOptions.add(new Item.ItemOption(0, Util.nextInt(6500,7500)));
                Gangthanlinhxd.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Gangthanlinhxd);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Gangthanlinhxd.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 100000)){
                Item Gangthanlinhnm = ItemService.gI().createNewItem((short) (564));
                Gangthanlinhnm.itemOptions.add(new Item.ItemOption(0, Util.nextInt(5500,6500)));
                Gangthanlinhnm.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Gangthanlinhnm);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Gangthanlinhnm.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 60000)){
                Item Giaythanlinh = ItemService.gI().createNewItem((short) (563));
                Giaythanlinh.itemOptions.add(new Item.ItemOption(23, Util.nextInt(50,60)));
                Giaythanlinh.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Giaythanlinh);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Giaythanlinh.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 60000)){
                Item Giaythanlinhxd = ItemService.gI().createNewItem((short) (567));
                Giaythanlinhxd.itemOptions.add(new Item.ItemOption(23, Util.nextInt(55,65)));
                Giaythanlinhxd.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Giaythanlinhxd);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Giaythanlinhxd.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 60000)){
                Item Giaythanlinhnm = ItemService.gI().createNewItem((short) (565));
                Giaythanlinhnm.itemOptions.add(new Item.ItemOption(23, Util.nextInt(65,75)));
                Giaythanlinhnm.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Giaythanlinhnm);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Giaythanlinhnm.template.name);
            }
        }
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110) {
            if(Util.isTrue(2, 100000)){
                Item Nhanthanlinh = ItemService.gI().createNewItem((short) (561));
                Nhanthanlinh.itemOptions.add(new Item.ItemOption(14, Util.nextInt(13,16)));
                Nhanthanlinh.itemOptions.add(new Item.ItemOption(21, Util.nextInt(15,17)));
                InventoryServiceNew.gI().addItemBag(player, Nhanthanlinh);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được "+Nhanthanlinh.template.name);
            }
        }
        
        
        // Rơi vật phẩm Mảnh Vỡ Bông Tai ( ID 541 )        
       if (this.zone.map.mapId >= 156 && this.zone.map.mapId <= 159) {
            if(Util.isTrue(7, 100)){
                Item quahongdao = ItemService.gI().createNewItem((short) (933));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Mảnh Vỡ Bông Tai ");
            }
        }
       // Rơi mảnh 
       if (this.zone.map.mapId >= 156 && this.zone.map.mapId <= 159) {
            if(Util.isTrue(7, 100)){
                Item quahongdao = ItemService.gI().createNewItem((short) (934));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Mảnh Hồn Bông Tai ");
            }
        }
       if (this.zone.map.mapId >= 156 && this.zone.map.mapId <= 159) {
            if(Util.isTrue(1, 200)){
                Item quahongdao = ItemService.gI().createNewItem((short) (935));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Đá Xanh Lam ");
            }
        }
                
 
        
        
        
        // Rơi vật phẩm Quả Hồng Đào ( ID 541 )
       if (this.zone.map.mapId >= 122 && this.zone.map.mapId <= 124) {
            if(Util.isTrue(2, 100)){
                Item quahongdao = ItemService.gI().createNewItem((short) (541));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Quả Hồng Đào ");
            }
        }
         
        // Rơi Event Giải - Khai -Phong - Ấn
        if (this.zone.map.mapId >= 92 && this.zone.map.mapId <= 94) {
            if(Util.isTrue(2, 100)){
                Item quahongdao = ItemService.gI().createNewItem((short) (537));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Chữ Giải ");
            }
        }
        if (this.zone.map.mapId >= 96 && this.zone.map.mapId <= 98) {
            if(Util.isTrue(2, 100)){
                Item quahongdao = ItemService.gI().createNewItem((short) (538));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Chữ Khai ");
            }
        }
        if (this.zone.map.mapId >= 99 && this.zone.map.mapId <= 100) {
            if(Util.isTrue(2, 100)){
                Item quahongdao = ItemService.gI().createNewItem((short) (539));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Chữ Phong ");
            }
        }
        
        //Rơi mảnh Tinh Ấn
        if (this.zone.map.mapId >= 149 && this.zone.map.mapId <= 152) {
            if(Util.isTrue(1, 200)){
                Item quahongdao = ItemService.gI().createNewItem((short) (1232));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được mảnh Tinh Ấn ");
            }
        }
        if (this.zone.map.mapId == 147 ) {
            if(Util.isTrue(1, 500)){
                Item quahongdao = ItemService.gI().createNewItem((short) (1232));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Chữ Phong ");
            }
        }
        //Rơi Bí Kíp Tuyệt Kỹ
        if (this.zone.map.mapId == 155 ) {
            if(Util.isTrue(5, 200)){
                Item quahongdao = ItemService.gI().createNewItem((short) (1215));                
                InventoryServiceNew.gI().addItemBag(player, quahongdao);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Mảnh Tuyệt Kỹ ");
            }
        }
        
        Item item1 = player.inventory.itemsBody.get(5); // Sự kiện mặc cải trang GoHanSSJ
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110){
            if(item1.isNotNullItem()){
                if (item1.template.id == 1192){ // Id cải trang
                    if (Util.isTrue(30, 500)) {    
                        Item Thoivang = ItemService.gI().createNewItem((short) (457));                
                        InventoryServiceNew.gI().addItemBag(player, Thoivang);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.gI().sendThongBao(player, "Bạn vừa nhận được Thỏi vàng ");
                    } // Rơi Thỏi Vàng
                }else if (item1.template.id != 1192){
                    if (Util.isTrue(0, 1)){
                        list.add(new ItemMap(zone, 76, 1, x, player.location.y, player.id)); // Rơi vàng
                    }
                    
                }
            }
        }
        
        Item item2 = player.inventory.itemsBody.get(5); // Sự kiện mặc cải trang GoHanSSJ
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110){
            if(item1.isNotNullItem()){
            if (item1.template.id == 1192){ // Id cải trang
        if (Util.isTrue(1, 1000)) {    
            Item Vehongngoc = ItemService.gI().createNewItem((short) (1132));                
                InventoryServiceNew.gI().addItemBag(player, Vehongngoc);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Vé Hồng Ngọc ");} // Rơi Vé Hồng Ngọc
        }else if (item1.template.id != 1192){
            if (Util.isTrue(0, 1))
            list.add(new ItemMap(zone, 76, 1, x, player.location.y, player.id)); // Rơi vàng
        }
        }}
        
        Item item3 = player.inventory.itemsBody.get(5); // Sự kiện mặc cải trang GoHanSSJ
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110){
            if(item1.isNotNullItem()){
            if (item1.template.id == 1192){ // Id cải trang
        if (Util.isTrue(20, 100)) {    
            Item Dangusac = ItemService.gI().createNewItem((short) (674));                
                InventoryServiceNew.gI().addItemBag(player, Dangusac);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Đá Ngũ Sắc ");} // Rơi vật phẩm Đá Ngũ Sắc
        }else if (item1.template.id != 1192){
            if (Util.isTrue(0, 1))
            list.add(new ItemMap(zone, 76, 1, x, player.location.y, player.id)); // Rơi vàng
        }
        }}
        
        Item item4 = player.inventory.itemsBody.get(5); // Sự kiện mặc cải trang Baby Vegeta
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110){
            if(item1.isNotNullItem()){
            if (item1.template.id == 1216){ // Id cải trang
        if (Util.isTrue(1, 400)) {    
            Item Thoivang = ItemService.gI().createNewItem((short) (457));                
                InventoryServiceNew.gI().addItemBag(player, Thoivang);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Thỏi vàng ");} // Rơi Thỏi Vàng
        }else if (item1.template.id != 1216){
            if (Util.isTrue(0, 1))
            list.add(new ItemMap(zone, 76, 1, x, player.location.y, player.id)); // Rơi vàng
        }
        }}
        
        Item item5 = player.inventory.itemsBody.get(5); // Sự kiện mặc cải trang Baby Vegeta
        if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110){
            if(item1.isNotNullItem()){
            if (item1.template.id == 1216){ // Id cải trang
        if (Util.isTrue(1, 900)) {    
            Item Vehongngoc = ItemService.gI().createNewItem((short) (1132));                
                InventoryServiceNew.gI().addItemBag(player, Vehongngoc);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Bạn vừa nhận được Vé Hồng Ngọc ");} // Rơi Vé Hồng Ngọc
        }else if (item1.template.id != 1216){
            if (Util.isTrue(0, 1))
            list.add(new ItemMap(zone, 76, 1, x, player.location.y, player.id)); // Rơi vàng
        }
        }}
        
        Item item6 = player.inventory.itemsBody.get(5); // ự kiện mặc cải trang Baby Vegeta
            if (this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110){
                if(item1.isNotNullItem()){
                if (item1.template.id == 1216){ // Id cải trang
            if (Util.isTrue(1, 300)) {    
                Item Dangusac = ItemService.gI().createNewItem((short) (674));                
                    InventoryServiceNew.gI().addItemBag(player, Dangusac);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendThongBao(player, "Bạn vừa nhận được Đá Ngũ Sắc ");} // Rơi vật phẩm Đá Ngũ Sắc
            }else if (item1.template.id != 1216){
                if (Util.isTrue(0, 1))
                list.add(new ItemMap(zone, 76, 1, x, player.location.y, player.id)); // Rơi vàng
            }
            }
        }
        


        if(this.zone.map.mapId >= 105 && this.zone.map.mapId <= 110){
            if(player.setClothes.setTL == 5){
                if(Util.isTrue(20, 100)){
                    list.add(new ItemMap(zone, Util.nextInt(663,667), 1, x, player.location.y, player.id));
                }
            }
        }
        if(this.zone.map.mapId >= 1 && this.zone.map.mapId <= 3 || this.zone.map.mapId >= 8 && this.zone.map.mapId <= 10 || this.zone.map.mapId >= 15 && this.zone.map.mapId <= 17){
            Item itemSKH = ItemService.dropSKHInMap(player);
                if(Util.isTrue(5, 100)){
                    InventoryServiceNew.gI().addItemBag(player, itemSKH);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendThongBao(player, "Bạn vừa nhận được "+itemSKH.template.name);
                }
            
        }


        return list;
    }

    private ItemMap dropItemTask(Player player) {
        ItemMap itemMap = null;
        switch (this.tempId) {
            case ConstMob.KHUNG_LONG:
            case ConstMob.LON_LOI:
            case ConstMob.QUY_DAT:
                if (TaskService.gI().getIdTask(player) == ConstTask.TASK_2_0) {
                    itemMap = new ItemMap(this.zone, 73, 1, this.location.x, this.location.y, player.id);
                }
                break;
        }
        if (itemMap != null) {
            return itemMap;
        }
        return null;
    }

    private void sendMobStillAliveAffterAttacked(double dameHit, boolean crit) {
        Message msg;
        try {
            msg = new Message(-9);
            msg.writer().writeByte(this.id);
            msg.writer().writeInt(this.point.gethp());
            msg.writer().writeInt(Util.TamkjllGH(dameHit));
            msg.writer().writeBoolean(crit); // chí mạng
            msg.writer().writeInt(-1);
            Service.getInstance().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }
}
