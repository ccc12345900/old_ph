package com.zuo.common;

import com.zuo.service.OldPhDataService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
/**
 * @author 橙宝cc
 * @date 2023/1/24 - 9:33
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class KNN {

    // 数据模型
    public static class KnnModel implements Comparable<KnnModel>{
        public double paramA;
        public double paramB;
        public double paramC;
        public double distance;
        String type;
        public double getDistance() {
            return distance;
        }

        public KnnModel(double a, double b, double c, String type) {
            this.paramA = a;
            this.paramB = b;
            this.paramC = c;
            this.type = type;
        }

        @Override
        public int compareTo(KnnModel model) {
            return Double.compare(this.distance, model.distance);
        }
    }

    /** 计算新数据与训练数据的距离 **/
    private static List<KnnModel> calculate(List<KnnModel> modelList, KnnModel model, int k) {
        for (KnnModel m : modelList) {
            double distanceA = Math.abs(model.paramA - m.paramA);
            double distanceB = Math.abs(model.paramB - m.paramB);
            double distanceC = Math.abs(model.paramC - m.paramC);
            double gap = distanceA + distanceB + distanceC;
            // 训练数据保存与目标数据的距离，方便下一步排序
            m.distance = gap;
        }
        // 根据distance大小进行排序(从小到大)
        Collections.sort(modelList, Comparator.comparingDouble(KnnModel::getDistance));
        // 返回差距最小的k个值
        List<KnnModel> resultList = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            resultList.add(modelList.get(i));
        }
        return resultList;
    }

    /** 统计出最多的类型 **/
    private static String findTypeByScope(List<KnnModel> modelList) {
        Map<String, Integer> typeMap = new HashMap<>(modelList.size());
        // 统计类型
        for (KnnModel model : modelList) {
            int sum = typeMap.get(model.type) == null ? 1 : typeMap.get(model.type) + 1;
            typeMap.put(model.type, sum);
        }
        // 返回出现次数最多的类型
        List<Map.Entry<String,Integer>> list = new ArrayList(typeMap.entrySet());
        Collections.sort(list, Comparator.comparingInt(Map.Entry::getValue));
        return list.get(list.size()-1).getKey();
    }

    /** Knn
     * @param modelList 训练数据集
     * @param model 待分类数据
     * @param k 范围变量
     * */
    public static String calculateKnn(List<KnnModel> modelList, KnnModel model, int k) {
        // (1) 计算训练数据与待分类数据的各自相对距离，并返回差距最小的K个训练结果
        List<KnnModel> minKnnList = calculate(modelList, model, k);
        double distance = minKnnList.get(0).distance;
        if(distance >= 15)
            return "身体数据异常";
        // (2) 找出差距最小的K个结果中，最多的类型
        return findTypeByScope(minKnnList);
    }

    public static void main(String[] args) {
        // 准备数据（假设参数A为身高，B为体重，C为颜值，用类型分为'帅哥''普通''屌丝'）

        List<KnnModel> knnModelList = new ArrayList<>();
        knnModelList.add(new KnnModel(178, 75, 88, "帅哥"));
        knnModelList.add(new KnnModel(180, 73, 96, "帅哥"));
        knnModelList.add(new KnnModel(183, 80, 95, "帅哥"));
//        knnModelList.add(new KnnModel(173, 75, 95, "普通"));
//        knnModelList.add(new KnnModel(170, 72, 80, "普通"));
//        knnModelList.add(new KnnModel(171, 71, 89, "普通"));
//        knnModelList.add(new KnnModel(155, 70, 60, "屌丝"));
//        knnModelList.add(new KnnModel(159, 80, 68, "屌丝"));
//        knnModelList.add(new KnnModel(160, 75, 70, "屌丝"));
        // 预测数据
        KnnModel model = new KnnModel(155.5, 70, 60, null);
        // 输出预测类型
        System.out.println(calculateKnn(knnModelList, model, 3));
    }

}

