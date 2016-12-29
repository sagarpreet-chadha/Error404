package thuglife.teamerror404;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Lenovo on 29-12-2016.
 */
@Table(name = "DashboardItems", id = "_UUID")
public class DashboardItem extends Model implements Serializable {
    @SerializedName("name")
    @Column(name = "name")
    @Expose
    String name;

    @SerializedName("category")
    @Column(name = "category")
    @Expose
    String category;

    @Expose
    @Column(name = "UUID" ,unique = true)
    java.util.UUID UUID;

    public DashboardItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.UUID getUUID() {
        return UUID;
    }

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<DashboardItem> getAllItems(){
        return new Select()
                .from(DashboardItem.class)
                .orderBy("name ASC")
                .execute();
    }
}
