package com.collinirwin.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.collinirwin.game.FlappyDemo;

/**
 * Created by irwin on 1/4/2016.
 */
public class Bird {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Animation birdAnimation;
    private Texture texture;
    private Sound flap;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("bird.png");
        Texture animatedBird = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(animatedBird), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt) {
        birdAnimation.update(dt);
        if(position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        position.add(0, velocity.y, 0);
        if(position.y < 0) {
            position.y = 0;
        }
        else if( position.y > (FlappyDemo.HEIGHT / 2) - 15) {
            position.y = (FlappyDemo.HEIGHT / 2) - 15;
        }
        velocity.scl(1 / dt);

        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public void jump() {
        flap.play(0.4f);
        velocity.y = 220;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        flap.dispose();
        texture.dispose();
    }
}
