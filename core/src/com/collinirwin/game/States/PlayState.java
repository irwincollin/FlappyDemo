package com.collinirwin.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private static final int GROUND_Y_OFFSET = -80;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1;
    private Vector2 groundPos2;

    private Array<Tube> tubes;

    private BitmapFont font;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 250);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        tubes = new Array<Tube>();

        for(int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        float pos = cam.position.x - cam.viewportWidth / 2;
        groundPos1 = new Vector2(pos, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(pos + ground.getWidth(), GROUND_Y_OFFSET);

        font = new BitmapFont();
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
        updateGround();
        cam.position.x = bird.getPosition().x + 80;
        cam.update();

        for(int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);
            final float camPos = cam.position.x - (cam.viewportWidth / 2.0f);
            final float tubeEdge = tube.getPosTopTube().x + tube.getTopTube().getWidth();
            final boolean isOffScreen = camPos > tubeEdge;
            if (isOffScreen) {
                final float newPos = tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT);
                tube.reposition(newPos);
            }

            if(tube.collides(bird.getBounds())) {
                gsm.set(new MenuState(gsm));
            }
        }

        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gsm.set(new MenuState(gsm));
        }
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
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos1.y);

        font.draw(sb, "" + IncScore(), cam.position.x, cam.position.y + (cam.viewportHeight / 2.11f) - 15.0f);

        sb.end();
    }

    private int score = 0;
    private int IncScore() {
        return ++score;
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for(Tube tube : tubes) {
            tube.dispose();
        }
        ground.dispose();
    }

    private void updateGround() {
        if(cam.position.x - cam.viewportWidth / 2 > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }

        if(cam.position.x - cam.viewportWidth / 2 > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
