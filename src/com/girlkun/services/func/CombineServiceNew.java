package com.girlkun.services.func;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.item.Item;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.npc.Npc;
import com.girlkun.models.npc.NpcManager;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.server.ServerNotify;
import com.girlkun.network.io.Message;
import com.girlkun.services.*;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

import java.util.*;
import java.util.stream.Collectors;


public class CombineServiceNew {

    private static final int COST_DOI_VE_DOI_DO_HUY_DIET = 500000000;
    private static final int COST_DAP_DO_KICH_HOAT = 1000000000;
    private static final int RUBY_DAP_DO_KICH_HOAT = 10000;
    private static final int GEM_DAP_DO_KICH_HOAT = 10000;
    private static final int COST_DOI_KICH_HOAT = 1000000;
    private static final int COST_DOI_MANH_KICH_HOAT = 500000000;

    private static final int COST = 500000000;
     private static final int RUBY = 20000;
     
     private static final long COST_TINH_LUYEN = 40_000_000_000L;
     private static final int RUBY_TINH_LUYEN = 10000;
     private static final int GEM_TINH_LUYEN = 10000;

    private static final int TIME_COMBINE = 1500;

    private static final byte MAX_STAR_ITEM = 8;
    private static final byte MAX_STAR_ITEM_TC1 = 8;
    private static final byte MAX_LEVEL_ITEM = 10;

    private static final byte OPEN_TAB_COMBINE = 0;
    private static final byte REOPEN_TAB_COMBINE = 1;
    private static final byte COMBINE_SUCCESS = 2;
    private static final byte COMBINE_FAIL = 3;
    private static final byte COMBINE_CHANGE_OPTION = 4;
    private static final byte COMBINE_DRAGON_BALL = 5;
    public static final byte OPEN_ITEM = 6;

    public static final int EP_SAO_TRANG_BI = 500;
    public static final int PHA_LE_HOA_TRANG_BI = 501;
    public static final int CHUYEN_HOA_TRANG_BI = 502;
    public static final int DOI_SKH_THANH_DNS = 503;
    public static final int DAP_SET_KICH_HOAT = 504;
    public static final int DAP_SET_KICH_HOAT_HD = 10000;
    public static final int DAP_SET_KICH_HOAT_TS = 10001;
    public static final int TAY_SKH_TRANG_BI = 10002;
    public static final int KHAC_CHI_SO_DA = 10003;
    public static final int KHAC_CHI_SO_NGOC_BOI = 10004;
    public static final int THANG_CAP_TRANG_BI = 10005;
    public static final int PHA_LE_HOA_TRANG_BI_THANG_CAP = 10006;
    public static final int EP_SAO_TRANG_BI_THANG_CAP = 10007;
    public static final int TIEN_HOA_NGOC_BOI = 10008;
    public static final int TINH_LUYEN_TRANG_BI = 10009;
    public static final int REN_TRANG_BI = 100010;
//    public static final int DOI_MANH_KICH_HOAT = 505;
//    public static final int DOI_CHUOI_KIEM = 506;
//    public static final int DOI_LUOI_KIEM = 507;
//    public static final int DOI_KIEM_THAN = 508;
//    public static final int OPTION_PORATA = 508;

    public static final int PS_HOA_TRANG_BI = 2150;
    public static final int TAY_PS_HOA_TRANG_BI = 2151;
    public static final int NANG_CAP_CAI_TRANG_SSJ = 2149;
    public static final int TIEN_HOA_CAI_TRANG_BABY_VEGETA = 2148;
    public static final int TIEN_HOA_CAI_TRANG_BLACK_GOKU = 2147;
    public static final int TIEN_HOA_CAI_TRANG_BILL = 2146;
    public static final int TIEN_HOA_CAI_TRANG_HEARTS_GOLD = 2145;
    public static final int NANG_CAP_VAT_PHAM = 510;
    public static final int NANG_CAP_BONG_TAI = 511;
    public static final int MO_CHI_SO_BONG_TAI = 519;
    public static final int LAM_PHEP_NHAP_DA = 512;
    public static final int NHAP_NGOC_RONG = 513;
    public static final int PHAN_RA_DO_THAN_LINH = 514;
    public static final int NANG_CAP_DO_TS = 515;
    public static final int NANG_CAP_SKH_VIP = 516;
    public static final int AN_TRANG_BI = 517;
    public static final int CHE_TAO_TRANG_BI_TS = 520;
    
    private static final int GOLD_BONG_TAI = 500_000_000;
    private static final int GEM_BONG_TAI = 5_000;
    private static final int RATIO_BONG_TAI = 30;
    private static final int RATIO_NANG_CAP = 20;
    private static final int RATIO_LINH_THU = 95;


    private final Npc baHatMit;
    private final Npc onggianoel;
    private final Npc npsthiensu64;
    


    private static CombineServiceNew i;

    public CombineServiceNew() {
        this.baHatMit = NpcManager.getNpc(ConstNpc.BA_HAT_MIT);
        this.onggianoel = NpcManager.getNpc(ConstNpc.ONG_GIA_NOEL);
        this.npsthiensu64 = NpcManager.getNpc(ConstNpc.NPC_64);
    }

    public static CombineServiceNew gI() {
        if (i == null) {
            i = new CombineServiceNew();
        }
        return i;
    }

