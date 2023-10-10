package com.girlkun.services;

import com.girlkun.models.Template;
import com.girlkun.models.Template.ItemOptionTemplate;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.models.shop.ItemShop;
import com.girlkun.server.Manager;
import com.girlkun.services.func.CombineServiceNew;
import com.girlkun.utils.TimeUtil;
import com.girlkun.utils.Util;
import com.girlkun.models.item.Item.ItemOption;

import java.util.*;
import java.util.stream.Collectors;


public class ItemService {

    private static ItemService i;

    public static ItemService gI() {
        if (i == null) {
            i = new ItemService();
        }
        return i;
    }
    
    public int buffParamHuyDiet(int paramGoc){
        int dameThem = paramGoc/100;
        int paramGocChuan = paramGoc + Util.nextInt(0, 15)*dameThem;
        
        buffParamHuyDietThieu = paramGocChuan % 1000;

        paramGoc = paramGocChuan - buffParamHuyDietThieu;
        paramGoc /= 1000;
        return paramGoc;
    }

    public int buffParamThienSu1(int paramGoc){
        int dameThem = paramGoc/100;
            paramGoc = paramGoc + Util.nextInt(30, 50)*dameThem;
        
        return paramGoc;
    }

    public int buffParamHuyDiet1(int paramGoc){
        int dameThem = paramGoc/100;
            paramGoc = paramGoc + Util.nextInt(0, 15)*dameThem;
        
        return paramGoc;
    }

    public int buffParamThienSu2(int paramGoc){
        int dameThem = paramGoc/100;
            paramGoc = paramGoc + Util.nextInt(30, 50)*dameThem;
        
        return paramGoc;
    }

    public int buffParamHuyDiet2(int paramGoc){
        int dameThem = paramGoc/100;
            paramGoc = paramGoc + Util.nextInt(0, 15)*dameThem;
        
        return paramGoc;
    }

    public int buffParamThienSuThieu;

    public int buffParamHuyDietThieu;


    public int buffParamThienSu(int paramGoc){
        int dameThem = paramGoc/100;
        int paramGocChuan = paramGoc + Util.nextInt(30, 50)*dameThem;
        
        buffParamThienSuThieu = paramGocChuan % 1000;

        paramGoc = paramGocChuan - buffParamThienSuThieu;
        paramGoc /= 1000;
        return paramGoc;
    }

    public short getItemIdByIcon(short IconID) {
        for (int i = 0; i < Manager.ITEM_TEMPLATES.size(); i++) {
            if (Manager.ITEM_TEMPLATES.get(i).iconID == IconID) {
                return Manager.ITEM_TEMPLATES.get(i).id;
            }
        }
        return -1;
    }
    public Item createItemNull() {
        Item item = new Item();
        return item;
    }

    public Item createItemFromItemShop(ItemShop itemShop) {
        Item item = new Item();
        item.template = itemShop.temp;
        item.quantity = 1;
        item.content = item.getContent();
        item.info = item.getInfo();
        for (Item.ItemOption io : itemShop.options) {
            item.itemOptions.add(new Item.ItemOption(io));
        }
        return item;
    }

    public Item copyItem(Item item) {
        Item it = new Item();
        it.itemOptions = new ArrayList<>();
        it.template = item.template;
        it.info = item.info;
        it.content = item.content;
        it.quantity = item.quantity;
        it.createTime = item.createTime;
        for (Item.ItemOption io : item.itemOptions) {
            it.itemOptions.add(new Item.ItemOption(io));
        }
        return it;
    }

    public Item createNewItem(short tempId) {
        return createNewItem(tempId, 1);
    }
    
    public Item otpts(short tempId) {
        return otpts(tempId, 1);
    }

    public Item createNewItem(short tempId, int quantity) {
        Item item = new Item();
        item.template = getTemplate(tempId);
        item.quantity = quantity;
        item.createTime = System.currentTimeMillis();

        item.content = item.getContent();
        item.info = item.getInfo();
        return item;
    }
    
        public Item otpts(short tempId, int quantity) {
        Item item = new Item();
        item.template = getTemplate(tempId);
        item.quantity = quantity;
        item.createTime = System.currentTimeMillis();
        if (item.template.type== 0){
            item.itemOptions.add(new ItemOption(21,80));
            item.itemOptions.add(new ItemOption(47, Util.nextInt(2000,2500)));
        }
        if (item.template.type== 1){
            item.itemOptions.add(new ItemOption(21,80));
            item.itemOptions.add(new ItemOption(22, Util.nextInt(150,200)));
        }
        if (item.template.type== 2){
            item.itemOptions.add(new ItemOption(21,80));
            item.itemOptions.add(new ItemOption(0, Util.nextInt(18000,20000)));
        }
        if (item.template.type== 3){
            item.itemOptions.add(new ItemOption(21,80));
            item.itemOptions.add(new ItemOption(23, Util.nextInt(150,200)));
        }
        if (item.template.type== 4){
            item.itemOptions.add(new ItemOption(21,80));
            item.itemOptions.add(new ItemOption(14, Util.nextInt(20,25)));
        }
        item.content = item.getContent();
        item.info = item.getInfo();
        return item;
    }

    public Item createItemSetKichHoat(int tempId, int quantity) {
        Item item = new Item();
        item.template = getTemplate(tempId);
        item.quantity = quantity;
        item.itemOptions = createItemNull().itemOptions;
        item.createTime = System.currentTimeMillis();
        item.content = item.getContent();
        item.info = item.getInfo();
        return item;
    }

    public Item createItemOpen(int tempId, int quantity) {
        Item item = new Item();
        item.template = getTemplate(tempId);
        item.quantity = quantity;
        item.itemOptions = createItemNull().itemOptions;
        item.createTime = System.currentTimeMillis();
        item.content = item.getContent();
        item.info = item.getInfo();
        return item;
    }

    public Item createItemDoHuyDiet(int tempId, int quantity) {
        Item item = new Item();
        item.template = getTemplate(tempId);
        item.quantity = quantity;
        item.itemOptions = createItemNull().itemOptions;
        item.createTime = System.currentTimeMillis();
        item.content = item.getContent();
        item.info = item.getInfo();
        return item;
    }

    public Item createItemFromItemMap(ItemMap itemMap) {
        Item item = createNewItem(itemMap.itemTemplate.id, itemMap.quantity);
        item.itemOptions = itemMap.options;
        return item;
    }

    public ItemOptionTemplate getItemOptionTemplate(int id) {
        return Manager.ITEM_OPTION_TEMPLATES.get(id);
    }

    public Template.ItemTemplate getTemplate(int id) {
        return Manager.ITEM_TEMPLATES.get(id);
    }

    public boolean isItemActivation(Item item) {
        return false;
    }

    public int getPercentTrainArmor(Item item) {
        if (item != null) {
            switch (item.template.id) {
                case 529:
                case 534:
                    return 10;
                case 530:
                case 535:
                    return 20;
                case 531:
                case 536:
                    return 30;
                default:
                    return 0;
            }
        } else {
            return 0;
        }
    }

