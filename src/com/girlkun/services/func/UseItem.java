package com.girlkun.services.func;

import com.girlkun.card.Card;
import com.girlkun.card.RadarCard;
import com.girlkun.card.RadarService;
import com.girlkun.consts.ConstMap;
import com.girlkun.models.item.Item;
import com.girlkun.consts.ConstNpc;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Inventory;
import static com.girlkun.models.player.Inventory.LIMIT_GOLD;
import com.girlkun.services.NpcService;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.network.io.Message;
import com.girlkun.utils.SkillUtil;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import com.girlkun.server.io.MySession;
import com.girlkun.services.ItemService;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.PetService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.TaskService;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.MapService;
import com.girlkun.services.NgocRongNamecService;
import com.girlkun.services.RewardService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.TimeUtil;
import java.util.Date;

public class UseItem {

    private static final int ITEM_BOX_TO_BODY_OR_BAG = 0;
    private static final int ITEM_BAG_TO_BOX = 1;
    private static final int ITEM_BODY_TO_BOX = 3;
    private static final int ITEM_BAG_TO_BODY = 4;
    private static final int ITEM_BODY_TO_BAG = 5;
    private static final int ITEM_BAG_TO_PET_BODY = 6;
    private static final int ITEM_BODY_PET_TO_BAG = 7;

    private static final byte DO_USE_ITEM = 0;
    private static final byte DO_THROW_ITEM = 1;
    private static final byte ACCEPT_THROW_ITEM = 2;
    private static final byte ACCEPT_USE_ITEM = 3;
    public static final int[][][] LIST_ITEM_CLOTHES = {
            // áo , quần , găng ,giày,rada
            //td -> nm -> xd
            {{0, 33, 3, 34, 136, 137, 138, 139, 230, 231, 232, 233, 555}, {6, 35, 9, 36, 140, 141, 142, 143, 242, 243, 244, 245, 556}, {21, 24, 37, 38, 144, 145, 146, 147, 254, 255, 256, 257, 562}, {27, 30, 39, 40, 148, 149, 150, 151, 266, 267, 268, 269, 563}, {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281, 561}},
            {{1, 41, 4, 42, 152, 153, 154, 155, 234, 235, 236, 237, 557}, {7, 43, 10, 44, 156, 157, 158, 159, 246, 247, 248, 249, 558}, {22, 46, 25, 45, 160, 161, 162, 163, 258, 259, 260, 261, 564}, {28, 47, 31, 48, 164, 165, 166, 167, 270, 271, 272, 273, 565}, {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281, 561}},
            {{2, 49, 5, 50, 168, 169, 170, 171, 238, 239, 240, 241, 559}, {8, 51, 11, 52, 172, 173, 174, 175, 250, 251, 252, 253, 560}, {23, 53, 26, 54, 176, 177, 178, 179, 262, 263, 264, 265, 566}, {29, 55, 32, 56, 180, 181, 182, 183, 274, 275, 276, 277, 567}, {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281, 561}}
    };

    private static UseItem instance;

    private UseItem() {

    }

    public static UseItem gI() {
        if (instance == null) {
            instance = new UseItem();
        }
        return instance;
    }

    public void getItem(MySession session, Message msg) {
        Player player = session.player;

        TransactionService.gI().cancelTrade(player);
        try {
            int type = msg.reader().readByte();
            int index = msg.reader().readByte();
            if (index == -1) {
                return;
            }
            switch (type) {
                case ITEM_BOX_TO_BODY_OR_BAG:
                    InventoryServiceNew.gI().itemBoxToBodyOrBag(player, index);
                    TaskService.gI().checkDoneTaskGetItemBox(player);
                    break;
                case ITEM_BAG_TO_BOX:
                    InventoryServiceNew.gI().itemBagToBox(player, index);
                    break;
                case ITEM_BODY_TO_BOX:
                    InventoryServiceNew.gI().itemBodyToBox(player, index);
                    break;
                case ITEM_BAG_TO_BODY:
                    InventoryServiceNew.gI().itemBagToBody(player, index);
                    break;
                case ITEM_BODY_TO_BAG:
                    InventoryServiceNew.gI().itemBodyToBag(player, index);
                    break;
                case ITEM_BAG_TO_PET_BODY:
                    InventoryServiceNew.gI().itemBagToPetBody(player, index);
                    break;
                case ITEM_BODY_PET_TO_BAG:
                    InventoryServiceNew.gI().itemPetBodyToBag(player, index);
                    break;
            }
            player.setClothes.setup();
            if (player.pet != null) {
                player.pet.setClothes.setup();
            }
            player.setClanMember();
            Service.getInstance().point(player);
        } catch (Exception e) {
            Logger.logException(UseItem.class, e);

        }
    }

    public void testItem(Player player, Message _msg) {
        TransactionService.gI().cancelTrade(player);
        Message msg;
        try {
            byte type = _msg.reader().readByte();
            int where = _msg.reader().readByte();
            int index = _msg.reader().readByte();
            System.out.println("type: " + type);
            System.out.println("where: " + where);
            System.out.println("index: " + index);
        } catch (Exception e) {
            Logger.logException(UseItem.class, e);
        }
    }

