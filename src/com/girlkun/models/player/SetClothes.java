package com.girlkun.models.player;

import com.girlkun.models.item.Item;
import com.girlkun.utils.Logger;


public class SetClothes {

    private Player player;

    public SetClothes(Player player) {
        this.player = player;
    }

    public byte songoku;
    public byte thienXinHang;
    public byte kirin;

    public byte ocTieu;
    public byte pikkoroDaimao;
    public byte picolo;

    public byte kakarot;
    public byte cadic;
    public byte nappa;

    public byte basil;
    public byte setTL;
    public byte setDHD;
    public byte SetTinhAn;
    public byte SetNguyetAn;
    public byte SetNhatAn;
    public byte SetThienSu;

    public boolean godClothes;
    public boolean isSDXungQuanh = false;
    public int ctHaiTac = -1;
    public int ctSDDep = -1;

    public void setup() {
        setDefault();
        setupSKT();
        this.godClothes = true;
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id > 567 || item.template.id < 555) {
                    this.godClothes = false;
                    break;
                }
            } else {
                this.godClothes = false;
                break;
            }
        }
        Item ct = this.player.inventory.itemsBody.get(5);
        if (ct.isNotNullItem()) {
            switch (ct.template.id) {
                case 618:
                case 619:
                case 620:
                case 621:
                case 622:
                case 623:
                case 624:
                case 626:
                case 627:
                    this.ctHaiTac = ct.template.id;
                    break;

            }
        }
        
        Item ct1 = this.player.inventory.itemsBody.get(5);
        if (ct1.isNotNullItem()) {
            switch (ct1.template.id) {
                case 584:
                    this.ctSDDep = ct1.template.id;
                    break;

            }
        }

        this.player.setClothes.SetThienSu = 0;
           for (int i = 0; i < 5; i++) {
           Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
              if (item.template.id >= 1048 && item.template.id <= 1062) {
                 
                        player.setClothes.SetThienSu++;
                }
            }  
           }
           
                this.player.setClothes.SetTinhAn = 0;
           for (int i = 0; i < 5; i++) {
           Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                    int chiso = 0;
                  Item.ItemOption optionLevel = null;
                  for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 34) {
                        chiso = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                 if (optionLevel != null){
                        player.setClothes.SetTinhAn++;
                 }
                
            }  
           }
    }

    

    private void setupSKT() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                boolean isActSet = false;
                for (Item.ItemOption io : item.itemOptions) {
                    switch (io.optionTemplate.id) {
                        case 129:
                        case 141:
                            isActSet = true;
                            songoku++;
                            break;
                        case 127:
                        case 139:
                            isActSet = true;
                            thienXinHang++;
                            break;
                        case 128:
                        case 140:
                            isActSet = true;
                            kirin++;
                            break;
                        case 131:
                        case 143:
                            isActSet = true;
                            ocTieu++;
                            break;
                        case 132:
                        case 144:
                            isActSet = true;
                            pikkoroDaimao++;
                            break;
                        case 130:
                        case 142:
                            isActSet = true;
                            picolo++;
                            break;
                        case 135:
                        case 138:
                            isActSet = true;
                            nappa++;
                            break;
                        case 133:
                        case 136:
                            isActSet = true;
                            kakarot++;
                            break;
                        case 134:
                        case 137:
                            isActSet = true;
                            cadic++;
                            break;
                        case 21:
                            if (io.param == 120) {
                                setDHD++;
                            } else if(io.param == 15 || io.param == 16 || io.param == 17){
                                setTL++;
                            }
                            break;
                        case 192:
                        case 193:
                            isActSet = true;
                            basil++;
                            break;
                        case 117:
                            isSDXungQuanh = true;
                            break;
//                        case 34:
//                            isActSet = true;
//                            tinhan++;
//                            break;
//                        case 35:
//                            isActSet = true;
//                            nguyetan++;
//                            break;
//                        case 36:
//                            isActSet = true;
//                            nhatan++;
//                            break;
                    }

                    if (isActSet) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    private void setDefault() {
        this.songoku = 0;
        this.thienXinHang = 0;
        this.kirin = 0;
        this.ocTieu = 0;
        this.pikkoroDaimao = 0;
        this.picolo = 0;
        this.kakarot = 0;
        this.cadic = 0;
        this.nappa = 0;
        this.setDHD = 0;
        this.setTL =0;
        this.basil = 0;
        this.SetTinhAn = 0;
        this.SetNguyetAn = 0;
        this.SetNhatAn = 0;
        this.SetThienSu = 0;
        this.godClothes = false;
        this.ctHaiTac = -1;
    }

    public void dispose() {
        this.player = null;
    }
}