    public boolean isTrainArmor(Item item) {
        if (item != null) {
            switch (item.template.id) {
                case 529:
                case 534:
                case 530:
                case 535:
                case 531:
                case 536:
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public boolean isOutOfDateTime(Item item) {
        if (item != null) {
            for (Item.ItemOption io : item.itemOptions) {
                if (io.optionTemplate.id == 93) {
                    int dayPass = (int) TimeUtil.diffDate(new Date(), new Date(item.createTime), TimeUtil.DAY);
                    if (dayPass != 0) {
                        io.param -= dayPass;
                        if (io.param <= 0) {
                            return true;
                        } else {
                            item.createTime = System.currentTimeMillis();
                        }
                    }
                }
            }
        }
        return false;
    }

    public void OpenSKH(Player player, int itemUseId, int select) throws Exception {
        if (select < 0 || select > 4) return;
        Item itemUse = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, itemUseId);
        int[][] items = {{0, 6, 21, 27, 12}, {1, 7, 22, 28, 12}, {2, 8, 23, 29, 12}};
        int[][] options = {{128, 129, 127}, {130, 131, 132}, {133, 135, 134}};
        int skhv1 = 25;// ti le
        int skhv2 = 35;//ti le
        int skhc = 40;//ti le
        int skhId = -1;

        int rd = Util.nextInt(1, 100);
        if (rd <= skhv1) {
            skhId = 0;
        } else if (rd <= skhv1 + skhv2) {
            skhId = 1;
        } else if (rd <= skhv1 + skhv2 + skhc) {
            skhId = 2;
        }
        Item item = null;
        switch (itemUseId) {
            case 2000:
                item = itemSKH(items[0][select], options[0][skhId]);
                break;
            case 2001:
                item = itemSKH(items[1][select], options[1][skhId]);
                break;
            case 2002:
                item = itemSKH(items[2][select], options[2][skhId]);
                break;
        }
        if (item != null && InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được " + item.template.name);
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemUse, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }

    public int randomSKHId(byte gender) {
        if (gender == 3) gender = 2;
        int[][] options = {{128, 129, 127}, {130, 131, 132}, {133, 135, 134}};
        int skhv1 = 25;
        int skhv2 = 35;
        int skhc = 40;
        int skhId = -1;
        int rd = Util.nextInt(1, 100);
        if (rd <= skhv1) {
            skhId = 0;
        } else if (rd <= skhv1 + skhv2) {
            skhId = 1;
        } else if (rd <= skhv1 + skhv2 + skhc) {
            skhId = 2;
        }
        return options[gender][skhId];
    }

    public void OpenDHD(Player player, int itemUseId, int select) throws Exception {
        if (select < 0 || select > 4) return;
        Item itemUse = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, itemUseId);
        int gender = -1;
        switch (itemUseId) {
            case 2003: //td
                gender = 0;
                break;
            case 2004: //xd
                gender = 2;
                break;
            case 2005: //nm
                gender = 1;
                break;
        }
        int[][] items = {{650, 651, 657, 658, 656}, {652, 653, 659, 660, 656}, {654, 655, 661, 662, 656}}; //td, namec,xd
        Item item = randomCS_DHD(items[gender][select], gender);

        if (item != null && InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được " + item.template.name);
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemUse, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }

    public void OpenItem2048(Player player, Item itemUse) {
        try {
            if (InventoryServiceNew.gI().getCountEmptyBag(player) <= 1) {
                Service.gI().sendThongBao(player, "Bạn phải có ít nhất 2 ô trống hành trang");
                return;
            }
            short[] icon = new short[2];
            int rd = Util.nextInt(1, 100);
            int rac = 45;
            int ruby = 20;
            int dbv = 10;
            int hps = 10;
            int bua = 5;
            int da = 10;
            Item item = randomRac();
            if (rd <= rac) {
                item = randomRac();
            } else if (rd <= rac + ruby) {
                item = CShongngoc();
            } else if (rd <= rac + ruby + dbv) {
                item = daBaoVe();
            } else if (rd <= rac + ruby + dbv + hps) {
                item = hps();
            } else if (rd <= rac + ruby + dbv + hps + bua) {
                item = buaGPS();
            } else if (rd <= rac + ruby + dbv + hps + bua + da) {
                item = daPhapSu();
            }
            if (item.template.id == 861) {
                item.quantity = Util.nextInt(10, 30);
            }
            icon[0] = itemUse.template.iconID;
            icon[1] = item.template.iconID;
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemUse, 1);
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().sendItemBags(player);
            player.inventory.event++;
            Service.gI().sendThongBao(player, "Bạn đã nhận được " + item.template.name);
//            CombineServiceNew.gI().sendEffectOpenItem(player, icon[0], icon[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void OpenItem2049(Player player, Item itemUse) {
        try {
            if (InventoryServiceNew.gI().getCountEmptyBag(player) <= 1) {
                Service.gI().sendThongBao(player, "Bạn phải có ít nhất 2 ô trống hành trang");
                return;
            }
            short[] icon = new short[2];
            int rd = Util.nextInt(1, 100);
            int dns = 10;
            int vinhdu = 20;
            int ruby = 10;
            int mtsthuong = 20;
            int hpscc = 15;
            int mtshiem = 10;           
            int dapscc = 15;
            Item item = randomRaccc();
            if (rd <= dns) {
                item = DaNguSac();
            }else if (rd <= dns + vinhdu) {
                item = LuaThan();
            }else if (rd <= dns + ruby+ vinhdu) {
                item = CShongngoccc();
            } else if (rd <= dns + ruby + mtsthuong+ vinhdu) {
                item = ManhTSthuong();
            } else if (rd <= dns + ruby + mtsthuong + hpscc+ vinhdu) {
                item = hpscc();
            } else if (rd <= dns + ruby + mtsthuong + hpscc + mtshiem+ vinhdu) {
                item = ManhTShiem();                
            } else if (rd <= dns + ruby + mtsthuong + hpscc + mtshiem + dapscc+ vinhdu) {
                item = daPhapSucc();
            }
            icon[0] = itemUse.template.iconID;
            icon[1] = item.template.iconID;
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemUse, 1);
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().sendItemBags(player); 
            player.diemhotong++;
            Service.gI().sendThongBao(player, "Bạn đã nhận được " + item.template.name);
//            CombineServiceNew.gI().sendEffectOpenItem(player, icon[0], icon[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void OpenItem736(Player player, Item itemUse) {
        try {
            if (InventoryServiceNew.gI().getCountEmptyBag(player) <= 1) {
                Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 2 ô trống hành trang");
                return;
            }
            short[] icon = new short[2];
            int rd = Util.nextInt(1, 100);
            int rac = 50;
            int ruby = 20;
            int dbv = 10;
            int vb = 10;
            int bh = 5;
            int ct = 5;
            Item item = randomRac();
            if (rd <= rac) {
                item = randomRac();
            } else if (rd <= rac + ruby) {
                item = Manager.RUBY_REWARDS.get(Util.nextInt(0, Manager.RUBY_REWARDS.size() - 1));
            } else if (rd <= rac + ruby + dbv) {
                item = daBaoVe();
            } else if (rd <= rac + ruby + dbv + vb) {
                item = vanBay2011(true);
            } else if (rd <= rac + ruby + dbv + vb + bh) {
                item = phuKien2011(true);
            } else if (rd <= rac + ruby + dbv + vb + bh + ct) {
                item = caitrang2011(true);
            }
            if (item.template.id == 861) {
                item.quantity = Util.nextInt(10, 30);
            }
            icon[0] = itemUse.template.iconID;
            icon[1] = item.template.iconID;
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemUse, 1);
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().sendItemBags(player);
//            player.inventory.event++;
            player.diemsk++;
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được " + item.template.name);
            CombineServiceNew.gI().sendEffectOpenItem(player, icon[0], icon[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void settaiyoken(Player player) throws Exception {
        for (int i = 0 ; i < 12;i++){
        Item hq = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1105+i);
        Item ao = ItemService.gI().otpts((short)1048);
        Item quan = ItemService.gI().otpts((short)1051);
        Item gang = ItemService.gI().otpts((short)1054);
        Item giay = ItemService.gI().otpts((short)1057);
        Item nhan = ItemService.gI().otpts((short)1060);
        ao.itemOptions.add(new Item.ItemOption(127,0));
        quan.itemOptions.add(new Item.ItemOption(127,0));
        gang.itemOptions.add(new Item.ItemOption(127,0));
        giay.itemOptions.add(new Item.ItemOption(127,0));
        nhan.itemOptions.add(new Item.ItemOption(127,0));
        ao.itemOptions.add(new Item.ItemOption(139,0));
        quan.itemOptions.add(new Item.ItemOption(139,0));
        gang.itemOptions.add(new Item.ItemOption(139,0));
        giay.itemOptions.add(new Item.ItemOption(139,0));
        nhan.itemOptions.add(new Item.ItemOption(139,0));
        ao.itemOptions.add(new Item.ItemOption(30,0));
        quan.itemOptions.add(new Item.ItemOption(30,0));
        gang.itemOptions.add(new Item.ItemOption(30,0));
        giay.itemOptions.add(new Item.ItemOption(30,0));
        nhan.itemOptions.add(new Item.ItemOption(30,0));
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 4) {
            InventoryServiceNew.gI().addItemBag(player, ao);
            InventoryServiceNew.gI().addItemBag(player, quan);
            InventoryServiceNew.gI().addItemBag(player, gang);
            InventoryServiceNew.gI().addItemBag(player, giay);
            InventoryServiceNew.gI().addItemBag(player, nhan);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được set thiên sứ ");
            InventoryServiceNew.gI().subQuantityItemsBag(player, hq, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 5 ô trống hành trang");
        }
    }
    }
    
    public void setgenki(Player player) throws Exception {
        for (int i = 0 ; i < 12;i++){
        Item hq = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1105+i);
        Item ao = ItemService.gI().otpts((short)1048);
        Item quan = ItemService.gI().otpts((short)1051);
        Item gang = ItemService.gI().otpts((short)1054);
        Item giay = ItemService.gI().otpts((short)1057);
        Item nhan = ItemService.gI().otpts((short)1060);
        ao.itemOptions.add(new Item.ItemOption(128,0));
        quan.itemOptions.add(new Item.ItemOption(128,0));
        gang.itemOptions.add(new Item.ItemOption(128,0));
        giay.itemOptions.add(new Item.ItemOption(128,0));
        nhan.itemOptions.add(new Item.ItemOption(128,0));
        ao.itemOptions.add(new Item.ItemOption(140,0));
        quan.itemOptions.add(new Item.ItemOption(140,0));
        gang.itemOptions.add(new Item.ItemOption(140,0));
        giay.itemOptions.add(new Item.ItemOption(140,0));
        nhan.itemOptions.add(new Item.ItemOption(140,0));
        ao.itemOptions.add(new Item.ItemOption(30,0));
        quan.itemOptions.add(new Item.ItemOption(30,0));
        gang.itemOptions.add(new Item.ItemOption(30,0));
        giay.itemOptions.add(new Item.ItemOption(30,0));
        nhan.itemOptions.add(new Item.ItemOption(30,0));
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 4) {
            InventoryServiceNew.gI().addItemBag(player, ao);
            InventoryServiceNew.gI().addItemBag(player, quan);
            InventoryServiceNew.gI().addItemBag(player, gang);
            InventoryServiceNew.gI().addItemBag(player, giay);
            InventoryServiceNew.gI().addItemBag(player, nhan);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được set thiên sứ ");
            InventoryServiceNew.gI().subQuantityItemsBag(player, hq, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 5 ô trống hành trang");
        }
    }
    }
    
        public void setkamejoko(Player player) throws Exception {
        for (int i = 0 ; i < 12;i++){
        Item hq = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1105+i);
        Item ao = ItemService.gI().otpts((short)1048);
        Item quan = ItemService.gI().otpts((short)1051);
        Item gang = ItemService.gI().otpts((short)1054);
        Item giay = ItemService.gI().otpts((short)1057);
        Item nhan = ItemService.gI().otpts((short)1060);
        ao.itemOptions.add(new Item.ItemOption(129,0));
        quan.itemOptions.add(new Item.ItemOption(129,0));
        gang.itemOptions.add(new Item.ItemOption(129,0));
        giay.itemOptions.add(new Item.ItemOption(129,0));
        nhan.itemOptions.add(new Item.ItemOption(129,0));
        ao.itemOptions.add(new Item.ItemOption(141,0));
        quan.itemOptions.add(new Item.ItemOption(141,0));
        gang.itemOptions.add(new Item.ItemOption(141,0));
        giay.itemOptions.add(new Item.ItemOption(141,0));
        nhan.itemOptions.add(new Item.ItemOption(141,0));
        ao.itemOptions.add(new Item.ItemOption(30,0));
        quan.itemOptions.add(new Item.ItemOption(30,0));
        gang.itemOptions.add(new Item.ItemOption(30,0));
        giay.itemOptions.add(new Item.ItemOption(30,0));
        nhan.itemOptions.add(new Item.ItemOption(30,0));
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 4) {
            InventoryServiceNew.gI().addItemBag(player, ao);
            InventoryServiceNew.gI().addItemBag(player, quan);
            InventoryServiceNew.gI().addItemBag(player, gang);
            InventoryServiceNew.gI().addItemBag(player, giay);
            InventoryServiceNew.gI().addItemBag(player, nhan);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được set thiên sứ ");
            InventoryServiceNew.gI().subQuantityItemsBag(player, hq, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 5 ô trống hành trang");
        }
    }
    }
        
    public void setgodki(Player player) throws Exception {
        for (int i = 0 ; i < 12;i++){
        Item hq = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1105+i);
        Item ao = ItemService.gI().otpts((short)1049);
        Item quan = ItemService.gI().otpts((short)1052);
        Item gang = ItemService.gI().otpts((short)1055);
        Item giay = ItemService.gI().otpts((short)1058);
        Item nhan = ItemService.gI().otpts((short)1061);
        ao.itemOptions.add(new Item.ItemOption(130,0));
        quan.itemOptions.add(new Item.ItemOption(130,0));
        gang.itemOptions.add(new Item.ItemOption(130,0));
        giay.itemOptions.add(new Item.ItemOption(130,0));
        nhan.itemOptions.add(new Item.ItemOption(130,0));
        ao.itemOptions.add(new Item.ItemOption(142,0));
        quan.itemOptions.add(new Item.ItemOption(142,0));
        gang.itemOptions.add(new Item.ItemOption(142,0));
        giay.itemOptions.add(new Item.ItemOption(142,0));
        nhan.itemOptions.add(new Item.ItemOption(142,0));
        ao.itemOptions.add(new Item.ItemOption(30,0));
        quan.itemOptions.add(new Item.ItemOption(30,0));
        gang.itemOptions.add(new Item.ItemOption(30,0));
        giay.itemOptions.add(new Item.ItemOption(30,0));
        nhan.itemOptions.add(new Item.ItemOption(30,0));
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 4) {
            InventoryServiceNew.gI().addItemBag(player, ao);
            InventoryServiceNew.gI().addItemBag(player, quan);
            InventoryServiceNew.gI().addItemBag(player, gang);
            InventoryServiceNew.gI().addItemBag(player, giay);
            InventoryServiceNew.gI().addItemBag(player, nhan);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được set thiên sứ ");
            InventoryServiceNew.gI().subQuantityItemsBag(player, hq, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 5 ô trống hành trang");
        }
    }
    }
    
    public void setgoddam(Player player) throws Exception {
        for (int i = 0 ; i < 12;i++){
        Item hq = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1105+i);
        Item ao = ItemService.gI().otpts((short)1049);
        Item quan = ItemService.gI().otpts((short)1052);
        Item gang = ItemService.gI().otpts((short)1055);
        Item giay = ItemService.gI().otpts((short)1058);
        Item nhan = ItemService.gI().otpts((short)1061);
        ao.itemOptions.add(new Item.ItemOption(131,0));
        quan.itemOptions.add(new Item.ItemOption(131,0));
        gang.itemOptions.add(new Item.ItemOption(131,0));
        giay.itemOptions.add(new Item.ItemOption(131,0));
        nhan.itemOptions.add(new Item.ItemOption(131,0));
        ao.itemOptions.add(new Item.ItemOption(143,0));
        quan.itemOptions.add(new Item.ItemOption(143,0));
        gang.itemOptions.add(new Item.ItemOption(143,0));
        giay.itemOptions.add(new Item.ItemOption(143,0));
        nhan.itemOptions.add(new Item.ItemOption(143,0));
        ao.itemOptions.add(new Item.ItemOption(30,0));
        quan.itemOptions.add(new Item.ItemOption(30,0));
        gang.itemOptions.add(new Item.ItemOption(30,0));
        giay.itemOptions.add(new Item.ItemOption(30,0));
        nhan.itemOptions.add(new Item.ItemOption(30,0));
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 4) {
            InventoryServiceNew.gI().addItemBag(player, ao);
            InventoryServiceNew.gI().addItemBag(player, quan);
            InventoryServiceNew.gI().addItemBag(player, gang);
            InventoryServiceNew.gI().addItemBag(player, giay);
            InventoryServiceNew.gI().addItemBag(player, nhan);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được set thiên sứ ");
            InventoryServiceNew.gI().subQuantityItemsBag(player, hq, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 5 ô trống hành trang");
        }
    }
    }
    
    public void setsummon(Player player) throws Exception {
        for (int i = 0 ; i < 12;i++){
        Item hq = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1105+i);
        Item ao = ItemService.gI().otpts((short)1049);
        Item quan = ItemService.gI().otpts((short)1052);
        Item gang = ItemService.gI().otpts((short)1055);
        Item giay = ItemService.gI().otpts((short)1058);
        Item nhan = ItemService.gI().otpts((short)1061);
        ao.itemOptions.add(new Item.ItemOption(132,0));
        quan.itemOptions.add(new Item.ItemOption(132,0));
        gang.itemOptions.add(new Item.ItemOption(132,0));
        giay.itemOptions.add(new Item.ItemOption(132,0));
        nhan.itemOptions.add(new Item.ItemOption(132,0));
        ao.itemOptions.add(new Item.ItemOption(144,0));
        quan.itemOptions.add(new Item.ItemOption(144,0));
        gang.itemOptions.add(new Item.ItemOption(144,0));
        giay.itemOptions.add(new Item.ItemOption(144,0));
        nhan.itemOptions.add(new Item.ItemOption(144,0));
        ao.itemOptions.add(new Item.ItemOption(30,0));
        quan.itemOptions.add(new Item.ItemOption(30,0));
        gang.itemOptions.add(new Item.ItemOption(30,0));
        giay.itemOptions.add(new Item.ItemOption(30,0));
        nhan.itemOptions.add(new Item.ItemOption(30,0));
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 4) {
            InventoryServiceNew.gI().addItemBag(player, ao);
            InventoryServiceNew.gI().addItemBag(player, quan);
            InventoryServiceNew.gI().addItemBag(player, gang);
            InventoryServiceNew.gI().addItemBag(player, giay);
            InventoryServiceNew.gI().addItemBag(player, nhan);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được set thiên sứ ");
            InventoryServiceNew.gI().subQuantityItemsBag(player, hq, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 5 ô trống hành trang");
        }
    }
    }
    
