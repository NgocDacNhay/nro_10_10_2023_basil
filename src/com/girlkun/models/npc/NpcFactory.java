package com.girlkun.models.npc;

import com.girlkun.consts.ConstMap;
import com.girlkun.models.boss.list_boss.nappa.Kuku;
import com.girlkun.server.ServerManager;
import com.girlkun.server.io.MySession;
import com.girlkun.models.map.challenge.MartialCongressService;
import com.girlkun.models.map.daihoi.DaiHoiManager;
import com.girlkun.services.*;
import com.girlkun.consts.ConstNpc;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.consts.ConstTask;
import com.girlkun.database.GirlkunDB;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.kygui.ShopKyGuiService;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.list_boss.NhanBan;
import com.girlkun.models.boss.list_boss.DuongTank;
import com.girlkun.models.clan.Clan;
import com.girlkun.models.clan.ClanMember;
import java.time.LocalDate;


import java.util.HashMap;
import java.util.List;

import com.girlkun.services.func.ChangeMapService;
import com.girlkun.services.func.SummonDragon;

import static com.girlkun.services.func.SummonDragon.SHENRON_1_STAR_WISHES_1;
import static com.girlkun.services.func.SummonDragon.SHENRON_1_STAR_WISHES_2;
import static com.girlkun.services.func.SummonDragon.SHENRON_2_STARS_WHISHES;
import static com.girlkun.services.func.SummonDragon.SHENRON_SAY;

import com.girlkun.models.player.Player;
import com.girlkun.models.item.Item;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.Zone;
import com.girlkun.models.map.blackball.BlackBallWar;
import com.girlkun.models.map.MapMaBu.MapMaBu;
import com.girlkun.models.map.bdkb.BdkbService;
import com.girlkun.models.map.doanhtrai.DoanhTrai;
import com.girlkun.models.map.doanhtrai.DoanhTraiService;
import com.girlkun.models.map.gas.Gas;
import com.girlkun.models.map.gas.GasService;
import com.girlkun.models.player.Inventory;
import com.girlkun.models.player.NPoint;
import com.girlkun.models.matches.PVPService;
import com.girlkun.models.matches.pvp.DaiHoiVoThuat;
import com.girlkun.models.matches.pvp.DaiHoiVoThuatService;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.shop.ShopServiceNew;
import com.girlkun.models.skill.Skill;
import com.girlkun.network.io.Message;
import com.girlkun.server.Client;
import com.girlkun.server.Maintenance;
import com.girlkun.server.Manager;
import static com.girlkun.services.NgocRongNamecService.TIME_OP;
import com.girlkun.services.func.CombineServiceNew;
import com.girlkun.services.func.Input;
import com.girlkun.services.func.LuckyRound;
import com.girlkun.services.func.TopService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.TimeUtil;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import com.girlkun.services.func.ChonAiDay;
import com.girlkun.utils.SkillUtil;
import static com.girlkun.utils.SkillUtil.getSkillbyId;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class NpcFactory {

    private static final int COST_HD = 50000000;

    private static boolean nhanVang = false;
    private static boolean nhanDeTu = false;

    //playerid - object
    public static final java.util.Map<Long, Object> PLAYERID_OBJECT = new HashMap<Long, Object>();

//    private static Npc onggianoel(int mapId, int status, int cx, int cy, int tempId, int avatar) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    private NpcFactory() {

    }

    private static Npc trungLinhThu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đổi Trứng Linh thú cần:\b|7|X99 Hồn Linh Thú + 1 Tỷ vàng", "Đổi Trứng\nLinh thú", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Item honLinhThu = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 2029);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ 99 Hồn Linh thú");
                                    } else if (player.inventory.gold < 1_000_000_000) {
                                        this.npcChat(player, "Bạn không đủ 1 Tỷ vàng");
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        player.inventory.gold -= 1_000_000_000;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 2028);
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Trứng Linh thú");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }   
        
 private static Npc popo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
//                if (player.clanMember.getNumDateFromJoinTimeToToday() < 1 && player.clan != null) {
//                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
//                                "Map Khí Gas chỉ cho phép những người ở trong bang trên 1 ngày. Hẹn ngươi quay lại vào lúc khác",
//                                "OK", "Hướng\ndẫn\nthêm");
//                        return;
//                    }
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (player.getSession().is_gift_box) {
//                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chào con, con muốn ta giúp gì nào?", "Giải tán bang hội", "Nhận quà\nđền bù");
                        } else {
                             this.createOtherMenu(player, ConstNpc.BASE_MENU, "Thượng đế vừa phát hiện 1 loại khí đang âm thầm\nhủy diệt mọi mầm sống trên Trái Đất,\nnó được gọi là Destron Gas.\nTa sẽ đưa các cậu đến nơi ấy, các cậu sẵn sàng chưa?","Thông Tin Chi Tiết","OK","Từ Chối");
                       }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 1:
                                if (player.clan != null) {
                                    if (player.clan.khiGas != null) {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPENED_GAS,
                                                "Bang hội của con đang đi DesTroy Gas cấp độ "
                                                        + player.clan.khiGas.level + "\nCon có muốn đi theo không?",
                                                "Đồng ý", "Từ chối");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPEN_GAS,
                                                "Khí Gas Huỷ Diệt đã chuẩn bị tiếp nhận các đợt tấn công của quái vật\n"
                                                        + "các con hãy giúp chúng ta tiêu diệt quái vật \n"
                                                        + "Ở đây có ta lo\nNhớ chọn cấp độ vừa sức mình nhé",
                                                "Chọn\ncấp độ", "Từ chối");
                                    }
                                } else {
                                    this.npcChat(player, "Con phải có bang hội ta mới có thể cho con đi");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPENED_GAS) {
                        switch (select) {
                            case 0:
                                if (player.isAdmin() || player.nPoint.power >= Gas.POWER_CAN_GO_TO_GAS) {
                                    player.type = 1;
                                    ChangeMapService.gI().goToGas(player);
                                } else {
                                    this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                            + Util.numberToMoney(Gas.POWER_CAN_GO_TO_GAS));
                                }
                                break;

                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPEN_GAS) {
                        switch (select) {
                            case 0:
                                if (player.isAdmin() || player.nPoint.power >= Gas.POWER_CAN_GO_TO_GAS) {
                                    Input.gI().createFormChooseLevelGas(player);
                                } else {
                                    this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                            + Util.numberToMoney(Gas.POWER_CAN_GO_TO_GAS));
                                }
                                break;
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_ACCPET_GO_TO_GAS) {
                        switch (select) {
                            case 0:
                                player.type = 1;
                                GasService.gI().openKhiGa(player, Integer.parseInt(String.valueOf(PLAYERID_OBJECT.get(player.id))));
                                break;
                        }
                    }
                }
            }
        };
    }


public static Npc docNhan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.clan == null) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chỉ tiếp các bang hội, miễn tiếp khách vãng lai", "Đóng");
                        return;
                    }
                    if (player.clan.haveGoneDoanhTrai) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ta đã thả ngọc rồng ở tất cả các map,mau đi nhặt đi. Hẹn ngươi quay lại vào ngày mai", "OK");
                        return;
                    }

                    boolean flag = true;
                    for (Mob mob : player.zone.mobs) {
                        if (!mob.isDie()) {
                            flag = false;
                        }
                    }
                    for (Player boss : player.zone.getBosses()) {
                        if (!boss.isDie()) {
                            flag = false;
                        }
                    }

                    if (flag) {
                        player.clan.haveGoneDoanhTrai = true;
                        player.clan.doanhTrai.setLastTimeOpen(System.currentTimeMillis() + 250_000);
                        player.clan.doanhTrai.DropNgocRong();
                        for (Player pl : player.clan.membersInGame) {
                            ItemTimeService.gI().sendTextDoanhTrai(pl);
                        }
                        player.clan.doanhTrai.timePickDragonBall = true;
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ta đã thả ngọc rồng ở tất cả các map,mau đi nhặt đi. Hẹn ngươi quay lại vào ngày mai", "OK");
                    } else {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Hãy tiêu diệt hết quái và boss trong map", "OK");
                    }

                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_JOIN_DOANH_TRAI:
                            if (select == 0) {
                                DoanhTraiService.gI().joinDoanhTrai(player);
                            } else if (select == 2) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                        case ConstNpc.IGNORE_MENU:
                            if (select == 1) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                    }
                }
            }
        };
    }    


    private static Npc npcuub(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đổi Vòng kim cô Mở ra PET cần:\b|7|X99 Thăng linh thạch + 40k Hồng ngọc", "Đổi Vòng\nkim cô", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Item honLinhThu = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 2031);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ 99 Thăng linh thạch");
                                    } else if (player.inventory.ruby < 40_000) {
                                        this.npcChat(player, "Bạn không đủ 40k Hồng ngọc");
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        player.inventory.ruby -= 40_000;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 543);
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Vòng kim cô");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }
     private static Npc fide(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 7 || this.mapId == 14 || this.mapId == 0) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "-Sự Kiện Hè-\bKhi mặc quần đi biển đánh quái sẽ có tỉ lệ rơi : \bSao Biển, Con Cua, Vỏ Ốc, Vỏ Sò \bTHÔNG TIN ĐỔI VẬT PHẨM \n|5|Đổi 4 loại Sao Biển, Con Cua, Vỏ Ốc, Vỏ Sò nhận 1 mảnh Cải Trang \nĐổi 4 loại Sao Biển, Con Cua, Vỏ Ốc, Vỏ Sò nhận 1 Hồn Linh Thú"
                                ,"x100 Mảnh\nCải Trang"
                                , "x100 Hồn\nLinh Thú"
                                , "Đổi Mảnh\n Đồ TS"    
                                ,"Đổi Ngọc Bội");
                    }
                }
            }

