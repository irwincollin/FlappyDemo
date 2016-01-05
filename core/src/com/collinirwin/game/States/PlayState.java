package com.collinirwin.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.collinirwin.game.FlappyDemo;
import com.collinirwin.game.sprites.Bird;

/**
 * Created by irwin on 1/4/2016.
 */
public class PlayState extends State {
    private Bird bird;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 100);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        bird.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        Vector3 birdPos = bird.getPosition();
        sb.draw(bird.getTexture(), birdPos.x, birdPos.y);
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
