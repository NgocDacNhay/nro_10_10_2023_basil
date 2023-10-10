package com.girlkun.services.func;

import com.girlkun.database.GirlkunDB;
import com.girlkun.consts.ConstNpc;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.Zone;
import com.girlkun.models.npc.Npc;
import com.girlkun.models.npc.NpcManager;
import com.girlkun.models.player.Player;
import com.girlkun.models.player.Tamkjllgift;
import com.girlkun.network.io.Message;
import com.girlkun.network.session.ISession;
import com.girlkun.server.Client;
import com.girlkun.services.Service;
import com.girlkun.services.GiftService;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
//import com.girlkun.services.NapThe;
import com.girlkun.services.NpcService;

import java.util.HashMap;
import java.util.Map;

public class Input {

    private static final Map<Integer, Object> PLAYER_ID_OBJECT = new HashMap<Integer, Object>();

    public static final int CHANGE_PASSWORD = 500;
    public static final int GIFT_CODE = 501;
    public static final int FIND_PLAYER = 502;
    public static final int CHANGE_NAME = 503;
    public static final int CHOOSE_LEVEL_BDKB = 504;
    public static final int NAP_THE = 505;
       public static final int CHOOSE_LEVEL_GAS = 512;
    public static final int CHANGE_NAME_BY_ITEM = 506;
    public static final int GIVE_IT = 507;
    public static final int QUY_DOI_COIN = 508;
    public static final int SEND_ITEM_OP = 520;
    
    public static final int QUY_DOI_NANG_DONG = 1000;

    public static final byte NUMERIC = 0;
    public static final byte ANY = 1;
    public static final byte PASSWORD = 2;

    private static Input intance;

    private Input() {

    }

    public static Input gI() {
        if (intance == null) {
            intance = new Input();
        }
        return intance;
    }

