import bagel.Image;
import bagel.Input;
import bagel.util.Rectangle;
import bagel.util.Point;


public abstract class GameEntity {
    public int x;
    public int y;
    public final Image image;

    public GameEntity(String imagePath, int x, int y) {
        this.image = new Image(imagePath);
        this.x = x;
        this.y = y;
    }

    public void drawImage() {
        image.draw(x, y);
    }

    public Rectangle getBoundingBox() {
        return image.getBoundingBoxAt(new Point(x, y));
    }

    public boolean isCollide(GameEntity other) {
        return (this.getBoundingBox()).intersects(other.getBoundingBox());
    }

    public abstract void Updating(Input input, Platform[] platforms, Ladder[] ladders, Hammer hammer, Donkey donkey);

}