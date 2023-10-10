package com.girlkun.models.map.daihoi;


import com.girlkun.models.player.Pet;
import com.girlkun.models.map.Map;
import com.girlkun.models.player.Player;
import com.girlkun.network.io.Message;
import com.girlkun.server.Client;
import com.girlkun.server.ServerNotify;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author BTH
 */
public class DaiHoiService {
    private static DaiHoiService instance;

    public static DaiHoiService gI() {
        if (instance == null) {
            instance = new DaiHoiService();
        }
        return instance;
    }

    public void initDaiHoiVoThuat() {
        Timer timerDHVT = new Timer();
        TimerTask DHVT = new TimerTask() {
            public void run()
            {
                DaiHoiManager.gI().lstIDPlayers.clear();
                DaiHoiManager.gI().lstIDPlayers2.clear();
                DaiHoiManager.gI().openDHVT = false;
                DaiHoiManager.gI().roundNow = (byte)0;
                int crrHOUR = DaiHoiManager.gI().hourDHVT;
                DaiHoiManager.gI().typeDHVT = hourToTypeDHVT(crrHOUR);
//                Util.log("HOUR: " + crrHOUR);
//                Util.log("typeDHVT: " + DaiHoiManager.gI().typeDHVT);
                Timer timerReset = new Timer();
                TimerTask OpenReset = new TimerTask() {
                    public void run()
                    {
                        DaiHoiManager.gI().hourDHVT = (DaiHoiManager.gI().hourDHVT + 1) >= 24 ? 0 : (DaiHoiManager.gI().hourDHVT + 1);
                        timerReset.cancel();
                    };
                };
                timerReset.schedule(OpenReset, 5000);

                if(DaiHoiManager.gI().typeDHVT > (byte)0) {
                    //SET LAI TIME
                    Calendar calendar = Calendar.getInstance();
                    int minuteStart = calendar.get(Calendar.MINUTE);
//                    Util.log("RUN ROI NE: ");
                    DaiHoiManager.gI().openDHVT = true;
                    DaiHoiManager.gI().tOpenDHVT = System.currentTimeMillis() + 1800000 - (minuteStart*60000);
                    //TASK THONG BAO DEN GIO THI DAU
                    Timer timerOpenDHVT = new Timer();
                    TimerTask OpenDHVT = new TimerTask() {
                        public void run()
                        {
                            if((System.currentTimeMillis() - DaiHoiManager.gI().tOpenDHVT) >= 0) {
                                //CALL TASK CHIA TRAN DAU
                                matchDHVT();
                                timerOpenDHVT.cancel();
                            }
                            DaiHoiManager.gI().openDHVT = true;

                            Player _p = null;
                            for(int i = 0; i < DaiHoiManager.gI().lstIDPlayers.size(); i++) {
                                _p = Client.gI().getPlayer(DaiHoiManager.gI().lstIDPlayers.get(i));
                                if(_p != null && _p.getSession() != null && _p.zone.map.mapId == 52) {
                                    Service.getInstance().sendThongBao(_p,"Trận đấu của bạn sẽ diễn ra trong vòng " + (int)((DaiHoiManager.gI().tOpenDHVT - System.currentTimeMillis())/60000) + " phút nữa");
                                }
                            }
                        };
                    };
                    timerOpenDHVT.schedule(OpenDHVT, 0, 30000);
                }
//                timerDHVT.cancel();
            };
        };
//        timerDHVT.schedule(DHVT, 10000);

        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.HOUR_OF_DAY);
        int crrHOUR = calendar.get(Calendar.HOUR_OF_DAY);
        int crrMINUTE = calendar.get(Calendar.MINUTE);
//        Util.log("crrHOUR: " + crrHOUR);
//        Util.log("crrMINUTE: " + crrMINUTE);
        Date dateSchedule = calendar.getTime();
//        Util.log("dateSchedule: " + dateSchedule);
        if(crrMINUTE < 30) {
            calendar.set(Calendar.HOUR_OF_DAY, crrHOUR);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            dateSchedule = calendar.getTime();
            DaiHoiManager.gI().typeDHVT = hourToTypeDHVT(crrHOUR); //SERVER 2
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, (crrHOUR + 1));
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            dateSchedule = calendar.getTime();
            DaiHoiManager.gI().typeDHVT = hourToTypeDHVT(crrHOUR + 12); //SERVER 2
        }
