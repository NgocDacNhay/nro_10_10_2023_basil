/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girlkun.models.player;

import com.girlkun.database.GirlkunDB;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.result.GirlkunResultSet;
import com.girlkun.server.Client;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.NpcService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Tamkjll
 */
public class Tamkjllgift {

    String code;
    int Luot;
    public HashMap<Integer, Integer> Item = new HashMap<>();
    public final ArrayList<Tamkjllgift> listGiftCode = new ArrayList<>();
    public ArrayList<ItemOption> option = new ArrayList<>();

    private static Tamkjllgift instance;

    public static Tamkjllgift gI() {
        if (instance == null) {
            instance = new Tamkjllgift();
        }
        return instance;
    }

    public void init() {
        listGiftCode.clear();
        try (Connection con = GirlkunDB.getConnection();) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM TamkjllGift WHERE Luot >= 1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tamkjllgift giftcode = new Tamkjllgift();
                giftcode.code = rs.getString("Code");
                giftcode.Luot = rs.getInt("Luot");
                JSONArray jar = (JSONArray) JSONValue.parse(rs.getString("Item"));
                if (jar != null) {
                    for (int i = 0; i < jar.size(); ++i) {
                        JSONObject jsonObj = (JSONObject) jar.get(i);
                        giftcode.Item.put(Integer.parseInt(jsonObj.get("id").toString()),
                                Integer.parseInt(jsonObj.get("soluong").toString()));
                        jsonObj.clear();
                    }
                }
                JSONArray option = (JSONArray) JSONValue.parse(rs.getString("Option"));
                if (option != null) {
                    for (int u = 0; u < option.size(); u++) {
                        JSONObject jsonobject = (JSONObject) option.get(u);
                        giftcode.option.add(new ItemOption(Integer.parseInt(jsonobject.get("id").toString()),
                                Integer.parseInt(jsonobject.get("param").toString())));
                        jsonobject.clear();
                    }
                }
                listGiftCode.add(giftcode);
            }
            con.close();
        } catch (Exception erorlog) {
            erorlog.printStackTrace();
        }
    }

    public Tamkjllgift checkUseGiftCode(int idPlayer, String code) throws Exception {
        GirlkunResultSet rs = null;
        rs = GirlkunDB.executeQuery("SELECT * FROM Tamkjll_History_Code WHERE `player_id` = " + idPlayer + " AND `code` = '" + code + "';");
        if (rs != null && rs.first()) {
            Service.getInstance().sendThongBaoOK(Client.gI().getPlayer(idPlayer).getSession(), "Bạn đã nhập code này vào: " + rs.getTimestamp("time"));
            return null;
        } else {
            for (Tamkjllgift giftCode : listGiftCode) {
                if (giftCode.code.equals(code) && giftCode.Luot > 0) {
                    giftCode.Luot -= 1;
                    String sqlSET = "(" + idPlayer + ", '" + code + "', '" + Util.toDateString(Date.from(Instant.now())) + "');";
                    GirlkunDB.executeUpdate("INSERT INTO `Tamkjll_History_Code` (`player_id`,`code`,`time`) VALUES " + sqlSET);
                    GirlkunDB.executeUpdate("UPDATE `TamkjllGift` SET `Luot` = '" + giftCode.Luot + "' WHERE `Code` = '" + code + "' LIMIT 1;");
                    return giftCode;
                }
            }
        }
        rs.dispose();
        return null;
    }

    public void checkInfomationGiftCode(Player p) {
        StringBuilder sb = new StringBuilder();
        for (Tamkjllgift giftCode : listGiftCode) {
            sb.append("Code: ").append(giftCode.code).append(", Số lượng: ").append(giftCode.Luot).append("\b");
        }
        NpcService.gI().createTutorial(p, 5073, sb.toString());
    }

    public void giftCode(Player player, String code) throws Exception {
        Tamkjllgift giftcode = Tamkjllgift.gI().checkUseGiftCode((int) player.id, code);
        // if(!Maintenance.gI().canUseCode){Service.gI().sendThongBao(player, "Không thể thực hiện lúc này ");return;}
        if (giftcode == null) {
            Service.getInstance().sendThongBao(player, "Code đã được sử dụng, hoặc không tồn tại!");
        } else {
            InventoryServiceNew.gI().addItemGiftCodeToPlayer(player, giftcode);
        }
    }
}