//            @Override
//            public void confirmMenu(Player player, int select) {
//                if (canOpenNpc(player)) {
//                    if (this.mapId == 104 || this.mapId == 5) {
//                        if (player.iDMark.isBaseMenu()) {
//                            if (select == 0) {
            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 7 || this.mapId == 14 || this.mapId == 0) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {                        
                                case 0: {
                                    Item honLinhThu = null;
                                    Item honLinhThu1 = null;
                                    Item honLinhThu2 = null;
                                    Item honLinhThu3 = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 695);
                                        honLinhThu1 = InventoryServiceNew.gI().findItemBag(player, 696);
                                        honLinhThu2 = InventoryServiceNew.gI().findItemBag(player, 697);
                                        honLinhThu3 = InventoryServiceNew.gI().findItemBag(player, 698);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu1 == null || honLinhThu2 == null || honLinhThu3 == null || honLinhThu.quantity < 100 || honLinhThu1.quantity < 100 || honLinhThu2.quantity < 100 || honLinhThu3.quantity < 100) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 100);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu1, 100);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu2, 100);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu3, 100);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 720, 100); // Mảnh Cải Trang                                                              
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được mảnh Cải Trang");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item honLinhThu = null;
                                    Item honLinhThu1 = null;
                                    Item honLinhThu2 = null;
                                    Item honLinhThu3 = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 695);
                                        honLinhThu1 = InventoryServiceNew.gI().findItemBag(player, 696);
                                        honLinhThu2 = InventoryServiceNew.gI().findItemBag(player, 697);
                                        honLinhThu3 = InventoryServiceNew.gI().findItemBag(player, 698);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu1 == null || honLinhThu2 == null || honLinhThu3 == null || honLinhThu.quantity < 100 || honLinhThu1.quantity < 100 || honLinhThu2.quantity < 100 || honLinhThu3.quantity < 100) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 100);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu1, 100);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu2, 100);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu3, 100);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 2029,100); // Hồn Linh Thú                                                              
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Hồn Linh Thú");
                                    }
                                    break;
                                }
                                
                                case 3:
                                    createOtherMenu(player, ConstNpc.MENU_EP_SKH,
                                            "Để nhận X1 Phiếu Đá Điêu Khắc cần 100 vinh dự samurai."
                                                    + "\nSử dụng x150 Vinh Dự Samurai để đổi x1 Ngọc Bội - Hoàng Kim"
                                                    + "|7| Vật phẩm đổi Đá Điêu Khắc Chỉ Số theo từng cấp như sau:|7|"
                                                    + "\nĐá Điêu Khắc CS Cấp 1 : x1 Phiếu Đổi Đá + x50 Vinh Dự Samurai"
                                                    + "\nĐá Điêu Khắc CS Cấp 2 : x2 Phiếu Đổi Đá + x100 Vinh Dự Samurai"
                                            + "\nĐá Điêu Khắc CS Cấp 3 : x4 Phiếu Đổi Đá + x150 Vinh Dự Samurai"
                                            + "\nĐá Điêu Khắc CS Cấp 4 : x8 Phiếu Đổi Đá + x200 Vinh Dự Samurai"
                                            + "\nĐá Điêu Khắc CS Cấp 5 : x15 Phiếu Đổi Đá + x300 Vinh Dự Samurai"
                                            + "\nĐá Điêu Khắc CS Cấp 6 : x25 Phiếu Đổi Đá + x400 Vinh Dự Samurai"
                                            + "\nĐá Điêu Khắc CS Cấp 7 : x35 Phiếu Đổi Đá + x500 Vinh Dự Samurai"
                                            + "\nĐá Điêu Khắc CS Cấp 8 : x45 Phiếu Đổi Đá + x600 Vinh Dự Samurai"
                                            + "\nĐá Điêu Khắc CS Cấp 9 : x55 Phiếu Đổi Đá + x700 Vinh Dự Samurai"
                                            + "\nĐá Điêu Khắc CS Cấp 10 : x65 Phiếu Đổi Đá + x800 Vinh Dự Samurai",
                                            "Đổi Phiếu\n Đá Điêu Khắc",
                                            "Đổi Vinh Dự\n Lấy Ngọc Bội",
                                            "Đổi Đá CS\n Cấp 1", 
                                            "Đổi Đá CS\n Cấp 2", 
                                            "Đổi Đá CS\n Cấp 3", 
                                            "Đổi Đá CS\n Cấp 4", 
                                            "Đổi Đá CS\n Cấp 5",
                                            "Đổi Đá CS\n Cấp 6",
                                            "Đổi Đá CS\n Cấp 7",
                                            "Đổi Đá CS\n Cấp 8",
                                            "Đổi Đá CS\n Cấp 9",
                                            "Đổi Đá CS\n Cấp 10",
                                            "Đổi Đá\n Tiến Hóa\n Ngọc Bội",
                                            "Đóng");
                                    break;
                                case 2:
                                    createOtherMenu(player, ConstNpc.MENU_DOI_MTS,
                                            "Ngươi muốn chọn Mảnh Thiên Sứ cần đổi bên dưới!",
                                            "Đổi Mảnh\n Quần TS \n=> Găng TS",
                                            "Đổi Mảnh\n Áo TS \n=> Găng TS",
                                            "Đổi Mảnh\n Giày TS \n=> Găng TS",
                                            "Đổi Mảnh\n Quần TS \n=> Nhẫn TS",
                                            "Đổi Mảnh\n Áo TS \n=> Nhẫn TS",
                                            "Đổi Mảnh\n Giày TS \n=> Nhẫn TS",
                                            "Đổi Mảnh\n Nhẫn TS \n=> Găng TS",
                                            "Đóng");
                                    break;

                            }
                        }
                        
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DOI_MTS) {
                            switch (select) {
                                case 0: {
                                    Item ManhquanTS = null;
                    
                                    try {
                                        ManhquanTS = InventoryServiceNew.gI().findItemBag(player, 1067);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (ManhquanTS == null || ManhquanTS.quantity < 500) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, ManhquanTS, 500);
                                        Service.getInstance().sendMoney(player);
                                        Item ManhgangTS = ItemService.gI().createNewItem((short) 1070,100); // Mảnh Găng Thiên Sư                                                         
                                        InventoryServiceNew.gI().addItemBag(player, ManhgangTS);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 100 Mảnh Thiên Sứ ( Găng )");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item ManhaoTS = null;
                    
                                    try {
                                        ManhaoTS = InventoryServiceNew.gI().findItemBag(player, 1066);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (ManhaoTS == null || ManhaoTS.quantity < 500) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, ManhaoTS, 500);
                                        Service.getInstance().sendMoney(player);
                                        Item ManhgangTS = ItemService.gI().createNewItem((short) 1070,100); // Mảnh Găng Thiên Sư                                                         
                                        InventoryServiceNew.gI().addItemBag(player, ManhgangTS);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 100 Mảnh Thiên Sứ ( Găng )");
                                    }
                                    break;
                                }
                                case 2: {
                                    Item ManhgiayTS = null;
                    
                                    try {
                                        ManhgiayTS = InventoryServiceNew.gI().findItemBag(player, 1068);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (ManhgiayTS == null || ManhgiayTS.quantity < 500) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, ManhgiayTS, 500);
                                        Service.getInstance().sendMoney(player);
                                        Item ManhgangTS = ItemService.gI().createNewItem((short) 1070,100); // Mảnh Găng Thiên Sư                                                         
                                        InventoryServiceNew.gI().addItemBag(player, ManhgangTS);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 100 Mảnh Thiên Sứ ( Găng )");
                                    }
                                    break;
                                }
                                case 3: {
                                    Item ManhquanTS = null;
                    
                                    try {
                                        ManhquanTS = InventoryServiceNew.gI().findItemBag(player, 1067);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (ManhquanTS == null || ManhquanTS.quantity < 500) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, ManhquanTS, 500);
                                        Service.getInstance().sendMoney(player);
                                        Item ManhgangTS = ItemService.gI().createNewItem((short) 1069,100); // Mảnh Nhẫn Thiên Sư                                                         
                                        InventoryServiceNew.gI().addItemBag(player, ManhgangTS);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 100 Mảnh Thiên Sứ ( Nhẫn )");
                                    }
                                    break;
                                }
                                case 4: {
                                    Item ManhaoTS = null;
                    
                                    try {
                                        ManhaoTS = InventoryServiceNew.gI().findItemBag(player, 1066);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (ManhaoTS == null || ManhaoTS.quantity < 500) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, ManhaoTS, 500);
                                        Service.getInstance().sendMoney(player);
                                        Item ManhgangTS = ItemService.gI().createNewItem((short) 1069,100); // Mảnh Găng Thiên Sư                                                         
                                        InventoryServiceNew.gI().addItemBag(player, ManhgangTS);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 100 Mảnh Thiên Sứ ( Nhẫn )");
                                    }
                                    break;
                                }
                                case 5: {
                                    Item ManhgiayTS = null;
                    
                                    try {
                                        ManhgiayTS = InventoryServiceNew.gI().findItemBag(player, 1068);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (ManhgiayTS == null || ManhgiayTS.quantity < 500) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, ManhgiayTS, 500);
                                        Service.getInstance().sendMoney(player);
                                        Item ManhgangTS = ItemService.gI().createNewItem((short) 1069,100); // Mảnh Găng Thiên Sư                                                         
                                        InventoryServiceNew.gI().addItemBag(player, ManhgangTS);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 100 Mảnh Thiên Sứ ( Nhẫn )");
                                    }
                                    break;
                                }
                                case 6: {
                                    Item NhanTS = null;
                    
                                    try {
                                        NhanTS = InventoryServiceNew.gI().findItemBag(player, 1069);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (NhanTS == null || NhanTS.quantity < 300) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, NhanTS, 300);
                                        Service.getInstance().sendMoney(player);
                                        Item ManhgangTS = ItemService.gI().createNewItem((short) 1070,100); // Mảnh Găng Thiên Sư                                                         
                                        InventoryServiceNew.gI().addItemBag(player, ManhgangTS);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 100 Mảnh Thiên Sứ ( Găng )");
                                    }
                                    break;
                                }
                                
                                }
                            }
                        
                        
                        
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_EP_SKH) {
                            switch (select) {
                                
                                case 0: {
                                      Item VinhDu = null;
                                    try {
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if ( VinhDu.quantity < 100 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ 100 Vinh Dự Samurai");                                                                                                           
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 100);
                                        Service.getInstance().sendMoney(player);
                                        Item PhieuDoi = ItemService.gI().createNewItem((short) 1252, 1); // Phiếu Đổi Đá Quý
                                        InventoryServiceNew.gI().addItemBag(player, PhieuDoi);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Phiếu Đổi Đá Quý");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item VinhDu = null;
                                    try {
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if ( VinhDu.quantity < 150 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ 150 Vinh Dự Samurai");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 150);
                                        Service.getInstance().sendMoney(player);
                                        Item NgocBoi = ItemService.gI().createNewItem((short) 1248, 1);
                                        NgocBoi.itemOptions.add(new ItemOption(77, Util.nextInt(20,40)));
                                        NgocBoi.itemOptions.add(new ItemOption(103, Util.nextInt(20,40)));
                                        NgocBoi.itemOptions.add(new ItemOption(50, Util.nextInt(20,40)));
                                        NgocBoi.itemOptions.add(new ItemOption(5, Util.nextInt(5,20)));
                                        InventoryServiceNew.gI().addItemBag(player, NgocBoi);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Ngọc Bội - Hoàng Kim");
                                    }
                                    break;
                                }
                                
                                case 2: {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 1 || VinhDu.quantity < 50 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 1);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 50);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 1));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 1");
                                    }
                                    break;
                                }
                                case 3:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 2 || VinhDu.quantity < 100 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 2);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 100);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 2));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 2");
                                    }
                                    break;
                                }
                                case 4:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 4 || VinhDu.quantity < 150 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 4);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 150);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 3));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 3");
                                    }
                                    break;
                                }
                                case 5:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 8 || VinhDu.quantity < 200 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 8);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 200);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 4));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 4");
                                    }
                                    break;
                                }
                                case 6:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 15 || VinhDu.quantity < 300 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 15);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 300);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 5));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 5");
                                    }
                                    break;
                                }
                                case 7:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 25 || VinhDu.quantity < 400 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 25);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 400);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 6));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 6");
                                    }
                                    break;
                                }
                                case 8:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 35 || VinhDu.quantity < 500 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 35);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 500);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 7));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 7");
                                    }
                                    break;
                                }
                                case 9:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 45 || VinhDu.quantity < 600 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 45);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 600);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 8));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 8");
                                    }
                                    break;
                                }
                                case 10:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 55 || VinhDu.quantity < 700 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 55);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 700);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 9));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 9");
                                    }
                                    break;
                                }
                                case 11:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 65 || VinhDu.quantity < 800 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 65);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 800);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1251, 1); // Đá Khắc Chỉ Số
                                        DaKhac.itemOptions.add(new ItemOption(217, 10));
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Khắc Chỉ Số Cấp 10");
                                    }
                                    break;
                                }
                                case 12:
                                    {
                                    Item PhieuDoi = null;
                                    Item VinhDu = null;
                                    try {
                                        PhieuDoi = InventoryServiceNew.gI().findItemBag(player, 1252);
                                        VinhDu = InventoryServiceNew.gI().findItemBag(player, 1249);
                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    
                                    if (PhieuDoi == null || PhieuDoi.quantity < 100 || VinhDu.quantity < 1000 || VinhDu == null ) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, PhieuDoi, 100);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VinhDu, 1000);
                                        Service.getInstance().sendMoney(player);
                                        Item DaKhac = ItemService.gI().createNewItem((short) 1258, 1); // Đá Tiến Hóa Ngọc Bội
                                        InventoryServiceNew.gI().addItemBag(player, DaKhac);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Đá Tiến Hóa Ngọc Bội");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }
     public static Npc GhiDanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            String[] menuselect = new String[]{};
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.mapId == 52) {
                        if (DaiHoiManager.gI().openDHVT && (System.currentTimeMillis() <= DaiHoiManager.gI().tOpenDHVT)) {
                            String nameDH = DaiHoiManager.gI().nameRoundDHVT();
                            this.createOtherMenu(pl, ConstNpc.MENU_DHVT, "Hiện đang có giải đấu " + nameDH + " bạn có muốn đăng ký không? \nSố người đã đăng ký :"+ DaiHoiManager.gI().lstIDPlayers.size(), new String[]{"Giải\n" + nameDH + "\n(" + DaiHoiManager.gI().costRoundDHVT() + ")", "Từ chối", "Đại Hội\nVõ Thuật\nLần thứ\n23", "Giải siêu hạng"});
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU, "Đã hết hạn đăng ký thi đấu, xin vui lòng chờ đến giải sau", new String[]{"Thông tin\bChi tiết", "OK", "Đại Hội\nVõ Thuật\nLần thứ\n23", "Giải siêu hạng\n(thử nghiệm)"});
                        }
                    }else if(this.mapId == 129){
//                        int goldchallenge = pl.goldChallenge;
                        if (pl.levelWoodChest == 0) {
                            menuselect = new String[]{"Thi đấu\n" + 200 + " Hồng ngọc", "Về\nĐại Hội\nVõ Thuật"};
                        } else {
                            menuselect = new String[]{"Thi đấu\n" + 200 + " Hồng ngọc", "Nhận thưởng\nRương cấp\n" + pl.levelWoodChest, "Về\nĐại Hội\nVõ Thuật"};
                        }
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU, "Đại hội võ thuật lần thứ 23\nDiễn ra bất kể ngày đêm,ngày nghỉ ngày lễ\nPhần thưởng vô cùng quý giá\nNhanh chóng tham gia nào", menuselect, "Từ chối");

                    }else{
                    super.openBaseMenu(pl);
                    }
                    }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if(this.mapId == 52) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    Service.getInstance().sendThongBaoFromAdmin(player, "Lịch thi đấu trong ngày\bGiải Nhi đồng: 8,13,18h\bGiải Siêu cấp 1: 9,14,19h\bGiải Siêu cấp 2: 10,15,20h\bGiải Siêu cấp 3: 11,16,21h\bGiải Ngoại hạng: 12,17,22,23h\nGiải thưởng khi thắng mỗi vòng\bGiải Nhi đồng: 2 ngọc\bGiải Siêu cấp 1: 4 ngọc\bGiải Siêu cấp 2: 6 ngọc\bGiải Siêu cấp 3: 8 ngọc\bGiải Ngoại hạng: 10.000 vàng\bVô địch: 5 viên đá nâng cấp\nVui lòng đến đúng giờ để đăng ký thi đấu");
                                    break;
                                case 1:
                                    Service.getInstance().sendThongBaoFromAdmin(player, "Nhớ Đến Đúng Giờ nhé");
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 129, player.location.x, 360);
                                    break;
                                case 3:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 113, player.location.x, 360);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DHVT) {
                            switch (select) {
                                case 0:
//                                    if (DaiHoiService.gI().canRegisDHVT(player.nPoint.power)) {
                                    if (DaiHoiManager.gI().lstIDPlayers.size() < 256) {
                                        if (DaiHoiManager.gI().typeDHVT == (byte) 5 && player.inventory.gold >= 10000) {
                                            if (DaiHoiManager.gI().isAssignDHVT(player.id)) {
                                                Service.getInstance().sendThongBao(player, "Bạn đã đăng ký tham gia đại hội võ thuật rồi");
                                            } else {
                                                player.inventory.gold -= 10000;
                                                Service.getInstance().sendMoney(player);
                                                Service.getInstance().sendThongBao(player, "Bạn đã đăng ký thành công, nhớ có mặt tại đây trước giờ thi đấu");
                                                DaiHoiManager.gI().lstIDPlayers.add(player.id);
                                            }
                                        } else if (DaiHoiManager.gI().typeDHVT > (byte) 0 && DaiHoiManager.gI().typeDHVT < (byte) 5 && player.inventory.gem >= (int) (2 * DaiHoiManager.gI().typeDHVT)) {
                                            if (DaiHoiManager.gI().isAssignDHVT(player.id)) {
                                                Service.getInstance().sendThongBao(player, "Bạn đã đăng ký tham gia đại hội võ thuật rồi");
                                            } else {
                                                player.inventory.gem -= (int) (2 * DaiHoiManager.gI().typeDHVT);
                                                Service.getInstance().sendMoney(player);
                                                Service.getInstance().sendThongBao(player, "Bạn đã đăng ký thành công, nhớ có mặt tại đây trước giờ thi đấu");
                                                DaiHoiManager.gI().lstIDPlayers.add(player.id);
                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng ngọc để đăng ký thi đấu");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hiện tại đã đạt tới số lượng người đăng ký tối đa, xin hãy chờ đến giải sau");
                                    }

//                                    } else {
//                                        Service.getInstance().sendThongBao(player, "Bạn không đủ điều kiện tham gia giải này, hãy quay lại vào giải phù hợp");
//                                    }
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 129, player.location.x, 360);
                                    break;
                            }
                        }
                    } else if (this.mapId == 129) {
//                            int goldchallenge = player.goldChallenge;
                            if (player.levelWoodChest == 0) {
                                switch (select) {
                                    case 0:
                                        if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                            if (player.inventory.ruby >= 200) {
                                                MartialCongressService.gI().startChallenge(player);
                                                player.inventory.ruby -= 200;
                                                PlayerService.gI().sendInfoHpMpMoney(player);
                                                player.goldChallenge += 2000000;
                                            } else {
                                                Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu " + Util.numberToMoney(200 - player.inventory.ruby) + " Hồng ngọc");
                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                        }
                                        break;
                                    case 1:
                                        ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                        break;
                                }
                            } else {
                                switch (select) {
                                    case 0:
                                        if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                            if (player.inventory.ruby >= 200) {
                                                MartialCongressService.gI().startChallenge(player);
                                                player.inventory.ruby -= (200);
                                                PlayerService.gI().sendInfoHpMpMoney(player);
                                                player.goldChallenge += 200;
                                            } else {
                                                Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu " + Util.numberToMoney(200 - player.inventory.ruby) + " vàng");
                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                        }
                                        break;
                                    case 1:
                                        if (!player.receivedWoodChest) {
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                Item it = ItemService.gI().createNewItem((short) 570);
                                                it.itemOptions.add(new Item.ItemOption(72, player.levelWoodChest));
                                                it.itemOptions.add(new Item.ItemOption(30, 0));
                                                it.createTime = System.currentTimeMillis();
                                                InventoryServiceNew.gI().addItemBag(player, it);
                                                InventoryServiceNew.gI().sendItemBags(player);

                                                player.receivedWoodChest = true;
                                                player.levelWoodChest = 0;
                                                Service.getInstance().sendThongBao(player, "Bạn nhận được rương gỗ");
                                            } else {
                                                this.npcChat(player, "Hành trang đã đầy");
                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Mỗi ngày chỉ có thể nhận rương báu 1 lần");
                                        }
                                        break;
                                    case 2:
                                        ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                        break;
                                }
                            }
                        }
                    }
                }
        };
    }

    private static Npc monaito(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đổi Lấy Đá Cầu Vòng:\b|7|X99 đá ngũ sắc + 1 Tỷ vàng", "Đổi Cầu\nVòng", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Item honLinhThu = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 674);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ 99 đá ngũ sắc");
                                    } else if (player.inventory.gold < 1_000_000_000) {
                                        this.npcChat(player, "Bạn không đủ 1 Tỷ vàng");
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        player.inventory.gold -= 1_000_000_000;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 1083);
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Đá Cầu Vòng");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }
    private static Npc jiren(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Sự kiện đánh quái bên tương lai ra bùa Giải, Khai, Phong\nQuả hồng đào đánh quái tại Ngũ hành Sơn đổi bùa Ấn\nTHÔNG TIN ĐỔI VẬT PHẨM\b|7|Đổi 1 Quả hồng đào => 1 bùa Ấn\nĐổi x99 4 loại bùa Giải, Khai, Phong, Ấn được cải trang chỉ số xịn\b|5|x99 Mảnh cải trang => CT Broly 100%TNSM 1 Ngày\nx999 Mảnh CT + 100K hngoc => CT Broly Vĩnh viễn"
                                , "Nhận bùa Ấn", "Cải trang Inosuke", "Cải trang Zenitsu", "Cải trang Nezuko", "Cải trang Tanjiro", "CT Broly\n1 Ngày", "CT Broly\nVĩnh viễn", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Item quahongdao = null;
                                    try {
                                        quahongdao = InventoryServiceNew.gI().findItemBag(player, 541);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (quahongdao == null || quahongdao.quantity < 1) {
                                        this.npcChat(player, "Bạn không đủ Quả hồng đào");
                                    
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, quahongdao, 1);
                                        Service.getInstance().sendMoney(player);
                                        Item chuan = ItemService.gI().createNewItem((short) 540);
                                        chuan.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, chuan);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được x1 Bùa Ấn");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item honLinhThu = null;
                                    Item honLinhThu1 = null;
                                    Item honLinhThu2 = null;
                                    Item honLinhThu3 = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 537);
                                        honLinhThu1 = InventoryServiceNew.gI().findItemBag(player, 538);
                                        honLinhThu2 = InventoryServiceNew.gI().findItemBag(player, 539);
                                        honLinhThu3 = InventoryServiceNew.gI().findItemBag(player, 540);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu1 == null || honLinhThu2 == null || honLinhThu3 == null || honLinhThu.quantity < 99 || honLinhThu1.quantity < 99 || honLinhThu2.quantity < 99 || honLinhThu3.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu1, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu2, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu3, 99);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 1089); // Cải trang Inosuke
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(50, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(77, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(103, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(101, Util.nextInt(50,60)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(210, 1));                                          
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Cải trang Inosuke");
                                    }
                                    break;
                                }
                                case 2: {
                                    Item honLinhThu = null;
                                    Item honLinhThu1 = null;
                                    Item honLinhThu2 = null;
                                    Item honLinhThu3 = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 537);
                                        honLinhThu1 = InventoryServiceNew.gI().findItemBag(player, 538);
                                        honLinhThu2 = InventoryServiceNew.gI().findItemBag(player, 539);
                                        honLinhThu3 = InventoryServiceNew.gI().findItemBag(player, 540);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu1 == null || honLinhThu2 == null || honLinhThu3 == null || honLinhThu.quantity < 99 || honLinhThu1.quantity < 99 || honLinhThu2.quantity < 99 || honLinhThu3.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu1, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu2, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu3, 99);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 1090); //Cải trang Zenitsu
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(50, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(77, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(103, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(101, Util.nextInt(50,60)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(210, 1)); 
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Cải trang Zenitsu");
                                    }
                                    break;
                                }
                                case 3: {
                                    Item honLinhThu = null;
                                    Item honLinhThu1 = null;
                                    Item honLinhThu2 = null;
                                    Item honLinhThu3 = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 537);
                                        honLinhThu1 = InventoryServiceNew.gI().findItemBag(player, 538);
                                        honLinhThu2 = InventoryServiceNew.gI().findItemBag(player, 539);
                                        honLinhThu3 = InventoryServiceNew.gI().findItemBag(player, 540);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu1 == null || honLinhThu2 == null || honLinhThu3 == null || honLinhThu.quantity < 99 || honLinhThu1.quantity < 99 || honLinhThu2.quantity < 99 || honLinhThu3.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu1, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu2, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu3, 99);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 1091); //Cải trang Nezuko
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(50, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(77, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(103, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(101, Util.nextInt(50,60)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(210, 1)); 
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Cải trang Nezuko");
                                    }
                                    break;
                                }
                                case 4: {
                                    Item honLinhThu = null;
                                    Item honLinhThu1 = null;
                                    Item honLinhThu2 = null;
                                    Item honLinhThu3 = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 537);
                                        honLinhThu1 = InventoryServiceNew.gI().findItemBag(player, 538);
                                        honLinhThu2 = InventoryServiceNew.gI().findItemBag(player, 539);
                                        honLinhThu3 = InventoryServiceNew.gI().findItemBag(player, 540);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu1 == null || honLinhThu2 == null || honLinhThu3 == null || honLinhThu.quantity < 99 || honLinhThu1.quantity < 99 || honLinhThu2.quantity < 99 || honLinhThu3.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu1, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu2, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu3, 99);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 1087); //Cải trang Tanjiro
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(50, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(77, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(103, Util.nextInt(30,45)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(101, Util.nextInt(50,60)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(210, 1)); 
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Cải trang Tanjiro");
                                    }
                                    break;
                                }
                                case 5: {
                                    Item honLinhThu = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 720);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ 99 Mảnh CT");
                                    
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 1214);
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(50, Util.nextInt(45,60)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(77, Util.nextInt(50,65)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(103, Util.nextInt(50,65)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(95, Util.nextInt(10,20)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(96, Util.nextInt(10,20)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(101, 100));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(93, 1));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(211, 1));
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được CT Broly Huyền thoại");
                                    }
                                    break;
                                }
                                case 6: {
                                   Item honLinhThu = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 720);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu.quantity < 999) {
                                        this.npcChat(player, "Bạn không đủ 999 Mảnh CT");
                                    
                                    }else if (player.inventory.ruby < 100_000) {
                                        this.npcChat(player, "Bạn không đủ 100k hồng ngọc");
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        player.inventory.ruby -= 100_000;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 999);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 1214);
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(50, Util.nextInt(45,55)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(77, Util.nextInt(50,65)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(103, Util.nextInt(50,65)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(95, Util.nextInt(10,20)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(96, Util.nextInt(10,20)));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(101, 100));
                                        trungLinhThu.itemOptions.add(new Item.ItemOption(211, 1));
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được CT Broly Huyền thoại");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }
    private static Npc gohanssj(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Nhận quà khi đạt mốc 100 tỷ sức mạnh"
                                + "\nĐổi 2 Công Thức Thường Thành 1 Công Thức Vip",
                                "Nhận Quà",
                                "Soi Boss",
                                "Đổi Điểm",
                                "Đổi\nHồng Bao",
                                "Đổi\nCapsule Hồng",
                                "Đổi Hộp\nPháp Sư",                             
                                "Đổi Ngọc\nRồng Băng",
                                "Đổi \nCông Thức VIP");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    if (player.getSession().player.nPoint.power < 100000000000L){
                                        this.npcChat(player, "Bạn không đủ điều kiện nhận danh hiệu");
                                        }
                                    else{                                                                           
                                        Item danhhieu = ItemService.gI().createNewItem((short) 1242);
                                        danhhieu.itemOptions.add(new Item.ItemOption(50, Util.nextInt(35,45)));
                                        danhhieu.itemOptions.add(new Item.ItemOption(77, Util.nextInt(40,50)));
                                        danhhieu.itemOptions.add(new Item.ItemOption(103, Util.nextInt(40,50)));
                                        danhhieu.itemOptions.add(new Item.ItemOption(95, Util.nextInt(5,10)));
                                        danhhieu.itemOptions.add(new Item.ItemOption(96, Util.nextInt(5,10)));
                                        InventoryServiceNew.gI().addItemBag(player, danhhieu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được danh hiệu Cao Thủ");
                                    }
                                break;
                                }
                                case 1: {                                    
                                BossManager.gI().showListBosspl(player);                               
                                break;
                                }
                                case 2: {                                    
                                Input.gI().createFormQDND(player);                               
                                break;
                                }
                                case 3:
                                    createOtherMenu(player, ConstNpc.MENU_1,
                                            "Ngươi muốn Đổi Hồng Bao sang Vàng hãy chọn bên dưới! "
                                                    + "\n Đổi x10 Hồng Bao sẽ nhận ngay 20 Tỷ Vàng "
                                                    + "\n Đổi x100 Hồng Bao sẽ nhận ngay 200 Tỷ Vàng "
                                                    + "\n Đổi x1000 Hồng Bao sẽ nhận ngay 2000 Tỷ Vàng "
                                                    + "\n Đổi x5000 Hồng Bao sẽ nhận ngay 10000 Tỷ Vàng"
                                                    + "\n Đổi x200.000 Hồng Bao sẽ nhận ngay 1000 Vé Hồng Ngọc",
                                            "Đổi x10\n Hồng Bao",
                                            "Đổi x100\n Hồng Bao",
                                            "Đổi x1000\n Hồng Bao", 
                                            "Đổi x5000\n Hồng Bao",
                                            "Đổi x200000\n Hồng Bao",
                                            "Đóng");
                                    break;
                                case 4:
                                    createOtherMenu(player, ConstNpc.MENU_2,
                                            "Ngươi muốn Capsule Hồng sang Vé Hồng Ngọc hãy chọn bên dưới! "
                                                    + "\n Đổi x1000 Capsule Hồng sẽ nhận 5 Vé Hồng Ngọc"
                                                    + "\n Đổi x10000 Capsule Hồng sẽ nhận 50 Vé Hồng Ngọc"
                                                    ,
                                            "Đổi x1000\n Capsule Hồng",
                                            "Đổi x10000\n Capsule Hồng", 
                                            "Đóng");
                                    break;
                                case 5:
                                    createOtherMenu(player, ConstNpc.MENU_3,
                                            "Ngươi muốn Hộp Pháp Sư sang Hộp Pháp Sư Cao Cấp hãy chọn bên dưới! "
                                                    + "\n Đổi x500 Hộp Pháp Sư sẽ nhận 1 Hộp Pháp Sư Cao Cấp"
                                                    + "\n Đổi x5000 Hộp Pháp Sư sẽ nhận 10 Hộp Pháp Sư Cao Cấp"
                                                    + "\n Đổi x50000 Hộp Pháp Sư sẽ nhận 100 Hộp Pháp Sư Cao Cấp"
                                                    ,
                                            "Đổi x500\nHộp Pháp Sư",
                                            "Đổi x5000\nHộp Pháp Sư",
                                            "Đổi x50000\nHộp Pháp Sư",
                                            "Đóng");
                                    break;
                                case 6:
                                    createOtherMenu(player, ConstNpc.MENU_4,
                                            "Ngươi muốn đổi Đá Lục Bảo, Saphia, Ruby, TiTan sang Đá Thạch Anh Tím hãy chọn bên dưới!"
                                                    + "\n Đổi x5000 Đá Lục Bảo sẽ nhận 500 Đá Thạch Anh Tím"
                                                    + "\n Đổi x5000 Đá Saphia sẽ nhận 500 Đá Thạch Anh Tím"
                                                    + "\n Đổi x5000 Đá Ruby sẽ nhận 500 Đá Thạch Anh Tím"
                                                    + "\n Đổi x5000 Đá TiTan sẽ nhận 500 Đá Thạch Anh Tím"
                                                    ,
                                            "Đổi x5000\nĐá Lục Bảo",
                                            "Đổi x5000\nĐá Saphia",
                                            "Đổi x5000\nĐá Ruby",
                                            "Đổi x5000\nĐá TiTan",
                                            "Đóng");
                                    break;
                                case 7:
                                    createOtherMenu(player, ConstNpc.MENU_5,
                                            "Ngươi muốn đổi Ngọc Rồng Băng hãy chọn bên dưới!"
                                                    + "\n Đổi x100 Ngọc Rồng Thường 7 Sao sẽ nhận x1 Ngọc Rồng Băng 7 Sao"
                                                    + "\n Đổi x100 Ngọc Rồng Thường 6 Sao sẽ nhận x1 Ngọc Rồng Băng 6 Sao"
                                                    + "\n Đổi x100 Ngọc Rồng Thường 5 Sao sẽ nhận x1 Ngọc Rồng Băng 5 Sao"
                                                    + "\n Đổi x100 Ngọc Rồng Thường 4 Sao sẽ nhận x1 Ngọc Rồng Băng 4 Sao"
                                                    + "\n Đổi x100 Ngọc Rồng Thường 3 Sao sẽ nhận x1 Ngọc Rồng Băng 3 Sao"
                                                    + "\n Đổi x100 Ngọc Rồng Thường 2 Sao sẽ nhận x1 Ngọc Rồng Băng 2 Sao"
                                                    + "\n Đổi x100 Ngọc Rồng Thường 1 Sao sẽ nhận x1 Ngọc Rồng Băng 1 Sao"
                                                    ,
                                            "Đổi Ngọc\nRồng Băng\n7 Sao",
                                            "Đổi Ngọc\nRồng Băng\n6 Sao",
                                            "Đổi Ngọc\nRồng Băng\n5 Sao",
                                            "Đổi Ngọc\nRồng Băng\n4 Sao",
                                            "Đổi Ngọc\nRồng Băng\n3 Sao",
                                            "Đổi Ngọc\nRồng Băng\n2 Sao",
                                            "Đổi Ngọc\nRồng Băng\n1 Sao",
                                            "Đóng");
                                    break;
                                case 8: 
                                    createOtherMenu(player, ConstNpc.MENU_6,
                                            "Ngươi muốn đổi Công Thức Vip hãy chọn bên dưới!"
                                                    + "\n Đổi x2 Công Thức Thường Trái Đất sẽ nhận X1 Công Thức Vip Trái Đất"
                                                    + "\n Đổi x2 Công Thức Thường Namek sẽ nhận X1 Công Thức Vip Namek"
                                                    + "\n Đổi x2 Công Thức Thường Xayda sẽ nhận X1 Công Thức Vip Xayda"                                                   
                                                    ,
                                            "Đổi CT VIP\nTrái Đất",
                                            "Đổi CT VIP\nNamek",
                                            "Đổi CT VIP\nXayda",                                            
                                            "Đóng");
                                
                            }
                        }
                        
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_6) {
                            switch (select) {
                                
                                case 0: {
                                    Item HongBao = null;
                                    
                                    try {
                                        HongBao = InventoryServiceNew.gI().findItemBag(player, 1071);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HongBao == null || HongBao.quantity < 2 ) {
                                        this.npcChat(player, "Bạn không đủ 2 Công thức thường");                                                                                                        
                                    } else { 
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HongBao, 2);
                                        Item VeHongNgoc = ItemService.gI().createNewItem((short) 1084,1);
                                        InventoryServiceNew.gI().addItemBag(player, VeHongNgoc);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Công Thức VIP");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item HongBao = null;
                                    
                                    try {
                                        HongBao = InventoryServiceNew.gI().findItemBag(player, 1072);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HongBao == null || HongBao.quantity < 2 ) {
                                        this.npcChat(player, "Bạn không đủ 2 Công thức thường");                                                                                                        
                                    } else { 
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HongBao, 2);
                                        Item VeHongNgoc = ItemService.gI().createNewItem((short) 1085,1);
                                        InventoryServiceNew.gI().addItemBag(player, VeHongNgoc);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Công Thức VIP");
                                    }
                                    break;
                                }
                                case 2: {
                                    Item HongBao = null;
                                    
                                    try {
                                        HongBao = InventoryServiceNew.gI().findItemBag(player, 1073);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HongBao == null || HongBao.quantity < 2 ) {
                                        this.npcChat(player, "Bạn không đủ 2 Công thức thường");                                                                                                        
                                    } else { 
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HongBao, 2);
                                        Item VeHongNgoc = ItemService.gI().createNewItem((short) 1086,1);
                                        InventoryServiceNew.gI().addItemBag(player, VeHongNgoc);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Công Thức VIP");
                                    }
                                    break;
                                }                               
                            }
                        }
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_1) {
                            switch (select) {
                                
                                case 0: {
                                    Item HongBao = null;
                                    
                                    try {
                                        HongBao = InventoryServiceNew.gI().findItemBag(player, 1244);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HongBao == null || HongBao.quantity < 10 ) {
                                        this.npcChat(player, "Bạn không đủ 10 Hồng Bao Vàng");                                                                                                        
                                    } else {
                                        player.inventory.gold += 20_000_000_000L;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HongBao, 10);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 20 Tỷ Vàng");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item HongBao = null;
                                    
                                    try {
                                        HongBao = InventoryServiceNew.gI().findItemBag(player, 1244);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HongBao == null || HongBao.quantity < 100 ) {
                                        this.npcChat(player, "Bạn không đủ 100 Hồng Bao Vàng");                                                                                                        
                                    } else {
                                        player.inventory.gold += 200_000_000_000L;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HongBao, 100);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 200 Tỷ Vàng");
                                    }
                                    break;
                                }
                                case 2: {
                                    Item HongBao = null;
                                    
                                    try {
                                        HongBao = InventoryServiceNew.gI().findItemBag(player, 1244);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HongBao == null || HongBao.quantity < 1000 ) {
                                        this.npcChat(player, "Bạn không đủ 1000 Hồng Bao Vàng");                                                                                                        
                                    } else {
                                        player.inventory.gold += 2_000_000_000_000L;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HongBao, 1000);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 2000 Tỷ Vàng");
                                    }
                                    break;
                                }
                                case 3: {
                                    Item HongBao = null;
                                    
                                    try {
                                        HongBao = InventoryServiceNew.gI().findItemBag(player, 1244);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HongBao == null || HongBao.quantity < 5000 ) {
                                        this.npcChat(player, "Bạn không đủ 5000 Hồng Bao Vàng");                                                                                                        
                                    } else {
                                        player.inventory.gold += 10_000_000_000_000L;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HongBao, 5000);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 10000 Tỷ Vàng");
                                    }
                                    break;
                                }
                                case 4: {
                                    Item HongBao = null;
                                    
                                    try {
                                        HongBao = InventoryServiceNew.gI().findItemBag(player, 1244);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HongBao == null || HongBao.quantity < 200000 ) {
                                        this.npcChat(player, "Bạn không đủ 200000 Hồng Bao Vàng");                                                                                                        
                                    } else {
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HongBao, 200000);
                                        Item VeHongNgoc = ItemService.gI().createNewItem((short) 1132,1000);
                                        InventoryServiceNew.gI().addItemBag(player, VeHongNgoc);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1000 Vé Hồng Ngọc");
                                    }
                                    break;
                                }
                            }
                        }
                        
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_2) {
                            switch (select) {
                                
                                case 0: {
                                    Item CapsuleHong = null;
                                    
                                    try {
                                        CapsuleHong = InventoryServiceNew.gI().findItemBag(player, 722);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (CapsuleHong == null || CapsuleHong.quantity < 1000 ) {
                                        this.npcChat(player, "Bạn không đủ 1000 Capsule Hồng");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, CapsuleHong, 1000);
                                        Item VeHongNgoc = ItemService.gI().createNewItem((short) 1132,5);
                                        InventoryServiceNew.gI().addItemBag(player, VeHongNgoc);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 5 Vé Hồng Ngọc");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item CapsuleHong = null;
                                    
                                    try {
                                        CapsuleHong = InventoryServiceNew.gI().findItemBag(player, 722);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (CapsuleHong == null || CapsuleHong.quantity < 10000 ) {
                                        this.npcChat(player, "Bạn không đủ 10000 Capsule Hồng");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, CapsuleHong, 10000);
                                        Item VeHongNgoc = ItemService.gI().createNewItem((short) 1132,50);
                                        InventoryServiceNew.gI().addItemBag(player, VeHongNgoc);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 50 Vé Hồng Ngọc");
                                    }
                                    break;
                                }                               
                            }
                        }
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_3) {
                            switch (select) {
                                
                                case 0: {
                                    Item HopPS = null;
                                    
                                    try {
                                        HopPS = InventoryServiceNew.gI().findItemBag(player, 2048);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HopPS == null || HopPS.quantity < 500 ) {
                                        this.npcChat(player, "Bạn không đủ 500 Hộp Pháp Sư");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HopPS, 500);
                                        Item HopPSCC = ItemService.gI().createNewItem((short) 2049,1);
                                        InventoryServiceNew.gI().addItemBag(player, HopPSCC);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Hộp Pháp Sư Cao Cấp");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item HopPS = null;
                                    
                                    try {
                                        HopPS = InventoryServiceNew.gI().findItemBag(player, 2048);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HopPS == null || HopPS.quantity < 5000 ) {
                                        this.npcChat(player, "Bạn không đủ 5000 Hộp Pháp Sư");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HopPS, 5000);
                                        Item HopPSCC = ItemService.gI().createNewItem((short) 2049,10);
                                        InventoryServiceNew.gI().addItemBag(player, HopPSCC);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 10 Hộp Pháp Sư Cao Cấp");
                                    }
                                    break;
                                }
                                case 2: {
                                    Item HopPS = null;
                                    
                                    try {
                                        HopPS = InventoryServiceNew.gI().findItemBag(player, 2048);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (HopPS == null || HopPS.quantity < 50000 ) {
                                        this.npcChat(player, "Bạn không đủ 50000 Hộp Pháp Sư");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, HopPS, 50000);
                                        Item HopPSCC = ItemService.gI().createNewItem((short) 2049,100);
                                        InventoryServiceNew.gI().addItemBag(player, HopPSCC);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 100 Hộp Pháp Sư Cao Cấp");
                                    }
                                    break;
                                } 
                            }
                        }
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_4) {
                            switch (select) {
                                
                                case 0: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 220);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 5000 ) {
                                        this.npcChat(player, "Bạn không đủ 5000 Đá Lục Bảo");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 5000);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 224,500);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 500 Đá Thạch Anh Tím");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 221);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 5000 ) {
                                        this.npcChat(player, "Bạn không đủ 5000 Đá Saphia");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 5000);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 224,500);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 500 Đá Thạch Anh Tím");
                                    }
                                    break;
                                }
                                case 2: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 222);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 5000 ) {
                                        this.npcChat(player, "Bạn không đủ 5000 Đá RuBy");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 5000);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 224,500);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 500 Đá Thạch Anh Tím");
                                    }
                                    break;
                                }
                                case 3: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 223);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 5000 ) {
                                        this.npcChat(player, "Bạn không đủ 5000 Đá TiTan");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 5000);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 224,500);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 500 Đá Thạch Anh Tím");
                                    }
                                    break;
                                } 
                            }
                        }
                        
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_5) {
                            switch (select) {
                                
                                case 0: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 20);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 100 ) {
                                        this.npcChat(player, "Bạn không đủ 100 Ngọc Rồng Thường 7 Sao");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 100);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 931,1);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Ngọc Rồng Băng 7 Sao");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 19);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 100 ) {
                                        this.npcChat(player, "Bạn không đủ 100 Ngọc Rồng Thường 6 Sao");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 100);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 930,1);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Ngọc Rồng Băng 6 Sao");
                                    }
                                    break;
                                }
                                case 2: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 18);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 100 ) {
                                        this.npcChat(player, "Bạn không đủ 100 Ngọc Rồng Thường 5 Sao");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 100);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 929,1);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Ngọc Rồng Băng 5 Sao");
                                    }
                                    break;
                                }
                                case 3: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 17);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 100 ) {
                                        this.npcChat(player, "Bạn không đủ 100 Ngọc Rồng Thường 4 Sao");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 100);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 928,1);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Ngọc Rồng Băng 4 Sao");
                                    }
                                    break;
                                }
                                case 4: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 16);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 100 ) {
                                        this.npcChat(player, "Bạn không đủ 100 Ngọc Rồng Thường 3 Sao");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 100);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 927,1);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Ngọc Rồng Băng 3 Sao");
                                    }
                                    break;
                                }
                                case 5: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 15);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 100 ) {
                                        this.npcChat(player, "Bạn không đủ 100 Ngọc Rồng Thường 2 Sao");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 100);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 926,1);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Ngọc Rồng Băng 2 Sao");
                                    }
                                    break;
                                }
                                case 6: {
                                    Item DaDoi = null;
                                    
                                    try {
                                        DaDoi = InventoryServiceNew.gI().findItemBag(player, 14);                                        
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (DaDoi == null || DaDoi.quantity < 100 ) {
                                        this.npcChat(player, "Bạn không đủ 100 Ngọc Rồng Thường 1 Sao");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DaDoi, 100);
                                        Item DaTAT = ItemService.gI().createNewItem((short) 925,1);
                                        InventoryServiceNew.gI().addItemBag(player, DaTAT);
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Ngọc Rồng Băng 1 Sao");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc poTaGe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Tâm con bấn loạn \b|7|Con muốn diệt trừ tâm ma của mình \b|1|Với giá 20K hồng ngọc không?", "Gọi Boss\nTâm Ma", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Boss oldBossClone = BossManager.gI().getBossById(Util.createIdBossClone((int) player.id));
                                    if (oldBossClone != null) {
                                        this.npcChat(player, "Nhà ngươi hãy tiêu diệt Boss lúc trước gọi ra đã, con boss đó đang ở khu " + oldBossClone.zone.zoneId);
                                    } else if (player.inventory.ruby < 20000) {
                                        this.npcChat(player, "Nhà ngươi không đủ 20.000 hồng ngọc ");
                                    } else {
                                        List<Skill> skillList = new ArrayList<>();
                                        for (byte i = 0; i < player.playerSkill.skills.size(); i++) {
                                            Skill skill = player.playerSkill.skills.get(i);
                                            if (skill.point > 0) {
                                                skillList.add(skill);
                                            }
                                        }
                                        int[][] skillTemp = new int[skillList.size()][3];
                                        for (byte i = 0; i < skillList.size(); i++) {
                                            Skill skill = skillList.get(i);
                                            if (skill.point > 0) {
                                                skillTemp[i][0] = skill.template.id;
                                                skillTemp[i][1] = skill.point;
                                                skillTemp[i][2] = skill.coolDown;
                                            }
                                        }
                                        BossData bossDataClone = new BossData(
                                                "Tâm Ma" + player.name,
                                                player.gender,
                                                new short[]{player.getHead(), player.getBody(), player.getLeg(), player.getFlagBag(), player.getAura(), player.getEffFront()},
                                                player.nPoint.dame/10L,
                                                new long[]{player.nPoint.hpMax*500L},
                                                new int[]{140},
                                                skillTemp,
                                                new String[]{"|-2|Boss Tâm Ma đã xuất hiện rồi"}, //text chat 1
                                                new String[]{"|-1|Ta sẽ chiếm lấy thân xác của ngươi hahaha!"}, //text chat 2
                                                new String[]{"|-1|Lần khác ta sẽ xử đẹp ngươi"}, //text chat 3
                                                60
                                        );

                                        try {
                                            new NhanBan(Util.createIdBossClone((int) player.id), bossDataClone, player.zone);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //trừ vàng khi gọi boss
                                        player.inventory.ruby -= 20000;
                                        Service.getInstance().sendMoney(player);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc quyLaoKame(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (player.getSession().is_gift_box) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chào con, con muốn ta giúp gì nào?\n|7|Đổi x1 Cỏ Sức Mạnh sẽ nhận được 10 tỷ sức mạnh", "Hút Cỏ", "Giải tán bang hội", "Top Nạp","Chuyển Sinh");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chào con, con muốn ta giúp gì nào?", "Hút Cỏ", "Giải tán bang hội","Bản đồ\nkho báu","Top SK\nNăng Động","Top\n Săn Boss");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0 :
                                
                                    Item cosm = null;
                                    try {
                                        cosm = InventoryServiceNew.gI().findItemBag(player, 1245);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (cosm == null || cosm.quantity < 1) {
                                        this.npcChat(player, "Bạn không đủ Cỏ Sức Mạnh");
                                    
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, cosm, 1);
                                        Service.getInstance().sendMoney(player);
                                        player.nPoint.power+=10000000000L;                                       
                                        player.nPoint.tiemNang+=10000000000L;
                                        this.npcChat(player, "Bạn nhận 10 tỷ tiềm năng + sức mạnh");
                                    }
                                    break;
                                                                 

                            case 1:
                                Clan clan = player.clan;
                                if (clan != null) {
                                    ClanMember cm = clan.getClanMember((int) player.id);
                                    if (cm != null) {
                                        if (clan.members.size() > 1) {
                                            Service.getInstance().sendThongBao(player, "Bang phải còn một người");
                                            break;
                                        }
                                        if (!clan.isLeader(player)) {
                                            Service.getInstance().sendThongBao(player, "Phải là bảng chủ");
                                            break;
                                        }
                                        
                                        NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DISSOLUTION_CLAN, -1, "Con có chắc chắn muốn giải tán bang hội không? Ta cho con 2 lựa chọn...",
                                                "Yes you do!", "Từ chối!");
                                    }
                                    break;
                                }
                                Service.getInstance().sendThongBao(player, "Có bang hội đâu ba!!!");
                                break;
                            case 2:
                                if (player.clan != null) {
                                    if (player.clan.bdkb != null) {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPENED_BDKB,
                                                "Bang hội của con đang đi vào hang cấp độ "
                                                        + player.clan.bdkb.level + "\nCon có muốn đi theo không?",
                                                "Đồng ý", "Từ chối");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPEN_BDKB,
                                                "Hang động có rất nhiều kho báu đang chờ các cậu đến tìm kiếm.\n"
                                                        + "Hãy đến và màng nó về cho ta.\n"
                                                        + "Ở đây có ta lo\nNhớ chọn cấp độ vừa sức mình nhé",
                                                "Chọn\ncấp độ", "Từ chối");
                                    }
                                } else {
                                    this.npcChat(player, "Con phải có bang hội ta mới có thể cho con đi");
                                }
                                break;
                                
                            case 3:

                                break;
                            case 4:

                                    break;

                            }
                      
                        
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPENED_BDKB) {
                        switch (select) {
                            case 0:
                                    player.type = 5;
                                    ChangeMapService.gI().goToBdkb(player);
                             
                                break;

                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPEN_BDKB) {
                        switch (select) {
                            case 0:

                                    Input.gI().createFormChooseLevelBDKB(player);
                                
                                break;
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_ACCEPT_GO_TO_BDKB) {
                        switch (select) {
                            case 0:
                                player.type = 5;
                                BdkbService.gI().openBanDoKhoBau(player, Integer.parseInt(String.valueOf(PLAYERID_OBJECT.get(player.id))));
                                break;
                        }
                    } 


                }
            }
        };
    }

    public static Npc truongLaoGuru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc vuaVegeta(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc ongGohan_ongMoori_ongParagus(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Cố Gắng Có Làm Mới Có Ăn Con, đừng lo lắng cho ta.\n"
                                        .replaceAll("%1", player.gender == ConstPlayer.TRAI_DAT ? "Quy lão Kamê"
                                                : player.gender == ConstPlayer.NAMEC ? "Trưởng lão Guru" : "Vua Vegeta") + "Đừng quên hàng ngày gặp ta Điểm Danh để nhận \n50K Hồng Ngọc và 5 tỷ vàng nhé!",
                                "Đổi Mật Khẩu", "Nhận 5K ngọc xanh", "Nhận đệ tử", "Nhận\nVàng", "Giftcode","Điểm danh\nhàng ngày");

                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                Input.gI().createFormChangePassword(player);
                                break;
                            case 1:
                                if (player.inventory.gem >= 5000) {
                                    this.npcChat(player, "Con nhiều ngọc quá rồi!!!");
                                    break;
                                }
                                player.inventory.gem += 5000;
                                Service.getInstance().sendMoney(player);
                                Service.getInstance().sendThongBao(player, "Con vừa nhận được 5K ngọc xanh");
                                break;
                            case 2:
                                if (player.pet == null) {
                                    PetService.gI().createNormalPet(player);
                                    Service.getInstance().sendThongBao(player, "Con vừa nhận được đệ tử! Hãy chăm sóc nó nhé");
                                } else {
                                    this.npcChat(player, "Đã có đệ tử rồi mà!");
                                   
                                }
                                break;
                            case 3:
                                if (Maintenance.isRuning) {
                                    break;
                                }
                                if (player.getSession().goldBar > 0) {
                                    if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                        int quantity = player.getSession().goldBar;
                                        if (PlayerDAO.subGoldBar(player, player.getSession().goldBar)) {
                                            Item goldBar = ItemService.gI().createNewItem((short) 457, quantity);
                                            InventoryServiceNew.gI().addItemBag(player, goldBar);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            this.npcChat(player, "Ta đã gửi " + quantity + " thỏi vàng vào hành trang của con\n con hãy kiểm tra ");
                                        } else {
                                            this.npcChat(player, "Lỗi vui lòng báo admin...");
                                        }
                                    } else {
                                        this.npcChat(player, "Hãy chừa cho ta 1 ô trống");
                                    }
                                } else {
                                    this.npcChat(player, "Ta nghèo lắm lấy đâu ra vàng cho con");
                                }
                                break;

                            case 4:
                                Input.gI().createFormGiftCode(player);
                                break;
                                
                            case 5:
                            { 
                                 LocalDate now = LocalDate.now();
                                // Chuyển đổi giá trị mili giây thành LocalDate
                                LocalDate lastDate = LocalDate.ofEpochDay(player.last_time_dd / (24 * 60 * 60 * 1000));
                              long  caichogi = now.toEpochDay() * (24 * 60 * 60 * 1000);                               
                                boolean isAttended = now.isEqual(lastDate);                               
                                if (!isAttended && caichogi!= player.last_time_dd) {
                                    System.out.println(caichogi);
                                    System.out.println(player.last_time_dd);
                                    player.inventory.ruby += 50000;
                                    player.inventory.gold += 5000000000L;
                                    Service.getInstance().sendMoney(player);
                                    this.npcChat(player, "Điểm danh thành công nhận 50k hồng ngọc và 5 tỷ vàng");
                                    player.last_time_dd = now.toEpochDay() * (24 * 60 * 60 * 1000);
                                } else {
                                    this.npcChat(player, "Hôm nay con đã điểm danh rồi");
                                }                              
                            }
                                break;
