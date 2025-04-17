import bagel.Image;
import bagel.Input;
import bagel.util.Rectangle;
import bagel.util.Point;

// superclass for all game entitys
public abstract class GameEntity {
    public int x;
    public int y;
    public final Image image;

    // constructor load image and set x-, y- coord for each game entity
    public GameEntity(String imagePath, int x, int y) {
        this.image = new Image(imagePath);
        this.x = x;
        this.y = y;
    }

    // draw image at the position
    public void drawImage() {
        image.draw(x, y);
    }

    // create a rectangle around the image centred (x, y)
    public Rectangle getBoundingBox() {
        return image.getBoundingBoxAt(new Point(x, y));
    }

    // determine if two game entitys are touched (intersected)
    public boolean isCollide(GameEntity other) {
        return (this.getBoundingBox()).intersects(other.getBoundingBox());
    }

    // abstract method for updating game entity, need to be
    // overridden in all subclasses for different implementations
    public abstract void Updating(Input input, Platform[] platforms, Ladder[] ladders,
                                  Hammer hammer, Donkey donkey, Barrel[] barrels);

}