package com.zuo.service;

/**
 * @author 橙宝cc
 * @date 2023/1/19 - 21:53
 */
public class createData {
    public static void main(String[] args) {
        String res = "";
        for(int i = 0;i <= 360;i++)
        {
            String dist = "\"dist" + i+"\"";
            String angle = "\"angle" + i+"\"";
            String dist_d = String.valueOf(10);
            String angle_d = String.valueOf(i);
            res += dist+":"+dist_d+","+angle+":"+angle_d;
            if(i!=360)res = res + ",";
        }
        System.out.println(res);
    }
}
/*
{"id":1,"type":1,r_data:{"dist0":10,"angle0":0,"dist1":10,"angle1":1,"dist2":10,"angle2":2,"dist3":10,"angle3":3,"dist4":10,"angle4":4,"dist5":10,"angle5":5,"dist6":10,"angle6":6,"dist7":10,"angle7":7,"dist8":10,"angle8":8,"dist9":10,"angle9":9,"dist10":10,"angle10":10,"dist11":10,"angle11":11,"dist12":10,"angle12":12,"dist13":10,"angle13":13,"dist14":10,"angle14":14,"dist15":10,"angle15":15,"dist16":10,"angle16":16,"dist17":10,"angle17":17,"dist18":10,"angle18":18,"dist19":10,"angle19":19,"dist20":10,"angle20":20,"dist21":10,"angle21":21,"dist22":10,"angle22":22,"dist23":10,"angle23":23,"dist24":10,"angle24":24,"dist25":10,"angle25":25,"dist26":10,"angle26":26,"dist27":10,"angle27":27,"dist28":10,"angle28":28,"dist29":10,"angle29":29,"dist30":10,"angle30":30,"dist31":10,"angle31":31,"dist32":10,"angle32":32,"dist33":10,"angle33":33,"dist34":10,"angle34":34,"dist35":10,"angle35":35,"dist36":10,"angle36":36,"dist37":10,"angle37":37,"dist38":10,"angle38":38,"dist39":10,"angle39":39,"dist40":10,"angle40":40,"dist41":10,"angle41":41,"dist42":10,"angle42":42,"dist43":10,"angle43":43,"dist44":10,"angle44":44,"dist45":10,"angle45":45,"dist46":10,"angle46":46,"dist47":10,"angle47":47,"dist48":10,"angle48":48,"dist49":10,"angle49":49,"dist50":10,"angle50":50,"dist51":10,"angle51":51,"dist52":10,"angle52":52,"dist53":10,"angle53":53,"dist54":10,"angle54":54,"dist55":10,"angle55":55,"dist56":10,"angle56":56,"dist57":10,"angle57":57,"dist58":10,"angle58":58,"dist59":10,"angle59":59,"dist60":10,"angle60":60,"dist61":10,"angle61":61,"dist62":10,"angle62":62,"dist63":10,"angle63":63,"dist64":10,"angle64":64,"dist65":10,"angle65":65,"dist66":10,"angle66":66,"dist67":10,"angle67":67,"dist68":10,"angle68":68}}

 */