//                            case 5:
//                                if (player.pet != null) {
//                                    int gender = player.pet.gender;
//                                    PetService.gI().changeXencon(player, gender);
//                                    break;}                           
                        }
                    }
                } else if (player.iDMark.getIndexMenu() == ConstNpc.QUA_TAN_THU) {
                    switch (select) {
                        case 0:
//                                        if (!player.gift.gemTanThu) {
                            if (true) {
                                player.inventory.gem = 100000;
                                Service.getInstance().sendMoney(player);
                                Service.getInstance().sendThongBao(player, "Bạn vừa nhận được 100K ngọc xanh");
                                player.gift.gemTanThu = true;
                            } else {
                                this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con đã nhận phần quà này rồi mà",
                                        "Đóng");
                            }
                            break;
//                            case 1:
//                                if (nhanVang) {
//                                    player.inventory.gold = Inventory.LIMIT_GOLD;
//                                    Service.getInstance().sendMoney(player);
//                                    Service.getInstance().sendThongBao(player, "Bạn vừa nhận được 2 tỉ vàng");
//                                } else {
//                                    this.npcChat("");
//                                }
//                                break;
                        case 1:
                            if (nhanDeTu) {
                                if (player.pet == null) {
                                    PetService.gI().createNormalPet(player);
                                    Service.getInstance().sendThongBao(player, "Bạn vừa nhận được đệ tử");
                                } else {
                                    this.npcChat("Con đã nhận đệ tử rồi");
                                }
                            }
                            break;
                    }
                } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHAN_THUONG) {
                    switch (select) {
                        case 0:
                            ShopServiceNew.gI().opendShop(player, "ITEMS_REWARD", true);
                            break;
//                            case 1:
//                                if (player.getSession().goldBar > 0) {
//                                    if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
//                                        int quantity = player.getSession().goldBar;
//                                        Item goldBar = ItemService.gI().createNewItem((short) 457, quantity);
//                                        InventoryServiceNew.gI().addItemBag(player, goldBar);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        this.npcChat(player, "Ông đã để " + quantity + " thỏi vàng vào hành trang con rồi đấy");
//                                        PlayerDAO.subGoldBar(player, quantity);
//                                        player.getSession().goldBar = 0;
//                                    } else {
//                                        this.npcChat(player, "Con phải có ít nhất 1 ô trống trong hành trang ông mới đưa cho con được");
//                                    }
//                                }
//                                break;
                    }
                } else if (player.iDMark.getIndexMenu() == ConstNpc.NAP_THE) {
                    Input.gI().createFormNapThe(player, (byte) select);
                }
            }
        };
    }

    public static Npc bulmaQK(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Cậu cần trang bị gì cứ đến chỗ tôi nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                if (player.gender == ConstPlayer.TRAI_DAT) {
                                    ShopServiceNew.gI().opendShop(player, "BUNMA", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Xin lỗi cưng, chị chỉ bán đồ cho người Trái Đất", "Đóng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc dende(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        
                                            int nPlSameClan1 = 0;
                                        for (Player pl : player.zone.getPlayers()) {
                                            if (!pl.equals(player) && pl.clan != null
                                                    && pl.clan.equals(player.clan) && pl.location.x >= 53
                                                    && pl.location.x <= 1188 && pl.idNRNM != -1) {
                                                    nPlSameClan1++;
                                            }
                                        }
                                        if (player.zone.map.mapId == 7 && nPlSameClan1 < 6 && player.idNRNM != -1) {
                                            createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                    "Ngươi phải có ít nhất đồng đội cùng bang đứng gần mới có thể\nvào\n"
                                                    + "tuy nhiên ta khuyên ngươi nên đi cùng với 7 người để khỏi chết.\n"
                                                    + "Hahaha.", "OK", "Hướng\ndẫn\nthêm");
                                            return;
                                        } 
                                        else if (player.zone.map.mapId == 7 && nPlSameClan1 >= 6 && player.idNRNM != -1 && player.idNRNM == 353) {

                                            this.createOtherMenu(player, 1, "Ồ, ngọc rồng namếc, bạn thật là may mắn\nnếu tìm đủ 7 viên sẽ được Rồng Thiêng Namếc ban cho điều ước", "Hướng\ndẫn\nGọi Rồng", "Gọi rồng", "Từ chối");
                                         }
//                        if (player.idNRNM != -1) {
//                            if (player.zone.map.mapId == 7) {
//                                this.createOtherMenu(player, 1, "Ồ, ngọc rồng namếc, bạn thật là may mắn\nnếu tìm đủ 7 viên sẽ được Rồng Thiêng Namếc ban cho điều ước", "Hướng\ndẫn\nGọi Rồng", "Gọi rồng", "Từ chối");
//                            }
//                        } 
                        else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Anh cần trang bị gì cứ đến chỗ em nhé", "Cửa\nhàng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                if (player.gender == ConstPlayer.NAMEC) {
                                    ShopServiceNew.gI().opendShop(player, "DENDE", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Xin lỗi anh, em chỉ bán đồ cho dân tộc Namếc", "Đóng");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == 1) {
                        if (player.zone.map.mapId == 7 && player.idNRNM != -1) {
                            if (player.idNRNM == 353) {
                                NgocRongNamecService.gI().firstNrNamec = true;
                                NgocRongNamecService.gI().timeNrNamec = 0;
                                NgocRongNamecService.gI().doneDragonNamec();
                                NgocRongNamecService.gI().initNgocRongNamec((byte) 1);
                                NgocRongNamecService.gI().reInitNrNamec((long) TIME_OP);
                                SummonDragon.gI().summonNamec(player);
                            } else {
                                Service.getInstance().sendThongBao(player, "Anh phải có viên ngọc rồng Namếc 1 sao");
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc appule(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi cần trang bị gì cứ đến chỗ ta nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                if (player.gender == ConstPlayer.XAYDA) {
                                    ShopServiceNew.gI().opendShop(player, "APPULE", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Về hành tinh hạ đẳng của ngươi mà mua đồ cùi nhé. Tại đây ta chỉ bán đồ cho người Xayda thôi", "Đóng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc drDrief(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.mapId == 84) {
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                pl.gender == ConstPlayer.TRAI_DAT ? "Đến\nTrái Đất" : pl.gender == ConstPlayer.NAMEC ? "Đến\nNamếc" : "Đến\nXayda");
                    } else if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                    "Đến\nNamếc", "Đến\nXayda", "Siêu thị");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 84) {
                        ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 24, -1, -1);
                    } else if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cargo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                    "Đến\nTrái Đất", "Đến\nXayda", "Siêu thị");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_FIND_BOSS = 50000000;

            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            if (this.mapId == 19) {

                                int taskId = TaskService.gI().getIdTask(pl);
                                switch (taskId) {
                                    case ConstTask.TASK_19_0:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_KUKU,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nKuku\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)", "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    case ConstTask.TASK_19_1:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_MAP_DAU_DINH,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nMập đầu đinh\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)", "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    case ConstTask.TASK_19_2:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_RAMBO,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nRambo\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)", "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    default:
                                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến Cold", "Đến\nNappa", "Từ chối");

                                        break;
                                }
                            } else if (this.mapId == 68) {
                                this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                        "Ngươi muốn về Thành Phố Vegeta", "Đồng ý", "Từ chối");
                            } else {
                                this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                        "Tàu vũ trụ Xayda sử dụng công nghệ mới nhất, "
                                        + "có thể đưa ngươi đi bất kỳ đâu, chỉ cần trả tiền là được.",
                                        "Đến\nTrái Đất", "Đến\nNamếc", "Siêu thị");
                            }
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 26) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 19) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_KUKU) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.KUKU);
                                    if (boss != null && !boss.isDie()) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
                                            if (z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                                                Service.getInstance().sendMoney(player);
                                            } else {
                                                Service.getInstance().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold) + " vàng");
                                        }
                                        break;
                                    }
                                    Service.getInstance().sendThongBao(player, "Chết rồi ba...");
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_MAP_DAU_DINH) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.MAP_DAU_DINH);
                                    if (boss != null && !boss.isDie()) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
                                            if (z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                                                Service.getInstance().sendMoney(player);
                                            } else {
                                                Service.getInstance().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold) + " vàng");
                                        }
                                        break;
                                    }
                                    Service.getInstance().sendThongBao(player, "Chết rồi ba...");
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_RAMBO) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.RAMBO);
                                    if (boss != null && !boss.isDie()) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
                                            if (z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                                                Service.getInstance().sendMoney(player);
                                            } else {
                                                Service.getInstance().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold) + " vàng");
                                        }
                                        break;
                                    }
                                    Service.getInstance().sendThongBao(player, "Chết rồi ba...");
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 68) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 19, -1, 1100);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc santa(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, ta có một số vật phẩm đặt biệt cậu có muốn xem không?",
                            "Cửa hàng", "Shop\nThử Đồ","Sự kiện","Hớt tóc");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5 || this.mapId == 13 || this.mapId == 20) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop
                                    ShopServiceNew.gI().opendShop(player, "SANTA", false);
                                    break;
                                case 1: //tiệm hồng ngọc
                                    ShopServiceNew.gI().opendShop(player, "SANTA_RUBY", false);
                                    break;
                                case 2:
                            //        if (player.getSession().actived) {
                                    ShopServiceNew.gI().opendShop(player, "SANTA_EVENT", false);
                            //        } 
                            //        else {
                            //            Service.getInstance().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                            //        }
                                    break;

                                case 3: //tiệm hớt tóc
                                    ShopServiceNew.gI().opendShop(player, "SANTA_HEAD", false);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc uron(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    ShopServiceNew.gI().opendShop(pl, "URON", false);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc baHatMit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Ép sao\ntrang bị", "Pha lê\nhóa\ntrang bị", "Pháp Sư Hoá\nTrang Bị", "Tẩy Pháp Sư", "Ấn trang\nbị","Đổi Đá\nNgũ Sắc","Ép SKH\n Trang Bị","Tiến Hóa\n Cải Trang\n","Nâng cấp\nNgọc Bội","Nâng cấp\nTrang Bị");
                    } else if (this.mapId == 121) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Về đảo\nrùa");

                    } else {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Cửa hàng\nBùa", "Nâng cấp\nVật phẩm",
                                "Nâng cấp\nBông tai\nPorata", "Mở chỉ số\nBông tai",
                                "Nhập\nNgọc Rồng", "Phân Rã\nĐồ Thần Linh", "Nâng Cấp \nĐồ Thiên Sứ");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
