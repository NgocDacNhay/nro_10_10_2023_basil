package com.girlkun.models.boss;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.skill.Skill;
import com.girlkun.utils.Util;
import com.girlkun.models.boss.BossID;

/**
 * @Stole By Arriety❤
 */
public class BossesData {

    /**
     * Prefix text chat |-1| Boss chat |-2| Player in map chat |-3| Parent chat
     * |0|,|1|,|n| Index boss in list chat
     */
    private static final int[][] FULL_DRAGON = new int[][]{{Skill.DRAGON, 1}, {Skill.DRAGON, 2}, {Skill.DRAGON, 3}, {Skill.DRAGON, 4}, {Skill.DRAGON, 5}, {Skill.DRAGON, 6}, {Skill.DRAGON, 7}};
    private static final int[][] FULL_DEMON = new int[][]{{Skill.DEMON, 1}, {Skill.DEMON, 2}, {Skill.DEMON, 3}, {Skill.DEMON, 4}, {Skill.DEMON, 5}, {Skill.DEMON, 6}, {Skill.DEMON, 7}};
    private static final int[][] FULL_GALICK = new int[][]{{Skill.GALICK, 1}, {Skill.GALICK, 2}, {Skill.GALICK, 3}, {Skill.GALICK, 4}, {Skill.GALICK, 5}, {Skill.GALICK, 6}, {Skill.GALICK, 7}};
    private static final int[][] FULL_KAMEJOKO = new int[][]{{Skill.KAMEJOKO, 1}, {Skill.KAMEJOKO, 2}, {Skill.KAMEJOKO, 3}, {Skill.KAMEJOKO, 4}, {Skill.KAMEJOKO, 5}, {Skill.KAMEJOKO, 6}, {Skill.KAMEJOKO, 7}};
    private static final int[][] FULL_MASENKO = new int[][]{{Skill.MASENKO, 1}, {Skill.MASENKO, 2}, {Skill.MASENKO, 3}, {Skill.MASENKO, 4}, {Skill.MASENKO, 5}, {Skill.MASENKO, 6}, {Skill.MASENKO, 7}};
    private static final int[][] FULL_ANTOMIC = new int[][]{{Skill.ANTOMIC, 1}, {Skill.ANTOMIC, 2}, {Skill.ANTOMIC, 3}, {Skill.ANTOMIC, 4}, {Skill.ANTOMIC, 5}, {Skill.ANTOMIC, 6}, {Skill.ANTOMIC, 7}};
    private static final int[][] FULL_LIENHOAN = new int[][]{{Skill.LIEN_HOAN, 1}, {Skill.LIEN_HOAN, 2}, {Skill.LIEN_HOAN, 3}, {Skill.LIEN_HOAN, 4}, {Skill.LIEN_HOAN, 5}, {Skill.LIEN_HOAN, 6}, {Skill.LIEN_HOAN, 7}};
    private static final int[][] FULL_TDHS = new int[][]{{Skill.THAI_DUONG_HA_SAN, 1}, {Skill.THAI_DUONG_HA_SAN, 2}, {Skill.THAI_DUONG_HA_SAN, 3}, {Skill.THAI_DUONG_HA_SAN, 4}, {Skill.THAI_DUONG_HA_SAN, 5}, {Skill.THAI_DUONG_HA_SAN, 6}, {Skill.THAI_DUONG_HA_SAN, 7}};

    private static final int REST_1_S = 1;
    private static final int REST_2_S = 2;
    private static final int REST_5_S = 5;
    private static final int REST_10_S = 10;
    private static final int REST_20_S = 20;
    private static final int REST_30_S = 30;
    private static final int REST_1_M = 60;
    private static final int REST_2_M = 120;
    private static final int REST_5_M = 300;
    private static final int REST_10_M = 600;
    private static final int REST_15_M = 900;
    private static final int REST_30_M = 1800;
    private static final int REST_1_H = 3600;
    private static final int REST_24_H = 86400000;