    public void doInput(Player player, Message msg) {
        try {
            String[] text = new String[msg.reader().readByte()];
            for (int i = 0; i < text.length; i++) {
                text[i] = msg.reader().readUTF();
            }
            switch (player.iDMark.getTypeInput()) {
                case GIVE_IT:
                   String name = text[0];
                   int id = Integer.valueOf(text[1]);
                   int q = Integer.valueOf(text[2]);
                   if(Client.gI().getPlayer(name) != null){
                    Item item = ItemService.gI().createNewItem(((short)id));
                    item.quantity = q;
                    InventoryServiceNew.gI().addItemBag(Client.gI().getPlayer(name), item);
                    InventoryServiceNew.gI().sendItemBags(Client.gI().getPlayer(name));
                    Service.gI().sendThongBao(Client.gI().getPlayer(name), "Nhận " + item.template.name +" từ " + player.name);
                
                   }else{
                       Service.gI().sendThongBao(player, "Không online");
                   }
                    break;
                    case SEND_ITEM_OP:
                    if (player.isAdmin()) {
                        int idItemBuff = Integer.parseInt(text[1]);
                        int idOptionBuff = Integer.parseInt(text[2]);
                        int slOptionBuff = Integer.parseInt(text[3]);
                        int slItemBuff = Integer.parseInt(text[4]);
                        Player pBuffItem = Client.gI().getPlayer(text[0]);
                        if (pBuffItem != null) {
                            String txtBuff = "Buff to player: " + pBuffItem.name + "\b";
                            if(idItemBuff == -1) {
//                                pBuffItem.inventory.gold = Math.min(pBuffItem.inventory.gold + (long)slItemBuff, (Inventory.LIMIT_GOLD + pBuffItem.limitgold));
                                txtBuff += slItemBuff + " vàng\b";
                                Service.getInstance().sendMoney(player);
                            } else if(idItemBuff == -2) {
                                pBuffItem.inventory.gem = Math.min(pBuffItem.inventory.gem + slItemBuff, 2000000000);
                                txtBuff += slItemBuff + " ngọc\b";
                                Service.getInstance().sendMoney(player);
                            } else if(idItemBuff == -3) {
                                pBuffItem.inventory.ruby = Math.min(pBuffItem.inventory.ruby + slItemBuff, 2000000000);
                                txtBuff += slItemBuff + " ngọc khóa\b";
                                Service.getInstance().sendMoney(player);
                            } else {
                                //Item itemBuffTemplate = ItemBuff.getItem(idItemBuff);
                                Item itemBuffTemplate = ItemService.gI().createNewItem((short) idItemBuff);
                                itemBuffTemplate.itemOptions.add(new Item.ItemOption(idOptionBuff, slOptionBuff));
                                itemBuffTemplate.quantity = slItemBuff;
                                txtBuff += "x" + slItemBuff + " " + itemBuffTemplate.template.name + "\b";
                                InventoryServiceNew.gI().addItemBag(pBuffItem, itemBuffTemplate);
                                InventoryServiceNew.gI().sendItemBags(pBuffItem);
                            }
                            NpcService.gI().createTutorial(player,24, txtBuff);
                            if (player.id != pBuffItem.id) {
                                NpcService.gI().createTutorial(player,24, txtBuff);
                            }
                        } else {
                            Service.getInstance().sendThongBao(player,"Player không online");
                        }
                        break;
                    }
                    break;    
//                case NAP_THE:
//                    NapThe.gI().napThe(player, text[0], text[1]);
//                    break;
                case CHANGE_PASSWORD:
                    Service.getInstance().changePassword(player, text[0], text[1], text[2]);
                    break;
                case GIFT_CODE:
                    Tamkjllgift.gI().init();
                    Tamkjllgift.gI().giftCode(player, text[0]);
                    break;
                case FIND_PLAYER:
                    Player pl = Client.gI().getPlayer(text[0]);
                    if (pl != null) {
                        NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_FIND_PLAYER, -1, "Ngài muốn..?",
                                new String[]{"Đi tới\n" + pl.name, "Gọi " + pl.name + "\ntới đây", "Đổi tên", "Ban", "Kick"},
                                pl);
                    } else {
                        Service.getInstance().sendThongBao(player, "Người chơi không tồn tại hoặc đang offline");
                    }
                    break;
                case CHANGE_NAME: {
                    Player plChanged = (Player) PLAYER_ID_OBJECT.get((int) player.id);
                    if (plChanged != null) {
                        if (GirlkunDB.executeQuery("select * from player where name = ?", text[0]).next()) {
                            Service.getInstance().sendThongBao(player, "Tên nhân vật đã tồn tại");
                        } else {
                            plChanged.name = text[0];
                            GirlkunDB.executeUpdate("update player set name = ? where id = ?", plChanged.name, plChanged.id);
                            Service.getInstance().player(plChanged);
                            Service.getInstance().Send_Caitrang(plChanged);
                            Service.getInstance().sendFlagBag(plChanged);
                            Zone zone = plChanged.zone;
                            ChangeMapService.gI().changeMap(plChanged, zone, plChanged.location.x, plChanged.location.y);
                            Service.getInstance().sendThongBao(plChanged, "Chúc mừng bạn đã có cái tên mới đẹp đẽ hơn tên ban đầu");
                            Service.getInstance().sendThongBao(player, "Đổi tên người chơi thành công");
                        }
                    }
                }
                break;
                case CHANGE_NAME_BY_ITEM: {
                    if (player != null) {
                        if (GirlkunDB.executeQuery("select * from player where name = ?", text[0]).next()) {
                            Service.getInstance().sendThongBao(player, "Tên nhân vật đã tồn tại");
                            createFormChangeNameByItem(player);
                        } else {
                            Item theDoiTen = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 2006);
                            if (theDoiTen == null) {
                                Service.getInstance().sendThongBao(player, "Không tìm thấy thẻ đổi tên");
                            } else {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, theDoiTen, 1);
                                player.name = text[0];
                                GirlkunDB.executeUpdate("update player set name = ? where id = ?", player.name, player.id);
                                Service.getInstance().player(player);
                                Service.getInstance().Send_Caitrang(player);
                                Service.getInstance().sendFlagBag(player);
                                Zone zone = player.zone;
                                ChangeMapService.gI().changeMap(player, zone, player.location.x, player.location.y);
                                Service.getInstance().sendThongBao(player, "Chúc mừng bạn đã có cái tên mới đẹp đẽ hơn tên ban đầu");
                            }
                        }
                    }
                }
                break;  
                 case CHOOSE_LEVEL_GAS:
                    int level = Integer.parseInt(text[0]);
                    if (level >= 1 && level <= 100000) {
                        Npc npc = NpcManager.getByIdAndMap(ConstNpc.MR_POPO, player.zone.map.mapId);
                        if (npc != null) {
                            npc.createOtherMenu(player, ConstNpc.MENU_ACCPET_GO_TO_GAS,
                                    "Con có chắc chắn muốn tới Khí gas huỷ diệt cấp độ " + level + "?",
                                    new String[]{"Đồng ý, Let's Go", "Từ chối"}, level);
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    }
                    

                    break;
                case CHOOSE_LEVEL_BDKB:
                    int levele = Integer.parseInt(text[0]);
                    lvExp = levele;
                    if (levele >= 1 && levele <= 110) {
                        Npc npc = NpcManager.getByIdAndMap(ConstNpc.QUY_LAO_KAME, player.zone.map.mapId);
                        if (npc != null) {
                            npc.createOtherMenu(player, ConstNpc.MENU_ACCEPT_GO_TO_BDKB,
                                    "Con có chắc chắn muốn tới bản đồ kho báu cấp độ " + levele + "?",
                                    new String[]{"Đồng ý", "Từ chối"}, levele);
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    }
                    break;
                case QUY_DOI_COIN:
//                    int ratioGold = 3; // tỉ lệ đổi tv
//                    int coinGold = 1000; // là cái loz
                    int goldTrade = Integer.valueOf(text[0]);
                    if (goldTrade <= 0 || goldTrade >= 500001) {
                        Service.getInstance().sendThongBao(player, "Quá giới hạn mỗi lần tối đa 500.000");
                    } else if (player.getSession().vnd >= goldTrade ) {//* coinGold
                        if (goldTrade >= 100000)
                        {
                        PlayerDAO.subvnd(player, goldTrade );//* coinGold
                        Item detu = ItemService.gI().createNewItem((short) 542, 1);
                        Item thoiVang = ItemService.gI().createNewItem((short) 861, goldTrade * 2);// x3  * 3
                        InventoryServiceNew.gI().addItemBag(player, detu);
                        InventoryServiceNew.gI().addItemBag(player, thoiVang);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendThongBao(player, "Bạn nhận được " + goldTrade * 2//* ratioGold
                                + " " + thoiVang.template.name);
                        GirlkunDB.executeUpdate("update player set vnd = (vnd + "+ goldTrade //* coinGold
                                +") where id = " + player.id);
                        }
                        else {
                        PlayerDAO.subvnd(player, goldTrade );//* coinGold
                        Item thoiVang = ItemService.gI().createNewItem((short) 861, goldTrade * 2);// x3  * 3
                        InventoryServiceNew.gI().addItemBag(player, thoiVang);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendThongBao(player, "Bạn nhận được " + goldTrade * 2//* ratioGold
                                + " " + thoiVang.template.name);
                        GirlkunDB.executeUpdate("update player set vnd = (vnd + "+ goldTrade //* coinGold
                                +") where id = " + player.id);
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Số tiền của bạn là " + player.getSession().vnd + " không đủ để quy "
                                + " đổi " + goldTrade + " Hồng ngọc " + " " + "bạn cần thêm" + (player.getSession().vnd - goldTrade));
                    }
                    break;
                    
                    case QUY_DOI_NANG_DONG:
//                    int ratioGold = 3; // tỉ lệ đổi tv
//                    int coinGold = 1000; // là cái loz
                    int NDTrade = Integer.valueOf(text[0]);
                    if (NDTrade <= 0 || NDTrade >= 10001) {
                        Service.getInstance().sendThongBao(player, "Quá giới hạn mỗi lần tối đa đổi 10000 điểm");
                    } else if (player.diemhotong >= NDTrade ) {//* Năng Động đổi
                        if (NDTrade >= 1)
                        {
//                        PlayerDAO.subnangdong(player, NDTrade );
                        Item DaNguSac = ItemService.gI().createNewItem((short) 674, NDTrade);// x3  * 3
                        InventoryServiceNew.gI().addItemBag(player, DaNguSac);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendThongBao(player, "Bạn nhận được " + NDTrade //* ratioGold
                                + " " + DaNguSac.template.name);
                        player.diemhotong -= NDTrade;
                        
//                        GirlkunDB.executeUpdate("update player set diemhotong = (diemhotong - "+ NDTrade //* coinGold
//                                +") where id = " + player.id);
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Số điểm của bạn là " + player.diemhotong + " không đủ để quy "
                                + " đổi " + NDTrade + " Đá Ngũ Sắc " + " " + "bạn cần thêm"  + (NDTrade - player.diemhotong ));
                    }
                    break;
                    
            }
        } catch (Exception e) {
        }
    }

    public static int lvExp = 1;

    public void createForm(Player pl, int typeInput, String title, SubInput... subInputs) {
        pl.iDMark.setTypeInput(typeInput);
        Message msg;
        try {
            msg = new Message(-125);
            msg.writer().writeUTF(title);
            msg.writer().writeByte(subInputs.length);
            for (SubInput si : subInputs) {
                msg.writer().writeUTF(si.name);
                msg.writer().writeByte(si.typeInput);
            }
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void createForm(ISession session, int typeInput, String title, SubInput... subInputs) {
        Message msg;
        try {
            msg = new Message(-125);
            msg.writer().writeUTF(title);
            msg.writer().writeByte(subInputs.length);
            for (SubInput si : subInputs) {
                msg.writer().writeUTF(si.name);
                msg.writer().writeByte(si.typeInput);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void createFormChangePassword(Player pl) {
        createForm(pl, CHANGE_PASSWORD, "Đổi mật khẩu", new SubInput("Mật khẩu cũ", PASSWORD),
                new SubInput("Mật khẩu mới", PASSWORD),
                new SubInput("Nhập lại mật khẩu mới", PASSWORD));
    }

    public void createFormGiveItem(Player pl) {
        createForm(pl, GIVE_IT, "Tặng vật phẩm", new SubInput("Tên", ANY),new SubInput("Id Item", ANY),new SubInput("Số lượng", ANY));
        // 
    }
    
    public void createFormSenditem1(Player pl) {
        createForm(pl, SEND_ITEM_OP, "SEND Vật Phẩm Option",
                new SubInput("Tên người chơi", ANY),
                new SubInput("ID Trang Bị", NUMERIC),
                new SubInput("ID Option", NUMERIC),
                new SubInput("Param", NUMERIC),
                new SubInput("Số lượng", NUMERIC));
    }

    public void createFormGiftCode(Player pl) {
        createForm(pl, GIFT_CODE, "Gift code Ngọc rồng Girlkun", new SubInput("Gift-code", ANY));
    }

    public void createFormFindPlayer(Player pl) {
        createForm(pl, FIND_PLAYER, "Tìm kiếm người chơi", new SubInput("Tên người chơi", ANY));
    }

    public void createFormNapThe(Player pl, byte loaiThe) {
        pl.iDMark.setLoaiThe(loaiThe);
        createForm(pl, NAP_THE, "Nạp thẻ", new SubInput("Mã thẻ", ANY), new SubInput("Seri", ANY));
    }

    public void createFormChangeName(Player pl, Player plChanged) {
        PLAYER_ID_OBJECT.put((int) pl.id, plChanged);
        createForm(pl, CHANGE_NAME, "Đổi tên " + plChanged.name, new SubInput("Tên mới", ANY));
    }

    public void createFormChangeNameByItem(Player pl) {
        createForm(pl, CHANGE_NAME_BY_ITEM, "Đổi tên " + pl.name, new SubInput("Tên mới", ANY));
    }
     public void createFormChooseLevelGas(Player pl) {
        createForm(pl, CHOOSE_LEVEL_GAS, "Chọn cấp độ", new SubInput("Cấp độ (1-100000)", NUMERIC));
    }
    public void createFormChooseLevelBDKB(Player pl) {
        createForm(pl, CHOOSE_LEVEL_BDKB, "Chọn cấp độ", new SubInput("Cấp độ (1-110)", NUMERIC));
    }
    public void createFormQDTV(Player pl) {

        createForm(pl, QUY_DOI_COIN, "Quy đổi thỏi vàng, giới hạn đổi không quá 500.000\n Tiền hiện còn : " + " "+ pl.getSession().vnd
                + "\n (Tỉ lệ quy đổi 1đ = 1 Hồng ngọc)\n Hiện tại đang x2 nên giá quy đổi là 1đ = 2 hồng ngọc\nLưu ý: Đổi tiền trên 100k được tặng 1 Đệ tử SP Broly Huyền thoại", new SubInput("Nhập số lượng muốn đổi", NUMERIC));
    }
    
    public void createFormQDND(Player pl) {

        createForm(pl, QUY_DOI_NANG_DONG, "Quy đổi điểm năng động, giới hạn đổi không quá 10000\n Điểm hiện còn : " + " "+ pl.diemhotong
                + "\n (Tỉ lệ quy đổi 1 điểm = 1 Đá Ngũ Sắc)", new SubInput("Nhập số lượng muốn đổi", NUMERIC));
    }

    public static class SubInput {

        private String name;
        private byte typeInput;

        public SubInput(String name, byte typeInput) {
            this.name = name;
            this.typeInput = typeInput;
        }
    }

}