//                                                CombineService.gI().openTabCombine(player, CombineService.EP_SAO_TRANG_BI);
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.EP_SAO_TRANG_BI);
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHA_LE_HOA_TRANG_BI);
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PS_HOA_TRANG_BI);
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TAY_PS_HOA_TRANG_BI);
                                    break;
                                case 4:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.AN_TRANG_BI);
                                    break;
                                case 5:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DOI_SKH_THANH_DNS );
                                    break;
                                case 6:
                                    createOtherMenu(player, ConstNpc.MENU_EP_SKH,
                                            "Ngươi muốn Ép SKH cho loại trang bị nào hãy chọn bên dưới!",
                                            "Trang Bị\nThần", "Trang Bị\nHủy Diệt", "Trang Bị\nThiên Sứ", "Tẩy SKH\nTrang Bị", "Đóng");
                                    break;
                                case 7:
                                    createOtherMenu(player, ConstNpc.MENU_CAI_TRANG,
                                            "Ngươi muốn Tiến Hóa Cải Trang nào hãy chọn bên dưới!",
                                            "Nâng Cấp\n Cải Trang","Cải Trang V2\nBaby Vegeta", "Cải Trang V3\nBlack Goku","Cải Trang V4\nBill","Cải Trang V5\nHearts Gold", "Đóng");
                                    break;
                                    
                                case 8:
                                    createOtherMenu(player, ConstNpc.MENU_NGOC_BOI,
                                            "Ngươi muốn Điêu Khắc Chỉ Số Đá hay \n Người muốn Điêu Khắc Chỉ Số Ngọc Bội hãy chọn bên dưới!",
                                            "Điêu Khắc\nChỉ Số\nVào Đá", "Điêu Khắc\nChỉ Số\nNgọc Bội","Tiến Hóa\nNgọc Bội", "Đóng");
                                    break;
                                case 9:
                                    createOtherMenu(player, ConstNpc.MENU_TRANG_BI,
                                            "Ngươi muốn Tăng Sức Mạnh Trang Bị hãy chọn bên dưới!",
                                            "Tinh Luyện\n Trang Bị", "Rèn\nTrang Bị", "Đóng");
                                    break;
                            }                           
                        }
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_CAI_TRANG) {
                            switch (select) {
                                case 0: 
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_CAI_TRANG_SSJ);
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TIEN_HOA_CAI_TRANG_BABY_VEGETA);
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TIEN_HOA_CAI_TRANG_BLACK_GOKU);
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TIEN_HOA_CAI_TRANG_BILL);
                                    break;
                                case 4:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TIEN_HOA_CAI_TRANG_HEARTS_GOLD);
                                    break;

                            }
                        }
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_EP_SKH) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DAP_SET_KICH_HOAT );
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DAP_SET_KICH_HOAT_HD );
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DAP_SET_KICH_HOAT_TS );
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TAY_SKH_TRANG_BI );
                                    break;
                            }
                        }
                        
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NGOC_BOI) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.KHAC_CHI_SO_DA );
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.KHAC_CHI_SO_NGOC_BOI );
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TIEN_HOA_NGOC_BOI );
                                    break;
                            }
                        }
                        
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRANG_BI) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TINH_LUYEN_TRANG_BI );
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.REN_TRANG_BI );
                                    break;

                            }
                        }
                        
                        
                        
                        
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.EP_SAO_TRANG_BI:
                                case CombineServiceNew.PHA_LE_HOA_TRANG_BI:
                                case CombineServiceNew.CHUYEN_HOA_TRANG_BI:
                                case CombineServiceNew.PS_HOA_TRANG_BI:
                                case CombineServiceNew.AN_TRANG_BI:
                                case CombineServiceNew.TAY_PS_HOA_TRANG_BI:
                                case CombineServiceNew.NANG_CAP_CAI_TRANG_SSJ:
                                case CombineServiceNew.DOI_SKH_THANH_DNS:
                                case CombineServiceNew.DAP_SET_KICH_HOAT:
                                case CombineServiceNew.DAP_SET_KICH_HOAT_HD:
                                case CombineServiceNew.DAP_SET_KICH_HOAT_TS:
                                case CombineServiceNew.TIEN_HOA_CAI_TRANG_BABY_VEGETA:
                                case CombineServiceNew.TAY_SKH_TRANG_BI:
                                case CombineServiceNew.KHAC_CHI_SO_DA:
                                case CombineServiceNew.KHAC_CHI_SO_NGOC_BOI:
                                case CombineServiceNew.TIEN_HOA_NGOC_BOI:
                                case CombineServiceNew.TINH_LUYEN_TRANG_BI:
                                case CombineServiceNew.REN_TRANG_BI:
                                case CombineServiceNew.TIEN_HOA_CAI_TRANG_BLACK_GOKU:
                                case CombineServiceNew.TIEN_HOA_CAI_TRANG_BILL:
                                case CombineServiceNew.TIEN_HOA_CAI_TRANG_HEARTS_GOLD:
                                   
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    } else if (select == 1) {
                                        CombineServiceNew.gI().startCombine1(player);
                                    } else if (select == 2) {
                                        CombineServiceNew.gI().startCombine2(player);
                                    }
                                    break;
                            }
                        }
                    } else if (this.mapId == 112) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 1156);
                                    break;
                            }
                        }
                    } else if (this.mapId == 42 || this.mapId == 43 || this.mapId == 44 || this.mapId == 84) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop bùa
                                    createOtherMenu(player, ConstNpc.MENU_OPTION_SHOP_BUA,
                                            "Bùa của ta rất lợi hại, nhìn ngươi yếu đuối thế này, chắc muốn mua bùa để "
                                            + "mạnh mẽ à, mua không ta bán cho, xài rồi lại thích cho mà xem.",
                                            "Bùa\n1 giờ", "Bùa\n8 giờ", "Bùa\n1 tháng", "Đóng");
                                    break;
                                case 1:
//                                                CombineService.gI().openTabCombine(player, CombineService.NANG_CAP_TRANG_BI);
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_VAT_PHAM);
                                    break;
                                case 2: //nâng cấp bông tai
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI);
                                    break;
                                case 3: //Mở chỉ số bông tai
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BONG_TAI);
                                    break;
                                case 4:
//                                                CombineService.gI().openTabCombine(player, CombineService.NHAP_NGOC_RONG);
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NHAP_NGOC_RONG);
                                    break;
                                case 5: //phân rã đồ thần linh
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHAN_RA_DO_THAN_LINH);
                                    break;
                                case 6:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_DO_TS);
                                    break;

                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_SHOP_BUA) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1H", true);
                                    break;
                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "BUA_8H", true);
                                    break;
                                case 2:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1M", true);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.NANG_CAP_VAT_PHAM:
                                case CombineServiceNew.NANG_CAP_BONG_TAI:
                                case CombineServiceNew.MO_CHI_SO_BONG_TAI:
                                case CombineServiceNew.LAM_PHEP_NHAP_DA:
                                case CombineServiceNew.NHAP_NGOC_RONG:
                                case CombineServiceNew.PHAN_RA_DO_THAN_LINH:
                                case CombineServiceNew.NANG_CAP_DO_TS:
                                case CombineServiceNew.NANG_CAP_SKH_VIP:
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    } else if (select == 1) {
                                        CombineServiceNew.gI().startCombine1(player);
                                    } else if (select == 2) {
                                        CombineServiceNew.gI().startCombine2(player);
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHAN_RA_DO_THAN_LINH) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_CAP_DO_TS) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc ruongDo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    InventoryServiceNew.gI().sendItemBox(player);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc duongtank(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    BlackBallWar.gI().setTime();
                    if (mapId == 0 || mapId == 5) {
                        this.createOtherMenu(player, 0, "Ngũ Hàng Sơn nhiều hoa quả lắm! \nBạn có muốn qua không", "OK", "Không");
                    }
                    if (mapId == 123) {
                        this.createOtherMenu(player, 0, "Bạn Muốn Quay Trở Lại Làng Aru?", "OK", "Từ chối");

                    }
                    if (mapId == 122) {
                        this.createOtherMenu(player, 0, "Xia xia thua phùa\b|7|Thí chủ đang có: " + player.NguHanhSonPoint + " điểm ngũ hành sơn\b|1|Thí chủ muốn đổi cải trang ko?", "OK", "Top Ngu Hanh Son", "No");
                    }
                    if (mapId == 92) {
                        try {
                            long now = System.currentTimeMillis();
                            if (now > BlackBallWar.TIME_OPEN_DuongTank && now < BlackBallWar.TIME_CLOSE_DuongTank) {
                                this.createOtherMenu(player, ConstNpc.MENU_OPEN_BDW, "Hộ tống ta đi tới Võ Đài Xên, "
                                        + "Ta sẽ tặng ngươi món quà?"
                                        , "Hộ Tống", "Từ chối");
                            }else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_BDW,
                                            "Hộ Tống ta vào khung giờ 20h - 20h20 hàng ngày!\n Ngươi muốn đi sớm hơn hãy đưa ta Lệnh Bài Thông Hành", "Hộ Tống\nLệnh Bài", "Từ chối");
                                }} catch (Exception ex) {
                            Logger.error("Lỗi mở menu rồng Omega");
                        }
//                        this.createOtherMenu(player, 0, "Hộ Tống ta tới Võ Đài Xên\n Ta sẽ tặng ngươi món quà?", "OK", "No");
                    }
                }
            }
