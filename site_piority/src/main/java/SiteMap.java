import java.util.List;

/**
 * Created by alina on 15.6.15.
 */
public class SiteMap {
    private String site;
    private Boolean hasSitemap;
    private Boolean hasPriority;
    private Boolean valid;
    private List<String> inventories;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Boolean getHasSitemap() {
        return hasSitemap;
    }

    public void setHasSitemap(Boolean hasSitemap) {
        this.hasSitemap = hasSitemap;
    }

    public Boolean getHasPriority() {
        return hasPriority;
    }

    public void setHasPriority(Boolean hasPriority) {
        this.hasPriority = hasPriority;
    }


    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<String> getInventories() {
        return inventories;
    }

    public void setInventories(List<String> inventories) {
        this.inventories = inventories;
    }
}
