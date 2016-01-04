package com.collinirwin.game.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.collinirwin.game.FlappyDemo;

/**
 * Created by irwin on 1/4/2016.
 */
public class MenuState extends State {
    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playBtn.png");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
        int centeredWidth = (FlappyDemo.WIDTH / 2) - (playBtn.getWidth()/2);
        sb.draw(playBtn, centeredWidth, FlappyDemo.HEIGHT / 2);
        sb.end();
    }

}