//{
                                                
                                    
                                    
            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_REWARD_BDW:
                            player.rewardBlackBall.getRewardSelect((byte) select);
                            break;
                        case ConstNpc.MENU_OPEN_BDW:
                            if (select == 0) {                   
                            if (mapId == 92) {                                
                                Boss oldDuongTank = BossManager.gI().getBossById(Util.createIdDuongTank((int) player.id));
                                
                                if (oldDuongTank != null) {
                                    this.npcChat(player, "Nhà người hãy hộ tống Đường Tăng cũ đi đã " + oldDuongTank.zone.zoneId);
                                } else if (player.inventory.ruby < 20000) {
                                    this.npcChat(player, "Nhà ngươi không đủ 20K Hồng Ngọc ");
                                } else {
//                                    Item honLinhThu = null;
//                                    try {
//                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 695);
//                                    } catch (Exception e) {
////                                        throw new RuntimeException(e);
//                                    }
//                                    if (honLinhThu == null || honLinhThu.quantity < 1) {
//                                        this.npcChat(player, "Bạn không đủ vật phẩm");                                                                                                        
//                                    } else {
//                                        
//                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 1);               
//                                        Service.getInstance().sendMoney(player);
//                                        InventoryServiceNew.gI().sendItemBags(player);
                                    
                                    BossData bossDataClone = new BossData(
                                            "Đường Tank" +" "+ player.name,
                                            (byte) 0,
                                            new short[]{467, 468, 469, -1, -1, -1},
                                            100000,
                                            new long[]{player.nPoint.hpMax * 2},
                                            new int[]{103},
                                            new int[][]{
                                            {Skill.TAI_TAO_NANG_LUONG, 7, 15000}},
                                            new String[]{}, //text chat 1
                                            new String[]{}, //text chat 2
                                            new String[]{}, //text chat 3
                                            60
                                    );

                                    try {
                                        DuongTank dt = new DuongTank(Util.createIdDuongTank((int) player.id), bossDataClone, player.zone, player.location.x - 20, player.location.y);
                                        dt.playerTarger = player;
                                        int[] map = {103};
                                        dt.mapCongDuc = map[Util.nextInt(map.length)];
                                        player.haveDuongTang = true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //trừ vàng khi gọi boss
                                    player.inventory.ruby -= 20000;
                                    Service.getInstance().sendMoney(player);
                                }
//                            }
                                break;
                            }}
                            
                             case ConstNpc.MENU_NOT_OPEN_BDW:
                            if (select == 0) {
                                Boss oldDuongTank = BossManager.gI().getBossById(Util.createIdDuongTank((int) player.id));
                                
                                if (oldDuongTank != null) {
                                    this.npcChat(player, "Nhà người hãy hộ tống Đường Tăng cũ đi đã " + oldDuongTank.zone.zoneId);
                                } else {
                                    Item Vethonghanh = null;
                                    try {
                                        Vethonghanh = InventoryServiceNew.gI().findItemBag(player, 1246); //Vé Thông Hành
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (Vethonghanh == null || Vethonghanh.quantity < 1) {
                                        this.npcChat(player, "Ngươi muốn đi sớm hơn hãy đưa ta Lệnh Bài Thông Hành");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, Vethonghanh, 1);               
                                        Service.getInstance().sendMoney(player);
                                        InventoryServiceNew.gI().sendItemBags(player);                                    
                                            BossData bossDataClone = new BossData(
                                            "Đường Tank" +" "+ player.name,
                                            (byte) 0,
                                            new short[]{467, 468, 469, -1, -1, -1},
                                            100000,
                                            new long[]{player.nPoint.hpMax * 2},
                                            new int[]{103},
                                            new int[][]{
                                            {Skill.TAI_TAO_NANG_LUONG, 7, 15000}},
                                            new String[]{}, //text chat 1
                                            new String[]{}, //text chat 2
                                            new String[]{}, //text chat 3
                                            60
                                    );

                                    try {
                                        DuongTank dt = new DuongTank(Util.createIdDuongTank((int) player.id), bossDataClone, player.zone, player.location.x - 20, player.location.y);
                                        dt.playerTarger = player;
                                        int[] map = {103};
                                        dt.mapCongDuc = map[Util.nextInt(map.length)];
                                        player.haveDuongTang = true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Service.getInstance().sendMoney(player);
                                }
                            }
                                break;
                            }
                            
                            if (mapId == 48 || mapId == 5 || mapId == 0) {
                                ChangeMapService.gI().changeMapInYard(player, 0, -1, 469);
                            }
                            if (mapId == 123) {
                                ChangeMapService.gI().changeMapInYard(player, 0, -1, 469);
                            }
                            if (mapId == 122) {
                                if (select == 0) {
                                    if (player.NguHanhSonPoint >= 500) {
                                        player.NguHanhSonPoint -= 500;
                                        Item item = ItemService.gI().createNewItem((short) (711));
                                        item.itemOptions.add(new Item.ItemOption(49, 80));
                                        item.itemOptions.add(new Item.ItemOption(77, 80));
                                        item.itemOptions.add(new Item.ItemOption(103, 50));
                                        item.itemOptions.add(new Item.ItemOption(207, 0));
                                        item.itemOptions.add(new Item.ItemOption(33, 0));
//                                      
                                        InventoryServiceNew.gI().addItemBag(player, item);
                                        Service.getInstance().sendThongBao(player, "Chúc Mừng Bạn Đổi Vật Phẩm Thành Công !");
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Không đủ điểm, bạn còn " + (500 - player.pointPvp) + " điểm nữa");
                                    }
                                } else if (select == 1) {
                                //    Util.showListTop(player, (byte) 4);
                                }
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc dauThan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    player.magicTree.openMenuTree();
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    TaskService.gI().checkDoneTaskConfirmMenuNpc(player, this, (byte) select);
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_LEFT_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                if (player.magicTree.level == 10) {
                                    player.magicTree.fastRespawnPea();
                                } else {
                                    player.magicTree.showConfirmUpgradeMagicTree();
                                }
                            } else if (select == 2) {
                                player.magicTree.fastRespawnPea();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_FULL_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUpgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UPGRADE:
                            if (select == 0) {
                                player.magicTree.upgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_UPGRADE:
                            if (select == 0) {
                                player.magicTree.fastUpgradeMagicTree();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUnuppgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UNUPGRADE:
                            if (select == 0) {
                                player.magicTree.unupgradeMagicTree();
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc calick(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            private final byte COUNT_CHANGE = 50;
            private int count;

            private void changeMap() {
                if (this.mapId != 102) {
                    count++;
                    if (this.count >= COUNT_CHANGE) {
                        count = 0;
                        this.map.npcs.remove(this);
                        Map map = MapService.gI().getMapForCalich();
                        this.mapId = map.mapId;
                        this.cx = Util.nextInt(100, map.mapWidth - 100);
                        this.cy = map.yPhysicInTop(this.cx, 0);
                        this.map = map;
                        this.map.npcs.add(this);
                    }
                }
            }

            @Override
            public void openBaseMenu(Player player) {
                player.iDMark.setIndexMenu(ConstNpc.BASE_MENU);
                if (TaskService.gI().getIdTask(player) < ConstTask.TASK_20_0) {
                    Service.getInstance().hideWaitDialog(player);
                    Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    return;
                }
                if (this.mapId != player.zone.map.mapId) {
                    Service.getInstance().sendThongBao(player, "Calích đã rời khỏi map!");
                    Service.getInstance().hideWaitDialog(player);
                    return;
                }

                if (this.mapId == 102) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?",
                            "Kể\nChuyện", "Quay về\nQuá khứ");
                } else {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?", "Kể\nChuyện", "Đi đến\nTương lai", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (this.mapId == 102) {
                    if (player.iDMark.isBaseMenu()) {
                        if (select == 0) {
                            //kể chuyện
                            NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                        } else if (select == 1) {
                            //về quá khứ
                            ChangeMapService.gI().goToQuaKhu(player);
                        }
                    }
                } else if (player.iDMark.isBaseMenu()) {
                    if (select == 0) {
                        //kể chuyện
                        NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                    } else if (select == 1) {
                        //đến tương lai
//                                    changeMap();
                        if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_20_0) {
                            ChangeMapService.gI().goToTuongLai(player);
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    }
                }
            }
        };
    }

    public static Npc jaco(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Gô Tên, Calich và Monaka đang gặp chuyện ở hành tinh Potaufeu \n Hãy đến đó ngay", "Đến \nPotaufeu");
                    } else if (this.mapId == 139) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    } else if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Bạn cần đổi vật phẩm gì?\nHƯỚNG DẪN ĐỔI ĐỒ\b|7|Đổi 1 đồ Thần Linh + 30 Đá Ngũ Sắc => Nhận 1 đồ Hủy Diệt \nĐổi 20 Đá Ngũ Sắc => Hộp Sét Kích Hoạt", "Đổi đồ\nHủy Diệt\nTrái Đất", "Đổi đồ\nHủy Diệt\nNamek", "Đổi Đồ\nHủy Diệt\nXayda", "Đổi \nHộp SKH");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                //đến potaufeu
                                ChangeMapService.gI().goToPotaufeu(player);
                            }
                        }
                    } else if (this.mapId == 139 || this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                //về trạm vũ trụ
                                case 0:
                                   this.createOtherMenu(player, 1,
                                            "Bạn muốn đổi 1 món đồ Thần Linh \nTrái đất và x30 đá ngũ sắc \n|6|Để đổi lấy 1 món đồ Hủy Diệt không?", "Áo\nHúy Diệt", "Quần\nHúy Diệt", "Găng\nDúy Diệt", "Giày\nHúy Diệt", "Nhẫn\nHúy Diệt", "Thôi Khỏi");
                                    break;
                                case 1:
                                    this.createOtherMenu(player, 2,
                                            "Bạn muốn đổi 1 món đồ thần linh \nNamek và x30 đá ngũ sắc \n|6|Để đổi lấy 1 món đồ Hủy Diệt không?", "Áo\nHúy Diệt", "Quần\nHúy Diệt", "Găng\nDúy Diệt", "Giày\nHúy Diệt", "Nhẫn\nHúy Diệt", "Thôi Khỏi");
                                    break;
                                case 2:
                                    this.createOtherMenu(player, 3,
                                            "Bạn muốn đổi 1 món đồ thần linh \nXayda và x30 đá ngũ sắc \n|6|Để đổi lấy 1 món đồ Hủy Diệt không?", "Áo\nHúy Diệt", "Quần\nHúy Diệt", "Găng\nDúy Diệt", "Giày\nHúy Diệt", "Nhẫn\nHúy Diệt", "Thôi Khỏi");
                                    break;
                                case 3:
                                    createOtherMenu(player, ConstNpc.MENU_1,
                                            "Ngươi muốn Đổi SKH hãy chọn bên dưới! "
                                                    + "\n Đổi 20 Đá Ngũ Sắc => Hộp Sét Kích Hoạt "
                                                    + "\n Đổi x1 Mây Ngũ Sắc => Hộp Sét Kích Hoạt "
                                                    ,
                                            
                                            "Đổi Đá NS\nLấy Hộp\nSKH Trái Đất", 
                                            "Đổi Đá NS\nLấy Hộp\nSKH Namek", 
                                            "Đổi Đá NS\nLấy Hộp\nSKH Xayda",
                                            "Đổi Mây NS\nLấy Hộp\nSKH Trái Đất", 
                                            "Đổi Mây NS\nLấy Hộp\nSKH Namek", 
                                            "Đổi Mây NS\nLấy Hộp\nSKH Xayda",

                                            "Đóng");
                                    break;                               
                            }
                        }
                        
                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_1) {
                            switch (select) {
                                
                                case 0: {
                                    Item dangusac = null;
                                    try {
                                        dangusac = InventoryServiceNew.gI().findItemBag(player, 674);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (dangusac == null || dangusac.quantity < 20) {
                                        this.npcChat(player, "Bạn không đủ 20 Đá Ngũ Sắc");
                                    
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, 20);
                                        Service.getInstance().sendMoney(player);
                                        Item hopskh = ItemService.gI().createNewItem((short) 2000);
                                        hopskh.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, hopskh);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được x1 Hộp SKH Trái Đất");
                                    }
                                    break;
                                }
                                case 1: {
                                    Item dangusac = null;
                                    try {
                                        dangusac = InventoryServiceNew.gI().findItemBag(player, 674);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (dangusac == null || dangusac.quantity < 20) {
                                        this.npcChat(player, "Bạn không đủ 20 Đá Ngũ Sắc");
                                    
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, 20);
                                        Service.getInstance().sendMoney(player);
                                        Item hopskh = ItemService.gI().createNewItem((short) 2001);
                                        hopskh.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, hopskh);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được x1 Hộp SKH Namek");
                                    }
                                    break;
                                }
                                case 2: {
                                     Item dangusac = null;
                                    try {
                                        dangusac = InventoryServiceNew.gI().findItemBag(player, 674);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (dangusac == null || dangusac.quantity < 20) {
                                        this.npcChat(player, "Bạn không đủ 20 Đá Ngũ Sắc");
                                    
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, 20);
                                        Service.getInstance().sendMoney(player);
                                        Item hopskh = ItemService.gI().createNewItem((short) 2002);
                                        hopskh.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, hopskh);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được x1 Hộp SKH Xayda");
                                    }
                                    break;
                                }
                                case 3: {
                                    Item dangusac = null;
                                    try {
                                        dangusac = InventoryServiceNew.gI().findItemBag(player, 1237);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (dangusac == null || dangusac.quantity < 1) {
                                        this.npcChat(player, "Bạn không đủ 1 Mây Ngũ Sắc");
                                    
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, 20);
                                        Service.getInstance().sendMoney(player);
                                        Item hopskh = ItemService.gI().createNewItem((short) 2000);
                                        hopskh.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, hopskh);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được x1 Hộp SKH Trái Đất");
                                    }
                                    break;
                                }
                                case 4: {
                                    Item dangusac = null;
                                    try {
                                        dangusac = InventoryServiceNew.gI().findItemBag(player, 1237);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (dangusac == null || dangusac.quantity < 1) {
                                        this.npcChat(player, "Bạn không đủ 1 Mây Ngũ Sắc");
                                    
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, 20);
                                        Service.getInstance().sendMoney(player);
                                        Item hopskh = ItemService.gI().createNewItem((short) 2001);
                                        hopskh.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, hopskh);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được x1 Hộp SKH Namek");
                                    }
                                    break;
                                }
                                case 5: {
                                     Item dangusac = null;
                                    try {
                                        dangusac = InventoryServiceNew.gI().findItemBag(player, 1237);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (dangusac == null || dangusac.quantity < 1) {
                                        this.npcChat(player, "Bạn không đủ 1 Mây Ngũ Sắc");
                                    
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, dangusac, 20);
                                        Service.getInstance().sendMoney(player);
                                        Item hopskh = ItemService.gI().createNewItem((short) 2002);
                                        hopskh.itemOptions.add(new Item.ItemOption(30, 1));
                                        InventoryServiceNew.gI().addItemBag(player, hopskh);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được x1 Hộp SKH Xayda");
                                    }
                                    break;
                                }
                            }
                        }
      
                        
                        else if (player.iDMark.getIndexMenu() == 1) { // action đổi dồ húy diệt
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 555);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 555 + i);
                                        
//                                        if(soLuong < 30 || thlinh < 1){this.npcChat(player, "Yêu cầu cần Áo Thần linh Trái đất + x30 Đá Ngũ Sắc!");}

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 555) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 650 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");                                            
                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo Thần linh Trái đất + x30 Đá Ngũ Sắc!");
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 556);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 556 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 556) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 651 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");
                                            break;                                            
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần Thần linh Trái đất + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 562);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 562 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 562) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 657 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng Thần linh Trái đất + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 563);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 563 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 563) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 658 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày Thần linh Trái đất + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 561) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 656 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");
                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Nhẫn Thần linh Trái đất + x30 Đá Ngũ Sắc!");
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 2) { // action đổi dồ húy diệt
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 557);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 557 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 557) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 652 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo Thần linh Namec + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 558);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 558 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 558) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 653 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần Thần linh Namec + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 564);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 564 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 564) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 659 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng Thần linh Namec + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 565);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 565 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 565) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 660 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày Thần linh Namec + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 561) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 656 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");
                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Nhẫn Thần linh Namec + x30 Đá Ngũ Sắc!");
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 3) { // action đổi dồ húy diệt
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 559);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 559 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 559) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 654 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo Thần linh Xayda + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 560);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 560 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 560) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 655 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần Thần linh Xayda + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 566);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 566 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 566) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 661 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng Thần linh Xayda + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 567);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 567 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 567) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 662 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày Thần linh Xayda + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 561) && soLuong >= 30 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 656 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");
                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Nhẫn Thần linh Xayde + x30 Đá Ngũ Sắc!");
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 4) { // action đổi dồ thiên sứ
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 650);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 650 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 650) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1048 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo húy diệt Trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 651);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 651 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 651) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1051 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần húy diệt Trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 657);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 657 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 657) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1054 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng húy diệt Trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 658);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 658 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 658) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1057 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày húy diệt Trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 656) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1060 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần nhẫn húy diệt Trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 5) { // action đổi dồ thiên sứ
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 652);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 652 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 652) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1049 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo húy diệt Namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 653);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 653 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 653) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1052 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần húy diệt Namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 659);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 659 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 659) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1055 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng húy diệt Namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 660);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 660 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 660) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1058 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày húy diệt Namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 656) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1061 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần nhẫn húy diệt Namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 6) { // action đổi dồ thiên sứ
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 654);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 654 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 654) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1050 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo húy diệt Xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 655);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 655 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 655) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1053 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần húy diệt Xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 661);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 661 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 661) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1056 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng húy diệt Xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 662);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 662 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 662) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1059 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày húy diệt Xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656);
                                    int soLuong = 0;
                                    int thlinh = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                        thlinh = tl.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 656) && soLuong >= 99 && thlinh >= 1) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, tl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1062 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần nhẫn húy diệt xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

//public static Npc Potage(int mapId, int status, int cx, int cy, int tempId, int avartar) {
//        return new Npc(mapId, status, cx, cy, tempId, avartar) {
//            @Override
//            public void openBaseMenu(Player player) {
//                if (canOpenNpc(player)) {
//                    if (this.mapId == 149) {
//                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
//                                "tét", "Gọi nhân bản");
//                    }
//                }
//            }
//            @Override
//            public void confirmMenu(Player player, int select) {
//                if (canOpenNpc(player)) {
//                   if (select == 0){
//                        BossManager.gI().createBoss(-214);
//                   }
//                }
//            }
//        };
//    }
    public static Npc npclytieunuong54(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                createOtherMenu(player, 0, "Trò chơi Chọn ai đây đang được diễn ra, nếu bạn tin tưởng mình đang tràn đầy may mắn thì có thể tham gia thử", "Thể lệ", "Chọn\nThỏi vàng");
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (canOpenNpc(pl)) {
                    String time = ((ChonAiDay.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) + " giây";
                    if (pl.iDMark.getIndexMenu() == 0) {
                        if (select == 0) {
                            createOtherMenu(pl, ConstNpc.IGNORE_MENU, "Thời gian giữa các giải là 5 phút\nKhi hết giờ, hệ thống sẽ ngẫu nhiên chọn ra 1 người may mắn.\nLưu ý: Số thỏi vàng nhận được sẽ bị nhà cái lụm đi 5%!Trong quá trình diễn ra khi đặt cược nếu thoát game mọi phần đặt đều sẽ bị hủy", "Ok");
                        } else if (select == 1) {
                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                        }
                    } else if (pl.iDMark.getIndexMenu() == 1) {
                        if (((ChonAiDay.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                                    break;
                                case 1: {
                                    if (InventoryServiceNew.gI().findItemBag(pl, 457).isNotNullItem() && InventoryServiceNew.gI().findItemBag(pl, 457).quantity >= 20) {
                                        InventoryServiceNew.gI().subQuantityItemsBag(pl, InventoryServiceNew.gI().findItemBag(pl, 457), 20);
                                        InventoryServiceNew.gI().sendItemBags(pl);
                                        pl.goldNormar += 20;
                                        ChonAiDay.gI().goldNormar += 20;
                                        ChonAiDay.gI().addPlayerNormar(pl);
                                        createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                                    } else {
                                        Service.getInstance().sendThongBao(pl, "Bạn không đủ thỏi vàng");
                                    }
                                }
                                break;
                                case 2: {
                                    if (InventoryServiceNew.gI().findItemBag(pl, 457).isNotNullItem() && InventoryServiceNew.gI().findItemBag(pl, 457).quantity >= 200) {
                                        InventoryServiceNew.gI().subQuantityItemsBag(pl, InventoryServiceNew.gI().findItemBag(pl, 457), 200);
                                        InventoryServiceNew.gI().sendItemBags(pl);
                                        pl.goldVIP += 200;
                                        ChonAiDay.gI().goldVip += 200;
                                        ChonAiDay.gI().addPlayerVIP(pl);
                                        createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                                    } else {
                                        Service.getInstance().sendThongBao(pl, "Bạn không đủ thỏi vàng");
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        };
    }


    public static Npc npcong_gia_moel98(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 5) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Bạn cần đổi gì?", "Đổi đồ\nHủy Diệt\nTrái Đất", "Đổi đồ\nHuy Diệt\nNamek", "Đổi Đồ\nHủy Diệt\nxayda", "Đổi Đồ\nThiên Sứ\nTrái Đất", "Đổi Đồ\nThiên Sứ\nNamek", "Đổi Đồ\nThiên Sú\nXayda");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, 1,
                                            "Bạn muốn đổi 1 món đồ thần linh \nTrái đất cùng loại và x30 đá ngũ sắc \n|6|Để đổi lấy 1 món đồ húy diệt có tý lệ ra SKH ko", "Áo\nHúy Diệt", "Quần\nHúy Diệt", "Găng\nDúy Diệt", "Giày\nHúy Diệt", "Nhẫn\nHúy Diệt", "Thôi Khỏi");
                                    break;
                                case 1:
                                    this.createOtherMenu(player, 2,
                                            "Bạn muốn đổi 1 món đồ thần linh \nNamek cùng loại và x30 đá ngũ sắc \n|6|Để đổi lấy 1 món đồ húy diệt có tý lệ ra SKH ko", "Áo\nHúy Diệt", "Quần\nHúy Diệt", "Găng\nDúy Diệt", "Giày\nHúy Diệt", "Nhẫn\nHúy Diệt", "Thôi Khỏi");
                                    break;
                                case 2:
                                    this.createOtherMenu(player, 3,
                                            "Bạn muốn đổi 1 món đồ thần linh \nXayda cùng loại và x30 đá ngũ sắc \n|6|Để đổi lấy 1 món đồ húy diệt có tý lệ ra SKH ko", "Áo\nHúy Diệt", "Quần\nHúy Diệt", "Găng\nDúy Diệt", "Giày\nHúy Diệt", "Nhẫn\nHúy Diệt", "Thôi Khỏi");
                                    break;
                                case 3:
                                    this.createOtherMenu(player, 4,
                                            "Bạn muốn đổi 1 món đồ húy diệt \nTrái đất cùng loại và x99 đá ngũ sắc \n|6|Để đổi lấy 1 món đồ thiên sứ có tý lệ rà SKH ko", "Áo\nThiên sứ", "Quần\nThiên sứ", "Găng\nThiên sứ", "Giày\nThiên Sứ", "Nhẫn\nThiên Sứ", "Từ Chối");
                                    break;
                                case 4:
                                    this.createOtherMenu(player, 5,
                                            "Bạn muốn đổi 1 món đồ húy diệt \nNamek cùng loại và x99 đá ngũ sắc \n|6|Để đổi lấy 1 món đồ thiên sứ có tý lệ rà SKH ko", "Áo\nThiên sứ", "Quần\nThiên sứ", "Găng\nThiên sứ", "Giày\nThiên Sứ", "Nhẫn\nThiên Sứ", "Từ Chối");
                                    break;
                                case 5:
                                    this.createOtherMenu(player, 6,
                                            "Bạn muốn đổi 1 món đồ húy diệt \nXayda cùng loại và x99 đá ngũ sắc \n|6|Để đổi lấy 1 món đồ thiên sứ có tý lệ rà SKH ko", "Áo\nThiên sứ", "Quần\nThiên sứ", "Găng\nThiên sứ", "Giày\nThiên Sứ", "Nhẫn\nThiên Sứ", "Từ Chối");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 1) { // action đổi dồ húy diệt
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 555);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 555 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 555 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 650 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo Thần linh trái đất + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 556);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 556 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 556 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 651 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần Thần linh trái đất + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 562);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 562 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 562 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 657 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng Thần linh trái đất + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 563);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 563 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 563 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 658 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày Thần linh trái đất + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 561 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 656 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");
                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Nhận Thần linh trái đất + x30 Đá Ngũ Sắc!");
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 2) { // action đổi dồ húy diệt
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 557);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 557 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 557 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 650 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo Thần linh namec + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 558);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 558 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 558 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 651 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần Thần linh namec + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 564);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 564 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 564 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 657 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng Thần linh namec + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 565);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 565 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 565 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 658 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày Thần linh namec + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 561 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 656 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");
                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Nhận Thần linh namec + x30 Đá Ngũ Sắc!");
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 3) { // action đổi dồ húy diệt
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 559);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 559 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 559 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 650 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo Thần linh xayda + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 560);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 560 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 560 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 651 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần Thần linh xayda + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 566);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 566 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 566 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 657 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng Thần linh xayda + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 567);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 567 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 567 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 658 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày Thần linh xayda + x30 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 561 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 561 + i) && soLuong >= 30) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoathuydiet(player, 656 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");
                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Nhận Thần linh xayde + x30 Đá Ngũ Sắc!");
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 4) { // action đổi dồ thiên sứ
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 650);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 650 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 650 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1048 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo húy diệt trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 651);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 651 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 651 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1051 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần húy diệt trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 657);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 657 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 657 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1054 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng húy diệt trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 658);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 658 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 658 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1057 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày húy diệt trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 656 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1060 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần nhận húy diệt trái đất + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 5) { // action đổi dồ thiên sứ
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 652);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 652 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 652 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1049 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo húy diệt namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 653);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 653 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 653 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1052 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần húy diệt namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 659);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 659 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 659 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1055 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng húy diệt namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 660);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 660 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 660 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1058 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày húy diệt namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 656 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1061 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần nhận húy diệt namec + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 6) { // action đổi dồ thiên sứ
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 654);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 654 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 654 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1050 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Áo húy diệt xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 655);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 655 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 655 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1053 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Quần húy diệt xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 661);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 661 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 661 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1056 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Găng húy diệt xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 662);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 662 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 662 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1059 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần Giày húy diệt xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 4: // trade
                                    try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item tl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item thl = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 656 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 656 + i) && soLuong >= 99) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 99);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, thl, 1);
                                            CombineServiceNew.gI().GetTrangBiKichHoatthiensu(player, 1062 + i);
                                            this.npcChat(player, "Chuyển Hóa Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần nhận húy diệt xayda + x99 Đá Ngũ Sắc!");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 5: // canel
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc thuongDe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 45) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Con muốn làm gì nào", "Đến Kaio", "Quay số\nmay mắn");
                    }