    public void doItem(Player player, Message _msg) {
        TransactionService.gI().cancelTrade(player);
        Message msg;
        byte type;
        try {
            type = _msg.reader().readByte();
            int where = _msg.reader().readByte();
            int index = _msg.reader().readByte();
//            System.out.println(type + " " + where + " " + index);
            switch (type) {
                case DO_USE_ITEM:
                    if (player != null && player.inventory != null) {
                        if (index != -1) {
                            Item item = player.inventory.itemsBag.get(index);
                            if (item.isNotNullItem()) {
                                if (item.template.type == 7) {
                                    msg = new Message(-43);
                                    msg.writer().writeByte(type);
                                    msg.writer().writeByte(where);
                                    msg.writer().writeByte(index);
                                    msg.writer().writeUTF("Bạn chắc chắn học " + player.inventory.itemsBag.get(index).template.name + "?");
                                    player.sendMessage(msg);
                                } else {
                                    UseItem.gI().useItem(player, item, index);
                                }
                            }
                        } else {
                            this.eatPea(player);
                        }
                    }
                    break;
                case DO_THROW_ITEM:
                    if (!(player.zone.map.mapId == 21 || player.zone.map.mapId == 22 || player.zone.map.mapId == 23)) {
                        Item item = null;
                        if (where == 0) {
                            item = player.inventory.itemsBody.get(index);
                        } else {
                            item = player.inventory.itemsBag.get(index);
                        }
                        msg = new Message(-43);
                        msg.writer().writeByte(type);
                        msg.writer().writeByte(where);
                        msg.writer().writeByte(index);
                        msg.writer().writeUTF("Bạn chắc chắn muốn vứt " + item.template.name + "?");
                        player.sendMessage(msg);
                    } else {
                        Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    }
                    break;
                case ACCEPT_THROW_ITEM:
                    InventoryServiceNew.gI().throwItem(player, where, index);
                    Service.getInstance().point(player);
                    InventoryServiceNew.gI().sendItemBags(player);
                    break;
                case ACCEPT_USE_ITEM:
                    UseItem.gI().useItem(player, player.inventory.itemsBag.get(index), index);
                    break;
            }
        } catch (Exception e) {
//            Logger.logException(UseItem.class, e);
        }
    }