    /**
     * Mở tab đập đồ
     *
     * @param player
     * @param type   kiểu đập đồ
     */
    public void openTabCombine(Player player, int type) {
        player.combineNew.setTypeCombine(type);
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_TAB_COMBINE);
            msg.writer().writeUTF(getTextInfoTabCombine(type));
            msg.writer().writeUTF(getTextTopTabCombine(type));
            if (player.iDMark.getNpcChose() != null) {
                msg.writer().writeShort(player.iDMark.getNpcChose().tempId);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiển thị thông tin đập đồ
     *
     * @param player
     */
    public void showInfoCombine(Player player, int[] index) {
        player.combineNew.clearItemCombine();
        if (index.length > 0) {
            for (int i = 0; i < index.length; i++) {
                player.combineNew.itemsCombine.add(player.inventory.itemsBag.get(index[i]));
            }
        }
        switch (player.combineNew.typeCombine) {
            
            case DAP_SET_KICH_HOAT:
                
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item  dhd = null, dtl = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id == 674) {
                                dtl = item;
                            } else if (item.template.id >= 555 && item.template.id <= 567) {
                                dhd = item;
                            } else{
                                
                                Service.getInstance().sendThongBao(player, "Đồ thân linh thôi");
                                break;
                            }
                        }
                    }
                    if (dhd != null && dhd.isSKH() && dtl != null ){
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Thần Linh chưa có SKH và 100 viên Đá Ngũ Sắc", "Đóng");
                    }
                    else if (dhd != null && dtl != null) {
                        String npcSay = "|6|" + dhd.template.name + "\n";
                        for (Item.ItemOption io : dhd.itemOptions) {
                            npcSay += "|2|" + io.getOptionString() + "\n";
                        }
                        npcSay += "Ngươi có muốn chuyển hóa thành\n";
                        npcSay += "|1|" + getNameItemC1(dhd.template.gender, dhd.template.type)
                                + " (ngẫu nhiên Set Kích Hoạt)\n|7|Tỉ lệ thành công " + (dtl != null ? "100%" : "40%") + "\n|2|Cần "
                                + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " Vàng" + Util.numberToMoney(RUBY_DAP_DO_KICH_HOAT) + " Hồng Ngọc" + Util.numberToMoney(GEM_DAP_DO_KICH_HOAT) + " Ngọc Xanh" ;
                        if (player.inventory.gold >= COST_DAP_DO_KICH_HOAT) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Cần " + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " Vàng" + Util.numberToMoney(RUBY_DAP_DO_KICH_HOAT) + " Hồng Ngọc" + Util.numberToMoney(GEM_DAP_DO_KICH_HOAT) + " Ngọc Xanh");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Chuẩn bị đủ tiền rồi hãy gặp ta!!!");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Thần Linh và 100 viên Đá Ngũ Sắc", "Đóng");
                    }
                }
                break;
                
            case DAP_SET_KICH_HOAT_HD:
                if (player.combineNew.itemsCombine.size() == 2) {
                    boolean dungDo;
                    Item  dhd = null, dtl = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id == 674) {
                                dtl = item;
                            } else if (item.template.id >= 650 && item.template.id <= 662) {
                                dhd = item;
                            }
                        }
                    }
                    if (dhd != null && dhd.isSKH() && dtl != null ){
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Hủy Diệt chưa có SKH và 150 viên Đá Ngũ Sắc", "Đóng");
                    }
                    else if (dhd != null && dtl != null) {
                        String npcSay = "|6|" + dhd.template.name + "\n";
                        for (Item.ItemOption io : dhd.itemOptions) {
                            npcSay += "|2|" + io.getOptionString() + "\n";
                        }
                        npcSay += "Ngươi có muốn chuyển hóa thành\n";
                        npcSay += "|1|" + getNameItemC1(dhd.template.gender, dhd.template.type)
                                + " (ngẫu nhiên Set Kích Hoạt)\n|7|Tỉ lệ thành công " + (dtl != null ? "100%" : "40%") + "\n|2|Cần "
                                + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " Vàng" + Util.numberToMoney(RUBY_DAP_DO_KICH_HOAT) + " Hồng Ngọc" + Util.numberToMoney(GEM_DAP_DO_KICH_HOAT) + " Ngọc Xanh" ;
                        if (player.inventory.gold >= COST_DAP_DO_KICH_HOAT) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Cần " + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " Vàng" + Util.numberToMoney(RUBY_DAP_DO_KICH_HOAT) + " Hồng Ngọc" + Util.numberToMoney(GEM_DAP_DO_KICH_HOAT) + " Ngọc Xanh");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Chuẩn bị đủ tiền rồi hãy gặp ta!!!");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Hủy Diệt và 150 viên Đá Ngũ Sắc", "Đóng");
                    }
                }
                break;
            case DAP_SET_KICH_HOAT_TS:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item  dhd = null, dtl = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id == 674) {
                                dtl = item;
                            } else if (item.template.id >= 1048 && item.template.id <= 1062) {
                                dhd = item;
                            }
                        }
                    }
                    if (dhd != null && dhd.isSKH() && dtl != null ){
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Thiên Sứ chưa có SKH và 200 viên Đá Ngũ Sắc", "Đóng");
                    }
                    else if (dhd != null && dtl != null) {
                        String npcSay = "|6|" + dhd.template.name + "\n";
                        for (Item.ItemOption io : dhd.itemOptions) {
                            npcSay += "|2|" + io.getOptionString() + "\n";
                        }
                        npcSay += "Ngươi có muốn chuyển hóa thành\n";
                        npcSay += "|1|" + getNameItemC1(dhd.template.gender, dhd.template.type)
                                + " (ngẫu nhiên Set Kích Hoạt)\n|7|Tỉ lệ thành công " + (dtl != null ? "100%" : "40%") + "\n|2|Cần "
                                + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " Vàng" + Util.numberToMoney(RUBY_DAP_DO_KICH_HOAT) + " Hồng Ngọc" + Util.numberToMoney(GEM_DAP_DO_KICH_HOAT) + " Ngọc Xanh" ;
                        if (player.inventory.gold >= COST_DAP_DO_KICH_HOAT) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Cần " + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " Vàng" + Util.numberToMoney(RUBY_DAP_DO_KICH_HOAT) + " Hồng Ngọc" + Util.numberToMoney(GEM_DAP_DO_KICH_HOAT) + " Ngọc Xanh");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Chuẩn bị đủ tiền rồi hãy gặp ta!!!");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Thiên Sứ và 200 viên Đá Ngũ Sắc", "Đóng");
                    }
                }
                break;
                
            case TAY_SKH_TRANG_BI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item  dhd = null, 
                    buatay = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id == 2047) {
                                buatay = item;
                            } else if (item.template.id >= 650 && item.template.id <= 662 
                                    || item.template.id >= 1048 && item.template.id <= 1062
                                    ||item.template.id >= 555 && item.template.id <= 567) {
                                dhd = item;
                            }
                        }
                    }
                    int level = 0;
                    int level2 = 0;
                    int level3 = 0;
                Item.ItemOption optionLevel = null;
                Item.ItemOption optionLevel2 = null;
                Item.ItemOption optionLevel3 = null;
                for (Item.ItemOption io : dhd.itemOptions) {
                    if (io.optionTemplate.id >= 127 && io.optionTemplate.id <= 135 ) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                for (Item.ItemOption io : dhd.itemOptions) {
                    if (io.optionTemplate.id >= 136 && io.optionTemplate.id <= 144) {
                        level2 = io.param;
                        optionLevel2 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : dhd.itemOptions) {
                    if (io.optionTemplate.id ==30) {
                        level3 = io.param;
                        optionLevel3 = io;
                        break;
                    }
                }
                
                    if ( dhd == null || buatay == null ){
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Kích Hoạt và 1 Bùa Tẩy Pháp Sư", "Đóng");
                    }
                    else if (dhd.isSKH() && dhd != null && buatay != null) {
                        String npcSay = "|6|" + dhd.template.name + "\n";
                        for (Item.ItemOption io : dhd.itemOptions) {
                            npcSay += "|2|" + io.getOptionString() + "\n";
                        }
                        npcSay += "Ngươi có muốn tẩy SKH trang bị này không?\n";
                        if (player.inventory.gold >= 0) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Tẩy SKH");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Chuẩn bị đủ tiền rồi hãy gặp ta!!!");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Hủy Diệt và 150 viên Đá Ngũ Sắc", "Đóng");
                    }
                }
                break;
            case KHAC_CHI_SO_DA:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item  ngocboi1 = null, DaKhac = null; 
                    
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id == 1251) {
                                DaKhac = item;
                            } else if (item.template.id == 1248) {
                                ngocboi1 = item;                                
                            }  
                        }
                    }                    
                    int level1_1 = 0;
                    int level1_2 = 0;
                    int level1_3 = 0;
                    int level1_4 = 0;
                    int level1_72 = 0;
                    int level1_102 = 0;
                    int leve2_213 = 0;
                    int leve2_217 = 0;
                Item.ItemOption optionLevel = null;
                Item.ItemOption optionLevel_102 = null;
                Item.ItemOption optionLeve2_213 = null;
                Item.ItemOption optionLeve2_217 = null;
                
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 213 ) { // Đã Điêu Khắc Chỉ Số
                        leve2_213 = io.param;
                        optionLeve2_213 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 217 ) { // Đá Điêu Khắc Cấp
                        leve2_217 = io.param; // Số Cấp
                        optionLeve2_217 = io; // ID Option
                        break;
                    }
                }
                
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 72 ) {
                        level1_72 = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 102 ) {
                        level1_102 = io.param;
                        optionLevel_102 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 50 ) {
                        level1_1 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 77) {
                        level1_2 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==103) {
                        level1_3 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==5) {
                        level1_4 = io.param;
                        break;
                    }
                }
                
                    if ( ngocboi1 == null || DaKhac == null  ){
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Ngọc Bội và 1 Đá Chưa Khắc Chỉ Số", "Đóng");
                    }
                    if (player.inventory.ruby < 100000) {
                    Service.getInstance().sendThongBao(player, "Chuẩn bị đủ 100K Hồng Ngọc hãy đến tìm ta");
                        return;
                    }
                    else if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && optionLevel_102 == null) {
                        String npcSay = "|6|" + ngocboi1.template.name + "\n";
                        for (Item.ItemOption io : ngocboi1.itemOptions) {
                            npcSay += "|2|" + io.getOptionString() + "\n";
                        }
                        npcSay += "Ngươi có muốn Khắc Chỉ Số vào Đá không?\n";
                        if (player.inventory.ruby >= 100000) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Điêu Khắc");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Chuẩn bị đủ tiền rồi hãy gặp ta!!!");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Ngọc Bội Zin và 1 Đá Chưa Điêu Khắc Chỉ Số", "Đóng");
                    }
                }
                break;
            case KHAC_CHI_SO_NGOC_BOI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item  ngocboi1 = null, DaKhac = null; 
                    
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id == 1251) {
                                DaKhac = item;
                            } else if (item.template.id == 1248
                                    || item.template.id == 1253
                                    || item.template.id == 1254
                                    || item.template.id == 1255
                                    || item.template.id == 1256
                                    || item.template.id == 1257) {
                                ngocboi1 = item;                                
                            }  
                        }
                    }                    
                    int level1_1 = 0;
                    int level1_2 = 0;
                    int level1_3 = 0;
                    int level1_4 = 0;
                    int level2_1 = 0;
                    int level2_2 = 0;
                    int level2_3 = 0;
                    int level2_4 = 0;
                    int level3_1 = 0;
                    int level3_2 = 0;
                    int level3_3 = 0;
                    int level3_4 = 0;
                    int level1_72 = 0;
                    int leve2_213 = 0;
                    int leve2_217 = 0;
                Item.ItemOption optionLevel = null;
                Item.ItemOption optionLeve2_213 = null;
                Item.ItemOption optionLeve2_217 = null;
                
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 213 ) { // Đã Điêu Khắc Chỉ Số
                        leve2_213 = io.param;
                        optionLeve2_213 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 217 ) { // Đá Điêu Khắc Cấp
                        leve2_217 = io.param; // Số Cấp
                        optionLeve2_217 = io; // ID Option
                        break;
                    }
                }
                
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 72 ) {
                        level1_72 = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 50 ) {
                        level1_1 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 77) {
                        level1_2 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==103) {
                        level1_3 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==5) {
                        level1_4 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 50 ) {
                        level2_1 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 77) {
                        level2_2 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id ==103) {
                        level2_3 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id ==5) {
                        level2_4 = io.param;
                        break;
                    }
                }
                
                    if ( ngocboi1 == null || DaKhac == null  ){
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Ngọc Bội và 1 Đá Khắc Chỉ Số", "Đóng");
                    }
                    if (player.inventory.ruby < 100000) {
                    Service.getInstance().sendThongBao(player, "Chuẩn bị đủ 100K Hồng Ngọc hãy đến tìm ta");
                        return;
                    }
                    else if (ngocboi1 != null && DaKhac != null && optionLeve2_213 != null) {
                        String npcSay = "|6|" + DaKhac.template.name + "\n";
                        for (Item.ItemOption io : DaKhac.itemOptions) {
                            npcSay += "|2|" + io.getOptionString() + "\n";
                        }
                        npcSay += "Ngươi có muốn Khắc Chỉ Số vào Ngọc Bội không?\n";
                        if (player.inventory.gold >= 0) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Điêu Khắc\n Ngọc Bội");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Chuẩn bị đủ tiền rồi hãy gặp ta!!!");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Ngọc Bội và 1 Đá Điêu Khắc Chỉ Số", "Đóng");
                    }
                }
                break;
                
            case TIEN_HOA_NGOC_BOI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item  ngocboi1 = null, DaKhac = null; 
                    
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id == 1258) {
                                DaKhac = item;
                            } else if (item.template.id == 1248
                                    || item.template.id == 1253
                                    || item.template.id == 1254
                                    || item.template.id == 1255
                                    || item.template.id == 1256
                                    || item.template.id == 1257) {
                                ngocboi1 = item;                                
                            }  
                        }
                    }                    
                    int level1_1 = 0;
                    int level1_2 = 0;
                    int level1_3 = 0;
                    int level1_4 = 0;
                    int level1_72 = 0;
                Item.ItemOption optionLevel_72 = null;
                
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 72 ) {
                        level1_72 = io.param;
                        optionLevel_72 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 50 ) {
                        level1_1 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 77) {
                        level1_2 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==103) {
                        level1_3 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==5) {
                        level1_4 = io.param;
                        break;
                    }
                }

                
                    if ( ngocboi1 == null || DaKhac == null  ){
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Ngọc Bội và 1 Đá Tiến Hóa Ngọc Bội", "Đóng");
                    }
                    if (player.inventory.ruby < 500000 || player.inventory.gold < 50_000_000_000L ) {
                    Service.getInstance().sendThongBao(player, "Chuẩn bị đủ 500K Hồng Ngọc và 50 Tỷ Vàng hãy đến tìm ta");
                        return;
                    }
                    else if (ngocboi1 != null && DaKhac != null && level1_72 == 10) {
                        String npcSay = "|6|" + ngocboi1.template.name + "\n";
                        for (Item.ItemOption io : ngocboi1.itemOptions) {
                            npcSay += "|2|" + io.getOptionString() + "\n";
                        }
                        npcSay += "Ngươi có muốn Tiến Hóa Ngọc Bội không?\n";
                        if (player.inventory.gold >= 50_000_000_000L && player.inventory.ruby >= 500000 ) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Tiến Hóa\n Ngọc Bội");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Chuẩn bị đủ tiền rồi hãy gặp ta!!!");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Ngọc Bội + 10 và 1 Đá Tiến Hóa Ngọc Bội", "Đóng");
                    }
                }
                break;
            case THANG_CAP_TRANG_BI:
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item  Trangbi = null, DaBV = null, LuaThan = null; 
                    
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.type == 0 
                                    || item.template.type == 1
                                    || item.template.type == 2
                                    || item.template.type == 3
                                    || item.template.type == 4) {
                                Trangbi = item;
                            } else if (item.template.id == 987) {
                                DaBV = item;                                
                            } else if (item.template.id == 1250) {
                                LuaThan = item;                                
                            }
                        }
                    }                    
                    int level_102 = 0;
                    int level_216 = 0;
                    int level_107 = 0;
                    Item.ItemOption optionLevel_102 = null;
                    Item.ItemOption optionLevel_216 = null;
                    Item.ItemOption optionLevel_107 = null;
                    
                for (Item.ItemOption io : Trangbi.itemOptions) {
                    if (io.optionTemplate.id == 102 ) { // Số Sao Pha Lê
                        level_102 = io.param;
                        optionLevel_102 = io;
                        break;
                    }
                }
                
                for (Item.ItemOption io : Trangbi.itemOptions) {
                    if (io.optionTemplate.id == 216 ) { // Đã Thăng Cấp Mấy Lần
                        level_216 = io.param;
                        optionLevel_216 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : Trangbi.itemOptions) {
                    if (io.optionTemplate.id == 107 ) { 
                        level_107 = io.param;
                        optionLevel_107 = io;
                        break;
                    }
                }
                
                    if ( Trangbi == null || DaBV == null || LuaThan == null   ){
                        this.onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Thăng Cấp, Đá Bảo Vệ và Lửa Thần", "Đóng");
                    }
                    
                    if (player.inventory.ruby < 100000) {
                    Service.getInstance().sendThongBao(player, "Chuẩn bị đủ 100K Hồng Ngọc hãy đến tìm ta");
                        return;
                    }
                    else if (Trangbi != null 
                            && DaBV != null 
                            && LuaThan != null                              
                            && level_102 == 8) {
                        String npcSay = "|6|" + Trangbi.template.name + "\n";
                        for (Item.ItemOption io : Trangbi.itemOptions) {
                            npcSay += "|2|" + io.getOptionString() + "\n";
                        }
                        npcSay += "Ngươi có muốn Thăng Cấp Trang Bị không?";
                        if (player.inventory.ruby >= 100000) {
                            this.onggianoel.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Thăng Cấp\n Trang Bị");
                        } else {
                            this.onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Chuẩn bị đủ tiền rồi hãy gặp ta!!!");
                        }
                    } else {
                        this.onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần Trang Bị Pha Lê Hóa 8 Sao", "Đóng");
                    }
                }
                break;
            case EP_SAO_TRANG_BI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item trangBi = null;
                    Item daPhaLe = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (isTrangBiPhaLeHoa(item)) {
                            trangBi = item;
                        } else if (isDaPhaLe(item)) {
                            daPhaLe = item;
                        }
                    }
                    int star = 0; //sao pha lê đã ép
                    int starEmpty = 0; //lỗ sao pha lê
                    int level_216 = 0;
                    Item.ItemOption optionLevel_216 = null;
                        
  
                        for (Item.ItemOption io : trangBi.itemOptions) {
                            if (io.optionTemplate.id == 216) {
                                level_216 = io.param;
                                optionLevel_216 = io;
                                break;
                            }
                        }
                    
                    
                  
                    if (trangBi != null && daPhaLe != null) {
                        if (optionLevel_216 == null){
                        for (Item.ItemOption io : trangBi.itemOptions) {
                            if (io.optionTemplate.id == 102) {
                                star = io.param;
                            } else if (io.optionTemplate.id == 107) {
                                starEmpty = io.param;
                            }
                        }
                        if (star < starEmpty) {
                            player.combineNew.gemCombine = getGemEpSao(star);
                            String npcSay = trangBi.template.name + "\n|2|";
                            for (Item.ItemOption io : trangBi.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            if (daPhaLe.template.type == 30) {
                                for (Item.ItemOption io : daPhaLe.itemOptions) {
                                    npcSay += "|7|" + io.getOptionString() + "\n";
                                }
                            } else {
                                npcSay += "|7|" + ItemService.gI().getItemOptionTemplate(getOptionDaPhaLe(daPhaLe)).name.replaceAll("#", getParamDaPhaLe(daPhaLe) + "") + "\n";
                            }
                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.gemCombine) + " ngọc";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");

                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                        }
                      } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Trang Bị của bạn đã thăng cấp\n Gặp Ông Hạt Dẻ để Pha Lê Hóa", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                }
                break;
                
            case TAY_PS_HOA_TRANG_BI:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 2) {
                        Item daHacHoa = null;
                        Item itemHacHoa = null;
                        for (Item item_ : player.combineNew.itemsCombine) {
                            if (item_.template.id == 2047) {
                                daHacHoa = item_;
                            } else if (item_.isTrangBiHacHoa()) {
                                itemHacHoa = item_;
                            }
                        }
                        if (daHacHoa == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn bùa giải pháp sư", "Đóng");
                            return;
                        }
                        if (itemHacHoa == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu trang bị", "Đóng");
                            return;
                        }

                        String npcSay = "|2|Hiện tại " + itemHacHoa.template.name + "\n|0|";
                        for (Item.ItemOption io : itemHacHoa.itemOptions) {
                            if (io.optionTemplate.id != 72) {
                                npcSay += io.getOptionString() + "\n";
                            }
                        }
                        npcSay += "|2|Sau khi nâng cấp sẽ xoá hết các chỉ số pháp sư ngẫu nhiên \n|7|"
                                + "\n|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%\n"
                                + "Cần " + Util.numberToMoney(COST) + " vàng";

                        this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                npcSay, "Nâng cấp\n" + Util.numberToMoney(COST) + " vàng", "Từ chối");
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần có trang bị có thể pháp sư và bùa giải pháp sư", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }

                break;
            case PS_HOA_TRANG_BI:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 2) {
                        Item daHacHoa = null;
                        Item itemHacHoa = null;
                        for (Item item_ : player.combineNew.itemsCombine) {
                            if (item_.template.id == 2046) {
                                daHacHoa = item_;
                            } else if (item_.isTrangBiHacHoa()) {
                                itemHacHoa = item_;
                            }
                        }
                        if (daHacHoa == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu đá pháp sư", "Đóng");
                            return;
                        }
                        if (itemHacHoa == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu trang bị", "Đóng");
                            return;
                        }
                        if (itemHacHoa != null) {
                            for (ItemOption itopt : itemHacHoa.itemOptions) {
                                if (itopt.optionTemplate.id == 223) {
                                    if (itopt.param >= 8) {
                                        Service.getInstance().sendThongBao(player, "Trang bị đã đạt tới giới hạn pháp sư");
                                        return;
                                    }
                                }
                            }
                        }
                        String npcSay = "|2|Hiện tại " + itemHacHoa.template.name + "\n|0|";
                        for (Item.ItemOption io : itemHacHoa.itemOptions) {
                            if (io.optionTemplate.id != 72) {
                                npcSay += io.getOptionString() + "\n";
                            }
                        }
                        npcSay += "|2|Sau khi nâng cấp sẽ cộng 1 chỉ số pháp sư ngẫu nhiên \n|7|"
                                + "\n|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%\n"
                                + "Cần " + Util.numberToMoney(COST) + " vàng " + "\nCần" + Util.numberToMoney(RUBY) + "Hồng ngọc" ;

                        this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                npcSay, "Nâng cấp", "Từ chối");
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần có trang bị có thể pháp sư và đá pháp sư", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }

                break;
                
                case TINH_LUYEN_TRANG_BI:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 5) {
                        Item TBTinhAn = null;
                        Item ManhTinhAn = null;
                        Item LuaThan = null;
                        Item VinhDu = null;
                        Item DaTinhLuyen = null;
                        
                            for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id >= 1048 && item.template.id <= 1062 ) {
                                TBTinhAn = item;
                            } else if (item.template.id == 1232) {
                                ManhTinhAn = item;
                            } else if (item.template.id == 1250) {
                                LuaThan = item;
                            } else if (item.template.id == 1249) {
                                VinhDu = item;
                            } else if (item.template.id == 1265) {
                                DaTinhLuyen = item;
                            } 
                        }
                    } 
                        
                        
                    int level_34 = 0;
                    Item.ItemOption optionLevel_34 = null;
                    for (Item.ItemOption io : TBTinhAn.itemOptions) {
                    if (io.optionTemplate.id == 34 ) {
                        level_34 = io.param;
                        optionLevel_34 = io;
                        break;
                    }
                } 
        
                        if (TBTinhAn == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu Trang Bị Thiên Sứ Tinh Ấn", "Đóng");
                            return;
                        }
                        if (ManhTinhAn == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu Mảnh Tinh Ấn", "Đóng");
                            return;
                        }
                        if (LuaThan == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu Lửa Thần", "Đóng");
                            return;
                        }
                        if (VinhDu == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu Vinh Dự Samurai", "Đóng");
                            return;
                        }
                        if (DaTinhLuyen == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu Đá Tinh Luyện", "Đóng");
                            return;
                        }

                        if (TBTinhAn != null) {
                            for (ItemOption itopt : TBTinhAn.itemOptions) {
                                if (itopt.optionTemplate.id == 203) {
                                    if (itopt.param >= 10) {
                                        Service.getInstance().sendThongBao(player, "Trang Bị Đã Tinh Ấn tối đa");
                                        return;
                                    }
                                }
                            }
                        }
                        
                        String npcSay = "|2|Hiện tại " + TBTinhAn.template.name + "\n|0|";
                        for (Item.ItemOption io : TBTinhAn.itemOptions) {
                                npcSay += io.getOptionString() + "\n";
                         
                        }
                        npcSay += "|2|Sau khi nâng cấp sẽ tăng sức mạnh vượt bậc! Nhưng cũng hên xui :)) \n|7|"
                               
                                + "Cần " + Util.numberToMoney(COST_TINH_LUYEN) + " vàng " + "\nCần" + Util.numberToMoney(RUBY)  + "Hồng ngọc, Ngọc Xanh" ;

                        this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                npcSay, "Tinh Luyện", "Từ chối");
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn chưa đủ điều kiện để tinh luyện", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }

                break;
                
            case PHA_LE_HOA_TRANG_BI:
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (isTrangBiPhaLeHoa(item)) {
                        int star = 0;
                        int level_216 = 0;
                        Item.ItemOption optionLevel_216 = null;
                        
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 107) {
                                star = io.param;
                                break;
                            }
                        }
                        
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 216) {
                                level_216 = io.param;
                                optionLevel_216 = io;
                                break;
                            }
                        }
                        
                        if (star < MAX_STAR_ITEM) {
                            if (optionLevel_216 == null){
                            player.combineNew.goldCombine = getGoldPhaLeHoa(star);
                            player.combineNew.gemCombine = getGemPhaLeHoa(star);
                            player.combineNew.ratioCombine = getRatioPhaLeHoa(star);

                            String npcSay = item.template.name + "\n|2|";
                            for (Item.ItemOption io : item.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                            if (player.combineNew.goldCombine <= player.inventory.gold) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.gemCombine + " hồng ngọc","Nâng cấp\n10 lần","Nâng cấp\n100 lần");
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Trang Bị của bạn đã thăng cấp\n Gặp Ông Hạt Dẻ để Pha Lê Hóa", "Đóng");
                        }

                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm đã đạt tối đa sao pha lê", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm này không thể đục lỗ", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy hãy chọn 1 vật phẩm để pha lê hóa", "Đóng");
                }
                break;
            
            case PHA_LE_HOA_TRANG_BI_THANG_CAP:
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item  Trangbi = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.type == 0 
                                    || item.template.type == 1
                                    || item.template.type == 2
                                    || item.template.type == 3
                                    || item.template.type == 4) {
                                Trangbi = item;
                            } 
                        }
                    } 
                    
                    int level_216 = 0;
                    Item.ItemOption optionLevel_216 = null;
                    
                    for (Item.ItemOption io : Trangbi.itemOptions) {
                            if (io.optionTemplate.id == 216) {
                                level_216 = io.param;
                                optionLevel_216 = io;
                                break;
                            }
                        }
                    
                    if (Trangbi != null && optionLevel_216 != null ) {
                        int star = 0;
                        for (Item.ItemOption io : Trangbi.itemOptions) {
                            if (io.optionTemplate.id == 107) {
                                star = io.param;
                                break;
                            }
                        }                        
                        if (star < MAX_STAR_ITEM_TC1) {      
                            player.combineNew.goldCombine = getGoldPhaLeHoaTC1(star);
                            player.combineNew.gemCombine = getGemPhaLeHoaTC1(star);
                            player.combineNew.ratioCombine = getRatioPhaLeHoaTC1(star);

                            String npcSay = Trangbi.template.name + "\n|2|";
                            for (Item.ItemOption io : Trangbi.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                            if (player.combineNew.goldCombine <= player.inventory.gold) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                onggianoel.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.gemCombine + " hồng ngọc");
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        

                        } else {
                            this.onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm đã đạt tối đa sao pha lê", "Đóng");
                        }
                    } else {
                        this.onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Trang Bị của bạn chưa thăng cấp\n Gặp Bà Hạt Mít để Pha Lê Hóa", "Đóng");
                    }
                } else {
                    this.onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy hãy chọn 1 Trang Bị Thăng Cấp để pha lê hóa", "Đóng");
                }
                break;
                
            case EP_SAO_TRANG_BI_THANG_CAP:
                if (player.combineNew.itemsCombine.size() == 2) {
                    
                    Item daPhaLe = null;
                    Item  Trangbi = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.type == 0 
                                    || item.template.type == 1
                                    || item.template.type == 2
                                    || item.template.type == 3
                                    || item.template.type == 4) {
                                Trangbi = item;
                            } else if (isDaPhaLeTC1(item)) {
                                daPhaLe = item;
                        }   
                        }
                    } 
                    int star = 0; //sao pha lê đã ép
                    int starEmpty = 0; //lỗ sao pha lê
                    int level_216 = 0;
                    Item.ItemOption optionLevel_216 = null;
                        
  
                        for (Item.ItemOption io : Trangbi.itemOptions) {
                            if (io.optionTemplate.id == 216) {
                                level_216 = io.param;
                                optionLevel_216 = io;
                                break;
                            }
                        }
                    
                    
                  
                    if (Trangbi != null && daPhaLe != null && optionLevel_216 != null ) {
                        
                        for (Item.ItemOption io : Trangbi.itemOptions) {
                            if (io.optionTemplate.id == 102) {
                                star = io.param;
                            } else if (io.optionTemplate.id == 107) {
                                starEmpty = io.param;
                            }
                        }
                        if (star < starEmpty) {
                            player.combineNew.gemCombine = getGemEpSao(star);
                            String npcSay = Trangbi.template.name + "\n|2|";
                            for (Item.ItemOption io : Trangbi.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            if (daPhaLe.template.type == 30) {
                                for (Item.ItemOption io : daPhaLe.itemOptions) {
                                    npcSay += "|7|" + io.getOptionString() + "\n";
                                }
                            } else {
                                npcSay += "|7|" + ItemService.gI().getItemOptionTemplate(getOptionDaPhaLeTC1(daPhaLe)).name.replaceAll("#", getParamDaPhaLeTC1(daPhaLe) + "") + "\n";
                            }
                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.gemCombine) + " ngọc";
                            onggianoel.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");

                        } else {
                            this.onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                        }
                      
                    } else {
                        this.onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                    }
                } else {
                    this.onggianoel.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                }
                break;
                
            case NHAP_NGOC_RONG:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 1) {
                        Item item = player.combineNew.itemsCombine.get(0);
                        if (item != null && item.isNotNullItem() && (item.template.id >14 && item.template.id <= 20) && item.quantity >= 7) {
                            String npcSay = "|2|Con có muốn biến 10 " + item.template.name + " thành\n"
                                    + "1 viên " + ItemService.gI().getTemplate((short) (item.template.id - 1)).name + "\n"
                                    + "|7|Cần 7 " + item.template.name;
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Làm phép","Làm phép\n10 lần","Làm phép\n100 lần", "Từ chối");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 7 viên ngọc rồng cùng sao trở lên", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }
                break;
            case AN_TRANG_BI:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 2) {
                        Item item = player.combineNew.itemsCombine.get(0);
                        Item dangusac = player.combineNew.itemsCombine.get(1);
                            if (isTrangBiAn(item)) {
                            if (item != null && item.isNotNullItem() && dangusac != null && dangusac.isNotNullItem() && (dangusac.template.id == 1232 || dangusac.template.id == 1233 || dangusac.template.id == 1234) && dangusac.quantity >= 99) {
                                String npcSay = item.template.name + "\n|2|";
                            for (Item.ItemOption io : item.itemOptions) {
                                    npcSay += io.getOptionString() + "\n";
                            }
                                npcSay += "|1|Con có muốn biến trang bị " + item.template.name + " thành\n"
                                        + "trang bị Ấn không?\b|4|Tỉ lệ thành công: 50%\n"
                                        + "|7|Cần 99 " + dangusac.template.name + "\nCần " + Util.numberToMoney(COST) + " vàng" + "\nCần " + Util.numberToMoney(RUBY) + "hồng ngọc" ;
                                this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Làm phép", "Từ chối");
                            } else {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn chưa bỏ đủ vật phẩm !!!", "Đóng");
                            }
                            } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm này không thể hóa ấn", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần bỏ đủ vật phẩm yêu cầu", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }
                break;
            case NANG_CAP_VAT_PHAM:
                if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đá nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    Item itemDo = null;
                    Item itemDNC = null;
                    Item itemDBV = null;
                    for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                        if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                                itemDBV = player.combineNew.itemsCombine.get(j);
                                continue;
                            }
                            if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                                itemDo = player.combineNew.itemsCombine.get(j);
                            } else {
                                itemDNC = player.combineNew.itemsCombine.get(j);
                            }
                        }
                    }
                    if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                        int level = 0;
                        for (Item.ItemOption io : itemDo.itemOptions) {
                            if (io.optionTemplate.id == 72) {
                                level = io.param;
                                break;
                            }
                        }
                        if (level < MAX_LEVEL_ITEM) {
                            player.combineNew.goldCombine = getGoldNangCapDo(level);
                            player.combineNew.ratioCombine = (float) getTileNangCapDo(level);
                            player.combineNew.countDaNangCap = getCountDaNangCapDo(level);
                            player.combineNew.countDaBaoVe = (short) getCountDaBaoVe(level);
                            String npcSay = "|2|Hiện tại " + itemDo.template.name + " (+" + level + ")\n|0|";
                            for (Item.ItemOption io : itemDo.itemOptions) {
                                if (io.optionTemplate.id != 72) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            String option = null;
                            int param = 0;
                            for (Item.ItemOption io : itemDo.itemOptions) {
                                if (io.optionTemplate.id == 47
                                        || io.optionTemplate.id == 6
                                        || io.optionTemplate.id == 0
                                        || io.optionTemplate.id == 7
                                        || io.optionTemplate.id == 14
                                        || io.optionTemplate.id == 22
                                        || io.optionTemplate.id == 23
                                        || io.optionTemplate.id == 235
                                        || io.optionTemplate.id == 236) {
                                    option = io.optionTemplate.name;
                                    param = io.param + (io.param * 10 / 100);
                                    break;
                                }
                            }
                            npcSay += "|2|Sau khi nâng cấp (+" + (level + 1) + ")\n|7|"
                                    + option.replaceAll("#", String.valueOf(param))
                                    + "\n|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%\n"
                                    + (player.combineNew.countDaNangCap > itemDNC.quantity ? "|7|" : "|1|")
                                    + "Cần " + player.combineNew.countDaNangCap + " " + itemDNC.template.name
                                    + "\n" + (player.combineNew.goldCombine > player.inventory.gold ? "|7|" : "|1|")
                                    + "Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";

                            String daNPC = player.combineNew.itemsCombine.size() == 3 && itemDBV != null ? String.format("\nCần tốn %s đá bảo vệ", player.combineNew.countDaBaoVe) : "";
                            if ((level == 2 || level == 4 || level == 6) && !(player.combineNew.itemsCombine.size() == 3 && itemDBV != null)) {
                                npcSay += "\nNếu thất bại sẽ rớt xuống (+" + (level - 1) + ")";
                            }
                            if (player.combineNew.countDaNangCap > itemDNC.quantity) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaNangCap - itemDNC.quantity) + " " + itemDNC.template.name);
                            } else if (player.combineNew.goldCombine > player.inventory.gold) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + Util.numberToMoney((player.combineNew.goldCombine - player.inventory.gold)) + " vàng");
                            } else if (player.combineNew.itemsCombine.size() == 3 && Objects.nonNull(itemDBV) && itemDBV.quantity < player.combineNew.countDaBaoVe) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaBaoVe - itemDBV.quantity) + " đá bảo vệ");
                            } else {
                                this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                        npcSay, "Nâng cấp\n" + Util.numberToMoney(player.combineNew.goldCombine) + " vàng" + daNPC, "Nâng cấp\n10 lần", "Nâng cấp\n100 lần", "Từ chối");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Trang bị của ngươi đã đạt cấp tối đa", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                    }
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cất đi con ta không thèm", "Đóng");
                        break;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                }
                break;
            case PHAN_RA_DO_THAN_LINH:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con hãy đưa ta đồ thần linh để phân rã", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 1) {
                    List<Integer> itemdov2 = new ArrayList<>(Arrays.asList(562, 564, 566));
                    int couponAdd = 0;
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (item.isNotNullItem()) {
                        if (item.template.id >= 555 && item.template.id <= 567) {
                            couponAdd = itemdov2.stream().anyMatch(t -> t == item.template.id) ? 2 : item.template.id == 561 ? 3 : 1;
                        }
                    }
                    if (couponAdd == 0) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta chỉ có thể phân rã đồ thần linh thôi", "Đóng");
                        return;
                    }
                    String npcSay = "|2|Sau khi phân rã vật phẩm\n|7|"
                            + "Bạn sẽ nhận được : " + couponAdd + " Đá Ngũ Sắc\n"
                            + (500000000 > player.inventory.gold ? "|7|" : "|1|")
                            + "Cần " + Util.numberToMoney(50000000) + " vàng";

                    if (player.inventory.gold < 50000000) {
                        this.baHatMit.npcChat(player, "Con không đủ 50TR vàng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_PHAN_RA_DO_THAN_LINH,
                            npcSay, "Phân Rã\n" + Util.numberToMoney(50000000) + " vàng", "Từ chối");
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta chỉ có thể phân rã 1 lần 1 món đồ thần linh", "Đóng");
                }
                break;
            case NANG_CAP_DO_TS:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy đưa ta 2 món Hủy Diệt bất kì và 1 món Thần Linh cùng loại", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 4) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTL()).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ thần linh", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDHD()).count() < 2) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ hủy diệt", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu mảnh thiên sứ", "Đóng");
                        return;
                    }

                    String npcSay = "|2|Con có muốn đổi các món nguyên liệu ?\n|7|"
                            + "Và nhận được " + player.combineNew.itemsCombine.stream().filter(Item::isManhTS).findFirst().get().typeNameManh() + " thiên sứ tương ứng\n"
                            + "|1|Cần " + Util.numberToMoney(COST) + " vàng";

                    if (player.inventory.gold < COST) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con không đủ vàng", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_NANG_CAP_DO_TS,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(COST) + " vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;
            case NANG_CAP_SKH_VIP:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy đưa ta 1 món thiên sứ và 2 món SKH ngẫu nhiên", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTS()).count() < 1) {
                        this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ thiên sứ", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).count() < 2) {
                        this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ kích hoạt ", "Đóng");
                        return;
                    }

                    String npcSay = "|2|Con có muốn đổi các món nguyên liệu ?\n|7|"
                            + "Và nhận được " + player.combineNew.itemsCombine.stream().filter(Item::isDTS).findFirst().get().typeName() + " kích hoạt VIP tương ứng\n"
                            + "|1|Cần " + Util.numberToMoney(COST) + " vàng";

                    if (player.inventory.gold < COST) {
                        this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                        return;
                    }
                    this.npsthiensu64.createOtherMenu(player, ConstNpc.MENU_NANG_DOI_SKH_VIP,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(COST) + " vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Nguyên liệu không phù hợp", "Đóng");
                        return;
                    }
                    this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;
            case NANG_CAP_BONG_TAI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item bongTai = null;
                    Item manhVo = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 454) {
                            bongTai = item;
                        } else if (item.template.id == 933) {
                            manhVo = item;
                        }
                    }
                    if (bongTai != null && manhVo != null && manhVo.quantity >= 99) {

                        player.combineNew.goldCombine = GOLD_BONG_TAI;
                        player.combineNew.gemCombine = GEM_BONG_TAI;
                        player.combineNew.ratioCombine = RATIO_BONG_TAI;

                        String npcSay = "Bông tai Porata cấp 2" + "\n|2|";
                        for (Item.ItemOption io : bongTai.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (player.combineNew.goldCombine <= player.inventory.gold) {
                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                        } else {
                            npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Bông tai Porata cấp 1 và X99 Mảnh vỡ bông tai", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Bông tai Porata cấp 1 và X99 Mảnh vỡ bông tai", "Đóng");
                }
                break;
            case MO_CHI_SO_BONG_TAI:
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item bongTai = null;
                    Item manhHon = null;
                    Item daXanhLam = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 921) {
                            bongTai = item;
                        } else if (item.template.id == 934) {
                            manhHon = item;
                        } else if (item.template.id == 935) {
                            daXanhLam = item;
                        }
                    }
                    if (bongTai != null && manhHon != null && daXanhLam != null && manhHon.quantity >= 99) {

                        player.combineNew.goldCombine = GOLD_BONG_TAI;
                        player.combineNew.gemCombine = GEM_BONG_TAI;
                        player.combineNew.ratioCombine = RATIO_NANG_CAP;

                        String npcSay = "Bông tai Porata cấp 2" + "\n|2|";
                        for (Item.ItemOption io : bongTai.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (player.combineNew.goldCombine <= player.inventory.gold) {
                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                        } else {
                            npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Bông tai Porata cấp 2, X99 Mảnh hồn bông tai và 1 Đá xanh lam", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Bông tai Porata cấp 2, X99 Mảnh hồn bông tai và 1 Đá xanh lam", "Đóng");
                }
                break;
            case CHE_TAO_TRANG_BI_TS:
                 if (player.combineNew.itemsCombine.size() == 0) {
                    this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "ọc ọc", "Yes");
                    return;
                }
                  if (player.combineNew.itemsCombine.size() >= 2 &&  player.combineNew.itemsCombine.size() < 5) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() &&  item.isCongThucVip()).count() < 1) {
                        this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Công thức Vip", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).count() < 1) {
                        this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Mảnh đồ thiên sứ", "Đóng");
                        return;
                    }
                    Item mTS = null, daNC = null, daMM = null;
                        for (Item item : player.combineNew.itemsCombine) {
                            if (item.isNotNullItem()) {
                                if (item.isManhTS()) {
                                mTS = item;
                            } else if (item.isDaNangCap()) {
                                daNC = item;
                            } else if (item.isDaMayMan()) {
                                daMM = item;
                            }
                        }
                    }
                    int tiLeMacDinh = 35;    
                    int tilenew = tiLeMacDinh;
                    if (daNC != null) {
                        tilenew += (daNC.template.id - 1073) * 10;                     
                    }

                    String npcSay = "|2|Chế tạo " + player.combineNew.itemsCombine.stream().filter(Item::isManhTS).findFirst().get().typeNameManh() + " Thiên sứ " 
                            + player.combineNew.itemsCombine.stream().filter(Item::isCongThucVip).findFirst().get().typeHanhTinh() + "\n"
                            + "|7|Mảnh ghép " +  mTS.quantity + "/999\n";