//                    if (this.mapId == 0) {
//                        this.createOtherMenu(player, 0,
//                                "Con muốn gì nào?\nCon đang còn : " + player.pointPvp + " điểm PvP Point", "Đến DHVT", "Đổi Cải trang sự kiên", "Top PVP");
//                    }
                    if (this.mapId == 129 || this.mapId == 141) {
                        this.createOtherMenu(player, 0,
                                "Con muốn gì nào?", "Quay về");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
//                    if (this.mapId == 0) {
//                        if (player.iDMark.getIndexMenu() == 0) { // 
//                            switch (select) {
//                                case 0:
//                                    ChangeMapService.gI().changeMapBySpaceShip(player, 129, -1, 354);
//                                    Service.getInstance().changeFlag(player, Util.nextInt(8));
//                                    break; // qua dhvt
//                                case 1:  // 
//                                    this.createOtherMenu(player, 1,
//                                            "Bạn có muốn đổi 500 điểm PVP lấy \n|6|Cải trang Mèo Kid Lân với tất cả chỉ số là 80%\n ", "Ok", "Tu choi");
//                                    // bat menu doi item
//                                    break;
//
//                                case 2:  // 
//                                    Util.showListTop(player, (byte) 3);
//                                    // mo top pvp
//                                    break;
//
//                            }
//                        }
//                        if (player.iDMark.getIndexMenu() == 1) { // action doi item
//                            switch (select) {
//                                case 0: // trade
//                                    if (player.pointPvp >= 500) {
//                                        player.pointPvp -= 500;
//                                        Item item = ItemService.gI().createNewItem((short) (1104));
//                                        item.itemOptions.add(new Item.ItemOption(49, 80));
//                                        item.itemOptions.add(new Item.ItemOption(77, 80));
//                                        item.itemOptions.add(new Item.ItemOption(103, 50));
//                                        item.itemOptions.add(new Item.ItemOption(207, 0));
//                                        item.itemOptions.add(new Item.ItemOption(33, 0));
////                                      
//                                        InventoryServiceNew.gI().addItemBag(player, item);
//                                        Service.getInstance().sendThongBao(player, "Chúc Mừng Bạn Đổi Cải Trang Thành Công !");
//                                    } else {
//                                        Service.getInstance().sendThongBao(player, "Không đủ điểm bạn còn " + (500 - player.pointPvp) + " Điểm nữa");
//                                    }
//                                    break;
//                            }
//                        }
//                    }
                    if (this.mapId == 129) {
                        switch (select) {
                            case 0: // quay ve
                                ChangeMapService.gI().changeMapBySpaceShip(player, 0, -1, 354);
                                break;
                        }
                    }
                    if (this.mapId == 141) {
                        switch (select) {
                            case 0: // quay ve
                                ChangeMapService.gI().changeMapBySpaceShip(player, 48, -1, 354);
                                break;
                        }
                    }
                    if (this.mapId == 45) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 48, -1, 354);
                                    break;
                                case 1:
                                    this.createOtherMenu(player, ConstNpc.MENU_CHOOSE_LUCKY_ROUND,
                                            "Con muốn làm gì nào?", "Quay bằng\nvàng",
                                            "Rương phụ\n("
                                            + (player.inventory.itemsBoxCrackBall.size()
                                            - InventoryServiceNew.gI().getCountEmptyListItem(player.inventory.itemsBoxCrackBall))
                                            + " món)",
                                            "Xóa hết\ntrong rương", "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_CHOOSE_LUCKY_ROUND) {
                            switch (select) {
                                case 0:
                                    LuckyRound.gI().openCrackBallUI(player, LuckyRound.USING_GOLD);
                                    break;
                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "ITEMS_LUCKY_ROUND", true);
                                    break;
                                case 2:
                                    NpcService.gI().createMenuConMeo(player,
                                            ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND, this.avartar,
                                            "Con có chắc muốn xóa hết vật phẩm trong rương phụ? Sau khi xóa "
                                            + "sẽ không thể khôi phục!",
                                            "Đồng ý", "Hủy bỏ");
                                    break;
                            }
                        }
                    }

                }
            }
        };
    }

    public static Npc thanVuTru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48 || this.mapId == 0) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Con muốn làm gì nào", "Di chuyển");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48 || this.mapId == 0) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, ConstNpc.MENU_DI_CHUYEN,
                                            "Con muốn đi đâu?", "Về\nthần điện", "Thánh địa\nKaio", "Con\nđường\nrắn độc", "Từ chối");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DI_CHUYEN) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 45, -1, 354);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMap(player, 141, -1, 318, 336);//con đường rắn độc
                                    break;
                            }
                        }
                    }
                }
            }

        };
    }
    public static Npc zamasu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Đề đến khu săn Đệ tử Super Broly Huyền thoại bạn cần Đạt các điều kiện:\n|7|1.Mang Cải trang DCTT\n2.Sức mạnh trên 300ty\n3.Có Đệ tử Berus\n4.Đã qua nhiệm vụ 24", "Di chuyển");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, ConstNpc.MENU_DI_CHUYEN,
                                            "Ở chỗ ta rất nguy hiểm!!!Hmmm?", "Đến\nChiến Trường", "Đến\nĐấu Trường\nAnh Hùng","Đến \nThánh Địa","Đến Boss \nTâm Ma","Đến Boss\nVip");
                                    break;
                                
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DI_CHUYEN) {
                            switch (select) {
                                case 0:
//                                    if (player.getSession().player.nPoint.power >= 1000) //&& player.inventory.haveOption(player.inventory.itemsBody, 5, 33) && (player.pet.typePet == 2 || player.pet.typePet == 3) && TaskService.gI().getIdTask(player) > ConstTask.TASK_24_0) 
                                    {
                                    ChangeMapService.gI().changeMap(player, 169, -1, 318, 336);//Chiến trường
                                    }
//                                    else 
//                                    {
//                                    this.npcChat(player, "Bạn chưa đủ sức mạnh để vào");
//                                    }
                                    break;
                                case 1:
                                    {
                                        Item NhanHacAm = null;
                                    try {
                                        NhanHacAm = InventoryServiceNew.gI().findItemBag(player, 1267);

                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (NhanHacAm == null || NhanHacAm.quantity < 1 ) {
                                        this.npcChat(player, "Để Khiêu Chiến Anh Hùng cần có Nhẫn Hắc Ám");                                                                                                        
                                    } else {
                                        
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, NhanHacAm, 1);
                                        Service.getInstance().sendMoney(player);
                                        ChangeMapService.gI().changeMap(player, 174, -1, 305, 312);//Đấu Trường Anh Hùng
                                    }                        
                                    }
                                    break;
                                case 2:
                                    if (player.getSession().player.nPoint.power >= 80000000000L) {

                                    ChangeMapService.gI().changeMapBySpaceShip(player, 156, -1, 432);
                                } else {
                                    this.npcChat(player, "Bạn chưa đủ 80 tỷ sức mạnh để vào");
                                }
                                break; // qua lanh dia
                                case 3:
                                    {
                                    ChangeMapService.gI().changeMap(player, 140, -1, 318, 336); //Đến Boss Tâm ma
                                    }
                                    break;
                                case 4:
                                    {
                                    ChangeMapService.gI().changeMap(player, 143, -1, 318, 336); //Đến Khu Boss Vip
                                    }
                                    break;
//                                case 3:
//                                    if (player.getSession().player.nPoint.power >= 80000000000L) {
//
//                                    ChangeMapService.gI().changeMapBySpaceShip(player, 156, -1, 432);
//                                } else {
//                                    this.npcChat(player, "Bạn chưa đủ 80 tỷ sức mạnh để vào");
//                                }
//                                break; // qua lanh dia
                        }
                    }
                }
            }

        }
    };
    }
    public static Npc kibit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Từ chối");
                    }
                    if (this.mapId == 114) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }
    public static Npc giuma(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 6 || this.mapId == 25 || this.mapId == 26) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Gô Tên, Calich và Monaka đang gặp chuyện ở hành tinh Potaufeu \n Hãy đến đó ngay", "Đến \nPotaufeu");
                    } else if (this.mapId == 139) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                //đến potaufeu
                                ChangeMapService.gI().goToPotaufeu(player);
                            }
                        }
                    } else if (this.mapId == 139) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                //về trạm vũ trụ
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 24 + player.gender, -1, -1);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc osin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Đến\nhành tinh\nBill", "Từ chối");
                    } else if (this.mapId == 154) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Về thánh địa", "Đến\nhành tinh\nngục tù", "Từ chối");
                    } else if (this.mapId == 155) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else if (this.mapId == 52) {
                        try {
                            MapMaBu.gI().setTimeJoinMapMaBu();
                            if (this.mapId == 52) {
                                long now = System.currentTimeMillis();
                                if (now > MapMaBu.TIME_OPEN_MABU && now < MapMaBu.TIME_CLOSE_MABU) {
                                    this.createOtherMenu(player, ConstNpc.MENU_OPEN_MMB, "Đại chiến Ma Bư đã mở, "
                                            + "ngươi có muốn tham gia không?",
                                            "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_MMB,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }

                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu osin");
                        }

                    } else if (this.mapId >= 114 && this.mapId < 120 && this.mapId != 116) {
                        if (player.fightMabu.pointMabu >= player.fightMabu.POINT_MAX) {
                            this.createOtherMenu(player, ConstNpc.GO_UPSTAIRS_MENU, "Ta có thể giúp gì cho ngươi ?",
                                    "Lên Tầng!", "Quay về", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                    "Quay về", "Từ chối");
                        }
                    } else if (this.mapId == 120) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                                    break;
                            }
                        }
                    } else if (this.mapId == 154) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 155, -1, 111, 792);
                                    break;
                            }
                        }
                    } else if (this.mapId == 155) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                            }
                        }
                    } else if (this.mapId == 52) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.MENU_REWARD_MMB:
                                break;
                            case ConstNpc.MENU_OPEN_MMB:
                                if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                } else if (select == 1) {
//                                    if (!player.getSession().actived) {
//                                        Service.getInstance().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
//                                    } else
                                    ChangeMapService.gI().changeMap(player, 114, -1, 318, 336);
                                }
                                break;
                            case ConstNpc.MENU_NOT_OPEN_BDW:
                                if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                }
                                break;
                        }
                    } else if (this.mapId >= 114 && this.mapId < 120 && this.mapId != 116) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.GO_UPSTAIRS_MENU) {
                            if (select == 0) {
                                player.fightMabu.clear();
                                ChangeMapService.gI().changeMap(player, this.map.mapIdNextMabu((short) this.mapId), -1, this.cx, this.cy);
                            } else if (select == 1) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        } else {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    } else if (this.mapId == 120) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc linhCanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.clan == null) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chỉ tiếp các bang hội, miễn tiếp khách vãng lai", "Đóng");
                        return;
                    }
                    if (player.clan.getMembers().size() < DoanhTrai.N_PLAYER_CLAN) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Bang hội phải có ít nhất 5 thành viên mới có thể mở", "Đóng");
                        return;
                    }
                    if (player.clan.doanhTrai != null) {
                        createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                                "Bang hội của ngươi đang đánh trại độc nhãn\n"
                                + "Thời gian còn lại là "
                                + TimeUtil.getMinLeft(player.clan.doanhTrai.getLastTimeOpen(), DoanhTrai.TIME_DOANH_TRAI / 1000)
                                + " Phút" + ". Ngươi có muốn tham gia không?",
                                "Tham gia", "Không", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    int nPlSameClan = 0;
                    for (Player pl : player.zone.getPlayers()) {
                        if (!pl.equals(player) && pl.clan != null
                                && pl.clan.equals(player.clan) && pl.location.x >= 1285
                                && pl.location.x <= 1645) {
                            nPlSameClan++;
                        }
                    }
                    if (nPlSameClan < DoanhTrai.N_PLAYER_MAP) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ngươi phải có ít nhất " + DoanhTrai.N_PLAYER_MAP + " đồng đội cùng bang đứng gần mới có thể\nvào\n"
                                + "tuy nhiên ta khuyên ngươi nên đi cùng với 3-4 người để khỏi chết.\n"
                                + "Hahaha.", "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                //    if (player.clanMember.getNumDateFromJoinTimeToToday() < 1 && player.clan != null) {
                //        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                //                "Doanh trại chỉ cho phép những người ở trong bang trên 1 ngày. Hẹn ngươi quay lại vào lúc khác",
                //                "OK", "Hướng\ndẫn\nthêm");
                //        return;
                //    }
                    if (player.clan.haveGoneDoanhTrai) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Bang hội của ngươi đã đi trại lúc " + TimeUtil.formatTime(player.clan.lastTimeOpenDoanhTrai, "HH:mm:ss") + " hôm nay. Người mở\n"
                                + "(" + player.clan.playerOpenDoanhTrai + "). Hẹn ngươi quay lại vào ngày mai", "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                            "Hôm nay bang hội của ngươi chưa vào trại lần nào. Ngươi có muốn vào\n"
                            + "không?\nĐể vào, ta khuyên ngươi nên có 3-4 người cùng bang đi cùng",
                            "Vào\n(miễn phí)", "Không", "Hướng\ndẫn\nthêm");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_JOIN_DOANH_TRAI:
                            if (select == 0) {
                                if (player.clan.doanhTrai != null && TimeUtil.getMinLeft(player.clan.doanhTrai.getLastTimeOpen(), DoanhTrai.TIME_DOANH_TRAI / 1000) == 0)
                                {
                                    Service.getInstance().sendThongBao(player, "Hết 30p gòi, đợi mai đê !!!!");
                                }else if (player.clan.doanhTrai == null)
                                {
                                    DoanhTraiService.gI().joinDoanhTrai(player);
                                }else if (player.clan.doanhTrai != null && TimeUtil.getMinLeft(player.clan.doanhTrai.getLastTimeOpen(), DoanhTrai.TIME_DOANH_TRAI / 1000)>0)
                                {
                                    ChangeMapService.gI().changeMapInYard(player, 53, -1, 60);

                                }
                            } else if (select == 2) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                        case ConstNpc.IGNORE_MENU:
                            if (select == 1) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc quaTrung(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_AP_TRUNG_NHANH = 1000000000;

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    player.mabuEgg.sendMabuEgg();
                    if (player.mabuEgg.getSecondDone() != 0) {
                        this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Bư bư bư...",
                                "Hủy bỏ\ntrứng", "Ấp nhanh\n" + Util.numberToMoney(COST_AP_TRUNG_NHANH) + " vàng", "Đóng");
                    } else {
                        this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Bư bư bư...", "Nở", "Hủy bỏ\ntrứng", "Đóng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.CAN_NOT_OPEN_EGG:
                            if (select == 0) {
                                this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                        "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                            } else if (select == 1) {
                                if (player.inventory.gold >= COST_AP_TRUNG_NHANH) {
                                    player.inventory.gold -= COST_AP_TRUNG_NHANH;
                                    player.mabuEgg.timeDone = 0;
                                    Service.getInstance().sendMoney(player);
                                    player.mabuEgg.sendMabuEgg();
                                } else {
                                    Service.getInstance().sendThongBao(player,
                                            "Bạn không đủ vàng để thực hiện, còn thiếu "
                                            + Util.numberToMoney((COST_AP_TRUNG_NHANH - player.inventory.gold)) + " vàng");
                                }
                            }
                            break;
                        case ConstNpc.CAN_OPEN_EGG:
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_EGG,
                                            "Bạn có chắc chắn cho trứng nở?\n"
                                            + "Đệ tử của bạn sẽ được thay thế bằng đệ Mabư",
                                            "Đệ mabư\nTrái Đất", "Đệ mabư\nNamếc", "Đệ mabư\nXayda", "Từ chối");
                                    break;
                                case 1:
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                            "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                    break;
                            }
                            break;
                        case ConstNpc.CONFIRM_OPEN_EGG:
                            switch (select) {
                                case 0:
                                    player.mabuEgg.openEgg(ConstPlayer.TRAI_DAT);
                                    break;
                                case 1:
                                    player.mabuEgg.openEgg(ConstPlayer.NAMEC);
                                    break;
                                case 2:
                                    player.mabuEgg.openEgg(ConstPlayer.XAYDA);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case ConstNpc.CONFIRM_DESTROY_EGG:
                            if (select == 0) {
                                player.mabuEgg.destroyEgg();
                            }
                            break;
                    }
                }
            }
        };
    }
    public static Npc duahau(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_AP_TRUNG_NHANH = 1000;

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    player.timedua.sendTimedua();
                    if (player.timedua.getSecondDone() != 0) {
                        this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_DUA, "Thu hoạch dưa hấu nhận 15000 Hồng ngọc",
                                "Hủy bỏ\nDưa hấu", "Đóng");
                    } else {
                        this.createOtherMenu(player, ConstNpc.CAN_OPEN_DUA, "Dưa chín rồi nè", "Thu hoạch", "Hủy bỏ\nDưa hấu", "Đóng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.CAN_NOT_OPEN_DUA:
                            if (select == 0) {
                                this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_DUA,
                                        "Bạn có chắc chắn muốn hủy bỏ Dưa hấu?", "Đồng ý", "Từ chối");
                            } 
                            break;
                        case ConstNpc.CAN_OPEN_DUA:
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_DUA,
                                            "Bạn có chắc chắn THU HOẠCH DƯA?\n"
                                            + "Sẽ nhận được 15000 hồng ngọc",
                                            "Thu hoạch");
                                    break;
                                case 1:
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_DUA,
                                            "Bạn có chắc chắn muốn hủy bỏ dưa hấu?", "Đồng ý", "Từ chối");
                                    break;
                            }
                            break;
                        case ConstNpc.CONFIRM_OPEN_DUA:
                            switch (select) {
                                case 0:
                                    player.inventory.ruby+=15000;
                            Service.getInstance().sendMoney(player);
                            this.npcChat(player, "Bạn nhận được 15000 hồng ngọc");
                            break;
                                
                            }
                            
                        case ConstNpc.CONFIRM_DESTROY_DUA:
                            if (select == 0) {
                                player.timedua.destroydua();
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc quocVuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                        "Con muốn nâng giới hạn sức mạnh cho bản thân hay đệ tử?",
                        "Bản thân", "Đệ tử", "Từ chối");
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                    this.createOtherMenu(player, ConstNpc.OPEN_POWER_MYSEFT,
                                            "Ta sẽ truyền năng lượng giúp con mở giới hạn sức mạnh của bản thân lên "
                                            + Util.numberToMoney(player.nPoint.getPowerNextLimit()),
                                            "Nâng\ngiới hạn\nsức mạnh",
                                            "Nâng ngay\n" + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "Sức mạnh của con đã đạt tới giới hạn",
                                            "Đóng");
                                }
                                break;
                            case 1:
                                if (player.pet != null) {
                                    if (player.pet.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                        this.createOtherMenu(player, ConstNpc.OPEN_POWER_PET,
                                                "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của đệ tử lên "
                                                + Util.numberToMoney(player.pet.nPoint.getPowerNextLimit()),
                                                "Nâng ngay\n" + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Sức mạnh của đệ con đã đạt tới giới hạn",
                                                "Đóng");
                                    }
                                } else {
                                    Service.gI().sendThongBao(player, "Không thể thực hiện");
                                }
                                //giới hạn đệ tử
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_MYSEFT) {
                        switch (select) {
                            case 0:
                                OpenPowerService.gI().openPowerBasic(player);
                                break;
                            case 1:
                                if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                    if (OpenPowerService.gI().openPowerSpeed(player)) {
                                        player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                        Service.gI().sendMoney(player);
                                    }
                                } else {
                                    Service.gI().sendThongBao(player,
                                            "Bạn không đủ vàng để mở, còn thiếu "
                                            + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_PET) {
                        if (select == 0) {
                            if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                if (OpenPowerService.gI().openPowerSpeed(player.pet)) {
                                    player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                    Service.gI().sendMoney(player);
                                }
                            } else {
                                Service.gI().sendThongBao(player,
                                        "Bạn không đủ vàng để mở, còn thiếu "
                                        + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
                            }
                        }
                    }
                }
            }
