package cn.bupt.runningplanner.classes.HomePage;

public class DongtaiItem {
    private String Title;
    private int ImageId;

    public DongtaiItem(String title, int imageId){
        this.Title = title;
        this.ImageId = imageId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }
}
