package com.collinirwin.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.collinirwin.game.FlappyDemo;
import com.collinirwin.game.sprites.Bird;
import com.collinirwin.game.sprites.Tube;

/**
 * Created by irwin on 1/4/2016.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;

    private Bird bird;
    private Texture bg;
    private Tube tube;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 100);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        bg = new Texture("bg.png");
        tube = new Tube(100);

        tubes = new Array<Tube>();

        for(int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for(Tube tube : tubes) {
            final float camPos = cam.position.x - (cam.viewportWidth / 2.0f);
            final float tubeEdge = tube.getPosTopTube().x + tube.getTopTube().getWidth();
            final boolean isOffScreen = camPos > tubeEdge;
            if (isOffScreen) {
                final float newPos = tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT);
                tube.reposition(newPos);
            }
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);

        Vector3 birdPos = bird.getPosition();
        sb.draw(bird.getTexture(), birdPos.x, birdPos.y);

        for(Tube tube : tubes) {
            Vector2 topPos = tube.getPosTopTube();
            Vector2 botPos = tube.getPosBotTube();
            sb.draw(tube.getTopTube(), topPos.x, topPos.y);
            sb.draw(tube.getBottomTube(), botPos.x, botPos.y);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
    }
}