//                            case 0:
//                                if (player.nPoint.limitPower <= 17) {
//                                    this.createOtherMenu(player, ConstNpc.OPEN_POWER_MYSEFT,
//                                            "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của bản thân lên "
//                                            + Util.numberToMoney(player.nPoint.getPowerNextLimit()),
//                                            "Nâng\ngiới hạn\nsức mạnh",
//                                            "Nâng ngay\n" + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
//                                }else if (player.nPoint.limitPower == 17) {
//                                    this.createOtherMenu(player, ConstNpc.OPEN_POWER_MYSEFT,
//                                            "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của bản thân lên "
//                                            + "300 tỷ Sức mạnh",
//                                            "Nâng ngay\n" + "66.000" + " hồng ngọc", "Đóng");
//                                }
//                                else {
//                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
//                                            "Sức mạnh của con đã đạt tới giới hạn",
//                                            "Đóng");
//                                }
//                                break;
//                            case 1:
//                                if (player.pet != null) {
//                                    if (player.pet.nPoint.limitPower < 9) {
//                                        this.createOtherMenu(player, ConstNpc.OPEN_POWER_PET,
//                                                "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của đệ tử lên "
//                                                + Util.numberToMoney(player.pet.nPoint.getPowerNextLimit()),
//                                                "Nâng ngay\n" + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
//                                    }
//                                    else if (player.pet.nPoint.limitPower == 9) {
//                                    this.createOtherMenu(player, ConstNpc.OPEN_POWER_PET,
//                                            "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của Đệ tử lên "
//                                            + "300 tỷ Sức mạnh",
//                                            "Nâng ngay\n" + "66.000" + " hồng ngọc", "Đóng");
//                                    }
//                                    else {
//                                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
//                                                "Sức mạnh của đệ con đã đạt tới giới hạn",
//                                                "Đóng");
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player, "Không thể thực hiện");
//                                }
//                                //giới hạn đệ tử
//                                break;
//                        }
//                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_MYSEFT && player.nPoint.limitPower < 9) {
//                        switch (select) {
//                            case 0:
//                                OpenPowerService.gI().openPowerBasic(player);
//                                break;
//                            case 1:
//                                if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
//                                    if (OpenPowerService.gI().openPowerSpeed(player)) {
//                                        player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
//                                        Service.getInstance().sendMoney(player);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player,
//                                            "Bạn không đủ vàng để mở, còn thiếu "
//                                            + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
//                                }
//                                break;
//                        }
//                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_MYSEFT && player.nPoint.limitPower == 9) {
//                        switch (select) {
//                            case 0:
//                                if (player.inventory.ruby >= 66000) {
//                                    if (OpenPowerService.gI().openPowerSpeed(player)) {
//                                        player.inventory.ruby -= 66000;
//                                        Service.getInstance().sendMoney(player);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player,
//                                            "Bạn không đủ hồng ngọc để mở, còn thiếu "
//                                            + Util.numberToMoney((66000 - player.inventory.ruby)) + " hồng ngọc");
//                                }
//                                break;
//                        }
//                    }
//                    else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_PET && player.pet.nPoint.limitPower < 9) {
//                        if (select == 0) {
//                            if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
//                                if (OpenPowerService.gI().openPowerSpeed(player.pet)) {
//                                    player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
//                                    Service.getInstance().sendMoney(player);
//                                }
//                            } else {
//                                Service.getInstance().sendThongBao(player,
//                                        "Bạn không đủ vàng để mở, còn thiếu "
//                                        + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
//                            }
//                        }
//                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_PET && player.pet.nPoint.limitPower == 9) {
//                        switch (select) {
//                            case 0:
//                                if (player.inventory.ruby >= 66000) {
//                                    if (OpenPowerService.gI().openPowerSpeed(player.pet)) {
//                                        player.inventory.ruby -= 66000;
//                                        Service.getInstance().sendMoney(player);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player,
//                                            "Bạn không đủ hồng ngọc để mở, còn thiếu "
//                                            + Util.numberToMoney((66000 - player.inventory.ruby)) + " hồng ngọc");
//                                }
//                                break;
//                        }
//                    }
//                }
//            }
        };
    }

    public static Npc bulmaTL(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 5) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Shop VIP nè, đốt xèng vô đê?", "Cửa hàng", "Đóng");
                        }
                    } else if (this.mapId == 104 || this.mapId == 102) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)){
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chụy chỉ bán những vật phẩm tương lai thui!!", "Cửa hàng", "Đóng");
                    }
                        }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "BUNMA_LINHTHU", true);
                            }
                        }
                    } else if ( this.mapId == 102) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "TUONG_LAI", true);
                            }
                        }
                    }
                }
            }
        };
    }
     public static Npc caythong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 5) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Shop VIP nè, đốt xèng vô đê?", "Cửa hàng", "Đóng");
                        }
                    } else if (this.mapId == 104) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Kính chào Ngài Linh thú sư!", "Cửa hàng", "Đóng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104 || this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "binn", true);
                            }
                        }
                    } else if (this.mapId == 104 || this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
//                                ShopServiceNew.gI().opendShop(player, "BUNMA_LINHTHU", true);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc rongOmega(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    BlackBallWar.gI().setTime();
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        try {
                            long now = System.currentTimeMillis();
                            if (now > BlackBallWar.TIME_OPEN && now < BlackBallWar.TIME_CLOSE) {
                                this.createOtherMenu(player, ConstNpc.MENU_OPEN_BDW, "Đường đến với ngọc rồng sao đen đã mở, "
                                        + "ngươi có muốn tham gia không?",
                                        "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                            } else {
                                String[] optionRewards = new String[7];
                                int index = 0;
                                for (int i = 0; i < 7; i++) {
                                    if (player.rewardBlackBall.timeOutOfDateReward[i] > System.currentTimeMillis()) {
                                        String quantily = player.rewardBlackBall.quantilyBlackBall[i] > 1 ? "x" + player.rewardBlackBall.quantilyBlackBall[i] + " " : "";
                                        optionRewards[index] = quantily + (i + 1) + " sao";
                                        index++;
                                    }
                                }
                                if (index != 0) {
                                    String[] options = new String[index + 1];
                                    for (int i = 0; i < index; i++) {
                                        options[i] = optionRewards[i];
                                    }
                                    options[options.length - 1] = "Từ chối";
                                    this.createOtherMenu(player, ConstNpc.MENU_REWARD_BDW, "Ngươi có một vài phần thưởng ngọc "
                                            + "rồng sao đen đây!",
                                            options);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_BDW,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }
                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu rồng Omega");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_REWARD_BDW:
                            player.rewardBlackBall.getRewardSelect((byte) select);
                            break;
                        case ConstNpc.MENU_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            } else if (select == 1) {
//                                if (!player.getSession().actived) {
//                                    Service.getInstance().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
//
//                                } else
                                player.iDMark.setTypeChangeMap(ConstMap.CHANGE_BLACK_BALL);
                                ChangeMapService.gI().openChangeMapTab(player);
                                BlackBallWar.gI().reInitNrd();
                            }
                            break;
                        case ConstNpc.MENU_NOT_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            }
                            break;
                    }
                }
            }

        };
    }

    public static Npc rong1_to_7s(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isHoldBlackBall()) {
                        this.createOtherMenu(player, ConstNpc.MENU_PHU_HP, "Ta có thể giúp gì cho ngươi?", "Phù hộ", "Từ chối");
                    } else {
                        if (BossManager.gI().existBossOnPlayer(player)
                                || player.zone.items.stream().anyMatch(itemMap -> ItemMapService.gI().isBlackBall(itemMap.itemTemplate.id))
                                || player.zone.getPlayers().stream().anyMatch(p -> p.iDMark.isHoldBlackBall())) {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_GO_HOME, "Ta có thể giúp gì cho ngươi?", "Về nhà", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_GO_HOME, "Ta có thể giúp gì cho ngươi?", "Về nhà", "Từ chối", "Gọi BOSS");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHU_HP) {
                        if (select == 0) {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_PHU_HP,
                                    "Ta sẽ giúp ngươi tăng HP lên mức kinh hoàng, ngươi chọn đi",
                                    "x3 HP\n" + Util.numberToMoney(BlackBallWar.COST_X3) + " vàng",
                                    "x5 HP\n" + Util.numberToMoney(BlackBallWar.COST_X5) + " vàng",
                                    "x7 HP\n" + Util.numberToMoney(BlackBallWar.COST_X7) + " vàng",
                                    "Từ chối"
                            );
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_GO_HOME) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
                        } else if (select == 2) {
                            BossManager.gI().callBoss(player, mapId);
                        } else if (select == 1) {
                            this.npcChat(player, "Để ta xem ngươi trụ được bao lâu");
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PHU_HP) {
                        if (player.effectSkin.xHPKI > 1) {
                            Service.getInstance().sendThongBao(player, "Bạn đã được phù hộ rồi!");
                            return;
                        }
                        switch (select) {
                            case 0:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X3);
                                break;
                            case 1:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X5);
                                break;
                            case 2:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X7);
                                break;
                            case 3:
                                this.npcChat(player, "Để ta xem ngươi trụ được bao lâu");
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc npcThienSu64(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 14) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh và Đến nhiệm vụ 24 "
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                }
                if (this.mapId == 7) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh và Đến nhiệm vụ 24 "
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                }
                if (this.mapId == 0) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh và Đến nhiệm vụ 24"
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                }
                if (this.mapId == 146) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                }
                if (this.mapId == 147) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                }
                if (this.mapId == 148) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                }
                if (this.mapId == 48) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đã tìm đủ nguyên liệu cho tôi chưa?\n Tôi sẽ giúp cậu mạnh lên kha khá đấy!\n\b|7| Điều kiện học Tuyệt kỹ\b|5| -Khi chưa học skill cần: x999 Bí kiếp tuyệt kỹ + 200k Hồng ngọc và SM trên 60 Tỷ\n -Mỗi một cấp yêu cầu Thông thạo đạt MAX 100% và cần 200k Hồng ngọc", "Hướng Dẫn",
                            "Đổi SKH","Học\ntuyệt kĩ","Chế Tạo Đồ\nThiên Sứ","Nâng SKH", "Từ Chối");                
                }
            }

            //if (player.inventory.gold < 500000000) {
//                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
//                return;
//            }
            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu() && this.mapId == 7) {
                        if (select == 0) {
                            if (player.getSession().player.nPoint.power >= 80000000000L && player.inventory.gold > COST_HD && TaskService.gI().getIdTask(player) > ConstTask.TASK_24_0) {
                                player.inventory.gold -= COST_HD;
                                Service.getInstance().sendMoney(player);
                                ChangeMapService.gI().changeMapBySpaceShip(player, 146, -1, -1);
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 14) {
                        if (select == 0) {
                            if (player.getSession().player.nPoint.power >= 80000000000L && player.inventory.gold > COST_HD && TaskService.gI().getIdTask(player) > ConstTask.TASK_24_0) {
                                player.inventory.gold -= COST_HD;
                                Service.getInstance().sendMoney(player);
                                ChangeMapService.gI().changeMapBySpaceShip(player, 146, -1, -1);
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 0) {
                        if (select == 0) {
                            if (player.getSession().player.nPoint.power >= 80000000000L && player.inventory.gold > COST_HD && TaskService.gI().getIdTask(player) > ConstTask.TASK_24_0) {
                                player.inventory.gold -= COST_HD;
                                Service.getInstance().sendMoney(player);
                                ChangeMapService.gI().changeMapBySpaceShip(player, 146, -1, -1);
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 147) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 0, -1, -1);
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 148) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 14, -1, -1);
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 146) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 7, -1, -1);
                        }
                        if (select == 1) {
                        }

                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 48) {
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOI_SKH_VIP);
                        }
                        if (select == 2) {
                            Message msg;
                            try {
                                if (player.gender == 0 ){
                                    Skill curSkill = SkillUtil.getSkillbyId(player, Skill.SUPER_KAME);
                                    if (curSkill.point == 0){
                                        Item honLinhThu = null;
                                        try {
                                            honLinhThu = InventoryServiceNew.gI().findItemBag(player, 1215);
                                        } catch (Exception e) {
    //                                        throw new RuntimeException(e);
                                        }
                                        if (player.nPoint.power >= 60000000000L) {
                                            if (honLinhThu == null || honLinhThu.quantity < 999) {
                                                this.npcChat(player, "Bạn không đủ 999 Bí Kíp Tuyệt Kỹ");
                                            }
                                            else if(player.inventory.ruby > 200_000 && honLinhThu.quantity >= 999)
                                            {
                                                player.inventory.ruby -= 200_000;
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 999);
                                                Service.getInstance().sendMoney(player);
                                                curSkill = SkillUtil.createSkill(Skill.SUPER_KAME, 1);
                                                SkillUtil.setSkill(player, curSkill);
                                                msg = Service.getInstance().messageSubCommand((byte) 23);
                                                msg.writer().writeShort(curSkill.skillId);
                                                player.sendMessage(msg);
                                                msg.cleanup();
                                            }else{
                                                    Service.getInstance().sendThongBao(player, "Không đủ Hồng ngọc");
                                            }
                                        }else {
                                            Service.getInstance().sendThongBao(player, "Yêu cầu sức mạnh trên 60 Tỷ");
                                        }
                                    }
                                    else if (curSkill.point > 0 && curSkill.point < 9){
                                        if(curSkill.currLevel == 1000 && player.inventory.ruby < 200_000){
                                            Service.getInstance().sendThongBao(player, "Không đủ Hồng ngọc");
                                        }
                                        else if(curSkill.currLevel == 1000 && player.inventory.ruby > 200_000){
                                            player.inventory.ruby -= 200_000;
                                            Service.getInstance().sendMoney(player);
                                            curSkill = SkillUtil.createSkill(Skill.SUPER_KAME, curSkill.point + 1);
                                            SkillUtil.setSkill(player, curSkill);
                                            msg = Service.getInstance().messageSubCommand((byte) 62);
                                            msg.writer().writeShort(curSkill.skillId);
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                        }else{
                                            Service.getInstance().sendThongBao(player, "Thông thạo của bạn chưa đủ 100%");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Tuyệt kĩ của bạn đã đạt cấp tối đa");
                                    }
                                }
                                if (player.gender == 1){
                                    Skill curSkill = SkillUtil.getSkillbyId(player, Skill.MA_PHONG_BA);
                                    if (curSkill.point == 0){
                                        Item manhTuyetKy = null;
                                        try {
                                            manhTuyetKy = InventoryServiceNew.gI().findItemBag(player, 1215);
                                        } catch (Exception e) {
    //                                        throw new RuntimeException(e);
                                        }
                                        if (player.nPoint.power >= 60000000000L) {
                                            if (manhTuyetKy == null || manhTuyetKy.quantity < 999) {
                                                this.npcChat(player, "Bạn không đủ 999 Bí Kíp Tuyệt Kỹ");
                                            }
                                            else if(player.inventory.ruby > 200_000 && manhTuyetKy.quantity >= 999)
                                            {
                                                player.inventory.ruby -= 200_000;
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, manhTuyetKy, 999);
                                                Service.getInstance().sendMoney(player);
                                                curSkill = SkillUtil.createSkill(Skill.MA_PHONG_BA, 1);
                                                SkillUtil.setSkill(player, curSkill);
                                                msg = Service.getInstance().messageSubCommand((byte) 23);
                                                msg.writer().writeShort(curSkill.skillId);
                                                player.sendMessage(msg);
                                                msg.cleanup();
                                            }else{
                                                Service.getInstance().sendThongBao(player, "Không đủ Hồng ngọc");
                                            }
                                        }else {
                                            Service.getInstance().sendThongBao(player, "Yêu cầu sức mạnh trên 60 Tỷ");
                                        }
                                    }else if (curSkill.point > 0 && curSkill.point < 9){
                                        if(curSkill.currLevel == 1000 && player.inventory.ruby < 200_000){
                                            Service.getInstance().sendThongBao(player, "Không đủ Hồng ngọc");
                                        }
                                        else if(curSkill.currLevel == 1000 && player.inventory.ruby > 200_000){
                                            player.inventory.ruby -= 200_000;
                                            Service.getInstance().sendMoney(player);
                                            curSkill = SkillUtil.createSkill(Skill.MA_PHONG_BA, curSkill.point + 1);
                                            SkillUtil.setSkill(player, curSkill);
                                            msg = Service.getInstance().messageSubCommand((byte) 62);
                                            msg.writer().writeShort(curSkill.skillId);
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                        }else{
                                            Service.getInstance().sendThongBao(player, "Thông thạo của bạn chưa đủ 100%");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Tuyệt kĩ của bạn đã đạt cấp tối đa");
                                    }
                                }
                                if (player.gender == 2){
                                    Skill curSkill = SkillUtil.getSkillbyId(player, Skill.LIEN_HOAN_CHUONG);
                                    if (curSkill.point == 0){
                                        Item honLinhThu = null;
                                        try {
                                            honLinhThu = InventoryServiceNew.gI().findItemBag(player, 1215);
                                        } catch (Exception e) {
    //                                        throw new RuntimeException(e);
                                        }
                                        if (player.nPoint.power >= 60000000000L) {
                                            if (honLinhThu == null || honLinhThu.quantity < 999) {
                                                this.npcChat(player, "Bạn không đủ 999 Bí Kíp Tuyệt Kỹ");
                                            }
                                            else if(player.inventory.ruby > 200_000 && honLinhThu.quantity >= 999)
                                            {
                                                player.inventory.ruby -= 200_000;
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 999);
                                                Service.getInstance().sendMoney(player);
                                                curSkill = SkillUtil.createSkill(Skill.LIEN_HOAN_CHUONG, 1);
                                                SkillUtil.setSkill(player, curSkill);
                                                msg = Service.getInstance().messageSubCommand((byte) 23);
                                                msg.writer().writeShort(curSkill.skillId);
                                                player.sendMessage(msg);
                                                msg.cleanup();
                                            }else{
                                                Service.getInstance().sendThongBao(player, "Không đủ Hồng ngọc");
                                            }
                                        }else {
                                            Service.getInstance().sendThongBao(player, "Yêu cầu sức mạnh trên 60 Tỷ");
                                        }
                                    }
                                    else if (curSkill.point > 0 && curSkill.point < 9){
                                        if(curSkill.currLevel == 1000 && player.inventory.ruby < 200_000){
                                            Service.getInstance().sendThongBao(player, "Không đủ Hồng ngọc");
                                        }
                                        else if(curSkill.currLevel == 1000 && player.inventory.ruby > 200_000){
                                            player.inventory.ruby -= 200_000;
                                            Service.getInstance().sendMoney(player);
                                            curSkill = SkillUtil.createSkill(Skill.LIEN_HOAN_CHUONG, curSkill.point + 1);
                                            SkillUtil.setSkill(player, curSkill);
                                            msg = Service.getInstance().messageSubCommand((byte) 62);
                                            msg.writer().writeShort(curSkill.skillId);
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                        }else{
                                            Service.getInstance().sendThongBao(player, "Thông thạo của bạn chưa đủ 100%");
                                        }
                                    }else {
                                        Service.getInstance().sendThongBao(player, "Tuyệt kĩ của bạn đã đạt cấp tối đa");
                                    }
                                }
                            }catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (select == 1) {
                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_SKH_VIP);
                        }

                        if (select == 3) {
                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.CHE_TAO_TRANG_BI_TS);
                        }                
                    } // hết map 48
                    else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.CHE_TAO_TRANG_BI_TS:
                                case CombineServiceNew.NANG_CAP_SKH_VIP:  
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;                                
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_CAP_DO_TS) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }                                              
                        }
                         else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_DOI_SKH_VIP) {
                        if (select == 0) {
                            CombineServiceNew.gI().startCombine(player);}
                        }
                    
                }
            }           
        };
    }

    //    public static Npc bill(int mapId, int status, int cx, int cy, int tempId, int avartar) {
