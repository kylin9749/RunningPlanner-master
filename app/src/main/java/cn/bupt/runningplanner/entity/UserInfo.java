package cn.bupt.runningplanner.entity;

public class UserInfo {
    private String name;
    private String email;
    private String password;
    private String sex;
    private int age;
    private int high;
    private int weight;
    private int totalLong;
    private int totalTime;
    private int totalCalorie;
    private int number;
    //判断返回i结果的
    private int resultCode;
    //判断上传数据的源  来自哪里
    private int updateSource;
    // 1代表跑步完成界面
    // 2代表修改name
    // 3代表修改年龄
    // 4代表修改身高
    // 5代表修改体重
    // 6代表修改年龄

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTotalLong() {
        return totalLong;
    }

    public void setTotalLong(int totalLong) {
        this.totalLong = totalLong;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalCalorie() {
        return totalCalorie;
    }

    public void setTotalCalorie(int totalCalorie) {
        this.totalCalorie = totalCalorie;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getUpdateSource() {
        return updateSource;
    }

    public void setUpdateSource(int updateSource) {
        this.updateSource = updateSource;
    }
}
