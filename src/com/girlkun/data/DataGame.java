package com.girlkun.data;

import com.girlkun.models.Template.HeadAvatar;
import com.girlkun.models.Template.MapTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.girlkun.utils.FileIO;
import com.girlkun.services.Service;
import com.girlkun.models.skill.NClass;
import com.girlkun.models.skill.Skill;
import com.girlkun.models.Template.MobTemplate;
import com.girlkun.models.Template.NpcTemplate;
import com.girlkun.models.Template.SkillTemplate;
import com.girlkun.network.session.ISession;
import com.girlkun.network.io.Message;
import com.girlkun.server.Manager;
import com.girlkun.server.io.MySession;
import com.girlkun.utils.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class DataGame {

    public static byte vsData = 80;
    public static byte vsMap = 80;
    public static byte vsSkill = 6;
    public static byte vsItem = 80;
    public static int vsRes = 752011;

    public static String LINK_IP_PORT = "Girlkun75-1:127.0.0.1:14445:0";
    private static final String MOUNT_NUM = "733:1,734:2,735:3,743:4,744:5,746:6,795:7,849:8,897:9,920:10";
    public static final Map MAP_MOUNT_NUM = new HashMap();

    static {
        String[] array = MOUNT_NUM.split(",");
        for (String str : array) {
            String[] data = str.split(":");
            short num = (short) (Short.parseShort(data[1]) + 30000);
            MAP_MOUNT_NUM.put(data[0], num);
        }
    }

    private DataGame() {

    }

    public static void sendVersionGame(MySession session) {
        Message msg;
        try {
            msg = Service.getInstance().messageNotMap((byte) 4);
            msg.writer().writeByte(vsData);
            msg.writer().writeByte(vsMap);
            msg.writer().writeByte(vsSkill);
            msg.writer().writeByte(vsItem);
            msg.writer().writeByte(0);

            long[] smtieuchuan = {1000L, 3000L, 15000L, 40000L, 90000L, 170000L, 340000L, 700000L,
                    1500000L, 15000000L, 150000000L, 1500000000L, 5000000000L, 10000000000L, 40000000000L,
                    50010000000L, 60010000000L, 70010000000L, 80010000000L, 100010000000L};
            msg.writer().writeByte(smtieuchuan.length);
            for (int i = 0; i < smtieuchuan.length; i++) {
                msg.writer().writeLong(smtieuchuan[i]);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    //vcData
    public static void updateData(MySession session) {
        System.out.println("update data");
        byte[] dart = FileIO.readFile("data/girlkun/update_data/dart");
        byte[] arrow = FileIO.readFile("data/girlkun/update_data/arrow");
        byte[] effect = FileIO.readFile("data/girlkun/update_data/effect");
        byte[] image = FileIO.readFile("data/girlkun/update_data/image");
        byte[] part = FileIO.readFile("data/girlkun/update_data/part");
        byte[] skill = FileIO.readFile("data/girlkun/update_data/skill");

        Message msg;
        try {
            msg = new Message(-87);
            msg.writer().writeByte(vsData);
            msg.writer().writeInt(dart.length);
            msg.writer().write(dart);
            msg.writer().writeInt(arrow.length);
            msg.writer().write(arrow);
            msg.writer().writeInt(effect.length);
            msg.writer().write(effect);
            msg.writer().writeInt(image.length);
            msg.writer().write(image);
            msg.writer().writeInt(part.length);
            msg.writer().write(part);
            msg.writer().writeInt(skill.length);
            msg.writer().write(skill);

            session.doSendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    //vcMap
    public static void updateMap(MySession session) {
        Message msg;
        try {
            msg = Service.getInstance().messageNotMap((byte) 6);
            msg.writer().writeByte(vsMap);
            msg.writer().writeByte(Manager.MAP_TEMPLATES.length);
            for (MapTemplate temp : Manager.MAP_TEMPLATES) {
                msg.writer().writeUTF(temp.name);
            }
            msg.writer().writeByte(Manager.NPC_TEMPLATES.size());
            for (NpcTemplate temp : Manager.NPC_TEMPLATES) {
                msg.writer().writeUTF(temp.name);
                msg.writer().writeShort(temp.head);
                msg.writer().writeShort(temp.body);
                msg.writer().writeShort(temp.leg);
                msg.writer().writeByte(0);
            }
            msg.writer().writeByte(Manager.MOB_TEMPLATES.size());
            for (MobTemplate temp : Manager.MOB_TEMPLATES) {
                msg.writer().writeByte(temp.type);
                msg.writer().writeUTF(temp.name);
                msg.writer().writeInt(temp.hp);
                msg.writer().writeByte(temp.rangeMove);
                msg.writer().writeByte(temp.speed);
                msg.writer().writeByte(temp.dartType);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(DataGame.class, e);
        }
    }

    //vcSkill
    public static void updateSkill(MySession session) {
        System.out.println("update skill");
        Message msg;
        try {
            msg = new Message(-28);

            msg.writer().writeByte(7);
            msg.writer().writeByte(vsSkill);
            msg.writer().writeByte(0); //count skill option

            msg.writer().writeByte(Manager.NCLASS.size());
            for (NClass nClass : Manager.NCLASS) {
                msg.writer().writeUTF(nClass.name);

                msg.writer().writeByte(nClass.skillTemplatess.size());
                for (SkillTemplate skillTemp : nClass.skillTemplatess) {
                    msg.writer().writeByte(skillTemp.id);
                    msg.writer().writeUTF(skillTemp.name);
                    msg.writer().writeByte(skillTemp.maxPoint);
                    msg.writer().writeByte(skillTemp.manaUseType);
                    msg.writer().writeByte(skillTemp.type);
                    msg.writer().writeShort(skillTemp.iconId);
                    msg.writer().writeUTF(skillTemp.damInfo);
                    msg.writer().writeUTF("Basil");
                    if (skillTemp.id != 0) {
                        msg.writer().writeByte(skillTemp.skillss.size());
                        for (Skill skill : skillTemp.skillss) {
                            msg.writer().writeShort(skill.skillId);
                            msg.writer().writeByte(skill.point);
                            msg.writer().writeLong(skill.powRequire);
                            msg.writer().writeShort(skill.manaUse);
                            msg.writer().writeInt(skill.coolDown);
                            msg.writer().writeShort(skill.dx);
                            msg.writer().writeShort(skill.dy);
                            msg.writer().writeByte(skill.maxFight);
                            msg.writer().writeShort(skill.damage);
                            msg.writer().writeShort(skill.price);
                            msg.writer().writeUTF(skill.moreInfo);
                        }
                    } else {
                        //Thêm 2 skill trống 105, 106
                        msg.writer().writeByte(skillTemp.skillss.size() + 2);
                        for (Skill skill : skillTemp.skillss) {
                            msg.writer().writeShort(skill.skillId);
                            msg.writer().writeByte(skill.point);
                            msg.writer().writeLong(skill.powRequire);
                            msg.writer().writeShort(skill.manaUse);
                            msg.writer().writeInt(skill.coolDown);
                            msg.writer().writeShort(skill.dx);
                            msg.writer().writeShort(skill.dy);
                            msg.writer().writeByte(skill.maxFight);
                            msg.writer().writeShort(skill.damage);
                            msg.writer().writeShort(skill.price);
                            msg.writer().writeUTF(skill.moreInfo);
                        }
                        for (int i = 105; i <= 106; i++) {
                            msg.writer().writeShort(i);
                            msg.writer().writeByte(0);
                            msg.writer().writeLong(0);
                            msg.writer().writeShort(0);
                            msg.writer().writeInt(0);
                            msg.writer().writeShort(0);
                            msg.writer().writeShort(0);
                            msg.writer().writeByte(0);
                            msg.writer().writeShort(0);
                            msg.writer().writeShort(0);
                            msg.writer().writeUTF("");
                        }
                    }
                }
            }
            session.doSendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(DataGame.class, e);
        }
    }

    


    public static void sendDataImageVersion(MySession session) {
        Message msg;
        try {
            msg = new Message(-111);
            msg.writer().write(FileIO.readFile("data/girlkun/data_img_version/x" + session.zoomLevel + "/img_version"));
            session.doSendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(DataGame.class, e);
        }
    }

    //--------------------------------------------------------------------------
    public static void sendEffectTemplate(MySession session, int id) {
        Message msg;
        try {
            byte[] eff_data = FileIO.readFile("data/girlkun/effdata/x" + session.zoomLevel + "/" + id);
            msg = new Message(-66);
            msg.writer().write(eff_data);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }
public static void effData(MySession session, int id, int... idtemp) {
        int idT = id;
        if(idtemp.length > 0 && idtemp[0] != 0){
            idT = idtemp[0];
        }
        Message msg;
        try {
            byte[] effData = FileIO.readFile("data/girlkun/effect/x" + session.zoomLevel + "/data/DataEffect_" + idT);
            byte[] effImg = FileIO.readFile("data/girlkun/effect/x" + session.zoomLevel + "/img/ImgEffect_" + idT+".png");
            msg = new Message(-66);
            msg.writer().writeShort(id);
            msg.writer().writeInt(effData.length);
            msg.writer().write(effData);
            msg.writer().writeByte(0);
            msg.writer().writeInt(effImg.length);
            msg.writer().write(effImg);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }
    public static void sendItemBGTemplate(MySession session, int id) {
        Message msg;
        try {
            byte[] bg_temp = FileIO.readFile("data/girlkun/item_bg_temp/x" + session.zoomLevel + "/" + id + ".png");
            msg = new Message(-32);
            msg.writer().writeShort(id);
            msg.writer().writeInt(bg_temp.length);
            msg.writer().write(bg_temp);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(DataGame.class, e);
        }
    }

    public static void sendDataItemBG(MySession session) {
        Message msg;
        try {
            byte[] item_bg = FileIO.readFile("data/girlkun/item_bg_temp/item_bg_data");
            msg = new Message(-31);
            msg.writer().write(item_bg);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public static void sendIcon(MySession session, int id) {
        Message msg;
        try {
            byte[] icon = FileIO.readFile("data/girlkun/icon/x" + session.zoomLevel + "/" + id + ".png");
            msg = new Message(-67);
            msg.writer().writeInt(id);
            msg.writer().writeInt(icon.length);
            msg.writer().write(icon);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public static void sendSmallVersion(MySession session) {
        Message msg;
        try {
            msg = new Message(-77);
            byte[] data = FileIO.readFile("data/girlkun/data_img_version/x" + session.zoomLevel + "/img_version");
            if (data != null) {
                msg.writer().write(data);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }


    private static List<Integer> list = new ArrayList<>();

    public static void requestMobTemplate(MySession session, int id) {
//        if (list.contains(id)) {
//            System.out.println("send mob: " + id);
//            return;
//        } else {
//            list.add(id);
//        }
        Message msg;
        try {
            byte[] mob = FileIO.readFile("data/girlkun/mob/x" + session.zoomLevel + "/" + id);
            msg = new Message(11);
            msg.writer().writeByte(id);
            msg.writer().write(mob);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public static void sendTileSetInfo(MySession session) {
        Message msg;
        try {
            msg = new Message(-82);
            msg.writer().write(FileIO.readFile("data/girlkun/map/tile_set_info"));
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public static void mainz(String[] args) {
        try {
            File folder = new File("data/girlkun/map/tile_map_data");
            for (File f : folder.listFiles()) {
                if (f.getName().equals("5")) {
                    DataInputStream dis = new DataInputStream(new FileInputStream(f));
                    int w = dis.readByte();
                    int h = dis.readByte();
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            System.out.print(dis.readByte() + " ");
                        }
                        System.out.println("");
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void mainzz(String[] args) throws Exception {
        List<String> list = new ArrayList<>();
        File folder = new File("C:\\Users\\admin\\Desktop\\nro qltk java by girlkun\\girlkun\\map\\tile_map_data");
//        for (File f : folder.listFiles()) {
//            list.add(f.getName());
//        }
        folder = new File("C:\\Users\\admin\\Desktop\\cbro\\data\\girlkun\\map\\tile_map_dataz");
        for (File f : folder.listFiles()) {
            if (!list.contains(f.getName())) {
                DataInputStream dis = new DataInputStream(new FileInputStream(f));
                dis.readByte();
                int w = dis.readByte();
                int h = dis.readByte();
                byte[] data = new byte[w * h];
                for (int i = 0; i < data.length; i++) {
                    data[i] = dis.readByte();
                }
                dis.close();
                DataOutputStream dos = new DataOutputStream(new FileOutputStream("C:\\Users\\admin\\Desktop\\cbro\\data\\girlkun\\map\\tile_map_data\\" + f.getName()));
                dos.writeByte(w);
                dos.writeByte(h);
                for (int i = 0; i < data.length; i++) {
                    System.out.print(data[i] + ",");
                    dos.writeByte(data[i]);
                }
                dos.flush();
                dos.close();
            }
            System.out.println("-----------------------------------------");
        }
        System.out.println("done");
    }

    //data vẽ map
    public static void sendMapTemp(MySession session, int id) {
        Message msg;
        try {
            msg = new Message(-28);
            msg.writer().writeByte(10);
            msg.writer().write(FileIO.readFile("data/girlkun/map/tile_map_data/" + id));
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(DataGame.class, e);
        }
    }

    //head-avatar
    public static void sendHeadAvatar(Message msg) {
        try {
            msg.writer().writeShort(Manager.HEAD_AVATARS.size());
            for (HeadAvatar ha : Manager.HEAD_AVATARS) {
                msg.writer().writeShort(ha.headId);
                msg.writer().writeShort(ha.avatarId);
            }
        } catch (Exception e) {
        }
    }

    public static void sendImageByName(MySession session, String imgName) {
        Message msg;
        try {
            msg = new Message(66);
            msg.writer().writeUTF(imgName);
            msg.writer().writeByte(Manager.getNFrameImageByName(imgName));
            byte[] data = FileIO.readFile("data/girlkun/img_by_name/x" + session.zoomLevel + "/" + imgName + ".png");
            msg.writer().writeInt(data.length);
            msg.writer().write(data);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    //download data res --------------------------------------------------------
    public static void sendVersionRes(ISession session) {
        Message msg;
        try {
            msg = new Message(-74);
            msg.writer().writeByte(0);
            msg.writer().writeInt(vsRes);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public static void sendSizeRes(MySession session) {
        Message msg;
        try {
            msg = new Message(-74);
            msg.writer().writeByte(1);
            msg.writer().writeShort(new File("data/girlkun/res/x" + session.zoomLevel).listFiles().length);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public static void sendRes(MySession session) {
        Message msg;
        try {
            for (final File fileEntry : new File("data/girlkun/res/x" + session.zoomLevel).listFiles()) {
                String original = fileEntry.getName();
                byte[] res = FileIO.readFile(fileEntry.getAbsolutePath());
                msg = new Message(-74);
                msg.writer().writeByte(2);
                msg.writer().writeUTF(original);
                msg.writer().writeInt(res.length);
                msg.writer().write(res);
                session.sendMessage(msg);
                msg.cleanup();
                Thread.sleep(10);
            }

            msg = new Message(-74);
            msg.writer().writeByte(3);
            msg.writer().writeInt(vsRes);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(DataGame.class, e);
        }
    }

    public static void sendLinkIP(MySession session) {
        Message msg;
        try {
            msg = new Message(-29);
            msg.writer().writeByte(2);
            msg.writer().writeUTF(LINK_IP_PORT + ",0,0");
            msg.writer().writeByte(1);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }
    /**
     * -server -Xms24G -Xmx24G -XX:PermSize=512m -XX:+UseG1GC
     * -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=20 -XX:ConcGCThreads=5
     * -XX:InitiatingHeapOccupancyPercent=70
     */
}