//        Util.log("dateSchedule2: " + dateSchedule);
        DaiHoiManager.gI().hourDHVT = calendar.get(Calendar.HOUR_OF_DAY);
//        Date dateNow = new Date();
//        if(dateNow.after(dateSchedule)) {
//            calendar.setTime(dateSchedule);
//            calendar.add(Calendar.DATE, 1);
//            dateSchedule = calendar.getTime();
//        }
//
        long period = 60 * 60 * 1000;

//        timerDHVT.schedule(DHVT, 0, 10000);
        timerDHVT.schedule(DHVT, dateSchedule, period);
    }

    public byte hourToTypeDHVT(int hour) {
        if(hour >= 24) {
            hour = hour - 24;
        }
        System.out.println("Đại hội võ thuật hour: "+ hour);
        if(hour == 8 || hour == 13 || hour == 18) {
            return (byte)1;
        } else if(hour == 9 || hour == 14 || hour == 19) {
            return (byte)2;
        } else if(hour == 10 || hour == 15 || hour == 20) {
            return (byte)3;
        } else if(hour == 11 || hour == 16 || hour == 21) {
            return (byte)4;
        } else if(hour == 12 || hour == 17 || hour == 22 || hour == 23 || hour == 0 || hour == 1 || hour == 2 || hour == 3 || hour == 4) {
            return (byte)5;
        } else {
            return (byte)0;
        }
    }

    public boolean canRegisDHVT(long tiemNang) {
        if((DaiHoiManager.gI().typeDHVT == (byte)1 && tiemNang < 1500000L) || (DaiHoiManager.gI().typeDHVT == (byte)2 && tiemNang < 15000000L)
                || (DaiHoiManager.gI().typeDHVT == (byte)3 && tiemNang < 150000000L) || (DaiHoiManager.gI().typeDHVT == (byte)4 && tiemNang < 1500000000L)
                || DaiHoiManager.gI().typeDHVT == (byte)5) {
            return true;
        }
        return false;
    }

    public void startVSDHVT(Player p, Player pVS, byte type) {
        Message m = null;
        try {
            m = new Message(-30);
            m.writer().writeByte((byte)35);
            m.writer().writeInt((int)p.id); //ID PLAYER
            m.writer().writeByte(type); //TYPE PK
            m.writer().flush();
            p.session.sendMessage(m);
            pVS.session.sendMessage(m);
            m.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }
    }
    public void updateTypePK(Player p, byte type) {
        Message m = null;
        try {
            m = new Message(-30);
            m.writer().writeByte((byte)35);
            m.writer().writeInt((int)p.id); //ID PLAYER
            m.writer().writeByte(type); //TYPE PK
            m.writer().flush();
            p.session.sendMessage(m);
            m.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }
    }

    public void matchDHVT() {
        int countPlayer = DaiHoiManager.gI().lstIDPlayers.size();
        DaiHoiManager.gI().roundNow += (byte) 1;
        if (countPlayer > 1) {
//                    int countMatch = (int)Math.floor(countPlayer/2);
            int countMatch = (int) (countPlayer / 2);
            DaiHoiManager.gI().tNextRound = System.currentTimeMillis() + 130000;
//                    Util.log("SO TRAN DAU: " + countMatch);
            //INIT DHVT
            Map dhvt = MapService.gI().getMapById(51);
            DaiHoiManager.gI().mapDhvt = dhvt;
            for (int i = 0; i < countMatch; i++) {
                //GET PLAYER 1
//                        int index1 = Util.nextInt(0, DaiHoiManager.gI().lstPlayers.size());
                int index1 = Util.nextIntDhvt(0, DaiHoiManager.gI().lstIDPlayers.size());
                long id1 = DaiHoiManager.gI().lstIDPlayers.get(index1);
//                        Player p1 = DaiHoiManager.gI().lstPlayers.get(index1);
                Player p1 = Client.gI().getPlayer(id1);
                if (p1 != null && p1.session != null) {
                    if (p1.zone.map.mapId == 52) {
                        if (p1.pet != null) {
                            p1.pet.changeStatus(Pet.GOHOME);
                        }
                        ChangeMapService.gI().changeMap(p1, 51, i, 385, 312);
                        DaiHoiManager.gI().lstIDPlayers2.add(id1);
                        DaiHoiManager.gI().lstIDPlayers.remove(index1);
//                                Util.log("COUNT PLAYER 1 VA 2: " + DaiHoiManager.gI().lstIDPlayers.size() +", "+ DaiHoiManager.gI().lstIDPlayers2.size());
                    } else {
                        DaiHoiManager.gI().lstIDPlayers.remove(index1);
                    }
                }
                //GET PLAYER 2
//                        index1 = Util.nextInt(0, DaiHoiManager.gI().lstPlayers.size());
                index1 = Util.nextIntDhvt(0, DaiHoiManager.gI().lstIDPlayers.size());
                long id2 = DaiHoiManager.gI().lstIDPlayers.get(index1);
//                        Player p2 = DaiHoiManager.gI().lstPlayers.get(index1);
                Player p2 = Client.gI().getPlayer(id2);
                if (p2 != null && p2.session != null) {
                    if (p2.zone.map.mapId == 52) {
                        if (p2.pet != null) {
                            p2.pet.changeStatus(Pet.GOHOME);
                        }
                        ChangeMapService.gI().changeMap(p2, 51, i, 385, 312);
                        DaiHoiManager.gI().lstIDPlayers2.add(id2);
                        DaiHoiManager.gI().lstIDPlayers.remove(index1);
                    } else {
                        DaiHoiManager.gI().lstIDPlayers.remove(index1);
                    }
                }
                Timer timerVS = new Timer();
                TimerTask vsDHVT = new TimerTask() {
                    public void run() {
                        Player p11 = Client.gI().getPlayer(id1);
                        Player p22 = Client.gI().getPlayer(id2);
                        if ((p11 == null || p11.session == null) && p22 != null && p22.session != null) {
                            winRoundDHVT(p22, p11);
                        } else if ((p22 == null || p22.session == null) && p11 != null && p11.session != null) {
                            winRoundDHVT(p11, p22);
                        } else if (p22 != null && p22.session != null && p11 != null && p11.session != null) {
                            if (p11.zone.map.mapId != 51) {
                                winRoundDHVT(p22, p11);
                            } else if (p22.zone.map.mapId != 51) {
                                winRoundDHVT(p11, p22);
                            } else {
                                p11.typePk = (byte) 3;
                                p22.typePk = (byte) 3;
                                p11.lockPK = true;
                                p22.lockPK = true;
                                startVSDHVT(p11, p22, (byte) 3);
                                startVSDHVT(p22, p11, (byte) 3);
                            }
                        }
                        timerVS.cancel();
                    }
                    ;
                };
                timerVS.schedule(vsDHVT, 5000);
                //TASK QUYET DINH NGUOI CHIEN THANG
                Timer timerWIN = new Timer();
                TimerTask winDHVT = new TimerTask() {
                    public void run() {
                        Player p11 = Client.gI().getPlayer(id1);
                        Player p22 = Client.gI().getPlayer(id2);
                        if ((p11 == null || p11.session == null) && p22 != null && p22.session != null) {
                            winRoundDHVT(p22, p11);
                        } else if ((p22 == null || p22.session == null) && p11 != null && p11.session != null) {
                            winRoundDHVT(p11, p22);
                        } else if (p11 != null && p11.session != null && p22 != null && p22.session != null) {
                            if (p11.zone.map.mapId != 51) {
                                winRoundDHVT(p22, p11);
                            } else if (p22.zone.map.mapId != 51) {
                                winRoundDHVT(p11, p22);
                            } else {
                                if (!p11.isDie() && !p22.isDie()) {
                                    p11.typePk = (byte) 0;
                                    p22.typePk = (byte) 0;
                                    p11.lockPK = false;
                                    p22.lockPK = false;
                                    if (p11.nPoint.hp >= p22.nPoint.hp) {
                                        //                                            DaiHoiManager.gI().lstPlayers.add(p11);
                                        DaiHoiManager.gI().lstIDPlayers.add(p11.id);
                                        Service.getInstance().sendThongBao(p11, "Bạn đã chiến thắng, bạn nhận được " + DaiHoiManager.gI().costRoundDHVT());
                                        //CHECK NHIEM VU VONG 2 DHVT
//                                        if (p11.taskId == (short) 18 && p11.crrTask.index == (byte) 1 && DaiHoiManager.gI().roundNow >= (byte) 2) {
//                                            TaskService.gI().updateCountTask(p11);
//                                        }
                                        startVSDHVT(p11, p22, (byte) 0);
                                        ChangeMapService.gI().changeMapInYard(p11, 52, -1, -1);
                                        Service.getInstance().hsChar(p22, 1, 1);
                                        ChangeMapService.gI().changeMapInYard(p22, 52, -1, -1);
                                        Service.getInstance().sendThongBao(p22, "Bạn đã thua, hẹn gặp lại ở giải sau");
                                    } else {
                                        //                                            DaiHoiManager.gI().lstPlayers.add(p22);
                                        DaiHoiManager.gI().lstIDPlayers.add(p22.id);
                                        Service.getInstance().sendThongBao(p22, "Bạn đã chiến thắng, bạn nhận được " + DaiHoiManager.gI().costRoundDHVT());
                                        //CHECK NHIEM VU VONG 2 DHVT
//                                        if (p22.taskId == (short) 18 && p22.crrTask.index == (byte) 1 && DaiHoiManager.gI().roundNow >= (byte) 2) {
//                                            TaskService.gI().updateCountTask(p22);
//                                        }
                                        startVSDHVT(p22, p11, (byte) 0);
                                        ChangeMapService.gI().changeMapInYard(p22, 52, -1, -1);
                                        Service.getInstance().hsChar(p11, 1, 1);
                                        ChangeMapService.gI().changeMapInYard(p11, 52, -1, -1);
                                        Service.getInstance().sendThongBao(p11, "Bạn đã thua, hẹn gặp lại ở giải sau");
                                    }
                                    timerVS.cancel();
                                } else {
                                    timerVS.cancel();
                                }
                            }
                        } else {
                            timerVS.cancel();
                        }
                    }
                    ;
                };
                timerWIN.schedule(winDHVT, 65000);
                p1.timerDHVT = timerWIN;
                p1._friendGiaoDich = p2;
                p2.timerDHVT = timerWIN;
                p2._friendGiaoDich = p1;
            }
            if (DaiHoiManager.gI().lstPlayers.size() > 0) {
                Service.getInstance().sendThongBao(Client.gI().getPlayer(DaiHoiManager.gI().lstIDPlayers.get(0)), "Bạn được vào vòng tiếp theo");
                //CHECK NHIEM VU VONG 2 DHVT
//                if (Client.gI().getPlayer(DaiHoiManager.gI().lstIDPlayers.get(0)).taskId == (short) 18 && Client.gI().getPlayer(DaiHoiManager.gI().lstIDPlayers.get(0)).crrTask.index == (byte) 1 && DaiHoiManager.gI().roundNow >= (byte) 2) {
//                    TaskService.gI().updateCountTask(Client.gI().getPlayer(DaiHoiManager.gI().lstIDPlayers.get(0)));
//                }
//                        DaiHoiManager.gI().lstPlayers.get(0).sendAddchatYellow("Bạn được vào vòng tiếp theo");
            }

            //
            Timer timerRestart = new Timer();
            TimerTask restartDHVT = new TimerTask() {
                public void run() {
                    if ((System.currentTimeMillis() - DaiHoiManager.gI().tNextRound) >= 0) {
                        //RESTART VONG TIEP THEO
//                                DaiHoiManager.gI().lstPlayers2.clear();
                        DaiHoiManager.gI().lstIDPlayers2.clear();
                        matchDHVT();
                        timerRestart.cancel();
                    } else {
                        if (DaiHoiManager.gI().lstIDPlayers.size() > 1) {
                            Player _p = null;
                            for (int i = 0; i < DaiHoiManager.gI().lstIDPlayers.size(); i++) {
                                _p = Client.gI().getPlayer(DaiHoiManager.gI().lstIDPlayers.get(i));
                                if (_p != null && _p.session != null) {
                                    Service.getInstance().sendThongBao(_p,"Trận đấu của bạn sẽ diễn ra trong vòng " + (int) ((DaiHoiManager.gI().tNextRound - System.currentTimeMillis()) / 1000) + " giây nữa");
                                }
                            }
                        } else {
                            //THONG BAO VO DICH VA END TASK
                            if (DaiHoiManager.gI().lstIDPlayers.size() == 1) {
                                ServerNotify.gI().notify(Client.gI().getPlayer(DaiHoiManager.gI().lstIDPlayers.get(0)).name + " đã vô địch giải " +  DaiHoiManager.gI().nameRoundDHVT() + " mọi người đều thán phục!");
                                Service.getInstance().sendThongBao(Client.gI().getPlayer(DaiHoiManager.gI().lstIDPlayers.get(0)), "Bạn đã vô địch giải đấu, xin chúc mừng bạn, bạn được thưởng 5 viên đá nâng cấp");
                            }
                            DaiHoiManager.gI().lstIDPlayers.clear();
                            DaiHoiManager.gI().lstIDPlayers2.clear();
                            DaiHoiManager.gI().openDHVT = false;
                            DaiHoiManager.gI().roundNow = (byte) 0;
                            timerRestart.cancel();
                        }
                    }
                }
                ;
            };
            timerRestart.schedule(restartDHVT, 70000, 10000);
        } else {
            //THONG BAO VO DICH
            if (countPlayer == 1) {
                Service.getInstance().sendThongBao(Client.gI().getPlayer(DaiHoiManager.gI().lstIDPlayers.get(0)), "Bạn đã vô địch giải đấu, xin chúc mừng bạn, bạn được thưởng 100 ngọc");
            }
            DaiHoiManager.gI().lstIDPlayers.clear();
            DaiHoiManager.gI().lstIDPlayers2.clear();
            DaiHoiManager.gI().openDHVT = false;
            DaiHoiManager.gI().roundNow = (byte) 0;
//                    Util.log("KHONG VAO DAY DAU YEN TAM");
        }
    }

    public void winRoundDHVT(Player pW, Player pL) {
        if(pW != null && pW.session != null) {
            Service.getInstance().sendThongBao(pW, "Đối thủ đã kiệt sức hoặc rơi đài, bạn đã thắng");
            Service.getInstance().sendThongBao(pW, "Bạn vừa nhận thưởng " + DaiHoiManager.gI().costRoundDHVT());
            //CHECK NHIEM VU VONG 2 DHVT
//            if(pW.taskId == (short)18 && pW.crrTask.index == (byte)1 && DaiHoiManager.gI().roundNow >= (byte)2) {
//                TaskService.gI().updateCountTask(pW);
//            }
        }
        Timer timerWIN = new Timer();
        TimerTask winDHVT = new TimerTask() {
            public void run()
            {
                if(pW != null && pW.session != null) {
                    DaiHoiManager.gI().lstIDPlayers.add(pW.id);
                    pW.typePk = (byte)0;
                    pW.lockPK = false;
                    updateTypePK(pW, (byte)0);
                    pW._friendGiaoDich = null;
                    if(pW.timerDHVT != null) {
                        pW.timerDHVT.cancel();
                        pW.timerDHVT = null;
                    }
                    ChangeMapService.gI().changeMapInYard(pW, 52, -1, -1);
                }
                if(pL != null && pL.session != null) {
                    pL.typePk = (byte)0;
                    pL.lockPK = false;
                    updateTypePK(pL, (byte)0);
                    pL._friendGiaoDich = null;
                    if(pL.timerDHVT != null) {
                        pL.timerDHVT.cancel();
                        pL.timerDHVT = null;
                    }
                    Service.getInstance().hsChar(pL, 1, 1);
                    ChangeMapService.gI().changeMapInYard(pL, 52, -1, -1);
                    Service.getInstance().sendThongBao(pL, "Bạn đã thua, hẹn gặp lại ở giải sau");
                }
            };
        };
        timerWIN.schedule(winDHVT, 5000);
    }
}