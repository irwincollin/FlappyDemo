package com.collinirwin.game.sprites;

import com.badlogic.gdx.graphics.Texture;
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

    private Texture texture;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("bird.png");
    }

    public void update(float dt) {
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
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void jump() {
        velocity.y = 300;
    }
}
