package pub.peking.elon.selectdormitory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private String id;
    private String name;
    private String gender;
    private String vcode;
    private String room;
    private String building;
    private String location;
    private String grade;

    public List<Map<String, String>> toList() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("title", "学号");
        map.put("info", id);
        list.add(map);
        map = new LinkedHashMap<String, String>();
        map.put("title", "姓名");
        map.put("info", name);
        list.add(map);
        map = new LinkedHashMap<String, String>();
        map.put("title", "性别");
        map.put("info", gender);
        list.add(map);
        map = new LinkedHashMap<String, String>();
        map.put("title", "验证码");
        map.put("info", vcode);
        list.add(map);
        map = new LinkedHashMap<String, String>();
        map.put("title", "房间号");
        map.put("info", room);
        list.add(map);
        map = new LinkedHashMap<String, String>();
        map.put("title", "楼号");
        map.put("info", building);
        list.add(map);
        map = new LinkedHashMap<String, String>();
        map.put("title", "校区");
        map.put("info", location);
        list.add(map);
        map = new LinkedHashMap<String, String>();
        map.put("title", "年级");
        map.put("info", grade);
        list.add(map);

        return list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", vcode='" + vcode + '\'' +
                ", room='" + room + '\'' +
                ", building='" + building + '\'' +
                ", location='" + location + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