//                            + "|2|Đá nâng cấp " + player.combineNew.itemsCombine.stream().filter(Item::isDaNangCap).findFirst().get().typeDanangcap()
//                            + " (+" + (daNC.template.id - 1073) + "0% tỉ lệ thành công)\n"
//                            + "|2|Đá may mắn " + player.combineNew.itemsCombine.stream().filter(Item::isDaMayMan).findFirst().get().typeDaMayman()
//                            + " (+" + (daMM.template.id - 1078) + "0% tỉ lệ tối đa các chỉ số)\n"
//                            + "|2|Tỉ lệ thành công: " + tilenew + "%\n"
//                            + "|7|Phí nâng cấp: 500 triệu vàng";
                    
                    if (daNC != null) {
                        
                        npcSay += "|2|Đá nâng cấp " + player.combineNew.itemsCombine.stream().filter(Item::isDaNangCap).findFirst().get().typeDanangcap() 
                                  + " (+" + (daNC.template.id - 1073) + "0% tỉ lệ thành công)\n";
                    }
                    if (daMM != null) {
                        npcSay += "|2|Đá may mắn " + player.combineNew.itemsCombine.stream().filter(Item::isDaMayMan).findFirst().get().typeDaMayman()
                                  + " (+" + (daMM.template.id - 1078) + "0% tỉ lệ tối đa các chỉ số)\n";
                    }
                    if (daNC != null) {
                        npcSay += "|2|Tỉ lệ thành công: " + tilenew + "%\n";
                    } else {
                        npcSay += "|2|Tỉ lệ thành công: " + tiLeMacDinh + "%\n";
                    }
                    npcSay += "|7|Phí nâng cấp: 1 tỷ vàng và 20K Hồng Ngọc";
                    if (player.inventory.gold < 1000000000 || player.inventory.ruby < 20000 ) {
                        this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn không đủ vàng hoặc hồng ngọc", "Đóng");
                        return;
                    }
                    this.npsthiensu64.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                            npcSay, "Nâng cấp\n1 Tỷ vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 4) {
                        this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Nguyên liệu không phù hợp", "Đóng");
                        return;
                    }
                    this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Không đủ nguyên liệu, mời quay lại sau", "Đóng");
                }
                break;
                
            case DOI_SKH_THANH_DNS:
                if (player.combineNew.itemsCombine.size() == 2) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).count() < 2) {
                        this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ kích hoạt ", "Đóng");
                        return;
                    }
                        String npcSay = "Ngươi có muốn đổi hai món đồ kích hoạt thành Mây Ngũ Sắc không ?\n";
                        if (player.inventory.gold >= COST_DOI_KICH_HOAT ) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Cần " + Util.numberToMoney(COST_DOI_KICH_HOAT ) + " vàng");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Còn thiếu\n"
                                    + Util.numberToMoney(player.inventory.gold - COST_DOI_KICH_HOAT ) + " vàng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần hai món đồ kích hoạt của ngươi", "Đóng");
                    }
                
                break;
            case NANG_CAP_CAI_TRANG_SSJ:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item caitrang = null;
                    Item dangusac = null;

                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 1192
                          ||item.template.id == 1216
                          ||item.template.id == 1280
                          ||item.template.id == 1208
                          ||item.template.id == 2008) {
                            caitrang = item;
                        } else if (item.template.id == 674) {
                            dangusac = item;

                        }
                    }
                    int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                    if (caitrang != null && dangusac != null && level < 10) {

                        player.combineNew.goldCombine = getGoldNCCaiTrang(level);
                        player.combineNew.gemCombine = getGemNangCaiTrang(level);;
                        player.combineNew.ratioCombine = getRatioNCCaiTrang(level);
                        player.combineNew.ratioCombine2 = getRatioNCCaiTrang2(level);
                        player.combineNew.ratioCombine3 = getRatioNCCaiTrang3(level);
                        player.combineNew.ratioCombine4 = getRatioNCCaiTrang4(level);
                        player.combineNew.ratioCombine5 = getRatioNCCaiTrang5(level);
                        

                        String npcSay = "Nâng Cấp Cải Trang" + "\n|2|";
                        for (Item.ItemOption io : caitrang.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (player.combineNew.goldCombine <= player.inventory.gold) {
                            npcSay += "|1|Cần " + getDaNangCap(level) + " Đá ngũ sắc";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                        } else {
                            npcSay += "Cần " + getDaNangCap(level) + " Đá ngũ sắc ";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else if ( level >= 10) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Đã Nâng Cấp Tối Đa Cải Trang", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                             "Cần 1 Cải Trang, Đá Ngũ Sắc", "Đóng");
                }
                break;
                
                
            case TIEN_HOA_CAI_TRANG_BABY_VEGETA:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item caitrang = null;
                    Item dangusac = null;

                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 1192) {
                            caitrang = item;
                        } else if (item.template.id == 674) {
                            dangusac = item;

                        }
                    }
                    int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                    if (caitrang != null && dangusac != null && level == 10) {

                        player.combineNew.goldCombine = 2000000000;
                        player.combineNew.gemCombine = 100000;                       

                        String npcSay = "Tiến Hóa Cải Trang GoHan SSJ +10 thành Cải Trang Baby Vegeta";                                                
                        if (player.combineNew.goldCombine <= player.inventory.gold && player.combineNew.gemCombine <= player.inventory.ruby ) {
                            npcSay += "|1|Cần " + 500 + " Đá ngũ sắc";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Tiến Hóa");
                        } else {
                            npcSay += "Cần " + 500 + " Đá ngũ sắc ";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else if ( level < 10) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cải Trang GoHan SSJ +10 mới có thể nâng cấp", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                             "Cần 1 Cải Trang GoHan SSJ5, Đá Ngũ Sắc", "Đóng");
                }
                break;
                
                case TIEN_HOA_CAI_TRANG_BLACK_GOKU:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item caitrang = null;
                    Item dangusac = null;

                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 1216) {
                            caitrang = item;
                        } else if (item.template.id == 674) {
                            dangusac = item;

                        }
                    }
                    int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                    if (caitrang != null && dangusac != null && level == 10) {

                        player.combineNew.goldCombine = 2000000000;
                        player.combineNew.gemCombine = 200000;                       

                        String npcSay = "Tiến Hóa Cải Trang Baby Vegeta +10 thành Cải Trang Black Goku";                                                
                        if (player.combineNew.goldCombine <= player.inventory.gold && player.combineNew.gemCombine <= player.inventory.ruby ) {
                            npcSay += "|1|Cần " + 1000 + " Đá ngũ sắc";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Tiến Hóa");
                        } else {
                            npcSay += "Cần " + 1000 + " Đá ngũ sắc ";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else if ( level < 10) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cải Trang Baby Vegeta +10 mới có thể nâng cấp", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                             "Cần 1 Cải Trang Baby Vegeta, Đá Ngũ Sắc", "Đóng");
                }
                break;
                
                case TIEN_HOA_CAI_TRANG_BILL:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item caitrang = null;
                    Item dangusac = null;

                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 1280) {
                            caitrang = item;
                        } else if (item.template.id == 674) {
                            dangusac = item;

                        }
                    }
                    int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                    if (caitrang != null && dangusac != null && level == 10) {

                        player.combineNew.goldCombine = 2000000000;
                        player.combineNew.gemCombine = 300000;                       

                        String npcSay = "Tiến Hóa Cải Trang Goku Black +10 thành Cải Trang Bill";                                                
                        if (player.combineNew.goldCombine <= player.inventory.gold && player.combineNew.gemCombine <= player.inventory.ruby ) {
                            npcSay += "|1|Cần " + 1500 + " Đá ngũ sắc";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Tiến Hóa");
                        } else {
                            npcSay += "Cần " + 1500 + " Đá ngũ sắc ";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else if ( level < 10) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cải Trang Goku Black +10 mới có thể nâng cấp", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                             "Cần 1 Cải Trang Goku Black, Đá Ngũ Sắc", "Đóng");
                }
                break;
                
                case TIEN_HOA_CAI_TRANG_HEARTS_GOLD:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item caitrang = null;
                    Item dangusac = null;

                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 2008) {
                            caitrang = item;
                        } else if (item.template.id == 674) {
                            dangusac = item;

                        }
                    }
                    int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                    if (caitrang != null && dangusac != null && level == 10) {

                        player.combineNew.goldCombine = 2000000000;
                        player.combineNew.gemCombine = 400000;                       

                        String npcSay = "Tiến Hóa Cải Trang Bill +10 thành Cải Trang Hearts Gold";                                                
                        if (player.combineNew.goldCombine <= player.inventory.gold && player.combineNew.gemCombine <= player.inventory.ruby ) {
                            npcSay += "|1|Cần " + 2000 + " Đá ngũ sắc";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Tiến Hóa");
                        } else {
                            npcSay += "Cần " + 2000 + " Đá ngũ sắc ";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else if ( level < 10) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cải Trang Bill +10 mới có thể nâng cấp", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                             "Cần 1 Cải Trang Bill, Đá Ngũ Sắc", "Đóng");
                }
                break;

        }
    }
    
    

    /**
     * Bắt đầu đập đồ - điều hướng từng loại đập đồ
     *
     * @param player
     */
    public void startCombine(Player player) {
        switch (player.combineNew.typeCombine) {
            case DAP_SET_KICH_HOAT:
                dapDoKichHoat(player);
                break;
            case DAP_SET_KICH_HOAT_HD:
                dapDoKichHoatHD(player);
                break;
            case DAP_SET_KICH_HOAT_TS:
                dapDoKichHoatTS(player);
                break;
            case TAY_SKH_TRANG_BI:
                TaySKH(player);
                break;
            case KHAC_CHI_SO_DA:
                KhacChiSoDa(player);
                break;
            case KHAC_CHI_SO_NGOC_BOI:
                KhacChiSoTrangBi(player);
                break;
            case TIEN_HOA_NGOC_BOI:
                TienHoaNgocBoi(player);
                break;
            case THANG_CAP_TRANG_BI:
                ThangCapTrangBi(player);
                break;
            case EP_SAO_TRANG_BI:
                epSaoTrangBi(player);
                break;
            case EP_SAO_TRANG_BI_THANG_CAP:
                epSaoTrangBiTC(player);
                break;
            case PHA_LE_HOA_TRANG_BI:
                phaLeHoaTrangBi(player);
                break;
            case PHA_LE_HOA_TRANG_BI_THANG_CAP:
                phaLeHoaTrangBiThangCap(player);
                break;
            
            case AN_TRANG_BI:
                antrangbi(player);
                break;
            case CHUYEN_HOA_TRANG_BI:

                break;
            case NHAP_NGOC_RONG:
                nhapNgocRong(player);
                break;

            case PHAN_RA_DO_THAN_LINH:
                phanradothanlinh(player);
                break;
            case NANG_CAP_DO_TS:
                openDTS(player);
                break;
            case NANG_CAP_SKH_VIP:
                openSKHVIP(player);
                break;
            case NANG_CAP_CAI_TRANG_SSJ:
                NangCapCaiTrangSSJ(player);
                break;
            case TIEN_HOA_CAI_TRANG_BABY_VEGETA:
                Tienhoacaitrangbaby(player);
                break;
            case TIEN_HOA_CAI_TRANG_BLACK_GOKU:
                Tienhoacaitrangblackgoku(player);
                break;
            case TIEN_HOA_CAI_TRANG_BILL:
                Tienhoacaitrangbill(player);
                break;
            case TIEN_HOA_CAI_TRANG_HEARTS_GOLD:
                Tienhoacaitranggold(player);
                break;
            case NANG_CAP_VAT_PHAM:
                nangCapVatPham(player);
                break;
            case NANG_CAP_BONG_TAI:
                nangCapBongTai(player);
                break;
            case MO_CHI_SO_BONG_TAI:
                moChiSoBongTai(player);
            case PS_HOA_TRANG_BI:
                psHoaTrangBi(player);
                break;
            case TINH_LUYEN_TRANG_BI:
                TinhLuyenTrangBi(player);
                break;
            case TAY_PS_HOA_TRANG_BI:
                tayHacHoaTrangBi(player);
                break;
            case CHE_TAO_TRANG_BI_TS:
                cheTaoDoTS(player);
                break;
            case DOI_SKH_THANH_DNS :
                doiskhthanhdns(player);
                break;
//            case OPTION_PORATA:
//                nangCapVatPham(player);
//                break;    
        }

        player.iDMark.setIndexMenu(ConstNpc.IGNORE_MENU);
        player.combineNew.clearParamCombine();
        player.combineNew.lastTimeCombine = System.currentTimeMillis();

    }
    public void startCombine1(Player player) {
        switch (player.combineNew.typeCombine) {

            case PHA_LE_HOA_TRANG_BI:
                phaLeHoaTrangBi10Lan(player);
                break;
            case NHAP_NGOC_RONG:
                nhapNgocRong10lan(player);
                break;
            case NANG_CAP_VAT_PHAM:
                nangCapVatPham10(player);
                break;

 
        }

        player.iDMark.setIndexMenu(ConstNpc.IGNORE_MENU);
        player.combineNew.clearParamCombine();
        player.combineNew.lastTimeCombine = System.currentTimeMillis();

    }
    public void startCombine2(Player player) {
        switch (player.combineNew.typeCombine) {

            case PHA_LE_HOA_TRANG_BI:
                phaLeHoaTrangBi100Lan(player);
                break;
            case NHAP_NGOC_RONG:
                nhapNgocRong100lan(player);
                break;
            case NANG_CAP_VAT_PHAM:
                nangCapVatPham100(player);
                break;
        }

        player.iDMark.setIndexMenu(ConstNpc.IGNORE_MENU);
        player.combineNew.clearParamCombine();
        player.combineNew.lastTimeCombine = System.currentTimeMillis();

    }

    private void dapDoKichHoat(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            Item dhd = null, dtl = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id ==674) {
                        dtl = item;
                    } else if (item.template.id >= 555 && item.template.id <= 567) {
                        dhd = item;
                    }
                }
            }
            if (dhd != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0 && player.inventory.gold >= COST_DAP_DO_KICH_HOAT && player.inventory.ruby >= RUBY_DAP_DO_KICH_HOAT && player.inventory.gem >= GEM_DAP_DO_KICH_HOAT) {
                    player.inventory.gold -= COST_DAP_DO_KICH_HOAT;
                    player.inventory.ruby -= RUBY_DAP_DO_KICH_HOAT;
                    player.inventory.gem -= GEM_DAP_DO_KICH_HOAT;
                    int tiLe = dtl != null ? 100 : 50;
                    if (Util.isTrue(tiLe, 100)) {
                        sendEffectSuccessCombine(player);
                        Item item = dhd;
                        RewardService.gI().initActivationOption(item.template.gender < 3 ? item.template.gender : player.gender, item.template.type, item.itemOptions);
                        
                    InventoryServiceNew.gI().addItemBag(player, item);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    
                    InventoryServiceNew.gI().subQuantityItemsBag(player, dhd, 1);
                    if (dtl != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, dtl, 100);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }
    private void dapDoKichHoatHD(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            Item dhd = null, dtl = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id ==674) {
                        dtl = item;
                    } else if (item.template.id >= 650 && item.template.id <= 662) {
                        dhd = item;
                    }
                }
            }
            if (dhd != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0 && player.inventory.gold >= COST_DAP_DO_KICH_HOAT && player.inventory.ruby >= RUBY_DAP_DO_KICH_HOAT && player.inventory.gem >= GEM_DAP_DO_KICH_HOAT) {
                    player.inventory.gold -= COST_DAP_DO_KICH_HOAT;
                    player.inventory.ruby -= RUBY_DAP_DO_KICH_HOAT;
                    player.inventory.gem -= GEM_DAP_DO_KICH_HOAT;
                    int tiLe = dtl != null ? 100 : 50;
                    if (Util.isTrue(tiLe, 100)) {
                        sendEffectSuccessCombine(player);
                        Item item = dhd;
                        RewardService.gI().initActivationOption(item.template.gender < 3 ? item.template.gender : player.gender, item.template.type, item.itemOptions);
                        
                    InventoryServiceNew.gI().addItemBag(player, item);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, dhd, 1);
                    if (dtl != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, dtl, 150);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }
    private void dapDoKichHoatTS(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            Item dhd = null, dtl = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id ==674) {
                        dtl = item;
                    } else if (item.template.id >= 1048 && item.template.id <= 1062) {
                        dhd = item;
                    }
                }
            }
            if (dhd != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0 && player.inventory.gold >= COST_DAP_DO_KICH_HOAT && player.inventory.ruby >= RUBY_DAP_DO_KICH_HOAT && player.inventory.gem >= GEM_DAP_DO_KICH_HOAT) {
                    player.inventory.gold -= COST_DAP_DO_KICH_HOAT;
                    player.inventory.ruby -= RUBY_DAP_DO_KICH_HOAT;
                    player.inventory.gem -= GEM_DAP_DO_KICH_HOAT;
                    int tiLe = dtl != null ? 100 : 50;
                    if (Util.isTrue(tiLe, 100)) {
                        sendEffectSuccessCombine(player);
                        Item item = dhd;
                        RewardService.gI().initActivationOption(item.template.gender < 3 ? item.template.gender : player.gender, item.template.type, item.itemOptions);
                        
                    InventoryServiceNew.gI().addItemBag(player, item);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, dhd, 1);
                    if (dtl != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, dtl, 200);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }
    
    private void TaySKH(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            Item dhd = null, buatay = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id ==2047) {
                        buatay = item;
                    } else if (item.template.id >= 650 && item.template.id <= 662 
                                || item.template.id >= 1048 && item.template.id <= 1062
                                ||item.template.id >= 555 && item.template.id <= 567) {
                        dhd = item;
                    }
                }
            }
            int level = 0;
            int level2 = 0;
            int level3 = 0;
            Item.ItemOption optionLevel = null;
            Item.ItemOption optionLevel2 = null;
            Item.ItemOption optionLevel3 = null;
            for (Item.ItemOption io : dhd.itemOptions) {
                if (io.optionTemplate.id >= 127 && io.optionTemplate.id <= 135 ) {
                    level = io.param;
                    optionLevel = io;
                    break;
                }
            }
            for (Item.ItemOption io : dhd.itemOptions) {
                if (io.optionTemplate.id >= 136 && io.optionTemplate.id <= 144) {
                    level2 = io.param;
                    optionLevel2 = io;
                    break;
                }
            }
                for (Item.ItemOption io : dhd.itemOptions) {
                    if (io.optionTemplate.id ==30) {
                        level3 = io.param;
                        optionLevel3 = io;
                        break;
                    }
                }
                if (Util.isTrue(100, 100)) {
                    
                    if (dhd.isSKH() && optionLevel != null && optionLevel2 != null) {
                    dhd.itemOptions.remove(optionLevel);
                    dhd.itemOptions.remove(optionLevel2);
                    dhd.itemOptions.remove(optionLevel3);
                    
                    }
                    sendEffectSuccessCombine(player);
                    Service.getInstance().sendThongBao(player, "Bạn đã tẩy thành công");
                    InventoryServiceNew.gI().sendItemBags(player);    
                }
                else {
                    sendEffectFailCombine(player);
                }
            InventoryServiceNew.gI().subQuantityItemsBag(player, buatay, 1);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendMoney(player);
        //    player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        }
    }
    
    private void KhacChiSoDa(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            Item ngocboi1 = null, DaKhac = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.isNotNullItem()) {
                            if (item.template.id == 1251) {
                                DaKhac = item;
                            } else if (item.template.id == 1248) {
                                ngocboi1 = item;
                            }
                        }
                }
            }
                     int level1_1 = 0;
                    int level1_2 = 0;
                    int level1_3 = 0;
                    int level1_4 = 0;
                    int level1_72 = 0;
                    int level1_102 = 0;
                    int leve2_213 = 0;
                    int leve2_217 = 0;
                Item.ItemOption optionLevel = null;
                Item.ItemOption optionLevel_102 = null;
                Item.ItemOption optionLeve2_213 = null;
                Item.ItemOption optionLeve2_217 = null;
                
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 213 ) { // Đã Điêu Khắc Chỉ Số
                        leve2_213 = io.param;
                        optionLeve2_213 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 217 ) { // Đá Điêu Khắc Cấp
                        leve2_217 = io.param; // Số Cấp
                        optionLeve2_217 = io; // ID Option
                        break;
                    }
                }
                
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 72 ) {
                        level1_72 = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 102 ) {
                        level1_102 = io.param;
                        optionLevel_102 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 50 ) {
                        level1_1 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 77) {
                        level1_2 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==103) {
                        level1_3 = io.param;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==5) {
                        level1_4 = io.param;
                        break;
                    }
                }
                if (Util.isTrue(100, 100)) {
                    if (player.inventory.ruby < 100000){
                        Service.getInstance().sendThongBao(player, "Điêu Khắc Chỉ Số vào Đá tốn 100K Hồng Ngọc");
                        return;
                        }
                    else{
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 1 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*10/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*10/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*10/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*10/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }
                    
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 2 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*15/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*15/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*15/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*15/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }
                    
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 3 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*20/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*20/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*20/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*20/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }
                    
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 4 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*25/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*25/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*25/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*25/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 5 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*30/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*30/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*30/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*30/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 6 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*35/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*35/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*35/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*35/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }
                    
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 7 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*40/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*40/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*40/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*40/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }
                    
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 8 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*45/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*45/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*45/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*45/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }
                    
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 9 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*50/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*50/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*50/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*50/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }
                    if (level1_72 == 0 && ngocboi1 != null && DaKhac != null && optionLeve2_213 == null && leve2_217 == 10 && optionLevel_102 == null) {                           
                        DaKhac.itemOptions.add(new ItemOption(50, level1_1*55/100));
                        DaKhac.itemOptions.add(new ItemOption(77, level1_2*55/100));
                        DaKhac.itemOptions.add(new ItemOption(103, level1_3*55/100));
                        DaKhac.itemOptions.add(new ItemOption(5, level1_4*55/100));
                        DaKhac.itemOptions.add(new ItemOption(213, 1));
                    }     
                    sendEffectSuccessCombine(player);
                    player.inventory.ruby -= 100000;
                    Service.getInstance().sendThongBao(player, "Bạn Điêu Khắc Chỉ Số vào Đá thành công");
                    InventoryServiceNew.gI().sendItemBags(player);    
            }}
                else {
                sendEffectFailCombine(player);
            }
            InventoryServiceNew.gI().subQuantityItemsBag(player, ngocboi1, 1);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendMoney(player);
            player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        }
    }
        
    private void KhacChiSoTrangBi(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            Item ngocboi1 = null; 
            Item DaKhac = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.isNotNullItem()) {
                            if (item.template.id == 1251) {
                                DaKhac = item;
                            } else if (item.template.id == 1248
                                    || item.template.id == 1253
                                    || item.template.id == 1254
                                    || item.template.id == 1255
                                    || item.template.id == 1256
                                    || item.template.id == 1257) {
                                ngocboi1 = item;
                            }
                        }
                    }
                }

                    int level1_1 = 0;
                    int level1_2 = 0;
                    int level1_3 = 0;
                    int level1_4 = 0;
                    int level2_1 = 0;
                    int level2_2 = 0;
                    int level2_3 = 0;
                    int level2_4 = 0;
                    int level3_1 = 0;
                    int level3_2 = 0;
                    int level3_3 = 0;
                    int level3_4 = 0;
                    int level1_72 = 0;
                    int leve2_213 = 0;
                    int leve2_217 = 0;
                Item.ItemOption optionLevel = null;
                Item.ItemOption optionLevel_5 = null;
                Item.ItemOption optionLevel_50 = null;
                Item.ItemOption optionLevel_77 = null;
                Item.ItemOption optionLevel_103 = null;
                Item.ItemOption optionLeve2_213 = null;
                Item.ItemOption optionLeve2_217 = null;
                
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 213 ) { // Đã Điêu Khắc Chỉ Số
                        leve2_213 = io.param;
                        optionLeve2_213 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 217 ) { // Đá Điêu Khắc Cấp
                        leve2_217 = io.param; // Số Cấp
                        optionLeve2_217 = io; // ID Option
                        break;
                    }
                }
                
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 72 ) {
                        level1_72 = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 50 ) {
                        level1_1 = io.param;
                        optionLevel_50 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 77) {
                        level1_2 = io.param;
                        optionLevel_77 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==103) {
                        level1_3 = io.param;
                        optionLevel_103 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==5) {
                        level1_4 = io.param;
                        optionLevel_5 = io;
                        break;
                    }
                }
                for (ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 50 ) {
                        level2_1 = io.param;
                        
                        break;
                    }
                }
                for (ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id == 77) {
                        level2_2 = io.param;
                        
                        break;
                    }
                }
                for (ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id ==103) {
                        level2_3 = io.param;
                        
                        break;
                    }
                }
                for (ItemOption io : DaKhac.itemOptions) {
                    if (io.optionTemplate.id ==5) {
                        level2_4 = io.param;
                        
                        break;
                    }
                }
                