        public void setgodgalick(Player player) throws Exception {
        for (int i = 0 ; i < 12;i++){
        Item hq = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1105+i);
        Item ao = ItemService.gI().otpts((short)1050);
        Item quan = ItemService.gI().otpts((short)1053);
        Item gang = ItemService.gI().otpts((short)1056);
        Item giay = ItemService.gI().otpts((short)1059);
        Item nhan = ItemService.gI().otpts((short)1062);
        ao.itemOptions.add(new Item.ItemOption(133,0));
        quan.itemOptions.add(new Item.ItemOption(133,0));
        gang.itemOptions.add(new Item.ItemOption(133,0));
        giay.itemOptions.add(new Item.ItemOption(133,0));
        nhan.itemOptions.add(new Item.ItemOption(133,0));
        ao.itemOptions.add(new Item.ItemOption(136,0));
        quan.itemOptions.add(new Item.ItemOption(136,0));
        gang.itemOptions.add(new Item.ItemOption(136,0));
        giay.itemOptions.add(new Item.ItemOption(136,0));
        nhan.itemOptions.add(new Item.ItemOption(136,0));
        ao.itemOptions.add(new Item.ItemOption(30,0));
        quan.itemOptions.add(new Item.ItemOption(30,0));
        gang.itemOptions.add(new Item.ItemOption(30,0));
        giay.itemOptions.add(new Item.ItemOption(30,0));
        nhan.itemOptions.add(new Item.ItemOption(30,0));
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 4) {
            InventoryServiceNew.gI().addItemBag(player, ao);
            InventoryServiceNew.gI().addItemBag(player, quan);
            InventoryServiceNew.gI().addItemBag(player, gang);
            InventoryServiceNew.gI().addItemBag(player, giay);
            InventoryServiceNew.gI().addItemBag(player, nhan);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được set thiên sứ ");
            InventoryServiceNew.gI().subQuantityItemsBag(player, hq, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 5 ô trống hành trang");
        }
    }
    }
        
    public void setmonkey(Player player) throws Exception {
        for (int i = 0 ; i < 12;i++){
        Item hq = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1105+i);
        Item ao = ItemService.gI().otpts((short)1050);
        Item quan = ItemService.gI().otpts((short)1053);
        Item gang = ItemService.gI().otpts((short)1056);
        Item giay = ItemService.gI().otpts((short)1059);
        Item nhan = ItemService.gI().otpts((short)1062);
        ao.itemOptions.add(new Item.ItemOption(134,0));
        quan.itemOptions.add(new Item.ItemOption(134,0));
        gang.itemOptions.add(new Item.ItemOption(134,0));
        giay.itemOptions.add(new Item.ItemOption(134,0));
        nhan.itemOptions.add(new Item.ItemOption(134,0));
        ao.itemOptions.add(new Item.ItemOption(137,0));
        quan.itemOptions.add(new Item.ItemOption(137,0));
        gang.itemOptions.add(new Item.ItemOption(137,0));
        giay.itemOptions.add(new Item.ItemOption(137,0));
        nhan.itemOptions.add(new Item.ItemOption(137,0));
        ao.itemOptions.add(new Item.ItemOption(30,0));
        quan.itemOptions.add(new Item.ItemOption(30,0));
        gang.itemOptions.add(new Item.ItemOption(30,0));
        giay.itemOptions.add(new Item.ItemOption(30,0));
        nhan.itemOptions.add(new Item.ItemOption(30,0));
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 4) {
            InventoryServiceNew.gI().addItemBag(player, ao);
            InventoryServiceNew.gI().addItemBag(player, quan);
            InventoryServiceNew.gI().addItemBag(player, gang);
            InventoryServiceNew.gI().addItemBag(player, giay);
            InventoryServiceNew.gI().addItemBag(player, nhan);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được set thiên sứ ");
            InventoryServiceNew.gI().subQuantityItemsBag(player, hq, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 5 ô trống hành trang");
        }
    }
    }
            
    public void setgodhp(Player player) throws Exception {
        for (int i = 0 ; i < 12;i++){
        Item hq = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1105+i);
        Item ao = ItemService.gI().otpts((short)1050);
        Item quan = ItemService.gI().otpts((short)1053);
        Item gang = ItemService.gI().otpts((short)1056);
        Item giay = ItemService.gI().otpts((short)1059);
        Item nhan = ItemService.gI().otpts((short)1062);
        ao.itemOptions.add(new Item.ItemOption(135,0));
        quan.itemOptions.add(new Item.ItemOption(135,0));
        gang.itemOptions.add(new Item.ItemOption(135,0));
        giay.itemOptions.add(new Item.ItemOption(135,0));
        nhan.itemOptions.add(new Item.ItemOption(135,0));
        ao.itemOptions.add(new Item.ItemOption(138,0));
        quan.itemOptions.add(new Item.ItemOption(138,0));
        gang.itemOptions.add(new Item.ItemOption(138,0));
        giay.itemOptions.add(new Item.ItemOption(138,0));
        nhan.itemOptions.add(new Item.ItemOption(138,0));
        ao.itemOptions.add(new Item.ItemOption(30,0));
        quan.itemOptions.add(new Item.ItemOption(30,0));
        gang.itemOptions.add(new Item.ItemOption(30,0));
        giay.itemOptions.add(new Item.ItemOption(30,0));
        nhan.itemOptions.add(new Item.ItemOption(30,0));
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 4) {
            InventoryServiceNew.gI().addItemBag(player, ao);
            InventoryServiceNew.gI().addItemBag(player, quan);
            InventoryServiceNew.gI().addItemBag(player, gang);
            InventoryServiceNew.gI().addItemBag(player, giay);
            InventoryServiceNew.gI().addItemBag(player, nhan);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được set thiên sứ ");
            InventoryServiceNew.gI().subQuantityItemsBag(player, hq, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 5 ô trống hành trang");
        }
    }
    }

    public Item itemSKH(int itemId, int skhId) {
        Item item = createItemSetKichHoat(itemId, 1);
        if (item != null) {
            item.itemOptions.addAll(ItemService.gI().getListOptionItemShop((short) itemId));
            item.itemOptions.add(new Item.ItemOption(skhId, 1));
            item.itemOptions.add(new Item.ItemOption(optionIdSKH(skhId), 1));
            item.itemOptions.add(new Item.ItemOption(30, 1));
        }
        return item;
    }

    public static Item itemSKHDrop(int itemId, int skhId) {
        Item item = createItemSetKichHoatDrop(itemId, 1);
        if (item != null) {
            item.itemOptions.addAll(ItemService.gI().getListOptionItemShop((short) itemId));
            item.itemOptions.add(new Item.ItemOption(skhId, 1));
            item.itemOptions.add(new Item.ItemOption(optionIdSKHDrop(skhId), 1));
            item.itemOptions.add(new Item.ItemOption(30, 1));
        }
        return item;
    }

    public static Item createItemSetKichHoatDrop(int tempId, int quantity) {
        Item item = new Item();
        item.template = getTemplateDrop(tempId);
        item.quantity = quantity;
        item.itemOptions = createItemNullDrop().itemOptions;
        item.createTime = System.currentTimeMillis();
        item.content = item.getContent();
        item.info = item.getInfo();
        return item;
    }

    public static Item createItemNullDrop() {
        Item item = new Item();
        return item;
    }

    public static Template.ItemTemplate getTemplateDrop(int id) {
        return Manager.ITEM_TEMPLATES.get(id);
    }
    public static int optionIdSKHDrop(int skhId) {
        switch (skhId) {
            case 127: //Set Arriety Taiyoken
                return 139;
            case 128: //Set Arriety Genki
                return 140;
            case 129: //Set Arriety Kamejoko
                return 141;
            case 130: //Set Arriety KI
                return 142;
            case 131: //Set Arriety Dame
                return 143;
            case 132: //Set Arriety Summon
                return 144;
            case 133: //Set Arriety Galick
                return 136;
            case 134: //Set Arriety Monkey
                return 137;
            case 135: //Set Arriety HP
                return 138;
        }
        return 0;
    }

    public static Item dropSKHInMap(Player player){
        int[][] items = {{0, 6, 21, 27, 12}, {1, 7, 22, 28, 12}, {2, 8, 23, 29, 12}};
        int[][] options = {{128, 129, 127}, {130, 131, 132}, {133, 135, 134}};
        Item item = null;
        int loaiDo = Util.nextInt(0, 4);
        int setKH = Util.nextInt(0, 2);
        item = itemSKHDrop(items[player.gender][loaiDo],options[player.gender][setKH]);
        return item;
    }

    public void openPet(Player player,  Item itemUse){
        try {
            int[] id = {1328, 1329, 1336, 1337, 1338,1107, 1133, 1140, 1180, 1181};
            int randId = Util.nextInt(0, id.length-1);
            short[] icon = new short[2];
            Item item = createItemOpen(id[randId], 1);
            if(item.isNotNullItem()){
                item.itemOptions.add(new Item.ItemOption(50, Util.nextInt(20, 50)));
                item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(20, 50)));
                item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(20, 50)));
                item.itemOptions.add(new Item.ItemOption(14, Util.nextInt(4, 10)));
                icon[0] = itemUse.template.iconID;
                icon[1] = item.template.iconID;
                InventoryServiceNew.gI().subQuantityItemsBag(player, itemUse, 1);
                InventoryServiceNew.gI().addItemBag(player, item);
                InventoryServiceNew.gI().sendItemBags(player); 
                Service.gI().sendThongBao(player, "Bạn đã nhận được " + item.template.name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openDeoLung(Player player,  Item itemUse){
        try {
            int[] id = {1243,1374,1155,1166};
            int randId = Util.nextInt(0, id.length-1);
            short[] icon = new short[2];
            Item item = createItemOpen(id[randId], 1);
            if(item.isNotNullItem()){
                item.itemOptions.add(new Item.ItemOption(50, Util.nextInt(20, 50)));
                item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(20, 50)));
                item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(20, 50)));
                item.itemOptions.add(new Item.ItemOption(14, Util.nextInt(4, 10)));
                icon[0] = itemUse.template.iconID;
                icon[1] = item.template.iconID;
                InventoryServiceNew.gI().subQuantityItemsBag(player, itemUse, 1);
                InventoryServiceNew.gI().addItemBag(player, item);
                InventoryServiceNew.gI().sendItemBags(player); 
                Service.gI().sendThongBao(player, "Bạn đã nhận được " + item.template.name);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int optionItemSKH(int typeItem) {
        switch (typeItem) {
            case 0:
                return 47;
            case 1:
                return 6;
            case 2:
                return 0;
            case 3:
                return 7;
            default:
                return 14;
        }
    }

    public int pagramItemSKH(int typeItem) {
        switch (typeItem) {
            case 0:
            case 2:
                return Util.nextInt(5);
            case 1:
            case 3:
                return Util.nextInt(20, 30);
            default:
                return Util.nextInt(3);
        }
    }

    public int optionIdSKH(int skhId) {
        switch (skhId) {
            case 127: //Set Arriety Taiyoken
                return 139;
            case 128: //Set Arriety Genki
                return 140;
            case 129: //Set Arriety Kamejoko
                return 141;
            case 130: //Set Arriety KI
                return 142;
            case 131: //Set Arriety Dame
                return 143;
            case 132: //Set Arriety Summon
                return 144;
            case 133: //Set Arriety Galick
                return 136;
            case 134: //Set Arriety Monkey
                return 137;
            case 135: //Set Arriety HP
                return 138;
        }
        return 0;
    }

    public Item itemDHD(int itemId, int dhdId) {
        Item item = createItemSetKichHoat(itemId, 1);
        if (item != null) {
            item.itemOptions.add(new Item.ItemOption(dhdId, 1));
            item.itemOptions.add(new Item.ItemOption(optionIdDHD(dhdId), 1));
            item.itemOptions.add(new Item.ItemOption(30, 1));
        }
        return item;
    }

    public int optionIdDHD(int skhId) {
        switch (skhId) {
            case 127: //Set Arriety Taiyoken
                return 139;
            case 128: //Set Arriety Genki
                return 140;
            case 129: //Set Arriety Kamejoko
                return 141;
            case 130: //Set Arriety KI
                return 142;
            case 131: //Set Arriety Dame
                return 143;
            case 132: //Set Arriety Summon
                return 144;
            case 133: //Set Arriety Galick
                return 136;
            case 134: //Set Arriety Monkey
                return 137;
            case 135: //Set Arriety HP
                return 138;
        }
        return 0;
    }


    public Item randomCS_DHD(int itemId, int gender) {
        Item it = createItemOpen(itemId, 1);
        List<Integer> ao = Arrays.asList(650, 652, 654);
        List<Integer> quan = Arrays.asList(651, 653, 655);
        List<Integer> gang = Arrays.asList(657, 659, 661);
        List<Integer> giay = Arrays.asList(658, 660, 662);
        int nhd = 656;
        // trai dat
        if(itemId == 650){
            it.itemOptions.add(new Item.ItemOption(47, buffParamHuyDiet1(1600))); // áo
        }
        if(itemId == 651){
            it.itemOptions.add(new Item.ItemOption(235, buffParamHuyDiet2(10400))); // hp  // paramId = 235 va 236 co don vi hang chuc
        //    it.itemOptions.add(new Item.ItemOption(6, buffParamHuyDietThieu));
        }
        if(itemId == 657){
            it.itemOptions.add(new Item.ItemOption(0, buffParamHuyDiet1(8800))); // dame
        }
        if(itemId == 658){
           it.itemOptions.add(new Item.ItemOption(236, buffParamHuyDiet2(9600))); // ki
        //   it.itemOptions.add(new Item.ItemOption(7, buffParamHuyDietThieu));
        }
        // namec
        if(itemId == 652){
            it.itemOptions.add(new Item.ItemOption(47, buffParamHuyDiet1(1700))); // áo
        }
        if(itemId == 653){
            it.itemOptions.add(new Item.ItemOption(235, buffParamHuyDiet2(10000))); // hp
        //    it.itemOptions.add(new Item.ItemOption(6, buffParamHuyDietThieu));
        }
        if(itemId == 659){
            it.itemOptions.add(new Item.ItemOption(0, buffParamHuyDiet1(8600))); // dame
        }
        if(itemId == 650){
           it.itemOptions.add(new Item.ItemOption(236, buffParamHuyDiet2(10000))); // ki
        //   it.itemOptions.add(new Item.ItemOption(7, buffParamHuyDietThieu));
        }
        // xayda
        if(itemId == 654){
            it.itemOptions.add(new Item.ItemOption(47, buffParamHuyDiet1(1800))); // áo
        }
        if(itemId == 655){
            it.itemOptions.add(new Item.ItemOption(235, buffParamHuyDiet2(9600))); // hp
        //    it.itemOptions.add(new Item.ItemOption(6, buffParamHuyDietThieu));
        }
        if(itemId == 661){
            it.itemOptions.add(new Item.ItemOption(0, buffParamHuyDiet1(9000))); // dame
        }
        if(itemId == 652){
           it.itemOptions.add(new Item.ItemOption(236, buffParamHuyDiet2(9200))); // ki
        //   it.itemOptions.add(new Item.ItemOption(7, buffParamHuyDietThieu));
        }

        if(itemId == 656){
           it.itemOptions.add(new Item.ItemOption(14, new Random().nextInt(3) + 16)); //chí mạng 17-19%
        }
        it.itemOptions.add(new Item.ItemOption(21, 80));// yêu cầu sm 80 tỉ
        it.itemOptions.add(new Item.ItemOption(30, 1));// ko the gd
        return it;
    }

    //Cải trang sự kiện 20/11
    public Item caitrang2011(boolean rating) {
        Item item = createItemSetKichHoat(680, 1);
        item.itemOptions.add(new Item.ItemOption(76, 1));//VIP
        item.itemOptions.add(new Item.ItemOption(77, 28));//hp 28%
        item.itemOptions.add(new Item.ItemOption(103, 25));//ki 25%
        item.itemOptions.add(new Item.ItemOption(147, 24));//sd 26%
        item.itemOptions.add(new Item.ItemOption(117, 18));//Đẹp + 18% sd
        if (Util.isTrue(995, 1000) && rating) {// tỉ lệ ra hsd
            item.itemOptions.add(new Item.ItemOption(93, new Random().nextInt(3) + 1));//hsd
        }
        return item;
    }




    //610 - bong hoa
    //Phụ kiện bó hoa 20/11
    public Item phuKien2011(boolean rating) {
        Item item = createItemSetKichHoat(954, 1);
        item.itemOptions.add(new Item.ItemOption(77, new Random().nextInt(5) + 5));
        item.itemOptions.add(new Item.ItemOption(103, new Random().nextInt(5) + 5));
        item.itemOptions.add(new Item.ItemOption(147, new Random().nextInt(5) + 5));
        if (Util.isTrue(1, 100)) {
            item.itemOptions.get(Util.nextInt(item.itemOptions.size() - 1)).param = 10;
        }
        item.itemOptions.add(new Item.ItemOption(30, 1));//ko the gd
        if (Util.isTrue(995, 1000) && rating) {// tỉ lệ ra hsd
            item.itemOptions.add(new Item.ItemOption(93, new Random().nextInt(3) + 1));//hsd
        }
        return item;
    }

    public Item vanBay2011(boolean rating) {
        Item item = createItemSetKichHoat(795, 1);
        item.itemOptions.add(new Item.ItemOption(89, 1));
        item.itemOptions.add(new Item.ItemOption(30, 1));//ko the gd
        if (Util.isTrue(950, 1000) && rating) {// tỉ lệ ra hsd
            item.itemOptions.add(new Item.ItemOption(93, new Random().nextInt(3) + 1));//hsd
        }
        return item;
    }

        public Item hps() {
        Item item = createItemSetKichHoat(2048, 1);//dac biet
        return item;
    }
        
    public Item daPhapSu() {
        Item item = createItemSetKichHoat(2046, 1);//dac biet
        return item;
    }
    
    public Item buaGPS() {
        Item item = createItemSetKichHoat(2047, 1);//dac biet
        return item;
    }
   
    
    public Item daBaoVe() {
        Item item = createItemSetKichHoat(987, 1);
        item.itemOptions.add(new Item.ItemOption(30, 1));//ko the gd
        return item;
    }
    
    public Item CShongngoc() {
        Item item = createItemSetKichHoat(722, 10);//ko the gd
        return item;
    }

    public Item randomRac() {
        short[] racs = {18, 17, 16, 15};
        Item item = createItemSetKichHoat(racs[Util.nextInt(racs.length - 1)], 1);
        if (optionRac(item.template.id) != 0) {
            item.itemOptions.add(new Item.ItemOption(optionRac(item.template.id), 1));
        }
        return item;
    }
    
    public Item hpscc() {
        Item item = createItemSetKichHoat(2049, 1);//dac biet
        return item;
    }
    
    public Item randomRaccc() {
        short[] racs = {674,1249};
        Item item = createItemSetKichHoat(racs[Util.nextInt(racs.length)], 1);        
        return item;
    }
    public Item DaNguSac() {
        Item item = createItemSetKichHoat(674, Util.nextInt(1,3));        
        return item;
    }
    
    public Item LuaThan() {
        Item item = createItemSetKichHoat(1250, Util.nextInt(1,10));        
        return item;
    }
    
    public Item daPhapSucc() {
        Item item = createItemSetKichHoat(2046, Util.nextInt(2,5));//dac biet
        return item;
    }
    
    public Item ManhTSthuong() {
        short[] racs = {1066,1067,1068};
        Item item = createItemSetKichHoat(racs[Util.nextInt(racs.length)], Util.nextInt(10,50));        
        return item;
    }
    public Item ManhTShiem() {
        short[] racs = {1069,1070};
        Item item = createItemSetKichHoat(racs[Util.nextInt(racs.length)], Util.nextInt(10,50));        
        return item;
    }
    public Item CShongngoccc() {
        Item item = createItemSetKichHoat(1232, Util.nextInt(1,3));//ko the gd
        return item;
    }
    public byte optionRac(short itemId) {
        switch (itemId) {
            case 220:
                return 71;
            case 221:
                return 70;
            case 222:
                return 69;
            case 224:
                return 67;
            case 223:
                return 68;
            default:
                return 0;
        }
    }

    public void openBoxVip(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) <= 1) {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 2 ô trống hành trang");
            return;
        }
        if (player.inventory.event < 3000) {
            Service.getInstance().sendThongBao(player, "Bạn không đủ bông...");
            return;
        }
        Item item;
        if (Util.isTrue(45, 100)) {
            item = caitrang2011(false);
        } else {
            item = phuKien2011(false);
        }
        short[] icon = new short[2];
        icon[0] = 6983;
        icon[1] = item.template.iconID;
        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
        player.inventory.event -= 3000;
        Service.getInstance().sendThongBao(player, "Bạn đã nhận được " + item.template.name);
        CombineServiceNew.gI().sendEffectOpenItem(player, icon[0], icon[1]);
    }

    public void giaobong(Player player, int quantity) {
        if (quantity > 10000) return;
        try {
            Item itemUse = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 610);
            if (itemUse.quantity < quantity) {
                Service.getInstance().sendThongBao(player, "Bạn không đủ bông...");
                return;
            }
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemUse, quantity);
            Item item = createItemSetKichHoat(736, (quantity / 100));
            item.itemOptions.add(new Item.ItemOption(30, 1));//ko the gd
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được x" + (quantity / 100) + " " + item.template.name);
        } catch (Exception e) {
            Service.getInstance().sendThongBao(player, "Bạn không đủ bông...");
        }
    }

    public Item PK_WC(int itemId) {
        Item phukien = createItemSetKichHoat(itemId, 1);
        int co = 983;
        int cup = 982;
        int bong = 966;
        if (cup == itemId) {
            phukien.itemOptions.add(new Item.ItemOption(77, new Random().nextInt(6) + 5)); // hp 5-10%
        }
        if (co == itemId) {
            phukien.itemOptions.add(new Item.ItemOption(103, new Random().nextInt(6) + 5)); // ki 5-10%
        }
        if (bong == itemId) {
            phukien.itemOptions.add(new Item.ItemOption(50, new Random().nextInt(6) + 5)); // sd 5- 10%
        }
        phukien.itemOptions.add(new Item.ItemOption(192, 1));//basil
        phukien.itemOptions.add(new Item.ItemOption(193, 1));//(2 món kích hoạt ....)
        if (Util.isTrue(99, 100)) {// tỉ lệ ra hsd
            phukien.itemOptions.add(new Item.ItemOption(93, new Random().nextInt(2) + 1));//hsd
        }
        return phukien;
    }

    //Cải trang Gohan WC
    public Item CT_WC(boolean rating) {
        Item caitrang = createItemSetKichHoat(883, 1);
        caitrang.itemOptions.add(new Item.ItemOption(77, 30));// hp 30%
        caitrang.itemOptions.add(new Item.ItemOption(103, 15));// ki 15%
        caitrang.itemOptions.add(new Item.ItemOption(50, 20));// sd 20%
        caitrang.itemOptions.add(new Item.ItemOption(192, 1));//basil
        caitrang.itemOptions.add(new Item.ItemOption(193, 1));//(2 món kích hoạt ....)
        if (Util.isTrue(99, 100) && rating) {// tỉ lệ ra hsd
            caitrang.itemOptions.add(new Item.ItemOption(93, new Random().nextInt(2) + 1));//hsd
        }
        return caitrang;
    }

    public void openDTS(Player player) {
        //check sl đồ tl, đồ hd
        if (player.combineNew.itemsCombine.stream().filter(item -> item.template.id >= 555 && item.template.id <= 567).count() < 1) {
            Service.getInstance().sendThongBao(player, "Thiếu đồ thần linh");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.template.id >= 650 && item.template.id <= 662).count() < 2) {
            Service.getInstance().sendThongBao(player, "Thiếu đồ hủy diệt");
            return;
        }
        if (player.combineNew.itemsCombine.size() != 3) {
            Service.getInstance().sendThongBao(player, "Thiếu đồ");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            Item itemTL = player.combineNew.itemsCombine.stream().filter(item -> item.template.id >= 555 && item.template.id <= 567).findFirst().get();
            List<Item> itemHDs = player.combineNew.itemsCombine.stream().filter(item -> item.template.id >= 650 && item.template.id <= 662).collect(Collectors.toList());
            short[][] itemIds = {{1048, 1051, 1054, 1057, 1060}, {1049, 1052, 1055, 1058, 1061}, {1050, 1053, 1056, 1059, 1062}}; // thứ tự td - 0,nm - 1, xd - 2

            Item itemTS = DoThienSu(itemIds[player.gender][itemTL.template.type], player.gender);
            itemTS.itemOptions.add(new ItemOption(30, 1));
            itemTS.itemOptions.add(new ItemOption(21, 120));
            InventoryServiceNew.gI().addItemBag(player, itemTS);

            InventoryServiceNew.gI().subQuantityItemsBag(player, itemTL, 1);
            itemHDs.forEach(item -> InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1));

            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendThongBao(player, "Bạn đã nhận được " + itemTS.template.name);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }

    public Item DoThienSu(int itemId, int gender) {
        Item dots = createItemOpen(itemId, 1);
        List<Integer> ao = Arrays.asList(1048, 1049, 1050);
        List<Integer> quan = Arrays.asList(1051, 1052, 1053);
        List<Integer> gang = Arrays.asList(1054, 1055, 1056);
        List<Integer> giay = Arrays.asList(1057, 1058, 1059);
        List<Integer> nhan = Arrays.asList(1060, 1061, 1062);
        // trai dat
        if(itemId == 1048){
            dots.itemOptions.add(new Item.ItemOption(47, buffParamThienSu1(1600))); // áo
        }
        if(itemId == 1051){
            dots.itemOptions.add(new Item.ItemOption(235, buffParamThienSu2(10400))); // hp
        }
        if(itemId == 1054){
            dots.itemOptions.add(new Item.ItemOption(0, buffParamThienSu1(8800))); // dame
        }
        if(itemId == 1057){
           dots.itemOptions.add(new Item.ItemOption(236, buffParamThienSu2(9600))); // ki
        
        }
        // namec
        if(itemId == 1049){
            dots.itemOptions.add(new Item.ItemOption(47, buffParamThienSu1(1700))); // áo
        }
        if(itemId == 1052){
            dots.itemOptions.add(new Item.ItemOption(235, buffParamThienSu2(10000))); // hp
        
        }
        if(itemId == 1055){
            dots.itemOptions.add(new Item.ItemOption(0, buffParamThienSu1(8600))); // dame
        }
        if(itemId == 1058){
           dots.itemOptions.add(new Item.ItemOption(236, buffParamThienSu2(10000))); // ki
        
        }
        // xayda
        if(itemId == 1050){
            dots.itemOptions.add(new Item.ItemOption(47, buffParamThienSu1(1800))); // áo
        }
        if(itemId == 1053){
            dots.itemOptions.add(new Item.ItemOption(235, buffParamThienSu2(9600))); // hp
       
        }
        if(itemId == 1056){
            dots.itemOptions.add(new Item.ItemOption(0, buffParamThienSu1(9000))); // dame
        }
        if(itemId == 1059){
           dots.itemOptions.add(new Item.ItemOption(236, buffParamThienSu2(9200))); // ki

        }    
        // Nhẫn
        if (nhan.contains(itemId)) {
            if (dots.template.id == 1060){
            dots.itemOptions.add(new Item.ItemOption(14, Util.nextInt(22, 27))); // Nhẫn TĐ
        }}
        if (nhan.contains(itemId)) {
            if (dots.template.id == 1061){
            dots.itemOptions.add(new Item.ItemOption(14, Util.nextInt(22, 27))); // Nhẫn Namec
        }}
        if (nhan.contains(itemId)) {
            if (dots.template.id == 1062){
            dots.itemOptions.add(new Item.ItemOption(14, Util.nextInt(22, 27))); // Nhẫn Xayda
        }}

    //    dots.itemOptions.add(new Item.ItemOption(21, 120));
        return dots;
    }

    public List<Item.ItemOption> getListOptionItemShop(short id) {
        List<Item.ItemOption> list = new ArrayList<>();
        Manager.SHOPS.forEach(shop -> shop.tabShops.forEach(tabShop -> tabShop.itemShops.forEach(itemShop -> {
            if (itemShop.temp.id == id && list.size() == 0) {
                list.addAll(itemShop.options);
            }
        })));
        return list;
    }
}
