package cayenne.tutorial.tapestry.domain.auto;

/** Class _Painting was generated by Cayenne.
  * It is probably a good idea to avoid changing this class manually, 
  * since it may be overwritten next time code is regenerated. 
  * If you need to make any customizations, please use subclass. 
  */
public class _Painting extends org.objectstyle.cayenne.CayenneDataObject {

    public static final String ESTIMATED_PRICE_PROPERTY = "estimatedPrice";
    public static final String PAINTING_TITLE_PROPERTY = "paintingTitle";
    public static final String TO_ARTIST_PROPERTY = "toArtist";
    public static final String TO_GALLERY_PROPERTY = "toGallery";

    public static final String PAINTING_ID_PK_COLUMN = "PAINTING_ID";

    public void setEstimatedPrice(java.math.BigDecimal estimatedPrice) {
        writeProperty("estimatedPrice", estimatedPrice);
    }
    public java.math.BigDecimal getEstimatedPrice() {
        return (java.math.BigDecimal)readProperty("estimatedPrice");
    }
    
    
    public void setPaintingTitle(String paintingTitle) {
        writeProperty("paintingTitle", paintingTitle);
    }
    public String getPaintingTitle() {
        return (String)readProperty("paintingTitle");
    }
    
    
    public void setToArtist(cayenne.tutorial.tapestry.domain.Artist toArtist) {
        setToOneTarget("toArtist", toArtist, true);
    }

    public cayenne.tutorial.tapestry.domain.Artist getToArtist() {
        return (cayenne.tutorial.tapestry.domain.Artist)readProperty("toArtist");
    } 
    
    
    public void setToGallery(cayenne.tutorial.tapestry.domain.Gallery toGallery) {
        setToOneTarget("toGallery", toGallery, true);
    }

    public cayenne.tutorial.tapestry.domain.Gallery getToGallery() {
        return (cayenne.tutorial.tapestry.domain.Gallery)readProperty("toGallery");
    } 
    
    
}