    //************************************************************************** Boss nappa
    public static final BossData KUKU = new BossData(
            "Kuku", //name
            ConstPlayer.XAYDA, //gender
            new short[]{159, 160, 161, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            9000, //dame
            new long[]{10000000000L}, //hp
            new int[]{29}, //map join
            new int[][]{
                {Skill.MASENKO, 3, 1000},
                {Skill.LIEN_HOAN, 7, 1000}},
            new String[]{"|-1|Hế lô em,anh đứng đây từ chiều",
                "|-1|Mày hiểu thế là sao chứ? Cuối cùng tao đã có thể giết mày!",
                "|-2|Tao lại sợ mày quá cơ,cho bố cái địa chỉ!",
                "|-1|Mày làm tao phấn khích rồi đấy hahaha.."
            }, //text chat 1
            new String[]{"|-1|Tao hơn hẳn mày, mày nên cầu cho may mắn ở phía mày đi",
                "|-1|Ha ha ha! Mắt mày mù à? Nhìn máy đo chỉ số đi!!",
                "|-1|Định chạy trốn hả, hử",
                "|-1|Ta sẽ tàn sát khu này trong vòng 5 phút nữa",
                "|-1|Hahaha mày đây rồi",
                "|-1|Tao đã có lệnh từ đại ca Fide rồi"
            }, //text chat 2
            new String[]{"|-2|Đẹp trai nó phải thế"}, //text chat 3
            REST_5_M //second rest
    );

    public static final BossData MAP_DAU_DINH = new BossData(
            "Mập Đầu Đinh", //name
            ConstPlayer.XAYDA, //gender
            new short[]{165, 166, 167, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{13000000}, //hp
            new int[]{29}, //map join
            new int[][]{
                {Skill.GALICK, 7, 1000},
                {Skill.ANTOMIC, 7, 10000},},//skill //skill
            new String[]{"|-1|Tao là Mập Đầu Đinh",
                "|-2|Em chào đại ca!",
                "|-1|Ai thèm làm đại ca của mày?"
            }, //text chat 1
            new String[]{"|-1|Chết hết đi cho tao",
                "|-1|Tao sẽ giết hết bọn mày",
                "|-1|Hahaha",
                "|-1|Tao chỉ cần 10 phút để giết hết bọn mày",
                "|-1|Được rồi tao sẽ thổi bay hết bọn mày",
                "|-1|Muốn đùa thì thêm tí muối đi!",
                "|-2|Thằng này,tao nhịn mày lâu lắm rồi ấy nhá",
                "|-2|Coi thường nhau quá đấy",}, //text chat 2
            new String[]{"|-1|Ôi bạn ơi ....ơi!!!"}, //text chat 3
            REST_5_M //second rest
    );

    public static final BossData RAMBO = new BossData(
            "Rambo", //name
            ConstPlayer.XAYDA, //gender
            new short[]{162, 163, 164, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            12400, //dame
            new long[]{15000000}, //hp
            new int[]{29}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.KAMEJOKO, 1, 1000},},//skill //skill
            new String[]{"|-2|Xin chào, lâu lắm không gặp!",
                "|-2|Tao đã kết liễu Mập đầu đinh rồi, lần này đến lượt mày...",
                "|-1|Mày đã giết Mập đầu đinh sao..!? Đừng.. đừng hòng lừa tao!",
                "|-2|Vậy sao mày không thử sức mạnh của tao luôn đi?"
            }, //text chat 1
            new String[]{"|-1|Hahaha",
                "|-1|Ngạc nhiên thật, đúng là mày đã tiến bộ rất nhanh..",
                "|-1|Tao sẽ cho mày biết lý do tại sao tao lại không dùng đến năng lực thực sự..",
                "|-1|Đến tao còn không thắng nổi thì đừng mộng tưởng đối đầu với đại ca Fide!",
                "|-1|Ha ha ha! Ngươi tưởng chạy trốn được sao?",
                "|-2|Oái..!",
                "|-2|Đừng tưởng thế này là xong..! Tao sẽ còn mạnh hơn nữa!",}, //text chat 2
            new String[]{"|-1|Ôi bạn ơi..."}, //text chat 3
            REST_5_M //second rest
    );

    //************************************************************************** Boss tiểu đội sát thủ
    public static final BossData SO_4 = new BossData(
            "Số 4 Guldo", //name
            ConstPlayer.XAYDA, //gender
            new short[]{168, 169, 170, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            17000, //dame
            new long[]{70000000}, //hp
            new int[]{83}, //map join
            //           new int[]{86}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.MASENKO, 7, 1000},},//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Các ngươi không nhúc nhích được sao?",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{"|-1|Cay quá!",
                "|-1|Ta mà lại thua được sao?",
                "|-1|Hãy trả thù cho ta!"
            }, //text chat 3
            REST_10_M
    );

    public static final BossData SO_3 = new BossData(
            "Số 3 Recome", //name
            ConstPlayer.XAYDA, //gender
            new short[]{174, 175, 176, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            18000, //dame
            new long[]{80000000}, //hp
            new int[]{83}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.ANTOMIC, 4, 1000},},//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{"|-1|Cay quá!",
                "|-1|Ta mà lại thua được sao?",
                "|-1|Hãy trả thù cho ta!"
            }, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData SO_2 = new BossData(
            "Số 2 Jeice", //name
            ConstPlayer.XAYDA, //gender
            new short[]{171, 172, 173, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            20720, //dame
            new long[]{90000000}, //hp
            new int[]{83}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.ANTOMIC, 3, 3000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{"|-1|Cay quá!",
                "|-1|Ta mà lại thua được sao?",
                "|-1|Hãy trả thù cho ta!"
            }, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData SO_1 = new BossData(
            "Số 1 Burter", //name
            ConstPlayer.XAYDA, //gender
            new short[]{177, 178, 179, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            25020, //dame
            new long[]{100000000}, //hp
            new int[]{83}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.KAMEJOKO, 4, 10000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{"|-1|Cay quá!",
                "|-1|Ta mà lại thua được sao?",
                "|-1|Hãy trả thù cho ta!"
            }, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData TIEU_DOI_TRUONG = new BossData(
            "Tiểu đội trưởng Ginyu", //name
            ConstPlayer.XAYDA, //gender
            new short[]{180, 181, 182, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            30000, //dame
            new long[]{150000000L}, //hp
            new int[]{83}, //map join
            new int[][]{
                {Skill.MASENKO, 7, 1000},
                {Skill.GALICK, 7, 1000},
            },//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{"|-1|Cay quá!"
            }, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    //************************************************************************** Boss Fide đại ca
    public static final BossData FIDE_DAI_CA_1 = new BossData(
            "Fide đại ca 1", //name
            ConstPlayer.XAYDA, //gender
            new short[]{183, 184, 185, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            50000, //dame
            new long[]{300000000L}, //hp
            new int[]{80}, //map join
            (int[][]) Util.addArray(FULL_ANTOMIC, FULL_GALICK), //skill
            new String[]{"|-1|Khá lắm con trai, dám phá tan giấc mộng của ta...",
                "|-1|Ta không thấy đám Ginyu đâu,... các ngươi đã giết chúng rồi à?",
                "|-1|Tuy không biết các ngươi dùng quỷ kế gì, nhưng ta rất ấn tượng đấy!",
                "|-1|Không thể tha thứ, ta không thể tha cho lũ sâu bọ các ngươi được!!"
            }, //text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                "|-1|Toàn bọn tốt thí",
                "|-2|Không..thể..nào!!",
                "|-2|Không ngờ..Hắn mạnh cỡ này sao..!!",
                "|-1|Chúng mày nghĩ kiến lại thắng nổi khủng long sao?",
                "|-1|Hô hô hô",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Biến hình, hây aaaa..."}, //text chat 3
            REST_10_M //second rest
    );

    public static final BossData FIDE_DAI_CA_2 = new BossData(
            "Fide đại ca 2", //name
            ConstPlayer.XAYDA, //gender
            new short[]{186, 187, 188, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            70000, //dame
            new long[]{300000000L}, //hp
            new int[]{80}, //map join
            (int[][]) Util.addArray(FULL_ANTOMIC, FULL_LIENHOAN), //skill
            new String[]{"|-1|Hê hê, cẩn thận đi",
                "|-1|Nếu đã biến thành thế này thì ta sẽ không nhùn nhặn như trước đâu"
            }, //text chat 1
            new String[]{"|-1|Oải rồi hả?",
                "|-1|Ê cố lên nhóc",
                "|-1|Ôi, xin lỗi nhé. Sức mạnh lớn quá nên ta cũng chẳng điều khiển nổi nữa!",
                "|-1|Hahaha! Ấn tượng đấy! Tên nào cũng lủi rất nhanh!",
                "|-2|A...Tốc độ nhanh quá!",
                "|-1|Hình như... mày không phải là một thằng nhóc bình thường!",
                "|-1|Mấy đòn vừa rồi, nói thật là cũng đau đấy!",
                "|-1|Nhưng tiếc rằng đối thủ của mày lại là Fide này...",
                "|-2|Chết tiệt.. chúng ta đã đánh giá quá thấp sức mạnh của hắn!!",
                "|-2|Đồ..Đồ quái vật..!",
                "|-2|Tốc độ kinh hoàng quá! Ai mà né nổi chứ!",}, //text chat 2
            new String[]{"|-1|Ác quỷ biến hình, Graaaaa...."}, //text chat 3
            TypeAppear.ANOTHER_LEVEL //type appear
    );

    public static final BossData FIDE_DAI_CA_3 = new BossData(
            "Fide đại ca 3", //name
            ConstPlayer.XAYDA, //gender
            new short[]{189, 190, 191, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            72000, //dame
            new long[]{300000000L}, //hp
            new int[]{80}, //map join
            (int[][]) Util.addArray(FULL_MASENKO, FULL_LIENHOAN), //skill
            new String[]{"|-1|Ta sẽ cho các ngươi thấy đâu mới là sức mạnh của ta!!"}, //text chat 1
            new String[]{"|-1|Ta nói các ngươi rồi! Sức mạnh này của ta còn đáng sợ hơn địa ngục!!",
                "|-1|Ta chơi thêm chút nữa chắc ngươi chóng mặt buồn nôn mất!!",
                "|-2|Ăn gì mà khỏe thế!",
                "|-2|Chết đi Fide!!!!",
                "|-1|Hô hô hô hô",
                "|-1|Chán thật! Khí của ngươi sắp hết rồi. Để ta tiễn ngươi về địa ngục!",
                "|-1|Ngươi quá tự cao rồi đấy,xem ta đây!",}, //text chat 2
            new String[]{"|-1|Lũ khốn..",
                "|-1|..Một ngày nào đó ta sẽ quay lại và trả thù các ngươi",
                "|-1|Nhớ mặt tao đấy !",}, //text chat 3
            TypeAppear.ANOTHER_LEVEL //type appear
    );

    //************************************************************************** Boss Android
    public static final BossData DR_KORE = new BossData(
            "Dr.Kôrê", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{255, 256, 257, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            80000, //dame
            new long[]{250000000L}, //hp
            new int[]{96, 94, 93}, //map join
            new int[][]{
                
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 1000},},//skill
            //skill
            new String[]{"|-2|Chào anh! em đứng đây từ chiều",
                "|-1|Quái lạ! Sao chúng biết rõ tung tích của bọn ta thế nhỉ?",
                "|-1|Chúng còn biết chính xác ta sẽ xuất hiện ở đây để đón đánh nữa!",
                "|-1|Chúng mày là ai từ đâu tới?Cho tao xin cái địa chỉ",
                "|-2|Điều ấy biết hay không cũng không còn quan trọng nữa",
                "|-1|Ừ bọn bây chỉ là hạng tôm tép ta chẳng cần biết tên làm gì!",
                "|-1|Số 19! Xuất chiêu đi nào",
                "|0|Okê đại ca, em sẽ xử lý bọn này trong vòng 2 tiếng."
            }, //text chat 1
            new String[]{"|-1|Oải rồi hả?",
                "|-1|Ê cố lên nhóc",
                "|-1|Chán",
                "|-1|Mi khá đấy, nhưng so với ta cũng chỉ là hạng tôm tép",
                "|-1|Lôi Công Trảo",
                "|-1|Cho dù ngươi có mạnh đến đâu.. thì cũng không đánh bại được rôbốt bọn ta",
                "|-2|Lão già khôn thật!!",
                "|-2|Hừ! Lão già khốn kiếp!",}, //text chat 2
            new String[]{}, //text chat 3
            REST_10_S, //second rest        
            new int[]{BossID.ANDROID_19}
            
    );

    public static final BossData ANDROID_19 = new BossData(
            "Android 19", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{249, 250, 251, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            82000, //dame
            new long[]{200000000}, //hp
            new int[]{96, 94, 93}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.LIEN_HOAN, 7, 10000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?",
                "|-1|Ê cố lên nhóc",
                "|-1|Chán",
                "|-1|Ngươi sẽ không bao giờ thắng được đâu!!",
                "|-2|Ngươi vừa hút được nhiều rồi đấy, nhưng giờ thì đừng hòng!!",}, //text chat 2
            new String[]{}, //text chat 3           
            TypeAppear.APPEAR_WITH_ANOTHER
    );

    //**************************************************************************
    public static final BossData ANDROID_13 = new BossData(
            "Android 13", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{252, 253, 254, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            80550, //dame
            new long[]{300000000L}, //hp
            new int[]{84, 104}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 10000},},//skill
            new String[]{"|-1|Sôn..gôku",
                "|-2|Lại là tiến sĩ Kôrê à.. rốt cuộc ông ta đã tạo ra bao nhiêu rôbốt nhân tạo thế nhỉ?",
                "|-1|Bọn ta là rôbốt sát thủ, sinh ra từ máy tính ngài Kôrê,..",
                "|-1|..cho một mục tiêu duy nhất là giết Sôngôku!",
                "|-2|Máy tính? Để giết Gôku sao?",
                "|-1|Mong muốn trả thù Gôku của ngài Kôrê đã được lưu hết vào máy tính..",
                "|-1|.., Bọn ta sinh ra từ lòng căm thù ngày một tăng bên trong chiếc máy tính có chứa mong muốn trả thù",
                "|-1|Mục tiêu của bọn ta chỉ là Gôku, nhưng mà.. nếu ngươi mà cản đường thì là chuyện khác!",}, //text chat 1
            new String[]{"|-1|Sao thế hả? Ta mới chỉ khởi động thôi mà!",
                "|-2|Ngươi đánh giá thấp bọn ta quá đấy!",
                "|-2|Đừng có tưởng bở, lũ sâu bọ!",
                "|-1|Nếu có ý định gây trở ngại cho cuộc chiến giữa ta và Sôngôku, thì ta cũng sẽ giết ngươi ngay lập tức",
                "|-2|Ngươi tưởng ta để cho ngươi giết được ta ngay à?",
                "|-2|Đúng là mạnh mồm thật đấy!",
                "|-2|Đỡ này",}, //text chat 2
            new String[]{"|-1|Sô..Sông...gôku....."}, //text chat 3
            REST_10_S
    );

    public static final BossData ANDROID_14 = new BossData(
            "Android 14", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{246, 247, 248, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            80000, //dame
            new long[]{400000000L}, //hp
            new int[]{84, 104}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 1000},},//skill
            new String[]{"|-2|Các ngươi là ai?",
                "|-2|Ta không thể cảm nhận được khí của các ngươi, các ngươi không phải là con người đúng chứ?",
                "|-2|Ta hiểu rồi, các ngươi là rôbốt sát thủ do tiến sĩ Kôrê tạo ra chứ gì?"
            }, //text chat 1
            new String[]{}, //text chat 2
            new String[]{"|0|Số 14 và số 15 tiêu tùng cả rồi à?"}, //text chat 3
            REST_10_S            
    );

    public static final BossData ANDROID_15 = new BossData(
            "Android 15", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{261, 262, 263, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            19200, //dame
            new long[]{300000000L}, //hp
            new int[]{84, 104}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 1000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{"|-2|Thì ra vẫn chỉ là một đống sắt vụn!"}, //text chat 3
            REST_10_S
    );
//**************************************************************************

    public static final BossData PIC = new BossData(
            "Số 17 Pic", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{237, 238, 239, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            17220, //dame
            new long[]{550000000L}, //hp
            new int[]{97, 98, 99}, //map join
            (int[][]) Util.addArray(FULL_GALICK, FULL_KAMEJOKO), //skill
            new String[]{"|-1|Chào! Có Gôku ở đây không?",
                "|-3|Tôi không nghĩ Gôku ở đây đâu!",
                "|-2|Biến khỏi đây đi, Gôku không có ở đây đâu!",
                "|-1|Bọn ta cũng nghĩ vậy, ngươi nói cho bọn ta biết hắn ở đâu được không!?",
                "|-2|Ngươi nghĩ bọn ta sẽ nói sao??",
                "|-1|Nếu ngươi không chịu nói bọn ta sẽ phải ra tay.."
            }, //text chat 1
            new String[]{"|-1|Ngươi thực sự rất mạnh dù không phải là một rôbốt. Ngươi không phải là Piccôlô",
                "|-1|Nhưng ta không quan tâm ngươi là ai, ta chỉ cần biết Gôku đang ở đâu!",
                "|-1|Sao ngươi không chịu nói cho ta biết chứ!?",
                "|-2|Mục đích của ngươi không phải là giết Gôku sao? Vì vậy ta không nói cho ngươi biết cậu ấy đang ở đâu",
                "|-1|Vậy thì ta bắt buộc phải tiếp tục đánh buộc ngươi phải nói ra!",
                "|-1|Lần này ta không nương tay đâu!",
                "|-2|Ngươi thực sự rất nhanh. Nhưng chưa đủ thực lực đâu!!",
                "|-1|Cái gì!? Đó là điều ngu ngốc nhất ta từng nghe.. ta là chiến binh mạnh nhất từ trước đến giờ.!",
                "|1|Hắn thực sự rất mạnh, đúng là cuộc chiến cân sức",
                "|-3|Pic, trả nhẽ cậu lại để thua mấy tên nhóc vặt này sao?"
            }, //text chat 2
            new String[]{"|1|Pic tiêu rồi, tớ lên trước nhé!",
                "|-3|Okê, xin cứ tự nhiên"
            }, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );

    public static final BossData POC = new BossData(
            "Số 18 Poc", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{240, 241, 242, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            18000, //dame
            new long[]{550000000L}, //hp
            new int[]{97, 98, 99}, //map join
            (int[][]) Util.addArray(FULL_KAMEJOKO), //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Đừng tưởng ta đây là con gái mà dễ bắt nạt nhé",
                "|-1|Khôn hồn thì chỉ chỗ Gôku cho bọn ta nhanh đi",
                "|-3|Coi kìa, một lũ xúm lại bắt nạt một cô gái kìa..",
                "|-1|Đừng có mà trọng nam khinh nữ",
                "|-2|Tại sao cô gái xinh đẹp thế này mà lại là rôbốt nhỉ?"
            }, //text chat 2
            new String[]{"|-2|Cô gái xinh đẹp vậy mà lại bị tên tiến sĩ Kôrê biến thành người máy.."}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );

    public static final BossData KING_KONG = new BossData(
            "Số 16 King Kong", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{243, 244, 245, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{550000000L}, //hp
            new int[]{97, 98, 99}, //map join
            (int[][]) Util.addArray(FULL_LIENHOAN, FULL_MASENKO), //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Mau đền mạng cho những người bạn của ta",
                "|-1|Sức mạnh của ta chênh nhau với các ngươi một trời một vực đấy!",
                "|-1|Thằng kia đừng để bọn nó trói tao !"
            }, //text chat 2
            new String[]{}, //text chat 3
            REST_10_M,
            new int[]{BossID.PIC, BossID.POC}
    );
    //************************************************************************** Boss cell

    public static final BossData XEN_BO_HUNG_1 = new BossData(
            "Xên bọ hung",
            ConstPlayer.XAYDA,
            new short[]{228, 229, 230, -1, -1, -1},
            80000,
            new long[]{500000000L},
            new int[]{100},
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.LIEN_HOAN, 7, 10000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 50000}},//skill
            new String[]{"|-2|Cái gì kia vậy!? Đó là loài gì thế!!?",
                "|-1|Hôm nay sẽ là ngày đáng nhớ đây!",
                "|-1|Ta sẽ hấp thụ số 17 và 18 để đạt được dạng hoàn hảo!",
                "|-1|Sao vậy, Picôlô? Nếu ngươi muốn ngăn ta lại thì xong lên đi chứ!?"
            }, //text chat 1
            new String[]{"|-2|Hắn làm ta bất ngờ đấy! Khốn kiếp!",
                "|-2|Tên đó muốn hấp thụ số 17 và 18 sao?",
                "|-1|Đến đây nào! Khi kết hợp với ta, ngươi sẽ trở nên bất bại, trở thành một thể sống hoàn mỹ!",
                "|-2|Mơ đi, sao ta phải để ngươi hấp thu hả!?",
                "|-2|Dù muốn hay không, ngươi cũng sẽ bị ta hấp thu thôi..",
                "|-2|Chúng ta không thể để hắn đạt đến dạng hoàn hảo được!",
                "|-2|Mục tiêu của hắn không phải là Sôngôku.., mà là phá hủy cả vũ trụ này!",
                "|-1|Làm đứt đuôi ta ư? Đừng quên ta có tế bào của Picôlô!!",
                "|-1|Ta có thể tái tạo.. mọi bộ phận cơ thể!",
                "|-2|Vậy thì để ngăn cản ngươi đạt đến dạng hoàn hảo, ta phải tiêu diệt ngươi!",
                "|-2|Hắn quá mạnh! Mình có thể làm được gì đây!?",
                "|-1|Có vẻ như ta đã trở nên quá mạnh, chắc là ta đã giết nhiều người hơn dự tính!!",
                "|-1|Ngươi không thể thắng nổi ta! Từ bỏ đi!!",
                "|-1|Đến lúc ta hấp thu ngươi rồi",
                "|-2|Đồ quái vật chết tiệt...",
                "|-1|Hê hê hê, rồi ngươi sẽ trở thành một phần của con quái vật này thôi!",
                "|-1|Lại thêm một tên ngốc nữa chán sống!"
            }, //text chat 2
            new String[]{"|-2|Khốn kiếp, Pic.. hắn bị Cell hấp thu rồi!!"}, //text chat 3
            REST_15_M
    );

    public static final BossData XEN_BO_HUNG_2 = new BossData(
            "Xên bọ hung 2",
            ConstPlayer.XAYDA,
            new short[]{231, 232, 233, -1, -1, -1},
            200000,
            new long[]{500000000L},
            new int[]{100},
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.KAMEJOKO, 7, 1000},
                //                    {Skill.TAI_TAO_NANG_LUONG, 7, 100000},
                {Skill.LIEN_HOAN, 7, 1000}}, //skill
            new String[]{}, //text chat 1
            new String[]{"|-2|Nguy rồi... thực sự nguy to rồi!",
                "|-1|Các ngươi nghĩ có thể chạy được sao!?",
                "|-1|Muốn chạy khỏi ta thì đừng hòng!!",
                "|-1|Ta cũng ngạc nhiên với tốc độ của mình! Đó tất nhiên là do ta hấp thụ được số 17!",
                "|-2|Hắn nhanh quá!!",
                "|-1|Ta muốn thử xem sức mạnh này đến đâu...",
                "|-1|Hmm.. có vẻ như sức mạnh này đã tăng lên gấp bội!",
                "|-1|Đã đến lúc ta đạt đến trạng thái hoàn hảo.!",
                "|-1|Có vẻ như ngươi muốn bị ép hơn là tự nguyện!!",
                "|-2|Bây giờ ta chưa thể thắng được ngươi!! Nhưng ngươi đừng hòng huyênh hoang!!!",
                "|-1|Muốn chạy à!!? Ta sẽ không để ngươi thoát đâu!!",}, //text chat 2
            new String[]{"|-1|Đến lúc rồi!"}, //text chat 3
            TypeAppear.ANOTHER_LEVEL
    );

    public static final BossData XEN_BO_HUNG_3 = new BossData(
            "Xên hoàn thiện",
            ConstPlayer.XAYDA,
            new short[]{234, 235, 236, -1, -1, -1},
            350000,
            new long[]{550000000L},
            new int[]{100},
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 60000},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                //                    {Skill.TAI_TAO_NANG_LUONG, 5, 100000},
                {Skill.LIEN_HOAN, 7, 1000},
            },
            //skill

            new String[]{"|-2|Cuối cùng hắn cũng đã biến đổi",
                "|-2|Khốn kiếp! Phải kết liễu hắn ngay lúc này!"
            }, //text chat 1
            new String[]{"|-2|Cell đã đạt đến dạng hoàn hảo rồi!",
                "|-2|Đồ khốn, sao ngươi dám làm vậy với số 18!!",
                "|-2|Không ấn tượng lắm với dạng hoàn hảo của ngươi..",
                "|-2|Sao hắn không hề hấn gì?",
                "|-1|Xin lỗi.. Ngươi có thể giúp ta làm nóng cơ thể lên không !?",
                "|-2|Tình hình nguy cấp rồi!",
                "|-2|Khốn kiếp! Ngươi không chú tâm vào trận đấu!",
                "|-1|Thì ta đã bảo đây chỉ là làm nóng cơ thể mà!!",
                "|-1|Giờ ngươi chỉ là rác rưởi mà thôi!",
                "|-2|Không thể nào! Ngươi dù sao cũng chỉ là đồ sâu bọ!",}, //text chat 2
            new String[]{"|-1|Oái.. không...",
                "|-1|Cơ thể hoàn hảo của ta!!",
                "|-1|Ta không tin chuyện này sẽ xảy ra!!",
                "|-1|Đồ khốn kiếp!! Rồi ngươi sẽ phải trả giá"
            }, //text chat 3
            TypeAppear.ANOTHER_LEVEL
    );//**************************************************************************
    //*************
    public static final BossData SIEU_BO_HUNG_1 = new BossData(
            "Xên Hoàn Thiện",
            ConstPlayer.XAYDA,
            new short[]{234, 235, 236, -1, -1, -1},
            800000,
            new long[]{600000000L},
            new int[]{103},
            new int[][]{
                //                    {Skill.TU_SAT, 7, 35000},
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 50000},
//                {Skill.TAI_TAO_NANG_LUONG, 7, 90000},
                {Skill.LIEN_HOAN, 7, 1000},
            },
            //skill
            new String[]{"|-1|Cuối cùng thì ngươi cũng đến Goku,... ta đang đợi ngươi đây...",
                "|-1|Chào mừng đến với giải đấu!!!",
                "|-2|Vậy thì... Chúng ta bắt đầu cuộc chiến thôi chứ! Tớ lên trước nhé!",
                "|-1|Đến giờ rồi..",
                "|-1|Ai muốn đấu trước cũng được,.. kẻ nào muốn đầu trước thì bước lên đi!",
                "|-1|Hãy bắt đầu trò chơi của cell thôi!!"
            }, //text chat 1
            new String[]{"|-1|Đến đây",
                "|-1|Làm tốt lắm! Thế mới là chiến đấu chứ",
                "|-1|Chẳng hay ho gì nếu không dồn hết sức vào trận đấu!",
                "|-1|Ngươi được lắm, thực sự rất khá đấy...",
                "|-1|Cảnh báo với ngươi là ta sẽ không thất bại như lúc nãy nữa đâu!!",
                "|-1|Giờ mà ngươi phí sức vào những đòn tấn công vô ích thì trận đấu sẽ vô vị lắm đấy!!",
                "|-1|Ta muốn ngươi giải trí cho ta thêm chút nữa!!",
                "|-1|Nếu không còn ai tham dự trò chơi của Cell,.. thì toàn bộ cư dân trái đất sẽ bị tiêu diệt!"
            }, //text chat 2
            new String[]{"|-1|Hô hô hô, đây sẽ là kết thúc của lũ ngu ngốc các ngươi!! Ta sẽ chết nhưng sẽ kéo theo cái hành tinh này luôn"}, //text chat 3
            REST_30_M
    );

    public static final BossData SIEU_BO_HUNG_2 = new BossData(
            "Xên Hoàn Thiện",
            ConstPlayer.XAYDA,
            new short[]{234, 235, 236, -1, -1, -1},
            100000,
            new long[]{600000000L},
            new int[]{103},
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 50000},
//                {Skill.TAI_TAO_NANG_LUONG, 7, 100000},
                //                    {Skill.TU_SAT, 7, 40000},
                
                {Skill.LIEN_HOAN, 7, 1000}},//skill
            new String[]{"|-1|Cuối cùng thì ngươi cũng đến Goku,... ta đang đợi ngươi đây...",
                "|-1|Chào mừng đến với giải đấu!!!",
                "|-2|Vậy thì... Chúng ta bắt đầu cuộc chiến thôi chứ! Tớ lên trước nhé!",
                "|-1|Đến giờ rồi..",
                "|-1|Ai muốn đấu trước cũng được,.. kẻ nào muốn đầu trước thì bước lên đi!",
                "|-1|Hãy bắt đầu trò chơi của cell thôi!!"}, //text chat 1
            new String[]{"|-1|Đến đây",
                "|-1|Làm tốt lắm! Thế mới là chiến đấu chứ",
                "|-1|Chẳng hay ho gì nếu không dồn hết sức vào trận đấu!",
                "|-1|Ngươi được lắm, thực sự rất khá đấy...",
                "|-1|Cảnh báo với ngươi là ta sẽ không thất bại như lúc nãy nữa đâu!!",
                "|-1|Giờ mà ngươi phí sức vào những đòn tấn công vô ích thì trận đấu sẽ vô vị lắm đấy!!",
                "|-1|Ta muốn ngươi giải trí cho ta thêm chút nữa!!",
                "|-1|Nếu không còn ai tham dự trò chơi của Cell,.. thì toàn bộ cư dân trái đất sẽ bị tiêu diệt!"}, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.ANOTHER_LEVEL
    );

    public static final BossData SIEU_BO_HUNG_3 = new BossData(
            "Siêu Xên Hoàn Thiện",
            ConstPlayer.XAYDA,
            new short[]{234, 235, 236, -1, -1, -1},
            80000,
            new long[]{700000000L},
            new int[]{103},
            new int[][]{
                {Skill.DEMON, 3, 1}, {Skill.DEMON, 6, 2}, {Skill.DRAGON, 7, 3}, {Skill.DRAGON, 1, 4}, {Skill.GALICK, 5, 5},
                {Skill.KAMEJOKO, 7, 6}, {Skill.KAMEJOKO, 6, 7}, {Skill.KAMEJOKO, 5, 8}, {Skill.KAMEJOKO, 4, 9}, {Skill.KAMEJOKO, 3, 10}, {Skill.KAMEJOKO, 2, 11}, {Skill.KAMEJOKO, 1, 12},
                {Skill.ANTOMIC, 1, 13}, {Skill.ANTOMIC, 2, 14}, {Skill.ANTOMIC, 3, 15}, {Skill.ANTOMIC, 4, 16}, {Skill.ANTOMIC, 5, 17}, {Skill.ANTOMIC, 6, 19}, {Skill.ANTOMIC, 7, 20},
                {Skill.MASENKO, 1, 21}, {Skill.MASENKO, 5, 22}, {Skill.MASENKO, 6, 23},
                {Skill.KAMEJOKO, 7, 1000},
                
                {Skill.KHIEN_NANG_LUONG, 7, 130000},
//                {Skill.TAI_TAO_NANG_LUONG, 7, 90000},
                },
            //skill
            new String[]{"|-2|Tại sao!? Sao ngươi vẫn còn sống vậy!?",
                "|-1|... Xem ra bọn bây rất ngạc nhiên",
                "|-1|Được rồi, ta sẽ nói cho các ngươi biết đã xảy ra chuyện gì...",
                "|-1|Ta cũng ngạc nhiên không kém đâu, hê hê hê...",
                "|-1|Bên trong đầu ta có một khối nhỏ, đó là nơi chữa trị cho cơ thể ta khi ta bị thương tổn.",
                "|-1|Nếu khối đó không bị hủy diệt thì ta vẫn còn có khả năng hồi phục lại toàn bộ cơ thể này.",
                "|-1|Khi ta tự nổ tung, thật may mắn khi khối thịt đó không bị tổn thương gì...",
                "|-1|Nói thật là việc này nằm ngoài dự tính của ta. Ta quả là gặp may mắn...",
                "|-1|Ta không định chơi đùa thêm nữa đâu... nên kết thúc mọi chuyện ngay bây giờ thôi!!",}, //text chat 1
            new String[]{"|-1|Đến đây",
                "|-1|Ta sẽ không chừa đứa nào trong bọn bây",
                "|-1|Hãy tan thành cát bụi cùng với hành tinh này!!",
                "|-1|Ha ha ha! Các ngươi thích thế này chứ hả?!",
                "|-2|Ta hận sức mạnh của chúng ta không đủ đánh bại hắn!!",
                "|-2|Thật khốn kiếp!!!",
                "|-2|Tấn công đi!! Ta biết mình không còn đủ sức nữa...",
                "|-2|Nhưng ta vẫn muốn tiêu diệt ngươi!!!",}, //text chat 2
            new String[]{"|-1|Aahhh...",
                "|-1|Không thể nào... Điều này không thể nào xảy ra...",
                "|-1|Ta không tin...",
                "|-1|Ta là bấ...",}, //text chat 3
            //            TypeAppear.ANOTHER_LEVEL
            REST_15_M
    );
    //**************************************************************************Boss doanh trại
    public static final BossData TRUNG_UY_TRANG = new BossData(
            "Trung úy trắng", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{141, 142, 143, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            1200, //dame
            new long[]{150000}, //hp
            new int[]{62}, //map join
            (int[][]) Util.addArray(FULL_DEMON), //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Nhóc con"}, //text chat 2
            new String[]{}, //text chat 3
            REST_10_S
    );
    //**************************************************************************
    public static final BossData COOLER_GOLD = new BossData(
            "Cooler Vàng", //name
            ConstPlayer.XAYDA, //gender
            new short[]{709, 710, 711, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            120000, //dame
            new long[]{2000000000L}, //hp
            new int[]{155}, //map join
            new int[][]{
                
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 50000},
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 4, 1000},
//                {Skill.TAI_TAO_NANG_LUONG, 7, 100000},
                {Skill.MASENKO, 7, 10000}}, //skill
            new String[]{"|-1|Hello cục cưng",
                "|-1|Mày có biết tao là ai không?",
                "|-2|Tao không cần biết mày là ai, mày nghĩ mày dọa được tao à?",
                "|-1|Thôi không nói nhiều nữa,giờ tao cho mày biết tao là ai."
            }, //text chat 1
            new String[]{"|-1|Tao hơn hẳn mày, mày nên cầu cho may mắn ở phía mày đi",
                "|-1|Ghê chưa ghê chưa!",
                "|-1|Tao có rất nhiều vật phẩm quý giá,nhưng với mày thì có cái..nịt",
                "|-1|Đánh tao à,lo mà luyện tập thêm đi",
                "|-1|Nói cho mày biết,tao là anh trai của Fide",
                "|-1|trạng thái Goldend Meta Cooler sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Đêm qua em đẹp lắm!"}, //text chat 3
            REST_15_M //second rest
    );

    public static final BossData CUMBER = new BossData(
            "Sayan Tà Ác Cumber", //name
            ConstPlayer.XAYDA, //gender
            new short[]{102, 474, 475, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            150000, //dame
            new long[]{2000000000L}, //hp
            new int[]{155}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.ANTOMIC, 7, 1000},
//                {Skill.TAI_TAO_NANG_LUONG, 7, 90000}
//                    {Skill.BIEN_KHI, 5, 100000}
            }, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên Sayan kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_15_M //second rest
    );
    //**************************************************************************
    public static final BossData XEN_CON = new BossData(
            "Xên con", //name
            ConstPlayer.XAYDA, //gender
            new short[]{264, 265, 266, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            15000, //dame
            new long[]{200000000L}, //hp
            new int[]{103}, //map join
            (int[][]) Util.addArray(FULL_DEMON, FULL_MASENKO), //skill
            new String[]{"|-1|Hello cục cưng",
                "|-1|Mày có biết tao là ai không?",
                "|-2|Tao không cần biết mày là ai, mày nghĩ mày dọa được tao à?",
                "|-1|Thôi không nói nhiều nữa,giờ tao cho mày biết tao là ai."
            }, //text chat 1
            new String[]{"|-1|Tao hơn hẳn mày, mày nên cầu cho may mắn ở phía mày đi",
                "|-1|Ghê chưa ghê chưa!",
                "|-1|Tao có rất nhiều vật phẩm quý giá,nhưng với mày thì có cái..nịt",
                "|-1|Đánh tao à,lo mà luyện tập thêm đi",
                "|-1|Nói cho mày biết,tao là con của Xên ",
                "|-1|Tao sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Đêm qua em đẹp lắm!"}, //text chat 3
            REST_15_M //second rest
    );
    //**************************************************************************

    //**************************************************************************cha con fide
    public static final BossData VUA_COLD = new BossData(
            "Thống Chế King COLD",
            ConstPlayer.XAYDA,
            new short[]{712, 713, 714, -1, -1, -1},
            15000,
            new long[]{10000000000L},
            new int[]{131, 132, 133},
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_LIENHOAN), //skill
            new String[]{"|-2|Hắn ta là ai vậy?"}, //text chat 1
            new String[]{"|-1|Thì ra đây là trái đất",
                "|-1|Hành tinh này bán đi chắc cũng kha khá đó!",
                "|-2|Ngươi làm ta khó chịu rồi đấy",
                "|-1|Ngươi sẽ không bao giờ thắng được đâu!!",
                "|-2|Tên này mạnh quá",}, //text chat 2
            new String[]{"|-1|Xin hãy tha cho ta !",
                "|-1|Ta sẽ cho ngươi nửa số hành tinh ta đang giữ!",
                "|-1|Đừng màaa!"}, //text chat 3
            REST_15_M, //second rest
            new int[]{BossID.FIDE_ROBOT}
    );

    public static final BossData FIDE_ROBOT = new BossData(
            "Fide người máy", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{715, 716, 717, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            12200, //dame
            new long[]{1200000000L}, //hp
            new int[]{131, 132, 133}, //map join
            (int[][]) Util.addArray(FULL_GALICK, FULL_ANTOMIC), //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Haaaahaa",
                "|-1|Chúng ta sẽ hủy diệt hành tinh này",
                "|-1|Tên Sôn gô ku mãi vẫn chưa tới",
                "|-1|Ngươi sẽ không bao giờ thắng được đâu!!",
                "|-2|Để xem ai mới là người chiến thắng!!",}, //text chat 2
            new String[]{"|-1|Ta thua rồi sao? Khôngggggggg!"}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );

    //**************************************************************************
    //**************************************************************************
    public static final BossData XUKA = new BossData(
            "Cô Bé Shizuka", //name
            ConstPlayer.XAYDA, //gender
            new short[]{802, 803, 804, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            21100, //dame
            new long[]{60000000L}, //hp
            new int[]{13}, //map join
            new int[][]{
                {Skill.MASENKO, 7, 1000},
                {Skill.DE_TRUNG, 7, 10000},},//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Khoa có nhầm không nhỉ",
                "|-1|Các ngươi không nhúc nhích được sao?",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData XEKO = new BossData(
            "Mõm Nhọn Suneo", //name
            ConstPlayer.XAYDA, //gender
            new short[]{850, 851, 852, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            31100, //dame
            new long[]{70000000}, //hp
            new int[]{13}, //map join
            new int[][]{
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 10000},
                {Skill.KAMEJOKO, 7, 1000},},//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData CHAIEN = new BossData(
            "Khỉ Đột Chaien", //name
            ConstPlayer.XAYDA, //gender
            new short[]{847, 848, 849, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            41110, //dame
            new long[]{80000000L}, //hp
            new int[]{13}, //map join
            new int[][]{
                {Skill.GALICK, 7, 10000},
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.BIEN_KHI, 1, 600000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData NOBITA = new BossData(
            "Chú Bé Đần Nobita", //name
            ConstPlayer.XAYDA, //gender
            new short[]{844, 845, 846, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            41100, //dame
            new long[]{90000000L}, //hp
            new int[]{13}, //map join
            new int[][]{
                {Skill.MASENKO, 7, 1000},
                {Skill.ANTOMIC, 7, 10000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Doraemon có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData DORAEMON = new BossData(
            "Người Máy Doraemon",
            ConstPlayer.XAYDA,
            new short[]{790, 791, 792, -1, -1, -1},
            50000,
            new long[]{100000000L},
            new int[]{13},
            //            new int[]{14},
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, //skill//skill
            new String[]{"|-2|Á đù, Doraemon !!!!!"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            REST_15_M, //second rest
            new int[]{BossID.CHAIEN, BossID.XEKO, BossID.NOBITA, BossID.XUKA} //boss join map together
    );
    
    //**************************************************************************
    //**************************************************************************
        public static final BossData BOSS_CAPTAIN = new BossData(
            "Anh Hùng Captain",
            ConstPlayer.XAYDA,
            new short[]{1270, 1271, 1272, -1, -1, -1},
            100000,
            new long[]{2_000_000_000L},
            new int[]{174},
            //            new int[]{14},
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                {Skill.THAI_DUONG_HA_SAN, 7, 120000},
                
            }, //skill//skill
            new String[]{"|-2|Ta là Siêu Anh Hùng Captain America !!!!!"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            REST_10_M, //second rest
            new int[]{BossID.BOSS_SPIDER, BossID.BOSS_GoHanSN, BossID.BOSS_ChiChiSN, BossID.BOSS_STRANGE, BossID.BOSS_PanSN} //boss join map together
    );
        
        public static final BossData BOSS_SPIDER = new BossData(
            "Anh Hùng Spider Man",
            ConstPlayer.XAYDA,
            new short[]{1273, 1274, 1275, -1, -1, -1},
            75000,
            new long[]{2_000_000_000L},
            new int[]{174},
            //            new int[]{14},
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                
                {Skill.KHIEN_NANG_LUONG, 7, 120000},
            }, //skill//skill
            new String[]{"|-2|Ta là Siêu Anh Hùng Spider Man !!!!!"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER 
    );
            public static final BossData BOSS_STRANGE = new BossData(
            "Anh Hùng Strange",
            ConstPlayer.XAYDA,
            new short[]{1282, 1283, 1284, -1, -1, -1},
            75000,
            new long[]{2_000_000_000L},
            new int[]{174},
            //            new int[]{14},
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                {Skill.THAI_DUONG_HA_SAN, 3, 900000},
                {Skill.KHIEN_NANG_LUONG, 7, 120000},
            }, //skill//skill
            new String[]{"|-2|Ta là Siêu Anh Hùng Strange !!!!!"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER 
    );
        
            public static final BossData BOSS_GoHanSN = new BossData(
            "Siêu Nhân GoHan",
            ConstPlayer.XAYDA,
            new short[]{1053, 1054, 1055, -1, -1, -1},
            50000,
            new long[]{1_000_000_000L},
            new int[]{174},
            //            new int[]{14},
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                {Skill.KHIEN_NANG_LUONG, 7, 120000},
            }, //skill//skill
            new String[]{"|-2|Ta là Siêu Nhân GoHan !!!!!"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER 
    );
            
            public static final BossData BOSS_ChiChiSN = new BossData(
            "Siêu Nhân ChiChi",
            ConstPlayer.XAYDA,
            new short[]{1056, 1057, 1058, -1, -1, -1},
            50000,
            new long[]{1_000_000_000L},
            new int[]{174},
            //            new int[]{14},
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                {Skill.KHIEN_NANG_LUONG, 7, 120000},
            
            }, //skill//skill
            new String[]{"|-2|Ta là Siêu Nhân ChiChi !!!!!"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER 
    );
            public static final BossData BOSS_PanSN = new BossData(
            "Siêu Nhân Pan",
            ConstPlayer.XAYDA,
            new short[]{1059, 1060, 1061, -1, -1, -1},
            50000,
            new long[]{1_000_000_000L},
            new int[]{174},
            //            new int[]{14},
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                {Skill.KHIEN_NANG_LUONG, 7, 120000},
            
            }, //skill//skill
            new String[]{"|-2|Ta là Siêu Nhân Pan !!!!!"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER 
    );

    

    //**************************************************************************
    //**************************************************************************
    public static final BossData BLACK_GOKU_BASE = new BossData(
            "Black Goku", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{550, 880, 881, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            80000, //dame
            new long[]{2000000000L}, //hp
            new int[]{0, 1, 2, 3, 4, 5, 6, 27, 28, 29}, //map join
            (int[][]) Util.addArray(FULL_GALICK, FULL_KAMEJOKO, FULL_LIENHOAN), //skill
            new String[]{"|-1|Ta là Sôn Gô Ku",
                "|-1|Cơ thể này,sức mạnh này",
                "|-1|Ta khá thích việc loại bỏ các ngươi",
                "|-1|Mau chấp nhận số phận đi lũ sâu bọ"
            }, //text chat 1
            new String[]{"|-1|Các ngươi chỉ có vậy thôi sao?",
                "|-1|Đúng là loài người thấp kém",
                "|-2|Ngươi nói như thể ngươi không phải con người vậy?",
                "|-2|Chiếc nhẫn kia lẽ nào ngươi là một Kaioshin?!",
                "|-1|Các ngươi không nên biết quá nhiều",
                "|-2|Xem đòn đánh của ta đây !",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Biến hình! Super Sayan Rose"}, //text chat 3
            REST_15_M //second rest
    );
    //**************************************************************************
    public static final BossData BLACK_GOKU = new BossData(
            "Black Goku", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{550, 880, 881, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            80000, //dame
            new long[]{2000000000L}, //hp
            new int[]{92, 93, 94, 96, 97, 98, 99, 100}, //map join
            (int[][]) Util.addArray(FULL_GALICK, FULL_KAMEJOKO), //skill
            new String[]{"|-1|Ta là Sôn Gô Ku",
                "|-1|Cơ thể này,sức mạnh này",
                "|-1|Ta khá thích việc loại bỏ các ngươi",
                "|-1|Mau chấp nhận số phận đi lũ sâu bọ"
            }, //text chat 1
            new String[]{"|-1|Các ngươi chỉ có vậy thôi sao?",
                "|-1|Đúng là loài người thấp kém",
                "|-2|Ngươi nói như thể ngươi không phải con người vậy?",
                "|-2|Chiếc nhẫn kia lẽ nào ngươi là một Kaioshin?!",
                "|-1|Các ngươi không nên biết quá nhiều",
                "|-2|Xem đòn đánh của ta đây !",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Biến hình! Super Sayan Rose"}, //text chat 3
            REST_15_M //second rest
    );

    public static final BossData SUPER_BLACK_GOKU = new BossData(
            "Super Black Goku Rose VIP", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{553, 880, 881, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            158000, //dame
            new long[]{2000000000L}, //hp
            new int[]{108, 106, 107}, //map join
            new int[][]{
                
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 10000},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.LIEN_HOAN, 7, 500}},
            //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Ta chính là người mang thân thể của Songoku",
                "|-1|Sức mạnh của ta là không có giới hạn",
                "|-1|Ta sẽ thống trị vũ trụ",
                "|-1|Để ta nói cho nghe,người Sayan sau khi hồi phục sức mạnh sẽ tăng lên rất nhiều",
                "|-2|Tại sao ngươi lại lấy thân thể của songoku chứ?"
            }, //text chat 2

            new String[]{"|-1|Chúng ta sẽ gặp lại nhau sớm thôi",
                "|-2|Ngươi nói gì chứ?"}, //text chat 3
            REST_30_M //type appear
    );

    //-------------------------------------------------------------------
    public static final BossData SUPER_BLACK_GOKU_2 = new BossData(
            "Super Black Goku Rose", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{553, 880, 881, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            190000, //dame
            new long[]{2000000000L}, //hp
            new int[]{108, 106, 107, 109, 110}, //map join
            //            new int[]{14}, //map join
            new int[][]{
                
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 20000},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.LIEN_HOAN, 7, 500}},
            //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Ta chính là người mang thân thể của Songoku",
                "|-1|Sức mạnh của ta là không có giới hạn",
                "|-1|Ta sẽ thống trị vũ trụ",
                "|-1|Để ta nói cho nghe,người Sayan sau khi hồi phục sức mạnh sẽ tăng lên rất nhiều",
                "|-2|Tại sao ngươi lại lấy thân thể của songoku chứ?"
            }, //text chat 2

            new String[]{"|-1|Hẹn gặp lại",
                "|-2|Không tiễn"}, //text chat 3
            REST_15_M, //second rest
            new int[]{BossID.ZAMASZIN}
    );

    public static final BossData ZAMAS = new BossData(
            "Kaioshin Zamas", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{433, 904, 905, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            45000, //dame
            new long[]{2000000000L}, //hp
            new int[]{92, 93, 94, 96, 97, 98, 99, 100}, //map join
            //            new int[]{14}, //map join

            new int[][]{
                {Skill.GALICK, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 10000},
//                {Skill.TAI_TAO_NANG_LUONG, 4, 80000},
                {Skill.KAMEJOKO, 7, 30000}}, //skill
            new String[]{"|-1|Kia là một con người sao?",
                "|-3|Ủa tên kia là ai vậy?",
                "|-2|Lẽ nào đúng như chúng ta đã nghĩ",
                "|-1|Lũ con người không đủ tư cách để nói chuyện với ta",
                "|-2|Zamas! Tại sao chứ !",
                "|-1|Ta sẽ cho người biết sức mạnh của một vị thần là như thế nào !"
            }, //text chat 1
            new String[]{"|-1|Ta là kaioshin của vũ trụ thứ 10 ",
                "|-1|Tên của ta là Zamas, ta sẽ thay đổi thế giới này",
                "|-1|Lũ con người các ngươi là những thứ ta cần loại bỏ đầu tiên",
                "|-2|Tại sao các ngươi lại nhắm tới con người bọn ta chứ?",
                "|-1|Bởi vì ta muốn thực hiện kế hoạch đưa con người về số 0 !",
                "|-1|Lần này ta không nương tay đâu!",
                "|-2|Ngươi thực sự rất mạnh. Nhưng chưa đủ thực lực đâu!!",
                "|-1|Cái gì!? Đó là điều ngu ngốc nhất ta từng nghe! Mau biến đi",
                "|-1|Hắn thực sự rất mạnh, đúng là cuộc chiến hay",
                "|-3|Không lí nào ta lại run sợ bọn con người sao"
            }, //text chat 2

            new String[]{"|-1|Chỉ còn một cách duy nhất mà thôi",
                "|-1|Bông tai Porata!"}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );
    //-------------------------------------------------------------------
    public static final BossData THANZM2 = new BossData(
            "Thần Zamas Tối Thượng", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{903, 904, 905, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000L}, //hp
            new int[]{92, 93, 94, 96, 97, 98, 99, 100}, //map join
            new int[][]{
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 15000},
                {Skill.KAMEJOKO, 5, 3000},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.QUA_CAU_KENH_KHI, 7, 120000}},
            //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Ta chính là thế giới",
                "|-1|Ta chính là công lí",
                "|-1|Hãy chiêm ngưỡng vẻ đẹp của ta !Hỡi con người",
                "|-1|Sức mạnh to lớn nằm trong cơ thể bất tử",
                "|-1|Ta sẽ đem công lí tới toàn bộ vũ trụ này", "|-2|Ngươi cứ lải nhải hoài 2 chữ công lí vậy?", "|-1|Lũ các ngươi làm ta thấy đau rồi ấy haha"
            }, //text chat 2
            new String[]{}, //text chat 3
            REST_15_M
    );

    //**************************************************************************
    public static final BossData MABU = new BossData(
            "Mabư",
            ConstPlayer.XAYDA,
            new short[]{297, 298, 299, -1, -1, -1},
            20000,//dame
            new long[]{100000000L}, //hp
            new int[]{52},//map join
            new int[][]{
                {Skill.KAMEJOKO, 3, 5000},
                {Skill.LIEN_HOAN, 7, 100},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.SOCOLA, 7, 10000}},
            //skill
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_10_M
    );
    //*******************
    //**************************************************************************
    public static final BossData BROLY_1 = new BossData(
            "Broly Base", //name
            ConstPlayer.XAYDA, //gender
            new short[]{1080, 1084, 1085, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            30000, //dame
            new long[]{2000000000L}, //hp
            new int[]{144}, //map join
            new int[][]{
                {Skill.KHIEN_NANG_LUONG, 7, 60000},
                {Skill.ANTOMIC, 7, 500}}, //skill
            new String[]{
                "|-1|Tuy không biết các ngươi là ai, nhưng ta rất ấn tượng đấy!",
                "|-2|Tới đây đi!"
            }, //text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                "|-1|Gaaaaaa",
                "|-2|Không..thể..nào!!",
                "|-2|Không ngờ..Hắn mạnh cỡ này sao..!!"
            }, //text chat 2
            new String[]{"|-1|Gaaaaaaaa!!!"}, //text chat 3
            REST_15_M //second rest
    );

    public static final BossData BROLY_2 = new BossData(
            "Super Broly", //name
            ConstPlayer.XAYDA, //gender
            new short[]{1083, 1084, 1085, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            24000, //dame
            new long[]{2000000000L}, //hp
            new int[]{5}, //map join
            new int[][]{
                {Skill.TAI_TAO_NANG_LUONG, 5, 15000},
                {Skill.ANTOMIC, 3, 1000}}, //skill
            new String[]{
                "|-1|Gaaaaaa",
                "|-2|Tới đây đi!"
            }, //text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                "|-1|Gaaaaaa",
                "|-2|Không..thể..nào!!",
                "|-2|Không ngờ..Hắn mạnh cỡ này sao..!!"
            }, //text chat 2
            new String[]{"|-1|Gaaaaaaaa!!!"}, //text chat 3
            TypeAppear.ANOTHER_LEVEL //type appear
    );

    public static final BossData BROLY_3 = new BossData(
            "Super Sayan Huyền Thoại", //name
            ConstPlayer.XAYDA, //gender
            new short[]{294, 295, 1085, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            40200, //dame
            new long[]{2000000000L}, //hp
            new int[]{5}, //map join
            new int[][]{
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 25000},
                {Skill.KAMEJOKO, 7, 1000}}, //skill
            new String[]{
                "|-1|Tuy không biết các ngươi là ai, nhưng ta rất ấn tượng đấy!",
                "|-2|Ta cũng cảm thấy phấn khích lắm!"
            }, //text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                "|-1|Gaaaaaa",
                "|-2|Không..thể..nào!!",
                "|-2|Tên này điên thật rồi!!",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Khôngggggggg!!"}, //text chat 3
            TypeAppear.ANOTHER_LEVEL //type appear
    );
    public static final BossData BROLY_4 = new BossData(
            "Super Sayan GOD", //name
            ConstPlayer.XAYDA, //gender
            new short[]{390, 295, 296, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            152000, //dame
            new long[]{2000000000L}, //hp
            new int[]{5}, //map join
            new int[][]{
                {Skill.SOCOLA, 7, 6000},
                {Skill.DE_TRUNG, 7, 6000},

                {Skill.DICH_CHUYEN_TUC_THOI, 7, 16000},
                {Skill.LIEN_HOAN, 7, 100},
                {Skill.KAMEJOKO, 7, 1000}}, //skill
            new String[]{
                "|-1|Tuy không biết các ngươi là ai, nhưng ta rất ấn tượng đấy!",
                "|-2|Ta cũng cảm thấy phấn khích lắm!"
            }, //text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                "|-1|Gaaaaaa",
                "|-2|Không..thể..nào!!",
                "|-2|Tên này điên thật rồi!!",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Khôngggggggg!!"}, //text chat 3
            TypeAppear.ANOTHER_LEVEL //type appear
    );

    //************************************************************************** Boss hủy diệt
    public static final BossData THIEN_SU_WHIS = new BossData(
            "Thiên sứ Whis", //name
            ConstPlayer.NAMEC, //gender
            new short[]{838, 839, 840, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000L}, //hp
            new int[]{146}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 10000},
                {Skill.KHIEN_NANG_LUONG, 7, 120000},
                },//skill
            new String[]{"|-2|Kia là ai thế",
                "|-1|Xin chào! Chúng tôi tới đây để tìm người!",
                "|-1|Ngài Berrus! Có vẻ hắn ta không có ở đây",
                "|-1|Ta có thể hỏi các ngươi",
                "|-2|Chuyện gì vậy?",
                "|-1|Các ngươi có biết tên Super Sayan God ở đâu không?",
                "|-2|Super Sayan God? đó là gì vậy?",
                "|-1|Có vẻ giấc mơ của ngài Berrus là bịp rồi! Hô Hô",
                "|0|Nếu các ngươi không biết hắn ở đâu",
                "|0|Chắc ta phải phá hủy hành tinh này vậy"
            }, //text chat 1
            new String[]{"|-1|Ta có thể ngồi ăn một chút được rồi!",
                "|-1|Các ngươi vẫn yếu vẫn như mọi khi",
                "|0|Thật là bực mình!",
                "|-2|Đây là sức mạnh của một thiên sứ sao?",
                "|-1|Hô Hô",
                "|-1|Các ngươi không đánh bại được ta đâu!",
                "|-2|Không thể nào",
                "|-2|Tại sao lại vậy chứ !",}, //text chat 2
            new String[]{"|-1|Ta đi về đây!Cảm ơn vì món ăn"}, //text chat 3
            REST_5_M //second rest
//            new int[]{BossID.THAN_HUY_DIET}
    );
    public static final BossData THAN_HUY_DIET = new BossData(
            "Thần Hủy Diệt Berrus", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{508, 509, 510, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000L}, //hp
            new int[]{146}, //map join
            new int[][]{
                {Skill.MASENKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 10000},
            {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Ta sẽ phá hủy hành tinh này",
                "|-1|Chán quá!",
                "|-1|Ta vẫn chưa dùng hết sức đâu!",
                "|-2|Hắn ta không cần phòng thủ luôn!",}, //text chat 2
            new String[]{"|-1|Ta buồn ngủ quá!"}, //text chat 3
            REST_5_M
//            TypeAppear.APPEAR_WITH_ANOTHER
    );
    public static final BossData THIEN_SU_VADOS = new BossData(
            "Thiên sứ Vados", //name
            ConstPlayer.NAMEC, //gender
            new short[]{530, 531, 532, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000L}, //hp
            new int[]{146}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 10000},
                {Skill.KHIEN_NANG_LUONG, 7, 120000},
                },//skill
            new String[]{"|-2|Kia là ai thế",
                "|-1|Ta là Vados",
                "|-1|Ta tới đây để kiếm một thứ",
                "|-1|Đó chính là Ngọc Rồng Siêu Cấp",
                "|0|Bọn này ta cho 1 búng là bay !"
            }, //text chat 1
            new String[]{"|-1|Ồ",
                "|-1|Ta làm vậy có hơi quá không?",
                "|0|Thật là bực mình!",
                "|-2|Sao ông ta lại mạnh tới vậy ?",
                "|-1|Hô Hô",
                "|-1|Các ngươi muốn đánh bại một Thiên Sứ sao?",
                "|-2|Khốn khiếp!",
                "|-2|Tại sao lại vậy chứ !",}, //text chat 2
            new String[]{"|-1|Hẹn gặp lại,ta rất hài lòng về cuộc chiến"}, //text chat 3
            REST_5_M //second rest
//            new int[]{BossID.THAN_HUY_DIET_CHAMPA}
    );
    public static final BossData THAN_HUY_DIET_CHAMPA = new BossData(
            "Thần Hủy Diệt Champa", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{511, 512, 513, -1, -1, 77}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000L}, //hp
            new int[]{146}, //map join
            new int[][]{
                {Skill.MASENKO, 3, 5000},
                {Skill.KHIEN_NANG_LUONG, 5, 100000},
                {Skill.LIEN_HOAN, 7, 10000},
            {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},},//skill//skill
            new String[]{"|-1|Các ngươi có biết",
                "|-1|Ngọc Rồng Siêu Cấp đang ở đâu hay không?",
                "|-2|Ai mà biết được",}, //text chat 1
            new String[]{"|-1|Một lũ yếu ớt",
                "|-1|Ta sẽ phá hủy hành tinh này",
                "|-1|Chán quá!",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-2|Hắn ta không cần phòng thủ luôn!",}, //text chat 2
            new String[]{"|-1|Chết tiệt!"}, //text chat 3
            REST_5_M
//            TypeAppear.APPEAR_WITH_ANOTHER
    );
    //**************************************************************************
    //************************************************************************** Boss goku
    public static final BossData SONGOKU_TA_AC = new BossData(
            "Siêu Goku Tà Ác", //name
            ConstPlayer.XAYDA, //gender
            new short[]{543, 57, 999, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            60000, //dame
            new long[]{2000000000L}, //hp
            new int[]{155}, //map join
            new int[][]{
                
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 50000},
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 1000}},
            new String[]{"|-1|Ta bị sao thế này !",
                "|-2|Chú Goku",
                "|-2|Đó không còn là chú Goku nữa rồi",
                "|-1|GAAAAAAAAAAAAAA!."
            }, //text chat 1
            new String[]{"|-2|Tỉnh lại đi chú Goku",
                "|-2|Đừng để bị hắn chi phối!",
                "|-1|Định chạy trốn hả?",
                "|-1|Ta sẽ tàn sát khu này trong vòng 5 phút nữa",
                "|-2|Không được rồi!",
                "|-2|Phải cố hết sức thôi"
            }, //text chat 2
            new String[]{"|-2|Mau nghỉ ngơi nào chú Goku"}, //text chat 3
            REST_15_M //second rest
    );

    //************************************************************************** Boss nrd
    public static final BossData Rong_1Sao = new BossData(
            "Rồng Syn 1 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{204, 205, 206, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000}, //hp
            new int[]{85}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 10000},
                {Skill.KAMEJOKO, 5, 10000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    public static final BossData Rong_2Sao = new BossData(
            "Rồng Haze 2 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{219, 220, 221, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000}, //hp
            new int[]{86}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.KAMEJOKO, 5, 1000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest

    );
    public static final BossData Rong_3Sao = new BossData(
            "Rồng Eis 3 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{207, 208, 209, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            120000, //dame
            new long[]{2000000000}, //hp
            new int[]{87}, //map join
            new int[][]{
                {Skill.THOI_MIEN, 4, 125000},
                
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 10000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest

    );
    public static final BossData Rong_4Sao = new BossData(
            "Rồng Nuova 4 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{210, 211, 212, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000}, //hp
            new int[]{88}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 10000},
                
                {Skill.KAMEJOKO, 5, 10000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    public static final BossData Rong_5Sao = new BossData(
            "Rồng Rage 5 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{213, 214, 215, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000}, //hp
            new int[]{89}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 5, 10000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    public static final BossData Rong_6Sao = new BossData(
            "Rồng Oceanus 6 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{222, 223, 224, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            200000, //dame
            new long[]{2000000000}, //hp
            new int[]{90}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 4000},
                {Skill.KAMEJOKO, 5, 10000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    public static final BossData Rong_7Sao = new BossData(
            "Rồng Naturon 7 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{216, 217, 218, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{2000000000}, //hp
            new int[]{91}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 5, 1000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    //**************************************************************************Team Mabu 12h
    public static final BossData MABU_12H = new BossData(
            "Mabư",
            ConstPlayer.XAYDA,
            new short[]{297, 298, 299, -1, -1, -1},
            100000,
            new long[]{2000000000L},
            new int[]{120},
            new int[][]{
//                {Skill.TU_SAT, 7, 100000},
                {Skill.DE_TRUNG, 7, 10000},
                {Skill.SOCOLA, 7, 1000},
                {Skill.DEMON, 7, 1000}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_20_S
    );
    public static final BossData DRABURA = new BossData(
            "Ma Vương Dabura",
            ConstPlayer.XAYDA,
            new short[]{418, 419, 420, -1, -1, -1},
            50000,
            new long[]{200000000L},
            new int[]{114},
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.DEMON, 7, 10000}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_M
    );
    public static final BossData DRABURA_2 = new BossData(
            "Ma Vương Dabura",
            ConstPlayer.XAYDA,
            new short[]{418, 419, 420, -1, -1, -1},
            100000,
            new long[]{100000000L},
            new int[]{115},
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.DEMON, 7, 10000}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_M
    );
    public static final BossData BUI_BUI = new BossData(
            "Bui Bui",
            ConstPlayer.XAYDA,
            new short[]{451, 452, 453, -1, -1, -1},
            20000,
            new long[]{7000000000L},
            new int[]{117},
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.DEMON, 7, 10000}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_M
    );

    public static final BossData BUI_BUI_2 = new BossData(
            "Bui Bui",
            ConstPlayer.XAYDA,
            new short[]{451, 452, 453, -1, -1, -1},
            20000,
            new long[]{8000000000L},
            new int[]{118},
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.DEMON, 7, 10000}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_M
    );
    public static final BossData YACON = new BossData(
            "Yacôn",
            ConstPlayer.XAYDA,
            new short[]{415, 416, 417, -1, -1, -1},
            20000,
            new long[]{8000000000L},
            new int[]{119},
            new int[][]{
                {Skill.DEMON, 7, 100}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_M
    );

    public static final BossData COOLER_1 = new BossData(
            "Cooler", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{508, 509, 510, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            1, //dame
            new long[]{10000000}, //hp
            new int[]{106, 107, 108, 109}, //map join
            new int[][]{
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 10000},
                {Skill.DE_TRUNG, 7, 1000},
                {Skill.LIEN_HOAN, 1, 400},
                {Skill.MASENKO, 2, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Chán quá!",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Ta sẽ cho ngươi cái nịt!",}, //text chat 2
            new String[]{"|-1|Biến hình !!!!!!!!!!!!"}, //text chat 3
            REST_15_M
    );
    public static final BossData COOLER_2 = new BossData(
            "Cooler 2", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{508, 509, 510, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            1, //dame
            new long[]{10000000}, //hp
            new int[]{106, 107, 108, 109}, //map join
            new int[][]{
                {Skill.DE_TRUNG, 7, 1000},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.KAMEJOKO, 1, 400},
                {Skill.LIEN_HOAN, 2, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Ta sẽ phá hủy hành tinh này",
                "|-1|Chán quá!",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Ta nghèo lắm!Đừng săn ta nữa",}, //text chat 2
            new String[]{"|-1|Đen lắm em trai !"}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );
    public static final BossData SUPER_ANDROID_17 = new BossData(
            "Sát Thủ", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{636, 637, 638, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            90000, //dame
            new long[]{10000000000L}, //hp
            new int[]{169}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 10000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Ta sẽ phá hủy hành tinh này",
                "|-1|Chán quá!",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Ta nghèo lắm!Đừng săn ta nữa",}, //text chat 2
            new String[]{"|-1|Đen lắm em trai !"}, //text chat 3
            REST_30_S
    );
    public static final BossData BOSS_VIET = new BossData(
            "Boss Quản Gia", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{573, 574, 575, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            35, //dame
            new long[]{1_000_000L}, //hp
            new int[]{0, 7, 14}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 1, 400},
                {Skill.LIEN_HOAN, 2, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Ta là Boss Sự Kiện, giỏi thì đánh với ta 1 trận",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Ta nghèo lắm!Đừng săn ta nữa",}, //text chat 2
            new String[]{"|-1|Đen lắm em trai !"}, //text chat 3
            REST_1_M
    );
    public static final BossData BOSS_DETU_BERUS = new BossData(
            "Gojo VIP", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{1258, 1259, 1260, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            50000, //dame
            new long[]{190000000L}, //hp
            new int[]{104}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                //                    {Skill.TAI_TAO_NANG_LUONG, 7, 90000},
                
                {Skill.LIEN_HOAN, 7, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Muốn có Đệ Berus đâu có dễ",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Mấy con gà, chơi Đệ Mabu trước đi",}, //text chat 2
            new String[]{"|-1|Đen lắm em trai !"}, //text chat 3
            REST_15_M
    );
    public static final BossData BOSS_GOJO = new BossData(
            "Sát Thủ Bóng Đêm", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{1258, 1259, 1260, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            30000, //dame
            new long[]{10000000000L}, //hp
            new int[]{169}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                //                    {Skill.TAI_TAO_NANG_LUONG, 7, 90000},
                
                {Skill.LIEN_HOAN, 7, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Muốn cướp Ngọc Bội của ta à",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Người đâu! Hộ Tống",}, //text chat 2
            new String[]{"|-1|Nay ta sẽ chôn xác ngươi ở đây!"}, //text chat 3
            REST_30_S
    );
        public static final BossData BOSS_GOHAN_SSJ = new BossData(
            "GOHAN SSJ5", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{1288, 1289, 1290, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            30000, //dame
            new long[]{300000000L}, //hp
            new int[]{143}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                //                    {Skill.TAI_TAO_NANG_LUONG, 7, 90000},
                
                {Skill.LIEN_HOAN, 7, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Muốn có Cải Trang GoHan SSJ đâu có dễ",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Mấy con gà, chơiCải Trang cùi trước đi",}, //text chat 2
            new String[]{"|-1|Đen lắm em trai !"}, //text chat 3
            REST_15_M
    );
    public static final BossData BOSS_ZENO = new BossData(
            "Thần Tối thượng ZENO", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{1216, 1217, 1218, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            400000, //dame
            new long[]{25000000000L}, //hp
            new int[]{144}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 3000},
                //                    {Skill.TAI_TAO_NANG_LUONG, 7, 90000},
                
                {Skill.LIEN_HOAN, 7, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1| Việt đẹp zai muốn hành tụi bây đó hahaha",
                "|-1|Ta là Thần mạnh nhất Vũ trụ này",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Búng chim sám hối đi",}, //text chat 2
            new String[]{"|-1|Mấy thằng nhóc !"}, //text chat 3
            REST_30_M
        );
    public static final BossData BOSS_TRAU = new BossData(
            "Ngưu Ma Vương", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{813, 814, 815, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            35, //dame
            new long[]{300000L}, //hp
            new int[]{5}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 1, 400},
                {Skill.LIEN_HOAN, 2, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Ta là Trâu Đen, giỏi thì đánh với ta 1 trận",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Ta nghèo lắm!Đừng săn ta nữa",}, //text chat 2
            new String[]{"|-1|Đen lắm em trai !"}, //text chat 3
            REST_10_S
    );
    public static final BossData BOSS_VANG = new BossData(
            "Mèo Thần Tài", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{1198, 1199, 1200, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            1, //dame
            new long[]{3000}, //hp
            new int[]{5}, //map join
            new int[][]{
                //                {Skill.KAMEJOKO, 7, 1000},
                //                    {Skill.DICH_CHUYEN_TUC_THOI, 7, 3000},
                //                    {Skill.TAI_TAO_NANG_LUONG, 7, 90000},
                {Skill.THAI_DUONG_HA_SAN, 7, 33000},
                {Skill.LIEN_HOAN, 7, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1| Ta giữ rất nhiều thỏi vàng hahaha",
                "|-1|Đánh ta đi này",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Tưởng thế nào!!",}, //text chat 2
            new String[]{"|-1|Mấy thằng nhóc !"}, //text chat 3
            REST_5_M
    );
    public static final BossData BOSS_DETU_BL = new BossData(
            "SP Broly Huyền Thoại", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{1318, 1319, 1320, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100000, //dame
            new long[]{1000000000L}, //hp
            new int[]{143}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 60000},
                //                    {Skill.TAI_TAO_NANG_LUONG, 7, 90000},
                
                {Skill.LIEN_HOAN, 7, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Muốn có Đệ Berus đâu có dễ",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Mấy con gà, chơi Đệ Mabu trước đi",}, //text chat 2
            new String[]{"|-1|Đen lắm em trai !"}, //text chat 3
            REST_10_M
    );
    public static final BossData SOI_HEC_QUYN = BossData.builder()
            .name("Sói Hẹc Quyn")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(4000)
            .hp(new long[]{10000000})
            .outfit(new short[]{394, 395, 396, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData O_DO = BossData.builder()
            .name("Ở Dơ")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(5000)
            .hp(new long[]{170000000})
            .outfit(new short[]{400, 401, 402, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData XINBATO = BossData.builder()
            .name("Xinbatô")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(6000)
            .hp(new long[]{200000000})
            .outfit(new short[]{359, 360, 361, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData CHA_PA = BossData.builder()
            .name("Cha pa")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(6500)
            .hp(new long[]{30000000L})
            .outfit(new short[]{362, 363, 364, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData PON_PUT = BossData.builder()
            .name("Pon put")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(7000)
            .hp(new long[]{40000000L})
            .outfit(new short[]{365, 366, 367, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData CHAN_XU = BossData.builder()
            .name("Chan xư")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(7500)
            .hp(new long[]{55000000L})
            .outfit(new short[]{371, 372, 373, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData TAU_PAY_PAY = BossData.builder()
            .name("Tàu Pảy Pảy")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(80000)
            .hp(new long[]{6200000000L})
            .outfit(new short[]{92, 93, 94, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData YAMCHA = BossData.builder()
            .name("Yamcha")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(90000)
            .hp(new long[]{7000000000L})
            .outfit(new short[]{374, 375, 376, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData JACKY_CHUN = BossData.builder()
            .name("Jacky Chun")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(10000)
            .hp(new long[]{800000000L})
            .outfit(new short[]{356, 357, 358, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData THIEN_XIN_HANG = BossData.builder()
            .name("Thiên Xin Hăng")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(15000)
            .hp(new long[]{1000000000L})
            .outfit(new short[]{368, 369, 370, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.THAI_DUONG_HA_SAN, 1, 15000}
            })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData THIEN_XIN_HANG_CLONE = BossData.builder()
            .name("Thiên Xin Hăng")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(7500)
            .hp(new long[]{1200000000L})
            .outfit(new short[]{368, 369, 370, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.THAI_DUONG_HA_SAN, 1, 15000}
            })
            .secondsRest(REST_5_S)
            .build();
    public static final BossData LIU_LIU = BossData.builder()
            .name("Lêu Lêu")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(25000)
            .hp(new long[]{4000000000L})
            .outfit(new short[]{397, 398, 399, -1, -1, -1})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
                    {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
                    {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
                    {Skill.GALICK, 1, 100},
                    {Skill.THAI_DUONG_HA_SAN, 1, 15000}
            })
            .secondsRest(REST_5_S)
            .build();
}
//************************************************************************** Boss nappa

//**************************************************************************
//**************************************************************************
//**************************************************************************
//**************************************************************************
//**************************************************************************
//**************************************************************************

