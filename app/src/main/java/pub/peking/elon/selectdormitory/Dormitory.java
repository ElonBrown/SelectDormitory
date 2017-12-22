package pub.peking.elon.selectdormitory;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Dormitory {
    private Integer integer1;
    private Integer integer2;
    private Integer integer3;
    private Integer integer4;
    private Integer integer5;

    public List<Map<String, Object>> toList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("title", "五号楼剩余床位：");
        map.put("info", integer1);
        list.add(map);
        map = new LinkedHashMap<String, Object>();
        map.put("title", "十三号楼剩余床位：");
        map.put("info", integer2);
        list.add(map);
        map = new LinkedHashMap<String, Object>();
        map.put("title", "十四号楼剩余床位：");
        map.put("info", integer3);
        list.add(map);
        map = new LinkedHashMap<String, Object>();
        map.put("title", "八号楼剩余床位：");
        map.put("info", integer4);
        list.add(map);
        map = new LinkedHashMap<String, Object>();
        map.put("title", "九号楼剩余床位：");
        map.put("info", integer5);
        list.add(map);

        return list;
    }

    @Override
    public String toString() {
        return "Dormitory{" +
                "integer1=" + integer1 +
                ", integer2=" + integer2 +
                ", integer3=" + integer3 +
                ", integer4=" + integer4 +
                ", integer5=" + integer5 +
                '}';
    }

    public Integer getInteger1() {
        return integer1;
    }

    public void setInteger1(Integer integer1) {
        this.integer1 = integer1;
    }

    public Integer getInteger2() {
        return integer2;
    }

    public void setInteger2(Integer integer2) {
        this.integer2 = integer2;
    }

    public Integer getInteger3() {
        return integer3;
    }

    public void setInteger3(Integer integer3) {
        this.integer3 = integer3;
    }

    public Integer getInteger4() {
        return integer4;
    }

    public void setInteger4(Integer integer4) {
        this.integer4 = integer4;
    }

    public Integer getInteger5() {
        return integer5;
    }

    public void setInteger5(Integer integer5) {
        this.integer5 = integer5;
    }
}
