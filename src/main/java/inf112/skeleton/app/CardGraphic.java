package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class CardGraphic extends Image {

    public CardGraphic(Card card){
        super(new Texture("./assets/cards/exampleCard.png"));

        if(card instanceof MovementCard){
            setDrawable(new SpriteDrawable(new Sprite(new Texture("./assets/cards/movementCard.png"))));
        }else if (card instanceof RotationCard){
            setDrawable(new SpriteDrawable(new Sprite(new Texture("./assets/cards/rotationCard.png"))));
        }

        float aspect = 193/128f;

        setBounds(0, 0, 100, 100*aspect);
        addListener(new DragListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("TOUCHED");
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x - getWidth()/2, y - getHeight()/2);
            }
        });

    }



}
