package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture dropImage, bucketImage;
	private OrthographicCamera camera;
	private Bucket bucket;
	private BitmapFont xVal, yVal, fps;
	private float accel = 3000, decel = 2000, switchVel = 127;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		bucket = new Bucket();
        xVal = new BitmapFont();
        xVal.setColor(Color.RED);
        yVal = new BitmapFont();
        yVal.setColor(Color.GREEN);
        fps = new BitmapFont();
        fps.setColor(Color.WHITE);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.getX(), bucket.getY());
		xVal.draw(batch, "X Velocity: " + String.valueOf(bucket.getXVelocity()), 1920/2-60, 300);
		yVal.draw(batch, "Y Velocity: " + String.valueOf(bucket.getYVelocity()), 1920/2-60, 200);
		fps.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 10, 1080-60);
		batch.end();
		bucketMovement();
	}
	
	private void bucketMovement(){
		if(Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.A)){
			decelXToStop();
		}else{
			if(Gdx.input.isKeyPressed(Keys.D)) {
				bucket.changeXVelocity(accel*Gdx.graphics.getDeltaTime());
				checkEdgeCollision();
			}
			if(Gdx.input.isKeyPressed(Keys.A)) {
				bucket.changeXVelocity((-accel)*Gdx.graphics.getDeltaTime());
				checkEdgeCollision();
			}
			if(!Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A)){
				decelXToStop();
			}
		}
		if(Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.S)){
			decelYToStop();
		}else{
			if(Gdx.input.isKeyPressed(Keys.W)) {
				bucket.changeYVelocity(accel*Gdx.graphics.getDeltaTime());
				checkEdgeCollision();
			}
			if(Gdx.input.isKeyPressed(Keys.S)) {
				bucket.changeYVelocity((-accel)*Gdx.graphics.getDeltaTime());
				checkEdgeCollision();
			}
			if(!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S)){
				decelYToStop();
			}
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			bucket.setXVelocity(0);
			bucket.setYVelocity(0);
		}
		checkEdgeCollision();
		bucket.setX(bucket.getX() + bucket.getXVelocity()*Gdx.graphics.getDeltaTime());
		bucket.setY(bucket.getY() + bucket.getYVelocity()*Gdx.graphics.getDeltaTime());
	}
	private void checkEdgeCollision(){
		if(bucket.getX() < 0) {bucket.setX(0); bucket.setXVelocity(0);}
		if(bucket.getX() > 1920 - 64) {bucket.setX(1920-64); bucket.setXVelocity(0);}
		if(bucket.getY() < 0) {bucket.setY(0); bucket.setYVelocity(0);}
		if(bucket.getY() > 1080 - 64) {bucket.setY(1080-64); bucket.setYVelocity(0);}
	}
	private void decelXToStop(){
		if (bucket.getXVelocity() > 0){
			if (bucket.getXVelocity() - decel*Gdx.graphics.getDeltaTime() < 0){
				bucket.setXVelocity(0);
			}else{
				bucket.changeXVelocity(-decel*Gdx.graphics.getDeltaTime());
			}
		}
		if (bucket.getXVelocity() < 0){
			if (bucket.getXVelocity() + decel*Gdx.graphics.getDeltaTime() > 0){
				bucket.setXVelocity(0);
			}else{
				bucket.changeXVelocity(decel*Gdx.graphics.getDeltaTime());
			}
		}
	}
	private void decelYToStop(){
		if (bucket.getYVelocity() > 0){
			if (bucket.getYVelocity() - decel*Gdx.graphics.getDeltaTime() < 0){
				bucket.setYVelocity(0);
			}else{
				bucket.changeYVelocity(-decel*Gdx.graphics.getDeltaTime());
			}
		}
		if (bucket.getYVelocity() < 0){
			if (bucket.getYVelocity() + decel*Gdx.graphics.getDeltaTime() > 0){
				bucket.setYVelocity(0);
			}else{
				bucket.changeYVelocity(decel*Gdx.graphics.getDeltaTime());
			}
		}
	}
}