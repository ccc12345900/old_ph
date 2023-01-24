package com.zuo.common;

/**
 * @author 橙宝cc
 * @date 2023/1/19 - 21:53
 */
public class createData {
    public static void main(String[] args) {
        String res = "";
        for(int i = 0;i <= 81;i++)
        {
            String dist = "\"heart" + i+"\"";
            String angle = "\"template" + i+"\"";
            String dist_d = String.valueOf(100 + i);
            String angle_d;
            if(i % 2 == 0)
                angle_d = String.valueOf(36);
            else
                angle_d = String.valueOf(37);
            res += dist+":"+dist_d+","+angle+":"+angle_d;
            if(i!=81)res = res + ",";
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

//手表健康数据更新
{"id":1,"type":2,o_data:{"heart0":100,"template0":36,"heart1":101,"template1":37,"heart2":102,"template2":36,"heart3":103,"template3":37,"heart4":104,"template4":36,"heart5":105,"template5":37,"heart6":106,"template6":36,"heart7":107,"template7":37,"heart8":108,"template8":36,"heart9":109,"template9":37,"heart10":110,"template10":36,"heart11":111,"template11":37,"heart12":112,"template12":36,"heart13":113,"template13":37,"heart14":114,"template14":36,"heart15":115,"template15":37,"heart16":116,"template16":36,"heart17":117,"template17":37,"heart18":118,"template18":36,"heart19":119,"template19":37,"heart20":120,"template20":36,"heart21":121,"template21":37,"heart22":122,"template22":36,"heart23":123,"template23":37,"heart24":124,"template24":36,"heart25":125,"template25":37,"heart26":126,"template26":36,"heart27":127,"template27":37,"heart28":128,"template28":36,"heart29":129,"template29":37,"heart30":130,"template30":36,"heart31":131,"template31":37,"heart32":132,"template32":36,"heart33":133,"template33":37,"heart34":134,"template34":36,"heart35":135,"template35":37,"heart36":136,"template36":36,"heart37":137,"template37":37,"heart38":138,"template38":36,"heart39":139,"template39":37,"heart40":140,"template40":36,"heart41":141,"template41":37,"heart42":142,"template42":36,"heart43":143,"template43":37,"heart44":144,"template44":36,"heart45":145,"template45":37,"heart46":146,"template46":36,"heart47":147,"template47":37,"heart48":148,"template48":36,"heart49":149,"template49":37,"heart50":150,"template50":36,"heart51":151,"template51":37,"heart52":152,"template52":36,"heart53":153,"template53":37,"heart54":154,"template54":36,"heart55":155,"template55":37,"heart56":156,"template56":36}}
//异常数据
{"id":1,"type":2,"heart":120,"blood":50,"template":36,"fall":0}
 */