//                if (ngocboi1 != null) {
//                for (ItemOption itopt : ngocboi1.itemOptions) {
//                    if (itopt.optionTemplate.id == 72) {
//                        if (itopt.param >= 10) {
//                            Service.getInstance().sendThongBao(player, "Trang bị đã Điêu Khắc tối đa");
//                            return;
//                        }
//                    }
//                }
//            }           
                    if (optionLevel != null && level1_72 == 10) {
                        Service.getInstance().sendThongBao(player, "Đã Điêu Khắc Trang Bị tối da");
                        return;                  
                    }
                    if (Util.isTrue(100, 100)) {
                        if (player.inventory.ruby < 100000){
                        Service.getInstance().sendThongBao(player, "Điêu Khắc Chỉ Số vào Đá tốn 100K Hồng Ngọc");
                        return;
                        }
                    else{
                    if (optionLevel == null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null) {
                        if (leve2_217 > 1){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 1");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 1));                        
                        }
                    }
                    
                    if (optionLevel != null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null && level1_72 == 1) {
                        if (leve2_217 > 2 || leve2_217 < 2 ){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 2");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        ngocboi1.itemOptions.remove(optionLevel);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 2));                        
                        }
                    }
                    
                    if (optionLevel != null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null && level1_72 == 2) {
                        if (leve2_217 > 3 || leve2_217 < 3){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 3");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        ngocboi1.itemOptions.remove(optionLevel);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 3));                        
                        }
                    }
                    
                    if (optionLevel != null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null && level1_72 == 3) {
                        if (leve2_217 > 4 || leve2_217 < 4){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 4");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        ngocboi1.itemOptions.remove(optionLevel);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 4));                       
                        }
                    }
                    if (optionLevel != null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null && level1_72 == 4) {
                        if (leve2_217 > 5 || leve2_217 < 5){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 5");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        ngocboi1.itemOptions.remove(optionLevel);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 5));                       
                        }
                    }
                    if (optionLevel != null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null && level1_72 == 5) {
                        if (leve2_217 > 6 || leve2_217 < 6){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 6");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        ngocboi1.itemOptions.remove(optionLevel);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 6));                        
                        }
                    }
                    
                    if (optionLevel != null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null && level1_72 == 6) {
                        if (leve2_217 > 7 || leve2_217 < 7){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 7");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        ngocboi1.itemOptions.remove(optionLevel);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 7));                        
                        }
                    }
                    
                    if (optionLevel != null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null && level1_72 == 7) {
                        if (leve2_217 > 8 || leve2_217 < 8){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 8");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        ngocboi1.itemOptions.remove(optionLevel);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 8));                       
                        }
                    }
                    
                    if (optionLevel != null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null && level1_72 == 8) {
                        if (leve2_217 > 9 || leve2_217 < 9){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 9");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        ngocboi1.itemOptions.remove(optionLevel);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 9));                        
                        }
                    }
                    if (optionLevel != null && ngocboi1 != null && DaKhac != null && optionLeve2_213 != null && level1_72 == 9) {
                        if (leve2_217 > 10 || leve2_217 < 10){
                        Service.getInstance().sendThongBao(player, "Đá Điêu Khắc không phù hợp!\n Hãy dùng Đá Điêu Khắc Chỉ Số Cấp 10");
                        return;
                        }else {
                        ngocboi1.itemOptions.remove(optionLevel_50);
                        ngocboi1.itemOptions.remove(optionLevel_77);
                        ngocboi1.itemOptions.remove(optionLevel_103);
                        ngocboi1.itemOptions.remove(optionLevel_5);
                        ngocboi1.itemOptions.remove(optionLevel);
                        
                        ngocboi1.itemOptions.add(new ItemOption(50, (level1_1 + level2_1)));
                        ngocboi1.itemOptions.add(new ItemOption(77, level1_2 + level2_2));
                        ngocboi1.itemOptions.add(new ItemOption(103, level1_3 + level2_3));
                        ngocboi1.itemOptions.add(new ItemOption(5, level1_4 + level2_4));  
                        ngocboi1.itemOptions.add(new ItemOption(72, 10));                        
                        }
                    }
                    }
                Service.getInstance().sendThongBao(player, "Bạn Điêu Khắc Chỉ Số vào Ngọc Bội thành công");
                player.inventory.ruby -= 100000;
                sendEffectSuccessCombine(player);
                InventoryServiceNew.gI().subQuantityItemsBag(player, DaKhac, 1);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.getInstance().sendMoney(player);
                player.combineNew.itemsCombine.clear();
                reOpenItemCombine(player);
                    }
        }
    }
    
    private void TienHoaNgocBoi(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            Item ngocboi1 = null; 
            Item DaKhac = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.isNotNullItem()) {
                            if (item.template.id == 1258) {
                                DaKhac = item;
                            } else if (item.template.id == 1248
                                    || item.template.id == 1253
                                    || item.template.id == 1254
                                    || item.template.id == 1255
                                    || item.template.id == 1256
                                    || item.template.id == 1257) {
                                ngocboi1 = item;                                
                            }  
                        }
                    }
                }

                    int level1_1 = 0;
                    int level1_2 = 0;
                    int level1_3 = 0;
                    int level1_4 = 0;
                    int level1_72 = 0;
                    int level1_102 = 0;
                    int level1_219 = 0;
                    int level1_220 = 0;
                    int level1_221 = 0;
                    int level1_222 = 0;
                    int level1_223 = 0;
                    int level1_107 = 0;
                    

                Item.ItemOption optionLevel_72 = null;
                Item.ItemOption optionLevel_5 = null;
                Item.ItemOption optionLevel_50 = null;
                Item.ItemOption optionLevel_77 = null;
                Item.ItemOption optionLevel_103 = null;
                Item.ItemOption optionLevel_102 = null;
                Item.ItemOption optionLevel_219 = null;
                Item.ItemOption optionLevel_220 = null;
                Item.ItemOption optionLevel_221 = null;
                Item.ItemOption optionLevel_222 = null;
                Item.ItemOption optionLevel_223 = null;
                Item.ItemOption optionLevel_107 = null;
   
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 72 ) {
                        level1_72 = io.param;
                        optionLevel_72 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 102 ) {
                        level1_102 = io.param;
                        optionLevel_102 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 107 ) {
                        level1_107 = io.param;
                        optionLevel_107 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 219 ) {
                        level1_219 = io.param;
                        optionLevel_219 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 220 ) {
                        level1_220 = io.param;
                        optionLevel_220 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 223 ) {
                        level1_223 = io.param;
                        optionLevel_223 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 221 ) {
                        level1_221 = io.param;
                        optionLevel_221 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 222 ) {
                        level1_222 = io.param;
                        optionLevel_222 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 50 ) {
                        level1_1 = io.param;
                        optionLevel_50 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id == 77) {
                        level1_2 = io.param;
                        optionLevel_77 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==103) {
                        level1_3 = io.param;
                        optionLevel_103 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : ngocboi1.itemOptions) {
                    if (io.optionTemplate.id ==5) {
                        level1_4 = io.param;
                        optionLevel_5 = io;
                        break;
                    }
                }

                        
                    if (ngocboi1.template.id == 1257 && level1_72 == 10) {
                        Service.getInstance().sendThongBao(player, "Ngọc Bội Đã Tiến Hóa Tối Đa");
                        return;                  
                    }
                    
                    if (ngocboi1 == null || DaKhac == null) {
                        Service.getInstance().sendThongBao(player, "Không đủ vật phẩm để Tiến Hóa Ngọc Bội");
                        return;                  
                    }
                    if (Util.isTrue(100, 100)) {
                        if (player.inventory.ruby > 500000 || player.inventory.gold > 50_000_000_000L ){
                     
                    if (ngocboi1.template.id == 1248 && level1_72 == 10 && DaKhac.quantity >=1) {
                                   
                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaKhac, 1);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, ngocboi1, 1);
                        
                        Item item = ItemService.gI().createNewItem((short) 1253);
                        item.itemOptions.add(new Item.ItemOption(50, level1_1 + 20));
                        item.itemOptions.add(new Item.ItemOption(77, level1_2 + 20));
                        item.itemOptions.add(new Item.ItemOption(103, level1_3 + 20));
                        item.itemOptions.add(new Item.ItemOption(5, level1_4 + 10));
                        item.itemOptions.add(new Item.ItemOption(107, level1_107));
                        item.itemOptions.add(new Item.ItemOption(102, level1_102));
                        
                        if (optionLevel_223 != null){
                        item.itemOptions.add(new Item.ItemOption(223, level1_223));
                        item.itemOptions.add(new Item.ItemOption(219, level1_219));
                        item.itemOptions.add(new Item.ItemOption(220, level1_220));
                        item.itemOptions.add(new Item.ItemOption(221, level1_221));
                        item.itemOptions.add(new Item.ItemOption(222, level1_222));
                        
                        }
                        
                        InventoryServiceNew.gI().addItemBag(player, item);
                        Service.getInstance().sendThongBao(player, "Bạn đã Tiến Hóa Ngọc Bội thành công");
                        player.inventory.ruby -= 500000;
                        player.inventory.gold -= 50_000_000_000L;
                        sendEffectSuccessCombine(player);
                
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendMoney(player);
                        player.combineNew.itemsCombine.clear();
                        reOpenItemCombine(player);
                        return;
                    }
                    
                    if (ngocboi1.template.id == 1253 && level1_72 == 10 && DaKhac.quantity >=2) {
                                   
                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaKhac, 2);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, ngocboi1, 1);
                        
                        Item item = ItemService.gI().createNewItem((short) 1254);
                        item.itemOptions.add(new Item.ItemOption(50, level1_1 + 30));
                        item.itemOptions.add(new Item.ItemOption(77, level1_2 + 30));
                        item.itemOptions.add(new Item.ItemOption(103, level1_3 + 30));
                        item.itemOptions.add(new Item.ItemOption(5, level1_4 + 15));
                        item.itemOptions.add(new Item.ItemOption(107, level1_107));
                        item.itemOptions.add(new Item.ItemOption(102, level1_102));
                        
                        if (optionLevel_223 != null){
                        item.itemOptions.add(new Item.ItemOption(223, level1_223));
                        item.itemOptions.add(new Item.ItemOption(219, level1_219));
                        item.itemOptions.add(new Item.ItemOption(220, level1_220));
                        item.itemOptions.add(new Item.ItemOption(221, level1_221));
                        item.itemOptions.add(new Item.ItemOption(222, level1_222));
                        }
                     
                        InventoryServiceNew.gI().addItemBag(player, item);
                        Service.getInstance().sendThongBao(player, "Bạn đã Tiến Hóa Ngọc Bội thành công");
                        player.inventory.ruby -= 500000;
                        player.inventory.gold -= 50_000_000_000L;
                        sendEffectSuccessCombine(player);
                
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendMoney(player);
                        player.combineNew.itemsCombine.clear();
                        reOpenItemCombine(player);
                        return;
                    }
                    
                    if (ngocboi1.template.id == 1254 && level1_72 == 10 && DaKhac.quantity >=3) {
                                   
                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaKhac, 3);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, ngocboi1, 1);
                        
                        Item item = ItemService.gI().createNewItem((short) 1255);
                        item.itemOptions.add(new Item.ItemOption(50, level1_1 + 45));
                        item.itemOptions.add(new Item.ItemOption(77, level1_2 + 45));
                        item.itemOptions.add(new Item.ItemOption(103, level1_3 + 45));
                        item.itemOptions.add(new Item.ItemOption(5, level1_4 + 20));
                        item.itemOptions.add(new Item.ItemOption(107, level1_107));
                        item.itemOptions.add(new Item.ItemOption(102, level1_102));
                        
                        if (optionLevel_223 != null){
                        item.itemOptions.add(new Item.ItemOption(223, level1_223));
                        item.itemOptions.add(new Item.ItemOption(219, level1_219));
                        item.itemOptions.add(new Item.ItemOption(220, level1_220));
                        item.itemOptions.add(new Item.ItemOption(221, level1_221));
                        item.itemOptions.add(new Item.ItemOption(222, level1_222));
                        }
                        InventoryServiceNew.gI().addItemBag(player, item);
                        Service.getInstance().sendThongBao(player, "Bạn đã Tiến Hóa Ngọc Bội thành công");
                        player.inventory.ruby -= 500000;
                        player.inventory.gold -= 50_000_000_000L;
                        sendEffectSuccessCombine(player);
                
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendMoney(player);
                        player.combineNew.itemsCombine.clear();
                        reOpenItemCombine(player);
                        return;
                    }
                    
                    if (ngocboi1.template.id == 1255 && level1_72 == 10 && DaKhac.quantity >=4) {
                                   
                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaKhac, 4);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, ngocboi1, 1);
                        
                        Item item = ItemService.gI().createNewItem((short) 1256);
                        item.itemOptions.add(new Item.ItemOption(50, level1_1 + 70));
                        item.itemOptions.add(new Item.ItemOption(77, level1_2 + 70));
                        item.itemOptions.add(new Item.ItemOption(103, level1_3 + 70));
                        item.itemOptions.add(new Item.ItemOption(5, level1_4 + 35));
                        item.itemOptions.add(new Item.ItemOption(107, level1_107));
                        item.itemOptions.add(new Item.ItemOption(102, level1_102));
                        
                        if (optionLevel_223 != null){
                        item.itemOptions.add(new Item.ItemOption(223, level1_223));
                        item.itemOptions.add(new Item.ItemOption(219, level1_219));
                        item.itemOptions.add(new Item.ItemOption(220, level1_220));
                        item.itemOptions.add(new Item.ItemOption(221, level1_221));
                        item.itemOptions.add(new Item.ItemOption(222, level1_222));
                        }
                        InventoryServiceNew.gI().addItemBag(player, item);
                        Service.getInstance().sendThongBao(player, "Bạn đã Tiến Hóa Ngọc Bội thành công");
                        player.inventory.ruby -= 500000;
                        player.inventory.gold -= 50_000_000_000L;
                        sendEffectSuccessCombine(player);
                
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendMoney(player);
                        player.combineNew.itemsCombine.clear();
                        reOpenItemCombine(player);
                        return;
                        
                    }
                    
                    if (ngocboi1.template.id == 1256 && level1_72 == 10 && DaKhac.quantity >=5) {
                                   
                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaKhac, 5);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, ngocboi1, 1);
                        
                        Item item = ItemService.gI().createNewItem((short) 1257);
                        item.itemOptions.add(new Item.ItemOption(50, level1_1 + 100));
                        item.itemOptions.add(new Item.ItemOption(77, level1_2 + 100));
                        item.itemOptions.add(new Item.ItemOption(103, level1_3 + 100));
                        item.itemOptions.add(new Item.ItemOption(5, level1_4 + 50));
                        item.itemOptions.add(new Item.ItemOption(107, level1_107));
                        item.itemOptions.add(new Item.ItemOption(102, level1_102));
                        
                        if (optionLevel_223 != null){
                        item.itemOptions.add(new Item.ItemOption(223, level1_223));
                        item.itemOptions.add(new Item.ItemOption(219, level1_219));
                        item.itemOptions.add(new Item.ItemOption(220, level1_220));
                        item.itemOptions.add(new Item.ItemOption(221, level1_221));
                        item.itemOptions.add(new Item.ItemOption(222, level1_222));
                        }
                        InventoryServiceNew.gI().addItemBag(player, item);
                        Service.getInstance().sendThongBao(player, "Bạn đã Tiến Hóa Ngọc Bội thành công");
                        player.inventory.ruby -= 500000;
                        player.inventory.gold -= 50_000_000_000L;
                        sendEffectSuccessCombine(player);
                
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendMoney(player);
                        player.combineNew.itemsCombine.clear();
                        reOpenItemCombine(player);
                        return;
                    } 
                   
                    
                        }else {
                    Service.getInstance().sendThongBao(player, "Không đủ điều kiện để Tiến Hóa Ngọc Bội");
                    return;
                     }
                    }
        }
    }
    
    
    private void ThangCapTrangBi(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            Item Trangbi = null; 
            Item DaBV = null;
            Item LuaThan = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.type == 0 
                                    || item.template.type == 1
                                    || item.template.type == 2
                                    || item.template.type == 3
                                    || item.template.type == 4) {
                                Trangbi = item;
                            } else if (item.template.id == 987) {
                                DaBV = item;                                
                            } else if (item.template.id == 1250) {
                                LuaThan = item;                                
                            }
                    }
                }

                    int level_102 = 0;
                    int level_216 = 0;
                    int level_107 = 0;
                    Item.ItemOption optionLevel_102 = null;
                    Item.ItemOption optionLevel_216 = null;
                    Item.ItemOption optionLevel_107 = null;
                    
                for (Item.ItemOption io : Trangbi.itemOptions) {
                    if (io.optionTemplate.id == 102 ) { // Số Sao Pha Lê
                        level_102 = io.param;
                        optionLevel_102 = io;
                        break;
                    }
                }
                
                for (Item.ItemOption io : Trangbi.itemOptions) {
                    if (io.optionTemplate.id == 216 ) { // Đã Thăng Cấp Mấy Lần
                        level_216 = io.param;
                        optionLevel_216 = io;
                        break;
                    }
                }
                for (Item.ItemOption io : Trangbi.itemOptions) {
                    if (io.optionTemplate.id == 107 ) { 
                        level_107 = io.param;
                        optionLevel_107 = io;
                        break;
                    }
                }
                
                    if ( level_102 < 16 ) {
                        Service.getInstance().sendThongBao(player, "Ta Cần Trang Bị Pha Lê Hóa 16 Sao");
                        return;                  
                    }
                    if (Util.isTrue(100, 100)) {
                        
                        if (player.inventory.ruby < 100000){
                        Service.getInstance().sendThongBao(player, "Điêu Khắc Chỉ Số vào Đá tốn 100K Hồng Ngọc");
                        return;
                        }
                    else{
                            
                    if (optionLevel_216 == null && level_102 == 16 && Trangbi != null && DaBV != null && LuaThan != null ) {
                        if (DaBV.quantity < 100 || LuaThan.quantity < 100 ){
                        Service.getInstance().sendThongBao(player, "Không đủ 100 Đá Bảo Vệ hoặc không đủ 100 Lửa Thần");
                        return;
                        }else {
                        Trangbi.itemOptions.remove(optionLevel_102);
                        Trangbi.itemOptions.remove(optionLevel_107);
                        Trangbi.itemOptions.add(new ItemOption(216, 1));
                        if (Util.isTrue(40, 100)) {
                            Trangbi.itemOptions.add(new ItemOption(194, 1));
                            Trangbi.itemOptions.add(new ItemOption(198, 5));                        
                        } else if (Util.isTrue(30, 100)){
                            Trangbi.itemOptions.add(new ItemOption(195, 1));
                            Trangbi.itemOptions.add(new ItemOption(199, 10));                        
                        } else if (Util.isTrue(20, 100)){
                            Trangbi.itemOptions.add(new ItemOption(196, 1));
                            Trangbi.itemOptions.add(new ItemOption(200, 15));                        
                        } else {
                            Trangbi.itemOptions.add(new ItemOption(197, 1));
                            Trangbi.itemOptions.add(new ItemOption(201, 20));                        
                        }
                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaBV, 100);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, LuaThan, 100);
                        }
                    }
                }
                Service.getInstance().sendThongBao(player, "Bạn Thăng Cấp Trang Bị thành công");
                player.inventory.ruby -= 100000;
                sendEffectSuccessCombine(player);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.getInstance().sendMoney(player);
                player.combineNew.itemsCombine.clear();
                reOpenItemCombine(player);
                    }
        }
    }
    private void psHoaTrangBi(Player player) {

        if (player.combineNew.itemsCombine.size() != 2) {
            Service.getInstance().sendThongBao(player, "Thiếu nguyên liệu");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isTrangBiHacHoa()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu trang bị pháp sư");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 2046).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu đá pháp sư");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (player.inventory.gold < COST) {
                Service.getInstance().sendThongBao(player, "Con cần thêm vàng để pháp sư hóa...");
                return;
            }
            if (player.inventory.ruby < RUBY) {
                Service.getInstance().sendThongBao(player, "Con cần thêm hồng ngọc để pháp sư hóa...");
                return;
            }
            player.inventory.gold -= COST;
            player.inventory.ruby -= RUBY;
            Item daHacHoa = player.combineNew.itemsCombine.stream().filter(item -> item.template.id == 2046).findFirst().get();
            Item trangBiHacHoa = player.combineNew.itemsCombine.stream().filter(Item::isTrangBiHacHoa).findFirst().get();
            if (daHacHoa == null) {
                Service.getInstance().sendThongBao(player, "Thiếu đá pháp sư");
                return;
            }
            if (trangBiHacHoa == null) {
                Service.getInstance().sendThongBao(player, "Thiếu trang bị pháp sư");
                return;
            }

            if (trangBiHacHoa != null) {
                for (ItemOption itopt : trangBiHacHoa.itemOptions) {
                    if (itopt.optionTemplate.id == 223) {
                        if (itopt.param >= 8) {
                            Service.getInstance().sendThongBao(player, "Trang bị đã đạt tới giới hạn pháp sư");
                            return;
                        }
                    }
                }
            }

            if (Util.isTrue(100, 100)) {
                sendEffectSuccessCombine(player);
                List<Integer> idOptionHacHoa = Arrays.asList(219, 220, 221, 222);
                int randomOption = idOptionHacHoa.get(Util.nextInt(0, 3));
                if (!trangBiHacHoa.haveOption(223)) {
                    trangBiHacHoa.itemOptions.add(new ItemOption(223, 1));
                } else {
                    for (ItemOption itopt : trangBiHacHoa.itemOptions) {
                        if (itopt.optionTemplate.id == 223) {
                            itopt.param += 1;
                            break;
                        }
                    }
                }
                if (!trangBiHacHoa.haveOption(randomOption)) {
                    trangBiHacHoa.itemOptions.add(new ItemOption(randomOption, Util.nextInt(5,10)));
                } else {
                    for (ItemOption itopt : trangBiHacHoa.itemOptions) {
                        if (itopt.optionTemplate.id == randomOption) {
                            itopt.param += Util.nextInt(5,10);
                            break;
                        }
                    }
                }

                Service.getInstance().sendThongBao(player, "Bạn đã pháp sư hóa thành công");
            } else {
                sendEffectFailCombine(player);
            }
            InventoryServiceNew.gI().subQuantityItemsBag(player, daHacHoa, 1);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendMoney(player);
            player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }
    
    private void TinhLuyenTrangBi (Player player) {
       if (player.combineNew.itemsCombine.size() == 5) {
                        Item TBTinhAn = null;
                        Item ManhTinhAn = null;
                        Item LuaThan = null;
                        Item VinhDu = null;
                        Item DaTinhLuyen = null;
                        
                            for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id >= 1048 && item.template.id <= 1062 ) {
                                TBTinhAn = item;
                            } else if (item.template.id == 1232) {
                                ManhTinhAn = item;
                            } else if (item.template.id == 1250) {
                                LuaThan = item;
                            } else if (item.template.id == 1249) {
                                VinhDu = item;
                            } else if (item.template.id == 1265) {
                                DaTinhLuyen = item;
                            }  
                        }
                    } 
                        
                        
                    int level_34 = 0;
                    int level_202 = 0;
                    int level_203 = 0;
                    int level_205 = 0;
                    
                    Item.ItemOption optionLevel_34 = null;
                    Item.ItemOption optionLevel_202 = null;
                    Item.ItemOption optionLevel_203 = null;
                    Item.ItemOption optionLevel_205 = null;
                    
                    
                    for (Item.ItemOption io : TBTinhAn.itemOptions) {
                    if (io.optionTemplate.id == 34 ) {
                        level_34 = io.param;
                        optionLevel_34 = io;
                        break;
                    }
                } 
                    for (Item.ItemOption io : TBTinhAn.itemOptions) {
                    if (io.optionTemplate.id == 202 ) {
                        level_202 = io.param;
                        optionLevel_202 = io;
                        break;
                    }
                }
                    for (Item.ItemOption io : TBTinhAn.itemOptions) {
                    if (io.optionTemplate.id == 203 ) {
                        level_203 = io.param;
                        optionLevel_203 = io;
                        break;
                    }
                }
                    
                    for (Item.ItemOption io : TBTinhAn.itemOptions) {
                    if (io.optionTemplate.id == 205 ) {
                        level_205 = io.param;
                        optionLevel_205 = io;
                        break;
                    }
                }
        
                    if (level_203 >= 10) {
                        Service.getInstance().sendThongBao(player, "Trang Bị Đã Tinh Luyện Tối Đa");
                        return;                  
                    }
            
                    if (TBTinhAn == null || ManhTinhAn == null || LuaThan == null || VinhDu == null || DaTinhLuyen == null) {
                        Service.getInstance().sendThongBao(player, "Không đủ vật phẩm để Tinh Luyện Trang Bị");
                        return;                  
                    }
                    
                    if (player.inventory.gold < COST_TINH_LUYEN || player.inventory.ruby < RUBY_TINH_LUYEN || player.inventory.gem < GEM_TINH_LUYEN ) {
                        Service.getInstance().sendThongBao(player, "Không đủ tiền để Tinh Luyện");
                        return;                  
                    }

                    
                    if (Util.isTrue(100, 100)) {                
                        if ( TBTinhAn != null && optionLevel_34 != null ){
                                if (ManhTinhAn.quantity >=10 && LuaThan.quantity >=100 && VinhDu.quantity >=20 && DaTinhLuyen.quantity >=1) {
//                                        sendEffectFailCombine(player);

                                        if(optionLevel_202 == null){
                                        TBTinhAn.itemOptions.add(new Item.ItemOption(202, 1));
                                            } else {
                                            optionLevel_202.param += Util.nextInt(1, 3);
                                                if(optionLevel_202.param >= 100){
                                                    if(optionLevel_203 == null){
                                                        TBTinhAn.itemOptions.add(new Item.ItemOption(203, 1));
                                                        TBTinhAn.itemOptions.add(new Item.ItemOption(205, 5));
                                                        } else {
                                                        optionLevel_203.param++;
                                                        optionLevel_205.param += 5;
                                                                }
                                                    optionLevel_202.param -= 100; 
                                                        }
                                                    }
                        InventoryServiceNew.gI().subQuantityItemsBag(player, ManhTinhAn, 10);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, LuaThan, 100);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 10);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaTinhLuyen, 1);
                        Service.getInstance().sendThongBao(player, "Bạn đã Tinh Luyện Thành Công");
                        
                        player.inventory.ruby -= RUBY_TINH_LUYEN;
                        player.inventory.gold -= COST_TINH_LUYEN;
                        player.inventory.gem -= GEM_TINH_LUYEN;

                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendMoney(player);
                        reOpenItemCombine(player);
                        return;
                        }
                    }
            }
        } else {
                    Service.getInstance().sendThongBao(player, "Thiếu Nguyên Liệu");
                    return;                  
               } 
    }

    private void tayHacHoaTrangBi(Player player) {

        if (player.combineNew.itemsCombine.size() != 2) {
            Service.getInstance().sendThongBao(player, "Thiếu nguyên liệu");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isTrangBiHacHoa()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu trang bị hắc hóa");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 2047).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu đá pháp sư");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (player.inventory.gold < 0) {
                Service.getInstance().sendThongBao(player, "Con cần thêm vàng để đổi...");
                return;
            }
            player.inventory.gold -= 0;
            Item buagiaihachoa = player.combineNew.itemsCombine.stream().filter(item -> item.template.id == 2047).findFirst().get();
            Item trangBiHacHoa = player.combineNew.itemsCombine.stream().filter(Item::isTrangBiHacHoa).findFirst().get();
            if (buagiaihachoa == null) {
                Service.getInstance().sendThongBao(player, "Thiếu bùa giải pháp sư");
                return;
            }
            if (trangBiHacHoa == null) {
                Service.getInstance().sendThongBao(player, "Thiếu trang bị pháp sư");
                return;
            }

            if (Util.isTrue(100, 100)) {
                sendEffectSuccessCombine(player);
                List<Integer> idOptionHacHoa = Arrays.asList(219, 220, 221, 222);

                ItemOption option_223 = new ItemOption();
                ItemOption option_219 = new ItemOption();
                ItemOption option_220 = new ItemOption();
                ItemOption option_221 = new ItemOption();
                ItemOption option_222 = new ItemOption();

                for (ItemOption itopt : trangBiHacHoa.itemOptions) {
                    if (itopt.optionTemplate.id == 223) {
                        System.out.println("223");
                        option_223 = itopt;
                    }
                    if (itopt.optionTemplate.id == 219) {
                        System.out.println("219");
                        option_219 = itopt;
                    }
                    if (itopt.optionTemplate.id == 220) {
                        System.out.println("220");
                        option_220 = itopt;
                    }
                    if (itopt.optionTemplate.id == 221) {
                        System.out.println("221");
                        option_221 = itopt;
                    }
                    if (itopt.optionTemplate.id == 222) {
                        System.out.println("222");
                        option_222 = itopt;
                    }
                }
                if (option_223 != null) {
                    trangBiHacHoa.itemOptions.remove(option_223);
                }
                if (option_219 != null) {
                    trangBiHacHoa.itemOptions.remove(option_219);
                }
                if (option_220 != null) {
                    trangBiHacHoa.itemOptions.remove(option_220);
                }
                if (option_221 != null) {
                    trangBiHacHoa.itemOptions.remove(option_221);
                }
                if (option_222 != null) {
                    trangBiHacHoa.itemOptions.remove(option_222);
                }
                Service.getInstance().sendThongBao(player, "Bạn đã tẩy thành công");
                InventoryServiceNew.gI().sendItemBags(player);
            } else {
                sendEffectFailCombine(player);
            }
            InventoryServiceNew.gI().subQuantityItemsBag(player, buagiaihachoa, 1);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendMoney(player);
            player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        }
    }

        public void GetTrangBiKichHoathuydiet(Player player, int id){
        Item item = ItemService.gI().createNewItem((short)id);
        int[][] optionNormal = {{127,128},{130,132},{133,135}};
        int[][] paramNormal = {{139,140},{142,144},{136,138}};
        int[][] optionVIP = {{129},{131},{134}};
        int[][] paramVIP = {{141},{143},{137}};
        int random = Util.nextInt(optionNormal.length);
        int randomSkh = Util.nextInt(100);
        if (item.template.type== 0){
            item.itemOptions.add(new ItemOption(47, Util.nextInt(800,1120)));
        }
        if (item.template.type== 1){
            item.itemOptions.add(new ItemOption(22, Util.nextInt(80,104)));
        }
        if (item.template.type== 2){
            item.itemOptions.add(new ItemOption(0, Util.nextInt(8800,12000)));
        }
        if (item.template.type== 3){
            item.itemOptions.add(new ItemOption(23, Util.nextInt(80,120)));
        }
        if (item.template.type== 4){
            item.itemOptions.add(new ItemOption(14, Util.nextInt(17,21)));
        }
//        if (randomSkh <= 20){//tile ra do kich hoat
//            if (randomSkh <= 5){ // tile ra option vip
//        item.itemOptions.add(new ItemOption(optionVIP[player.gender][0], 0));
//        item.itemOptions.add(new ItemOption(paramVIP[player.gender][0], 0));
//        item.itemOptions.add(new ItemOption(30, 0));
//            }else{// 
//        item.itemOptions.add(new ItemOption(optionNormal[player.gender][random], 0));
//        item.itemOptions.add(new ItemOption(paramNormal[player.gender][random], 0));
//        item.itemOptions.add(new ItemOption(30, 0));
//            }
//        }
        
        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }
    public void GetTrangBiKichHoatthiensu(Player player, int id){
        Item item = ItemService.gI().createNewItem((short)id);
        int[][] optionNormal = {{127,128},{130,132},{133,135}};
        int[][] paramNormal = {{139,140},{142,144},{136,138}};
        int[][] optionVIP = {{129},{131},{134}};
        int[][] paramVIP = {{141},{143},{137}};
        int random = Util.nextInt(optionNormal.length);
        int randomSkh = Util.nextInt(100);
        if (item.template.type== 0){
            item.itemOptions.add(new ItemOption(47, Util.nextInt(2000,2500)));
        }
        if (item.template.type== 1){
            item.itemOptions.add(new ItemOption(22, Util.nextInt(150,200)));
        }
        if (item.template.type== 2){
            item.itemOptions.add(new ItemOption(0, Util.nextInt(18000,20000)));
        }
        if (item.template.type== 3){
            item.itemOptions.add(new ItemOption(23, Util.nextInt(150,200)));
        }
        if (item.template.type== 4){
            item.itemOptions.add(new ItemOption(14, Util.nextInt(20,25)));
        }
        if (randomSkh <= 20){//tile ra do kich hoat
            if (randomSkh <= 5){ // tile ra option vip
        item.itemOptions.add(new ItemOption(optionVIP[player.gender][0], 0));
        item.itemOptions.add(new ItemOption(paramVIP[player.gender][0], 0));
        item.itemOptions.add(new ItemOption(30, 0));
            }else{// 
        item.itemOptions.add(new ItemOption(optionNormal[player.gender][random], 0));
        item.itemOptions.add(new ItemOption(paramNormal[player.gender][random], 0));
        item.itemOptions.add(new ItemOption(30, 0));
            }
        }
        
        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    private void doiKiemThan(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            Item keo = null, luoiKiem = null, chuoiKiem = null;
            for (Item it : player.combineNew.itemsCombine) {
                if (it.template.id == 2015) {
                    keo = it;
                } else if (it.template.id == 2016) {
                    chuoiKiem = it;
                } else if (it.template.id == 2017) {
                    luoiKiem = it;
                }
            }
            if (keo != null && keo.quantity >= 99 && luoiKiem != null && chuoiKiem != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2018);
                    item.itemOptions.add(new Item.ItemOption(50, Util.nextInt(9, 15)));
                    item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(8, 15)));
                    item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(8, 15)));
                    if (Util.isTrue(80, 100)) {
                        item.itemOptions.add(new Item.ItemOption(93, Util.nextInt(1, 15)));
                    }
                    InventoryServiceNew.gI().addItemBag(player, item);

                    InventoryServiceNew.gI().subQuantityItemsBag(player, keo, 99);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, luoiKiem, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, chuoiKiem, 1);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiChuoiKiem(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item manhNhua = player.combineNew.itemsCombine.get(0);
            if (manhNhua.template.id == 2014 && manhNhua.quantity >= 99) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2016);
                    InventoryServiceNew.gI().addItemBag(player, item);

                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhNhua, 99);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiLuoiKiem(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item manhSat = player.combineNew.itemsCombine.get(0);
            if (manhSat.template.id == 2013 && manhSat.quantity >= 99) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2017);
                    InventoryServiceNew.gI().addItemBag(player, item);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhSat, 99);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiManhKichHoat(Player player) {
        if (player.combineNew.itemsCombine.size() == 2 || player.combineNew.itemsCombine.size() == 3) {
            Item nr1s = null, doThan = null, buaBaoVe = null;
            for (Item it : player.combineNew.itemsCombine) {
                if (it.template.id == 14) {
                    nr1s = it;
                } else if (it.template.id == 2010) {
                    buaBaoVe = it;
                } else if (it.template.id >= 555 && it.template.id <= 567) {
                    doThan = it;
                }
            }

            if (nr1s != null && doThan != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= COST_DOI_MANH_KICH_HOAT) {
                    player.inventory.gold -= COST_DOI_MANH_KICH_HOAT;
                    int tiLe = buaBaoVe != null ? 100 : 50;
                    if (Util.isTrue(tiLe, 100)) {
                        sendEffectSuccessCombine(player);
                        Item item = ItemService.gI().createNewItem((short) 2009);
                        item.itemOptions.add(new Item.ItemOption(30, 0));
                        InventoryServiceNew.gI().addItemBag(player, item);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, nr1s, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, doThan, 1);
                    if (buaBaoVe != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, buaBaoVe, 1);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }
            } else {
                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị thần linh và 1 viên ngọc rồng 1 sao", "Đóng");
            }
        }
    }

    private void phanradothanlinh(Player player) {
                if (player.combineNew.itemsCombine.size() == 1) {
            player.inventory.gold -= 50000000;
            List<Integer> itemdov2 = new ArrayList<>(Arrays.asList(562, 564, 566));
            Item item = player.combineNew.itemsCombine.get(0);
            int couponAdd = itemdov2.stream().anyMatch(t -> t == item.template.id) ? 2 : item.template.id == 561 ? 3 : 1;
            sendEffectSuccessCombine(player);
            Item dangusac = ItemService.gI().createNewItem((short) 674);
            Item dangusac1 = ItemService.gI().createNewItem((short) 674);
            Item dangusac2 = ItemService.gI().createNewItem((short) 674);
                                        InventoryServiceNew.gI().addItemBag(player, dangusac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        if (item.template.id == 561){
                                         InventoryServiceNew.gI().addItemBag(player, dangusac);
                                         InventoryServiceNew.gI().addItemBag(player, dangusac1);
                                         InventoryServiceNew.gI().addItemBag(player, dangusac2);
                                        InventoryServiceNew.gI().sendItemBags(player);}
                                        else if (item.template.id == 562 || item.template.id == 564 || item.template.id == 566){
                                        InventoryServiceNew.gI().addItemBag(player, dangusac);
                                        InventoryServiceNew.gI().addItemBag(player, dangusac1);
                                        InventoryServiceNew.gI().sendItemBags(player);}
                                        Service.gI().sendThongBaoOK(player, "Bạn Nhận Được Đá Ngũ Sắc");
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
            player.combineNew.itemsCombine.clear();
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            reOpenItemCombine(player);
        }
    }

    public void openDTS(Player player) {
        //check sl đồ tl, đồ hd
        // new update 2 mon huy diet + 1 mon than linh(skh theo style) +  5 manh bat ki
        if (player.combineNew.itemsCombine.size() != 4) {
            Service.getInstance().sendThongBao(player, "Thiếu đồ");
            return;
        }
        if (player.inventory.gold < COST) {
            Service.getInstance().sendThongBao(player, "Con không đủ vàng");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 1) {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
            return;
        }
        Item itemTL = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTL()).findFirst().get();
        List<Item> itemHDs = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDHD()).collect(Collectors.toList());
        Item itemManh = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).findFirst().get();

        player.inventory.gold -= COST;
        sendEffectSuccessCombine(player);
        short[][] itemIds = {{1048, 1051, 1054, 1057, 1060}, {1049, 1052, 1055, 1058, 1061}, {1050, 1053, 1056, 1059, 1062}}; // thứ tự td - 0,nm - 1, xd - 2

        Item itemTS = ItemService.gI().DoThienSu(itemIds[itemTL.template.gender > 2 ? player.gender : itemTL.template.gender][itemManh.typeIdManh()], itemTL.template.gender);
        itemTS.itemOptions.add(new ItemOption(30, 1));
        itemTS.itemOptions.add(new ItemOption(21, 120));
        InventoryServiceNew.gI().addItemBag(player, itemTS);

        InventoryServiceNew.gI().subQuantityItemsBag(player, itemTL, 1);
        InventoryServiceNew.gI().subQuantityItemsBag(player, itemManh, 999);
        itemHDs.forEach(item -> InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1));
        InventoryServiceNew.gI().sendItemBags(player);
        Service.getInstance().sendMoney(player);
        Service.getInstance().sendThongBao(player, "Bạn đã nhận được " + itemTS.template.name);
        player.combineNew.itemsCombine.clear();
        reOpenItemCombine(player);
    }

    public void openSKHVIP(Player player) {
        // 1 thiên sứ + 2 món kích hoạt -- món đầu kh làm gốc
        if (player.combineNew.itemsCombine.size() != 3) {
            Service.getInstance().sendThongBao(player, "Thiếu nguyên liệu");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTS()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu đồ thiên sứ");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).count() != 2) {
            Service.getInstance().sendThongBao(player, "Thiếu đồ kích hoạt");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (player.inventory.gold < 1) {
                Service.getInstance().sendThongBao(player, "Con cần thêm vàng để đổi...");
                return;
            }
            player.inventory.gold -= COST;
            Item itemTS = player.combineNew.itemsCombine.stream().filter(Item::isDTS).findFirst().get();
            List<Item> itemSKH = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).collect(Collectors.toList());
            CombineServiceNew.gI().sendEffectOpenItem(player, itemTS.template.iconID, itemTS.template.iconID);
            short itemId;
            if (itemTS.template.gender == 3 || itemTS.template.type == 4) {
                itemId = Manager.radaSKHVip[Util.nextInt(0, 5)];
                if (player.getSession().bdPlayer > 0 && Util.isTrue(1, (int) (100 / player.getSession().bdPlayer))) {
                    itemId = Manager.radaSKHVip[6];
                }
            } else {
                itemId = Manager.doSKHVip[itemTS.template.gender][itemTS.template.type][Util.nextInt(0, 5)];
                if (player.getSession().bdPlayer > 0 && Util.isTrue(1, (int) (100 / player.getSession().bdPlayer))) {
                    itemId = Manager.doSKHVip[itemTS.template.gender][itemTS.template.type][6];
                }
            }
            int skhId = ItemService.gI().randomSKHId(itemTS.template.gender);
            Item item;
            if (new Item(itemId).isDTL()) {
                item = Util.ratiItemTL(itemId);
                item.itemOptions.add(new Item.ItemOption(skhId, 1));
                item.itemOptions.add(new Item.ItemOption(ItemService.gI().optionIdSKH(skhId), 1));
                item.itemOptions.remove(item.itemOptions.stream().filter(itemOption -> itemOption.optionTemplate.id == 21).findFirst().get());
                item.itemOptions.add(new Item.ItemOption(21, 15));
                item.itemOptions.add(new Item.ItemOption(30, 1));
            } else {
                item = ItemService.gI().itemSKH(itemId, skhId);
            }
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemTS, 1);
            itemSKH.forEach(i -> InventoryServiceNew.gI().subQuantityItemsBag(player, i, 1));
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendMoney(player);
            player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }

    

    private void doiVeHuyDiet(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item item = player.combineNew.itemsCombine.get(0);
            if (item.isNotNullItem() && item.template.id >= 555 && item.template.id <= 567) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= COST_DOI_VE_DOI_DO_HUY_DIET) {
                    player.inventory.gold -= COST_DOI_VE_DOI_DO_HUY_DIET;
                    Item ticket = ItemService.gI().createNewItem((short) (2001 + item.template.type));
                    ticket.itemOptions.add(new Item.ItemOption(30, 0));
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                    InventoryServiceNew.gI().addItemBag(player, ticket);
                    sendEffectOpenItem(player, item.template.iconID, ticket.template.iconID);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void epSaoTrangBi(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.getInstance().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item trangBi = null;
            Item daPhaLe = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (isTrangBiPhaLeHoa(item)) {
                    trangBi = item;
                } else if (isDaPhaLe(item)) {
                    daPhaLe = item;
                }
            }
            int star = 0; //sao pha lê đã ép
            int starEmpty = 0; //lỗ sao pha lê
            if (trangBi != null && daPhaLe != null) {
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : trangBi.itemOptions) {
                    if (io.optionTemplate.id == 102) {
                        star = io.param;
                        optionStar = io;
                    } else if (io.optionTemplate.id == 107) {
                        starEmpty = io.param;
                    }
                }
                if (star < starEmpty) {
                    player.inventory.gem -= gem;
                    int optionId = getOptionDaPhaLe(daPhaLe);
                    int param = getParamDaPhaLe(daPhaLe);
                    Item.ItemOption option = null;
                    for (Item.ItemOption io : trangBi.itemOptions) {
                        if (io.optionTemplate.id == optionId) {
                            option = io;
                            break;
                        }
                    }
                    if (option != null) {
                        option.param += param;
                    } else {
                        trangBi.itemOptions.add(new Item.ItemOption(optionId, param));
                    }
                    if (optionStar != null) {
                        optionStar.param++;
                    } else {
                        trangBi.itemOptions.add(new Item.ItemOption(102, 1));
                    }

                    InventoryServiceNew.gI().subQuantityItemsBag(player, daPhaLe, 1);
                    sendEffectSuccessCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.getInstance().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }
    
    private void epSaoTrangBiTC(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.getInstance().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item daPhaLe = null;
            Item Trangbi = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.type == 0 
                                    || item.template.type == 1
                                    || item.template.type == 2
                                    || item.template.type == 3
                                    || item.template.type == 4) {
                                Trangbi = item;
                            } else if (isDaPhaLeTC1(item)) {
                            daPhaLe = item;
                        }
                            
                        }
                    } 
                    int star = 0; //sao pha lê đã ép
                    int starEmpty = 0; //lỗ sao pha lê
                    int level_216 = 0;
                    Item.ItemOption optionLevel_216 = null;
                        
  
                        for (Item.ItemOption io : Trangbi.itemOptions) {
                            if (io.optionTemplate.id == 216) {
                                level_216 = io.param;
                                optionLevel_216 = io;
                                break;
                            }
                        }
                        
            if (Trangbi != null && daPhaLe != null && optionLevel_216 != null ) {
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : Trangbi.itemOptions) {
                    if (io.optionTemplate.id == 102) {
                        star = io.param;
                        optionStar = io;
                    } else if (io.optionTemplate.id == 107) {
                        starEmpty = io.param;
                    }
                }
                if (star < starEmpty) {
                    player.inventory.gem -= gem;
                    int optionId = getOptionDaPhaLeTC1(daPhaLe);
                    int param = getParamDaPhaLeTC1(daPhaLe);
                    Item.ItemOption option = null;
                    for (Item.ItemOption io : Trangbi.itemOptions) {
                        if (io.optionTemplate.id == optionId) {
                            option = io;
                            break;
                        }
                    }
                    if (option != null) {
                        option.param += param;
                    } else {
                        Trangbi.itemOptions.add(new Item.ItemOption(optionId, param));
                    }
                    if (optionStar != null) {
                        optionStar.param++;
                    } else {
                        Trangbi.itemOptions.add(new Item.ItemOption(102, 1));
                    }

                    InventoryServiceNew.gI().subQuantityItemsBag(player, daPhaLe, 1);
//                    sendEffectSuccessCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.getInstance().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }


    private void phaLeHoaTrangBi(Player player) {
        if (!player.combineNew.itemsCombine.isEmpty()) {
            int gold = player.combineNew.goldCombine;
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gold < gold) {
                Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            } else if (player.inventory.gem < gem) {
                Service.getInstance().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item item = player.combineNew.itemsCombine.get(0);
            if (isTrangBiPhaLeHoa(item)) {
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 107) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (star < MAX_STAR_ITEM) {
                    player.inventory.gold -= gold;
                    player.inventory.gem -= gem;
                    byte ratio = (optionStar != null && optionStar.param > 4) ? (byte) 2 : 1;
                    if (Util.isTrue(player.combineNew.ratioCombine, 100 * ratio)) {
                        if (optionStar == null) {
                            item.itemOptions.add(new Item.ItemOption(107, 1));
                        } else {
                            optionStar.param++;
                        }
                        sendEffectSuccessCombine(player);
                        if (optionStar != null && optionStar.param >= 7) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                    + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                        }
                        if (optionStar != null && optionStar.param >= 1 && optionStar.param <= 2) {
                           item.itemOptions.add(new Item.ItemOption(30, 1));
                        }
                    } else {
                        sendEffectFailCombine(player);
                    }
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.getInstance().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }
    
    private void phaLeHoaTrangBi10Lan(Player player) {
        int k = 0;
        for(int i=0 ;i < 10 ;i++ ){
            if (!player.combineNew.itemsCombine.isEmpty()) {
                int gold = player.combineNew.goldCombine;
                int gem = player.combineNew.gemCombine;
                if (player.inventory.gold < gold) {
                    Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                } else if (player.inventory.gem < gem) {
                    Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                    return;
                }
                Item item = player.combineNew.itemsCombine.get(0);
                if (isTrangBiPhaLeHoa(item)) {
                    int star = 0;
                    Item.ItemOption optionStar = null;
                    for (Item.ItemOption io : item.itemOptions) {
                        if (io.optionTemplate.id == 107) {
                            star = io.param;
                            optionStar = io;
                            break;
                        }
                    }
                    if (star < MAX_STAR_ITEM) {
                        player.inventory.gold -= gold;
                        player.inventory.gem -= gem;
                        byte ratio = (optionStar != null && optionStar.param > 4) ? (byte) 2 : 1;
                        if (Util.isTrue(player.combineNew.ratioCombine, 100 * ratio)) {
                            if (optionStar == null) {
                                item.itemOptions.add(new Item.ItemOption(107, 1));
                            } else {
                                optionStar.param++;
                            }
                            sendEffectSuccessCombine(player);
                            if (optionStar != null && optionStar.param >= 7) {
                                ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                        + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                            }
                            k = 1;
                        } else {
                            k = 0;
                            sendEffectFailCombine(player);
                        }
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
            if(k==1){
                Service.gI().sendThongBao(player, "Nâng sao "+(i+1)+"lần");
                break;
            } 
        }
        
    }  

    private void phaLeHoaTrangBi100Lan(Player player) {
        int k = 0;
        for(int i=0 ;i < 100 ;i++ ){
            if (!player.combineNew.itemsCombine.isEmpty()) {
                int gold = player.combineNew.goldCombine;
                int gem = player.combineNew.gemCombine;
                if (player.inventory.gold < gold) {
                    Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                } else if (player.inventory.gem < gem) {
                    Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                    return;
                }
                Item item = player.combineNew.itemsCombine.get(0);
                if (isTrangBiPhaLeHoa(item)) {
                    int star = 0;
                    Item.ItemOption optionStar = null;
                    for (Item.ItemOption io : item.itemOptions) {
                        if (io.optionTemplate.id == 107) {
                            star = io.param;
                            optionStar = io;
                            break;
                        }
                    }
                    if (star < MAX_STAR_ITEM) {
                        player.inventory.gold -= gold;
                        player.inventory.gem -= gem;
                        byte ratio = (optionStar != null && optionStar.param > 4) ? (byte) 2 : 1;
                        if (Util.isTrue(player.combineNew.ratioCombine, 100 * ratio)) {
                            if (optionStar == null) {
                                item.itemOptions.add(new Item.ItemOption(107, 1));
                            } else {
                                optionStar.param++;
                            }
                            sendEffectSuccessCombine(player);
                            if (optionStar != null && optionStar.param >= 7) {
                                ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                        + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                            }
                            k = 1;
                        } else {
                            k = 0;
                            sendEffectFailCombine(player);
                        }
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
            if(k==1){
                Service.gI().sendThongBao(player, "Nâng sao "+(i+1)+"lần");
                break;
            } 
        }
        
    }  

    private void phaLeHoaTrangBiThangCap(Player player) {
        if (!player.combineNew.itemsCombine.isEmpty()) {
            int gold = player.combineNew.goldCombine;
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gold < gold) {
                Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            } else if (player.inventory.gem < gem) {
                Service.getInstance().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            
            Item  Trangbi = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.type == 0 
                                    || item.template.type == 1
                                    || item.template.type == 2
                                    || item.template.type == 3
                                    || item.template.type == 4) {
                                Trangbi = item;
                            } 
                        }
                    } 
                    
                    int level_216 = 0;
                    Item.ItemOption optionLevel_216 = null;
                    
                    for (Item.ItemOption io : Trangbi.itemOptions) {
                            if (io.optionTemplate.id == 216) {
                                level_216 = io.param;
                                optionLevel_216 = io;
                                break;
                            }
                        }
                    
                    
                    
                    
            if (Trangbi != null && optionLevel_216 != null) {
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : Trangbi.itemOptions) {
                    if (io.optionTemplate.id == 107) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (star < MAX_STAR_ITEM_TC1) {
                    player.inventory.gold -= gold;
                    player.inventory.gem -= gem;
                    byte ratio = (optionStar != null && optionStar.param > 4) ? (byte) 2 : 1;
                    if (Util.isTrue(player.combineNew.ratioCombine, 100 * ratio)) {
                        if (optionStar == null) {
                            Trangbi.itemOptions.add(new Item.ItemOption(107, 1));
                        } else {
                            optionStar.param++;
                        }
//                        sendEffectSuccessCombine(player);
                        if (optionStar != null && optionStar.param >= 7) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                    + "thành công " + Trangbi.template.name + " lên " + optionStar.param + " sao pha lê");
                        }  
                    } else {
//                        sendEffectFailCombine(player);
                    }
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.getInstance().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void nhapNgocRong(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (!player.combineNew.itemsCombine.isEmpty()) {
                Item item = player.combineNew.itemsCombine.get(0);
                short iconID = item.template.iconID;
                if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                    Item nr = ItemService.gI().createNewItem((short) (item.template.id - 1));
                    InventoryServiceNew.gI().addItemBag(player, nr);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item, 7);
                    InventoryServiceNew.gI().sendItemBags(player);
                    reOpenItemCombine(player);
                    sendEffectCombineDB(player, iconID);
                }
            }
        }
    }
    
    private void nhapNgocRong10lan(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (!player.combineNew.itemsCombine.isEmpty()) {
                Item item = player.combineNew.itemsCombine.get(0);
                short iconID = item.template.iconID;
                for(int i =0; i <= 10; i++){
                    
                    if(i == 10){
                        Service.gI().sendThongBao(player, "Nâng ngọc rồng" +i+"lần");
                        break;
                    }
                    if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                        Item nr = ItemService.gI().createNewItem((short) (item.template.id - 1));
                        InventoryServiceNew.gI().addItemBag(player, nr);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, item, 7);
                        InventoryServiceNew.gI().sendItemBags(player);
                        reOpenItemCombine(player);
                        sendEffectCombineDB(player, iconID);
                    }
                    if((item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity < 7)){
                        Service.gI().sendThongBao(player, "Nâng ngọc rồng" +(i+1)+"lần");
                        break;

                    }
                }
            }
        }
    }

    private void nhapNgocRong100lan(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (!player.combineNew.itemsCombine.isEmpty()) {
                Item item = player.combineNew.itemsCombine.get(0);
                short iconID = item.template.iconID;
                for(int i =0; i <= 100; i++){
                    
                    if(i == 100){
                        Service.gI().sendThongBao(player, "Nâng ngọc rồng" +i+"lần");
                        break;
                    }
                    if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                        Item nr = ItemService.gI().createNewItem((short) (item.template.id - 1));
                        InventoryServiceNew.gI().addItemBag(player, nr);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, item, 7);
                        InventoryServiceNew.gI().sendItemBags(player);
                        reOpenItemCombine(player);
                        sendEffectCombineDB(player, iconID);
                    }
                    if((item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity < 7)){
                        Service.gI().sendThongBao(player, "Nâng ngọc rồng" +(i+1)+"lần");
                        break;

                    }
                }
            }
        }
    }
    
    

   
    private void antrangbi(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            
            if (player.inventory.gold < COST) {
                Service.getInstance().sendThongBao(player, "Con cần thêm vàng để ép ấn...");
                return;
            }
            if (player.inventory.ruby < RUBY) {
                Service.getInstance().sendThongBao(player, "Con cần thêm hồng ngọc để ép ấn...");
                return;
            }
            
            if (!player.combineNew.itemsCombine.isEmpty()) {
                Item item = player.combineNew.itemsCombine.get(0);
                Item itemAn = player.combineNew.itemsCombine.get(1);
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 34 || io.optionTemplate.id == 35 || io.optionTemplate.id == 36) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (item != null && item.isNotNullItem() && itemAn != null && itemAn.isNotNullItem() && (itemAn.template.id == 1232 || itemAn.template.id == 1233 || itemAn.template.id == 1234) && itemAn.quantity >= 99) {
                    if (optionStar == null){
                        player.inventory.gold -= COST;
                        player.inventory.ruby -= RUBY;
                        
                    if (itemAn.template.id == 1232){
                        if(Util.isTrue(20, 100)){
                            item.itemOptions.add(new Item.ItemOption(34, 0));
                            sendEffectSuccessCombine(player);
                        }else {
                            sendEffectFailCombine(player);
                        }
                    }else if (itemAn.template.id == 1233){
                            if(Util.isTrue(20, 100)){
                            item.itemOptions.add(new Item.ItemOption(35, 0));
                            sendEffectSuccessCombine(player);
                        } else {
                            sendEffectFailCombine(player);
                        }
                    }else if (itemAn.template.id == 1234){
                        if(Util.isTrue(20, 100)){
                            item.itemOptions.add(new Item.ItemOption(36, 0));
                            sendEffectSuccessCombine(player);
                        }else {
                            sendEffectFailCombine(player);
                        }
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, itemAn, 99);
                    Service.getInstance().sendMoney(player);
                    InventoryServiceNew.gI().sendItemBags(player);
                    reOpenItemCombine(player);
                    } else {
                        Service.getInstance().sendThongBao(player, "Trang bị của bạn có ấn rồi mà !!!");
                    }
                }
            }
        }
    }
    public void cheTaoDoTS(Player player) {
        // Công thức vip + x999 Mảnh thiên sứ + đá nâng cấp + đá may mắn
        if (player.combineNew.itemsCombine.size() < 2 || player.combineNew.itemsCombine.size() > 4) {
            Service.getInstance().sendThongBao(player, "Thiếu vật phẩm, vui lòng thêm vào");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isCongThucVip()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu Công thức Vip");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu Mảnh thiên sứ");
            return;
        }
        Item mTS = null, daNC = null, daMM = null, CtVip = null;
        for (Item item : player.combineNew.itemsCombine) {
            if(item.isNotNullItem() && !item.isManhTS() && !item.isDaNangCap() && !item.isDaMayMan() && !item.isCongThucVip()){
                Service.getInstance().sendThongBao(player, "Hãy cho đúng đồ vào nâng cấp.");
                return;
            }
            if (item.isNotNullItem()) {
                if (item.isManhTS()) {
                    mTS = item;
                } else if (item.isDaNangCap()) {
                    daNC = item;
                } else if (item.isDaMayMan()) {
                    daMM = item;
                } else if (item.isCongThucVip()) {
                    CtVip = item;
                }
            }
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0 ) {//check chỗ trống hành trang
            if (player.inventory.gold < 1000000000 || player.inventory.ruby < 20000 ) {
                Service.getInstance().sendThongBao(player, "Không đủ vàng hoặc hồng ngọc để thực hiện");
                return;
            }
            player.inventory.gold -= 1000000000;
            player.inventory.ruby -= 20000;  
            int tiLeMacDinh = 35;
            int tileLucky = 20;
            if (daNC != null) {
                tiLeMacDinh += (daNC.template.id - 1073)*10;
            } else {
                tiLeMacDinh = tiLeMacDinh;
            }
            if (daMM != null) {
                tileLucky += tileLucky*(daMM.template.id - 1078)*10/100;
            } else {
                tileLucky = tileLucky;
            }    
            if (Util.nextInt(0, 100) < tiLeMacDinh) {
                Item itemCtVip = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isCongThucVip()).findFirst().get();
                if (daNC != null) {
                    Item itemDaNangC = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaNangCap()).findFirst().get();
                }
                if (daMM != null) {
                    Item itemDaMayM = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaMayMan()).findFirst().get();
                }
                Item itemManh = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).findFirst().get();      

                short[][] itemIds = {{1048, 1051, 1054, 1057, 1060}, {1049, 1052, 1055, 1058, 1061}, {1050, 1053, 1056, 1059, 1062}}; // thứ tự td - 0,nm - 1, xd - 2
                Item itemTS = ItemService.gI().DoThienSu(itemIds[itemCtVip.template.gender > 2 ? player.gender : itemCtVip.template.gender][itemManh.typeIdManh()], itemCtVip.template.gender);                     
                     
                        
                if (Util.nextInt(0, 100)  <= tileLucky) {
                    int k = Util.nextInt(0, 2);
                    ArrayList<Integer> listOptionBonus = new ArrayList<>();
                    listOptionBonus.add(50);  // tang cong
                    listOptionBonus.add(77);  // hp
                    listOptionBonus.add(103);  // ki
                    listOptionBonus.add(78); // suc huy diet
                    listOptionBonus.add(5); // suc danh chi mang
                    listOptionBonus.add(218); //sd, hp, ki
                    itemTS.itemOptions.add(new ItemOption(227, k+1));
                    for (int i = 0; i <= k; i++) {
                        int indexOption = Util.nextInt(0, listOptionBonus.size()-1);
                        itemTS.itemOptions.add(new ItemOption(listOptionBonus.get(indexOption), Util.nextInt(5, 20)));
                        listOptionBonus.remove(indexOption);
                    }
                }
                itemTS.itemOptions.add(new ItemOption(30, 1));
                itemTS.itemOptions.add(new ItemOption(21, 120));
                InventoryServiceNew.gI().addItemBag(player, itemTS);
                sendEffectSuccessCombine(player);
                if (mTS != null && daMM != null && daNC != null && CtVip != null ) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, daNC, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 999);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, daMM, 1);
                } else if (CtVip != null && mTS != null) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 999);
                } else if (CtVip != null && mTS != null && daNC != null) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 999);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, daNC, 1);
                } else if (CtVip != null && mTS != null && daMM != null) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 999);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, daMM, 1);
                }

            } else {                 //false
                sendEffectFailCombine(player);
                if (mTS != null && daMM != null && daNC != null && CtVip != null ) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, daNC, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 99);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, daMM, 1);
                } else if (CtVip != null && mTS != null) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 99);
                } else if (CtVip != null && mTS != null && daNC != null) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 99);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, daNC, 1);
                } else if (CtVip != null && mTS != null && daMM != null) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 99);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, daMM, 1);
                }
            }
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendMoney(player);
            reOpenItemCombine(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }


    private void nangCapVatPham(Player player) {
        if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() != 1) {
                return;//admin
            }
            Item itemDo = null; //Đổi Cải Trang V2
            Item itemDNC = null;
            Item itemDBV = null;
            for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                        itemDBV = player.combineNew.itemsCombine.get(j);
                        continue;
                    }
                    if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                        itemDo = player.combineNew.itemsCombine.get(j);
                    } else {
                        itemDNC = player.combineNew.itemsCombine.get(j);
                    }
                }
            }
            if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                int countDaNangCap = player.combineNew.countDaNangCap;
                int gold = player.combineNew.goldCombine;
                short countDaBaoVe = player.combineNew.countDaBaoVe;
                if (player.inventory.gold < gold) {
                    Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                }

                if (itemDNC.quantity < countDaNangCap) return;
                if (player.combineNew.itemsCombine.size() == 3) {
                    if (Objects.isNull(itemDBV)) return;
                    if (itemDBV.quantity < countDaBaoVe) return;
                }

                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : itemDo.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (level < MAX_LEVEL_ITEM) {
                    player.inventory.gold -= gold;
                    Item.ItemOption option = null;
                    Item.ItemOption option2 = null;
                    for (Item.ItemOption io : itemDo.itemOptions) {
                        if (io.optionTemplate.id == 47
                                || io.optionTemplate.id == 6
                                || io.optionTemplate.id == 0
                                || io.optionTemplate.id == 7
                                || io.optionTemplate.id == 14
                                || io.optionTemplate.id == 22
                                || io.optionTemplate.id == 23
                                || io.optionTemplate.id == 235
                                || io.optionTemplate.id == 236) {
                            option = io;
                        } else if (io.optionTemplate.id == 27
                                || io.optionTemplate.id == 28) {
                            option2 = io;
                        }
                    }
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        option.param += (option.param * 10 / 100);
                        if (option2 != null) {
                            option2.param += (option2.param * 10 / 100);
                        }
                        if (optionLevel == null) {
                            itemDo.itemOptions.add(new Item.ItemOption(72, 1));
                        } else {
                            optionLevel.param++;
                        }
//                        if (optionLevel != null && optionLevel.param >= 5) {
//                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa nâng cấp "
//                                    + "thành công " + trangBi.template.name + " lên +" + optionLevel.param);
//                        }
                        sendEffectSuccessCombine(player);
                    } else {
                        if ((level == 2 || level == 4 || level == 6) && (player.combineNew.itemsCombine.size() != 3)) {
                            option.param -= (option.param * 10 / 100);
                            if (option2 != null) {
                                option2.param -= (option2.param * 10 / 100);
                            }
                            optionLevel.param--;
                        }
                        sendEffectFailCombine(player);
                    }
                    if (player.combineNew.itemsCombine.size() == 3)
                        InventoryServiceNew.gI().subQuantityItemsBag(player, itemDBV, countDaBaoVe);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, itemDNC, player.combineNew.countDaNangCap);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }
    
    private void nangCapVatPham10(Player player) {
        int k= 0;
        for(int i=0;i<10;i++){

            if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
                if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() != 1) {
                    return;
                }
                if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() != 1) {
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() != 1) {
                    return;//admin
                }
                Item itemDo = null; //Đổi Cải Trang V2
                Item itemDNC = null;
                Item itemDBV = null;
                for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                    if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                        if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                            itemDBV = player.combineNew.itemsCombine.get(j);
                            continue;
                        }
                        if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                            itemDo = player.combineNew.itemsCombine.get(j);
                        } else {
                            itemDNC = player.combineNew.itemsCombine.get(j);
                        }
                    }
                }
                if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                    int countDaNangCap = player.combineNew.countDaNangCap;
                    int gold = player.combineNew.goldCombine;
                    short countDaBaoVe = player.combineNew.countDaBaoVe;
                    if (player.inventory.gold < gold) {
                        Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                        return;
                    }

                    if (itemDNC.quantity < countDaNangCap) return;
                    if (player.combineNew.itemsCombine.size() == 3) {
                        if (Objects.isNull(itemDBV)) return;
                        if (itemDBV.quantity < countDaBaoVe) return;
                    }

                    int level = 0;
                    Item.ItemOption optionLevel = null;
                    for (Item.ItemOption io : itemDo.itemOptions) {
                        if (io.optionTemplate.id == 72) {
                            level = io.param;
                            optionLevel = io;
                            break;
                        }
                    }
                    if (level < MAX_LEVEL_ITEM) {
                        player.inventory.gold -= gold;
                        Item.ItemOption option = null;
                        Item.ItemOption option2 = null;
                        for (Item.ItemOption io : itemDo.itemOptions) {
                            if (io.optionTemplate.id == 47
                                    || io.optionTemplate.id == 6
                                    || io.optionTemplate.id == 0
                                    || io.optionTemplate.id == 7
                                    || io.optionTemplate.id == 14
                                    || io.optionTemplate.id == 22
                                    || io.optionTemplate.id == 23
                                    || io.optionTemplate.id == 235
                                    || io.optionTemplate.id == 236) {
                                option = io;
                            } else if (io.optionTemplate.id == 27
                                    || io.optionTemplate.id == 28) {
                                option2 = io;
                            }
                        }
                        if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                            option.param += (option.param * 10 / 100);
                            if (option2 != null) {
                                option2.param += (option2.param * 10 / 100);
                            }
                            if (optionLevel == null) {
                                itemDo.itemOptions.add(new Item.ItemOption(72, 1));
                            } else {
                                optionLevel.param++;
                            }
                            sendEffectSuccessCombine(player);
                            k=1;
                        } else {
                            if ((level == 2 || level == 4 || level == 6) && (player.combineNew.itemsCombine.size() != 3)) {
                                option.param -= (option.param * 10 / 100);
                                if (option2 != null) {
                                    option2.param -= (option2.param * 10 / 100);
                                }
                                optionLevel.param--;
                            }
                            sendEffectFailCombine(player);
                        }
                        if (player.combineNew.itemsCombine.size() == 3) {
                            InventoryServiceNew.gI().subQuantityItemsBag(player, itemDBV, countDaBaoVe);
                        }
                        InventoryServiceNew.gI().subQuantityItemsBag(player, itemDNC, player.combineNew.countDaNangCap);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendMoney(player);
                        reOpenItemCombine(player);
                    }
                }
            }
            if(k==1){
                Service.getInstance().sendThongBao(player, "Số lần nâng cấp: "+(i+1));
                break;
            } else {
                Service.getInstance().sendThongBao(player, "Số lần nâng cấp: 10");
            }
        }
    }
    
    private void nangCapVatPham100(Player player) {
        int k= 0;
        for(int i=0;i<100;i++){

            if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
                if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() != 1) {
                    return;
                }
                if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() != 1) {
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() != 1) {
                    return;//admin
                }
                Item itemDo = null; //Đổi Cải Trang V2
                Item itemDNC = null;
                Item itemDBV = null;
                for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                    if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                        if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                            itemDBV = player.combineNew.itemsCombine.get(j);
                            continue;
                        }
                        if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                            itemDo = player.combineNew.itemsCombine.get(j);
                        } else {
                            itemDNC = player.combineNew.itemsCombine.get(j);
                        }
                    }
                }
                if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                    int countDaNangCap = player.combineNew.countDaNangCap;
                    int gold = player.combineNew.goldCombine;
                    short countDaBaoVe = player.combineNew.countDaBaoVe;
                    if (player.inventory.gold < gold) {
                        Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                        return;
                    }

                    if (itemDNC.quantity < countDaNangCap) return;
                    if (player.combineNew.itemsCombine.size() == 3) {
                        if (Objects.isNull(itemDBV)) return;
                        if (itemDBV.quantity < countDaBaoVe) return;
                    }

                    int level = 0;
                    Item.ItemOption optionLevel = null;
                    for (Item.ItemOption io : itemDo.itemOptions) {
                        if (io.optionTemplate.id == 72) {
                            level = io.param;
                            optionLevel = io;
                            break;
                        }
                    }
                    if (level < MAX_LEVEL_ITEM) {
                        player.inventory.gold -= gold;
                        Item.ItemOption option = null;
                        Item.ItemOption option2 = null;
                        for (Item.ItemOption io : itemDo.itemOptions) {
                            if (io.optionTemplate.id == 47
                                    || io.optionTemplate.id == 6
                                    || io.optionTemplate.id == 0
                                    || io.optionTemplate.id == 7
                                    || io.optionTemplate.id == 14
                                    || io.optionTemplate.id == 22
                                    || io.optionTemplate.id == 23
                                    || io.optionTemplate.id == 235
                                    || io.optionTemplate.id == 236) {
                                option = io;
                            } else if (io.optionTemplate.id == 27
                                    || io.optionTemplate.id == 28) {
                                option2 = io;
                            }
                        }
                        if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                            option.param += (option.param * 10 / 100);
                            if (option2 != null) {
                                option2.param += (option2.param * 10 / 100);
                            }
                            if (optionLevel == null) {
                                itemDo.itemOptions.add(new Item.ItemOption(72, 1));
                            } else {
                                optionLevel.param++;
                            }
                            sendEffectSuccessCombine(player);
                            k=1;
                        } else {
                            if ((level == 2 || level == 4 || level == 6) && (player.combineNew.itemsCombine.size() != 3)) {
                                option.param -= (option.param * 10 / 100);
                                if (option2 != null) {
                                    option2.param -= (option2.param * 10 / 100);
                                }
                                optionLevel.param--;
                            }
                            sendEffectFailCombine(player);
                        }
                        if (player.combineNew.itemsCombine.size() == 3) {
                            InventoryServiceNew.gI().subQuantityItemsBag(player, itemDBV, countDaBaoVe);
                        }
                        InventoryServiceNew.gI().subQuantityItemsBag(player, itemDNC, player.combineNew.countDaNangCap);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendMoney(player);
                        reOpenItemCombine(player);
                    }
                }
            }
            if(k==1){
                Service.getInstance().sendThongBao(player, "Số lần nâng cấp: "+(i+1));
                break;
            } else {
                Service.getInstance().sendThongBao(player, "Số lần nâng cấp: 100");
            }
        }
    }
    
    
    private void nangCapBongTai(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item bongTai = null;
            Item manhVo = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id == 454) {
                    bongTai = item;
                } else if (item.template.id == 933) {
                    manhVo = item;
                }
            }
            if (bongTai != null && manhVo != null && manhVo.quantity >= 99) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, manhVo, 99);
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                    bongTai.template = ItemService.gI().getTemplate(921);                    
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void moChiSoBongTai(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item bongtai = null;
            Item honbongtai = null;
            Item daxanhlam = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id == 921) {
                    bongtai = item;
                } else if (item.template.id == 934) {
                    honbongtai = item;
                } else if (item.template.id == 935) {
                    daxanhlam = item;
                }
            }
            if (bongtai != null && honbongtai != null && honbongtai.quantity >= 99) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, honbongtai, 99);
                InventoryServiceNew.gI().subQuantityItemsBag(player, daxanhlam, 1);
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                    bongtai.itemOptions.clear();
                    bongtai.itemOptions.add(new Item.ItemOption(72, 2));
                    int rdUp = Util.nextInt(0, 7);
                    if (rdUp == 0) {
                        bongtai.itemOptions.add(new Item.ItemOption(50, Util.nextInt(5, 25)));
                    } else if (rdUp == 1) {
                        bongtai.itemOptions.add(new Item.ItemOption(77, Util.nextInt(5, 30)));
                    } else if (rdUp == 2) {
                        bongtai.itemOptions.add(new Item.ItemOption(103, Util.nextInt(5, 30)));
                    } else if (rdUp == 3) {
                        bongtai.itemOptions.add(new Item.ItemOption(108, Util.nextInt(5, 25)));
                    } else if (rdUp == 4) {
                        bongtai.itemOptions.add(new Item.ItemOption(94, Util.nextInt(5, 15)));
                    } else if (rdUp == 5) {
                        bongtai.itemOptions.add(new Item.ItemOption(14, Util.nextInt(5, 15)));
                    } else if (rdUp == 6) {
                        bongtai.itemOptions.add(new Item.ItemOption(95, Util.nextInt(5, 25)));
                    } else if (rdUp == 7) {
                        bongtai.itemOptions.add(new Item.ItemOption(96, Util.nextInt(5, 25)));
                    }
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }
    
    private void doiskhthanhdns(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).count() != 2) {
            Service.gI().sendThongBao(player, "Thiếu đồ kích hoạt");
            return;
        }
             List<Item> itemSKH = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).collect(Collectors.toList());
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= COST_DOI_KICH_HOAT ) {
                    player.inventory.gold -= COST_DOI_KICH_HOAT ;
                    int tiLe = 100;
                    sendEffectSuccessCombine(player);
                        Item item = ItemService.gI().createNewItem((short) 1237);
                        InventoryServiceNew.gI().addItemBag(player, item);
                    } 
                else {
                    sendEffectFailCombine(player);
                }
                itemSKH.forEach(i -> InventoryServiceNew.gI().subQuantityItemsBag(player, i, 1));
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    player.combineNew.itemsCombine.clear();
                    reOpenItemCombine(player);
                
            
        }
    }
        private void Tienhoacaitrangbaby(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
                        Item CTGoHanSSJ = null;
                        Item Dangusac = null;
                        for (Item item_ : player.combineNew.itemsCombine) {
                            if (item_.template.id == 674) {
                                Dangusac = item_;
                            } else if (item_.isCTGoHanSSJ()) {
                                CTGoHanSSJ = item_;
                            }
                        }
            if (isCTGoHanSSJ(CTGoHanSSJ, Dangusac)) {
                int countDangusac = 500;
                int gold = 2000000000;
                int ruby = 100000;
                
                if (player.inventory.gold < gold) {
                    Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                }
                if (player.inventory.ruby < ruby) {
                    Service.getInstance().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                    return;
                }
                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : CTGoHanSSJ.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (level == 10 && Dangusac.quantity >= 500) {
                    player.inventory.gold -= gold;
                    player.inventory.ruby -= ruby;
                    
                    InventoryServiceNew.gI().subQuantityItemsBag(player, Dangusac, 500);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CTGoHanSSJ, 1);
                    
                        Item item = ItemService.gI().createNewItem((short) 1216);
                        item.itemOptions.add(new Item.ItemOption(50, 70));
                        item.itemOptions.add(new Item.ItemOption(77, 70));
                        item.itemOptions.add(new Item.ItemOption(103, 70));
                        item.itemOptions.add(new Item.ItemOption(14, 30));
                        item.itemOptions.add(new Item.ItemOption(5, 50));
                        item.itemOptions.add(new Item.ItemOption(106, 1));
                        item.itemOptions.add(new Item.ItemOption(206, 2));         
                        InventoryServiceNew.gI().addItemBag(player, item);                                               
                        sendEffectSuccessCombine(player);
                  
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }else {
                Service.getInstance().sendThongBao(player, "Thiếu Đá Ngũ Sắc");
                    return;
                }
            }
        }
    }
        
        private void Tienhoacaitrangblackgoku(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
                        Item CaiTrang = null;
                        Item Dangusac = null;
                        for (Item item_ : player.combineNew.itemsCombine) {
                            if (item_.template.id == 674) {
                                Dangusac = item_;
                            } else if (item_.isCTBaby()) {
                                CaiTrang = item_;
                            }
                        }
            if (isCTBaby(CaiTrang, Dangusac)) {
                int countDangusac = 1000;
                int gold = 2000000000;
                int ruby = 200000;
                
                if (player.inventory.gold < gold) {
                    Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                }
                if (player.inventory.ruby < ruby) {
                    Service.getInstance().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                    return;
                }
                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : CaiTrang.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (level == 10 && Dangusac.quantity >= 1000) {
                    player.inventory.gold -= gold;
                    player.inventory.ruby -= ruby;
                    
                    InventoryServiceNew.gI().subQuantityItemsBag(player, Dangusac, 1000);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CaiTrang, 1);
                    
                        Item item = ItemService.gI().createNewItem((short) 1280);
                        item.itemOptions.add(new Item.ItemOption(50, Util.nextInt(130, 180)));
                        item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(130, 180)));
                        item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(130, 180)));
                        item.itemOptions.add(new Item.ItemOption(14, 40));
                        item.itemOptions.add(new Item.ItemOption(5, Util.nextInt(90, 120)));
                        item.itemOptions.add(new Item.ItemOption(106, 1));
                        item.itemOptions.add(new Item.ItemOption(206, 3));          
                        InventoryServiceNew.gI().addItemBag(player, item);                                               
                        sendEffectSuccessCombine(player);
                  
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }else {
                Service.getInstance().sendThongBao(player, "Thiếu Đá Ngũ Sắc");
                    return;
                }
            }
        }
    }
        private void Tienhoacaitrangbill(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
                        Item CTGoHanSSJ = null;
                        Item Dangusac = null;
                        for (Item item_ : player.combineNew.itemsCombine) {
                            if (item_.template.id == 674) {
                                Dangusac = item_;
                            } else if (item_.isCTBlackGoku()) {
                                CTGoHanSSJ = item_;
                            }
                        }
            if (isCTBlackGoku(CTGoHanSSJ, Dangusac)) {
                int countDangusac = 1500;
                int gold = 2000000000;
                int ruby = 300000;
                
                if (player.inventory.gold < gold) {
                    Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                }
                if (player.inventory.ruby < ruby) {
                    Service.getInstance().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                    return;
                }
                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : CTGoHanSSJ.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (level == 10 && Dangusac.quantity >= 1500) {
                    player.inventory.gold -= gold;
                    player.inventory.ruby -= ruby;
                    
                    InventoryServiceNew.gI().subQuantityItemsBag(player, Dangusac, 1500);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CTGoHanSSJ, 1);
                    
                        Item item = ItemService.gI().createNewItem((short) 2008);
                        item.itemOptions.add(new Item.ItemOption(50, Util.nextInt(200, 250)));
                        item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(200, 250)));
                        item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(200, 250)));
                        item.itemOptions.add(new Item.ItemOption(14, 50));
                        item.itemOptions.add(new Item.ItemOption(5, Util.nextInt(140, 170)));
                        item.itemOptions.add(new Item.ItemOption(106, 1));
                        item.itemOptions.add(new Item.ItemOption(206, 4));         
                        InventoryServiceNew.gI().addItemBag(player, item);                                               
                        sendEffectSuccessCombine(player);
                  
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }else {
                Service.getInstance().sendThongBao(player, "Thiếu Đá Ngũ Sắc");
                    return;
                }
            }
        }
    }
        private void Tienhoacaitranggold(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
                        Item CTGoHanSSJ = null;
                        Item Dangusac = null;
                        for (Item item_ : player.combineNew.itemsCombine) {
                            if (item_.template.id == 674) {
                                Dangusac = item_;
                            } else if (item_.isCTBill()) {
                                CTGoHanSSJ = item_;
                            }
                        }
            if (isCTBill(CTGoHanSSJ, Dangusac)) {
                int countDangusac = 2000;
                int gold = 2000000000;
                int ruby = 400000;
                
                if (player.inventory.gold < gold) {
                    Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                }
                if (player.inventory.ruby < ruby) {
                    Service.getInstance().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                    return;
                }
                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : CTGoHanSSJ.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (level == 10 && Dangusac.quantity >= 2000) {
                    player.inventory.gold -= gold;
                    player.inventory.ruby -= ruby;
                    
                    InventoryServiceNew.gI().subQuantityItemsBag(player, Dangusac, 2000);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, CTGoHanSSJ, 1);
                    
                        Item item = ItemService.gI().createNewItem((short) 1208);
                        item.itemOptions.add(new Item.ItemOption(50, Util.nextInt(300, 350)));
                        item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(300, 350)));
                        item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(300, 350)));
                        item.itemOptions.add(new Item.ItemOption(14, 60));
                        item.itemOptions.add(new Item.ItemOption(5, Util.nextInt(200, 230)));
                        item.itemOptions.add(new Item.ItemOption(106, 1));
                        item.itemOptions.add(new Item.ItemOption(206, 5));         
                        InventoryServiceNew.gI().addItemBag(player, item);                                               
                        sendEffectSuccessCombine(player);
                  
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }else {
                Service.getInstance().sendThongBao(player, "Thiếu Đá Ngũ Sắc");
                    return;
                }
            }
        }
    }
        
    private void NangCapCaiTrangSSJ(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item caitrang = null;
            Item dangusac = null;
 
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id == 1192
                          ||item.template.id == 1216
                          ||item.template.id == 1280
                          ||item.template.id == 1208
                          ||item.template.id == 2008) {
                    caitrang = item;
                } else if (item.template.id == 674) {
                    dangusac = item;
                }
            }
            int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
            int hp = 0;
                Item.ItemOption optionhp = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 77) {
                        hp = io.param;
                        optionhp = io;
                        break;
                    }
                }
            int ki = 0;
                Item.ItemOption optionki = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 103) {
                        ki = io.param;
                        optionki = io;
                        break;
                    }
                }
            int sd = 0;
                Item.ItemOption optionsd = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 50) {
                        sd = io.param;
                        optionsd = io;
                        break;
                    }
                }
             int crit = 0;
                Item.ItemOption optioncrit = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 14) {
                        crit = io.param;
                        optioncrit = io;
                        break;
                    }
                }
            int sdcrit = 0;
                Item.ItemOption optionsdcrit = null;
                for (Item.ItemOption io : caitrang.itemOptions) {
                    if (io.optionTemplate.id == 5) {
                        sdcrit = io.param;
                        optionsdcrit = io;
                        break;
                    }
                }
            if (caitrang.template.id == 1192 && dangusac != null && level < 10 && dangusac.quantity >= getDaNangCap(level) ) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, getDaNangCap(level));
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
 
                    if (optionLevel == null) {
                       caitrang.itemOptions.add(new Item.ItemOption(72,1));
                   } else  {
                       optionLevel.param++;
                   }
                        optionhp.param += Util.nextInt(10, 11);
                        optionki.param += Util.nextInt(10, 11);
                        optionsd.param += Util.nextInt(10, 11);
                        optioncrit.param += 2;
                        optionsdcrit.param += Util.nextInt(6, 7);                  
                    sendEffectSuccessCombine(player);
                }                   
                else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
                 
            }
            else if (caitrang.template.id == 1216 && dangusac != null && level < 10 && dangusac.quantity >= getDaNangCap(level) ) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, getDaNangCap(level));
                if (Util.isTrue(player.combineNew.ratioCombine2, 100)) {
 
                    if (optionLevel == null) {
                       caitrang.itemOptions.add(new Item.ItemOption(72,1));
                   } else  {
                       optionLevel.param++;
                   }
                        optionhp.param += Util.nextInt(12, 14);
                        optionki.param += Util.nextInt(12, 14);
                        optionsd.param += Util.nextInt(12, 14);
                        optioncrit.param += 3;
                        optionsdcrit.param += Util.nextInt(8, 10);                  
                    sendEffectSuccessCombine(player);
                }                   
                else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
                 
            }
            else if (caitrang.template.id == 1280 && dangusac != null && level < 10 && dangusac.quantity >= getDaNangCap(level) ) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, getDaNangCap(level));
                if (Util.isTrue(player.combineNew.ratioCombine3, 100)) {
 
                    if (optionLevel == null) {
                       caitrang.itemOptions.add(new Item.ItemOption(72,1));
                   } else  {
                       optionLevel.param++;
                   }
                        optionhp.param += Util.nextInt(15, 18);
                        optionki.param += Util.nextInt(15, 18);
                        optionsd.param += Util.nextInt(15, 18);
                        optioncrit.param += 4;
                        optionsdcrit.param += Util.nextInt(11, 14);                   
                    sendEffectSuccessCombine(player);
                }                   
                else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
                 
            }
            else if (caitrang.template.id == 2008 && dangusac != null && level < 10 && dangusac.quantity >= getDaNangCap(level) ) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, getDaNangCap(level));
                if (Util.isTrue(player.combineNew.ratioCombine4, 100)) {
 
                    if (optionLevel == null) {
                       caitrang.itemOptions.add(new Item.ItemOption(72,1));
                   } else  {
                       optionLevel.param++;
                   }
                        optionhp.param += Util.nextInt(19, 23);
                        optionki.param += Util.nextInt(19, 23);
                        optionsd.param += Util.nextInt(19, 23);
                        optioncrit.param += 5;
                        optionsdcrit.param += Util.nextInt(15, 19);                  
                    sendEffectSuccessCombine(player);
                }                   
                else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
                 
            }
            else if (caitrang.template.id == 1208 && dangusac != null && level < 10 && dangusac.quantity >= getDaNangCap(level) ) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, getDaNangCap(level));
                if (Util.isTrue(player.combineNew.ratioCombine5, 100)) {
 
                    if (optionLevel == null) {
                       caitrang.itemOptions.add(new Item.ItemOption(72,1));
                   } else  {
                       optionLevel.param++;
                   }
                        optionhp.param += Util.nextInt(25, 30);
                        optionki.param += Util.nextInt(25, 30);
                        optionsd.param += Util.nextInt(25, 30);
                        optioncrit.param += 5;
                        optionsdcrit.param += Util.nextInt(20, 25);                   
                    sendEffectSuccessCombine(player);
                }                   
                else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
                 
            }
            else{
                Service.gI().sendThongBao(player, "Không đủ đá ngũ sắc");
                return;
            }
        
    }}

    //--------------------------------------------------------------------------

    /**r
     * Hiệu ứng mở item
     *
     * @param player
     */
    public void sendEffectOpenItem(Player player, short icon1, short icon2) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_ITEM);
            msg.writer().writeShort(icon1);
            msg.writer().writeShort(icon2);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiệu ứng đập đồ thành công
     *
     * @param player
     */
    private void sendEffectSuccessCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_SUCCESS);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiệu ứng đập đồ thất bại
     *
     * @param player
     */
    private void sendEffectFailCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_FAIL);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Gửi lại danh sách đồ trong tab combine
     *
     * @param player
     */
    private void reOpenItemCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(REOPEN_TAB_COMBINE);
            msg.writer().writeByte(player.combineNew.itemsCombine.size());
            for (Item it : player.combineNew.itemsCombine) {
                for (int j = 0; j < player.inventory.itemsBag.size(); j++) {
                    if (it == player.inventory.itemsBag.get(j)) {
                        msg.writer().writeByte(j);
                    }
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiệu ứng ghép ngọc rồng
     *
     * @param player
     * @param icon
     */
    private void sendEffectCombineDB(Player player, short icon) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_DRAGON_BALL);
            msg.writer().writeShort(icon);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    //--------------------------------------------------------------------------Ratio, cost combine
    private int getGoldPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 50000000;
            case 1:
                return 60000000;
            case 2:
                return 70000000;
            case 3:
                return 80000000;
            case 4:
                return 90000000;
            case 5:
                return 100000000;
            case 6:
                return 110000000;
            case 7:
                return 120000000;
            case 8:
                return 130000000;
            case 9:
                return 140000000;
            case 10:
                return 150000000;
            case 11:
                return 160000000;
            case 12:
                return 170000000;
            case 13:
                return 180000000;
            case 14:
                return 190000000;
            case 15:
                return 200000000;
        }
        return 0;
    }
    
        private int getGoldPhaLeHoaTC1(int star) {
        switch (star) {
            case 0:
                return 100000000;
            case 1:
                return 120000000;
            case 2:
                return 140000000;
            case 3:
                return 160000000;
            case 4:
                return 180000000;
            case 5:
                return 200000000;
            case 6:
                return 220000000;
            case 7:
                return 240000000;
            case 8:
                return 260000000;
            case 9:
                return 280000000;
            case 10:
                return 300000000;
            case 11:
                return 320000000;
            case 12:
                return 340000000;
            case 13:
                return 360000000;
            case 14:
                return 380000000;
            case 15:
                return 400000000;
            case 16:
                return 420000000;
        }
        return 0;
    }
