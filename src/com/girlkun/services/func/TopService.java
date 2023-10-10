package com.girlkun.services.func;

import com.girlkun.database.GirlkunDB;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.models.player.Player;
import com.girlkun.server.Client;
import com.girlkun.server.Manager;
import com.girlkun.network.io.Message;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class TopService implements Runnable{
    private static TopService i;

    public static TopService gI() {
        if (i == null) {
            i = new TopService();
        }
        return i;
    }
    
    @Override
    public void run() {
        while(true){
            try{
                if (Manager.timeRealTop + (30 * 60 * 1000) < System.currentTimeMillis()) {
                    Manager.timeRealTop = System.currentTimeMillis();
                    try (Connection con = GirlkunDB.getConnection()) {
                        
                        Manager.topNV = Manager.realTop(Manager.queryTopNV, con);
                        Manager.topSM = Manager.realTop(Manager.queryTopSM, con);
                        Manager.topSK = Manager.realTop(Manager.queryTopSK, con);
                        Manager.topPVP = Manager.realTop(Manager.queryTopPVP, con);
                        Manager.topNHS = Manager.realTop(Manager.queryTopNHS, con);
                    //    Manager.topKhiGas = Manager.realTop(Manager.queryTopKhiGas, con);
//                        Manager.topNap = Manager.realTop(Manager.queryTopNap, con);
                    } catch (Exception ignored) {
                        Logger.error("Lỗi đọc top");
                    }
                }
                Thread.sleep(1000);
            }catch (Exception ignored) {
            }
        }
    }

}
