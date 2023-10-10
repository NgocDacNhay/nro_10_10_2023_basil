package com.girlkun.models.matches;

import com.girlkun.models.player.Player;
import com.girlkun.server.ServerManager;
import java.util.ArrayList;


public class PVPManager implements Runnable {

    private static PVPManager i;

    public static PVPManager gI() {
        if (i == null) {
            i = new PVPManager();
        }
        return i;
    }

    private ArrayList<PVP> pvps;

    public PVPManager() {
        this.pvps = new ArrayList<>();
        new Thread(this, "Update pvp").start();
    }

    public void removePVP(PVP pvp) {
        this.pvps.remove(pvp);
    }

    public void addPVP(PVP pvp) {
        this.pvps.add(pvp);
    }

    public PVP getPVP(Player player) {
        for (PVP pvp : this.pvps) {
            if (pvp.p1.equals(player) || pvp.p2.equals(player)) {
                return pvp;
            }
        }
        return null;
    }

    @Override
    public void run() {
        this.update();
    }

    private void update() {
        while (ServerManager.isRunning) {
            try {
                long st = System.currentTimeMillis();
                for (PVP pvp : pvps) {
                    pvp.update();
                }
                Thread.sleep(1000 - (System.currentTimeMillis() - st));
            } catch (Exception e) {
            }
        }
    }

}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức Hãy tôn trọng tác giả
 * của mã nguồn này Xin cảm ơn! - Girlkun75
 */