private int getGoldNCCaiTrang(int level) {
        switch (level) {
            case 0:
                return 50000000;
            case 1:
                return 60000000;
            case 2:
                return 70000000;
            case 3:
                return 80000000;
            case 4:
                return 90000000;
            case 5:
                return 100000000;
            case 6:
                return 110000000;
            case 7:
                return 120000000;
            case 8:
                return 130000000;
            case 9:
                return 140000000;
            case 10:
                return 150000000;
            case 11:
                return 160000000;
            case 12:
                return 170000000;
            case 13:
                return 180000000;
            case 14:
                return 190000000;
            case 15:
                return 200000000;
        }
        return 0;
    }
    private float getRatioPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 70f;
            case 1:
                return 60f;
            case 2:
                return 60f;
            case 3:
                return 50f;
            case 4:
                return 50f;
            case 5:
                return 40f;
            case 6:
                return 35f;
            case 7:
                return 10f;
            case 8:
                return 50f;
            case 9:
                return 0.4f;    
            case 10:
                return 0.2f;
            case 11:
                return 0.1f;
             case 12:
                return 0.05f;
             case 13:
                return 0.01f;
             case 14:
                return 0.005f;
             case 15:
                return 0.001f;
        }
        
        
        return 0;
    }
    
    private float getRatioPhaLeHoaTC1(int star) {
        switch (star) {
            case 0:
                return 35f;
            case 1:
                return 30f;
            case 2:
                return 30f;
            case 3:
                return 25f;
            case 4:
                return 25f;
            case 5:
                return 20f;
            case 6:
                return 10f;
            case 7:
                return 5f;
            case 8:
                return 5f;
            case 9:
                return 0.2f;    
            case 10:
                return 0.1f;
            case 11:
                return 0.05f;
             case 12:
                return 0.025f;
             case 13:
                return 0.005f;
             case 14:
                return 0.0025f;
             case 15:
                return 0.0005f;
            case 16:
                return 0.0001f;
        }
        
        
        return 0;
    }

    private int getGemPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 10;
            case 1:
                return 20;
            case 2:
                return 20;
            case 3:
                return 20;
            case 4:
                return 30;
            case 5:
                return 30;
            case 6:
                return 35;
            case 7:
                return 40;
            case 8:
                return 50;    
            case 9:
                return 70;    
            case 10:
                return 80;
            case 11:
                return 90;
             case 12:
                return 100; 
            case 13:
                return 120;    
            case 14:
                return 140;    
            case 15:
                return 150;
        }
        return 0;
    }
    
    private int getGemPhaLeHoaTC1(int star) {
        switch (star) {
            case 0:
                return 20;
            case 1:
                return 30;
            case 2:
                return 40;
            case 3:
                return 50;
            case 4:
                return 60;
            case 5:
                return 70;
            case 6:
                return 80;
            case 7:
                return 90;
            case 8:
                return 100;    
            case 9:
                return 110;    
            case 10:
                return 120;
            case 11:
                return 130;
             case 12:
                return 150; 
            case 13:
                return 180;    
            case 14:
                return 220;    
            case 15:
                return 270;
            case 16:
                return 350;
        }
        return 0;
    }

    private int getGemEpSao(int star) {
        switch (star) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 5;
            case 3:
                return 10;
            case 4:
                return 25;
            case 5:
                return 50;
            case 6:
                return 100;
            case 7:
                return 110;
            case 8:
                return 120;
            case 9:
                return 130;
            case 10:
                return 140; 
            case 11:
                return 150; 
            case 12:
                return 160;
            case 13:
                return 140; 
            case 14:
                return 150; 
            case 15:
                return 160;
        }
        return 0;
    }

    private double getTileNangCapDo(int level) {
        switch (level) {
            case 0:
                return 80;
            case 1:
                return 50;
            case 2:
                return 50;
            case 3:
                return 50;
            case 4:
                return 30;
            case 5:
                return 20;
            case 6:
                return 10;
            case 7: // 7 sao
                return 3;
            case 8:
                return 5;
            case 9:
                return 1;
            case 10: // 7 sao
                return 0.3;
            case 11: // 7 sao
                return 0.3; 
            case 12: // 7 sao
                return 0.3;
            case 13: // 7 sao
                return 0.3;
            case 14: // 7 sao
                return 0.3; 
            case 15: // 7 sao
                return 0.3;
        }
        return 0;
    }

    private int getCountDaNangCapDo(int level) {
        switch (level) {
            case 0:
                return 3;
            case 1:
                return 7;
            case 2:
                return 11;
            case 3:
                return 17;
            case 4:
                return 23;
            case 5:
                return 35;
            case 6:
                return 50;
            case 7:
                return 60;
            case 8:
                return 70;    
            case 9:
                return 70;
            case 10:
                return 70;
            case 11:
                return 80;
            case 12:
                return 90;    
            case 13:
                return 90;
            case 14:
                return 90; 
            case 15:
                return 99;
                
        }
        return 0;
    }

    private int getCountDaBaoVe(int level) {
        return level + 1;
    }

    private int getGoldNangCapDo(int level) {
        switch (level) {
            case 0:
                return 10000000;
            case 1:
                return 17000000;
            case 2:
                return 30000000;
            case 3:
                return 40000000;
            case 4:
                return 70000000;
            case 5:
                return 80000000;
            case 6:
                return 100000000;
            case 7:
                return 250000000;
            case 8:
                return 10000000;
            case 9:
                return 17000000;
            case 10:
                return 30000000;
            case 11:
                return 40000000;
            case 12:
                return 70000000;
            case 13:
                return 80000000;
            case 14:
                return 100000000;
            case 15:
                return 250000000;
//                
        }
        return 0;
    }
        private float getRatioNCCaiTrang(int level) { //Tỉ lệ nâng cấp cải trang
        switch (level) {
            case 0:
                return 90f;
            case 1:
                return 60f;
            case 2:
                return 50f;
            case 3:
                return 50f;
            case 4:
                return 50f;
            case 5:
                return 40f;
            case 6:
                return 40f;
            case 7:
                return 30f;
            case 8:
                return 30f;    
            case 9:
                return 20f;    
            case 10:
                return 20f;
            case 11:
                return 0.6f;
             case 12:
                return 0.4f;
             case 13:
                return 0.3f;
             case 14:
                return 0.2f;
             case 15:
                return 0.1f;
        }               
        return 0;
    }
        
    private float getRatioNCCaiTrang2(int level) { //Tỉ lệ nâng cấp cải trang
        switch (level) {
            case 0:
                return 70f;
            case 1:
                return 40f;
            case 2:
                return 30f;
            case 3:
                return 30f;
            case 4:
                return 30f;
            case 5:
                return 15f;
            case 6:
                return 15f;
            case 7:
                return 10f;
            case 8:
                return 10f;    
            case 9:
                return 5f;    
            case 10:
                return 3f;
            case 11:
                return 0.6f;
             case 12:
                return 0.4f;
             case 13:
                return 0.3f;
             case 14:
                return 0.2f;
             case 15:
                return 0.1f;
        }               
        return 0;
    }
    
        private float getRatioNCCaiTrang3(int level) { //Tỉ lệ nâng cấp cải trang
        switch (level) {
            case 0:
                return 60f;
            case 1:
                return 30f;
            case 2:
                return 20f;
            case 3:
                return 20f;
            case 4:
                return 20f;
            case 5:
                return 10f;
            case 6:
                return 10f;
            case 7:
                return 5f;
            case 8:
                return 5f;    
            case 9:
                return 2.5f;    
            case 10:
                return 1.5f;
            case 11:
                return 0.6f;
             case 12:
                return 0.4f;
             case 13:
                return 0.3f;
             case 14:
                return 0.2f;
             case 15:
                return 0.1f;
        }               
        return 0;
    }
        
        private float getRatioNCCaiTrang4(int level) { //Tỉ lệ nâng cấp cải trang
        switch (level) {
           case 0:
                return 50f;
            case 1:
                return 20f;
            case 2:
                return 10f;
            case 3:
                return 10f;
            case 4:
                return 10f;
            case 5:
                return 7f;
            case 6:
                return 7f;
            case 7:
                return 3f;
            case 8:
                return 3f;    
            case 9:
                return 2f;    
            case 10:
                return 1f;
            case 11:
                return 0.6f;
             case 12:
                return 0.4f;
             case 13:
                return 0.3f;
             case 14:
                return 0.2f;
             case 15:
                return 0.1f;
        }               
        return 0;
    }
        
        private float getRatioNCCaiTrang5(int level) { //Tỉ lệ nâng cấp cải trang
        switch (level) {
            case 0:
                return 40f;
            case 1:
                return 15f;
            case 2:
                return 10f;
            case 3:
                return 10f;
            case 4:
                return 10f;
            case 5:
                return 5f;
            case 6:
                return 5f;
            case 7:
                return 2f;
            case 8:
                return 2f;    
            case 9:
                return 1f;    
            case 10:
                return 0.5f;
            case 11:
                return 0.6f;
             case 12:
                return 0.4f;
             case 13:
                return 0.3f;
             case 14:
                return 0.2f;
             case 15:
                return 0.1f;
        }               
        return 0;
    }
        private int getDaNangCap(int level) {
        switch (level) {
            case 0:
                return 50;
            case 1:
                return 100;
            case 2:
                return 110;
            case 3:
                return 120 ;
            case 4:
                return 130;
            case 5:
                return 140;
            case 6:
                return 150;
            case 7:
                return 160;
            case 8:
                return 170;
            case 9:
                return 180;
            case 10:
                return 190;
            case 11:
                return 200;
            case 12:
                return 210;
        }
        return 0;
    }
    private int getGemNangCaiTrang(int level) {
        switch (level) {
            case 0:
                return 10000;
            case 1:
                return 20000;
            case 2:
                return 30000;
            case 3:
                return 40000;
            case 4:
                return 50000;
            case 5:
                return 60000;
            case 6:
                return 70000;
            case 7:
                return 80000;
            case 8:
                return 90000;    
            case 9:
                return 100000;    
            case 10:
                return 110000;
            case 11:
                return 120000;
             case 12:
                return 130000; 
            case 13:
                return 140000;    
            case 14:
                return 150000;    
            case 15:
                return 160000;
        }
        return 0;
    }

    //--------------------------------------------------------------------------check
    private boolean isCoupleItemNangCap(Item item1, Item item2) {
        Item trangBi = null;
        Item daNangCap = null;
        if (item1 != null && item1.isNotNullItem()) {
            if (item1.template.type < 5) {
                trangBi = item1;
            } else if (item1.template.type == 14) {
                daNangCap = item1;
            }
        }
        if (item2 != null && item2.isNotNullItem()) {
            if (item2.template.type < 5) {
                trangBi = item2;
            } else if (item2.template.type == 14) {
                daNangCap = item2;
            }
        }
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.type == 0 && daNangCap.template.id == 223) {
                return true;
            } else if (trangBi.template.type == 1 && daNangCap.template.id == 222) {
                return true;
            } else if (trangBi.template.type == 2 && daNangCap.template.id == 224) {
                return true;
            } else if (trangBi.template.type == 3 && daNangCap.template.id == 221) {
                return true;
            } else if (trangBi.template.type == 4 && daNangCap.template.id == 220) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isCoupleItemNangCapCheck(Item trangBi, Item daNangCap) {
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.type == 0 && daNangCap.template.id == 223) {
                return true;
            } else if (trangBi.template.type == 1 && daNangCap.template.id == 222) {
                return true;
            } else if (trangBi.template.type == 2 && daNangCap.template.id == 224) {
                return true;
            } else if (trangBi.template.type == 3 && daNangCap.template.id == 221) {
                return true;
            } else if (trangBi.template.type == 4 && daNangCap.template.id == 220) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isDaPhaLe(Item item) {
        return item != null && (item.template.type == 30 || (item.template.id >= 14 && item.template.id <= 20) || (item.template.id >= 1185 && item.template.id <= 1191));
    }
    
    private boolean isDaPhaLeTC1(Item item) {
        return item != null && (item.template.type == 30 || (item.template.id >= 925 && item.template.id <= 931));
    }
    
    private boolean isCTGoHanSSJ(Item trangBi, Item daNangCap) {
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.id == 1192 && daNangCap.template.id == 674) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    private boolean isCTBaby(Item trangBi, Item daNangCap) {
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.id == 1216 && daNangCap.template.id == 674) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    private boolean isCTBlackGoku(Item trangBi, Item daNangCap) {
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.id == 1280 && daNangCap.template.id == 674) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    private boolean isCTBill(Item trangBi, Item daNangCap) {
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.id == 2008 && daNangCap.template.id == 674) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
        
        

    private boolean isTrangBiPhaLeHoa(Item item) {
        if (item != null && item.isNotNullItem()) {
            if (item.template.type < 5 || item.template.type == 32 || item.template.type == 5 || item.template.id == 1179 || item.template.id == 1175 || item.template.id == 1170 || item.template.id == 1174){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    

    
    private boolean isTrangBiAn(Item item) {
        if (item != null && item.isNotNullItem()) {
            if (item.template.id >= 1048 && item.template.id <= 1062 ){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private int getParamDaPhaLe(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).param;
        }
        switch (daPhaLe.template.id) {
            case 20:
                return 5; // +5%hp
            case 19:
                return 5; // +5%ki
            case 18:
                return 5; // +5%hp/30s
            case 17:
                return 5; // +5%ki/30s
            case 16:
                return 3; // +3%sđ
            case 15:
                return 2; // +2%giáp
            case 14:
                return 2; // +2%né đòn
            case 931:
                return 10;
            case 930:
                return 10;
            case 929:
                return 10;
            case 928:
                return 10;
            case 927:
                return 6;
            case 926:
                return 4;
            case 925:
                return 4;            
            default:
                return -1;
        }
    }

    private int getOptionDaPhaLe(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).optionTemplate.id;
        }
        switch (daPhaLe.template.id) {
            case 20:
                return 228;   //hp
            case 19:
                return 229;  //ki
            case 18:
                return 230;  //hp30s
            case 17:
                return 231; // ki30s
            case 16:
                return 232; //sd
            case 15:
                return 233; //giap
            case 14:
                return 234; //ne don
            case 931:
                return 228; 
            case 930:
                return 229;
            case 929:
                return 230;
            case 928:
                return 231;
            case 927:
                return 232; // sức đánh
            case 926:
                return 233;
            case 925:
                return 234;
            default:
                return -1;
        }
    }
    
    private int getParamDaPhaLeTC1(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).param;
        }
        switch (daPhaLe.template.id) {

            case 931:
                return 10;
            case 930:
                return 10;
            case 929:
                return 10;
            case 928:
                return 10;
            case 927:
                return 6;
            case 926:
                return 4;
            case 925:
                return 4;            
            default:
                return -1;
        }
    }
    
    private int getOptionDaPhaLeTC1(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).optionTemplate.id;
        }
        switch (daPhaLe.template.id) {
            case 931:
                return 228;
            case 930:
                return 229;
            case 929:
                return 230;
            case 928:
                return 231;
            case 927:
                return 232; // sức đánh
            case 926:
                return 233;
            case 925:
                return 234;
            default:
                return -1;
        }
    }

    /**
     * Trả về id item c0
     *
     * @param gender
     * @param type
     * @return
     */
    private int getTempIdItemC0(int gender, int type) {
        if (type == 4) {
            return 12;
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return 0;
                    case 1:
                        return 6;
                    case 2:
                        return 21;
                    case 3:
                        return 27;
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return 1;
                    case 1:
                        return 7;
                    case 2:
                        return 22;
                    case 3:
                        return 28;
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return 2;
                    case 1:
                        return 8;
                    case 2:
                        return 23;
                    case 3:
                        return 29;
                }
                break;
        }
        return -1;
    }
    private int getTempIdItemC1(int gender, int type) {
        if (type == 4) {
            return 561;
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return 555;
                    case 1:
                        return 556;
                    case 2:
                        return 562;
                    case 3:
                        return 563;
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return 557;
                    case 1:
                        return 558;
                    case 2:
                        return 564;
                    case 3:
                        return 565;
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return 559;
                    case 1:
                        return 560;
                    case 2:
                        return 566;
                    case 3:
                        return 567;
                }
                break;
        }
        return -1;
    }
    private String getNameItemC1(int gender, int type) {
        if (type == 4) {
            return "Nhẫn Thần Linh";
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return "Áo Thần Linh";
                    case 1:
                        return "Quần Thần Linh";
                    case 2:
                        return "Găng Thần Linh";
                    case 3:
                        return "Giầy Thần Linh";
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return "Áo Thần Namek";
                    case 1:
                        return "Quần Thần Namek";
                    case 2:
                        return "Găng Thần Namek";
                    case 3:
                        return "Giầy Thần Namek";
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return "Áo Thần Xayda";
                    case 1:
                        return "Quần Thần Xayda";
                    case 2:
                        return "Găng Thần Xayda";
                    case 3:
                        return "Giầy Thần Xayda";
                }
                break;
        }
        return "";
    }

    //Trả về tên đồ c0
    private String getNameItemC0(int gender, int type) {
        if (type == 4) {
            return "Rada cấp 1";
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return "Áo vải 3 lỗ";
                    case 1:
                        return "Quần vải đen";
                    case 2:
                        return "Găng thun đen";
                    case 3:
                        return "Giầy nhựa";
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return "Áo sợi len";
                    case 1:
                        return "Quần sợi len";
                    case 2:
                        return "Găng sợi len";
                    case 3:
                        return "Giầy sợi len";
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return "Áo vải thô";
                    case 1:
                        return "Quần vải thô";
                    case 2:
                        return "Găng vải thô";
                    case 3:
                        return "Giầy vải thô";
                }
                break;
        }
        return "";
    }

    //--------------------------------------------------------------------------Text tab combine
    private String getTextTopTabCombine(int type) {
        switch (type) {
            case EP_SAO_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở lên mạnh mẽ";
            case EP_SAO_TRANG_BI_THANG_CAP:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở lên mạnh mẽ";
            case PHA_LE_HOA_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở thành trang bị pha lê";
            case PHA_LE_HOA_TRANG_BI_THANG_CAP:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở thành trang bị pha lê";
            case AN_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở thành trang bị Ấn";
            case NHAP_NGOC_RONG:
                return "Ta sẽ phù phép\ncho 10 viên Ngọc Rồng\nthành 1 viên Ngọc Rồng cấp cao";
            case NANG_CAP_VAT_PHAM:
                return "Ta sẽ phù phép cho trang bị của ngươi trở lên mạnh mẽ";
            case PHAN_RA_DO_THAN_LINH:
                return "Ta sẽ phân rã \n  trang bị của người thành Mây Ngũ Sắc!";
            case NANG_CAP_DO_TS:
                return "Ta sẽ nâng cấp \n  trang bị của người thành\n đồ thiên sứ!";
            case NANG_CAP_SKH_VIP:
                return "Thiên sứ nhờ ta nâng cấp \n  trang bị của người thành\n SKH VIP!";               
            case NANG_CAP_BONG_TAI:
                return "Ta sẽ phù phép\ncho bông tai Porata của ngươi\nthành cấp 2";
            case MO_CHI_SO_BONG_TAI:
                return "Ta sẽ phù phép\ncho bông tai Porata cấp 2 của ngươi\ncó 1 chỉ số ngẫu nhiên";
            case PS_HOA_TRANG_BI:
                return "Pháp sư hóa trang bị";
            case TAY_PS_HOA_TRANG_BI:
                return "Tẩy pháp sư";
            case NANG_CAP_CAI_TRANG_SSJ:
                return "Ta sẽ phù phép\ncho Cải Trang của ngươi\nthêm sức mạnh";
            case TIEN_HOA_CAI_TRANG_BABY_VEGETA:
                return "Ta sẽ phù phép\nTiến Hóa cải trang của ngươi mạnh hơn";
                
            case CHE_TAO_TRANG_BI_TS:
                return "Chế tạo\ntrang bị thiên sứ";
            case DOI_SKH_THANH_DNS:
                return "Ta sẽ phân rã \n  trang bị skh của ngươi thành Mây Ngũ Sắc!";
            case DAP_SET_KICH_HOAT:
                return "Đưa ta 100 Đá Ngũ Sắc và 1 Trang Bị Thần Linh \n Ta sẽ phù phép thành Set Kích Hoạt!";
            case DAP_SET_KICH_HOAT_HD:
                return "Đưa ta 150 Đá Ngũ Sắc và 1 Trang Bị Hủy Diệt \n Ta sẽ phù phép thành Set Kích Hoạt!";
            case DAP_SET_KICH_HOAT_TS:
                return "Đưa ta 200 Đá Ngũ Sắc và 1 Trang Bị Thiên Sứ \n Ta sẽ phù phép thành Set Kích Hoạt!";
            case TAY_SKH_TRANG_BI:
                return "Đưa ta 1 Trang Bị SKH và 1 Bùa Tẩy Pháp Sư \n Ta sẽ giúp ngươi tẩy Set Kích Hoạt!";
            default:
                return "";
        }
    }

    private String getTextInfoTabCombine(int type) {
        switch (type) {
            case EP_SAO_TRANG_BI:
                return "Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa) có ô đặt sao pha lê\nChọn loại sao pha lê\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case EP_SAO_TRANG_BI_THANG_CAP:
                return "Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa) có ô đặt sao pha lê\nChọn loại sao pha lê\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case PHA_LE_HOA_TRANG_BI:
                return "Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nSau đó chọn 'Nâng cấp'";
            case PHA_LE_HOA_TRANG_BI_THANG_CAP:
                return "Chọn trang bị đã Thăng Cấp\nSau đó chọn 'Nâng cấp'";
            case NHAP_NGOC_RONG:
                return "Vào hành trang\nChọn 10 viên ngọc cùng sao\nSau đó chọn 'Làm phép'";
            case NANG_CAP_VAT_PHAM:
                return "vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nChọn loại đá để nâng cấp\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case PHAN_RA_DO_THAN_LINH:
                return "Vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc nhẫn thần linh)\n"
                        + "Sau đó chọn 'Phân Rã'";
            case NANG_CAP_DO_TS:
                return "vào hành trang\nChọn 2 trang bị hủy diệt bất kì\nkèm 1 món đồ thần linh\n và 999 mảnh thiên sứ\n "                         
                        + "Sau đó chọn 'Nâng Cấp'";
            case NANG_CAP_SKH_VIP:
                return "vào hành trang\nChọn 1 trang bị thiên sứ bất kì\nChọn tiếp ngẫu nhiên 2 món SKH thường \n " +
                        " đồ SKH VIP sẽ cùng loại \n với đồ thiên sứ!"
                        + "Chỉ cần chọn 'Nâng Cấp'";
           
            case PS_HOA_TRANG_BI:
                return "vào hành trang\nChọn 1 trang bị có thể hắc hóa ( danh hiệu cao thủ ) và đá pháp sư \n "
                        + " để nâng cấp chỉ số pháp sư"
                        + "Chỉ cần chọn 'Nâng Cấp'";
            case TAY_PS_HOA_TRANG_BI:
                return "vào hành trang\nChọn 1 trang bị có thể hắc hóa ( phụ kiên,ngọc bội,pet,..) và bùa giải pháp sư \n "
                        + " để xoá nâng cấp chỉ số pháp sư"
                        + "Chỉ cần chọn 'Nâng Cấp'";
            case CHE_TAO_TRANG_BI_TS:
                return "Cần 1 công thức vip\nMảnh trang bị tương ứng\n"
                        + "Số Lượng 999"
                        + "Có thể thêm\nĐá nâng cấp (tùy chọn) để tăng tỉ lệ chế tạo\n"
                        + "Đá may mắn (tùy chọn) để tăng tỉ lệ các chỉ số cơ bản và chỉ số ẩn\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case NANG_CAP_CAI_TRANG_SSJ:
                return "Vào hành trang\nChọn Cải Trang GoHan SSJ5\nSau đó chọn 'Nâng cấp'"; 
            case TIEN_HOA_CAI_TRANG_BABY_VEGETA:
                return "Vào hành trang\nChọn Cải Trang GoHan SSJ5 +10\n Chọn tiếp 500 Đá Ngũ Sắc\nSau đó chọn 'Tiến Hóa'"; 
           
            case DOI_SKH_THANH_DNS:
                return "Cần 2 món sét kích hoạt bất kỳ"
                        + "Sau đó chọn 'Nâng cấp'";
            case DAP_SET_KICH_HOAT:
                return "Cần 1 Trang Bị Thần Linh bất kỳ và 100 Đá Ngũ Sắc"
                        + "Sau đó chọn 'Nâng cấp'";
            case DAP_SET_KICH_HOAT_HD:
                return "Cần 1 Trang Bị Hủy diệt bất kỳ và 150 Đá Ngũ Sắc"
                        + "Sau đó chọn 'Nâng cấp'";
            case DAP_SET_KICH_HOAT_TS:
                return "Cần 1 Trang Bị Thiên sứ bất kỳ và 200 Đá Ngũ Sắc"
                        + "Sau đó chọn 'Nâng cấp'";
                
            case TAY_SKH_TRANG_BI:
                return "Cần 1 Trang Bị SKH ( Thần Linh, Hủy Diệt, Thiên Sứ và 1 Bùa Tẩy Pháp Sư"
                        + "\nSau đó chọn 'Tẩy SKH'";
            case KHAC_CHI_SO_DA:
                return "Cần 1 Ngọc Bội - Hoàng Kim và 1 Đá Khắc Chỉ Số"
                        + "\nSau đó chọn 'Điêu Khắc'";
            case KHAC_CHI_SO_NGOC_BOI:
                return "Cần 1 Ngọc Bội - Hoàng Kim và 1 Đá Khắc Chỉ Số"
                        + "\nSau đó chọn 'Điêu Khắc Ngọc Bội'";
            case THANG_CAP_TRANG_BI:
                return "Cần 1 Trang Bị 8 Sao, Đá Bảo Vệ và Lửa Thần"
                        + "\nSau đó chọn 'Thăng Cấp Trang Bị'";
            case AN_TRANG_BI:
                return "Vào hành trang\nChọn 1 Trang bị THIÊN SỨ và 99 mảnh Ấn\nSau đó chọn 'Làm phép'\n--------\nTinh ấn (5 món +30%HP)\n Nhật ấn (5 món +30%KI\n Nguyệt ấn (5 món +20%SD)";
            default:
                return "";
        }
    }

}