    private void useItem(Player pl, Item item, int indexBag) {
        if (item.template.strRequire <= pl.nPoint.power) {
            switch (item.template.type) {
                case 7: //sách học, nâng skill
                    learnSkill(pl, item);
                    break;
                case 6: //đậu thần
                    this.eatPea(pl);
                    break;
                case 12: //ngọc rồng các loại
                    controllerCallRongThan(pl, item);
                    break;
                case 23: //thú cưỡi mới
                case 24: //thú cưỡi cũ
                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                    break;
                case 11: //item bag
                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                    Service.getInstance().sendFlagBag(pl);
                    break;
                case 33:
                    UseCard(pl, item);
                    break;
                case 75: 
                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                    Service.getInstance().sendchienlinh(pl, (short) (item.template.iconID - 1));
                    break;
                case 72: {
                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                    Service.getInstance().sendPetFollow(pl, (short) (item.template.iconID - 1));
                    break;
                }
                default:
                    switch (item.template.id) {
                        case 457:
                            pl.inventory.gold+=500000000;
                            Service.getInstance().sendMoney(pl);
                              InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            InventoryServiceNew.gI().sendItemBags(pl);
                            break;
                        case 1244:
                            if ( pl.inventory.gold >= LIMIT_GOLD )
                            {
                            Service.getInstance().sendThongBao(pl, "Đã đạt giới hạn vàng");
                                return;
                            }else{
                            pl.inventory.gold+=2000000000L;
                            Service.getInstance().sendMoney(pl);
                              InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            InventoryServiceNew.gI().sendItemBags(pl);
                            break;}
                        case 1245:
                            pl.nPoint.power+=10000000000L;                                       
                            pl.nPoint.tiemNang+=10000000000L;
                            Service.getInstance().sendMoney(pl);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            InventoryServiceNew.gI().sendItemBags(pl);
                            break;
                        case 1132:
                            pl.inventory.ruby+=10000;
                            pl.inventory.gem+=10000;
                            Service.getInstance().sendMoney(pl);
                              InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            InventoryServiceNew.gI().sendItemBags(pl);
                            break;
                        case 992:
                            pl.type = 1;
                            pl.maxTime = 5;
                            Service.getInstance().Transport(pl);
                            break;
                        case 361:
                            if (pl.idNRNM != -1) {
                                Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
                                return;
                            }
                            pl.idGo = (short) Util.nextInt(0, 6);
                            NpcService.gI().createMenuConMeo(pl, ConstNpc.CONFIRM_TELE_NAMEC, -1, "1 Sao (" + NgocRongNamecService.gI().getDis(pl, 0, (short) 353) + " m)\n2 Sao (" + NgocRongNamecService.gI().getDis(pl, 1, (short) 354) + " m)\n3 Sao (" + NgocRongNamecService.gI().getDis(pl, 2, (short) 355) + " m)\n4 Sao (" + NgocRongNamecService.gI().getDis(pl, 3, (short) 356) + " m)\n5 Sao (" + NgocRongNamecService.gI().getDis(pl, 4, (short) 357) + " m)\n6 Sao (" + NgocRongNamecService.gI().getDis(pl, 5, (short) 358) + " m)\n7 Sao (" + NgocRongNamecService.gI().getDis(pl, 6, (short) 359) + " m)", "Đến ngay\nViên " + (pl.idGo + 1) + " Sao\n50 ngọc", "Kết thức");
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            InventoryServiceNew.gI().sendItemBags(pl);
                            break;
                        case 942:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 966, 967, 968);
                            Service.getInstance().point(pl);
                            break;

                        case 1336:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1449, 1450, 1451);
                            Service.getInstance().point(pl);
                            break;
                        case 1337:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1452, 1453, 1454);
                            Service.getInstance().point(pl);
                            break;
                        case 1338:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1455, 1456, 1457);
                            Service.getInstance().point(pl);
                            break;
                        case 1328:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1420,	1421,	1422);
                            Service.getInstance().point(pl);
                            break;
                        case 1329:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1423,	1424	,1425);
                            Service.getInstance().point(pl);
                            break;
                        case 943:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 969, 970, 971);
                            Service.getInstance().point(pl);
                            break;
                        case 944:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 972, 973, 974);
                            Service.getInstance().point(pl);
                            break;
                        case 967:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1050, 1051, 1052);
                            Service.getInstance().point(pl);
                            break;
                        case 1107:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1183, 1184, 1185);
                            Service.getInstance().point(pl);
                            break;
                        case 1140:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1285, 1286, 1287);
                            Service.getInstance().point(pl);
                            break;
                        case 1133:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1261, 1262, 1263);
                            Service.getInstance().point(pl);
                            break;
                        case 1180:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1270, 1271, 1272);
                            Service.getInstance().point(pl);
                            break;
                        case 1181:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1273, 1274, 1275);
                            Service.getInstance().point(pl);
                            break;
                        case 1196:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1294, 1295, 1296);
                            Service.getInstance().point(pl);
                            break;
                        case 1197:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1297, 1298, 1299);
                            Service.getInstance().point(pl);
                            break;
                        case 1198:
                            InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                            PetService.Pet2(pl, 1300, 1301, 1302);
                            Service.getInstance().point(pl);
                            break;
                        case 823:
                            openTuiqua(pl, item);
                        
                        case 1247:
                            openQuadenbu(pl, item);

                        case 211: //nho tím
                        case 212: //nho xanh
                            eatGrapes(pl, item);
                            break;
                        case 1105://hop qua skh, item 2002 xd
                            UseItem.gI().Hopts(pl, item);
                            break;
                        case 342:
                        case 343:
                        case 344:
                        case 345:
                            if (pl.zone.items.stream().filter(it -> it != null && it.itemTemplate.type == 22).count() < 5) {
                                Service.getInstance().DropVeTinh(pl, item, pl.zone, pl.location.x, pl.location.y);
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            } else {
                                Service.getInstance().sendThongBao(pl, "Đặt ít thôi con");
                            }
                            break;
                        case 380: //cskb
                            openCSKB(pl, item);
                            break;
                        case 722: //cskb
                            openCSH(pl, item);
                            break;
                        case 570: //cskb
                            openWoodChest(pl, item);
                            break;
                        case 381: //cuồng nộ
                        case 382: //bổ huyết
                        case 383: //bổ khí
                        case 384: //giáp xên
                        case 385: //ẩn danh
                        case 379: //máy dò capsule
                        case 1201:

                        case 663: //bánh pudding
                        case 664: //xúc xíc
                        case 665: //kem dâu
                        case 666: //mì ly
                        case 667:  //sushi
                        case 579: // Duoi khi
                        case 1099:
                        case 1100:
                        case 1101:
                        case 1102:
                        case 1103:
                            useItemTime(pl, item);
                            break;
                        case 521: //tdlt
                            useTDLT(pl, item);
                            break;
                        case 454: //bông tai
                            UseItem.gI().usePorata(pl);
                            break;
                        case 921: //bông tai
                            UseItem.gI().usePorata2(pl);
                            break;
                        case 1129: //bông tai
                            UseItem.gI().usePorata3(pl);
                            break;
                        case 193: //gói 10 viên capsule
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                        case 194: //capsule đặc biệt
                            openCapsuleUI(pl);
                            break;
                        case 401: //đổi đệ tử
                            changePet(pl, item);
                            break;
                        case 1108: //đổi đệ tử
                            changePetBerus(pl, item);
                            break;
                        case 543: //đổi đệ tử
                            {
                            if (InventoryServiceNew.gI().getCountEmptyBag(pl) == 0) {
                                Service.getInstance().sendThongBao(pl, "Hành trang không đủ chỗ trống");
                            } else {
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                                Item linhThu = ItemService.gI().createNewItem((short) Util.nextInt(942, 944));
                                linhThu.itemOptions.add(new Item.ItemOption(50, 15));
                                linhThu.itemOptions.add(new Item.ItemOption(77, 10));
                                linhThu.itemOptions.add(new Item.ItemOption(103, 10));
                                linhThu.itemOptions.add(new Item.ItemOption(80, 5));
                                linhThu.itemOptions.add(new Item.ItemOption(81, 5));
                                linhThu.itemOptions.add(new Item.ItemOption(30, 1));
                                InventoryServiceNew.gI().addItemBag(pl, linhThu);
                                InventoryServiceNew.gI().sendItemBags(pl);
                                Service.getInstance().sendThongBao(pl, "Chúc mừng bạn nhận được Pet Lỏ " + linhThu.template.name);
                            }
                            break;
                        }
                        case 542: //đổi đệ tử
                            changePetPic(pl, item);
                            break;
                        
                        case 402: //sách nâng chiêu 1 đệ tử
                        case 403: //sách nâng chiêu 2 đệ tử
                        case 404: //sách nâng chiêu 3 đệ tử
                        case 759: //sách nâng chiêu 4 đệ tử
                            upSkillPet(pl, item);
                            break;
                        
                        case 2000://hop qua skh, item 2000 td
                        case 2001://hop qua skh, item 2001 nm
                        case 2002://hop qua skh, item 2002 xd
                            UseItem.gI().ItemSKH(pl, item);
                            break;
                          


                        case 2003://hop qua skh, item 2003 td
                        case 2004://hop qua skh, item 2004 nm
                        case 2005://hop qua skh, item 2005 xd
                            UseItem.gI().ItemDHD(pl, item);
                            break;
                        case 736:
                            ItemService.gI().OpenItem736(pl, item);
                            break;
                        case 2048:
                            ItemService.gI().OpenItem2048(pl, item);
                            break;
                        case 2049:
                            ItemService.gI().OpenItem2049(pl, item);
                            break;
                        case 1375:
                            ItemService.gI().openPet(pl, item);
                            break;
                        case 987:
                            Service.getInstance().sendThongBao(pl, "Bảo vệ trang bị không bị rớt cấp"); //đá bảo vệ
                            break;
                        case 2006:
                            Input.gI().createFormChangeNameByItem(pl);
                            break;
                        case 2028: {
                            if (InventoryServiceNew.gI().getCountEmptyBag(pl) == 0) {
                                Service.getInstance().sendThongBao(pl, "Hành trang không đủ chỗ trống");
                            } else {
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                                Item linhThu = ItemService.gI().createNewItem((short) Util.nextInt(2019, 2026));
                                linhThu.itemOptions.add(new Item.ItemOption(50, 10));
                                linhThu.itemOptions.add(new Item.ItemOption(77, 5));
                                linhThu.itemOptions.add(new Item.ItemOption(103, 5));
                                linhThu.itemOptions.add(new Item.ItemOption(95, 3));
                                linhThu.itemOptions.add(new Item.ItemOption(96, 3));
                                InventoryServiceNew.gI().addItemBag(pl, linhThu);
                                InventoryServiceNew.gI().sendItemBags(pl);
                                Service.getInstance().sendThongBao(pl, "Chúc mừng bạn nhận được Linh thú " + linhThu.template.name);
                            }
                            break;
                        }
                        case 1376:
                            ItemService.gI().openDeoLung(pl, item);
                            break;

                            //
                    }
                    break;
            }
            InventoryServiceNew.gI().sendItemBags(pl);
        } else {
            Service.getInstance().sendThongBaoOK(pl, "Sức mạnh không đủ yêu cầu");
        }
    }

    private void useItemChangeFlagBag(Player player, Item item) {
        switch (item.template.id) {
            case 994: //vỏ ốc
                break;
            case 995: //cây kem
                break;
            case 996: //cá heo
                break;
            case 997: //con diều
                break;
            case 998: //diều rồng
                break;
            case 999: //mèo mun
                if (!player.effectFlagBag.useMeoMun) {
                    player.effectFlagBag.reset();
                    player.effectFlagBag.useMeoMun = !player.effectFlagBag.useMeoMun;
                } else {
                    player.effectFlagBag.reset();
                }
                break;
            case 1000: //xiên cá
                if (!player.effectFlagBag.useXienCa) {
                    player.effectFlagBag.reset();
                    player.effectFlagBag.useXienCa = !player.effectFlagBag.useXienCa;
                } else {
                    player.effectFlagBag.reset();
                }
                break;
            case 1001: //phóng heo
                if (!player.effectFlagBag.usePhongHeo) {
                    player.effectFlagBag.reset();
                    player.effectFlagBag.usePhongHeo = !player.effectFlagBag.usePhongHeo;
                } else {
                    player.effectFlagBag.reset();
                }
                break;
        }
        Service.getInstance().point(player);
        Service.getInstance().sendFlagBag(player);
    }

    private void changePet(Player player, Item item) {
        if (player.pet != null) {
            int gender = player.pet.gender + 1;
            if (gender > 2) {
                gender = 0;
            }
            PetService.gI().changeNormalPet(player, gender);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
        } else {
            Service.getInstance().sendThongBao(player, "Không thể thực hiện");
        }
    }

    private void changePetBerus(Player player, Item item) {
        if (player.pet != null) {
            int gender = player.pet.gender ;
//            if (gender > 2) {
//                gender = 0;
//            }
            PetService.gI().changeBerusPet(player, gender);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
        } else {
            Service.getInstance().sendThongBao(player, "Không thể thực hiện");
        }
    }
    private void changePetMabu(Player player, Item item) {
        if (player.pet != null) {
            int gender = player.pet.gender;
//            if (gender > 2) {
//                gender = 0;
//            }
            PetService.gI().changeMabuPet1(player, gender);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
        } else {
            Service.getInstance().sendThongBao(player, "Không thể thực hiện");
        }
    }
    private void changePetPic(Player player, Item item) {
        if (player.pet != null) {
            int gender = player.pet.gender ;
//            if (gender > 2) {
//                gender = 0;
//            }
            PetService.gI().changePicPet(player, gender);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
        } else {
            Service.getInstance().sendThongBao(player, "Không thể thực hiện");
        }
    }

    private void openPhieuCaiTrangHaiTac(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            Item ct = ItemService.gI().createNewItem((short) Util.nextInt(618, 626));
            ct.itemOptions.add(new ItemOption(147, 3));
            ct.itemOptions.add(new ItemOption(77, 3));
            ct.itemOptions.add(new ItemOption(103, 3));
            ct.itemOptions.add(new ItemOption(149, 0));
            if (item.template.id == 2006) {
                ct.itemOptions.add(new ItemOption(93, Util.nextInt(1, 7)));
            } else if (item.template.id == 2007) {
                ct.itemOptions.add(new ItemOption(93, Util.nextInt(7, 30)));
            }
            InventoryServiceNew.gI().addItemBag(pl, ct);
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            InventoryServiceNew.gI().sendItemBags(pl);
            CombineServiceNew.gI().sendEffectOpenItem(pl, item.template.iconID, ct.template.iconID);
        } else {
            Service.getInstance().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    private void eatGrapes(Player pl, Item item) {
        int percentCurrentStatima = pl.nPoint.stamina * 100 / pl.nPoint.maxStamina;
        if (percentCurrentStatima > 50) {
            Service.getInstance().sendThongBao(pl, "Thể lực vẫn còn trên 50%");
            return;
        } else if (item.template.id == 211) {
            pl.nPoint.stamina = pl.nPoint.maxStamina;
            Service.getInstance().sendThongBao(pl, "Thể lực của bạn đã được hồi phục 100%");
        } else if (item.template.id == 212) {
            pl.nPoint.stamina += (pl.nPoint.maxStamina * 20 / 100);
            Service.getInstance().sendThongBao(pl, "Thể lực của bạn đã được hồi phục 20%");
        }
        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
        InventoryServiceNew.gI().sendItemBags(pl);
        PlayerService.gI().sendCurrentStamina(pl);
    }

    private void openCSKB(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            short[] temp = {76, 188, 189, 190, 381, 382, 383, 384, 385};
            int[][] gold = {{5000, 20000}};
            byte index = (byte) Util.nextInt(0, temp.length - 1);
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            if (index <= 3) {
                pl.inventory.gold += Util.nextInt(gold[0][0], gold[0][1]);
                if (pl.inventory.gold > Inventory.LIMIT_GOLD) {
                    pl.inventory.gold = Inventory.LIMIT_GOLD;
                }
                PlayerService.gI().sendInfoHpMpMoney(pl);
                icon[1] = 930;
            } else {
                Item it = ItemService.gI().createNewItem(temp[index]);
                it.itemOptions.add(new ItemOption(73, 0));
                InventoryServiceNew.gI().addItemBag(pl, it);
                icon[1] = it.template.iconID;
            }
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            InventoryServiceNew.gI().sendItemBags(pl);

            CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
        } else {
            Service.getInstance().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }
     private void openCSH(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            short[] temp = {861, 861};
            int[][] gold = {{70, 150}};
            byte index = (byte) Util.nextInt(0, temp.length - 1);
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            if (index <= 1) {
                pl.inventory.ruby += Util.nextInt(gold[0][0], gold[0][1]);
                if (pl.inventory.ruby > 2000000000) {
                    pl.inventory.ruby = 2000000000;
                }
                PlayerService.gI().sendInfoHpMpMoney(pl);
                icon[1] = 7743;
            } 
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            InventoryServiceNew.gI().sendItemBags(pl);
            Service.getInstance().sendMoney(pl);

//            CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
        } else {
            Service.getInstance().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }
    private void openWoodChest(Player pl, Item item) {
            int time = (int) TimeUtil.diffDate(new Date(), new Date(item.createTime), TimeUtil.DAY);
        if (time != 0) {
            Item itemReward = null;
            int param = pl.inventory.getParam(item, 72);
        short[] temp = {1079, 722};
            int[][] gold = {{1000, 2000}};
            byte index = (byte) Util.nextInt(0, temp.length - 1);
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            if (param < 9) {
                pl.inventory.ruby += Util.nextInt(gold[0][0], gold[0][1]);
                if (pl.inventory.ruby > 2000000000) {
                    pl.inventory.ruby = 2000000000;
                }
                PlayerService.gI().sendInfoHpMpMoney(pl);
                icon[1] = 7743;
            } else if (param == 9 || param == 10){
                itemReward = ItemService.gI().createNewItem((short) 861);
            itemReward.quantity = Util.nextInt(2000, 5000);
            InventoryServiceNew.gI().addItemBag(pl, itemReward);
            icon[1] = itemReward.template.iconID;
            }
        if (param == 11){
            Item it = ItemService.gI().createNewItem(temp[index]);
                it.quantity = Util.nextInt(50, 100);
                it.itemOptions.add(new ItemOption(73, 0));
                InventoryServiceNew.gI().addItemBag(pl, it);
                icon[1] = it.template.iconID;
        }
        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
//        pl.inventory.addGold(gold);
//        InventoryServiceNew.gI().sendItemBags(pl);
        PlayerService.gI().sendInfoHpMpMoney(pl);
        CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
            
            
        } else {
            Service.getInstance().sendThongBao(pl, "Vui lòng đợi 24h");
        }
    }
    private int randClothes(int level) {
        return LIST_ITEM_CLOTHES[Util.nextInt(0, 2)][Util.nextInt(0, 4)][level - 1];
    }
    
    private void useItemTime(Player pl, Item item) {
        switch (item.template.id) {
            case 382: //bổ huyết
                pl.itemTime.lastTimeBoHuyet = System.currentTimeMillis();
                pl.itemTime.isUseBoHuyet = true;
                break;
            case 383: //bổ khí
                pl.itemTime.lastTimeBoKhi = System.currentTimeMillis();
                pl.itemTime.isUseBoKhi = true;
                break;
            case 384: //giáp xên
                pl.itemTime.lastTimeGiapXen = System.currentTimeMillis();
                pl.itemTime.isUseGiapXen = true;
                break;
            case 381: //cuồng nộ
                pl.itemTime.lastTimeCuongNo = System.currentTimeMillis();
                pl.itemTime.isUseCuongNo = true;
                Service.getInstance().point(pl);
                break;
            case 385: //ẩn danh
                pl.itemTime.lastTimeAnDanh = System.currentTimeMillis();
                pl.itemTime.isUseAnDanh = true;
                break;
            case 379: //máy dò capsule
                pl.itemTime.lastTimeUseMayDo = System.currentTimeMillis();
                pl.itemTime.isUseMayDo = true;
                break;
            
            case 1099:// cn
                pl.itemTime.lastTimeCuongNo2 = System.currentTimeMillis();
                pl.itemTime.isUseCuongNo2 = true;
                Service.getInstance().point(pl);

                break;
            case 1100:// bo huyet
                pl.itemTime.lastTimeBoHuyet2 = System.currentTimeMillis();
                pl.itemTime.isUseBoHuyet2 = true;
                break;
            case 1101://bo khi
                pl.itemTime.lastTimeBoKhi2 = System.currentTimeMillis();
                pl.itemTime.isUseBoKhi2 = true;
                break;
            case 1102://xbh
                pl.itemTime.lastTimeGiapXen2 = System.currentTimeMillis();
                pl.itemTime.isUseGiapXen2 = true;
                break;
            case 1103://an danh
                pl.itemTime.lastTimeAnDanh2 = System.currentTimeMillis();
                pl.itemTime.isUseAnDanh2 = true;
                break;
            
            case 663: //bánh pudding
            case 664: //xúc xíc
            case 665: //kem dâu
            case 666: //mì ly
            case 667: //sushi
                pl.itemTime.lastTimeThucAn = System.currentTimeMillis();
                pl.itemTime.isThucAn = true;
                ItemTimeService.gI().removeItemTime(pl, pl.itemTime.iconThucAn);
                pl.itemTime.iconThucAn = item.template.iconID;
                break;
            case 579: // duoi khi
                pl.itemTime.lastTimeDuoikhi = System.currentTimeMillis();
                pl.itemTime.isDuoikhi = true;
                ItemTimeService.gI().removeItemTime(pl, pl.itemTime.iconDuoi);
                pl.itemTime.iconDuoi = item.template.iconID;
                break;
            case 1201: //máy dò đồ
                pl.itemTime.lastTimeUseMayDo2 = System.currentTimeMillis();
                pl.itemTime.isUseMayDo2 = true;
                break;
        }
        Service.getInstance().point(pl);
        ItemTimeService.gI().sendAllItemTime(pl);
        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
        InventoryServiceNew.gI().sendItemBags(pl);
    }

    private void controllerCallRongThan(Player pl, Item item) {
        int tempId = item.template.id;
        if (tempId >= SummonDragon.NGOC_RONG_1_SAO && tempId <= SummonDragon.NGOC_RONG_7_SAO) {
            switch (tempId) {
                case SummonDragon.NGOC_RONG_1_SAO:
                case SummonDragon.NGOC_RONG_2_SAO:
//                case SummonDragon.NGOC_RONG_3_SAO:
                    SummonDragon.gI().openMenuSummonShenron(pl, (byte) (tempId - 13));
                    break;
                default:
                    NpcService.gI().createMenuConMeo(pl, ConstNpc.TUTORIAL_SUMMON_DRAGON,
                            -1, "Bạn chỉ có thể gọi rồng từ ngọc 3 sao, 2 sao, 1 sao", "Hướng\ndẫn thêm\n(mới)", "OK");
                    break;
            }
        }
    }

    private void learnSkill(Player pl, Item item) {
        Message msg;
        try {
            if (item.template.gender == pl.gender || item.template.gender == 3) {
                String[] subName = item.template.name.split("");
                byte level = Byte.parseByte(subName[subName.length - 1]);
                Skill curSkill = SkillUtil.getSkillByItemID(pl, item.template.id);
                if (curSkill.point == 7) {
                    Service.getInstance().sendThongBao(pl, "Kỹ năng đã đạt tối đa!");
                } else {
                    if (curSkill.point == 0) {
                        if (level == 1) {
                            curSkill = SkillUtil.createSkill(SkillUtil.getTempSkillSkillByItemID(item.template.id), level);
                            SkillUtil.setSkill(pl, curSkill);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            msg = Service.getInstance().messageSubCommand((byte) 23);
                            msg.writer().writeShort(curSkill.skillId);
                            pl.sendMessage(msg);
                            msg.cleanup();
                        } else {
                            Skill skillNeed = SkillUtil.createSkill(SkillUtil.getTempSkillSkillByItemID(item.template.id), level);
                            Service.getInstance().sendThongBao(pl, "Vui lòng học " + skillNeed.template.name + " cấp " + skillNeed.point + " trước!");
                        }
                    } else {
                        if (curSkill.point + 1 == level) {
                            curSkill = SkillUtil.createSkill(SkillUtil.getTempSkillSkillByItemID(item.template.id), level);
                            //System.out.println(curSkill.template.name + " - " + curSkill.point);
                            SkillUtil.setSkill(pl, curSkill);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            msg = Service.getInstance().messageSubCommand((byte) 62);
                            msg.writer().writeShort(curSkill.skillId);
                            pl.sendMessage(msg);
                            msg.cleanup();
                        } else {
                            Service.getInstance().sendThongBao(pl, "Vui lòng học " + curSkill.template.name + " cấp " + (curSkill.point + 1) + " trước!");
                        }
                    }
                    InventoryServiceNew.gI().sendItemBags(pl);
                }
            } else {
                Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
            }
        } catch (Exception e) {
            Logger.logException(UseItem.class, e);
        }
    }

    private void useTDLT(Player pl, Item item) {
        if (pl.itemTime.isUseTDLT) {
            ItemTimeService.gI().turnOffTDLT(pl, item);
        } else {
            ItemTimeService.gI().turnOnTDLT(pl, item);
        }
    }
    
      private void usePorata2(Player pl) {
        if (pl.pet == null || pl.fusion.typeFusion == 4) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
        } else {
            if (pl.fusion.typeFusion == ConstPlayer.NON_FUSION) {
                pl.pet.fusion2(true);
            } else {
                pl.pet.unFusion();
            }
        }
    }
      private void usePorata3(Player pl) {
        if (pl.pet == null || pl.fusion.typeFusion == 4) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
        } else {
            if (pl.fusion.typeFusion == ConstPlayer.NON_FUSION) {
                pl.pet.fusion3(true);
            } else {
                pl.pet.unFusion();
            }
        }
    }

    private void usePorata(Player pl) {
        if (pl.pet == null || pl.fusion.typeFusion == 4) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
        } else {
            if (pl.fusion.typeFusion == ConstPlayer.NON_FUSION) {
                pl.pet.fusion(true);
            } else {
                pl.pet.unFusion();
            }
        }
    }

    private void openQuadenbu(Player pl, Item item) {
    if (InventoryServiceNew.gI().getCountEmptyBag(pl) < 1) {
            Service.gI().sendThongBao(pl, "Bạn phải có ít nhất 1 ô trống hành trang");
            return;
        }
         InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1); 
        if (Util.isTrue(100, 100)) {
        Item caitrang = ItemService.gI().createNewItem((short) 1248,1); //Găng thiên sứ
        if (caitrang.template.id == 1248){
        caitrang.itemOptions.add(new Item.ItemOption(77, 40));
        caitrang.itemOptions.add(new Item.ItemOption(103, 40));
        caitrang.itemOptions.add(new Item.ItemOption(50, 40));
        caitrang.itemOptions.add(new Item.ItemOption(5,20));
        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }}}            
    
    private void openTuiqua(Player pl, Item item) {
    if (InventoryServiceNew.gI().getCountEmptyBag(pl) < 1) {
            Service.gI().sendThongBao(pl, "Bạn phải có ít nhất 1 ô trống hành trang");
            return;
        }
         InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1); 
        if (Util.isTrue(100, 100)) {
            int rd = Util.nextInt(0,9);
            if (rd == 0 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1055,1); //Găng thiên sứ
        if (caitrang.template.id == 1055){
            caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1));            
        }            
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }
              if (rd == 1 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1052,1); //Quần thiên sứ
        if (caitrang.template.id == 1052){
            caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1));
        }           
        
        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }  
              
               if (rd == 2 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1058,1); //Giày thiên sứ
        if (caitrang.template.id == 1058){
            caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1));
        }           
        
        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }  
               if (rd == 3 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1049,1); //Áo thiên sứ
        if (caitrang.template.id == 1049){
           caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1));
        }
        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }  
                  if (rd == 4 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1061,1); //Nhẫn thiên sứ
        if (caitrang.template.id == 1061){
            caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1));
        }           

        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }   
                        if (rd == 5 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1107,1); //Pet
        if (caitrang.template.id == 1107){
            caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1));

        }           
     
        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }   
        if (rd == 6 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1130,1); //Cải trang
        if (caitrang.template.id == 1130){
            caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1));
        }           
        
        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }
        if (rd == 7 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1239,1); //Danh hiệu
        if (caitrang.template.id == 1242){
            caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1)); 
        }           
        
        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }
        if (rd == 8 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1225,1); //Danh hiệu
        if (caitrang.template.id == 1225){
            caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1));
        }           
        
        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }
        if (rd == 9 ){
        Item caitrang = ItemService.gI().createNewItem((short) 1143,1); //Danh hiệu
        if (caitrang.template.id == 1143){
            caitrang.itemOptions.add(new ItemOption(50, 1000)); 
            caitrang.itemOptions.add(new ItemOption(77, 1000));
            caitrang.itemOptions.add(new ItemOption(103, 1000));
            caitrang.itemOptions.add(new ItemOption(218, 1000));
            caitrang.itemOptions.add(new ItemOption(5, 100));
            caitrang.itemOptions.add(new ItemOption(14, 25));
            caitrang.itemOptions.add(new ItemOption(101, 150));
            caitrang.itemOptions.add(new ItemOption(107, 16));
            caitrang.itemOptions.add(new ItemOption(131, 1));
            caitrang.itemOptions.add(new ItemOption(143, 1));
        }           
        
        
        InventoryServiceNew.gI().addItemBag(pl, caitrang);
        InventoryServiceNew.gI().sendItemBags(pl);
        }
            
        short[] icon = new short[1];
        icon[1] = item.template.iconID;
        Service.gI().sendThongBao(pl, "Bạn đã nhận được " + item.template.name);
        CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
        
        }
        
    }
    private void openCapsuleUI(Player pl) {
        pl.iDMark.setTypeChangeMap(ConstMap.CHANGE_CAPSULE);
        ChangeMapService.gI().openChangeMapTab(pl);
    }

    public void choseMapCapsule(Player pl, int index) {
        int zoneId = -1;
        Zone zoneChose = pl.mapCapsule.get(index);
        //Kiểm tra số lượng người trong khu

        if (zoneChose.getNumOfPlayers() > 30
                || MapService.gI().isMapDoanhTrai(zoneChose.map.mapId)
                || MapService.gI().isMapMaBu(zoneChose.map.mapId)
                || MapService.gI().isMapHuyDiet(zoneChose.map.mapId)) {
            Service.getInstance().sendThongBao(pl, "Hiện tại không thể vào được khu!");
            return;
        }
        if (index != 0 || zoneChose.map.mapId == 21
                || zoneChose.map.mapId == 22
                || zoneChose.map.mapId == 23) {
            pl.mapBeforeCapsule = pl.zone;
        } else {
            zoneId = pl.mapBeforeCapsule != null ? pl.mapBeforeCapsule.zoneId : -1;
            pl.mapBeforeCapsule = null;
        }
        ChangeMapService.gI().changeMapBySpaceShip(pl, pl.mapCapsule.get(index).map.mapId, zoneId, -1);
    }

    public static void eatPea(Player player) {
        Item pea = null;
        for (Item item : player.inventory.itemsBag) {
            if (item.isNotNullItem() && item.template.type == 6) {
                pea = item;
                break;
            }
        }
        if (pea != null) {
            int hpKiHoiPhuc = 0;
            int lvPea = Integer.parseInt(pea.template.name.substring(13));
            for (Item.ItemOption io : pea.itemOptions) {
                if (io.optionTemplate.id == 2) {
                    hpKiHoiPhuc = io.param * 1000;
                    break;
                }
                if (io.optionTemplate.id == 48) {
                    hpKiHoiPhuc = io.param;
                    break;
                }
            }
            player.nPoint.setHp(player.nPoint.hp + hpKiHoiPhuc);
            player.nPoint.setMp(player.nPoint.mp + hpKiHoiPhuc);
            PlayerService.gI().sendInfoHpMp(player);
            Service.getInstance().sendInfoPlayerEatPea(player);
            if (player.pet != null && player.zone.equals(player.pet.zone) && !player.pet.isDie()) {
                int statima = 100 * lvPea;
                player.pet.nPoint.stamina += statima;
                if (player.pet.nPoint.stamina > player.pet.nPoint.maxStamina) {
                    player.pet.nPoint.stamina = player.pet.nPoint.maxStamina;
                }
                player.pet.nPoint.setHp(player.pet.nPoint.hp + hpKiHoiPhuc);
                player.pet.nPoint.setMp(player.pet.nPoint.mp + hpKiHoiPhuc);
                Service.getInstance().sendInfoPlayerEatPea(player.pet);
                Service.getInstance().chatJustForMe(player, player.pet, "Cảm ơn sư phụ đã cho con đậu thần");
            }

            InventoryServiceNew.gI().subQuantityItemsBag(player, pea, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        }
    }

    private void upSkillPet(Player pl, Item item) {
        if (pl.pet == null) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
            return;
        }
        try {
            switch (item.template.id) {
                case 402: //skill 1
                    if (SkillUtil.upSkillPet(pl.pet.playerSkill.skills, 0)) {
                        Service.getInstance().chatJustForMe(pl, pl.pet, "Cảm ơn sư phụ");
                        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                    } else {
                        Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
                    }
                    break;
                case 403: //skill 2
                    if (SkillUtil.upSkillPet(pl.pet.playerSkill.skills, 1)) {
                        Service.getInstance().chatJustForMe(pl, pl.pet, "Cảm ơn sư phụ");
                        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                    } else {
                        Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
                    }
                    break;
                case 404: //skill 3
                    if (SkillUtil.upSkillPet(pl.pet.playerSkill.skills, 2)) {
                        Service.getInstance().chatJustForMe(pl, pl.pet, "Cảm ơn sư phụ");
                        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                    } else {
                        Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
                    }
                    break;
                case 759: //skill 4
                    if (SkillUtil.upSkillPet(pl.pet.playerSkill.skills, 3)) {
                        Service.getInstance().chatJustForMe(pl, pl.pet, "Cảm ơn sư phụ");
                        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                    } else {
                        Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
                    }
                    break;

            }

        } catch (Exception e) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
        }
    }

    private void ItemSKH(Player pl, Item item) {//hop qua skh
        NpcService.gI().createMenuConMeo(pl, item.template.id, -1, "Hãy chọn một món quà", "Áo", "Quần", "Găng", "Giày", "Rada", "Từ Chối");
    }

    private void ItemDHD(Player pl, Item item) {//hop qua do huy diet
        NpcService.gI().createMenuConMeo(pl, item.template.id, -1, "Hãy chọn một món quà", "Áo", "Quần", "Găng", "Giày", "Rada", "Từ Chối");
    }

    private void Hopts(Player pl, Item item) {//hop qua do huy diet
        NpcService.gI().createMenuConMeo(pl, item.template.id, -1, "Chọn hành tinh của mày đi", "Set trái đất", "Set namec", "Set xayda", "Từ chổi");
    }

    public void UseCard(Player pl, Item item) {
        RadarCard radarTemplate = RadarService.gI().RADAR_TEMPLATE.stream().filter(c -> c.Id == item.template.id).findFirst().orElse(null);
        if (radarTemplate == null) {
            return;
        }
        if (radarTemplate.Require != -1) {
            RadarCard radarRequireTemplate = RadarService.gI().RADAR_TEMPLATE.stream().filter(r -> r.Id == radarTemplate.Require).findFirst().orElse(null);
            if (radarRequireTemplate == null) {
                return;
            }
            Card cardRequire = pl.Cards.stream().filter(r -> r.Id == radarRequireTemplate.Id).findFirst().orElse(null);
            if (cardRequire == null || cardRequire.Level < radarTemplate.RequireLevel) {
                Service.gI().sendThongBao(pl, "Bạn cần sưu tầm " + radarRequireTemplate.Name + " ở cấp độ " + radarTemplate.RequireLevel + " mới có thể sử dụng thẻ này");
                return;
            }
        }
        Card card = pl.Cards.stream().filter(r -> r.Id == item.template.id).findFirst().orElse(null);
        if (card == null) {
            Card newCard = new Card(item.template.id, (byte) 1, radarTemplate.Max, (byte) -1, radarTemplate.Options);
            if (pl.Cards.add(newCard)) {
                RadarService.gI().RadarSetAmount(pl, newCard.Id, newCard.Amount, newCard.MaxAmount);
                RadarService.gI().RadarSetLevel(pl, newCard.Id, newCard.Level);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                InventoryServiceNew.gI().sendItemBags(pl);
            }
        } else {
            if (card.Level >= 2) {
                Service.gI().sendThongBao(pl, "Thẻ này đã đạt cấp tối đa");
                return;
            }
            card.Amount++;
            if (card.Amount >= card.MaxAmount) {
                card.Amount = 0;
                if (card.Level == -1) {
                    card.Level = 1;
                } else {
                    card.Level++;
                }
                Service.gI().point(pl);
            }
            RadarService.gI().RadarSetAmount(pl, card.Id, card.Amount, card.MaxAmount);
            RadarService.gI().RadarSetLevel(pl, card.Id, card.Level);
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            InventoryServiceNew.gI().sendItemBags(pl);
        }
    }

}
