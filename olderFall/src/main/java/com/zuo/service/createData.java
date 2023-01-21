package com.zuo.service;

/**
 * @author 橙宝cc
 * @date 2023/1/19 - 21:53
 */
public class createData {
    public static void main(String[] args) {
        String res = "";
        for(int i = 0;i <= 86;i++)
        {
            String dist = "\"dist" + i+"\"";
            String angle = "\"angle" + i+"\"";
            String dist_d = String.valueOf(10);
            String angle_d = String.valueOf(i);
            res += dist+":"+dist_d+","+angle+":"+angle_d;
            if(i!=86)res = res + ",";
        }
        System.out.println(res);
    }
}
/*
//只有一个人
{"id":1,"type":1,r_data:{"dist0":10,"angle0":0,"dist1":10,"angle1":1,"dist2":10,"angle2":2,"dist3":10,"angle3":3,"dist4":10,"angle4":4,"dist5":10,"angle5":5,"dist6":10,"angle6":6,"dist7":10,"angle7":7,"dist8":10,"angle8":8,"dist9":10,"angle9":9,"dist10":10,"angle10":10,"dist11":10,"angle11":11,"dist12":10,"angle12":12,"dist13":10,"angle13":13,"dist14":10,"angle14":14,"dist15":10,"angle15":15,"dist16":10,"angle16":16}}
//两人
{"id":1,"type":1,r_data:{"dist0":10,"angle0":0,"dist1":10,"angle1":1,"dist2":10,"angle2":2,"dist3":10,"angle3":3,"dist4":10,"angle4":4,"dist5":10,"angle5":5,"dist6":10,"angle6":6,"dist7":10,"angle7":7,"dist8":10,"angle8":8,"dist9":10,"angle9":9,"dist10":10,"angle10":30,"dist11":10,"angle11":31,"dist12":10,"angle12":32,"dist13":10,"angle13":33,"dist14":10,"angle14":34,"dist15":10,"angle15":35,"dist16":10,"angle16":36}}
//两个人，indoor位置一个人
{"id":1,"type":1,r_data:{"dist0":10,"angle0":0,"dist1":10,"angle1":1,"dist2":10,"angle2":2,"dist3":10,"angle3":3,"dist4":10,"angle4":4,"dist5":10,"angle5":5,"dist6":10,"angle6":6,"dist7":10,"angle7":7,"dist8":10,"angle8":8,"dist9":10,"angle9":9,"dist10":95,"angle10":30,"dist11":95,"angle11":31,"dist12":95,"angle12":32,"dist13":95,"angle13":33,"dist14":95,"angle14":34,"dist15":95,"angle15":35,"dist16":95,"angle16":36}}
//两个人，outdoor位置一个人
{"id":1,"type":1,r_data:{"dist0":10,"angle0":0,"dist1":10,"angle1":1,"dist2":10,"angle2":2,"dist3":10,"angle3":3,"dist4":10,"angle4":4,"dist5":10,"angle5":5,"dist6":10,"angle6":6,"dist7":10,"angle7":7,"dist8":10,"angle8":8,"dist9":10,"angle9":9,"dist10":98,"angle10":14,"dist11":98,"angle11":15,"dist12":98,"angle12":16,"dist13":98,"angle13":17,"dist14":98,"angle14":18,"dist15":98,"angle15":19,"dist16":98,"angle16":20}}
//两个人，已出门
{"id":1,"type":1,r_data:{"dist0":10,"angle0":0,"dist1":10,"angle1":1,"dist2":10,"angle2":2,"dist3":10,"angle3":3,"dist4":10,"angle4":4,"dist5":10,"angle5":5,"dist6":10,"angle6":6,"dist7":10,"angle7":7,"dist8":10,"angle8":8,"dist9":10,"angle9":9,"dist10":103,"angle10":14,"dist11":103,"angle11":15,"dist12":103,"angle12":16,"dist13":103,"angle13":17,"dist14":103,"angle14":18,"dist15":103,"angle15":19,"dist16":103,"angle16":20}}

{"id":1,"type":1,r_data:{"dist0":10,"angle0":0,"dist1":10,"angle1":1,"dist2":10,"angle2":2,"dist3":10,"angle3":3,"dist4":10,"angle4":4, "dist5":10,"angle5":5,"dist6":10,"angle6":6,"dist7":10,"angle7":7,"dist8":10,"angle8":8,"dist9":10,"angle9":9, "dist10":10,"angle10":10,"dist11":10,"angle11":11,"dist12":10,"angle12":12,"dist13":10,"angle13":13,"dist14":10, "angle14":14,"dist15":10,"angle15":15,"dist16":10,"angle16":16,"dist17":10,"angle17":17,"dist18":10,"angle18":18, "dist19":10,"angle19":19,"dist20":10,"angle20":20,"dist21":10,"angle21":21,"dist22":10,"angle22":22,"dist23":10, "angle23":23,"dist24":10,"angle24":24,"dist25":10,"angle25":25,"dist26":10,"angle26":26,"dist27":10,"angle27":27, "dist28":10,"angle28":28,"dist29":10,"angle29":29,"dist30":10,"angle30":30,"dist31":10,"angle31":31,"dist32":10, "angle32":32,"dist33":10,"angle33":33,"dist34":10,"angle34":34,"dist35":10,"angle35":35,"dist36":10,"angle36":36}}
 */