//        return new Npc(mapId, status, cx, cy, tempId, avartar) {
//            @Override
//            public void openBaseMenu(Player player) {
//                if (canOpenNpc(player)) {
//                    if (this.mapId == 48) {
//                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ngươi muốn gì nào?" + player.inventory.coupon+, "Đóng");
//                    } else {
//                        super.openBaseMenu(player);
//                    }
//                }
//            }
//
//            @Override
//            public void confirmMenu(Player player, int select) {
//                if (canOpenNpc(player)) {
//                    switch (this.mapId) {
//                        case 48:
//                            switch (player.iDMark.getIndexMenu()) {
//                                case ConstNpc.BASE_MENU:
//                                    if (select == 0) {
//
//                                    }
//                                    break;
//                            }
//                            break;
//                    }
//                }
//            }
//        };
//    }
    public static Npc bill(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Ngươi muốn gì nào?",
                            "Xem Điểm ", "SHOP", "Top Sức Mạnh", "Top Nhiệm Vụ", "Đóng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 48:
                        case 5:
                            switch (player.iDMark.getIndexMenu()) {
                                case ConstNpc.BASE_MENU:
                                    if (select == 0) {
                                        createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ngươi đang có: " + player.inventory.coupon + " điểm", "Đóng");
                                        break;
                                    }
                                    if (select == 1) {
                                    //    this.npcChat(player, "Chưa biết bán gì?");
                                        ShopServiceNew.gI().opendShop(player, "BILL", true);
                                        break;

                                    }
                                    if (select == 2) {
                                //        Util.showListTop(player, (byte) 0);
                                        break;
                                    }
                                    if (select == 3) {
                                //        Util.showListTop(player, (byte) 1);
                                        break;
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
        };
    }
    
    public static Npc onggianoel(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Thăng Cấp\nTrang Bị ",
                                "Pha Lê Hóa\nTrang Bị\nThăng Cấp ",
                                "Ép Sao\nTrang Bị\nThăng Cấp ");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.THANG_CAP_TRANG_BI);
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHA_LE_HOA_TRANG_BI_THANG_CAP);
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.EP_SAO_TRANG_BI_THANG_CAP);
                                    break;
                            }                           
                        } 

                        else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.THANG_CAP_TRANG_BI:
                                case CombineServiceNew.PHA_LE_HOA_TRANG_BI_THANG_CAP:
                                case CombineServiceNew.EP_SAO_TRANG_BI_THANG_CAP:
                                
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc boMong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 47 || this.mapId == 84) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, cậu muốn tôi giúp gì?", "Nhiệm vụ\nhàng ngày", "Nhận ngọc\nmiễn phí", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 47 || this.mapId == 84) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    if (player.playerTask.sideTask.template != null) {
                                        String npcSay = "Nhiệm vụ hiện tại: " + player.playerTask.sideTask.getName() + " ("
                                                + player.playerTask.sideTask.getLevel() + ")"
                                                + "\nHiện tại đã hoàn thành: " + player.playerTask.sideTask.count + "/"
                                                + player.playerTask.sideTask.maxCount + " ("
                                                + player.playerTask.sideTask.getPercentProcess() + "%)\nSố nhiệm vụ còn lại trong ngày: "
                                                + player.playerTask.sideTask.leftTask + "/" + ConstTask.MAX_SIDE_TASK;
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_PAY_SIDE_TASK,
                                                npcSay, "Trả nhiệm\nvụ", "Hủy nhiệm\nvụ");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK,
                                                "Tôi có vài nhiệm vụ theo cấp bậc, "
                                                + "sức cậu có thể làm được cái nào?\b|5|Phần thưởng:\b|7| Dễ: 1000 Hồng Ngọc + 100TR Vàng + 1 Điểm Năng Động\n Bình thường: 2000 Hồng Ngọc + 200TR Vàng + 2 Điểm Năng Động\n Khó: 4000 Hồng Ngọc + 400TR Vàng + 3 Điểm Năng Động\n Siêu khó: 10000 Hồng Ngọc + 1 Tỷ Vàng + 4 Điểm Năng Động\n Địa ngục: 20000 Hồng Ngọc + 2 Tỷ Vàng + 5 Điểm Năng Động",
                                                "Dễ", "Bình thường", "Khó", "Siêu khó", "Địa ngục", "Từ chối");
                                    }
                                    break;
                                    case 1:
                                    player.achievement.Show();
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    TaskService.gI().changeSideTask(player, (byte) select);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PAY_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                    TaskService.gI().paySideTask(player);
                                    break;
                                case 1:
                                    TaskService.gI().removeSideTask(player);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc karin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu bé muốn mua gì nào?", "Cửa hàng", "Đóng");
                        }
                    } else if (this.mapId == 46 || this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Kính chào Ngài Linh thú sư!", "Cửa hàng", "Đóng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "BUNMA_FUTURE", true);
                            }
                        }
                    } else if (this.mapId == 46 || this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "KARIN", true);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc vados(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Ta Vừa Hắc Mắp Xêm Được Tóp Của Toàn Server\b|7|Người Muốn Xem Tóp Gì?",
                            "Tóp Sức Mạnh", "Top Nhiệm Vụ", "Tóp Pvp", "Tóp Ngũ Hành Sơn", "Đóng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 5:
                            switch (player.iDMark.getIndexMenu()) {
                                case ConstNpc.BASE_MENU:
                                    if (select == 0) {
                                        Service.gI().showListTop(player, Manager.topSM);
                                        break;
                                    }
                                    if (select == 1) {
                                        Service.gI().showListTop(player, Manager.topNV);
                                        break;
                                    }
                                    if (select == 2) {
                                        Service.gI().showListTop(player, Manager.topPVP);
                                        break;
                                    }
                                    if (select == 3) {
                                        Service.gI().showListTop(player, Manager.topNHS);
                                        break;
                                    }
                                    if (select == 4) {
                                        Service.gI().showListTop(player, Manager.topSD);
                                        break;
                                    }
                                break;
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc gokuSSJ_1(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 80) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xin chào, tôi có thể giúp gì cho cậu?", "Tới hành tinh\nYardart", "Từ chối");
                    } else if (this.mapId == 131) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xin chào, tôi có thể giúp gì cho cậu?", "Quay về", "Từ chối");
                    } else {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.BASE_MENU:
                            if (this.mapId == 131) {
                                if (select == 0) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 80, -1, 870);
                                }
                            }
                            if (this.mapId == 80) {
                                if (select == 0) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 131, -1, 870);
                                }
                            }                           
                            break;
                    }
                }
            }
        };
    }

    public static Npc gokuSSJ_2(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    try {
                        Item biKiep = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 590);
                        if (biKiep != null) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Bạn đang có " + biKiep.quantity + " bí kiếp.\n"
                                    + "Hãy kiếm đủ 10000 bí kiếp và 70k Hồng ngọc tôi sẽ dạy bạn cách dịch chuyển tức thời của người Yardart", "Học dịch\nchuyển", "Đóng");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    try {
                        Item biKiep = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 590);
                        if (biKiep != null) {
                            if (biKiep.quantity >= 10000 && InventoryServiceNew.gI().getCountEmptyBag(player) > 0 && player.inventory.ruby > 70_000) {
                                player.inventory.ruby -= 70_000;
                                Service.getInstance().sendMoney(player);
                                Item yardart = ItemService.gI().createNewItem((short) (player.gender + 592));
                                yardart.itemOptions.add(new Item.ItemOption(47, 400));
                                yardart.itemOptions.add(new Item.ItemOption(108, 10));
                                yardart.itemOptions.add(new Item.ItemOption(33, 1));
                                yardart.itemOptions.add(new Item.ItemOption(30, 1));
                                InventoryServiceNew.gI().addItemBag(player, yardart);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, biKiep, 10000);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.getInstance().sendThongBao(player, "Bạn vừa nhận được trang phục tộc Yardart");
                            }
                            else Service.getInstance().sendThongBao(player, "Cày tiếp đi con gà");
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        };
    }
    public static Npc mavuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, tôi có thể giúp gì cho cậu?", "Tây thánh địa", "Từ chối");
                    } else if (this.mapId == 156) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                //đến tay thanh dia
                                ChangeMapService.gI().changeMapBySpaceShip(player, 156, -1, 360);
                            }
                        }
                    } else if (this.mapId == 156) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                //về lanh dia bang hoi
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 153, -1, 432);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc kyGui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, 0, "Cửa hàng chúng tôi chuyên mua bán hàng hiệu, hàng độc, cảm ơn bạn đã ghé thăm.", "Hướng\ndẫn\nthêm", "Mua bán\nKý gửi", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (canOpenNpc(pl)) {
                    switch (select) {
                        case 0:
                            Service.gI().sendPopUpMultiLine(pl, tempId, avartar, "Cửa hàng chuyên nhận ký gửi mua bán vật phẩm\bChỉ với 5 hồng ngọc\bGiá trị ký gửi 10k-200Tr vàng hoặc 2-2k ngọc\bMột người bán, vạn người mua, mại dô, mại dô");
                            break;
                        case 1:
                            ShopKyGuiService.gI().openShopKyGui(pl);
                            break;

                    }
                }
            }
        };
    }


    public static Npc createNPC(int mapId, int status, int cx, int cy, int tempId) {
        int avatar = Manager.NPC_TEMPLATES.get(tempId).avatar;
        try {
            switch (tempId) {
                case ConstNpc.CUA_HANG_KY_GUI:
                    return kyGui(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.MR_POPO:
                      return popo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUNG_LINH_THU:
                    return trungLinhThu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GHI_DANH:
                    return GhiDanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.POTAGE:
                    return poTaGe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUY_LAO_KAME:
                    return quyLaoKame(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUONG_LAO_GURU:
                    return truongLaoGuru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VUA_VEGETA:
                    return vuaVegeta(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.ONG_GOHAN:
                case ConstNpc.ONG_MOORI:
                case ConstNpc.ONG_PARAGUS:
                    return ongGohan_ongMoori_ongParagus(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BUNMA:
                    return bulmaQK(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DENDE:
                    return dende(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.APPULE:
                    return appule(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DR_DRIEF:
                    return drDrief(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CARGO:
                    return cargo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CUI:
                    return cui(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.SANTA:
                    return santa(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.URON:
                    return uron(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BA_HAT_MIT:
                    return baHatMit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RUONG_DO:
                    return ruongDo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DAU_THAN:
                    return dauThan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CALICK:
                    return calick(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.JACO:
                    return jaco(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THUONG_DE:
                    return thuongDe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VADOS:
                    return vados(mapId, status, cx, cy, tempId, avatar);
//                case ConstNpc.POTAGE:
//                    return Potage(mapId, status, cx, cy, tempId, avatar);    
                case ConstNpc.THAN_VU_TRU:
                    return thanVuTru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.KIBIT:
                    return kibit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.OSIN:
                    return osin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LY_TIEU_NUONG:
                    return npclytieunuong54(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LINH_CANH:
                    return linhCanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.FIDE:
                    return fide(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GIUMA_DAU_BO:
                    return giuma(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUA_TRUNG:
                    return quaTrung(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DUA_HAU:
                    return duahau(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.UUB:
                    return npcuub(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUOC_VUONG:
                    return quocVuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BUNMA_TL:
                    return bulmaTL(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.Monaito:
                    return monaito(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.Jiren:
                    return jiren(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.ZAMASU:
                    return zamasu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOHANSSJ:
                    return gohanssj(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_OMEGA:
                    return rongOmega(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_1S:
                case ConstNpc.RONG_2S:
                case ConstNpc.RONG_3S:
                case ConstNpc.RONG_4S:
                case ConstNpc.RONG_5S:
                case ConstNpc.RONG_6S:
                case ConstNpc.RONG_7S:
                    return rong1_to_7s(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.NPC_64:
                    return npcThienSu64(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BILL:
                    return bill(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.ONG_GIA_NOEL:
                    return onggianoel(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BO_MONG:
                    return boMong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THAN_MEO_KARIN:
                    return karin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOKU_SSJ:
                    return gokuSSJ_1(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CAY_THONG_NOEL:
                    return caythong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOKU_SSJ_:
                    return gokuSSJ_2(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DUONG_TANG:
                    return duongtank(mapId, status, cx, cy, tempId, avatar);
                default:
                    return new Npc(mapId, status, cx, cy, tempId, avatar) {
                        @Override
                        public void openBaseMenu(Player player) {
                            if (canOpenNpc(player)) {
                                super.openBaseMenu(player);
                            }
                        }

                        @Override
                        public void confirmMenu(Player player, int select) {
                            if (canOpenNpc(player)) {
//                                ShopService.gI().openShopNormal(player, this, ConstNpc.SHOP_BUNMA_TL_0, 0, player.gender);
                            }
                        }
                    };
            }
        } catch (Exception e) {
            Logger.logException(NpcFactory.class, e, "Lỗi load npc");
            return null;
        }
    }

    //girlbeo-mark
    public static void createNpcRongThieng() {
        Npc npc = new Npc(-1, -1, -1, -1, ConstNpc.RONG_THIENG, -1) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:

                        break;
                    case ConstNpc.SHENRON_CONFIRM:
                        if (select == 0) {
                            SummonDragon.gI().confirmWish();
                        } else if (select == 1) {
                            SummonDragon.gI().reOpenShenronWishes(player);
                        }
                        break;
                    case ConstNpc.SHENRON_1_1:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_1 && select == SHENRON_1_STAR_WISHES_1.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_2, SHENRON_SAY, SHENRON_1_STAR_WISHES_2);
                            break;
                        }
                    case ConstNpc.SHENRON_1_2:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_2 && select == SHENRON_1_STAR_WISHES_2.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_1, SHENRON_SAY, SHENRON_1_STAR_WISHES_1);
                            break;
                        }
                    case ConstNpc.SHENRON_2:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_2 && select == SHENRON_2_STARS_WHISHES.length) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_2, SHENRON_SAY, SHENRON_2_STARS_WHISHES);
                            break;
                        }
                    default:
                        SummonDragon.gI().showConfirmShenron(player, player.iDMark.getIndexMenu(), (byte) select);
                        break;
                }
            }
        };
    }

    public static void createNpcConMeo() {
        Npc npc;
        npc = new Npc(-1, -1, -1, -1, ConstNpc.CON_MEO, 351) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:

                        break;
                    case ConstNpc.MAKE_MATCH_PVP: //                        if (player.getSession().actived) 
                    {
                        if (Maintenance.isRuning) {
                            break;
                        }
                        PVPService.gI().sendInvitePVP(player, (byte) select);
                        break;
                    }
//                        else {
//                            Service.getInstance().sendThongBao(player, "|5|VUI LÒNG KÍCH HOẠT TÀI KHOẢN TẠI\n|7|NROGOD.COM\n|5|ĐỂ MỞ KHÓA TÍNH NĂNG");
//                            break;
//                        }
                    case ConstNpc.MAKE_FRIEND:
                        if (select == 0) {
                            Object playerId = PLAYERID_OBJECT.get(player.id);
                            if (playerId != null) {
                                FriendAndEnemyService.gI().acceptMakeFriend(player,
                                        Integer.parseInt(String.valueOf(playerId)));
                            }
                        }
                        break;
                    case ConstNpc.REVENGE:
                        if (select == 0) {
                            PVPService.gI().acceptRevenge(player);
                        }
                        break;
                    case ConstNpc.TUTORIAL_SUMMON_DRAGON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        }
                        break;
                    case ConstNpc.SUMMON_SHENRON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        } else if (select == 1) {
                            SummonDragon.gI().summonShenron(player);
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM1105:
                        if (select == 0) {
                            IntrinsicService.gI().sattd(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().satnm(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().setxd(player);
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2000:
                    case ConstNpc.MENU_OPTION_USE_ITEM2001:
                    case ConstNpc.MENU_OPTION_USE_ITEM2002:
                        try {
                            ItemService.gI().OpenSKH(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2003:
                    case ConstNpc.MENU_OPTION_USE_ITEM2004:
                    case ConstNpc.MENU_OPTION_USE_ITEM2005:
                        try {
                            ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM736:
                        try {
                            ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2048:
                        try {
                            ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().showAllIntrinsic(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().showConfirmOpen(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().showConfirmOpenVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().open(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC_VIP:
                        if (select == 0) {
                            IntrinsicService.gI().openVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_LEAVE_CLAN:
                        if (select == 0) {
                            ClanService.gI().leaveClan(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_NHUONG_PC:
                        if (select == 0) {
                            ClanService.gI().phongPc(player, (int) PLAYERID_OBJECT.get(player.id));
                        }
                        break;
                    case ConstNpc.BAN_PLAYER:
                        if (select == 0) {
                            PlayerService.gI().banPlayer((Player) PLAYERID_OBJECT.get(player.id));
                            Service.getInstance().sendThongBao(player, "Ban người chơi " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                        }
                        break;

                    case ConstNpc.BUFF_PET:
                        if (select == 0) {
                            Player pl = (Player) PLAYERID_OBJECT.get(player.id);
                            if (pl.pet == null) {
                                PetService.gI().createNormalPet(pl);
                                Service.getInstance().sendThongBao(player, "Phát đệ tử cho " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                            }
                        }
                        break;
                    case ConstNpc.MENU_ADMIN:
                        switch (select) {
                            case 0:
                                for (int i = 14; i <= 20; i++) {
                                    Item item = ItemService.gI().createNewItem((short) i);
                                    InventoryServiceNew.gI().addItemBag(player, item);
                                }
                                InventoryServiceNew.gI().sendItemBags(player);
                                break;
                            case 1:
                                if (player.pet == null) {
                                    PetService.gI().createNormalPet(player);
                                } else {
                                    if (player.pet.typePet == 1) {
                                        PetService.gI().changePicPet(player);
                                    } else if (player.pet.typePet == 2) {
                                        PetService.gI().changeMabuPet(player);
                                    }
                                    PetService.gI().changeBerusPet(player);
                                }
                                break;
                            case 2:
                                if (player.isAdmin()) {
                                    System.out.println(player.name);
//                                PlayerService.gI().baoTri();
                                Maintenance.gI().start(15);
                                System.out.println(player.name);
                                }
                                break;
                            case 3:
                                Input.gI().createFormFindPlayer(player);
                                break;
                            case 4:
                                BossManager.gI().showListBoss(player);
                                break;
                            case 5:
                                Input.gI().createFormSenditem1(player);
                        }
                        break;

                    case ConstNpc.menutd:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().settaiyoken(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setgenki(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setkamejoko(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.menunm:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().setgodki(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setgoddam(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setsummon(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.menuxd:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().setgodgalick(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setmonkey(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setgodhp(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.CONFIRM_DISSOLUTION_CLAN:
                        switch (select) {
                            case 0:
                                Clan clan = player.clan;
                                clan.deleteDB(clan.id);
                                Manager.CLANS.remove(clan);
                                player.clan = null;
                                player.clanMember = null;
                                ClanService.gI().sendMyClan(player);
                                ClanService.gI().sendClanId(player);
                                Service.getInstance().sendThongBao(player, "Đã giải tán bang hội.");
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_ACTIVE:
                        switch (select) {
                            case 0:
                                if (player.getSession().goldBar >= 20) {
                                    player.getSession().actived = true;
                                    if (PlayerDAO.subGoldBar(player, 20)) {
                                        Service.getInstance().sendThongBao(player, "Đã mở thành viên thành công!");
                                        break;
                                    } else {
                                        this.npcChat(player, "Lỗi vui lòng báo admin...");
                                    }
                                }
//                                Service.getInstance().sendThongBao(player, "Bạn không có vàng\n Vui lòng NROGOD.COM để nạp thỏi vàng");
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND:
                        if (select == 0) {
                            for (int i = 0; i < player.inventory.itemsBoxCrackBall.size(); i++) {
                                player.inventory.itemsBoxCrackBall.set(i, ItemService.gI().createItemNull());
                            }
                            player.inventory.itemsBoxCrackBall.clear();
                            Service.getInstance().sendThongBao(player, "Đã xóa hết vật phẩm trong rương");
                        }
                        break;
                    case ConstNpc.MENU_FIND_PLAYER:
                        Player p = (Player) PLAYERID_OBJECT.get(player.id);
                        if (p != null) {
                            switch (select) {
                                case 0:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMapYardrat(player, p.zone, p.location.x, p.location.y);
                                    }
                                    break;
                                case 1:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMap(p, player.zone, player.location.x, player.location.y);
                                    }
                                    break;
                                case 2:
                                    Input.gI().createFormChangeName(player, p);
                                    break;
                                case 3:
                                    String[] selects = new String[]{"Đồng ý", "Hủy"};
                                    NpcService.gI().createMenuConMeo(player, ConstNpc.BAN_PLAYER, -1,
                                            "Bạn có chắc chắn muốn ban " + p.name, selects, p);
                                    break;
                                case 4:
                                    Service.getInstance().sendThongBao(player, "Kik người chơi " + p.name + " thành công");
                                    Client.gI().getPlayers().remove(p);
                                    Client.gI().kickSession(p.getSession());
                                    break;
                            }
                        }
                        break;
//                    case ConstNpc.DOI_TIEN:  // 
//                        switch (select) {
//                            case 0:
//                                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_DOITIEN1, 564,
//                                        "Con đang có " + player.getSession().vnd + "VND\n Chào mừng Server Oh My God OPEN chính thức\nSever hiện tại x2 đổi vàng và hồng ngọc","20.000vnđ\n100 Thỏi vàng", "50.000vnđ\n250 Thỏi vàng", "100.000vnđ\n500 Thỏi vàng", "200.000vnđ\n1000 Thỏi vàng");
//                                                break;
//                                                
//                            case 1:  //
//
//                                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_DOITIEN2, 564,
//                                        "Con đang có " + player.getSession().vnd + " VND\n Chào mừng Server Oh My God OPEN chính thức\nSever hiện tại x2 đổi vàng và hồng ngọc", "20.000vnđ\n20k ngọc hồng", "50.000vnđ\n50k ngọc hồng", "100.000vnđ\n100k ngọc hồng", "200.000vnđ\n200k ngọc hồng");
//                                break;
//                                
//                            case 2:  //
//
//                                break;
//
//                        }
//                        break;
//                    case ConstNpc.MENU_DOITIEN1:
//                        switch (select) {
//                            case 0:
//                                if (player.getSession().vnd >= 20000) {
//                                    try {
//                                        Item thoivang = ItemService.gI().createNewItem((short) 457, 100);
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        PlayerDAO.subvnd(player, 20000);
//                                        GirlkunDB.executeUpdate("update player set vnd = (vnd + 20000) where id = " + player.id);
//                                        Service.getInstance().sendThongBao(player, "Đổi thành công 100tv");
//                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player, "Bạn không đủ tiền");
//
//                                }
//                                break;
//
//                            case 1:
//                                if (player.getSession().vnd >= 50000) {
//                                    try {
//                                        Item thoivang = ItemService.gI().createNewItem((short) 457, 250);
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        PlayerDAO.subvnd(player, 50000);
//                                        GirlkunDB.executeUpdate("update player set vnd = (vnd + 50000) where id = " + player.id);
//                                        Service.getInstance().sendThongBao(player, "Đổi thành công 250tv");
//                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player, "Bạn không đủ tiền");
//
//                                }
//                                break;
//                            case 2:  // 
//                                if (player.getSession().vnd >= 100000) {
//                                    try {
//                                        Item thoivang = ItemService.gI().createNewItem((short) 457, 500);
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        PlayerDAO.subvnd(player, 100000);
//                                        GirlkunDB.executeUpdate("update player set vnd = (vnd + 100000) where id = " + player.id);
//                                        Service.getInstance().sendThongBao(player, "Đổi thành công 500tv");
//                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player, "Bạn không đủ tiền");
//
//                                }
//                                break;
//                            case 3:  // 
//                                if (player.getSession().vnd >= 200000) {
//                                    try {
//                                        Item thoivang = ItemService.gI().createNewItem((short) 457, 1000);
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        PlayerDAO.subvnd(player, 200000);
//                                        GirlkunDB.executeUpdate("update player set vnd = (vnd + 200000) where id = " + player.id);
//                                        Service.getInstance().sendThongBao(player, "Đổi thành công 1000tv");
//                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player, "Bạn không đủ tiền");
//
//                                }
//                                break;
//                        }
//                        break;
//                    case ConstNpc.MENU_DOITIEN2:
//                        switch (select) {
//                            case 0:
//                                if (player.getSession().vnd >= 20000) {
//                                    try {
//                                        player.inventory.ruby += 20000;
//                                        Service.getInstance().sendMoney(player);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        PlayerDAO.subvnd(player, 20000);
//                                        GirlkunDB.executeUpdate("update player set vnd = (vnd + 20000) where id = " + player.id);
//                                        Service.getInstance().sendThongBao(player, "Đổi thành công 20.000 ngọc hồng");
//                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player, "Bạn không đủ tiền");
//
//                                }
//                                break;
//
//                            case 1:
//                                if (player.getSession().vnd >= 50000) {
//
//                                    try {
//                                        player.inventory.ruby += 50000;
//                                        Service.getInstance().sendMoney(player);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        PlayerDAO.subvnd(player, 50000);
//                                        GirlkunDB.executeUpdate("update player set vnd = (vnd + 50000) where id = " + player.id);
//                                        Service.getInstance().sendThongBao(player, "Đổi thành công 50.000 ngọc hồng");
//                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player, "Bạn không đủ tiền");
//
//                                }
//                                break;
//                            case 2:  // 
//                                if (player.getSession().vnd >= 100000) {
//
//                                    try {
//                                        player.inventory.ruby += 100000;
//                                        Service.getInstance().sendMoney(player);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        PlayerDAO.subvnd(player, 100000);
//                                        GirlkunDB.executeUpdate("update player set vnd = (vnd + 100000) where id = " + player.id);
//                                        Service.getInstance().sendThongBao(player, "Đổi thành công 100.000 ngọc hồng");
//                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player, "Bạn không đủ tiền");
//
//                                }
//                                break;
//                            case 3:  // 
//                                if (player.getSession().vnd >= 200000) {
//                                    try {
//                                        player.inventory.ruby += 200000;
//                                        Service.getInstance().sendMoney(player);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        PlayerDAO.subvnd(player, 200000);
//                                        GirlkunDB.executeUpdate("update player set vnd = (vnd + 200000) where id = " + player.id);
//
//                                        Service.getInstance().sendThongBao(player, "Đổi thành công 200.000 ngọc hồng");
//                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                } else {
//                                    Service.getInstance().sendThongBao(player, "Bạn không đủ tiền");
//
//                                }
//                                break;
//
//                        }
//                        break;
                    case ConstNpc.MENU_EVENT:
                        switch (select) {
                            case 0:
                                Service.getInstance().sendThongBaoOK(player, "Điểm sự kiện: " + player.inventory.event + " ngon ngon...");
                                break;
                            case 1:
                            //    Util.showListTop(player, (byte) 2);
                                break;
                            case 2:
                                Service.getInstance().sendThongBao(player, "Sự kiện đã kết thúc...");
//                                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_GIAO_BONG, -1, "Người muốn giao bao nhiêu bông...",
//                                        "100 bông", "1000 bông", "10000 bông");
                                break;
                            case 3:
                                Service.getInstance().sendThongBao(player, "Sự kiện đã kết thúc...");
//                                NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN, -1, "Con có thực sự muốn đổi thưởng?\nPhải giao cho ta 3000 điểm sự kiện đấy... ",
//                                        "Đồng ý", "Từ chối");
                                break;

                        }
                        break;
                    case ConstNpc.MENU_GIAO_BONG:
                        ItemService.gI().giaobong(player, (int) Util.tinhLuyThua(10, select + 2));
                        break;
                    case ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN:
                        if (select == 0) {
                            ItemService.gI().openBoxVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_TELE_NAMEC:
                        if (select == 0) {
                            NgocRongNamecService.gI().teleportToNrNamec(player);
                            player.inventory.subGemAndRuby(50);
                            Service.getInstance().sendMoney(player);
                        }
                        break;
                }
            }
        };
    }

}
